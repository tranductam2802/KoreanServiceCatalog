package gdg.nat.util;

import java.io.File;

import android.content.Context;

public class GetImage {
	public static String getImage(Context context, String fileName) {
		File iconDir = new File(context.getExternalFilesDir(null),
				StorageUtil.DIR_ICON);
		StringBuilder builder = new StringBuilder();
		builder.append(iconDir.getAbsolutePath()).append(File.separator)
				.append(fileName).append(StorageUtil.FILE_ICON_TYPE);
		return builder.toString();
	}
}