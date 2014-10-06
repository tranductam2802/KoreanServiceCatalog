package gdg.nat.connection;

import gdg.nat.ksc.config.Config;
import gdg.nat.util.GdgLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

enum WebServiceStatus {
	READY, PRE_EXECUTE, RUNNING
}

/** An AsyncTask task handle request server */
public class WebServiceCaller extends AsyncTask<Request, Integer, Response> {
	private final String TAG = "TrackingWebService";
	private final String TAG_REQUEST = "TrackingRequest";
	private final String TAG_RESPONSE = "TrackingResponse";
	private IWebServiceListener webServiceListener;

	private WebServiceStatus webServiceStatus = WebServiceStatus.READY;

	public WebServiceStatus webServiceStatus() {
		return webServiceStatus;
	}

	private void setWebServiceStatus(WebServiceStatus webServiceStatus) {
		this.webServiceStatus = webServiceStatus;
	}

	public boolean isRunning() {
		return webServiceStatus == WebServiceStatus.PRE_EXECUTE
				|| webServiceStatus == WebServiceStatus.RUNNING;
	}

	protected WebServiceCaller() {
		// Do nothing. Do not allow user create a new WebServiceCaller instance.
	}

	public void setWebServiceListener(IWebServiceListener webServiceListener) {
		this.webServiceListener = webServiceListener;
	}

	@Override
	protected void onPreExecute() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onPreExecute");
		}
		setWebServiceStatus(WebServiceStatus.PRE_EXECUTE);
		super.onPreExecute();
	}

	@Override
	protected Response doInBackground(Request... params) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before doInBackground");
		}
		setWebServiceStatus(WebServiceStatus.RUNNING);

		if (params[0] == null || params[0].getRequestParam() == null) {
			GdgLog.e(TAG, "RequestParam sending mull");
			return null;
		}

		// Get request from parameter
		Request request = params[0];
		String api = request.getApi();
		GdgLog.i(TAG_REQUEST, "Send data api: " + api);
		String requestParam = request.getRequestParam();
		String subURL = request.getSubURL();
		EHttpMethod httpMethod = request.getHttpMethod();

		// Prepare connection
		HttpURLConnection httpURLConnection = null;
		// Prepare out put stream of the connection
		OutputStream outputStream = null;
		// Prepare input stream reader to read output stream
		InputStreamReader inputStreamReader = null;
		// Prepare buffer of the reader
		BufferedReader bufferedReader = null;

		// URL to web service
		URL url = null;
		try {
			// Create URL from configure file
			if (subURL.length() == 0) {
				url = new URL(Config.getMainServerURL());
			} else {
				url = new URL(Config.getMainServerURL() + subURL);
			}
			GdgLog.i(TAG, "Send data url: " + url.toString());

			// Open a connection to server
			httpURLConnection = (HttpURLConnection) url.openConnection();

			// Setup common property to this connection
			httpURLConnection.setConnectTimeout(Config.TIMEOUT_CONNECT);
			httpURLConnection.setReadTimeout(Config.TIMEOUT_READ);

			// Setup HTTP Method
			switch (httpMethod) {
				case GET:
					// Default HTTP method of HttpUrlConnection
					break;
				case POST:
					// Setup POST HTTP method information
					httpURLConnection.setRequestMethod(EHttpMethod.POST
							.getMethod());
					httpURLConnection.setDoOutput(true);
					httpURLConnection.setRequestProperty("Content-Language",
							"en-US");
					httpURLConnection.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					GdgLog.i(TAG_REQUEST, "Send data: " + requestParam);
					httpURLConnection.setRequestProperty("Content-Length",
							Integer.toString(requestParam.getBytes().length));
					// Parse request parameter to byte before sending
					byte[] postDataByte = requestParam.getBytes("UTF-8");

					// Get output stream to send data
					outputStream = httpURLConnection.getOutputStream();
					// Send data to output stream
					outputStream.write(postDataByte);
					break;
				case HEAD:
					// Setup HEAD HTTP method information
					httpURLConnection.setRequestMethod(EHttpMethod.HEAD
							.getMethod());
					break;
				case OPTIONS:
					// Setup OPTIONS HTTP method information
					httpURLConnection.setRequestMethod(EHttpMethod.OPTIONS
							.getMethod());
					break;
				case PUT:
					// Setup PUT HTTP method information
					httpURLConnection.setRequestMethod(EHttpMethod.PUT
							.getMethod());
					break;
				case DELETE:
					// Setup DELETE HTTP method information
					httpURLConnection.setRequestMethod(EHttpMethod.DELETE
							.getMethod());
					break;
				case TRACE:
					// Setup TRACE HTTP method information
					httpURLConnection.setRequestMethod(EHttpMethod.TRACE
							.getMethod());
					break;
			}

			// Get response status
			int responseCode = httpURLConnection.getResponseCode();
			String responseMsg = httpURLConnection.getResponseMessage();
			GdgLog.i(TAG, "Server code: " + String.valueOf(responseCode)
					+ "\nMessage: " + responseMsg);

			// Get input stream to read response
			inputStreamReader = new InputStreamReader(
					httpURLConnection.getInputStream(), "UTF-8");
			// Create an buffer to tempt data saved
			bufferedReader = new BufferedReader(inputStreamReader);

			// Add data to buffer
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = bufferedReader.readLine()) != null) {
				// Read string response
				response.append(inputLine);
			}

			String outputData = response.toString();
			GdgLog.i(TAG_RESPONSE, outputData);
			return new Response(api, requestParam,
					ResponseCode.SERVER_SUCCESS.getCode(), outputData);
		} catch (MalformedURLException e) {
			GdgLog.e(TAG,
					"MalformedURLException: " + String.valueOf(e.getMessage()));
			e.printStackTrace();
		} catch (ProtocolException e) {
			GdgLog.e(TAG,
					"ProtocolException: " + String.valueOf(e.getMessage()));
			e.printStackTrace();
		} catch (IOException e) {
			GdgLog.e(TAG, "IOException: " + String.valueOf(e.getMessage()));
			e.printStackTrace();
		} finally {
			// Check and close output stream for close connection
			if (outputStream != null)
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			// Check and close input stream for close connection
			if (inputStreamReader != null)
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			// Check and close buffer for clear memory
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			// Close connection for free socket
			if (httpURLConnection != null)
				httpURLConnection.disconnect();
		}
		return new Response(api, requestParam,
				ResponseCode.CLIENT_ERROR_NO_CONNECTION.getCode(), "");
	}

	@Override
	protected void onPostExecute(Response result) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onPostExecute");
		}
		super.onPostExecute(result);

		// Set ready status
		setWebServiceStatus(WebServiceStatus.READY);
		if (webServiceListener != null)
			webServiceListener.onFinishRequest(result);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onProgressUpdate");
		}
		super.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled() {
		if (Config.isTrackingAppStatus()) {
			Log.i(TAG, "Start tracking before onCancelled");
		}
		super.onCancelled();
	}
}