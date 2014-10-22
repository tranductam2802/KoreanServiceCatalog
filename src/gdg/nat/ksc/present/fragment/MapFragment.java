package gdg.nat.ksc.present.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
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
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		String name = "";
		String address = "";
		double longitude = 0;
		double latitude = 0;

		Bundle bundle = getArguments();
		if (bundle != null) {
			name = bundle.getString(INTENT_NAME);
			address = bundle.getString(INTENT_ADDRESS);
			longitude = bundle.getDouble(INTENT_LON);
			latitude = bundle.getDouble(INTENT_LAT);
		}

		// Getting Map for the SupportMapFragment
		googleMap = getMap();
		// Enable/Disable MyLocation Button in the Map
		googleMap.setMyLocationEnabled(true);
		// Enable/Disable Zoom Control
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		// viewPoint Ha Noi
		ViewPoint(latitude, longitude, 15, 0, 0);
		// create Maker Ha Noi - Viet Nam
		CreatMaker(latitude, longitude, name, address);
		// create Maker Ha Noi - Viet Nam with custom icon maker
		// unlock src to test funtion
		// CreatMakerWithDrawable(21.02785, 105.852283, "Ha Noi",
		// " Hoan Kiem Ha Noi Viet Nam",R.drawable.ic_launcher);
		// draw line on Map
		// drawLine(21.02785, 105.852283, 21.030704, 105.852369);
	}

	private void drawLine(double lat1, double long1, double lat2, double long2) {
		// draw a line have location (lat1,long1) & (lat2,long2)
		googleMap.addPolyline(new PolylineOptions()
				.add(new LatLng(lat1, long1), new LatLng(lat2, long2)).width(5)
				.color(Color.RED));

	}

	private void CreatMaker(double latitude, double longitude, String mTitle,
			String mSnippet) {
		googleMap.addMarker(new MarkerOptions()
				.position(new LatLng(latitude, longitude))
				.title(mTitle)
				.snippet(mSnippet)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
	}

	// creat maker with icon is image
	private void CreatMakerWithDrawable(double latitude, double longitude,
			String mTitle, String mSnippet, int icon) {
		googleMap.addMarker(new MarkerOptions()
				.position(new LatLng(latitude, longitude)).title(mTitle)
				.snippet(mSnippet)
				.icon(BitmapDescriptorFactory.fromResource(icon)));
	}

	private void ViewPoint(double lattitude, double longitude, float mZoom,
			float mtilt, float mbearing) {

		// Init View Position
		CameraPosition mPosition = new CameraPosition(new LatLng(lattitude,
				longitude), mZoom, mtilt, mbearing);

		// update camera
		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(mPosition));
	}
}