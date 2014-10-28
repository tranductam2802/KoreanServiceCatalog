package gdg.nat.ksc.connection.request;

import gdg.nat.connection.EHttpMethod;
import gdg.nat.connection.RequestParam;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchRequest extends RequestParam {
	private static final long serialVersionUID = -5538466472825348922L;
	private final String KEYWORD = "keyword";
	private final String CATE_ID = "catid";

	private String keyword = "";
	private String cateId = "";

	public SearchRequest(String keyword, String cateId) {
		this.keyword = keyword;
		this.cateId = cateId;
	}

	@Override
	public EHttpMethod getHttpMethod() {
		return EHttpMethod.POST;
	}

	@Override
	public String getApi() {
		return "search";
	}

	@Override
	public String getParam() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(API, getApi());
			jsonObject.put(KEYWORD, keyword);
			jsonObject.put(CATE_ID, cateId);
			return jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
}