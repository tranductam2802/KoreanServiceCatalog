package gdg.nat.ksc.connection.response;

import gdg.nat.connection.ResponseCode;
import gdg.nat.connection.ResponseParser;

import org.json.JSONObject;

public class DownloadIconResponse extends ResponseParser {
	private String zip;

	public String getZip() {
		return zip;
	}

	public DownloadIconResponse(String data, int code) {
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
				zip = jsonObject.getString(KEY_DATA);
			}
		} catch (Exception e) {
			setCode(ResponseCode.CLIENT_ERROR_PARSE_JSON);
			e.printStackTrace();
		}
	}
}