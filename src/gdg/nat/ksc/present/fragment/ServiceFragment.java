package gdg.nat.ksc.present.fragment;

import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import gdg.nat.ksc.present.adapter.SlidingIndicatorPagerAdapter;
import gdg.nat.util.LocationUtil;
import gdg.nat.view.FragmentPagerIndicatorItem;
import gdg.nat.view.SlidingTabLayout;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ServiceFragment extends BaseFragment implements
		OnItemClickListener {
	private final String TAG = "TrackingServiceFragment";

	private final String INTENT_CATE_NAME = "name";
	private final String INTENT_CATE_ID = "cate_id";

	public final int NEW_SERVICE = 0;

	private String screenName = "";
	private String cateId = "";

	private SlidingTabLayout mSlidingTabLayout;
	private ViewPager mViewPager;
	private SlidingIndicatorPagerAdapter adapter;

	public static ServiceFragment newInstance(String cate_id, String cate_name) {
		ServiceFragment fragment = new ServiceFragment();
		Bundle bundle = new Bundle();
		bundle.putString(fragment.INTENT_CATE_ID, cate_id);
		bundle.putString(fragment.INTENT_CATE_NAME, cate_name);
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
			if (screenName.length() == 0
					&& bundle.containsKey(INTENT_CATE_NAME)) {
				screenName = bundle.getString(INTENT_CATE_NAME);
			}
			if (cateId.length() == 0 && bundle.containsKey(INTENT_CATE_ID)) {
				cateId = bundle.getString(INTENT_CATE_ID);
			}
		}

		if (screenName.length() > 0) {
			getNavigationBar().setTitle(screenName);
		} else {
			getNavigationBar().setTitle(R.string.title_new_service_screen);
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

	private void initData() {
		if (adapter == null)
			return;
		// Initial tab Ha Noi
		String tabHNName = getString(R.string.title_service_tab_ha_noi);
		int tabHNIndicator = Color.BLUE;
		BaseFragment tabHNFragment = ListServiceFragment.newInstance(cateId,
				LocationUtil.CITY_HA_NOI, screenName);
		adapter.addTab(new FragmentPagerIndicatorItem(tabHNName,
				tabHNIndicator, tabHNFragment));

		// Initial tab Ho Chi Minh
		String tabHCMName = getString(R.string.title_service_tab_ho_chi_minh);
		int tabHCMIndicator = Color.BLUE;
		BaseFragment tabHCMFragment = ListServiceFragment.newInstance(cateId,
				LocationUtil.CITY_HO_CHI_MINH, screenName);
		adapter.addTab(new FragmentPagerIndicatorItem(tabHCMName,
				tabHCMIndicator, tabHCMFragment));

		adapter.notifyDataSetChanged();
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