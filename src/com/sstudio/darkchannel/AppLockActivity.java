package com.sstudio.darkchannel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import com.sstudio.darkchannel.config.Constant;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;


public class AppLockActivity extends Activity {
	
	protected static final short NEW_KEY = 0;
	protected static final short VERIFY_KEY = 1;
	protected static final short MAX_KEY = 4;
	private short action = NEW_KEY;
	private TextView[] keys;
	private short currentIndexKey = 0;
	private StringBuilder inputValueKeys = new StringBuilder(4);
	private String existKey;
	private String firstKeys;
	private String secondKeys;
	private short inputTimes = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_lock);
		setup();
	}
		
	protected void setup() {
		keys = new TextView[4];
		keys[0] = (TextView) findViewById(R.id.app_lock_key1);
		keys[1] = (TextView) findViewById(R.id.app_lock_key2);
		keys[2] = (TextView) findViewById(R.id.app_lock_key3);
		keys[3] = (TextView) findViewById(R.id.app_lock_key4);
		if ((existKey = getExistKey()) != null) {
			action = VERIFY_KEY;
		} else {
			String message = getResources().getString(R.string.create_new_key_msg);
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}
	}
	
	protected String getExistKey() {
		return getSharedPreferences(Constant.SETTINGS_TAG, Context.MODE_PRIVATE)
		       .getString(Constant.APP_LOCK_CODE_TAG, null);
	}
	
	public void processButton(View button) {
		if (button instanceof Button) {
			keys[currentIndexKey].setText("*");
			Button btn = (Button) button;
			inputValueKeys.append(btn.getText());
			currentIndexKey++;
			if (currentIndexKey == MAX_KEY) {
				onDone();
			}
		}
	}
	
	protected void onDone() {
		if (action == VERIFY_KEY) {
			if (inputValueKeys.toString().equals(existKey)) {
				redirect();
			} else {
				onWrongKey();
			}
		} else {
			inputTimes++;
			if (inputTimes < 2) {
				firstKeys = inputValueKeys.toString();
				confirmNewKey();
			} else {
				secondKeys = inputValueKeys.toString();
				if (firstKeys.equals(secondKeys)) {
					saveNewKey(secondKeys);
					redirect();
				} else {
					onMismatchNewKey();
				}
			}
		}
	}
	
	protected void redirect() {
		Intent intent = new Intent(this, ChatRoomActivity.class);
		startActivity(intent);
		finish();
	}
	
	protected void confirmNewKey() {
		String message = getResources().getString(R.string.confirm_new_key_msg);
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		resetKeys();
	}
	
	protected void saveNewKey(String key) {
		getSharedPreferences(Constant.SETTINGS_TAG, Context.MODE_PRIVATE)
		.edit().putString(Constant.APP_LOCK_CODE_TAG, key)
		.commit();
	}
	
	private void resetKeys() {
		currentIndexKey = 0;
		inputValueKeys.delete(0, 4);
		for (TextView key : keys) {
			key.setText("-");
		}
	}
	
	protected void onWrongKey() {
		resetKeys();
	}
	
	protected void onMismatchNewKey() {
		String message = getResources().getString(R.string.mismatch_new_key_msg);
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		inputTimes = 0;
		resetKeys();
	}
}
