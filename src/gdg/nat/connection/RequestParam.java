package gdg.nat.connection;

import java.io.Serializable;

import com.google.gson.Gson;

public abstract class RequestParam implements Serializable {
	private static final long serialVersionUID = -4942903539643673029L;

	private String api = "";

	protected abstract void setApi();

	protected void setApi(String api) {
		this.api = api;
	}

	public String getApi() {
		return api;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}