package gdg.nat.ksc.connection.response;

import gdg.nat.connection.ResponseCode;
import gdg.nat.connection.ResponseParser;
import gdg.nat.ksc.data.Categories;
import gdg.nat.ksc.data.Category;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetCategoriesResponse extends ResponseParser {
	private final String KEY_ID = "id";
	private final String KEY_NAME = "name";
	private final String KEY_IMG_ID = "imgId";
	private final String KEY_SUB_CATE = "subCat";
	private Categories categories;

	public Categories getCategories() {
		return categories;
	}

	public GetCategoriesResponse(String data, int code) {
		super(data, code);
	}

	@Override
	protected void parseResponse(JSONObject jsonObject) {
		categories = new Categories();
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

					String avatarId = "";
					if (jsonObjectItem.has(KEY_IMG_ID)) {
						avatarId = jsonObjectItem.getString(KEY_IMG_ID);
					}

					JSONArray subCateList = null;
					if (jsonObjectItem.has(KEY_SUB_CATE)) {
						subCateList = jsonObjectItem.getJSONArray(KEY_SUB_CATE);
					}

					Category category = new Category(id, name, avatarId);
					int subCateListLength = subCateList.length();
					if (subCateList != null) {
						for (int j = 0; j < subCateListLength; j++) {
							String subId = "";
							if (jsonObjectItem.has(KEY_ID)) {
								subId = jsonObjectItem.getString(KEY_ID);
							}

							String subName = "";
							if (jsonObjectItem.has(KEY_NAME)) {
								subName = jsonObjectItem.getString(KEY_NAME);
							}

							String subAva = "";
							if (jsonObjectItem.has(KEY_IMG_ID)) {
								subAva = jsonObjectItem.getString(KEY_IMG_ID);
							}

							Category subCategory = new Category(subId, subName,
									subAva);
							category.addCate(subCategory);
						}
					}
					categories.add(category);
				}
			}
		} catch (Exception e) {
			setCode(ResponseCode.CLIENT_ERROR_PARSE_JSON.getCode());
			e.printStackTrace();
		}
	}
}