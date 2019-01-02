/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package filesystem;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

public class TestsForMove extends FileSystemTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testFileMove() {
		ArrayList<FileSystemItem> content;
		
		// Check Preconditions
		TestCase.assertTrue(this.file1InDir1.getPath().compareTo("C:\\subDir1\\File1InDir1") == 0);
		TestCase.assertSame(this.file1InDir1.getParent(), this.subDir1);
		content = subDir2.getContent();
		TestCase.assertTrue(content.contains(this.file1InDir1) == false);
		content = subDir1.getContent();
		TestCase.assertTrue(content.contains(this.file1InDir1) == true);
		
		// Do move
		this.subDir2.add(this.file1InDir1);
		
		// Check Postconditions
		TestCase.assertTrue(this.file1InDir1.getPath().compareTo("C:\\subDir2\\File1InDir1") == 0);
		TestCase.assertSame(this.file1InDir1.getParent(), this.subDir2);
		content = subDir2.getContent();
		TestCase.assertTrue(content.contains(this.file1InDir1) == true);
		content = subDir1.getContent();
		TestCase.assertTrue(content.contains(this.file1InDir1) == false);
	}
}
