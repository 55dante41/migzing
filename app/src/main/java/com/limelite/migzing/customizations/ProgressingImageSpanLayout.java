package com.limelite.migzing.customizations;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class ProgressingImageSpanLayout extends LinearLayout {

	Button pb;

	public ProgressingImageSpanLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.VERTICAL);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		pb = new Button(context);
		pb.setText("HAHAHA");
		pb.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		addView(pb);
	}

}
