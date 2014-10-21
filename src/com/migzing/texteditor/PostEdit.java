package com.migzing.texteditor;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class PostEdit extends EditText {

	public interface onSelectionChangedListener {
		public void onSelectionChanged(int selStart, int selEnd);
	}

	private List<onSelectionChangedListener> listeners;

	public int cursorPosition;

	public PostEdit(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		listeners = new ArrayList<onSelectionChangedListener>();

	}

	public PostEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		listeners = new ArrayList<onSelectionChangedListener>();
	}

	public PostEdit(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		listeners = new ArrayList<onSelectionChangedListener>();
	}

	public void addOnSelectionChangedListener(onSelectionChangedListener o) {
		listeners.add(o);
	}

	@Override
	protected void onSelectionChanged(int selStart, int selEnd) {
		// TODO Auto-generated method stub
		// super.onSelectionChanged(selStart, selEnd);
		if (listeners != null) {
			for (onSelectionChangedListener a : listeners) {
				a.onSelectionChanged(selStart, selEnd);

			}
		} else {
			listeners = new ArrayList<onSelectionChangedListener>();
		}

	}

}
