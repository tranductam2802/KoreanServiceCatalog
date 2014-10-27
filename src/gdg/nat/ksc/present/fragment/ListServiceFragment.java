package gdg.nat.ksc.present.fragment;

import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import gdg.nat.ksc.data.Categories;
import gdg.nat.ksc.data.Service;
import gdg.nat.ksc.present.adapter.ListServiceAdapter;
import gdg.nat.navigation.INaviDefaultViewListener;
import gdg.nat.util.ObjectCache;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListServiceFragment extends BaseFragment implements
		INaviDefaultViewListener{
	private final String TAG = "TrackingSearch";
	
	private final String INTENT_KEYWORD = "keyword";
	private final String INTENT_CATE_ID = "cate_id";
	
	private String keyword = "";
	private String cateID = "";
	
	private ListServiceAdapter adapter;
	private ListView listView;
	
	public static ListServiceFragment newInstance(String keyword, String cateId){
		ListServiceFragment fragment = new ListServiceFragment();
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
		}else{
			
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
		return inflater.inflate(R.layout.fg_service, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		if(savedInstanceState == null){
			Bundle bundle = getArguments();
			setKeyword(bundle.getString(INTENT_KEYWORD));
			cateID = bundle.getString(INTENT_CATE_ID);
		}else{
			setKeyword(savedInstanceState.getString(INTENT_KEYWORD));
			cateID = savedInstanceState.getString(INTENT_CATE_ID);
		}
		
		Bundle bundle = getArguments();
		
		if(bundle != null){
			setKeyword(bundle.getString(INTENT_KEYWORD));
			cateID = bundle.getString(INTENT_CATE_ID);
			listView = (ListView) view.findViewById(R.id.list);
			if(adapter == null){
				adapter = new ListServiceAdapter(getActivity());
				
				List<Service> list = new ArrayList<Service>();
				list.add(new Service("1", "1-1", "Ha noi chu o dau",
						Service.RATE_MEDIUM, (double) 0, (double) 0, true));
				list.add(new Service("2", "2-0", "Ha noi chu o dau",
						Service.RATE_LOW, (double) 0, (double) 0, true));
				list.add(new Service("3", "3", "Ha noi chu o dau",
						Service.RATE_MEDIUM, (double) 0, (double) 0, true));
				list.add(new Service("4", "4", "Ha noi chu o dau",
						Service.RATE_MEDIUM, (double) 0, (double) 0, true));
				list.add(new Service("5", "5-2", "Ha noi chu o dau",
						Service.RATE_HIGH, (double) 0, (double) 0, true));
				list.add(new Service("6", "6", "Ha noi chu o dau",
						Service.RATE_MEDIUM, (double) 0, (double) 0, true));
				list.add(new Service("7", "7-2", "Ha noi chu o dau",
						Service.RATE_HIGH, (double) 0, (double) 0, true));
				list.add(new Service("8", "8-0", "Ha noi chu o dau",
						Service.RATE_LOW, (double) 0, (double) 0, true));
				list.add(new Service("9", "9-2", "Ha noi chu o dau",
						Service.RATE_HIGH, (double) 0, (double) 0, true));
				list.add(new Service("10", "10", "Ha noi chu o dau",
						Service.RATE_MEDIUM, (double) 0, (double) 0, true));
				list.add(new Service("11", "11", "Ha noi chu o dau",
						Service.RATE_MEDIUM, (double) 0, (double) 0, true));
				list.add(new Service("12", "12-0", "Ha noi chu o dau",
						Service.RATE_LOW, (double) 0, (double) 0, true));
				list.add(new Service("13", "13-0", "Ha noi chu o dau",
						Service.RATE_LOW, (double) 0, (double) 0, true));
				list.add(new Service("14", "14-2", "Ha noi chu o dau",
						Service.RATE_HIGH, (double) 0, (double) 0, true));
				list.add(new Service("15", "15", "Ha noi chu o dau",
						Service.RATE_MEDIUM, (double) 0, (double) 0, true));
				list.add(new Service("16", "16-0", "Ha noi chu o dau",
						Service.RATE_LOW, (double) 0, (double) 0, true));
				list.add(new Service("17", "17-0", "Ha noi chu o dau",
						Service.RATE_LOW, (double) 0, (double) 0, true));
				list.add(new Service("18", "18", "Ha noi chu o dau",
						Service.RATE_MEDIUM, (double) 0, (double) 0, true));
				list.add(new Service("19", "19-2", "Ha noi chu o dau",
						Service.RATE_HIGH, (double) 0, (double) 0, true));
				list.add(new Service("20", "20", "Ha noi chu o dau",
						Service.RATE_MEDIUM, (double) 0, (double) 0, true));
				
				adapter.setListServices(list);
			}
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id){
					Service service = adapter.getItem(position);
					String serviceId = service.getId();
					String name = service.getName();
					DetailServiceFragment fragment = DetailServiceFragment
							.newInstance(serviceId, name);
					getNavigationManager().showPage(fragment);
				}
			});
		}
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
		// TODO request search
	}
}