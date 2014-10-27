package gdg.nat.ksc.connection.request;

import org.json.JSONException;
import org.json.JSONObject;

import gdg.nat.connection.EHttpMethod;
import gdg.nat.connection.RequestParam;

public class CheckCateVersionRequest extends RequestParam{
	private static final long serialVersionUID = 7272992186161662869L;
	
	private final String VERSION = "ver";
	private int version = 0;
	
	public CheckCateVersionRequest(int version){
		this.version = version;
	}
	
	@Override
	public EHttpMethod getHttpMethod(){
		return EHttpMethod.POST;
	}
	
	@Override
	public String getApi(){
		return "version";
	}
	
	@Override
	public String getParam(){
		JSONObject jsonObject = new JSONObject();
		try{
			jsonObject.put(API, getApi());
			jsonObject.put(VERSION, version);
			return jsonObject.toString();
		}catch(JSONException e){
			e.printStackTrace();
			return "";
		}
	}
}