package com.limelite.migzing.filebrowser;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.migzing.main.R;

public class BrowseMemoryDialogFragment extends DialogFragment {
	// Declare all views/fields/objects in the fragment
	LayoutInflater inflater;
	View dialogLayout;
	Button viewPrevious, viewNext;
	CheckBox selectThis;
	EditText searchListings;
	ListView viewerListings;
	FileAdapter viewerAdapter;
	String filePath = "";
	ArrayList<ListFile> browseList;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Get a LayoutInflater for the activity
		inflater = getActivity().getLayoutInflater();

		// Inflate the required Layout using the inflater into a View
		dialogLayout = inflater.inflate(R.layout.file_browser, null);

		// Get the child views/fields of the layout/view and define
		// corresponding dependancies
		viewNext = (Button) dialogLayout
				.findViewById(R.id.filebrowser_move_forward);
		viewPrevious = (Button) dialogLayout
				.findViewById(R.id.filebrowser_move_back);
		searchListings = (EditText) dialogLayout
				.findViewById(R.id.filebrowser_search_bar);
		viewerListings = (ListView) dialogLayout
				.findViewById(R.id.filebrowser_listings);

		File sdFiles = Environment.getRootDirectory();
		File[] sdList = sdFiles.listFiles();
		browseList = new ArrayList<ListFile>();
		for (int i = 0; i < sdList.length; i++) {
			ListFile a = new ListFile(sdList[i]);
			browseList.add(a);
		}
		viewerAdapter = new FileAdapter(getActivity(), browseList);
		viewerListings.setAdapter(viewerAdapter);
		viewerListings.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("Got here!");
				if (browseList.get(arg2).getFile().isDirectory()) {
					File[] sdList = browseList.get(arg2).getFile().listFiles();
					System.out.println(sdList.length);
					browseList = new ArrayList<ListFile>();
					for (int i = 0; i < sdList.length && sdList.length != 0; i++) {
						ListFile a = new ListFile(sdList[i]);
						browseList.add(a);
					}
					viewerAdapter = new FileAdapter(getActivity(), browseList);
					viewerAdapter.notifyDataSetChanged();
					viewerListings.setAdapter(viewerAdapter);
				} 
			}

		});

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(dialogLayout)
				.setTitle("Choose a file...")
				.setPositiveButton("Select",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ListFile selectedFile = getSelectedPaths();
								Log.d("MESSAGE", selectedFile.filePath);
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});

		viewPrevious.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("HEHEHE!!!!!!!!1");
			}
		});

		viewNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("HEHEHE!!!!!!!!1");
			}
		});
		return builder.create();

	}

	public ListFile getSelectedPaths() {
		return viewerAdapter.getSelected().get(0);
	}

}
