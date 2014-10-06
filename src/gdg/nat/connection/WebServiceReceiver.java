package gdg.nat.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WebServiceReceiver extends BroadcastReceiver {
	public static final String INTENT_WEB_SERVICE = "response";
	public static final String KEY_STATUS = "status";
	public static final String KEY_API = "api";
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
			String api = intent.getStringExtra(KEY_API);
			String requestParam = intent.getStringExtra(KEY_REQUEST_PARAM);
			if (status == STATUS_START) {
				webServiceReceiverListener.onRequest(api, requestParam);
			} else {
				int code = intent.getIntExtra(KEY_CODE,
						ResponseCode.CLIENT_ERROR_UNKNOW.getCode());
				String data = intent.getStringExtra(KEY_DATA);
				Response response = new Response(api, requestParam, code, data);
				webServiceReceiverListener.onReceiver(response);
			}
		}
	}

	public void setWebServiceReceiverListener(
			IWebServiceReceiverListener webServiceReceiverListener) {
		this.webServiceReceiverListener = webServiceReceiverListener;
	}
}