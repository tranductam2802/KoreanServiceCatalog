package gdg.nat.base;

import gdg.nat.ksc.config.Config;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	private final String TAG = "TrackingBaseFragment";

	public abstract void dismisAllDialog();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onCreate:\n" + "\tBundle: "
					+ savedInstanceState + "\n----------");
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onStart" + "\n----------");
		}
		super.onStart();
	}

	@Override
	public void onResume() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onResume" + "\n----------");
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onPause" + "\n----------");
		}
		super.onPause();
	}

	@Override
	public void onStop() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onStop" + "\n----------");
		}
		super.onStop();
	}

	@Override
	public void onDestroy() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onDestroy" + "\n----------");
		}
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onCreateView" + "\n----------");
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onDestroyView" + "\n----------");
		}
		super.onDestroyView();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onActivityCreated"
					+ "\n----------");
		}
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onActivityResult:\n"
					+ "\tRequestCode: " + requestCode + " ResultCode: "
					+ resultCode + " Intent " + data + "\n----------");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onAttach(Activity activity) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onAttach: " + activity
					+ "\n----------");
		}
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onDetach" + "\n----------");
		}
		super.onDetach();
	}

	@Override
	public void onInflate(Activity activity, AttributeSet attrs,
			Bundle savedInstanceState) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onInflate:\n" + "\tActivity: "
					+ activity + "\n\tAttributeSet: " + attrs + "\n\tBundle: "
					+ savedInstanceState + "\n----------");
		}
		super.onInflate(activity, attrs, savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (Config.isTrackingAppStatus()) {
			Random random = new Random();
			if (random.nextInt() % 2 == 0)
				outState.putString(TAG, TAG);
			Log.i(TAG, "Start tracking before onSaveInstanceState:\n"
					+ "\tBundle: " + outState + "\n----------");
		}
		super.onSaveInstanceState(outState);
	}
}