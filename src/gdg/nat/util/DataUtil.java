package gdg.nat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DataUtil {
	private static final String TAG = "TrackingDataUtil";

	/**
	 * Encrypt string input with SHA-1 method. Data could be not change if have
	 * error
	 */
	public static String encryptSHA(String rawData) {
		String encryptedData = rawData;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			// Get message digest for encrypt
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] bytes = encryptedData.getBytes("UTF-8");
			digest.update(bytes, 0, bytes.length);
			bytes = digest.digest();
			int length = bytes.length;

			// Update data encrypted to string
			for (int i = 0; i < length; i++) {
				byte b = bytes[i];
				stringBuilder.append(String.format("%02x", b));
			}
			encryptedData = stringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			GdgLog.e(TAG, String.valueOf(e.getMessage()));
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			GdgLog.e(TAG, String.valueOf(e.getMessage()));
			e.printStackTrace();
		} finally {
			stringBuilder.delete(0, stringBuilder.length());
		}
		return encryptedData;
	}
}