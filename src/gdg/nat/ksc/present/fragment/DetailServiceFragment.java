package gdg.nat.ksc.present.fragment;

import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import gdg.nat.ksc.present.adapter.SlidingIndicatorPagerAdapter;
import gdg.nat.view.FragmentPagerIndicatorItem;
import gdg.nat.view.SlidingTabLayout;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailServiceFragment extends BaseFragment {
	private final String TAG = "TrackingServiceFragment";

	private final String INTENT_ID = "id";
	private final String INTENT_NAME = "name";

	private String id = "";
	private String name = "";

	private SlidingTabLayout mSlidingTabLayout;
	private ViewPager mViewPager;
	private SlidingIndicatorPagerAdapter adapter;

	public static DetailServiceFragment newInstance(String id, String name) {
		DetailServiceFragment fragment = new DetailServiceFragment();
		Bundle bundle = new Bundle();
		bundle.putString(fragment.INTENT_ID, id);
		bundle.putString(fragment.INTENT_NAME, name);
		fragment.setArguments(bundle);
		return fragment;
	}

	private void initData() {
		if (adapter == null)
			return;
		// Initial tab description
		String tabDesName = getString(R.string.title_service_tab_description);
		int tabDesIndicator = Color.BLUE;
		String tabDesLink = "http://google.com";
		BaseFragment tabDesFragment = WebFragment.newInstance(tabDesLink);
		adapter.addTab(new FragmentPagerIndicatorItem(tabDesName,
				tabDesIndicator, tabDesFragment));

		// Initial tab promotion
		String tabProName = getString(R.string.title_service_tab_promotion);
		int tabProIndicator = Color.BLUE;
		String tabProLink = "http://facebook.com";
		BaseFragment tabProFragment = WebFragment.newInstance(tabProLink);
		adapter.addTab(new FragmentPagerIndicatorItem(tabProName,
				tabProIndicator, tabProFragment));

		// Initial tab Map
		String tabMapName = getString(R.string.title_service_tab_map);
		int tabMapIndicator = Color.BLUE;
		String locationName = "Ha Noi";
		String locationDescription = "Demo address of Ha Noi";
		double lat = 21.02785;
		double lon = 105.852283;
		Fragment tabMapFragment = MapFragment.newInstance(locationName,
				locationDescription, lat, lon);
		adapter.addTab(new FragmentPagerIndicatorItem(tabMapName,
				tabMapIndicator, tabMapFragment));
		adapter.notifyDataSetChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fg_detail_service, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(INTENT_ID)) {
				id = bundle.getString(INTENT_ID);
			}
			if (bundle.containsKey(INTENT_NAME)) {
				name = bundle.getString(INTENT_NAME);
			}
		}

		// Find view and setup view pager
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		if (adapter == null) {
			adapter = new SlidingIndicatorPagerAdapter(
					getChildFragmentManager());
			initData();
		}
		mViewPager.setAdapter(adapter);

		// Find view and setup tab layout
		mSlidingTabLayout = (SlidingTabLayout) view
				.findViewById(R.id.sliding_tabs);
		mSlidingTabLayout.setViewPager(mViewPager);
		mSlidingTabLayout.setCustomTabColorizer(adapter.getTabColorizer());
	}

	@Override
	public String getTitle() {
		return name;
	}

	@Override
	public String getFragmentTag() {
		return TAG;
	}
}