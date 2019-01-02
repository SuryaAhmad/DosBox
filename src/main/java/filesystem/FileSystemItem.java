/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package filesystem;

import java.util.ArrayList;

/**This class abstracts File and Directory.
 * Composite-Pattern: Component
 * 
 * Responsibilities:
 * - declares the common interface for objects in the composition. This is:
 *    - get/setName(), toString()
 *    - getPath()
 *    - isDirectory()
 *    - getNumberOfFiles(), getNumberOfDirectories()
 *    - getSize()
 * - implements default behavior for the interface common to all classes, as appropriate. 
 * - declares an interface for accessing and managing its child components. This is
 *    - getContent()
 * - defines an interface for accessing a component's parent in the recursive structure,
 *   and implements it if that's appropriate. This is
 *    - getParent()
 */
public abstract class FileSystemItem {

	private String name;
	private Directory parent;
	private static final String ILLEGAL_ARGUMENT_TEXT = "Error: A file or directory name may not contain '/', '\', ',', ' ' or ':'";
	
	protected FileSystemItem(String name, Directory parent) throws IllegalArgumentException {
		if(checkName(name) == false) {
			throw new IllegalArgumentException(name + " - " + ILLEGAL_ARGUMENT_TEXT);
		}
		this.name = name;
		this.parent = parent;
	}

	/**Returns the name of the file system item.
	 * Only returns the name, not the path. The path must be obtained by getPath. 
	 */
	public String getName() {
		return this.name;
	}
	
	/**Sets the name of the item.
	 * @param newName new name of the item. May not contain any ':', '/', ',', ' ' or '\' in the name.
	 *                Otherwise, the name is not changed and an IllegalArgumentException is thrown. 
	 * @throws IllegalArgumentException
	*/
	public void setName(String newName) throws IllegalArgumentException {
		if(checkName(newName) == false) {
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_TEXT);
		}
		
		this.name = newName;
	}
	
	protected static boolean checkName(String name) {
		if(name.contains("\\") == true || name.contains("/") == true
				|| name.contains(",") == true || name.contains(" ") == true) {
			return false;
		}
		
		return true;
	}
	
	/**Returns the full path of the item
	 * @return Full path, e.g. "C:\thisdir\thatdir\file.txt"
	 */
	public String getPath() {
		String path = new String();
		
		if(parent != null) {
			path = parent.getPath() + "\\" + this.name;
		}
		else {  // For root directory
			path = this.name;
		}
		
		return path;
	}
	
	/** Returns the full path of the item
	 *  See getPath()
	 */
	@Override
	public String toString() {
		return getPath();
	}

	/** Returns the content of the item.
	 * @return - the list of contained files and directories if isDirectory() == true
	 *         - null if isDirectory() == false 
	 * @throws IllegalAccessException if called on a File
	 */
	abstract public ArrayList<FileSystemItem> getContent() throws IllegalAccessException;

	/**Returns the parent directory.
	 * If this item is the root directory (the top most directory), null is returned. 
	 * @return Parent directory or null if this item is already the top most directory.
	 */
	public Directory getParent() {
		return this.parent;
	}
	
	/**Changes the parent; only accessible for subclasses.
	 * Use Directory.add() otherwise 
	 * @param parent new parent directory. No checks are made if parent belongs to drive or if it is null
	 */
	protected void setParent(Directory parent) {
		this.parent = parent;
	}

	abstract public boolean isDirectory();

	public abstract int getNumberOfContainedFiles();

	/**Returns the number of directories contained by this item
	 * @return number of contained directories if isDirectory() == true
	 *         0 if isDirectory() == false
	 */
	public abstract int getNumberOfContainedDirectories();

	/**Returns the size of the item
	 * @return the size in bytes of the file (string length of the content) if isDirectory() == false
	 *         0 if isDirectory() == true
	 */
	public abstract int getSize();
}
