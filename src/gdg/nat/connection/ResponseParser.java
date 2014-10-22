package gdg.nat.connection;

import gdg.nat.util.GdgLog;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class ResponseParser {
	private final String TAG = "TrackingResponseParser";
	public final String KEY_CODE = "code";
	public final String KEY_DATA = "data";
	private int code = ResponseCode.CLIENT_ERROR_UNKNOW.getCode();

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public ResponseParser() {
	}

	public ResponseParser(String data, int code) {
		setCode(code);
		try {
			JSONObject jsonObject = new JSONObject(data);
			parseResponse(jsonObject);
		} catch (JSONException e) {
			GdgLog.e(TAG, String.valueOf(e.getMessage()));
			setCode(ResponseCode.CLIENT_ERROR_PARSE_JSON.getCode());
			e.printStackTrace();
		}
	}

	protected abstract void parseResponse(JSONObject jsonObject);
}