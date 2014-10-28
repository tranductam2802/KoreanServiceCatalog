package gdg.nat.connection;

import gdg.nat.ksc.connection.request.CheckCateVersionRequest;
import gdg.nat.ksc.connection.request.GetCategoriesRequest;
import gdg.nat.ksc.connection.request.SearchRequest;
import gdg.nat.ksc.connection.response.CheckCateVersionResponse;
import gdg.nat.ksc.connection.response.GetCategoriesResponse;
import gdg.nat.ksc.connection.response.ListServiceResponse;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WebServiceReceiver extends BroadcastReceiver {
	public static final String INTENT_WEB_SERVICE = "response";
	public static final String KEY_STATUS = "status";
	public static final String KEY_REQUEST_PARAM = "request_param";
	public static final String KEY_CODE = "code";
	public static final String KEY_DATA = "data";

	public static final int STATUS_START = 0;
	public static final int STATUS_FINISH = 1;

	private IWebServiceReceiverListener webServiceReceiverListener;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (webServiceReceiverListener != null) {
			int status = intent.getIntExtra(KEY_STATUS, STATUS_START);
			RequestParam requestParam = (RequestParam) intent
					.getSerializableExtra(KEY_REQUEST_PARAM);
			if (status == STATUS_START) {
				/* ===== Send on start request ===== */
				webServiceReceiverListener.onRequest(requestParam);
			} else {
				/* ===== Get response data and show system error here ===== */
				int code = intent.getIntExtra(KEY_CODE,
						ResponseCode.CLIENT_ERROR_UNKNOW);
				String data = intent.getStringExtra(KEY_DATA);

				/* ===== Parse response here ===== */
				ResponseParser responseParser = null;
				if (requestParam instanceof CheckCateVersionRequest) {
					responseParser = new CheckCateVersionResponse(data, code);
				} else if (requestParam instanceof GetCategoriesRequest) {
					GetCategoriesResponse categoriesResponse = new GetCategoriesResponse(
							data, code);
					responseParser = categoriesResponse;
				} else if (requestParam instanceof SearchRequest) {
					ListServiceResponse listServiceResponse = new ListServiceResponse(
							data, code);
					responseParser = listServiceResponse;
				}
				webServiceReceiverListener.onReceiver(requestParam,
						responseParser);
			}
		}
	}

	public void setWebServiceReceiverListener(
			IWebServiceReceiverListener webServiceReceiverListener) {
		this.webServiceReceiverListener = webServiceReceiverListener;
	}
}