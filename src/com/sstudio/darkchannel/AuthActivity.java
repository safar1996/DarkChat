package com.sstudio.darkchannel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import com.sstudio.darkchannel.config.Constant;
import android.content.Context;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;


public class AuthActivity extends Activity implements View.OnClickListener {
	
	private EditText usernameInput;
	private EditText passwordInput;
	private Button loginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (checkAuthExist() == true) {
			redirect();
		}
		setContentView(R.layout.login);
		usernameInput = (EditText) findViewById(R.id.login_username);
		passwordInput = (EditText) findViewById(R.id.login_password);
		loginButton = (Button) findViewById(R.id.login_btn);
		loginButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.login_btn:
				String username = usernameInput.getText().toString();
				String password = passwordInput.getText().toString();
				processLogin(username, password);
				break;
		}
	}
	
	private void processLogin(String username, String password) {
		// TODO: verifiy username and password
		getSharedPreferences(Constant.SETTINGS_TAG, Context.MODE_PRIVATE).edit()
		.putString(Constant.USERNAME_TAG, username)
		.commit();
		redirect();
	}
	
	private void redirect() {
		Intent intent = new Intent(this, AppLockActivity.class);
		startActivity(intent);
		finish();
	}
	
	private boolean checkAuthExist() {
		String username = getSharedPreferences(Constant.SETTINGS_TAG, Context.MODE_PRIVATE)
		.getString(Constant.USERNAME_TAG, null);
		return username != null;
	}
	
}
