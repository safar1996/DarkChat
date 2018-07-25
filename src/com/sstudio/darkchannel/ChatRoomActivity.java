package com.sstudio.darkchannel;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import com.sstudio.darkchannel.config.Constant;
import com.sstudio.darkchannel.main.ChatRoomView;
import com.sstudio.darkchannel.main.DatabaseHelper;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.sstudio.darkchannel.main.*;
import android.widget.*;
import android.os.*;
import android.content.*;
import com.sstudio.darkchannel.service.*;
import android.util.*;
import android.app.*;


public class ChatRoomActivity extends Activity implements MessageReceiver.Receiver {
	public static final String TAG = ChatRoomActivity.class.getName();
	private ChatRoomView chatRoomView;
	private boolean isIncomingServiceBound = false;
	private MessageReceiver msgReceiver;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_room_layout);
		chatRoomView = (ChatRoomView) findViewById(R.id.chatRoomView);
		msgReceiver = new MessageReceiver(new Handler());
		msgReceiver.setReceiver(this);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Intent intent = new Intent(this, IncomingMessageService.class);
		
		Bundle data = new Bundle();
		data.putParcelable(IncomingMessageService.RECEIVER_TAG, msgReceiver);
		intent.putExtras(data);
        
		startService(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	protected void makeUserMessage(String username, String msgType, String msg) {
		TextView msgView = (TextView) LayoutInflater.from(this).inflate(R.layout.user_text_msg, null, false);
		msgView.setText(msg);
		chatRoomView.addMessage(msgView, username);
	}
	
	protected void makeSystemMessage(String msg) {
		TextView msgView = (TextView) LayoutInflater.from(this).inflate(R.layout.system_text_msg, null, false);
		msgView.setText(msg);
		chatRoomView.addMessage(msgView);
	}
	
	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName p1, IBinder p2) {
			isIncomingServiceBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName p1) {
			isIncomingServiceBound = false;
		}
	};

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		makeUserMessage("Debugger", "message/text", "Test");
	}
	
}
