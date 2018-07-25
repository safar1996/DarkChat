package com.sstudio.darkchannel.main;

import android.util.AttributeSet;
import android.content.Context;
import com.sstudio.darkchannel.config.Constant;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.sstudio.darkchannel.R;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;


public class ChatRoomView extends LinearLayout {
	
	private final String username;
	private Context context;
	private String lastUsername = "";
	
	public ChatRoomView(Context context) {
		super(context);
		username = getUserFromReference(context);
		this.context = context;
	}
	
	public ChatRoomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		username = getUserFromReference(context);
		this.context = context;
	}
	
	public ChatRoomView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		username = getUserFromReference(context);
		this.context = context;
	}
	
	protected String getUserFromReference(Context context) {
		return context.getSharedPreferences(Constant.SETTINGS_TAG, Context.MODE_PRIVATE)
		.getString(Constant.USERNAME_TAG, "");
	}
	
	public void addMessage(View msgView, String _username) {
		if (username.equals(_username)) {
			addMyMessage(msgView);
		} else {
			addOtherMessages(msgView, _username);
		}
		lastUsername = _username;
	}
	
	public void addMessage(View msgView) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View baseContainer = inflater.inflate(R.layout.message_container_view, null, false);
		View container = baseContainer.findViewById(R.id.messageContainer);
		ViewGroup centerContainer = (LinearLayout) container.findViewById(R.id.centerMessageContainer);
		centerContainer.addView(msgView);
		centerContainer.setVisibility(ViewGroup.VISIBLE);
		LinearLayout msgPoolView = (LinearLayout) findViewById(R.id.msgPoolView);
		msgPoolView.addView(baseContainer);
	}
	
	protected void addOtherMessages(View msgView, String username) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View baseContainer = inflater.inflate(R.layout.message_container_view, null, false);
		View container = baseContainer.findViewById(R.id.messageContainer);
		ViewGroup leftContainer = (LinearLayout) container.findViewById(R.id.leftMessageContainer);
		if (!lastUsername.equals(username)) {
			TextView usernameView = (TextView) baseContainer.findViewById(R.id.usernameView);
			usernameView.setText(((username.equals("")) ? "anonymous" : username) + ":");
			usernameView.setVisibility(View.VISIBLE);
		}
		leftContainer.addView(msgView);
		leftContainer.setVisibility(ViewGroup.VISIBLE);
		LinearLayout msgPoolView = (LinearLayout) findViewById(R.id.msgPoolView);
		msgPoolView.addView(baseContainer);
	}
	
	protected void addMyMessage(View msgView) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View baseContainer = inflater.inflate(R.layout.message_container_view, null, false);
		View container = baseContainer.findViewById(R.id.messageContainer);
		ViewGroup rightContainer = (LinearLayout) container.findViewById(R.id.rightMessageContainer);
		rightContainer.addView(msgView);
		rightContainer.setVisibility(ViewGroup.VISIBLE);
		LinearLayout msgPoolView = (LinearLayout) findViewById(R.id.msgPoolView);
		msgPoolView.addView(baseContainer);
	}
}
