package gdg.nat.ksc.data;

public class Service {
	public static final int RATE_LOW = 0;
	public static final int RATE_MEDIUM = 1;
	public static final int RATE_HIGH = 2;

	private String id;
	private String name;
	private String address;
	private String website;
	private String phone;
	private String description;
	private String promotion;
	private int rate;
	private double lon;
	private double lat;
	private double distance;
	private boolean isPromotion;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getWebsite() {
		return website;
	}

	public String getPhone() {
		return phone;
	}

	public String getDescription() {
		return description;
	}

	public String getPromotion() {
		return promotion;
	}

	public int getRate() {
		return rate;
	}

	public double getLon() {
		return lon;
	}

	public double getLat() {
		return lat;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}

	public boolean isPromotion() {
		return isPromotion;
	}

	public Service(String id, String name, String address, int rate,
			double lon, double lat, boolean isPromotion) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.rate = rate;
		this.lon = 105.852283;
		this.lat = 21.02785;
		this.isPromotion = isPromotion;
		this.distance = 0;
	}

	public Service(String id, String name, String address, String website,
			String phone, String description, String promotion, int rate,
			double lon, double lat, boolean isPromotion) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.website = website;
		this.phone = phone;
		this.description = description;
		this.promotion = promotion;
		this.rate = rate;
		this.lon = 105.852283;
		this.lat = 21.02785;
		this.isPromotion = isPromotion;
		this.distance = 0;
	}
}