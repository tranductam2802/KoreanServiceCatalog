package gdg.nat.connection;

public enum EHttpMethod {
	GET("GET"), POST("POST"), HEAD("HEAD"), OPTIONS("OPTIONS"), PUT("PUT"), DELETE(
			"DELETE"), TRACE("TRACE");
	private String method;

	EHttpMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return this.method;
	}
}