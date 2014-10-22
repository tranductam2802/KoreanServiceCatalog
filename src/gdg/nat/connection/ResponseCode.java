package gdg.nat.connection;

/** Define response code from server */
public enum ResponseCode {
	/****** Response code from server ******/
	// On server success
	SERVER_SUCCESS(00),
	// Server default unknown error. Contact to server when meet this code
	SERVER_UNKNOW_ERROR(01),
	// Data format sending to server wrong
	SERVER_WRONG_DATA_FORMAT(02),
	// API out of date
	SERVER_OUT_OF_DATE_API(03),
	// Throw exception
	SERVER_API_NOT_FOUND(04),
	// Data had been changed
	SERVER_DATA_CHANGED(05),
	// Re-login or change account
	SERVER_INVALID_TOKEN(10),
	// Show error dialog
	SERVER_INVALID_USERNAME(11),
	// Show error dialog
	SERVER_INVALID_EMAIL(12),
	// Show error dialog
	SERVER_INVALID_PASSWORD(13),
	// Show error dialog
	SERVER_EMAIL_OR_PASSWORD_NOT_FOUND(14),
	// Show error dialog
	SERVER_EMAIL_REGISTED(15),
	// Show error dialog
	SERVER_NOT_FOUND(20),
	// Show error dialog
	SERVER_CATEGORIES_NOT_FOUND(21),
	// Show error dialog
	SERVER_SUB_CATEGORIES_NOT_FOUND(22),
	// Show error dialog
	SERVER_SERVICE_NOT_FOUND(23),
	// Show error dialog
	SERVER_ACCESS_DENY(30),

	/****** Error code from client ******/
	// Change to SERVER_SUCCESS code
	CLIENT_SUCCESS(000),
	// Throw exception
	CLIENT_ERROR_UNKNOW(101),
	// Show error dialog
	CLIENT_ERROR_NO_CONNECTION(102),
	// Throw exception
	CLIENT_ERROR_NO_DATA(103),
	// Throw exception
	CLIENT_ERROR_PARSE_JSON(104),
	// Show error dialog
	CLIENT_FILE_NOT_FOUND(105);

	private int code;

	ResponseCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}