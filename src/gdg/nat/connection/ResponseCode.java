package gdg.nat.connection;

/** Define response code from server */
public class ResponseCode {
	/****** Response code from server ******/
	// On server success
	public static final int SERVER_SUCCESS = 0;
	// Server default unknown error. Contact to server when meet this code
	public static final int SERVER_UNKNOW_ERROR = 01;
	// Data format sending to server wrong
	public static final int SERVER_WRONG_DATA_FORMAT = 02;
	// API out of date
	public static final int SERVER_OUT_OF_DATE_API = 03;
	// Throw exception
	public static final int SERVER_API_NOT_FOUND = 04;
	// Data had been changed
	public static final int SERVER_DATA_CHANGED = 05;
	// Re-login or change account
	public static final int SERVER_INVALID_TOKEN = 10;
	// Show error dialog
	public static final int SERVER_INVALID_USERNAME = 11;
	// Show error dialog
	public static final int SERVER_INVALID_EMAIL = 12;
	// Show error dialog
	public static final int SERVER_INVALID_PASSWORD = 13;
	// Show error dialog
	public static final int SERVER_EMAIL_OR_PASSWORD_NOT_FOUND = 14;
	// Show error dialog
	public static final int SERVER_EMAIL_REGISTED = 15;
	// Show error dialog
	public static final int SERVER_NOT_FOUND = 20;
	// Show error dialog
	public static final int SERVER_CATEGORIES_NOT_FOUND = 21;
	// Show error dialog
	public static final int SERVER_SUB_CATEGORIES_NOT_FOUND = 22;
	// Show error dialog
	public static final int SERVER_SERVICE_NOT_FOUND = 23;
	// Show error dialog
	public static final int SERVER_ACCESS_DENY = 30;

	/****** Error code from client ******/
	// Change to SERVER_SUCCESS code
	public static final int CLIENT_SUCCESS = 000;
	// Throw exception
	public static final int CLIENT_ERROR_UNKNOW = 101;
	// Show error dialog
	public static final int CLIENT_ERROR_NO_CONNECTION = 102;
	// Throw exception
	public static final int CLIENT_ERROR_NO_DATA = 103;
	// Throw exception
	public static final int CLIENT_ERROR_PARSE_JSON = 104;
	// Show error dialog
	public static final int CLIENT_FILE_NOT_FOUND = 105;
}