package gdg.nat.base;

import gdg.nat.connection.IWebServiceReceiverListener;
import gdg.nat.connection.WebServiceManager;
import gdg.nat.connection.WebServiceReceiver;
import gdg.nat.ksc.R;
import gdg.nat.ksc.config.Config;
import gdg.nat.navigation.NavigationBar;
import gdg.nat.navigation.NavigationManager;
import gdg.nat.util.GdgLog;

import java.util.Random;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout.LayoutParams;

public abstract class BaseActivity extends FragmentActivity {
	private final String TAG = "TrackingBaseActivity";
	private WebServiceManager webServiceManager = WebServiceManager
			.getInstance();
	private WebServiceReceiver webServiceReceiver = new WebServiceReceiver();
	private NavigationBar navibar;
	protected FrameLayout placeholder;
	private NavigationManager navigationManager;

	private boolean isNavigatable = false;

	public abstract void dismisAllDialog();

	protected WebServiceManager getWebServiceManager() {
		return webServiceManager;
	}

	protected WebServiceReceiver getWebServiceReceiver() {
		return webServiceReceiver;
	}

	@Override
	protected void onCreate(Bundle arg0) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onCreate:\n" + "\tBundle: "
					+ arg0 + "\n----------");
		}
		super.onCreate(arg0);
		setContentView(R.layout.ac_base);
		isNavigatable = true;
		setupNavigation(false, false);
		if (this instanceof IWebServiceReceiverListener)
			webServiceReceiver
					.setWebServiceReceiverListener((IWebServiceReceiverListener) this);
	}

	public NavigationBar getNavigationBar() {
		return navibar;
	}

	public NavigationManager getNavigationManager() {
		return navigationManager;
	}

	protected void setupNavigation(boolean isShow, boolean isPlaceHolder) {
		navibar = (NavigationBar) findViewById(R.id.navigation_bar);
		placeholder = (FrameLayout) findViewById(R.id.place_holer);
		if (navibar == null || placeholder == null)
			return;
		if (isShow) {
			navibar.setVisibility(View.VISIBLE);
			navibar.init(this);
			LayoutParams params = ((LayoutParams) placeholder.getLayoutParams());
			if (isPlaceHolder) {
				params.topMargin = getResources().getDimensionPixelSize(
						R.dimen.navi_bar_height);
			} else {
				params.topMargin = 0;
			}
			placeholder.setLayoutParams(params);
		} else {
			navibar.setVisibility(View.GONE);
			LayoutParams params = ((LayoutParams) placeholder.getLayoutParams());
			params.topMargin = 0;
			placeholder.setLayoutParams(params);
		}

		navigationManager = new NavigationManager(this, placeholder.getId());
	}

	private void terminateNavigation() {
		navigationManager.terminate();
	}

	public boolean isNavigatable() {
		return isNavigatable;
	}

	@Override
	protected void onStart() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onStart" + "\n----------");
		}
		super.onStart();
		isNavigatable = true;
	}

	@Override
	protected void onResume() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onResume" + "\n----------");
		}
		super.onResume();
		LocalBroadcastManager.getInstance(this).registerReceiver(
				webServiceReceiver,
				new IntentFilter(WebServiceReceiver.INTENT_WEB_SERVICE));
	}

	@Override
	protected void onPause() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onPause" + "\n----------");
		}
		super.onPause();
		dismisAllDialog();
	}

	@Override
	protected void onStop() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onStop" + "\n----------");
		}
		super.onStop();
		dismisAllDialog();
	}

	@Override
	protected void onDestroy() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onDestroy" + "\n----------");
		}
		super.onDestroy();
		if (navibar != null)
			navibar.terminate();
		terminateNavigation();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				webServiceReceiver);
		dismisAllDialog();
	}

	@Override
	protected void onRestart() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onRestart" + "\n----------");
		}
		super.onRestart();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (Config.isTrackingAppStatus()) {
			Random random = new Random();
			if (random.nextInt() % 2 == 0)
				outState.putString(TAG, TAG);
			Log.i(TAG, "Start tracking before onSaveInstanceState:\n"
					+ "\tBundle: " + outState + "\n----------");
		}
		super.onSaveInstanceState(outState);
		isNavigatable = false;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking after onRestoreInstanceState:\n"
					+ "\tBundle: " + savedInstanceState + "\n----------");
		}
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onActivityResult:\n"
					+ "\tRequest code: " + arg0 + "\n\tResult code: " + arg1
					+ "\n\tIntent: " + arg2 + "\n----------");
		}
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	public void onAttachedToWindow() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onAttacherToWindow"
					+ "\n----------");
		}
		super.onAttachedToWindow();
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onAttachFragment\n\t" + fragment
					+ "\n----------");
		}
		super.onAttachFragment(fragment);
	}

	@Override
	protected void onResumeFragments() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking after onResumeFragment" + "\n----------");
		}
		super.onResumeFragments();
		isNavigatable = true;
	}

	@Override
	public void onBackPressed() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before obBackPressd" + "\n----------");
		}
		if (!navigationManager.goBack()) {
			super.onBackPressed();
		}
	}

	@Override
	public void onDetachedFromWindow() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onDetachedFromWindow"
					+ "\n----------");
		}
		super.onDetachedFromWindow();
	}

	protected void showPage(BaseFragment fragment) {
		if (navigationManager.showPage(fragment)) {
			if (Config.IS_TRACKING_USER) {
				Log.i(TAG, "Navigate to: " + fragment.toString());
			}
		} else {
			GdgLog.e(TAG, "Can't navigate to: " + fragment.toString());
		}
	}

	protected void showPage(BaseFragment fragment, boolean isAnimation) {
		if (navigationManager.showPage(fragment, isAnimation)) {
			if (Config.IS_TRACKING_USER) {
				Log.i(TAG, "Navigate to: " + fragment.toString());
			}
		} else {
			GdgLog.e(TAG, "Can't navigate to: " + fragment.toString());
		}
	}

	protected void goBack() {
		if (navigationManager.goBack()) {
			if (Config.IS_TRACKING_USER) {
				Log.i(TAG, "Go back");
			}
		} else {
			GdgLog.e(TAG, "Can't goback");
		}
	}
}