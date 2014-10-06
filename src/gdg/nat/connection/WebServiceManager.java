package gdg.nat.connection;

import gdg.nat.ksc.config.KSCApp;
import gdg.nat.util.GdgLog;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

public class WebServiceManager implements IWebServiceListener {
	private final String TAG = "TrackingWSManager";
	private static WebServiceManager webServiceManager = new WebServiceManager();

	private WebServiceCaller webServiceCaller;

	private List<Request> requestQueue = new ArrayList<>();
	private List<Response> oldResponseQueue = new ArrayList<>();

	// COnfig area
	private final long DELAY_SAME_API = 1 * 1000;
	private final EHttpMethod DEFAULT_HTTP_METHOD = EHttpMethod.GET;

	private WebServiceManager() {
		// Do nothing
	}

	public static WebServiceManager getInstance() {
		return webServiceManager;
	}

	/** Add a request to queue to waiting for running */
	private void addRequest(Request request) {
		requestQueue.add(request);
	}

	/** Remove the exited request in queue */
	private void removeExitedRequest(String requestParam) {
		List<Response> newList = new ArrayList<Response>();
		for (Response response : oldResponseQueue) {
			if (!response.getRequestParam().equals(requestParam))
				newList.add(response);
		}
		oldResponseQueue = newList;
	}

	/** And executed request's response to queue */
	private void addResponse(Response response) {
		oldResponseQueue.add(response);
	}

	private boolean isNearRequest(String requestParam) {
		if (oldResponseQueue.size() <= 0)
			return false;
		return oldResponseQueue.get(oldResponseQueue.size() - 1)
				.getRequestParam().equals(requestParam);
	}

	/** Get the response of the request requested */
	private Response getOldResponse(String requestParam) {
		if (oldResponseQueue.size() <= 0)
			return null;

		// Create a response result
		Response oldResponse = null;
		for (Response responseItem : oldResponseQueue) {
			String oldRequestJson = responseItem.getRequestParam();
			String requestJson = requestParam;
			if (oldRequestJson.equals(requestJson)) {
				oldResponse = responseItem;
			}
		}
		return oldResponse;
	}

	/** Remove exited request's response in queue */
	private void removeOldResponse(String api) {
		List<Response> oldResponseQueueTemp = new ArrayList<>();

		for (Response responseItem : oldResponseQueue) {
			String oldApi = responseItem.getApi();
			if (!oldApi.equals(api)) {
				oldResponseQueueTemp.add(responseItem);
			}
		}
		oldResponseQueue = oldResponseQueueTemp;
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-place this request.
	 */
	public final void restartRequestServer(RequestParam requestParam) {
		restartRequestServer(requestParam, DEFAULT_HTTP_METHOD);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-place this request.
	 */
	public final void restartRequestServer(RequestParam requestParam,
			String subURL) {
		restartRequestServer(requestParam, subURL, DEFAULT_HTTP_METHOD);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-place this request.
	 */
	public final void restartRequestServer(String subURL) {
		restartRequestServer(subURL, DEFAULT_HTTP_METHOD);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-place this request.
	 */
	public final void restartRequestServer(RequestParam requestParam,
			EHttpMethod httpMethod) {
		startRequestServer(requestParam, httpMethod, true);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-place this request.
	 */
	public final void restartRequestServer(RequestParam requestParam,
			String subURL, EHttpMethod httpMethod) {
		startRequestServer(requestParam, subURL, httpMethod, true);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-place this request.
	 */
	public final void restartRequestServer(String subURL, EHttpMethod httpMethod) {
		startRequestServer(subURL, httpMethod, true);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-request at local.
	 */
	public final void startRequestServer(RequestParam requestParam) {
		startRequestServer(requestParam, DEFAULT_HTTP_METHOD);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-request at local.
	 */
	public final void startRequestServer(RequestParam requestParam,
			String subURL) {
		startRequestServer(requestParam, subURL, DEFAULT_HTTP_METHOD);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-request at local.
	 */
	public final void startRequestServer(String subURL) {
		startRequestServer(subURL, DEFAULT_HTTP_METHOD);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-request at local.
	 */
	public final void startRequestServer(RequestParam requestParam,
			EHttpMethod httpMethod) {
		startRequestServer(requestParam, httpMethod, false);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-request at local.
	 */
	public final void startRequestServer(RequestParam requestParam,
			String subURL, EHttpMethod httpMethod) {
		startRequestServer(requestParam, subURL, httpMethod, false);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-request at local.
	 */
	public final void startRequestServer(String subURL, EHttpMethod httpMethod) {
		startRequestServer(subURL, httpMethod, false);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-request at local. This is an base method. Only change this method.
	 */
	public void startRequestServer(RequestParam requestParam,
			EHttpMethod httpMethod, boolean isRestart) {
		Request request = new Request(requestParam, httpMethod);
		addRequest(request);
		notifyExecute(isRestart);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-request at local. This is an base method. Only change this method.
	 */
	public void startRequestServer(RequestParam requestParam, String subURL,
			EHttpMethod httpMethod, boolean isRestart) {
		Request request = new Request(requestParam, subURL, httpMethod);
		addRequest(request);
		notifyExecute(isRestart);
	}

	/**
	 * Start a request to server. If had an exited request before, it will be
	 * re-request at local. This is an base method. Only change this method.
	 */
	public void startRequestServer(String subURL, EHttpMethod httpMethod,
			boolean isRestart) {
		Request request = new Request(subURL, httpMethod);
		addRequest(request);
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

		final Request request = requestQueue.get(0);
		onStartRequest(request);
		if (isRestart) {
			if (isNearRequest(request.getRequestParam())) {
				removeOldResponse(request.getApi());
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						startRequest();
					}
				}, DELAY_SAME_API);
			} else {
				removeOldResponse(request.getApi());
				startRequest();
			}
		} else {
			Response oldResponse = getOldResponse(request.getRequestParam());
			if (oldResponse == null) {
				startRequest();
			} else {
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
			Request request = requestQueue.remove(0);
			webServiceCaller = new WebServiceCaller();
			webServiceCaller.setWebServiceListener(this);
			webServiceCaller.execute(request);
		}
	}

	public void onStartRequest(Request request) {
		Intent intent = new Intent(WebServiceReceiver.INTENT_WEB_SERVICE);
		intent.putExtra(WebServiceReceiver.KEY_STATUS,
				WebServiceReceiver.STATUS_START);
		intent.putExtra(WebServiceReceiver.KEY_API, request.getApi());
		intent.putExtra(WebServiceReceiver.KEY_REQUEST_PARAM,
				request.getRequestParam());
		LocalBroadcastManager.getInstance(KSCApp.getInstance()).sendBroadcast(
				intent);
	}

	@Override
	public void onFinishRequest(Response response) {
		// Add response when success
		if (response.getResponseCode() != ResponseCode.SERVER_SUCCESS.getCode()) {
			removeExitedRequest(response.getRequestParam());
			removeOldResponse(response.getApi());
			addResponse(response);
		} else {
			// TODO: Error UI.
		}
		Intent intent = new Intent(WebServiceReceiver.INTENT_WEB_SERVICE);
		intent.putExtra(WebServiceReceiver.KEY_STATUS,
				WebServiceReceiver.STATUS_FINISH);
		intent.putExtra(WebServiceReceiver.KEY_API, response.getApi());
		intent.putExtra(WebServiceReceiver.KEY_REQUEST_PARAM,
				response.getRequestParam());
		intent.putExtra(WebServiceReceiver.KEY_CODE, response.getResponseCode());
		intent.putExtra(WebServiceReceiver.KEY_DATA, response.getResponseData());
		LocalBroadcastManager.getInstance(KSCApp.getInstance()).sendBroadcast(
				intent);

		if (requestQueue.size() > 0) {
			final Request nextRequest = requestQueue.get(0);
			Response oldResponse = getOldResponse(nextRequest.getRequestParam());
			if (oldResponse == null) {
				startRequest();
			} else {
				onFinishRequest(oldResponse);
			}
		}
	}
}