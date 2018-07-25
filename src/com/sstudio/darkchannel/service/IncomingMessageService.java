package com.sstudio.darkchannel.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.content.Intent;
import android.app.IntentService;
import android.os.ResultReceiver;
import android.widget.Toast;
import android.content.*;


public class IncomingMessageService extends IntentService {
	
    public static final String RECEIVER_TAG = "receiver";
	private static ResultReceiver receiver;
	private static boolean isRunning = false;
	
	public IncomingMessageService() {
		super(IncomingMessageService.class.getName());
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
        receiver = intent.getExtras().getParcelable(RECEIVER_TAG);
        if (receiver == null || isRunning == false) {
            Toast.makeText(this, "Starting", Toast.LENGTH_SHORT).show();
            checkIncomingMessage();
        }
        Toast.makeText(this, "Continuing", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		isRunning = false;
		super.onDestroy();
	}
	
	private void checkIncomingMessage() {
		while (isRunning) {
			if (receiver != null) {
				receiver.send(0, null);
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {

			}
		}
	}
	
	public class LocalBinder extends Binder {
		IntentService getService() {
			return IncomingMessageService.this;
		}
	}
	
}
