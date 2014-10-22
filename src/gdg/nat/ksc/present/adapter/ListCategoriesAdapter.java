package gdg.nat.ksc.present.adapter;

import gdg.nat.ksc.R;
import gdg.nat.ksc.data.Category;
import gdg.nat.ksc.present.activity.MainActivity;
import gdg.nat.ksc.present.fragment.ListServiceFragment;
import gdg.nat.ksc.present.fragment.SubcategoriesFragment;
import gdg.nat.ksc.service.GetImage;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListCategoriesAdapter extends BaseAdapter {
	private List<Category> categories;

	private Context context;
	private boolean isList = true;
	private static final int NUM_TYPE = 2;
	private static final int TYPE_LIST = 0;
	private static final int TYPE_GRID = 1;

	public ListCategoriesAdapter(Context context) {
		this.context = context;
		categories = new ArrayList<Category>();
	}

	public void setListCate(List<Category> categories) {
		this.categories = categories;
	}

	public void displayInList(boolean isList) {
		this.isList = isList;
	}

	public boolean isList() {
		return isList;
	}

	@Override
	public int getViewTypeCount() {
		return NUM_TYPE;
	}

	@Override
	public int getItemViewType(int position) {
		if (isList) {
			return TYPE_LIST;
		} else {
			return TYPE_GRID;
		}
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Category getItem(int position) {
		return categories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		switch (type) {
			case TYPE_LIST:
				return getListView(position, convertView, parent);
			case TYPE_GRID:
				return getGridView(position, convertView, parent);
		}
		return convertView;
	}

	public View getListView(final int position, View convertView,
			ViewGroup parent) {
		ListHolder holder = null;
		if (convertView == null || convertView.getTag() instanceof GridHolder) {
			holder = new ListHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_category_list, parent,
					false);
			holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.description = (TextView) convertView
					.findViewById(R.id.description);
			convertView.setTag(holder);
		} else {
			holder = (ListHolder) convertView.getTag();
		}
		Category category = categories.get(position);
		holder.name.setText(category.getName());
		holder.description
				.setText("Some thing to be a description for this service. I need it long to 4 line.");
		holder.avatar.setImageResource(GetImage.getImage());
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (context instanceof MainActivity) {
					Category category = getItem(position);
					List<Category> categories = category.getSubCategories();
					if (categories != null && categories.size() > 0) {
						SubcategoriesFragment fragment = SubcategoriesFragment
								.newInstance(position % 2 == 0,
										category.getId());
						((MainActivity) context).getNavigationManager()
								.showPage(fragment);
					} else {
						ListServiceFragment fragment = ListServiceFragment
								.newInstance("", category.getId());
						((MainActivity) context).getNavigationManager()
								.showPage(fragment);
					}
				}
			}
		});
		return convertView;
	}

	public View getGridView(int position, View convertView, ViewGroup parent) {
		GridHolder holder = null;
		if (convertView == null || convertView.getTag() instanceof ListHolder) {
			holder = new GridHolder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_category_grid, parent,
					false);
			holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (GridHolder) convertView.getTag();
		}
		Category category = categories.get(position);
		holder.name.setText(category.getName());
		holder.avatar.setImageResource(GetImage.getImage());
		return convertView;
	}

	public class ListHolder {
		public ImageView avatar;
		public TextView name;
		public TextView description;
	}

	public class GridHolder {
		public ImageView avatar;
		public TextView name;
	}
}