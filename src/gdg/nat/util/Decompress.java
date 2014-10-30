package gdg.nat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Decompress {
	private final String TAG = "TrackingDecompress";

	private String zipFile;
	private String location;

	public Decompress(String zipFile, String unZipfile) {
		this.zipFile = zipFile;
		this.location = unZipfile;
		dirChecker("");
	}

	public void unzip() {
		try {
			FileInputStream fin = new FileInputStream(zipFile);
			ZipInputStream zin = new ZipInputStream(fin);
			ZipEntry ze = zin.getNextEntry();
			while (ze != null) {
				GdgLog.v(TAG, "Unzipping: " + ze.getName());

				if (ze.isDirectory()) {
					dirChecker(ze.getName());
				} else {
					String iconName = ze.getName();
					String[] iconDirList = iconName.split(File.separator);
					iconName = iconDirList[iconDirList.length - 1];
					iconName = iconName.substring(0, iconName.indexOf("."))
							+ StorageUtil.FILE_ICON_TYPE;
					File file = new File(location + File.separator + iconName);
					file.createNewFile();
					GdgLog.i(TAG, "Unzipping file: " + file.getPath());
					FileOutputStream fout = new FileOutputStream(file.getPath());
					byte[] b = new byte[1024];
					int length = -1;
					while ((length = zin.read(b)) != -1) {
						fout.write(b, 0, length);
					}
					zin.closeEntry();
					fout.flush();
					fout.close();
				}
				ze = zin.getNextEntry();
			}
			zin.close();
		} catch (Exception e) {
			GdgLog.e("Decompress", e.getMessage());
		}
		// delete the .zip file
		File file = new File(zipFile);
		if (file.exists()) {
			file.delete();
		}
	}

	private void dirChecker(String dir) {
		File f = new File(location + dir);
		if (!f.isDirectory()) {
			f.mkdirs();
		}
	}
}