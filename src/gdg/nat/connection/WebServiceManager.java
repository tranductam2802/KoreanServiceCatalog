package gdg.nat.connection;

import gdg.nat.ksc.config.KSCApp;
import gdg.nat.ksc.connection.request.GetCategoriesRequest;
import gdg.nat.ksc.connection.response.GetCategoriesResponse;
import gdg.nat.ksc.data.Categories;
import gdg.nat.util.GdgLog;
import gdg.nat.util.ObjectCache;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

public class WebServiceManager implements IWebServiceListener {
	private final String TAG = "TrackingWSManager";
	private static WebServiceManager webServiceManager = new WebServiceManager();

	private WebServiceCaller webServiceCaller;

	private List<RequestParam> requestQueue = new ArrayList<RequestParam>();
	private List<Response> oldResponseQueue = new ArrayList<Response>();

	// COnfig area
	private final long DELAY_SAME_API = 1 * 1000;

	private WebServiceManager() {
		// Do nothing
	}

	public static WebServiceManager getInstance() {
		return webServiceManager;
	}

	/** Add a request to queue to waiting for running */
	private void addRequest(RequestParam requestParam) {
		requestQueue.add(requestParam);
	}

	/** Remove the exited request in queue */
	private void removeExitedRequest(RequestParam requestParam) {
		List<Response> newList = new ArrayList<Response>();
		for (Response response : oldResponseQueue) {
			String oldApi = response.getRequestParam().getApi();
			String currentApi = requestParam.getApi();
			String oldRequest = response.getRequestParam().getParam();
			String curentRequest = requestParam.getParam();
			if (!oldApi.equals(currentApi) || !oldRequest.equals(curentRequest))
				newList.add(response);
		}
		oldResponseQueue = newList;
	}

	/** And executed request's response to queue */
	private void addResponse(Response response) {
		oldResponseQueue.add(response);
	}

	private boolean isNearRequest(RequestParam requestParam) {
		if (oldResponseQueue.size() <= 0)
			return false;
		Response response = oldResponseQueue.get(oldResponseQueue.size() - 1);
		String oldApi = response.getRequestParam().getApi();
		String currentApi = requestParam.getApi();
		String oldRequest = response.getRequestParam().getParam();
		String curentRequest = requestParam.getParam();
		return oldApi.equals(currentApi) && oldRequest.equals(curentRequest);
	}

	/** Get the response of the request requested */
	private Response getOldResponse(RequestParam requestParam) {
		if (oldResponseQueue.size() <= 0)
			return null;

		// Create a response result
		Response oldResponse = null;
		for (Response response : oldResponseQueue) {
			String oldApi = response.getRequestParam().getApi();
			String currentApi = requestParam.getApi();
			String oldRequest = response.getRequestParam().getParam();
			String curentRequest = requestParam.getParam();

			if (oldApi.equals(currentApi) && oldRequest.equals(curentRequest)) {
				oldResponse = response;
			}
		}
		return oldResponse;
	}

	/** Remove exited request's response in queue */
	private void removeOldResponse(RequestParam requestParam) {
		List<Response> oldResponseQueueTemp = new ArrayList<Response>();

		for (Response response : oldResponseQueue) {
			String oldApi = response.getRequestParam().getApi();
			String currentApi = requestParam.getApi();
			String oldRequest = response.getRequestParam().getParam();
			String curentRequest = requestParam.getParam();
			if (!oldApi.equals(currentApi) || !oldRequest.equals(curentRequest)) {
				oldResponseQueueTemp.add(response);
			}
		}
		oldResponseQueue = oldResponseQueueTemp;
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-place this request.
	 */
	public final void restartRequestServer(RequestParam requestParam) {
		startRequestServer(requestParam, true);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-request at local.
	 */
	public final void startRequestServer(RequestParam requestParam) {
		startRequestServer(requestParam, false);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-request at local. This is an base method. Only change this method.
	 */
	public void startRequestServer(RequestParam requestParam, boolean isRestart) {
		addRequest(requestParam);
		notifyExecute(isRestart);
	}

	/**
	 * Check and notify WebServiceCaller to execute request.
	 * <p>
	 * The first, It check to be restart request or not. If restart request is
	 * required, don't care about old response at first. WebServiceCaller will
	 * remove it at the executeNextCall() method. The next, it check size of the
	 * request queue. WebServiceCaller is going to execute when have at least
	 * one. The last, remember to remove the old request's response.
	 * </p>
	 * <p>
	 * If haven't to restart request, it check the request queue to get the next
	 * request. Inspect exited request's response in queue. It act the same the
	 * first isRestart condition when don't have any the same response. Or it
	 * load the old response.
	 * </p>
	 */
	private void notifyExecute(boolean isRestart) {
		// Check request to execute. If don't have any request, return.
		if (requestQueue.size() <= 0) {
			GdgLog.e(TAG, "Do not have any request in queue");
			return;
		}

		final RequestParam requestParam = requestQueue.get(0);
		onStartRequest(requestParam);
		if (isRestart) {
			if (isNearRequest(requestParam)) {
				removeOldResponse(requestParam);
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						startRequest();
					}
				}, DELAY_SAME_API);
			} else {
				removeOldResponse(requestParam);
				startRequest();
			}
		} else {
			Response oldResponse = getOldResponse(requestParam);
			if (oldResponse == null) {
				startRequest();
			} else {
				requestQueue.remove(0);
				onFinishRequest(oldResponse);
			}
		}
	}

	/** Only this method control instance of webServiceCaller */
	private void startRequest() {
		// Check request to execute. If don't have any request, return.
		if (requestQueue.size() <= 0) {
			GdgLog.e(TAG, "Do not have any request in queue");
			return;
		}

		if (webServiceCaller != null && webServiceCaller.isRunning()) {
			GdgLog.e(TAG, "Web service caller is running");
		} else {
			RequestParam requestParam = requestQueue.remove(0);
			webServiceCaller = new WebServiceCaller();
			webServiceCaller.setWebServiceListener(this);
			webServiceCaller.execute(requestParam);
		}
	}

	public void onStartRequest(RequestParam requestParam) {
		Intent intent = new Intent(WebServiceReceiver.INTENT_WEB_SERVICE);
		intent.putExtra(WebServiceReceiver.KEY_STATUS,
				WebServiceReceiver.STATUS_START);
		intent.putExtra(WebServiceReceiver.KEY_REQUEST_PARAM, requestParam);
		LocalBroadcastManager.getInstance(KSCApp.getInstance()).sendBroadcast(
				intent);
	}

	@Override
	public void onFinishRequest(Response response) {
		// Add response when success
		if (response.getResponseCode() != ResponseCode.SERVER_SUCCESS) {
			removeExitedRequest(response.getRequestParam());
			removeOldResponse(response.getRequestParam());
			addResponse(response);
		}

		if (response.getRequestParam() instanceof GetCategoriesRequest) {
			GetCategoriesResponse categoriesResponse = new GetCategoriesResponse(
					response.getResponseData(), response.getResponseCode());
			Categories categories = categoriesResponse.getCategories();
			ObjectCache.getInstance().saveCategories(categories);
		}
		Intent intent = new Intent(WebServiceReceiver.INTENT_WEB_SERVICE);
		intent.putExtra(WebServiceReceiver.KEY_STATUS,
				WebServiceReceiver.STATUS_FINISH);
		intent.putExtra(WebServiceReceiver.KEY_REQUEST_PARAM,
				response.getRequestParam());
		intent.putExtra(WebServiceReceiver.KEY_CODE, response.getResponseCode());
		intent.putExtra(WebServiceReceiver.KEY_DATA, response.getResponseData());
		LocalBroadcastManager.getInstance(KSCApp.getInstance()).sendBroadcast(
				intent);

		if (requestQueue.size() > 0) {
			final RequestParam nextRequest = requestQueue.get(0);
			Response oldResponse = getOldResponse(nextRequest);
			if (oldResponse == null) {
				startRequest();
			} else {
				requestQueue.remove(0);
				onFinishRequest(oldResponse);
			}
		}
	}
}