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
		INaviDefaultViewListener {
	private final String TAG = "TrackingSearch";

	private final String INTENT_KEYWORD = "keyword";
	private final String INTENT_CATE_ID = "cate_id";

	private String keyword = "";
	private String cateID = "";

	private ListServiceAdapter adapter;
	private ListView listView;
	private FrameLayout frameAds;

	public static ListServiceFragment newInstance(String keyword, String cateId) {
		ListServiceFragment fragment = new ListServiceFragment();
		Bundle bundle = new Bundle();
		bundle.putString(fragment.INTENT_KEYWORD, keyword);
		bundle.putString(fragment.INTENT_CATE_ID, cateId);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString(INTENT_KEYWORD, keyword);
		outState.putString(INTENT_CATE_ID, cateID);
		super.onSaveInstanceState(outState);
	}

	private void setKeyword(String keyword) {
		this.keyword = keyword;
		getNavigationBar().setTitle(keyword);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (keyword.length() > 0) {
			getNavigationBar().setTitle(keyword);
		} else {

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fg_list, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState == null) {
			Bundle bundle = getArguments();
			setKeyword(bundle.getString(INTENT_KEYWORD));
			cateID = bundle.getString(INTENT_CATE_ID);
		} else {
			setKeyword(savedInstanceState.getString(INTENT_KEYWORD));
			cateID = savedInstanceState.getString(INTENT_CATE_ID);
		}

		Bundle bundle = getArguments();

		if (bundle != null) {
			setKeyword(bundle.getString(INTENT_KEYWORD));
			cateID = bundle.getString(INTENT_CATE_ID);
			listView = (ListView) view.findViewById(R.id.list);
			frameAds = (FrameLayout) view.findViewById(R.id.ads);
			frameAds.setVisibility(View.GONE);
			if (adapter == null) {
				adapter = new ListServiceAdapter(getActivity());

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
				list.add(new Service(
						"",
						"Minh Ngoc Nguyen",
						"",
						"Quan an ngon o dau do cu bia ra nhin cho no dai dai de con test duoc cai ten dai chu neu khong thi cai ten dai kho ma test cho duoc",
						"", 0));
				list.add(new Service(
						"",
						"Minh Ngoc Nguyen",
						"",
						"Quan an ngon o dau do cu bia ra nhin cho no dai dai de con test duoc cai ten dai chu neu khong thi cai ten dai kho ma test cho duoc",
						"", 0));
				list.add(new Service(
						"",
						"Minh Ngoc Nguyen",
						"",
						"Quan an ngon o dau do cu bia ra nhin cho no dai dai de con test duoc cai ten dai chu neu khong thi cai ten dai kho ma test cho duoc",
						"", 0));
				list.add(new Service(
						"",
						"Minh Ngoc Nguyen",
						"",
						"Quan an ngon o dau do cu bia ra nhin cho no dai dai de con test duoc cai ten dai chu neu khong thi cai ten dai kho ma test cho duoc",
						"", 0));
				list.add(new Service(
						"",
						"Minh Ngoc Nguyen",
						"",
						"Quan an ngon o dau do cu bia ra nhin cho no dai dai de con test duoc cai ten dai chu neu khong thi cai ten dai kho ma test cho duoc",
						"", 0));
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
					Service service = adapter.getItem(position);
					String serviceId = service.getId();
					String name = service.getName();
					DetailServiceFragment fragment = DetailServiceFragment.newInstance(
							serviceId, name);
					getNavigationManager().showPage(fragment);
				}
			});
		}
	}

	@Override
	public String getFragmentTag() {
		return TAG;
	}

	@Override
	public String getTitle() {
		Categories categories = ObjectCache.getInstance().getCategories();
		if (categories.getCategories(cateID) != null) {
			return categories.getCategories(cateID).getName();
		}
		return super.getTitle();
	}

	@Override
	public void onGoBack() {
		getNavigationManager().goBack();
	}

	@Override
	public void onSearch(String keyword) {
		// TODO request search
	}
}