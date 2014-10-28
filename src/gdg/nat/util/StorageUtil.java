package gdg.nat.util;

import java.io.File;

import android.os.Environment;

public class StorageUtil {
	private static final String DIR_HOME = "/ksc";
	private static final String DIR_IMAGE = "/image";

	private static final String FILE_ICON_ZIP = "/icon.zip";

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
}