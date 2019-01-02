/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package interfaces;

import filesystem.Directory;
import filesystem.FileSystemItem;

/**Abstracts the drive (e.g. C:\)
 * Responsibilities:
 *  - provides access to data about the drive like label, drive letter, ...
 *  - owns the root directory which is the top of the directory tree.
 *  - knows the current directory on which most of the commands must be performed.
 *  - queries drive information from a path
 *  - stores drive data persistently
 */
public interface IDrive {
	/**Sets drive label, as used in command VOL and LABEL
	 * @param newLabel new label. Any text, may contain spaces
	 */
	void setLabel(String newLabel);
	
	
	/**Sets drive label, as used in command VOL and LABEL
	 * @return current drive label
	 */
	String getLabel();

	/**E.g. "C:"
	 */
	String getDriveLetter();
	
	/**Current Directory + ">"
	 */
	String getPrompt();

	/**Gets the drive's root directory.
	 */
	Directory getRootDirectory();

	/**Gets the currently active directory on this drive.
	 */
	Directory getCurrentDirectory();
	
	/**Changes the currently active directory on this drive.
	 * @param newCurrentDirectory Must be a directory within the drive's directory structure!
	 * @return false if current directory could not be changed
	 */
	boolean changeCurrentDirectory(Directory newCurrentDirectory);
	
	/**Returns the object of a given path name.
	 * 
     * Example:
     * getItemFromPath("C:\\temp\\aFile.txt");
     * Returns the FileSystemItem-object which abstracts aFile.txt in the temp directory.
     * 
     * Remarks:
     * - Always use "\\" for backslashes since the backslash is used as escape character for Java strings.
     * - This operation works for relative paths (temp\\aFile.txt) too. The lookup starts at the current directory.
     * - This operation works for forward slashes '/' too.
     * - ".." and "." are supported too.
     *
     * @param givenItemPath Path for which the item shall be returned.
     * @return FileSystemObject or null if no path found.
     */
	FileSystemItem getItemFromPath(String givenItemPath);
	
	/**Stores the current directory structure persistently.
	 */
	void save();
	
	/**Creates a directory structure from the stored structure. The current directory structure is deleted.
	 */
	void restore();
	
    /**Builds up a directory structure from the given path on a real drive.
     * Sub-directories become directories and sub-directories
     * Files in that directory and the sub-directories become files, content is set to
     * full path, filename and size of that file.
     * 
     * Example:
     * C:\temp
     * +-- MyFile1.txt (size 112000 Bytes)
     * +-- MyFile2.txt (50000)
     * +-- SubDir1 (Dir)
     * ....+-- AnExecutable.exe (1234000)
     * ....+-- ConfigFiles (Dir)
     * 
     * Results in
     * - All files and sub-directories of the root directory deleted
     * - Current directory set to root directory
     * - File MyFile1.txt added to root directory with content "C:\temp\MyFile1.txt, size 112000 Bytes"
     * - File MyFile2.txt added to root directory with content "C:\temp\MyFile2.txt, size 50000 Bytes"
     * - Directory SubDir1 added to root directory
     * - File AnExecutable.exe added to SubDir1 with content "C:\temp\SubDir1\AnExecutable.exe, size 1234000 Bytes"
     * - Directory ConfigFiles added to SubDir1
     * @param realPath The path to a real directory on any memory device.
     */
	void createFromRealDirectory(String realPath);
}