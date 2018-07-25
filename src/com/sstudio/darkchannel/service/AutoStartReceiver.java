package com.sstudio.darkchannel.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AutoStartReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent myIntent = new Intent(context, IncomingMessageService.class);
		context.startService(myIntent);
	}

}
