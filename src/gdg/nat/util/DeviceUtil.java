package gdg.nat.util;

import gdg.nat.ksc.config.KSCApp;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class DeviceUtil {
	/** Get device id thought TELEPHONY_SERVICE */
	public static String getDeviceId() {
		TelephonyManager telephonyManager = (TelephonyManager) KSCApp
				.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	/** Detect if the device's platform version is KITKAT or later. */
	public static boolean hasKITKAT() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	}

	/** Detect if the device's platform version is JELLY_BEAN_MR2 or later. */
	public static boolean hasJELLY_BEAN_MR2() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
	}

	/** Detect if the device's platform version is JELLY_BEAN_MR1 or later. */
	public static boolean hasJELLY_BEAN_MR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
	}

	/** Detect if the device's platform version is JELLY_BEAN or later. */
	public static boolean hasJELLY_BEAN() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	/** The device's platform version is ICE_CREAM_SANDWICH_MR1 or later. */
	public static boolean hasICE_CREAM_SANDWICH_MR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
	}

	/** Detect if the device's platform version is ICE_CREAM_SANDWICH or later. */
	public static boolean hasICE_CREAM_SANDWICH() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	/** Detect if the device's platform version is HONEYCOMB_MR2 or later. */
	public static boolean hasHONEYCOMB_MR2() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
	}

	/** Detect if the device's platform version is HONEYCOMB_MR1 or later. */
	public static boolean hasHONEYCOMB_MR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	/** Detect if the device's platform version is HONEYCOMB or later. */
	public static boolean hasHONEYCOMB() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/** Detect if the device's platform version is GINGERBREAD_MR1 or later. */
	public static boolean hasGINGERBREAD_MR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
	}

	/** Detect if the device's platform version is GINGERBREAD or later. */
	public static boolean hasGINGERBREAD() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	/** Detect if the device's platform version is FROYO or later. */
	public static boolean hasFROYO() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}
}