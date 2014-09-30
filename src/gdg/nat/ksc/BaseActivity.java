package gdg.nat.ksc;

import gdg.nat.ksc.util.GdgLog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity {
	private final String TAG = "BaseActivity";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
	}

	@Override
	protected void onPause() {
		GdgLog.i(TAG, "onPause");
		super.onPause();
		dismisAllDialog();
	}

	@Override
	protected void onStop() {
		super.onStop();
		dismisAllDialog();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismisAllDialog();
	}

	public abstract void dismisAllDialog();
}