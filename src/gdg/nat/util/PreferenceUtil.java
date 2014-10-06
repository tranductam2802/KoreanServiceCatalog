package gdg.nat.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import gdg.nat.ksc.config.KSCApp;

public class PreferenceUtil {
	private static final String TAG = "TrackingPreference";
	private static final String PREFERENCE_NAME = "KSC_Base_data";

	private static PreferenceUtil preference = new PreferenceUtil();

	private PreferenceUtil() {
		// Do nothing
	}

	public static PreferenceUtil getInstance() {
		return preference;
	}

	private SharedPreferences getSharedPreferences() {
		return KSCApp.getInstance().getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	private Editor getEditor() {
		return getSharedPreferences().edit();
	}

	/***** Preference data *****/

	private final String KEY_GCM_ID = "gcm_id";

	public void saveGCMId(String id) {
		GdgLog.i(TAG, "Save " + KEY_GCM_ID + ": " + id);
		getEditor().putString(KEY_GCM_ID, id).commit();
	}

	public String getGCMId() {
		String id = getSharedPreferences().getString(KEY_GCM_ID, "");
		GdgLog.i(TAG, "Get " + KEY_GCM_ID + ": " + id);
		return id;
	}

	public void clearGCMId() {
		GdgLog.i(TAG, "Clear " + KEY_GCM_ID);
		getEditor().remove(KEY_GCM_ID);
	}
}