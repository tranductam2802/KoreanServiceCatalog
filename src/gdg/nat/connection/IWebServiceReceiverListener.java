package gdg.nat.connection;

public interface IWebServiceReceiverListener {
	public void onRequest(RequestParam requestParam);

	public void onReceiver(RequestParam requestParam,
			ResponseParser responseParser);
}