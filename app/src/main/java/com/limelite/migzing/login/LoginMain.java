package com.limelite.migzing.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.migzing.main.R;

public class LoginMain extends Activity {

	EditText fieldUser, fieldPassword;
	Button doLogin;
	TextView loginStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		fieldUser = (EditText) findViewById(R.id.loginfield_user);
		fieldPassword = (EditText) findViewById(R.id.loginfield_password);
		doLogin = (Button) findViewById(R.id.doLogin);
		loginStatus = (TextView) findViewById(R.id.feedback_login);

		Login_OCL locl = new Login_OCL(fieldUser, fieldPassword, loginStatus);
		doLogin.setOnClickListener(locl);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
