package gdg.nat.connection;

public class Response {
	protected RequestParam requestParam;
	protected int responseCode;
	protected String responseData;

	public RequestParam getRequestParam() {
		return requestParam;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getResponseData() {
		return responseData;
	}

	public Response(RequestParam requestPram, int responseCode,
			String responseData) {
		this.requestParam = requestPram;
		this.responseCode = responseCode;
		this.responseData = responseData;
	}

	public Response(RequestParam requestPram, int responseCode) {
		this.requestParam = requestPram;
		this.responseCode = responseCode;
		this.responseData = "";
	}
}