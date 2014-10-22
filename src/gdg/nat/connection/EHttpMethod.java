package gdg.nat.connection;

public enum EHttpMethod {
	GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

	private String method;

	EHttpMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return this.method;
	}
}