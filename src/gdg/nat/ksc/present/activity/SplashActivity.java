package gdg.nat.ksc.present.activity;

import gdg.nat.base.BaseActivity;
import gdg.nat.connection.IWebServiceReceiverListener;
import gdg.nat.connection.Response;
import gdg.nat.ksc.R;
import gdg.nat.util.GdgLog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends BaseActivity implements
		IWebServiceReceiverListener {
	private TextView txtLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_splash);
		txtLoading = (TextView) findViewById(R.id.txt_loading);

		getWebServiceManager().startRequestServer("/ksc/api/categories/");
	}

	@Override
	public void dismisAllDialog() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onRequest(String api, String requestParam) {
		if (api.equals("/ksc/api/categories/"))
			txtLoading.setText("Tam dep trai loading: " + api + " | "
					+ requestParam);
	}

	@Override
	public void onReceiver(final Response response) {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				txtLoading.setText(response.getResponseData());
			}
		}, 3000);
		GdgLog.d(
				"Tam dep trai",
				"" + response.getApi() + " | " + response.getRequestParam()
						+ " | " + response.getResponseCode() + " | "
						+ response.getResponseData());
	}
}