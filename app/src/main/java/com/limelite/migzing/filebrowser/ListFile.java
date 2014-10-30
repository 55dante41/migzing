package com.limelite.migzing.filebrowser;

import java.io.File;

public class ListFile {

	File coreFile;
	String fileName;
	String filePath;

	public ListFile(File file) {
		coreFile = file;
		fileName = coreFile.getName();
		filePath = coreFile.getPath();
	}

	public File getFile() {
		return coreFile;
	}

	public String getFileName() {
		return fileName;
	}

	public String getPath() {
		return filePath;
	}

	public boolean isFile() {
		return coreFile.isFile();
	}

	public boolean isDirectory() {
		return coreFile.isDirectory();
	}

}
