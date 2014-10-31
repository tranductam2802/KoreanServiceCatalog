package gdg.nat.view;

import android.support.v4.app.Fragment;

public class FragmentPagerIndicatorItem {

	private final CharSequence mTitle;
	private final int mIndicatorColor;
	private Fragment fragment;

	public FragmentPagerIndicatorItem(CharSequence title, int indicatorColor,
			Fragment fragment) {
		mTitle = title;
		mIndicatorColor = indicatorColor;
		this.fragment = fragment;
	}

	public Fragment createFragment() {
		return fragment;
	}

	public CharSequence getTitle() {
		return mTitle;
	}

	public int getIndicatorColor() {
		return mIndicatorColor;
	}
}