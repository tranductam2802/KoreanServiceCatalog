package gdg.nat.connection;

public interface IWebServiceReceiverListener {
	public void onRequest(String api, String requestParam);
	public void onReceiver(Response response);
}