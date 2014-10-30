package com.limelite.migzing.texteditor;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

public class Bold_OCL implements OnClickListener {

	PostEdit editor;
	ToggleButton setBold;

	public Bold_OCL() {
		// TODO Auto-generated constructor stub
	}

	public Bold_OCL(PostEdit pe, ToggleButton tb) {
		editor = pe;
		setBold = tb;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		int selectedStart = editor.getSelectionStart();
		int selectedEnd = editor.getSelectionEnd();
		if (selectedStart == selectedEnd) {
			if (!setBold.isChecked()) {
				StyleSpan[] sStyles = editor.getText().getSpans(0,
						selectedStart, StyleSpan.class);
				for (StyleSpan aSpan : sStyles) {
					int startStyle = editor.getText().getSpanStart(aSpan);
					int endStyle = editor.getText().getSpanEnd(aSpan);
					if (selectedStart > startStyle && selectedEnd <= endStyle) {
						if (aSpan.getStyle() == Typeface.BOLD) {
							Editable editing = editor.getText();
							editing.removeSpan(aSpan);

							editing.setSpan(new StyleSpan(Typeface.BOLD),
									startStyle, selectedStart,
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

							editing.setSpan(new StyleSpan(Typeface.BOLD),
									selectedEnd, endStyle,
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

						}
					}
				}
			}
		} else {
			if (setBold.isChecked()) {
				SpannableString k = new SpannableString(editor.getText());
				k.setSpan(new StyleSpan(Typeface.BOLD), selectedStart,
						selectedEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				editor.setText(k);
				editor.setSelection(selectedEnd);

			} else {
				StyleSpan[] sStyles = editor.getText().getSpans(selectedStart,
						selectedEnd, StyleSpan.class);
				for (StyleSpan aSpan : sStyles) {
					if (aSpan.getStyle() == Typeface.BOLD) {
						int startStyle = editor.getText().getSpanStart(aSpan);
						int endStyle = editor.getText().getSpanEnd(aSpan);
						editor.getText().removeSpan(aSpan);
						if (startStyle < selectedStart) {
							editor.getText().setSpan(
									new StyleSpan(Typeface.BOLD), startStyle,
									selectedStart,
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

						}
						if (endStyle > selectedEnd) {
							editor.getText().setSpan(
									new StyleSpan(Typeface.BOLD), selectedEnd,
									endStyle,
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						}

					}
				}

			}
		}

	}

}
