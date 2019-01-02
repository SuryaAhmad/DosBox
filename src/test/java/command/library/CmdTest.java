/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package command.library;

import command.framework.TestOutput;
import interfaces.IDrive;
import invoker.CommandInvoker;
import filesystem.Directory;
import filesystem.File;
import filesystem.Drive;

public abstract class CmdTest {
	protected CommandInvoker commandInvoker;
	protected TestOutput testOutput;
	protected IDrive drive;
	protected Directory rootDir;
	protected File fileInRoot1;
	protected File fileInRoot2;
	protected Directory subDir1;
	protected File file1InDir1;
	protected File file2InDir1;
	protected Directory subDir2;
	protected int numbersOfDirectoriesBeforeTest;
	protected int numbersOfFilesBeforeTest;

	public CmdTest() {
		super();
	}

    /**Sets up a directory structure as follows:
     * C:\
     * |---FileInRoot1 ("an entry")
     * |---FileInRoot2 ("a long entry in a file")
     * |---subDir1
     * |   |---File1InDir1 (empty)
     * |   |---File2InDir1 (empty)
     * |---subdir2
     */
	protected void createTestFileStructure() {
		this.drive = new Drive("C");
		this.rootDir = this.drive.getRootDirectory();
		this.fileInRoot1 = new File("FileInRoot1", "an entry");
		this.rootDir.add(this.fileInRoot1);
		this.fileInRoot2 = new File("FileInRoot2", "a long entry in a file");
		this.rootDir.add(this.fileInRoot2);
		
		this.subDir1 = new Directory("subDir1");
		this.rootDir.add(subDir1);
		this.file1InDir1 = new File("File1InDir1", "");
		this.subDir1.add(this.file1InDir1);
		this.file2InDir1 = new File("File2InDir1", "");
		this.subDir1.add(this.file2InDir1);
		
		this.subDir2 = new Directory("subDir2");
		this.rootDir.add(subDir2);
		
		this.commandInvoker = new CommandInvoker();
		this.testOutput = new TestOutput();
		
		this.numbersOfDirectoriesBeforeTest = this.drive.getRootDirectory().getNumberOfContainedDirectories();
		this.numbersOfFilesBeforeTest = this.drive.getRootDirectory().getNumberOfContainedFiles();
	}
	
	protected void executeCommand(String commandLine) {
		if(this.commandInvoker == null)
			this.commandInvoker = new CommandInvoker();
		this.commandInvoker.executeCommand(commandLine, this.testOutput);
	}
}