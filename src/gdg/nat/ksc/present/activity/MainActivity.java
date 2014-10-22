package gdg.nat.ksc.present.activity;

import gdg.nat.base.BaseActivity;
import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import gdg.nat.ksc.present.fragment.CategoriesFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setupNavigation(true, true);
		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager.getBackStackEntryCount() == 0) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.setCustomAnimations(0, R.anim.fragment_exit,
					R.anim.fragment_pop_enter, 0);
			CategoriesFragment fragment = CategoriesFragment.newInstance();
			transaction.add(placeholder.getId(), fragment);
			getNavigationBar().onPageChanged(fragment);
			transaction.commit();
		} else {
			Fragment fragment = fragmentManager.findFragmentById(placeholder
					.getId());
			if (fragment instanceof BaseFragment) {
				getNavigationBar().onPageChanged((BaseFragment) fragment);
			}
		}
	}

	@Override
	public void dismisAllDialog() {
	}
}