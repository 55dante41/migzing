package com.limelite.migzing.register;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class Register_OCL implements OnClickListener {

	EditText et1, et2, et3, et4;
	TextView tv;

	public Register_OCL() {
		// Default Constructor
		// Currently no use...
	}

	public Register_OCL(EditText arg1, EditText arg2, EditText arg3,
			EditText arg4, TextView arg5) {
		// Object Cosntructor
		et1 = arg1;
		et2 = arg2;
		et3 = arg3;
		et4 = arg4;
		tv = arg5;
	}

	@Override
	public void onClick(View arg0) {
		// Get all Strings from the text fields
		String s1 = et1.getText().toString();
		String s2 = et2.getText().toString();
		String s3 = et3.getText().toString();
		String s4 = et4.getText().toString();
		String url = "http://192.168.1.12/version3/m/register";

		NetworkThread nt = new NetworkThread();
		nt.execute(s1, s2, s3, s4, url);

	}

	private class NetworkThread extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {

			String s1 = "";
			String s2 = "";
			// TODO Auto-generated method stub
			// Prepare a NameValuePair List and add the mapped key-values
			ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
			nvp.add(new BasicNameValuePair("fullname", arg0[0]));
			nvp.add(new BasicNameValuePair("email", arg0[1]));
			nvp.add(new BasicNameValuePair("username", arg0[2]));
			nvp.add(new BasicNameValuePair("passkey", arg0[3]));

			// Prepare the HttpClient, HttpPost and Entity Objects
			// for sending POST request and execute the request

			try {
				DefaultHttpClient dhc = new DefaultHttpClient();
				HttpPost hp = new HttpPost(arg0[4]);
				StringEntity se = new UrlEncodedFormEntity(nvp);
				hp.setEntity(se);
				HttpResponse hr = dhc.execute(hp);
				BufferedReader br = new BufferedReader(new InputStreamReader(hr
						.getEntity().getContent()));

				while ((s1 = br.readLine()) != null) {
					s2 = s2 + s1;
				}

				// Debugging
				System.out.println(s2);

				// Wrapping Response in form of JSON and
				// get the values of corresponding keys

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return s2;
		}

		protected void onPostExecute(String arg1) {
			try {
				JSONObject jO = new JSONObject(arg1);
				tv.setText((CharSequence) jO.get("message"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
