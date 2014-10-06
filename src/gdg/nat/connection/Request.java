package gdg.nat.connection;

public class Request {
	private String api;
	private String requestParam;
	private String subURL = "";
	private EHttpMethod httpMethod;

	public Request(RequestParam requestParam, EHttpMethod httpMethod) {
		this.api = requestParam.getApi();
		this.requestParam = requestParam.toString();
		this.httpMethod = httpMethod;
	}

	public Request(String subURL, EHttpMethod httpMethod) {
		this.api = subURL;
		this.requestParam = "";
		this.subURL = subURL;
		this.httpMethod = httpMethod;
	}

	public Request(RequestParam requestParam, String subURL,
			EHttpMethod httpMethod) {
		this.api = requestParam.getApi();
		this.requestParam = requestParam.toString();
		this.subURL = subURL;
		this.httpMethod = httpMethod;
	}

	public String getApi() {
		return api;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public String getSubURL() {
		return subURL;
	}

	public void setHttpMethod(EHttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public EHttpMethod getHttpMethod() {
		return httpMethod;
	}
}