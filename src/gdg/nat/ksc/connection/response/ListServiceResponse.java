package gdg.nat.ksc.connection.response;

import gdg.nat.connection.ResponseCode;
import gdg.nat.connection.ResponseParser;
import gdg.nat.ksc.data.Service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ListServiceResponse extends ResponseParser {
	private final String KEY_ID = "id";
	private final String KEY_NAME = "name";
	private final String KEY_ADDRESS = "address";
	private final String KEY_WEBSITE = "website";
	private final String KEY_PHONE = "phone";
	private final String KEY_DESCRIPTION = "description";
	private final String KEY_PROMOTION = "promotion";
	private final String KEY_RATE = "rate";
	private final String KEY_LAT = "lat";
	private final String KEY_LON = "lng";
	private final String KEY_IS_PROMOTION = "isPromotion";

	List<Service> services;

	public List<Service> getListServices() {
		return services;
	}

	public ListServiceResponse(String data, int code) {
		super(data, code);
	}

	@Override
	protected void parseResponse(JSONObject jsonObject) {
		if (jsonObject == null)
			return;
		try {
			if (jsonObject.has(KEY_CODE)) {
				int code = jsonObject.getInt(KEY_CODE);
				setCode(code);
			}
			if (jsonObject.has(KEY_DATA)) {
				JSONArray array = jsonObject.getJSONArray(KEY_DATA);
				int arrayLength = array.length();
				services = new ArrayList<Service>();
				for (int i = 0; i < arrayLength; i++) {
					JSONObject jsonObjectItem = array.getJSONObject(i);
					String id = "";
					if (jsonObjectItem.has(KEY_ID)) {
						id = jsonObjectItem.getString(KEY_ID);
					}

					String name = "";
					if (jsonObjectItem.has(KEY_NAME)) {
						name = jsonObjectItem.getString(KEY_NAME);
					}

					String address = "";
					if (jsonObjectItem.has(KEY_ADDRESS)) {
						address = jsonObjectItem.getString(KEY_ADDRESS);
					}

					String website = "";
					if (jsonObjectItem.has(KEY_WEBSITE)) {
						website = jsonObjectItem.getString(KEY_WEBSITE);
					}

					String phone = "";
					if (jsonObjectItem.has(KEY_PHONE)) {
						phone = jsonObjectItem.getString(KEY_PHONE);
					}

					String description = "";
					if (jsonObjectItem.has(KEY_DESCRIPTION)) {
						description = jsonObjectItem.getString(KEY_DESCRIPTION);
					}

					String promotion = "";
					if (jsonObjectItem.has(KEY_PROMOTION)) {
						promotion = jsonObjectItem.getString(KEY_PROMOTION);
					}

					int rate = Service.RATE_MEDIUM;
					if (jsonObjectItem.has(KEY_RATE)) {
						rate = jsonObjectItem.getInt(KEY_RATE);
					}

					double lon = 0;
					if (jsonObjectItem.has(KEY_LON)) {
						lon = jsonObjectItem.getDouble(KEY_LON);
					}

					double lat = 0;
					if (jsonObjectItem.has(KEY_LAT)) {
						lat = jsonObjectItem.getDouble(KEY_LAT);
					}

					boolean isPromotion = false;
					if (jsonObjectItem.has(KEY_IS_PROMOTION)) {
						if (jsonObjectItem.getInt(KEY_IS_PROMOTION) == 1) {
							isPromotion = true;
						}
					}

					Service service = new Service(id, name, address, website,
							phone, description, promotion, rate, lon, lat,
							isPromotion);
					services.add(service);
				}
			}
		} catch (Exception e) {
			setCode(ResponseCode.CLIENT_ERROR_PARSE_JSON);
			e.printStackTrace();
		}
	}
}