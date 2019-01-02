/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package filesystem;

import interfaces.IDrive;

abstract class FileSystemTestCase {

	protected Directory rootDir;
	protected Directory subDir1;
	protected Directory subDir2;
	protected File fileInRoot1;
	protected File fileInRoot2;
	protected File file1InDir1;
	protected File file2InDir1;
	protected IDrive drive;

	protected void setUp() throws Exception {
		this.drive = new Drive("C");
		this.rootDir = this.drive.getRootDirectory();
		this.fileInRoot1 = new File("FileInRoot1", "");
		this.rootDir.add(this.fileInRoot1);
		this.fileInRoot2 = new File("FileInRoot2", "");
		this.rootDir.add(this.fileInRoot2);
		
		this.subDir1 = new Directory("subDir1");
		this.rootDir.add(subDir1);
		this.file1InDir1 = new File("File1InDir1", "");
		this.subDir1.add(this.file1InDir1);
		this.file2InDir1 = new File("File2InDir1", "");
		this.subDir1.add(this.file2InDir1);
		
		this.subDir2 = new Directory("subDir2");
		this.rootDir.add(subDir2);
	}

}
