package com.migzing.filebrowser;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filterable;
import android.widget.TextView;

import com.migzing.customizations.FontAwesomeTextView;
import com.migzing.main.R;

public class FileAdapter extends ArrayAdapter<ListFile> implements Filterable,
		OnCheckedChangeListener {

	List<ListFile> fileList, filteredFileList, selectedList;
	SparseBooleanArray isSelectedList, isFilteredSelectedList;
	Context context;
	FontAwesomeTextView fileIcon;
	TextView fileName;
	CheckBox selectThisFile;

	public FileAdapter(Context c, List<ListFile> objects) {
		super(c, R.layout.file_browser_listitem, objects);
		this.context = c;
		this.fileList = objects;
		this.filteredFileList = objects;
		this.selectedList = new ArrayList<ListFile>();
		this.isSelectedList = new SparseBooleanArray(objects.size());
		this.isFilteredSelectedList = new SparseBooleanArray(objects.size());
	}

	public class ListFileHolder {
		public TextView fileNameHolder;
		public CheckBox selectionHolder;
	}

	public int getCount() {
		return filteredFileList.size();
	}

	public int getSelectionsCount() {
		return selectedList.size();
	}

	public ListFile getItem(int position) {
		return filteredFileList.get(position);
	}

	public void resetFilter() {
		filteredFileList = fileList;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		ListFileHolder holder;

		if (v == null) {
			holder = new ListFileHolder();
			LayoutInflater itemInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = itemInflater.inflate(R.layout.file_browser_listitem, parent,
					false);
			fileName = (TextView) v
					.findViewById(R.id.filebrowser_listitem_name);
			selectThisFile = (CheckBox) v
					.findViewById(R.id.filebrowser_listitem_checkbox);

			holder.fileNameHolder = fileName;
			holder.selectionHolder = selectThisFile;

			v.setTag(holder);
		} else {
			holder = (ListFileHolder) v.getTag();
		}

		fileIcon = (FontAwesomeTextView) v
				.findViewById(R.id.filebrowser_listitem_icon);

		ListFile aFile = filteredFileList.get(position);
		if (aFile.isFile()) {
			fileIcon.setText(context.getString(R.string.fontawesomeicon_file));
		}
		if (aFile.isDirectory()) {
			fileIcon.setText(context
					.getString(R.string.fontawesomeicon_directory));
		}
		holder.fileNameHolder.setText(aFile.getFileName());
		holder.selectionHolder.setTag(position);
		holder.selectionHolder.setChecked(isSelectedList.get(position, false));
		holder.selectionHolder.setOnCheckedChangeListener(this);
		return v;
	}

	public boolean isChecked(int position) {
		return isSelectedList.get(position, false);
	}

	public void setChecked(int position, boolean isChecked) {
		isSelectedList.put(position, isChecked);
	}

	public void toggle(int position) {
		isSelectedList.put(position, !isSelectedList.get(position, false));
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		isSelectedList.put((Integer) arg0.getTag(), arg1);
		if (arg1) {
			Log.d("MESSAGE", "Item added to checklist");
			selectedList.add(fileList.get((Integer) arg0.getTag()));
		} else {
			Log.d("MESSAGE", "Item removed from checklist");
			selectedList.remove(fileList.get((Integer) arg0.getTag()));
		}
	}
	
	public ArrayList<ListFile> getSelected() {
		return (ArrayList<ListFile>) selectedList;
	}

	

}
