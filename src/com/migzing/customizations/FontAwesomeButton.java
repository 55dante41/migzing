package com.migzing.customizations;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

public class FontAwesomeButton extends Button {

	private static LruCache<String, Typeface> fontawesomeCache;

	public FontAwesomeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		fontawesomeCache = new LruCache<String, Typeface>(12);
		try {
			String typefaceName = "fonts/fontawesome.ttf";
			if (!isInEditMode() && !TextUtils.isEmpty(typefaceName)) {
				Typeface fontawesomeObject = fontawesomeCache.get(typefaceName);
				if (fontawesomeObject == null) {
					fontawesomeObject = Typeface.createFromAsset(
							context.getAssets(), typefaceName);
					fontawesomeCache.put(typefaceName, fontawesomeObject);
				}
				setTypeface(fontawesomeObject);
				setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("DEBUG", "fontawesome EXCEPTION !!!");
		}

	}

}
