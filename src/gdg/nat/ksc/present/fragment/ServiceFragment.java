package gdg.nat.ksc.present.fragment;

import java.util.ArrayList;
import java.util.List;

import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import gdg.nat.ksc.data.Service;
import gdg.nat.ksc.present.adapter.ListServiceAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ServiceFragment extends BaseFragment implements
		OnItemClickListener {
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

			List<Service> list = new ArrayList<Service>();
			list.add(new Service("1", "1-1", "Ha noi chu o dau",
					Service.RATE_MEDIUM, (double) 0, (double) 0, true));
			list.add(new Service("2", "2-0", "Ha noi chu o dau",
					Service.RATE_LOW, (double) 0, (double) 0, true));
			list.add(new Service("3", "3", "Ha noi chu o dau",
					Service.RATE_MEDIUM, (double) 0, (double) 0, true));
			list.add(new Service("4", "4", "Ha noi chu o dau",
					Service.RATE_MEDIUM, (double) 0, (double) 0, true));
			list.add(new Service("5", "5-2", "Ha noi chu o dau",
					Service.RATE_HIGH, (double) 0, (double) 0, true));
			list.add(new Service("6", "6", "Ha noi chu o dau",
					Service.RATE_MEDIUM, (double) 0, (double) 0, true));
			list.add(new Service("7", "7-2", "Ha noi chu o dau",
					Service.RATE_HIGH, (double) 0, (double) 0, true));
			list.add(new Service("8", "8-0", "Ha noi chu o dau",
					Service.RATE_LOW, (double) 0, (double) 0, true));
			list.add(new Service("9", "9-2", "Ha noi chu o dau",
					Service.RATE_HIGH, (double) 0, (double) 0, true));
			list.add(new Service("10", "10", "Ha noi chu o dau",
					Service.RATE_MEDIUM, (double) 0, (double) 0, true));
			list.add(new Service("11", "11", "Ha noi chu o dau",
					Service.RATE_MEDIUM, (double) 0, (double) 0, true));
			list.add(new Service("12", "12-0", "Ha noi chu o dau",
					Service.RATE_LOW, (double) 0, (double) 0, true));
			list.add(new Service("13", "13-0", "Ha noi chu o dau",
					Service.RATE_LOW, (double) 0, (double) 0, true));
			list.add(new Service("14", "14-2", "Ha noi chu o dau",
					Service.RATE_HIGH, (double) 0, (double) 0, true));
			list.add(new Service("15", "15", "Ha noi chu o dau",
					Service.RATE_MEDIUM, (double) 0, (double) 0, true));
			list.add(new Service("16", "16-0", "Ha noi chu o dau",
					Service.RATE_LOW, (double) 0, (double) 0, true));
			list.add(new Service("17", "17-0", "Ha noi chu o dau",
					Service.RATE_LOW, (double) 0, (double) 0, true));
			list.add(new Service("18", "18", "Ha noi chu o dau",
					Service.RATE_MEDIUM, (double) 0, (double) 0, true));
			list.add(new Service("19", "19-2", "Ha noi chu o dau",
					Service.RATE_HIGH, (double) 0, (double) 0, true));
			list.add(new Service("20", "20", "Ha noi chu o dau",
					Service.RATE_MEDIUM, (double) 0, (double) 0, true));

			adapter.setListServices(list);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO: TamTD - Do something
	}
}