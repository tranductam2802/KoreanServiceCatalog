package gdg.nat.ksc.present.fragment;

import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import gdg.nat.ksc.data.Service;
import gdg.nat.ksc.present.adapter.SlidingIndicatorPagerAdapter;
import gdg.nat.navigation.INaviDefaultViewListener;
import gdg.nat.view.FragmentPagerIndicatorItem;
import gdg.nat.view.SlidingTabLayout;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailServiceFragment extends BaseFragment implements
		INaviDefaultViewListener{
	private final String TAG = "TrackingServiceFragment";
	
	private final String INTENT_ID = "id";
	private final String INTENT_NAME = "name";
	private final String INTENT_ADDRESS = "address";
	private final String INTENT_WEBSITE = "website";
	private final String INTENT_PHONE = "phone";
	private final String INTENT_DESCRIPTION = "description";
	private final String INTENT_PROMOTION = "promotion";
	private final String INTENT_RATE = "rate";
	private final String INTENT_LAT = "lat";
	private final String INTENT_LON = "lon";
	private final String INTENT_IS_PROMOTION = "is_promotion";
	
	private Service service;
	
	private SlidingTabLayout mSlidingTabLayout;
	private ViewPager mViewPager;
	private SlidingIndicatorPagerAdapter adapter;
	
	public static DetailServiceFragment newInstance(Service service){
		DetailServiceFragment fragment = new DetailServiceFragment();
		Bundle bundle = new Bundle();
		bundle.putString(fragment.INTENT_ID, service.getId());
		bundle.putString(fragment.INTENT_NAME, service.getName());
		bundle.putString(fragment.INTENT_ADDRESS, service.getAddress());
		bundle.putString(fragment.INTENT_WEBSITE, service.getWebsite());
		bundle.putString(fragment.INTENT_PHONE, service.getPhone());
		bundle.putString(fragment.INTENT_DESCRIPTION, service.getDescription());
		bundle.putString(fragment.INTENT_PROMOTION, service.getPromotion());
		bundle.putInt(fragment.INTENT_RATE, service.getRate());
		bundle.putDouble(fragment.INTENT_LON, service.getLon());
		bundle.putDouble(fragment.INTENT_LAT, service.getLat());
		bundle.putBoolean(fragment.INTENT_IS_PROMOTION, service.isPromotion());
		fragment.setArguments(bundle);
		return fragment;
	}
	
	private void initData(){
		if(adapter == null) return;
		// Initial tab description
		String tabDesName = getString(R.string.title_service_tab_description);
		int tabDesIndicator = Color.BLUE;
		String tabDesLink = service.getDescription();
		BaseFragment tabDesFragment = WebFragment.newInstance(tabDesLink);
		adapter.addTab(new FragmentPagerIndicatorItem(tabDesName,
				tabDesIndicator, tabDesFragment));
		
		// Initial tab promotion
		String tabProName = getString(R.string.title_service_tab_promotion);
		int tabProIndicator = Color.BLUE;
		String tabProLink = service.getPromotion();
		BaseFragment tabProFragment = WebFragment.newInstance(tabProLink);
		adapter.addTab(new FragmentPagerIndicatorItem(tabProName,
				tabProIndicator, tabProFragment));
		
		// Initial tab Map
		String tabMapName = getString(R.string.title_service_tab_map);
		int tabMapIndicator = Color.BLUE;
		String locationName = service.getName();
		String locationDescription = service.getAddress();
		double lat = service.getLat();
		double lon = service.getLon();
		Fragment tabMapFragment = MapFragment.newInstance(locationName,
				locationDescription, lon, lat);
		adapter.addTab(new FragmentPagerIndicatorItem(tabMapName,
				tabMapIndicator, tabMapFragment));
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
		return inflater.inflate(R.layout.fg_detail_service, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState){
		Bundle bundle = getArguments();
		String id = "";
		String name = "";
		String address = "";
		String website = "";
		String phone = "";
		String description = "";
		String promotion = "";
		int rate = Service.RATE_MEDIUM;
		double lon = 0;
		double lat = 0;
		boolean isPromotion = false;
		if(bundle != null){
			if(bundle.containsKey(INTENT_ID)){
				id = bundle.getString(INTENT_ID);
			}
			if(bundle.containsKey(INTENT_NAME)){
				name = bundle.getString(INTENT_NAME);
			}
			if(bundle.containsKey(INTENT_ADDRESS)){
				address = bundle.getString(INTENT_ADDRESS);
			}
			if(bundle.containsKey(INTENT_WEBSITE)){
				website = bundle.getString(INTENT_WEBSITE);
			}
			if(bundle.containsKey(INTENT_PHONE)){
				phone = bundle.getString(INTENT_PHONE);
			}
			if(bundle.containsKey(INTENT_DESCRIPTION)){
				description = bundle.getString(INTENT_DESCRIPTION);
			}
			if(bundle.containsKey(INTENT_PROMOTION)){
				promotion = bundle.getString(INTENT_PROMOTION);
			}
			if(bundle.containsKey(INTENT_RATE)){
				rate = bundle.getInt(INTENT_RATE);
			}
			if(bundle.containsKey(INTENT_LON)){
				lon = bundle.getDouble(INTENT_LON);
			}
			if(bundle.containsKey(INTENT_LAT)){
				lat = bundle.getDouble(INTENT_LAT);
			}
			if(bundle.containsKey(INTENT_IS_PROMOTION)){
				isPromotion = bundle.getBoolean(INTENT_IS_PROMOTION);
			}
		}
		service = new Service(id, name, address, website, phone, description,
				promotion, rate, lon, lat, isPromotion);
		
		// Find view and setup view pager
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		mViewPager.setOffscreenPageLimit(2);
		if(adapter == null){
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
		
		// Hide unused view
		TextView txtDistance = (TextView) view.findViewById(R.id.distance);
		ImageView imgPromotion = (ImageView) view.findViewById(R.id.promotion);
		txtDistance.setVisibility(View.INVISIBLE);
		imgPromotion.setVisibility(View.INVISIBLE);
		
		// Initial view
		TextView txtName = (TextView) view.findViewById(R.id.name);
		txtName.setText(name);
		TextView txtAddress = (TextView) view.findViewById(R.id.address);
		txtAddress.setText(address);
		Button btnSubmit = (Button) view.findViewById(R.id.submit);
		btnSubmit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				final String phone = service.getPhone();
				final String web = service.getWebsite();
				if(phone.length() > 0 && web.length() > 0){
					Dialog dialog = new Dialog(getActivity());
					dialog.setContentView(R.layout.dialog_contact);
					dialog.show();
					TextView txtPhone = (TextView) dialog
							.findViewById(R.id.phone);
					txtPhone.setText(phone);
					txtPhone.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v){
							call(phone);
						}
					});
					
					TextView txtWeb = (TextView) dialog
							.findViewById(R.id.website);
					txtWeb.setText(web);
					txtWeb.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v){
							visit(web);
						}
					});
				}else{
					if(phone.length() > 0){
						call(phone);
					}else if(web.length() > 0){
						visit(web);
					}
				}
			}
		});
	}
	
	private void call(String phoneNum){
		// tel: is default charactor
		String number = "tel:" + phoneNum.trim();
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
		startActivity(callIntent);
	}
	
	private void visit(String webLink){
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse(webLink));
		startActivity(browserIntent);
	}
	
	@Override
	public String getTitle(){
		return service.getName();
	}
	
	@Override
	public String getFragmentTag(){
		return TAG;
	}
	
	@Override
	public void onGoBack(){
		getNavigationManager().goBack();
	}
	
	@Override
	public void onSearch(String keyword){
		if(keyword == null || keyword.length() <= 0) return;
		SearchServiceFragment fragment = SearchServiceFragment.newInstance(
				keyword, CategoriesFragment.DEFAULT_CATEGORIES);
		getNavigationManager().showPage(fragment);
	}
}