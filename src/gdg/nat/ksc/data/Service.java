package gdg.nat.ksc.data;

public class Service {
	public static final int RATE_LOW = 0;
	public static final int RATE_MEDIUM = 1;
	public static final int RATE_HIGH = 2;

	private String id;
	private String name;
	private String description;
	private String categoryId;
	private String avatarId;
	private int rate;
	private double longitude;
	private double latitude;
	private double distance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Service(String id, String name, String avatarId, String description,
			String categoryId, int rate, double longitude, double latitude) {
		this.id = id;
		this.name = name;
		this.avatarId = avatarId;
		this.description = description;
		this.categoryId = categoryId;
		this.rate = rate;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Service(String id, String name, String avatarId, String description,
			String categoryId, int rate) {
		this.id = id;
		this.name = name;
		this.avatarId = avatarId;
		this.description = description;
		this.categoryId = categoryId;
		this.rate = rate;
		this.longitude = 105.852283;
		this.latitude = 21.02785;
	}
}