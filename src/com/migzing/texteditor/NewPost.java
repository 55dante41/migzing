package com.migzing.texteditor;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.migzing.main.R;
import com.migzing.texteditor.PostEdit.onSelectionChangedListener;

public class NewPost extends FragmentActivity {

	// Declare fields
	PostEdit editorPostBody;
	TextView identifierPostHeading, identifierPostSubheading,
			identifierPostBody;
	ToggleButton toggleBold, toggleItalic;
	Button toggleLink;
	Button saveText, insertImage;
	boolean isRemoved = false, cursorCheckedBold = false,
			cursorCheckedItalic = false;
	ArrayList<Bitmap> allImages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.texteditor_screen);

		// Associate fields to views
		editorPostBody = (PostEdit) findViewById(R.id.post_body_texteditor);

		toggleBold = (ToggleButton) findViewById(R.id.button1);
		toggleItalic = (ToggleButton) findViewById(R.id.button3);
		toggleLink = (Button) findViewById(R.id.button5);
		saveText = (Button) findViewById(R.id.button2);
		insertImage = (Button) findViewById(R.id.button4);

		// Define Listeners
		// OnClick - Bold,Italic and Save; OnSelectionChanged; OnTextChanged
		Bold_OCL bocl = new Bold_OCL(editorPostBody, toggleBold);
		Italic_OCL iocl = new Italic_OCL(editorPostBody, toggleItalic);
		Save_OCL socl = new Save_OCL(editorPostBody);
		InsertImageListener il = new InsertImageListener(NewPost.this,
				editorPostBody, getSupportFragmentManager());
		Link_OCL locl = new Link_OCL(NewPost.this, editorPostBody);
		onSelectionChangedListener cursorMonitor = new onSelectionChangedListener() {
			@Override
			public void onSelectionChanged(int selStart, int selEnd) {
				// TODO Auto-generated method stub
				if (selStart == selEnd) {
					StyleSpan[] sSpans = editorPostBody.getText().getSpans(0,
							editorPostBody.getText().length(), StyleSpan.class);
					for (StyleSpan pp : sSpans) {

						if (pp.getStyle() == Typeface.BOLD
								&& !cursorCheckedBold) {

							int startBold = editorPostBody.getText()
									.getSpanStart(pp);
							int endBold = editorPostBody.getText().getSpanEnd(
									pp);
							if (selStart > startBold && selEnd <= endBold) {
								toggleBold.setChecked(true);
								cursorCheckedBold = true;
							} else {
								toggleBold.setChecked(false);
							}
						}

						if (pp.getStyle() == Typeface.ITALIC
								&& !cursorCheckedItalic) {

							int startItalic = editorPostBody.getText()
									.getSpanStart(pp);

							int endItalic = editorPostBody.getText()
									.getSpanEnd(pp);
							if (selStart > startItalic && selEnd <= endItalic) {
								toggleItalic.setChecked(true);
								cursorCheckedItalic = true;
							} else {
								toggleItalic.setChecked(false);
							}
						}

					}
					cursorCheckedBold = false;
					cursorCheckedItalic = false;
				}
			}
		};
		TextWatcher textEditorWatcher = new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (toggleBold.isChecked() && !isRemoved) {
					s.setSpan(new StyleSpan(Typeface.BOLD),
							editorPostBody.getSelectionStart() - 1,
							editorPostBody.getSelectionStart(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				} else {

				}

				if (toggleItalic.isChecked() && !isRemoved) {
					s.setSpan(new StyleSpan(Typeface.ITALIC),
							editorPostBody.getSelectionStart() - 1,
							editorPostBody.getSelectionStart(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				} else {

				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (before > count) {
					isRemoved = true;
				} else if (before < count) {
					isRemoved = false;
				} else {

				}
			}
		};

		// Assign Listeners to the fields (views)
		editorPostBody.addOnSelectionChangedListener(cursorMonitor);
		editorPostBody.addTextChangedListener(textEditorWatcher);
		toggleBold.setOnClickListener(bocl);
		toggleItalic.setOnClickListener(iocl);
		toggleLink.setOnClickListener(locl);
		saveText.setOnClickListener(socl);
		insertImage.setOnClickListener(il);

	}
}
