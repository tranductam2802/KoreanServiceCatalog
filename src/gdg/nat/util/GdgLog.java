package gdg.nat.util;

import gdg.nat.ksc.config.Config;
import android.util.Log;

public class GdgLog {
	private static final String TAG = "Google Developer Group";

	public static void d(String msg) {
		if (Config.IS_DEBUG)
			Log.d(TAG, msg);
	}

	public static void d(String tag, String msg) {
		if (Config.IS_DEBUG)
			Log.d(tag, msg);
	}

	public static void d(String tag, String msg, Throwable throwable) {
		if (Config.IS_DEBUG)
			Log.d(tag, msg, throwable);
	}

	public static void e(String msg) {
		if (Config.IS_DEBUG)
			Log.e(TAG, msg);
	}

	public static void e(String tag, String msg) {
		if (Config.IS_DEBUG)
			Log.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable throwable) {
		if (Config.IS_DEBUG)
			Log.e(tag, msg, throwable);
	}

	public static void i(String msg) {
		if (Config.IS_DEBUG)
			Log.i(TAG, msg);
	}

	public static void i(String tag, String msg) {
		if (Config.IS_DEBUG)
			Log.i(tag, msg);
	}

	public static void i(String tag, String msg, Throwable throwable) {
		if (Config.IS_DEBUG)
			Log.i(tag, msg, throwable);
	}

	public static void v(String msg) {
		if (Config.IS_DEBUG)
			Log.v(TAG, msg);
	}

	public static void v(String tag, String msg) {
		if (Config.IS_DEBUG)
			Log.v(tag, msg);
	}

	public static void v(String tag, String msg, Throwable throwable) {
		if (Config.IS_DEBUG)
			Log.v(tag, msg, throwable);
	}

	public static void w(String msg) {
		if (Config.IS_DEBUG)
			Log.w(TAG, msg);
	}

	public static void w(String tag, String msg) {
		if (Config.IS_DEBUG)
			Log.w(tag, msg);
	}

	public static void w(String tag, String msg, Throwable throwable) {
		if (Config.IS_DEBUG)
			Log.w(tag, msg, throwable);
	}

	public static void wtf(String msg) {
		if (Config.IS_DEBUG)
			Log.wtf(TAG, msg);
	}

	public static void wtf(String tag, String msg) {
		if (Config.IS_DEBUG)
			Log.wtf(tag, msg);
	}

	public static void wtf(String tag, String msg, Throwable throwable) {
		if (Config.IS_DEBUG)
			Log.wtf(tag, msg, throwable);
	}
}