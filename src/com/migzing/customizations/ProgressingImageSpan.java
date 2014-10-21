package com.migzing.customizations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ImageSpan;
import android.view.ViewGroup;

public class ProgressingImageSpan extends ImageSpan {
	
	ViewGroup ax;

	public ProgressingImageSpan(Context context, Bitmap b, ViewGroup vg) {
		super(context, b);
		ax = vg;
	}

	@Override
	public void draw(Canvas canvas, CharSequence text, int start, int end,
			float x, int top, int y, int bottom, Paint paint) {
		super.draw(canvas, text, start, end, x, top, y, bottom, paint);
		ax.draw(canvas);
	}
	
	

}
