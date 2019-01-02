/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package filesystem;

import java.util.ArrayList;

/**This class implements the behavior of concrete directories.
 * Composite-Pattern: Composite
 * 
 * Responsibilities:
 * - defines behavior for components (directories) having children. 
 * - stores child components (files and sub-directories). 
 * - implements child-related operations in the Component interface. These are:
 *   - getContent()
 *   - add(Directory), add(File)
 *   - getNumberOfFiles(), getNumberOfDirectories()
 */
public class Directory extends FileSystemItem {

	private ArrayList<FileSystemItem> content;

	public Directory(String name) {
		super(name, null);
		this.content = new ArrayList<FileSystemItem>();
	}

	@Override
	public ArrayList<FileSystemItem> getContent() {
		return this.content;
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

	public void add(Directory directoryToAdd) {
		this.content.add(directoryToAdd);
		if(hasAnotherParent(directoryToAdd)) {
			removeParent(directoryToAdd);
		}
		directoryToAdd.setParent(this);
	}

	public void add(File fileToAdd) {
		this.content.add(fileToAdd);
		if(hasAnotherParent(fileToAdd)) {
			removeParent(fileToAdd);
		}
		fileToAdd.setParent(this);
	}

	private static boolean removeParent(FileSystemItem item) {
		return item.getParent().content.remove(item);
	}

	private static boolean hasAnotherParent(FileSystemItem item) {
		return item.getParent() != null;
	}
	
	/**Removes a directory or a file from current directory.
	 * Note: If you need to remove the entire content, you cannot use
	 * an iterator since you change the list, the iterator is enumerating. 
	 * Use this code instead:
	 * while(root.getContent().size() > 0) {
	 *    root.remove(root.getContent().get(0));
	 * }
	 */ 
	public void remove(FileSystemItem item) {
		if(this.content.contains(item) == true) {
			item.setParent(null);
			this.content.remove(item);
		}
	}

	@Override
	public int getNumberOfContainedFiles() {
		int numberOfFiles = 0;
		for(FileSystemItem item : this.content) {
			if(!item.isDirectory()) {
				numberOfFiles++;
			}
		}
		return numberOfFiles;
	}

	@Override
	public int getNumberOfContainedDirectories() {
		int numberOfDirs = 0;
		for(FileSystemItem item : this.content) {
			if(item.isDirectory() == true) {
				numberOfDirs++;
			}
		}
		return numberOfDirs;
	}

	@Override
	public int getSize() {
		return 0; // A directory has no size
	}
}
