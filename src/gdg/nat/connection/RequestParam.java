package gdg.nat.connection;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class RequestParam implements Serializable {
	public final String SLASH = "/";
	public final String API = "api";
	public static final EHttpMethod DEFAULT_HTTP_METHOD = EHttpMethod.GET;
	private EHttpMethod httpMethod = DEFAULT_HTTP_METHOD;

	public EHttpMethod getHttpMethod() {
		return httpMethod;
	}

	public abstract String getApi();

	public abstract String getParam();
}