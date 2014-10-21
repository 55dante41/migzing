package com.migzing.login;

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

public class Login_OCL implements OnClickListener {

	EditText et1;
	EditText et2;
	TextView tv;

	public Login_OCL() {
		// TODO Auto-generated constructor stub
	}

	public Login_OCL(EditText arg1, EditText arg2, TextView arg3) {
		et1 = arg1;
		et2 = arg2;
		tv = arg3;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String s1 = et1.getText().toString();
		String s2 = et2.getText().toString();
		String url = "http://192.168.1.6/version3/m/login";
		NetworkThread nt = new NetworkThread();
		nt.execute(s1, s2, url);

	}

	private class NetworkThread extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			String line = "";
			String res = "";

			ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
			nvp.add(new BasicNameValuePair("username", arg0[0]));
			nvp.add(new BasicNameValuePair("passkey", arg0[1]));

			DefaultHttpClient dhp = new DefaultHttpClient();
			HttpPost hp = new HttpPost(arg0[2]);
			try {
				StringEntity se = new UrlEncodedFormEntity(nvp);
				hp.setEntity(se);
				HttpResponse hr = dhp.execute(hp);
				BufferedReader br = new BufferedReader(new InputStreamReader(hr
						.getEntity().getContent()));
				while ((line = br.readLine()) != null) {
					res = res + line;
				}

				System.out.println("debug: " + res);

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return res;
		}

		protected void onPostExecute(String arg1) {
			JSONObject jO;
			try {
				jO = new JSONObject(arg1);
				if (jO.get("success").equals("true")) {
					tv.setText("Connection Successful");
				} else {
					tv.setText((CharSequence) jO.get("message"));
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
