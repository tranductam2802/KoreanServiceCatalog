package gdg.nat.navigation;

import gdg.nat.base.BaseActivity;
import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

public class NavigationManager {
	private BaseActivity activity;
	private int placeholder = 0;
	private FragmentManager fragmentManager;

	public NavigationManager(BaseActivity baseActivity, int placeHolder) {
		this.activity = baseActivity;
		this.placeholder = placeHolder;
		fragmentManager = activity.getSupportFragmentManager();
		fragmentManager
				.addOnBackStackChangedListener(new OnBackStackChangedListener() {
					@Override
					public void onBackStackChanged() {
						activity.getNavigationBar().onPageChanged(
								findActivePageByPlaceHolder());
					}
				});
	}

	public boolean showPage(BaseFragment baseFragment) {
		return showPage(baseFragment, true);
	}

	public boolean showPage(BaseFragment baseFragment, boolean isAnimation) {
		if (!activity.isNavigatable() || baseFragment == null)
			return false;

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if (isAnimation) {
			transaction.setCustomAnimations(R.anim.fragment_enter,
					R.anim.fragment_exit, R.anim.fragment_pop_enter,
					R.anim.fragment_pop_exit);
		}
		transaction.replace(placeholder, baseFragment);
		transaction.addToBackStack(baseFragment.getFragmentTag());
		transaction.commit();
		return true;
	}

	public boolean goBack() {
		if (activity == null || !activity.isNavigatable()
				|| fragmentManager.getBackStackEntryCount() == 0)
			return false;
		fragmentManager.popBackStack();
		return true;
	}

	public void terminate() {
		activity = null;
	}

	public boolean isEmpty() {
		return fragmentManager.getBackStackEntryCount() == 0;
	}

	public BaseFragment findActivePageByPlaceHolder() {
		return (BaseFragment) fragmentManager.findFragmentById(placeholder);
	}
}