package gdg.nat.ksc.present.fragment;

import gdg.nat.base.BaseFragment;
import gdg.nat.connection.IWebServiceReceiverListener;
import gdg.nat.connection.RequestParam;
import gdg.nat.connection.ResponseParser;
import gdg.nat.ksc.R;
import gdg.nat.ksc.data.Categories;
import gdg.nat.ksc.data.Category;
import gdg.nat.ksc.present.adapter.CategoriesAdapter;
import gdg.nat.navigation.INaviDefaultViewListener;
import gdg.nat.util.ObjectCache;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;

public class CategoriesFragment extends BaseFragment implements
		INaviDefaultViewListener, IWebServiceReceiverListener {
	private final String TAG = "TrackingCategoriesFragment";

	private final String INTENT_NAME = "name";
	private final String INTENT_CATE_ID = "cate_id";

	private static final String DEFAULT_CATEGORIES = "0";

	private String screenName = "";
	private String cateId = DEFAULT_CATEGORIES;

	private GridView gridView;
	private FrameLayout frameAds;

	private CategoriesAdapter adapter;

	public static CategoriesFragment newInstance() {
		CategoriesFragment fragment = new CategoriesFragment();
		return fragment;
	}

	public static CategoriesFragment newInstance(String name, String id) {
		CategoriesFragment fragment = new CategoriesFragment();
		Bundle bundle = new Bundle();
		bundle.putString(fragment.INTENT_NAME, name);
		bundle.putString(fragment.INTENT_CATE_ID, id);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_categories, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(INTENT_NAME)) {
				screenName = bundle.getString(INTENT_NAME);
			}
			if (bundle.containsKey(INTENT_CATE_ID)) {
				cateId = bundle.getString(INTENT_CATE_ID);
			}
		}

		gridView = (GridView) view.findViewById(R.id.grid);
		frameAds = (FrameLayout) view.findViewById(R.id.ads);
		frameAds.setVisibility(View.GONE);

		if (adapter == null) {
			adapter = new CategoriesAdapter(getActivity());
		}

		gridView.setAdapter(adapter);

		if (screenName.length() > 0) {
			getNavigationBar().setTitle(screenName);
		}

		if (cateId.equals(DEFAULT_CATEGORIES)) {
			loadListCategories();
		} else {
			loadListCategories(cateId);
		}
	}

	private void loadListCategories() {
		Categories categories = ObjectCache.getInstance().getCategories();
		List<Category> list = categories.getListCategories();
		adapter.setListCategories(list);
	}

	private void loadListCategories(String cateId) {
		Categories categories = ObjectCache.getInstance().getCategories();
		List<Category> list = categories.getListCategories();
		List<Category> listSubCategories = new ArrayList<Category>();
		for (Category cateItem : list) {
			if (cateItem.getId().equals(cateId)) {
				listSubCategories.addAll(cateItem.getSubCategories());
			}
		}
		adapter.setListCategories(listSubCategories);
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
				cateId);
		getNavigationManager().showPage(fragment);
	}

	@Override
	public int getTitleResource() {
		return R.string.title_home_screen;
	}

	@Override
	public void onRequest(RequestParam requestParam) {
		// TODO TamTD - request server
	}

	@Override
	public void onReceiver(RequestParam requestParam,
			ResponseParser responseParser) {
	}
}