package gdg.nat.comparator;

import gdg.nat.ksc.data.Service;

import java.util.Comparator;

public class ServiceComparator implements Comparator<Service> {
	@Override
	public int compare(Service lhs, Service rhs) {
		int lRate = lhs.getRate();
		int rRate = rhs.getRate();
		if (lRate == rRate) {
			double lDistance = lhs.getDistance();
			double rDistance = rhs.getDistance();
			if (lDistance == rDistance) {
				String lName = lhs.getName();
				String rName = rhs.getName();
				return lName.compareTo(rName);
			} else {
				if (lDistance > rDistance) {
					return -1;
				} else {
					return 1;
				}
			}
		} else {
			if (lRate > rRate) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}