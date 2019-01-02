/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package filesystem;

import interfaces.IDrive;

import java.util.ArrayList;
import java.util.Iterator;

/**This class implements the access to the composition.
 * Composite-Pattern: Client<br>
 * <br>
 * Responsibilities:<br>
 * <li> manipulates objects in the composition through the Component interface.<br>
 * <li> owns the root directory which is the top of the directory tree.<br>
 * <li> knows the current directory on which most of the commands must be performed.<br>
 */
public class Drive implements IDrive {
	private String driveLetter;
	private String label;
	private Directory rootDir;
	private Directory currentDir;

	/**Creates a new drive and a root directory.
	 * @param driveLetter Name of the drive. May only contain a single uppercase letter.
	 * If longer name given, only the first character is taken.
	 */
	public Drive(String driveLetter) {
		//TODO: How to convert parameter from char to String?
		this.driveLetter = driveLetter.substring(0, 1);
		this.driveLetter = this.driveLetter.toUpperCase();
		this.label = new String("");
		this.rootDir = new Directory(this.driveLetter + ":");
		this.currentDir = this.rootDir;
	}
	
	/* (non-Javadoc)
	 * @see filesystem.IDrive#getRootDirectory()
	 */
	@Override
	public Directory getRootDirectory() {
		return this.rootDir;
	}

	/**Gets the current directory
	 */
	@Override
	public Directory getCurrentDirectory()
	{
		return this.currentDir;
	}
	
	/**Changes current directory
	 * @param dir new current directory
	 * @return
	 * - true if succeeded
	 * - false otherwise
	 */
	@Override
	public boolean changeCurrentDirectory(Directory dir) {
		if(this.getItemFromPath(dir.getPath()) == dir) {	
			this.currentDir = dir;
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public String getDriveLetter() {
		return this.driveLetter + ":";
	}

	@Override
	public String getPrompt() {
		return this.currentDir.getPath() + "> ";
	}

	/**Same as getDriveName()
	 */
	public String toString() {
		return this.getDriveLetter();
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public String getLabel() {
		return this.label;
	}
	
	@Override
	public FileSystemItem getItemFromPath(String givenItemPath) {
		
		// Replace any "/" with "\"
		String givenItemPathPatched = givenItemPath.replace('/', '\\');
		
		// Remove ending "\"
		givenItemPathPatched = givenItemPathPatched.trim();
		if(   givenItemPathPatched.charAt(givenItemPathPatched.length()-1) == '\\'
		   && givenItemPathPatched.length() >= 2) {
				givenItemPathPatched = givenItemPathPatched.substring(0, givenItemPathPatched.length()-1);
		}
		
		// Test for special paths
		if(givenItemPathPatched.compareTo("\\") == 0) {
			return this.getRootDirectory();
		}
		
		if(givenItemPathPatched.compareTo("..") == 0) {
			Directory parent = this.getCurrentDirectory().getParent();
			if(parent == null) {  // Case if current directory is already root
				parent = this.getRootDirectory();
			}
			return parent;
		}
		
		if(givenItemPathPatched.compareTo(".") == 0) {
			return this.getCurrentDirectory();
		}
		
		// Check for .\
		if(givenItemPathPatched.length() >= 2) {
			if(givenItemPathPatched.substring(0, 2).compareTo(".\\") == 0) {
				givenItemPathPatched = givenItemPathPatched.substring(2, givenItemPathPatched.length());
			}
		}
		
		// Check for ..\
		if(givenItemPathPatched.length() >= 3) {
			if(givenItemPathPatched.substring(0, 3).compareTo("..\\") == 0) {
				StringBuilder temp = new StringBuilder();
				temp.append(this.getCurrentDirectory().getParent().getPath());
				temp.append("\\");
				temp.append(givenItemPathPatched.substring(3, givenItemPathPatched.length()));
				givenItemPathPatched = temp.toString();
			}
		}
		
		// Add drive name if path starts with "\"
		if(givenItemPathPatched.charAt(0) == '\\') {
			givenItemPathPatched = this.driveLetter + ":" + givenItemPathPatched;
		}
		
		// Make absolute path from relative paths
		if(givenItemPathPatched.length() == 1 || givenItemPathPatched.charAt(1) != ':') {
			givenItemPathPatched = this.getCurrentDirectory() + "\\" + givenItemPathPatched;
		}
		
		// Find more complex paths recursive
		if(givenItemPathPatched.compareTo(this.rootDir.getPath()) == 0) {
			return this.rootDir;
		}
		
		return getItemFromDirectory(givenItemPathPatched, this.rootDir);
	}
	
	/**Helper for getItemFromPath()
	 * */
	private FileSystemItem getItemFromDirectory(String givenItemName, Directory directoryToLookup) {
		ArrayList<FileSystemItem> content = directoryToLookup.getContent();
		
		Iterator<FileSystemItem> it = content.iterator();
		FileSystemItem item;
		FileSystemItem retVal;
		String pathName;
		while(it.hasNext()) {
			item = it.next();
			pathName = item.getPath();
			if(pathName.equalsIgnoreCase(givenItemName)) {
				return item;
			}
			if(item.isDirectory() == true) {
				retVal = this.getItemFromDirectory(givenItemName, (Directory)item);
				if(retVal != null) {
					return retVal;
				}
			}
		}
		return null;
	}
	
	public void createFromRealDirectory(String path) {
		// Not yet implemented
	}
	
	public void save() {
		// Not yet implemented
	}
	
	public void restore() {
		// Not yet implemented
	}
}
