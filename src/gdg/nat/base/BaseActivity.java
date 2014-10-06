package gdg.nat.base;

import gdg.nat.connection.IWebServiceReceiverListener;
import gdg.nat.connection.WebServiceManager;
import gdg.nat.connection.WebServiceReceiver;
import gdg.nat.ksc.config.Config;

import java.util.Random;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public abstract class BaseActivity extends FragmentActivity {
	private final String TAG = "TrackingBaseActivity";
	private WebServiceManager webServiceManager = WebServiceManager
			.getInstance();
	private WebServiceReceiver webServiceReceiver = new WebServiceReceiver();

	public abstract void dismisAllDialog();

	protected WebServiceManager getWebServiceManager() {
		return webServiceManager;
	}

	@Override
	protected void onCreate(Bundle arg0) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onCreate:\n" + "\tBundle: "
					+ arg0 + "\n----------");
		}
		super.onCreate(arg0);
		if (this instanceof IWebServiceReceiverListener)
			webServiceReceiver
					.setWebServiceReceiverListener((IWebServiceReceiverListener) this);
	}

	@Override
	protected void onStart() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onStart" + "\n----------");
		}
		super.onStart();
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
	}

	@Override
	public void onBackPressed() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before obBackPressd" + "\n----------");
		}
		super.onBackPressed();
	}

	@Override
	public void onDetachedFromWindow() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onDetachedFromWindow"
					+ "\n----------");
		}
		super.onDetachedFromWindow();
	}
}