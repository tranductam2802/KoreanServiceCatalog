package gdg.nat.ksc.present.fragment;

import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import gdg.nat.ksc.present.adapter.ListServiceAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ServiceFragment extends BaseFragment {
	private final String TAG = "TrackingServiceFragment";

	private final String INTENT_CATE_NAME = "name";
	private final String INTENT_CATE_ID = "cate_id";
	private final String INTENT_CITY = "city";

	private ListView mList;

	private ListServiceAdapter adapter;

	public final int NEW_SERVICE = 0;
	public final int CITY_HA_NOI = 1;
	public final int CITY_HO_CHI_MINH = 2;

	private String screenName = "";
	private String cateId = "";
	private int city = CITY_HA_NOI;

	public static ServiceFragment newInstance(String cate_id, String cate_name,
			int city) {
		ServiceFragment fragment = new ServiceFragment();
		if (city != fragment.CITY_HA_NOI && city != fragment.CITY_HO_CHI_MINH)
			throw new IllegalArgumentException("city value(" + city
					+ ") is not Ha Noi or Ho Chi Minh");

		Bundle bundle = new Bundle();
		bundle.putString(fragment.INTENT_CATE_ID, cate_id);
		bundle.putString(fragment.INTENT_CATE_NAME, cate_name);
		bundle.putInt(fragment.INTENT_CITY, city);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fg_service, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(INTENT_CATE_NAME)) {
				screenName = bundle.getString(INTENT_CATE_NAME);
			}
			if (bundle.containsKey(INTENT_CATE_ID)) {
				cateId = bundle.getString(INTENT_CATE_ID);
			}
			if (bundle.containsKey(INTENT_CITY)) {
				city = bundle.getInt(INTENT_CITY);
			}
		}
		mList = (ListView) view.findViewById(R.id.list);

		if (adapter == null) {
			adapter = new ListServiceAdapter(getActivity());
		}

		mList.setAdapter(adapter);
		if (screenName.length() > 0) {
			getNavigationBar().setTitle(screenName);
		} else {
			getNavigationBar().setTitle(R.string.title_new_service_screen);
		}
	}

	@Override
	public String getFragmentTag() {
		return TAG;
	}
}