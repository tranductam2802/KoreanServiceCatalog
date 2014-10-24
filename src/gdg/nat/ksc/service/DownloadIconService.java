package gdg.nat.ksc.service;

import gdg.nat.ksc.config.Config;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class DownloadIconService extends IntentService {
	private static final String TAG = "TrackingDownloadIconService";

	public static final int UPDATE_PROGRESS = 8344;

	public DownloadIconService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String urlToDownload = intent.getStringExtra(Config.getImgServerURL());
		ResultReceiver receiver = (ResultReceiver) intent
				.getParcelableExtra("receiver");
		try {
			URL url = new URL(urlToDownload);
			URLConnection connection = url.openConnection();
			connection.connect();
			int fileLength = connection.getContentLength();

			// download the file
			InputStream input = new BufferedInputStream(
					connection.getInputStream());
			OutputStream output = new FileOutputStream(
					"/sdcard/BarcodeScanner-debug.apk");

			byte data[] = new byte[1024];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				Bundle resultData = new Bundle();
				resultData.putInt("progress", (int) (total * 100 / fileLength));
				receiver.send(UPDATE_PROGRESS, resultData);
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Bundle resultData = new Bundle();
		resultData.putInt("progress", 100);
		receiver.send(UPDATE_PROGRESS, resultData);
	}
}