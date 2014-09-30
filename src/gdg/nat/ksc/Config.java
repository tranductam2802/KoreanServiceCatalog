package gdg.nat.ksc;

enum SERVER_TYPE{
	LOCAL
}

public class Config {
	/**
	 * Debug flag control debugging status of this application. If debugging is
	 * true, it allow application sending debug log, debug exception or debug
	 * logic.
	 */
	public static final boolean IS_DEBUG = true;

	/** Tracking user activity or not */
	public static final boolean IS_TRACKING_USER = false;

	/** Tracking application status or not */
	private static final boolean IS_TRACKING_APP = true;
	
	private static final SERVER_TYPE SERVER = SERVER_TYPE.LOCAL;

	/** Only tracking application status when debugging application */
	public static final boolean isTrackingAppStatus() {
		if (IS_DEBUG)
			return IS_TRACKING_APP;
		else
			return IS_DEBUG;
	}
	
	// Setup server
	
	
	
	static {
		
	}
}