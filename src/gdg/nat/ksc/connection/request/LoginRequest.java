package gdg.nat.ksc.connection.request;

import gdg.nat.connection.RequestParam;

public class LoginRequest extends RequestParam {
	private static final long serialVersionUID = -5113066929097792085L;
	private String device_id;
	private String email;
	private String pwd;
	private String fb_id;
	private String notify_token;
	private String login_time;

	@Override
	protected void setApi() {
		setApi("login");
	}

	public LoginRequest(String deviceId, String email, String password,
			String login_time, String notify_token) {
		super();
		setApi();
		this.device_id = deviceId;
		this.email = email;
		this.pwd = password;
		this.login_time = login_time;
		this.notify_token = notify_token;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getFb_id() {
		return fb_id;
	}

	public void setFb_id(String fb_id) {
		this.fb_id = fb_id;
	}

	public String getNotify_token() {
		return notify_token;
	}

	public void setNotify_token(String notify_token) {
		this.notify_token = notify_token;
	}

	public String getLogin_time() {
		return login_time;
	}

	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}