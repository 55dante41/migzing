package com.limelite.migzing.register;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.migzing.main.R;

public class RegisterMain extends Activity {

	EditText fieldName, fieldEmail, fieldPass, fieldUser;
	Button doRegister;
	TextView registrationStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_screen);

		// Connect all views in the layout to the
		// corresponding objects in this activity
		fieldName = (EditText) findViewById(R.id.registerfield_name);
		fieldEmail = (EditText) findViewById(R.id.registerfield_email);
		fieldUser = (EditText) findViewById(R.id.registerfield_user);
		fieldPass = (EditText) findViewById(R.id.registerfield_password);
		doRegister = (Button) findViewById(R.id.doRegister);
		registrationStatus = (TextView) findViewById(R.id.feedback_register);

		// Set the corresponding Listeners and Action Events
		Register_OCL rocl = new Register_OCL(fieldName, fieldEmail, fieldUser,
				fieldPass, registrationStatus);
		doRegister.setOnClickListener(rocl);

	}

}
