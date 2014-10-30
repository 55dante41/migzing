// STATUS			: INCOMPLETE
// FUNCTIONALITY	: WORKING
// DEBUGGING		: PENDING

package com.limelite.migzing.texteditor;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

public class Italic_OCL implements OnClickListener {

	// ---------------------------------------------------------------------------------------------------
	// Fields START
	PostEdit inputEditor;
	ToggleButton toggleItalic;

	// Fields END
	// ---------------------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------------------
	// Constructors START
	public Italic_OCL() {
		// Default Empty Constructor
	}

	public Italic_OCL(PostEdit pe, ToggleButton tb) {
		// Class/Object Constructor
		inputEditor = pe;
		toggleItalic = tb;
	}

	// Constructors END
	// ---------------------------------------------------------------------------------------------------

	// ---------------------------------------------------------------------------------------------------
	// Methods START
	@Override
	public void onClick(View v) {
		int selectedStart = inputEditor.getSelectionStart();
		int selectedEnd = inputEditor.getSelectionEnd();
		if (selectedStart == selectedEnd) {
			if (!toggleItalic.isChecked()) {
				StyleSpan[] sStyles = inputEditor.getText().getSpans(0,
						selectedStart, StyleSpan.class);
				for (StyleSpan aSpan : sStyles) {
					int startStyle = inputEditor.getText().getSpanStart(aSpan);
					int endStyle = inputEditor.getText().getSpanEnd(aSpan);
					if (selectedStart > startStyle && selectedEnd <= endStyle) {
						if (aSpan.getStyle() == Typeface.ITALIC) {
							Editable editing = inputEditor.getText();
							editing.removeSpan(aSpan);

							editing.setSpan(new StyleSpan(Typeface.ITALIC),
									startStyle, selectedStart,
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

							editing.setSpan(new StyleSpan(Typeface.ITALIC),
									selectedEnd, endStyle,
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

						}
					}
				}
			}
		} else {
			if (toggleItalic.isChecked()) {
				SpannableString k = new SpannableString(inputEditor.getText());
				k.setSpan(new StyleSpan(Typeface.ITALIC), selectedStart,
						selectedEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				inputEditor.setText(k);
				inputEditor.setSelection(selectedEnd);

			} else {
				StyleSpan[] sStyles = inputEditor.getText().getSpans(
						selectedStart, selectedEnd, StyleSpan.class);
				for (StyleSpan aSpan : sStyles) {
					if (aSpan.getStyle() == Typeface.ITALIC) {
						int startStyle = inputEditor.getText().getSpanStart(
								aSpan);
						int endStyle = inputEditor.getText().getSpanEnd(aSpan);
						inputEditor.getText().removeSpan(aSpan);
						if (startStyle < selectedStart) {
							inputEditor.getText().setSpan(
									new StyleSpan(Typeface.ITALIC), startStyle,
									selectedStart,
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						}
						if (endStyle > selectedEnd) {
							inputEditor.getText().setSpan(
									new StyleSpan(Typeface.ITALIC),
									selectedEnd, endStyle,
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						}

					}
				}

			}
		}

	}
	// Methods END
	// ---------------------------------------------------------------------------------------------------

}
