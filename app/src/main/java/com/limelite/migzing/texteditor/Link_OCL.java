package com.limelite.migzing.texteditor;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Link_OCL implements OnClickListener {

	Context context;
	EditText editor;
	int selectionStart, selectionEnd;
	String urlLink;
	Editable textContent;

	public Link_OCL(Context ctx, EditText et) {
		context = ctx;
		editor = et;
	}

	@Override
	public void onClick(View v) {
		selectionStart = editor.getSelectionStart();
		selectionEnd = editor.getSelectionEnd();
		textContent = editor.getText();
		urlLink = "http://www.google.co.in";
		if (selectionStart == selectionEnd) {
			Log.d("MESSAGE",
					"Requested action not possible with empty selection");
		} else {
			SpannableString s = new SpannableString(textContent);
			URLSpan[] existingSpans = s.getSpans(selectionStart, selectionEnd,
					URLSpan.class);
			if (existingSpans.length == 0) {
				Log.d("MESSAGE", "No existing URLSpans in the selection");
				s.setSpan(new URLSpan(urlLink), selectionStart, selectionEnd,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				editor.setText(s);
			} else {
				Log.d("MESSAGE", "URLSpan already exists in the selection");
				for (URLSpan anURLSpan : existingSpans) {
					textContent.removeSpan(anURLSpan);
				}
			}
		}

	}

}
