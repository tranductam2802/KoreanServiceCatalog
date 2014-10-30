package gdg.nat.util;

import gdg.nat.ksc.config.KSCApp;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

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

	private final String KEY_CATEGORY_VERSION = "category_version";

	public void saveCateVersion(String cateVersion) {
		GdgLog.i(TAG, "Save " + KEY_CATEGORY_VERSION + ": " + cateVersion);
		getEditor().putString(KEY_CATEGORY_VERSION, cateVersion).commit();
	}

	public String getCateVersion() {
		String cateVersion = getSharedPreferences().getString(
				KEY_CATEGORY_VERSION, "");
		GdgLog.i(TAG, "Get " + KEY_CATEGORY_VERSION + ": " + cateVersion);
		return cateVersion;
	}

	public void clearCateVersion() {
		GdgLog.i(TAG, "Clear " + KEY_CATEGORY_VERSION);
		getEditor().remove(KEY_CATEGORY_VERSION);
	}

	private final String KEY_CATEGORIES = "categories";

	public void saveCategories(String listCategories) {
		GdgLog.i(TAG, "Save " + KEY_CATEGORIES + ": " + listCategories);
		getEditor().putString(KEY_CATEGORIES, listCategories);
	}

	public String getCategories() {
		String listCategories = getSharedPreferences().getString(
				KEY_CATEGORIES, "");
		GdgLog.i(TAG, "Get " + KEY_CATEGORIES + ": " + listCategories);
		return listCategories;
	}

	public void clearCategories() {
		GdgLog.i(TAG, "Clear " + KEY_CATEGORIES);
		getEditor().remove(KEY_CATEGORIES);
	}
}