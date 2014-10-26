package gdg.nat.ksc.connection.response;

import org.json.JSONObject;

import gdg.nat.connection.ResponseCode;
import gdg.nat.connection.ResponseParser;

public class CheckCateVersionResponse extends ResponseParser{
	public CheckCateVersionResponse(String data, int code){
		super(data, code);
	}
	
	@Override
	protected void parseResponse(JSONObject jsonObject){
		if(jsonObject == null) return;
		try{
			if(jsonObject.has(KEY_CODE)){
				int code = jsonObject.getInt(KEY_CODE);
				setCode(code);
			}
		}catch(Exception e){
			setCode(ResponseCode.CLIENT_ERROR_PARSE_JSON.getCode());
			e.printStackTrace();
		}
	}
}