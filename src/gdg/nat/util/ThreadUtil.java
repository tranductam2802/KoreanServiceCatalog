package gdg.nat.util;

import gdg.nat.ksc.config.Config;
import android.os.Looper;

public class ThreadUtil {
	public static boolean ensureOnMainThread() {
		if (Looper.myLooper() != Looper.getMainLooper())
			if (Config.IS_DEBUG) {
				throw new IllegalStateException(
						"This method must be called from the UI thread.");
			} else {
				return false;
			}
		else
			return true;
	}
}