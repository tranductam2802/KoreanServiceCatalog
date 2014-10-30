package gdg.nat.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;

public class StorageUtil {
	private static final String TAG = "TrackingStorageUtil";

	public static final String DIR_HOME = "/ksc";
	public static final String DIR_IMAGE = "/image";
	public static final String DIR_ICON = "/icon";

	private static final String FILE_ICON_ZIP = "/icon.zip";

	protected static final String TYPE_JPG = ".jpg";
	protected static final String TYPE_PNG = ".png";
	protected static final String TYPE_BITMAP = ".bmp";
	public static final String FILE_ICON_TYPE = TYPE_PNG;

	public static File getExternalStorageDirectory() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				Environment.getExternalStorageDirectory().getAbsolutePath())
				.append(DIR_HOME);
		return new File(builder.toString());
	}

	public static File getImageDirectory() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				Environment.getExternalStorageDirectory().getAbsolutePath())
				.append(DIR_HOME).append(DIR_IMAGE);
		return new File(builder.toString());
	}

	public static File getIconZip() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				Environment.getExternalStorageDirectory().getAbsolutePath())
				.append(DIR_HOME).append(DIR_IMAGE).append(FILE_ICON_ZIP);
		return new File(builder.toString());
	}

	public static byte[] decodeBase64(String base64Zip) {
		return Base64.decode(base64Zip, Base64.DEFAULT);
	}

	public static boolean saveIconFile(Context context, String fileName,
			byte[] data) {
		File iconDir = new File(context.getExternalFilesDir(null), DIR_ICON);
		if (!iconDir.exists()) {
			iconDir.mkdir();
		}
		File fileZip = new File(iconDir, "icon_tmp");
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(fileZip);
			GdgLog.i(TAG, iconDir.getAbsolutePath());
			fileOutputStream.write(data);
			fileOutputStream.flush();
			fileOutputStream.close();

			// Decompress file
			Decompress des = new Decompress(fileZip.getPath(),
					iconDir.getPath());
			des.unzip();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}