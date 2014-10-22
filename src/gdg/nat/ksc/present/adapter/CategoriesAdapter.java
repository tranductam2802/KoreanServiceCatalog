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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoriesAdapter extends BaseAdapter {
	private Context context;
	private List<Category> listCate;

	public void setListCategories(List<Category> listCate) {
		this.listCate = listCate;
		notifyDataSetChanged();
	}

	public CategoriesAdapter(Context context) {
		this.context = context;
		this.listCate = new ArrayList<Category>();
	}

	@Override
	public int getCount() {
		return listCate.size();
	}

	@Override
	public Category getItem(int position) {
		return listCate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_category_grid, parent,
					false);

			holder.imgIcon = (ImageView) convertView.findViewById(R.id.avatar);
			holder.txtName = (TextView) convertView.findViewById(R.id.name);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Category category = getItem(position);

		String name = category.getName();
		holder.txtName.setText(name);
		holder.imgIcon.setImageResource(GetImage.getImage());

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

	public class Holder {
		public ImageView imgIcon;
		public TextView txtName;
	}
}