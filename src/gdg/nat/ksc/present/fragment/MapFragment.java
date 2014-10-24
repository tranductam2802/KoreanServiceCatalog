package gdg.nat.ksc.present.fragment;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapFragment extends SupportMapFragment {
	private final String INTENT_NAME = "name";
	private final String INTENT_ADDRESS = "address";
	private final String INTENT_LON = "lon";
	private final String INTENT_LAT = "lat";

	private final float MIN_DISTANCE = 50 * 1000; // 50km

	private GoogleMap googleMap;

	public static MapFragment newInstance(String name, String address,
			double longitude, double latitude) {
		MapFragment fragment = new MapFragment();
		Bundle bundle = new Bundle();
		bundle.putString(fragment.INTENT_NAME, name);
		bundle.putString(fragment.INTENT_ADDRESS, address);
		bundle.putDouble(fragment.INTENT_LON, longitude);
		bundle.putDouble(fragment.INTENT_LAT, latitude);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		String name = "";
		String address = "";
		double lon = 0;
		double lat = 0;

		Bundle bundle = getArguments();
		if (bundle != null) {
			name = bundle.getString(INTENT_NAME);
			address = bundle.getString(INTENT_ADDRESS);
			lon = bundle.getDouble(INTENT_LON);
			lat = bundle.getDouble(INTENT_LAT);
		}
		// Initial Google map view
		initGoogleMap(true, true, false);

		makeViewPoint(lat, lon, 15, 0, 0);
		creatMaker(lat, lon, name, address);

		drawDefaultWayLineFromMe(lat, lon);
	}

	private void initGoogleMap(boolean isZoom, boolean isCompass,
			boolean isIndoor) {
		if (googleMap == null) {
			googleMap = getMap();
		}

		// Enable/Disable MyLocation button in the map
		googleMap.setMyLocationEnabled(true);

		// Get UI setting to setting the UI
		UiSettings settings = googleMap.getUiSettings();

		settings.setZoomControlsEnabled(isZoom);
		settings.setCompassEnabled(isCompass);
		settings.setIndoorLevelPickerEnabled(isIndoor);
	}

	private void drawDefaultWayLineFromMe(double lat, double lon) {
		drawWayLineFromMe(lat, lon, 5, Color.RED);
	}

	/** Draw a line help direct the way from my location to destination */
	private void drawWayLineFromMe(double lat, double lon, int width, int color) {
		Location currentLocation = googleMap.getMyLocation();
		double currentLat = currentLocation.getLatitude();
		double currentLon = currentLocation.getLongitude();
		Location destLocation = new Location("");
		destLocation.setLongitude(lon);
		destLocation.setLatitude(lat);
		float distance = currentLocation.distanceTo(destLocation);
		if (distance < MIN_DISTANCE) {
			drawWayLine(currentLat, currentLon, lat, lon, width, color);
		}
	}

	protected void drawDefaultWayLine(double lat1, double lon1, double lat2,
			double lon2) {
		drawWayLine(lat1, lon1, lat2, lon2, 5, Color.RED);
	}

	/** Draw a line help direct the way from source to destination */
	private void drawWayLine(double lat1, double lon1, double lat2,
			double lon2, int width, int color) {
		LatLng coorSource = new LatLng(lat1, lon1);
		LatLng coorDest = new LatLng(lat2, lon2);
		PolylineOptions options = new PolylineOptions()
				.add(coorSource, coorDest).width(width).color(color);
		googleMap.addPolyline(options);
	}

	/** Create a maker in map with default icon */
	private void creatMaker(double lat, double lon, String title, String snippet) {
		LatLng coordinate = new LatLng(lat, lon);
		BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED);
		MarkerOptions options = new MarkerOptions().position(coordinate)
				.title(title).snippet(snippet).icon(bitmapDescriptor);
		googleMap.addMarker(options);
	}

	/** Create a maker in map with custom icon */
	protected void creatMakerWithDrawable(double lat, double lon, String title,
			String snippet, int icon) {
		LatLng coordinate = new LatLng(lat, lon);
		BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
				.fromResource(icon);
		MarkerOptions options = new MarkerOptions().position(coordinate)
				.title(title).snippet(snippet).icon(bitmapDescriptor);
		googleMap.addMarker(options);
	}

	/** Create an camera view point */
	private void makeViewPoint(double lat, double lon, float zoom, float tilt,
			float bearing) {
		CameraPosition position = new CameraPosition(new LatLng(lat, lon),
				zoom, tilt, bearing);
		CameraUpdate cameraUpdate = CameraUpdateFactory
				.newCameraPosition(position);
		googleMap.animateCamera(cameraUpdate);
	}
}