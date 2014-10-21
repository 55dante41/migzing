package com.migzing.texteditor;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.Html;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;

public class Save_OCL implements OnClickListener {

	PostEdit editorPostBody;
	String postHtmlContent;
	int count;
	ArrayList<JSONObject> urlJSON;
	int ailength;

	public Save_OCL() {
		// TODO Auto-generated constructor stub
	}

	public Save_OCL(PostEdit body) {
		editorPostBody = body;

	}

	@Override
	public void onClick(View v) {
		
		urlJSON = new ArrayList<JSONObject>();
		// TODO Auto-generated method stub
		Editable editorPostBodyContent = editorPostBody.getText();
		System.out.println(Html.toHtml(editorPostBodyContent));
		ImageSpan[] allImages = editorPostBodyContent.getSpans(0,
				editorPostBodyContent.length() - 1, ImageSpan.class);
		ailength = allImages.length;
		count = 0;
		for (ImageSpan aSpan : allImages) {
			count++;
			Bitmap aBitmap = ((BitmapDrawable) aSpan.getDrawable()).getBitmap();
			PostImageUploadTask uploader = new PostImageUploadTask(count);
			uploader.execute(aBitmap);
		}

	}

	public class PostImageUploadTask extends AsyncTask<Bitmap, Void, String> {

		int count;

		public PostImageUploadTask(int c) {
			count = c;
		}

		@Override
		protected String doInBackground(Bitmap... params) {
			// TODO Auto-generated method stub

			String line = "";
			String res = "";

			try {
				DefaultHttpClient dhc = new DefaultHttpClient();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				params[0].compress(CompressFormat.JPEG, 100, bos);
				byte[] imageData = bos.toByteArray();
				String imgdata = Base64.encodeToString(imageData,
						Base64.DEFAULT);
				HttpPost hp = new HttpPost(
						"http://192.168.1.8/version3/m/upload");
				MultipartEntityBuilder mpe = MultipartEntityBuilder.create();
				mpe.addTextBody("bytearray", imgdata);
				mpe.addTextBody("imageid", Integer.valueOf(count).toString());
				ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
				nvp.add(new BasicNameValuePair("bytearray", imageData
						.toString()));
				nvp.add(new BasicNameValuePair("imageid", Integer
						.valueOf(count).toString()));
				hp.setEntity(mpe.build());
				HttpResponse hr = dhc.execute(hp);
				BufferedReader br = new BufferedReader(new InputStreamReader(hr
						.getEntity().getContent()));
				while ((line = br.readLine()) != null) {
					res = res + line;
				}

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
			System.out.println(res);
			return res;
		}

		protected void onPostExecute(String res) {
			try {
				JSONObject jO = new JSONObject(res);
				System.out.println(jO.get("imgsrc").toString());
				urlJSON.add(jO);

				if (urlJSON.size() == ailength) {
					int i = 0;
					postHtmlContent = Html.toHtml(editorPostBody.getText())
							.replace("</b><b>", "").replace("</i><i>", "");
					Document postHtmlBuild = Jsoup.parse(postHtmlContent);
					Elements imgElements = postHtmlBuild
							.getElementsByTag("img");
					for (Element eachImgElement : imgElements) {

						String str = eachImgElement.toString();
						System.out.println("BEFORE:");
						System.out.println(str);
						str = urlJSON.get(i).get("imgsrc").toString();
						System.out.println("AFTER :");
						System.out.println(str);
						eachImgElement = eachImgElement.attr("src", str);
						i++;
					}
					postHtmlContent = postHtmlBuild.toString()
							.replace("&gt;", ">").replace("&lt;", "<");
					System.out.println(postHtmlContent);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
