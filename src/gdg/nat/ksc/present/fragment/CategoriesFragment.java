package gdg.nat.ksc.present.fragment;

import gdg.nat.base.BaseFragment;
import gdg.nat.connection.IWebServiceReceiverListener;
import gdg.nat.connection.RequestParam;
import gdg.nat.connection.ResponseParser;
import gdg.nat.ksc.R;
import gdg.nat.ksc.config.Config;
import gdg.nat.ksc.data.Categories;
import gdg.nat.ksc.data.Category;
import gdg.nat.ksc.data.Service;
import gdg.nat.ksc.present.adapter.HomeAdapter;
import gdg.nat.navigation.INaviDefaultViewListener;
import gdg.nat.util.ObjectCache;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;

public class CategoriesFragment extends BaseFragment implements
		INaviDefaultViewListener, LocationListener, IWebServiceReceiverListener {
	private final String TAG = "TrackingCategoriesFragment";

	private HomeAdapter adapter;
	private ListView listView;
	private FrameLayout frameAds;

	public static CategoriesFragment newInstance() {
		CategoriesFragment fragment = new CategoriesFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_list, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView = (ListView) view.findViewById(R.id.list);
		frameAds = (FrameLayout) view.findViewById(R.id.ads);

		if (adapter == null) {
			adapter = new HomeAdapter(getActivity());
			loadListCategories();

			List<Service> list = new ArrayList<Service>();
			list.add(new Service(
					"",
					"Minh Ngoc Nguyen",
					"",
					"Quan an ngon o dau do cu bia ra nhin cho no dai dai de con test duoc cai ten dai chu neu khong thi cai ten dai kho ma test cho duoc",
					"", 2));
			list.add(new Service(
					"",
					"Minh Ngoc Nguyen",
					"",
					"Quan an ngon o dau do cu bia ra nhin cho no dai dai de con test duoc cai ten dai chu neu khong thi cai ten dai kho ma test cho duoc",
					"", 1));
			list.add(new Service(
					"",
					"Minh Ngoc Nguyen",
					"",
					"Quan an ngon o dau do cu bia ra nhin cho no dai dai de con test duoc cai ten dai chu neu khong thi cai ten dai kho ma test cho duoc",
					"", 0));

			adapter.setListServices(list);
		}
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					return;
				}
				Service service = adapter.getItem(position);
				String serviceId = service.getId();
				String name = service.getName();
				DetailServiceFragment fragment = DetailServiceFragment.newInstance(
						serviceId, name);
				getNavigationManager().showPage(fragment);
			}
		});
		frameAds.setVisibility(View.GONE);
		updateLocation();
	}

	private void updateLocation() {
		LocationManager lm = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		Location location = lm.getLastKnownLocation(Config.LOCATION_PROVIDER);
		if (location != null) {
			double longitude = location.getLongitude();
			double latitude = location.getLatitude();
			adapter.calculateDistance(latitude, longitude);
		}

		lm.requestLocationUpdates(Config.LOCATION_PROVIDER,
				Config.DELAY_UPDATE_LOCATION, Config.MIN_DISTANCE, this);
	}

	private void loadListCategories() {
		Categories categories = ObjectCache.getInstance().getCategories();
		List<Category> list = categories.getListCategories();
		adapter.setListCategories(list);
	}

	@Override
	public String getFragmentTag() {
		return TAG;
	}

	@Override
	public void onGoBack() {
		getNavigationManager().goBack();
	}

	@Override
	public void onSearch(String keyword) {
		if (keyword == null || keyword.length() <= 0)
			return;
		ListServiceFragment fragment = ListServiceFragment.newInstance(keyword,
				"");
		getNavigationManager().showPage(fragment);
	}

	@Override
	public int getTitleResource() {
		return R.string.app_title;
	}

	@Override
	public void onRequest(RequestParam requestParam) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onReceiver(RequestParam requestParam,
			ResponseParser responseParser) {
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null && adapter != null) {
			double longitude = location.getLongitude();
			double latitude = location.getLatitude();
			adapter.calculateDistance(latitude, longitude);
		}
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