package gdg.nat.ksc.present.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;

public class DescriptionFragment extends BaseFragment {
	private final String TAG = "TrackingDescriptionFragment";

	private final String INTENT_NAME = "name";
	private final String INTENT_ADDRESS = "address";
	private final String INTENT_PHONE = "phone";
	private final String INTENT_WEBSITE = "website";

	private TextView txtName;
	private TextView txtAddress;
	private TextView txtPhone;
	private TextView txtWebsite;

	public static DescriptionFragment newInstance(String name, String address,
			String phone, String website) {
		DescriptionFragment fragment = new DescriptionFragment();
		Bundle bundle = new Bundle();
		bundle.putString(fragment.INTENT_NAME, name);
		bundle.putString(fragment.INTENT_ADDRESS, address);
		bundle.putString(fragment.INTENT_PHONE, phone);
		bundle.putString(fragment.INTENT_WEBSITE, website);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public String getFragmentTag() {
		return TAG;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fg_description, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		txtName = (TextView) view.findViewById(R.id.name);
		txtAddress = (TextView) view.findViewById(R.id.address);
		txtPhone = (TextView) view.findViewById(R.id.phone);
		txtWebsite = (TextView) view.findViewById(R.id.website);

		Bundle bundle = getArguments();
		if (bundle != null) {
			txtName.setText(String.valueOf(bundle.getString(INTENT_NAME)));
			txtAddress
					.setText(String.valueOf(bundle.getString(INTENT_ADDRESS)));
			txtPhone.setText(String.valueOf(bundle.getString(INTENT_PHONE)));
			txtWebsite
					.setText(String.valueOf(bundle.getString(INTENT_WEBSITE)));
		}
	}
}