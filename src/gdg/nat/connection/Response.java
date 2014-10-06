package gdg.nat.connection;

public class Response {
	protected String api;
	protected String requestParam;
	protected int responseCode;
	protected String responseData;

	public String getApi() {
		return api;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getResponseData() {
		return responseData;
	}

	public Response(String api, String requestPram, int responseCode,
			String responseData) {
		this.api = api;
		this.requestParam = requestPram;
		this.responseCode = responseCode;
		this.responseData = responseData;
	}
}