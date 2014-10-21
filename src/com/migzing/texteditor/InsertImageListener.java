package com.migzing.texteditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;

import com.migzing.filebrowser.BrowseMemoryDialogFragment;
import com.migzing.main.R;

public class InsertImageListener implements OnClickListener {

	PostEdit postBodyEditor;
	Bitmap imageToInsert;
	Context context;
	FragmentManager fragManager;

	public InsertImageListener() {
		// Default Empty Constructor
	}

	public InsertImageListener(Context c, PostEdit pe, FragmentManager fm) {
		// Class/Object Constructor
		postBodyEditor = pe;
		context = c;
		fragManager = fm;
	}

	@Override
	public void onClick(View v) {
		// Behaviour when the associated view is clicked
		boolean fromMemory = false;
		if (!fromMemory) {
			imageToInsert = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.ic_launcher);
			ImageSpan iSpan = new ImageSpan(context, imageToInsert);
			Editable bodyContent = postBodyEditor.getText();

			if (postBodyEditor.getSelectionStart() == postBodyEditor
					.getSelectionEnd()
					&& postBodyEditor.getSelectionEnd() < postBodyEditor
							.length()) {
				SpannableStringBuilder bodyBuilder = new SpannableStringBuilder();
				Spannable contentBeforeImg = (Spannable) bodyContent.subSequence(
						0, postBodyEditor.getSelectionStart());
				Spannable contentAfterImg = (Spannable) bodyContent.subSequence(
						postBodyEditor.getSelectionEnd(),
						bodyContent.length() - 1);
				String tag = "[inline_image_]";
				bodyBuilder.append(contentBeforeImg);
				bodyBuilder.append("\n" + tag + "\n");
				bodyBuilder.append(contentAfterImg);
				bodyBuilder.setSpan(iSpan, contentBeforeImg.length() + 1,
						contentBeforeImg.length() + tag.length() + 1,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

				// bodyContent.setSpan(new AlignmentSpan.Standard(
				// Alignment.ALIGN_CENTER), postBodyEditor
				// .getSelectionStart(),
				// postBodyEditor.getSelectionEnd() + 1,
				// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				postBodyEditor.setText(bodyBuilder);

			} else {
				// Display Error as image cannot be inserted over a selection
				System.out.println("Nope! Cannot do that!!");
			}
		} else {
			browseImageMemory();
		}
	}

	public void browseImageMemory() {
		// Check if the external memory is mounted
		String externalMountState = Environment.getExternalStorageState();
		if (externalMountState.equals(Environment.MEDIA_MOUNTED)) {
			// Get all the files in the external memory
			// File sdFiles = Environment.getExternalStorageDirectory();
			// File[] sdList = sdFiles.listFiles();
			DialogFragment browseDialog = new BrowseMemoryDialogFragment();
			browseDialog.show(fragManager, "file_browser");

		} else {
			// Display Error
			System.out.println("No mounts dude");
		}
	}

}
