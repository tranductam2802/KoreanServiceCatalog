package gdg.nat.ksc.present.fragment;

import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import gdg.nat.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
	private List<SamplePagerItem> mTabs = new ArrayList<SamplePagerItem>();

	public static DetailServiceFragment newInstance(String id, String name) {
		DetailServiceFragment fragment = new DetailServiceFragment();
		Bundle bundle = new Bundle();
		bundle.putString(fragment.INTENT_ID, id);
		bundle.putString(fragment.INTENT_NAME, name);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			Bundle bundle = getArguments();
			id = bundle.getString(INTENT_ID);
			name = bundle.getString(INTENT_NAME);
		} else {
			id = savedInstanceState.getString(INTENT_ID);
			name = savedInstanceState.getString(INTENT_NAME);
		}
		initData();
	}

	private void initData() {
		String name = "Ha Noi";
		String address = "Demo address of Ha Noi";

		mTabs.add(new SamplePagerItem(
				getString(R.string.title_service_tab_description), Color.BLUE,
				Color.GRAY, WebFragment.newInstance("http://google.com")));

		mTabs.add(new SamplePagerItem(
				getString(R.string.title_service_tab_promotion), Color.RED,
				Color.GRAY, WebFragment.newInstance("http://facebook.com")));

		mTabs.add(new SamplePagerItem(
				getString(R.string.title_service_tab_map), Color.YELLOW,
				Color.GRAY, MapFragment.newInstance(name, address, 105.852283,
						21.02785)));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString(INTENT_ID, id);
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fg_detail_service, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		mViewPager.setAdapter(new SampleFragmentPagerAdapter(
				getChildFragmentManager()));
		mSlidingTabLayout = (SlidingTabLayout) view
				.findViewById(R.id.sliding_tabs);
		mSlidingTabLayout.setViewPager(mViewPager);

		mSlidingTabLayout
				.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

					@Override
					public int getIndicatorColor(int position) {
						return mTabs.get(position).getIndicatorColor();
					}

					@Override
					public int getDividerColor(int position) {
						return mTabs.get(position).getDividerColor();
					}

				});
	}

	@Override
	public String getTitle() {
		return name;
	}

	@Override
	public String getFragmentTag() {
		return TAG;
	}

	static class SamplePagerItem {
		private final CharSequence mTitle;
		private final int mIndicatorColor;
		private final int mDividerColor;
		private Fragment fragment;

		SamplePagerItem(CharSequence title, int indicatorColor,
				int dividerColor, Fragment fragment) {
			mTitle = title;
			mIndicatorColor = indicatorColor;
			mDividerColor = dividerColor;
			this.fragment = fragment;
		}

		Fragment createFragment() {
			return fragment;
		}

		CharSequence getTitle() {
			return mTitle;
		}

		int getIndicatorColor() {
			return mIndicatorColor;
		}

		int getDividerColor() {
			return mDividerColor;
		}
	}

	class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

		SampleFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			return mTabs.get(i).createFragment();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mTabs.get(position).getTitle();
		}
	}
}