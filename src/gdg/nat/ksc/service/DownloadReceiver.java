package gdg.nat.ksc.service;

import gdg.nat.util.GdgLog;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class DownloadReceiver extends ResultReceiver {
	private final String TAG = "TrackingDownLoad";
	private IUpdateDownloadProgress listener;

	public DownloadReceiver(Handler handler) {
		super(handler);
	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		super.onReceiveResult(resultCode, resultData);
		if (resultCode == DownloadIconService.UPDATE_PROGRESS) {
			int progress = resultData.getInt("progress");
			GdgLog.i(TAG, String.valueOf("Update progress: " + progress));
			if (listener != null) {
				listener.onUpdate(progress);
			}
		}
	}

	public void setListener(IUpdateDownloadProgress downloadProgress) {
		this.listener = downloadProgress;
	}

	public interface IUpdateDownloadProgress {
		public void onUpdate(int progress);
	}
}