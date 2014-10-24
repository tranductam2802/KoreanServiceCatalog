package gdg.nat.service;

import android.app.IntentService;
import android.content.Intent;

public class GCMService extends IntentService {
	public GCMService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
	}
}