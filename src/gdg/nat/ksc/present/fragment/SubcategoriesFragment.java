package gdg.nat.ksc.present.fragment;

import java.util.List;

import gdg.nat.base.BaseFragment;
import gdg.nat.ksc.R;
import gdg.nat.ksc.data.Categories;
import gdg.nat.ksc.data.Category;
import gdg.nat.ksc.present.activity.MainActivity;
import gdg.nat.ksc.present.adapter.ListCategoriesAdapter;
import gdg.nat.navigation.INaviDefaultViewListener;
import gdg.nat.util.ObjectCache;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;

public class SubcategoriesFragment extends BaseFragment implements
		INaviDefaultViewListener, OnItemClickListener {
	private final String TAG = "TrackingSearch";

	private final String INTENT_IS_LIST = "is_list";
	private final String INTENT_CATE_ID = "cate_id";

	private boolean isList = true;
	private String cateID = "";

	private Categories categories;

	private ListCategoriesAdapter adapter;
	private GridView gridView;
	private FrameLayout frameAds;

	public static SubcategoriesFragment newInstance(boolean isList,
			String cateId) {
		SubcategoriesFragment fragment = new SubcategoriesFragment();
		Bundle bundle = new Bundle();
		bundle.putBoolean(fragment.INTENT_IS_LIST, isList);
		bundle.putString(fragment.INTENT_CATE_ID, cateId);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			Bundle bundle = getArguments();
			isList = bundle.getBoolean(INTENT_IS_LIST);
			cateID = bundle.getString(INTENT_CATE_ID);
		} else {
			isList = savedInstanceState.getBoolean(INTENT_IS_LIST);
			cateID = savedInstanceState.getString(INTENT_CATE_ID);
		}
		categories = ObjectCache.getInstance().getCategories();
		if (categories.getCategories(cateID) == null) {
			getNavigationManager().goBack();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(INTENT_IS_LIST, isList);
		outState.putString(INTENT_CATE_ID, cateID);
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_sub_categories, container,
				false);
		initView(view);
		initData();
		return view;
	}

	private void initView(View v) {
		gridView = (GridView) v.findViewById(R.id.grid);
		frameAds = (FrameLayout) v.findViewById(R.id.ads);
		frameAds.setVisibility(View.GONE);
	}

	private void initData() {
		// TODO: Init data
		if (adapter == null) {
			adapter = new ListCategoriesAdapter(getActivity());
			adapter.setListCate(categories.getCategories(cateID)
					.getSubCategories());
		}
		adapter.displayInList(isList);
		if (isList) {
			gridView.setNumColumns(1);
		} else {
			gridView.setNumColumns(4);
		}
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
	}

	@Override
	public String getFragmentTag() {
		return TAG;
	}

	@Override
	public String getTitle() {
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Category category = adapter.getItem(position);
		List<Category> categories = category.getSubCategories();
		if (categories != null && categories.size() > 0) {
			SubcategoriesFragment fragment = SubcategoriesFragment.newInstance(
					position % 2 == 0, category.getId());
			getNavigationManager().showPage(fragment);
		} else {
			ListServiceFragment fragment = ListServiceFragment.newInstance("",
					category.getId());
			getNavigationManager().showPage(fragment);
		}
	}
}