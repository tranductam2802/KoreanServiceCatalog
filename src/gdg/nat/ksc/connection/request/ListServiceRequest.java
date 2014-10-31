package gdg.nat.ksc.connection.request;

import gdg.nat.connection.EHttpMethod;
import gdg.nat.connection.RequestParam;
import gdg.nat.util.LocationUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ListServiceRequest extends RequestParam {
	private static final long serialVersionUID = -5538466472825348922L;
	private final String CATE_ID = "cid";
	private final String CITY = "city";

	private String cateId = "";
	private int city = LocationUtil.CITY_ALL;

	public ListServiceRequest(String cateId, int city) {
		this.cateId = cateId;
		this.city = city;
	}

	@Override
	public EHttpMethod getHttpMethod() {
		return EHttpMethod.POST;
	}

	@Override
	public String getApi() {
		return "get_service_by_cid_and_city";
	}

	@Override
	public String getParam() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(API, getApi());
			jsonObject.put(CATE_ID, cateId);
			jsonObject.put(CITY, city);
			return jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
}