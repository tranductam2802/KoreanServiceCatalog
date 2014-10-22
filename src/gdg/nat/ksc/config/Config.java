package gdg.nat.ksc.config;

import android.location.LocationManager;

enum SERVER_TYPE {
	LOCAL, DEMO, KOREAN
}

public class Config {
	/**
	 * Debug flag control debugging status of this application. If debugging is
	 * true, it allow application sending debug log, debug exception or debug
	 * logic.
	 */
	public static final boolean IS_DEBUG = true;

	/** Tracking application status or not */
	private static final boolean IS_TRACKING_APP = true;

	/** Tracking user activity or not */
	public static final boolean IS_TRACKING_USER = true;

	/** Type of the server */
	private static final SERVER_TYPE SERVER = SERVER_TYPE.DEMO;
	public static final String SERVER_API = "/ksc/api/";

	/** Default provider for location service */
	public static final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

	/** Request read time-out */
	public static final int TIMEOUT_READ = 15 * 1000;

	/** Request connection time-out */
	public static final int TIMEOUT_CONNECT = 15 * 1000;

	/** Delay time using to update the new location */
	public static final long DELAY_UPDATE_LOCATION = 5 * 1000;

	/** Minimum distance to update new location. Unit meter */
	public static final int MIN_DISTANCE = 10;

	/** Only tracking application status when debugging application */
	public static final boolean isTrackingAppStatus() {
		if (IS_DEBUG)
			return IS_TRACKING_APP;
		else
			return IS_DEBUG;
	}

	/* Setup server for this application. This logic are going to */
	private static String SERVER_URL = "";
	private static String SERVER_PORT = "";
	private static String IMAGE_SERVER_URL = "";
	private static String IMAGE_SERVER_PORT = "";

	private static final String HTTP_PROTOCOL = "http://";
	private static final String HTTPS_PROTOCOL = "https://";

	static {
		switch (SERVER) {
			case LOCAL:
				SERVER_URL = "202.221.140.194";
				SERVER_PORT = "9119";
				IMAGE_SERVER_URL = SERVER_URL;
				IMAGE_SERVER_PORT = "9117";
				break;
			case DEMO:
				SERVER_URL = "108.61.250.246";
				SERVER_PORT = "80";
				IMAGE_SERVER_URL = SERVER_URL;
				IMAGE_SERVER_PORT = "9117";
				break;
			case KOREAN:
				SERVER_URL = "";
				SERVER_PORT = "9119";
				IMAGE_SERVER_URL = "";
				IMAGE_SERVER_PORT = "9117";
				break;
		}
	}

	/** Return main server URL */
	public static final String getMainServerURL() {
		StringBuilder builder = new StringBuilder();
		builder.append(HTTP_PROTOCOL).append(SERVER_URL).append(":")
				.append(SERVER_PORT);
		return builder.toString();
	}

	/** Return main server in SSL protocol */
	public static final String getMainServerURLSecured() {
		StringBuilder builder = new StringBuilder();
		builder.append(HTTPS_PROTOCOL).append(SERVER_URL).append(":")
				.append(SERVER_PORT);
		return builder.toString();
	}

	/** Return image server URL */
	public static final String getImgServerURL() {
		StringBuilder builder = new StringBuilder();
		builder.append(HTTP_PROTOCOL).append(IMAGE_SERVER_URL).append(":")
				.append(IMAGE_SERVER_PORT);
		return builder.toString();
	}
}