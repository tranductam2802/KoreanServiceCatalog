package gdg.nat.base;

import gdg.nat.connection.IWebServiceReceiverListener;
import gdg.nat.connection.RequestParam;
import gdg.nat.connection.WebServiceManager;
import gdg.nat.connection.WebServiceReceiver;
import gdg.nat.ksc.config.Config;
import gdg.nat.navigation.NavigationBar;
import gdg.nat.navigation.NavigationManager;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	private final String TAG = "TrackingBaseFragment";
	private WebServiceReceiver webServiceReceiver = new WebServiceReceiver();
	private NavigationManager navigationManager;

	public static final int RESOURCE_EMPTY = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onCreate:\n" + "\tBundle: "
					+ savedInstanceState + "\n----------");
		}
		super.onCreate(savedInstanceState);
		if (this instanceof IWebServiceReceiverListener)
			webServiceReceiver
					.setWebServiceReceiverListener((IWebServiceReceiverListener) this);
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
				webServiceReceiver,
				new IntentFilter(WebServiceReceiver.INTENT_WEB_SERVICE));
	}

	protected WebServiceManager getWebServiceManager() {
		Activity activity = getActivity();
		if (activity instanceof BaseActivity) {
			return ((BaseActivity) activity).getWebServiceManager();
		} else {
			throw new IllegalStateException(
					"getWebServiceManager() do not call from BaseActivity's fragment child");
		}
	}

	public NavigationManager getNavigationManager() {
		return navigationManager;
	}

	public NavigationBar getNavigationBar() {
		return ((BaseActivity) getActivity()).getNavigationBar();
	}

	protected void startRequest(RequestParam requestParam) {
		WebServiceManager manager = getWebServiceManager();
		manager.startRequestServer(requestParam);
	}

	protected void restartRequest(RequestParam requestParam) {
		WebServiceManager manager = getWebServiceManager();
		manager.restartRequestServer(requestParam);
	}

	public abstract String getFragmentTag();

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
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
				webServiceReceiver);
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
		navigationManager = ((BaseActivity) getActivity())
				.getNavigationManager();
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

	public int getTitleResource() {
		return RESOURCE_EMPTY;
	}

	public String getTitle() {
		return "";
	}
}