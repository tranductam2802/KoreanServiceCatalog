package gdg.nat.ksc.connection.request;

import gdg.nat.connection.EHttpMethod;
import gdg.nat.connection.RequestParam;

import org.json.JSONException;
import org.json.JSONObject;

public class GetCategoriesRequest extends RequestParam {
	private static final long serialVersionUID = 8441982197458081921L;

	@Override
	public EHttpMethod getHttpMethod() {
		return EHttpMethod.POST;
	}

	@Override
	public String getApi() {
		return "list_categories";
	}

	@Override
	public String getParam() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(API, getApi());
			return jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
}