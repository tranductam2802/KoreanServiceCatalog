package gdg.nat.util;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;

public class LocationUtil {
	private static final String TAG = "TrackingLocationUtil";

	public static boolean isServicesConnected(Context context) {
		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(context);
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			GdgLog.d(TAG, "Google Play services is available.");
			return true;
		} else {
			GdgLog.e(TAG, "Google Play services is not available.");
			return false;
		}
	}

	public static Location getLastLocation(Context context) {
		LocationClient client = new LocationClient(context, null, null);
		return client.getLastLocation();
	}
}