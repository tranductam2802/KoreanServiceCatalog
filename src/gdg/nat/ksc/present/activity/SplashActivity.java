package gdg.nat.ksc.present.activity;

import gdg.nat.base.BaseActivity;
import gdg.nat.connection.IWebServiceReceiverListener;
import gdg.nat.connection.RequestParam;
import gdg.nat.connection.ResponseCode;
import gdg.nat.connection.ResponseParser;
import gdg.nat.ksc.R;
import gdg.nat.ksc.connection.request.CheckCateVersionRequest;
import gdg.nat.ksc.connection.request.DownloadIconRequest;
import gdg.nat.ksc.connection.request.GetCategoriesRequest;
import gdg.nat.ksc.connection.response.DownloadIconResponse;
import gdg.nat.ksc.connection.response.GetCategoriesResponse;
import gdg.nat.ksc.data.Categories;
import gdg.nat.ksc.service.DownloadIconService;
import gdg.nat.ksc.service.DownloadReceiver;
import gdg.nat.ksc.service.DownloadReceiver.IUpdateDownloadProgress;
import gdg.nat.util.ObjectCache;
import gdg.nat.util.PreferenceUtil;
import gdg.nat.util.StorageUtil;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseActivity implements
		IWebServiceReceiverListener {
	private final int NUM_OF_PROGRESS = 2;
	private int progressTask = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_splash);
		requestVersion();
	}

	private void requestVersion() {
		String version = PreferenceUtil.getInstance().getCateVersion();
		CheckCateVersionRequest request = new CheckCateVersionRequest(version);
		restartRequest(request);
	}

	private void requestCategory() {
		GetCategoriesRequest requestParam = new GetCategoriesRequest();
		restartRequest(requestParam);
	}

	private void requestIcon() {
		DownloadIconRequest request = new DownloadIconRequest();
		restartRequest(request);
	}

	private void directToHome() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public void startDownloadIconServices() {
		Intent intent = new Intent(this, DownloadIconService.class);
		DownloadReceiver downloadReceiver = new DownloadReceiver(new Handler());
		downloadReceiver.setListener(new IUpdateDownloadProgress() {
			@Override
			public void onUpdate(int progress) {
				if (progress == 100) {
					progressTask++;
					if (progressTask >= NUM_OF_PROGRESS) {
						directToHome();
					}
				}
			}
		});
		intent.putExtra("receiver", new DownloadReceiver(new Handler()));
		startService(intent);
	}

	@Override
	public void dismisAllDialog() {
	}

	@Override
	public void onRequest(RequestParam requestParam) {
	}

	@Override
	public void onReceiver(RequestParam requestParam,
			ResponseParser responseParser) {
		if (requestParam instanceof CheckCateVersionRequest) {
			int code = responseParser.getCode();
			if (code == ResponseCode.SERVER_OUT_OF_DATE_API
					|| ObjectCache.getInstance().getCategories() == null) {
				requestCategory();
				requestIcon();
			} else if (code == ResponseCode.CLIENT_SUCCESS) {
				directToHome();
			} else {
				Context context = this;
				Builder builder = new Builder(context);
				builder.setTitle(context.getString(R.string.title_error));
				builder.setMessage(context.getString(R.string.error_unknow));
				builder.setPositiveButton(
						context.getString(R.string.error_btn_positive),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								requestCategory();
							}
						});
				// Set error message
				if (code == ResponseCode.CLIENT_ERROR_NO_CONNECTION) {
					builder.setMessage(context
							.getString(R.string.error_no_connection));
				}
				builder.show();
			}
		} else if (requestParam instanceof GetCategoriesRequest) {
			int code = responseParser.getCode();
			if (code != ResponseCode.CLIENT_SUCCESS) {
				Context context = this;
				Builder builder = new Builder(context);
				builder.setTitle(context.getString(R.string.title_error));
				builder.setMessage(context.getString(R.string.error_unknow));
				builder.setPositiveButton(
						context.getString(R.string.error_btn_positive),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								requestCategory();
							}
						});
				// Set error message
				if (code == ResponseCode.CLIENT_ERROR_NO_CONNECTION) {
					builder.setMessage(context
							.getString(R.string.error_no_connection));
				}
				builder.show();
			} else {
				Categories categories = ObjectCache.getInstance()
						.getCategories();
				if (categories == null
						|| categories.getListCategories().size() == 0) {
					requestCategory();
				} else {
					if (responseParser instanceof GetCategoriesResponse) {
						String version = ((GetCategoriesResponse) responseParser)
								.getVersion();
						PreferenceUtil.getInstance().saveCateVersion(version);
					}
					progressTask++;
					if (progressTask >= NUM_OF_PROGRESS) {
						directToHome();
					}
				}
			}
		} else if (requestParam instanceof DownloadIconRequest) {
			int code = responseParser.getCode();
			if (code != ResponseCode.CLIENT_SUCCESS) {
				Context context = this;
				Builder builder = new Builder(context);
				builder.setTitle(context.getString(R.string.title_error));
				builder.setMessage(context.getString(R.string.error_unknow));
				builder.setPositiveButton(
						context.getString(R.string.error_btn_positive), null);
				// Set error message
				if (code == ResponseCode.CLIENT_ERROR_NO_CONNECTION) {
					builder.setMessage(context
							.getString(R.string.error_no_connection));
				}
				builder.show();
			} else {
				if (responseParser instanceof DownloadIconResponse) {
					String zip = ((DownloadIconResponse) responseParser)
							.getZip();
					StorageUtil.saveIconFile(this, "zip",
							StorageUtil.decodeBase64(zip));
				}
				progressTask++;
				if (progressTask >= NUM_OF_PROGRESS) {
					directToHome();
				}
			}
		}
	}
}