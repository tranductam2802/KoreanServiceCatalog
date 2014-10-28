package gdg.nat.ksc.present.activity;

import gdg.nat.base.BaseActivity;
import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import gdg.nat.ksc.config.Config;
import gdg.nat.ksc.present.fragment.CategoriesFragment;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends BaseActivity implements LocationListener {
	private LocationManager locationManager;
	private Location currentLocation;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setupNavigation(true, true);

		// Get location service
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				Config.DELAY_UPDATE_LOCATION, Config.MIN_DISTANCE, this);
		this.currentLocation = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		// Update first fragment view
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

	public Location getMyLocation() {
		return this.currentLocation;
	}

	@Override
	public void dismisAllDialog() {
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location == null)
			return;
		this.currentLocation = location;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}
}