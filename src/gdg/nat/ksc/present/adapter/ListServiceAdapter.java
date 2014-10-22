package gdg.nat.ksc.present.adapter;

import gdg.nat.comparator.ServiceComparator;
import gdg.nat.ksc.R;
import gdg.nat.ksc.data.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListServiceAdapter extends BaseAdapter {
	private List<Service> listService;

	private Context context;

	public ListServiceAdapter(Context context) {
		this.context = context;
		this.listService = new ArrayList<Service>();
	}

	public ListServiceAdapter(Context context, List<Service> listService) {
		this.context = context;
		if (listService == null) {
			this.listService = new ArrayList<Service>();
		} else {
			this.listService = listService;
		}
	}

	public void setListServices(List<Service> listService) {
		if (listService == null)
			return;
		this.listService = listService;
		notifyDataSetChanged();
	}

	public void calculateDistance(double lat1, double lng1) {
		for (Service services : listService) {
			double lat2 = services.getLatitude();
			double lng2 = services.getLongitude();
			services.setDistance(distanceFrom(lat1, lng1, lat2, lng2));
		}
		notifyDataSetChanged();
	}

	public double distanceFrom(double lat1, double lng1, double lat2,
			double lng2) {
		double earthRadius = 6371; // kilometers
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = (double) (earthRadius * c);

		return dist;
	}

	@Override
	public int getCount() {
		return listService.size();
	}

	@Override
	public Service getItem(int position) {
		return listService.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public void notifyDataSetChanged() {
		orderItem();
		super.notifyDataSetChanged();
	}

	private void orderItem() {
		Collections.sort(listService, new ServiceComparator());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ServiceHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater
					.inflate(R.layout.item_service, parent, false);
			holder = new ServiceHolder();
			holder.layout = (RelativeLayout) convertView
					.findViewById(R.id.layout);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.distance = (TextView) convertView
					.findViewById(R.id.distance);
			convertView.setTag(holder);
		} else {
			holder = (ServiceHolder) convertView.getTag();
		}

		// TODO: Show view
		Service services = getItem(position);

		// Set service name
		holder.name.setText(services.getName());
		// Set service description
		holder.address.setText(services.getDescription());
		// Set distance from service
		if (services.getDistance() > 1) {
			holder.distance
					.setText(String.format(context.getString(R.string.unit_km),
							services.getDistance()));
		} else {
			holder.distance.setText(context.getString(R.string.unit_near));
		}

		if (services.getRate() == Service.RATE_HIGH) {
			holder.name.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.ic_hot, 0);
		}

		return convertView;
	}

	public class ServiceHolder {
		public RelativeLayout layout;
		public TextView name;
		public TextView address;
		public TextView distance;
	}
}