package com.migzing.filebrowser;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.migzing.main.R;

public class FileBrowser extends Activity {

	ListView browseView;
	FileAdapter browseListAdapter;
	Button back, forward, select;
	ArrayList<ListFile> browseList;
	ArrayList<ArrayList<ListFile>> browsingHistory;
	int fileLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_browser);
		browseView = (ListView) findViewById(R.id.filebrowser_listings);
		back = (Button) findViewById(R.id.filebrowser_move_back);
		forward = (Button) findViewById(R.id.filebrowser_move_forward);

		File sdFiles = Environment.getRootDirectory();
		fileLevel = 0;
		File[] sdList = sdFiles.listFiles();
		browseList = new ArrayList<ListFile>();
		browsingHistory = new ArrayList<ArrayList<ListFile>>();
		for (int i = 0; i < sdList.length; i++) {
			ListFile a = new ListFile(sdList[i]);
			browseList.add(a);
		}
		browseListAdapter = new FileAdapter(this, browseList);
		browseView.setAdapter(browseListAdapter);
		browsingHistory.add(browseList);

		browseView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (fileLevel == browseList.size() - 1) {
					File newFile = (File) browseList.get(arg2).getFile();
					File[] newList = newFile.listFiles();
					ArrayList<ListFile> browseList = new ArrayList<ListFile>();
					for (int i = 0; i < newList.length; i++) {
						ListFile a = new ListFile(newList[i]);
						browseList.add(a);
					}
					browseListAdapter = new FileAdapter(FileBrowser.this,
							browseList);
					browseListAdapter.notifyDataSetChanged();
					browseView.setAdapter(browseListAdapter);
					browsingHistory.add(browseList);
					fileLevel = fileLevel + 1;
				} else {
					for (int i = browsingHistory.size() - 1; i > fileLevel; i--) {
						browsingHistory.remove(i);
					}
					File newFile = (File) browseList.get(arg2).getFile();
					File[] newList = newFile.listFiles();
					ArrayList<ListFile> browseList = new ArrayList<ListFile>();
					for (int i = 0; i < newList.length; i++) {
						ListFile a = new ListFile(newList[i]);
						browseList.add(a);
					}
					browseListAdapter = new FileAdapter(FileBrowser.this,
							browseList);
					browseListAdapter.notifyDataSetChanged();
					browseView.setAdapter(browseListAdapter);
					browsingHistory.add(browseList);
					fileLevel = fileLevel + 1;
				}

			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (fileLevel != 0) {
					browseListAdapter = new FileAdapter(FileBrowser.this,
							browsingHistory.get(fileLevel));
					browseListAdapter.notifyDataSetChanged();
					browseView.setAdapter(browseListAdapter);
					fileLevel = fileLevel - 1;
				}
			}
		});

		forward.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (fileLevel != browsingHistory.size()) {
					browseListAdapter = new FileAdapter(FileBrowser.this,
							browsingHistory.get(fileLevel));
					browseListAdapter.notifyDataSetChanged();
					browseView.setAdapter(browseListAdapter);
					fileLevel = fileLevel + 1;
				}

			}
		});
	}

}
