package gdg.nat.ksc.present.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;

public class ContactFragment extends BaseFragment {
	private final String TAG = "TrackingContactFragment";

	@Override
	public String getFragmentTag() {
		return TAG;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fg_contact, container, false);
	}
}