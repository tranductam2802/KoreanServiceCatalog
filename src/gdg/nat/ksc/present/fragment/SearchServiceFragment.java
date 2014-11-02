package gdg.nat.ksc.present.fragment;

import gdg.nat.base.BaseFragment;
import gdg.nat.connection.IWebServiceReceiverListener;
import gdg.nat.connection.RequestParam;
import gdg.nat.connection.ResponseCode;
import gdg.nat.connection.ResponseParser;
import gdg.nat.ksc.R;
import gdg.nat.ksc.connection.request.SearchRequest;
import gdg.nat.ksc.connection.response.ListServiceResponse;
import gdg.nat.ksc.data.Categories;
import gdg.nat.ksc.data.Service;
import gdg.nat.ksc.present.activity.MainActivity;
import gdg.nat.ksc.present.adapter.ListServiceAdapter;
import gdg.nat.navigation.INaviDefaultViewListener;
import gdg.nat.util.ObjectCache;

import java.util.List;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchServiceFragment extends BaseFragment implements
		INaviDefaultViewListener, IWebServiceReceiverListener{
	private final String TAG = "TrackingSearch";
	
	private final String INTENT_KEYWORD = "keyword";
	private final String INTENT_CATE_ID = "cate_id";
	
	private String keyword = "";
	private String cateID = "";
	
	private ListServiceAdapter adapter;
	private ListView listView;
	
	public static SearchServiceFragment newInstance(String keyword,
			String cateId){
		SearchServiceFragment fragment = new SearchServiceFragment();
		Bundle bundle = new Bundle();
		bundle.putString(fragment.INTENT_KEYWORD, keyword);
		bundle.putString(fragment.INTENT_CATE_ID, cateId);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState){
		outState.putString(INTENT_KEYWORD, keyword);
		outState.putString(INTENT_CATE_ID, cateID);
		super.onSaveInstanceState(outState);
	}
	
	private void setKeyword(String keyword){
		this.keyword = keyword;
		getNavigationBar().setTitle(keyword);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		if(keyword.length() > 0){
			getNavigationBar().setTitle(keyword);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
		return inflater.inflate(R.layout.fg_list_service, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		Bundle bundle = getArguments();
		
		if(savedInstanceState != null){
			if(keyword.length() == 0
					&& savedInstanceState.containsKey(INTENT_KEYWORD)){
				setKeyword(savedInstanceState.getString(INTENT_KEYWORD));
			}
			if(cateID.length() == 0
					&& savedInstanceState.containsKey(INTENT_CATE_ID)){
				cateID = savedInstanceState.getString(INTENT_CATE_ID);
			}
		}else if(bundle != null){
			if(keyword.length() == 0 && bundle.containsKey(INTENT_KEYWORD)){
				setKeyword(bundle.getString(INTENT_KEYWORD));
			}
			if(cateID.length() == 0 && bundle.containsKey(INTENT_CATE_ID)){
				cateID = bundle.getString(INTENT_CATE_ID);
			}
		}
		
		listView = (ListView) view.findViewById(R.id.list);
		if(adapter == null){
			adapter = new ListServiceAdapter(getActivity());
		}
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id){
				Service service = adapter.getItem(position);
				DetailServiceFragment fragment = DetailServiceFragment
						.newInstance(service);
				getNavigationManager().showPage(fragment);
			}
		});
		listView.setEmptyView(view.findViewById(R.id.loading));
		
		initData();
	}
	
	private void initData(){
		requestSearch(keyword, cateID);
	}
	
	private void requestSearch(String keyword, String cateId){
		getNavigationBar().setTitle(keyword);
		SearchRequest request = new SearchRequest(keyword, cateId);
		restartRequest(request);
	}
	
	@Override
	public String getFragmentTag(){
		return TAG;
	}
	
	@Override
	public String getTitle(){
		Categories categories = ObjectCache.getInstance().getCategories();
		if(categories.getCategories(cateID) != null){ return categories
				.getCategories(cateID).getName(); }
		return super.getTitle();
	}
	
	@Override
	public void onGoBack(){
		getNavigationManager().goBack();
	}
	
	@Override
	public void onSearch(String keyword){
		if(keyword == null || keyword.length() <= 0) return;
		if(this.keyword.equals(keyword)) return;
		this.keyword = keyword;
		requestSearch(keyword, cateID);
		adapter.clear();
	}
	
	@Override
	public void onRequest(RequestParam requestParam){
		View view = listView.getEmptyView();
		view.findViewById(R.id.progress).setVisibility(View.VISIBLE);
		view.findViewById(R.id.empty).setVisibility(View.GONE);
	}
	
	@Override
	public void onReceiver(RequestParam requestParam,
			ResponseParser responseParser){
		View view = listView.getEmptyView();
		view.findViewById(R.id.progress).setVisibility(View.GONE);
		view.findViewById(R.id.empty).setVisibility(View.VISIBLE);
		if(requestParam instanceof SearchRequest){
			int code = responseParser.getCode();
			if(code == ResponseCode.SERVER_SUCCESS){
				if(responseParser instanceof ListServiceResponse){
					ListServiceResponse response = (ListServiceResponse) responseParser;
					List<Service> list = response.getListServices();
					adapter.setListServices(list);
					if(getActivity() instanceof MainActivity){
						Location location = ((MainActivity) getActivity())
								.getMyLocation();
						if(location != null){
							adapter.calculateDistance(location.getLatitude(),
									location.getLongitude());
						}
					}
				}
			}
		}
	}
}