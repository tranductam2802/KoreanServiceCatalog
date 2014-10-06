package gdg.nat.ksc.config;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.util.Log;

public class KSCApp extends Application {
	private final String TAG = "TrackingApplication";
	private static KSCApp mKSCApp;

	/** Get Application instance when do not have context */
	public static KSCApp getInstance() {
		return mKSCApp;
	}

	@Override
	public void onCreate() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onCreate" + "\n----------");
		}
		super.onCreate();
		mKSCApp = this;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onConfigurationChanged:\n"
					+ "\tConfiguration: " + newConfig + "\n----------");
		}
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onLowMemory" + "\n----------");
		}
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onTerminate" + "\n----------");
		}
		super.onTerminate();
	}

	@Override
	protected void attachBaseContext(Context base) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before attachBaseContext:\n"
					+ "\tContext: " + base + "\n----------");
		}
		super.attachBaseContext(base);
	}

	@Override
	public boolean bindService(Intent service, ServiceConnection conn, int flags) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before bindService:\n" + "\tIntent: "
					+ service + "\n\tServieceCOnnection: " + conn
					+ "\n\tFlags: " + flags + "\n----------");
		}
		return super.bindService(service, conn, flags);
	}
}