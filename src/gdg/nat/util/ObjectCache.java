package gdg.nat.util;

import gdg.nat.ksc.config.KSCApp;
import gdg.nat.ksc.data.Categories;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;

public class ObjectCache {
	private static ObjectCache cache = new ObjectCache();

	private final String FILE_CATEGORIES = "categories";

	public static ObjectCache getInstance() {
		return cache;
	}

	public void saveCategories(Categories categories) {
		FileOutputStream fos = null;
		ObjectOutputStream os = null;
		try {
			fos = KSCApp.getInstance().openFileOutput(FILE_CATEGORIES,
					Context.MODE_PRIVATE);
			os = new ObjectOutputStream(fos);
			os.writeObject(categories);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Categories getCategories() {
		FileInputStream fis = null;
		ObjectInputStream is = null;
		Categories responseParser = null;
		try {
			fis = KSCApp.getInstance().openFileInput(FILE_CATEGORIES);
			is = new ObjectInputStream(fis);
			responseParser = (Categories) is.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseParser;
	}
}