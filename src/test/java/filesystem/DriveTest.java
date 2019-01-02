/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package filesystem;

import interfaces.IDrive;

import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

public class DriveTest extends FileSystemTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testConstructor() {
		String driveName = "d";
		
		IDrive testdrive = new Drive(driveName);
		TestCase.assertSame(testdrive.getRootDirectory(), testdrive.getCurrentDirectory());
		TestCase.assertTrue(testdrive.getCurrentDirectory().getName().compareTo("D:") == 0);
		TestCase.assertTrue(testdrive.getRootDirectory().getName().compareTo("D:") == 0);
		TestCase.assertTrue(testdrive.getDriveLetter().compareTo("D:") == 0);
		
		testdrive = new Drive("Hello");
		TestCase.assertTrue(testdrive.getDriveLetter().compareTo("H:") == 0);
	}
	
	@Test
	public void testCurrentDirectory() {
		TestCase.assertTrue(this.drive.getCurrentDirectory().getName().compareTo("C:") == 0);
		
		Directory subDir = new Directory("subDir");
		this.drive.getRootDirectory().add(subDir);
		this.drive.changeCurrentDirectory(subDir);
		TestCase.assertSame(this.drive.getCurrentDirectory(), subDir);
	}
	
	@Test
	public void testGetItemFromPathWithAbsolutePaths() {
		String testpath;
		
		testpath = this.rootDir.getPath();
		TestCase.assertSame(this.drive.getItemFromPath(testpath), this.rootDir);
		testpath = this.subDir1.getPath();
		TestCase.assertSame(this.drive.getItemFromPath(testpath), this.subDir1);
		testpath = this.subDir2.getPath();
		testpath = testpath.replace('\\', '/');
		TestCase.assertSame(this.drive.getItemFromPath(testpath), this.subDir2);
		
		testpath = this.file2InDir1.getPath();
		TestCase.assertSame(this.drive.getItemFromPath(testpath), this.file2InDir1);
		testpath = this.fileInRoot1.getPath();
		TestCase.assertSame(this.drive.getItemFromPath(testpath), this.fileInRoot1);
		
		testpath = "g:\\gaga\\gugus";
		TestCase.assertTrue(this.drive.getItemFromPath(testpath) == null);
		
		testpath = "\\" + this.subDir1.getName();
		TestCase.assertSame(this.drive.getItemFromPath(testpath), this.subDir1);

		TestCase.assertSame(this.drive.getItemFromPath("C:\\subDir1"), this.subDir1);
		TestCase.assertSame(this.drive.getItemFromPath("c:\\subDir1"), this.subDir1);
		TestCase.assertSame(this.drive.getItemFromPath("c:/subDir1"), this.subDir1);
}

	@Test
	public void testGetItemFromPathWithRelativePaths() {
		String subSubDirName = new String("SubSubDir1");
		Directory subSubDir1 = new Directory(subSubDirName);
		this.subDir1.add(subSubDir1);
		
		this.drive.changeCurrentDirectory(this.subDir1);
		TestCase.assertSame(this.drive.getItemFromPath(subSubDirName), subSubDir1);
	}
	
	@Test
	public void testGetItemFromPathWithSpecialPaths() {

		// Path "\"
		TestCase.assertSame(this.drive.getItemFromPath("\\"), this.drive.getRootDirectory());
		
		// Path ".."
		this.drive.changeCurrentDirectory(this.subDir2);
		TestCase.assertSame(this.drive.getItemFromPath(".."), this.subDir2.getParent());
		this.drive.changeCurrentDirectory(this.rootDir);
		TestCase.assertSame(this.drive.getItemFromPath(".."), this.rootDir);
		
		// Path "."
		this.drive.changeCurrentDirectory(this.subDir1);
		TestCase.assertSame(this.drive.getItemFromPath("."), this.subDir1);
		
		// Path ".\"
		this.drive.changeCurrentDirectory(this.subDir1);
		TestCase.assertSame(this.drive.getItemFromPath(".\\"), this.subDir1);
		
		// Path ".\subDir2"
		this.drive.changeCurrentDirectory(this.rootDir);
		TestCase.assertSame(this.drive.getItemFromPath(".\\subDir2"), this.subDir2);
		
		// Path "..\subDir1"
		this.drive.changeCurrentDirectory(this.subDir2);
		TestCase.assertSame(this.drive.getItemFromPath("..\\subDir1"), this.subDir1);
		
		// Path ".\..\subDir1"
		this.drive.changeCurrentDirectory(this.subDir2);
		TestCase.assertSame(this.drive.getItemFromPath(".\\..\\subDir1"), this.subDir1);
	}
	
	@Test
	public void testSingleCharacterDirectories() {
		TestCase.assertTrue(rootDir.getNumberOfContainedDirectories() == 2);

		Directory newDir = new Directory("N");
		this.rootDir.add(newDir);
		TestCase.assertTrue(rootDir.getNumberOfContainedDirectories() == 3);

		FileSystemItem item = this.drive.getItemFromPath(rootDir.getPath() + "\\N");
		TestCase.assertNotNull(item);
		TestCase.assertTrue(item.isDirectory() == true);
		TestCase.assertSame(item, newDir);
	}
}
