package de.dc.fx.spring.wiki.ui.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public abstract class BaseFolderUtil<T> {
	
	public static final File rootFolder = new File("");
	public static final File contentFolder = new File(rootFolder.getAbsoluteFile() + "/content");
	public File resFolder;
	
	protected abstract String resFolderName();
	
	public abstract File getFolderBy(T t);
	public abstract File createFolder(T t);
	
	public File[] getFolderContent(T t) {
		return getFolderBy(t).listFiles();
	}
	
	public void createIfNotExist() {
		existFolder(contentFolder);
		existFolder(getResFolder());
	}
	
	protected File getResFolder() {
		if (resFolder ==null) {
			resFolder = new File(contentFolder.getAbsolutePath()+ "/" + resFolderName());
		}
		return resFolder;
	}
	
	public File getFolderBy(Long id) {
		String number = String.format ("%016d", id);
		File folder = new File(contentFolder.getAbsolutePath()+ "/" + resFolderName() + "/" + number);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return folder;
	}
	
	public String getFolderPathBy(T t) {
		return getFolderBy(t).getAbsolutePath();
	}
	
	public File createFolder(long id) {
		String number = String.format ("%016d", id);
		File folder = new File(contentFolder.getAbsolutePath()+ "/" + resFolderName() + "/" + number);
		folder.mkdirs();
		return folder;
	}
	
	public void deleteFolderWithContent(T t) {
		deleteFolderWithContent(getFolderBy(t));
	}
	
	public void deleteFolderWithContent(File folder) {
		try {
			FileUtils.deleteDirectory(folder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteFile(String filePath) {
		try {
			FileUtils.forceDelete(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void existFolder(File folder) {
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}
	
	public void copyFileTo(File sourceFile, File destinationFile) {
		try {
			if (!sourceFile.getAbsolutePath().equals(destinationFile.getAbsolutePath())) {
				FileUtils.copyFile(sourceFile, destinationFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
