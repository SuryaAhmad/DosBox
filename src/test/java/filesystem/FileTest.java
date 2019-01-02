/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package filesystem;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileTest {

	private File testfile;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

	@Before
	public void setUp() throws Exception {
		testfile = new File("test", "test content");
	}

    @After
    public void tearDown() throws Exception {
    }
	
	@Test
	public void testConstructor() {
		String name = "hello.txt";
		String content = "This is the content";
		
		testfile = new File(name, content);
		assertTrue(testfile.getName().compareTo(name) == 0);
		assertTrue(testfile.getFileContent().compareTo(content) == 0);
	}
	
	@Test
	public void testForDirectory() {
		assertTrue(testfile.isDirectory() == false);
	}

	@Test
	public void testRename() {
		this.testfile.setName("NewName");
		assertTrue(this.testfile.getName().compareTo("NewName") == 0);
	}
	
	@Test
	public void testRenameWithIllegalNames() {
		String defaultName = new String("default");
		this.testfile.setName(defaultName);
		assertTrue(this.testfile.getName().compareTo(defaultName) == 0);
		
		try {
			this.testfile.setName("Illegal\\Name");
			fail();  // must throw an exception
		}
		catch(IllegalArgumentException e) {
			assertTrue(this.testfile.getName().compareTo(defaultName) == 0);
		}
	}
	
	@Test
	public void testFileSize() {
		//TODO
	}

    /**
     * Test of getFileContent method, of class File.
     */
    @Test
    public void testGetFileContent() {
        System.out.println("getFileContent");
        File instance = new File();
        String expResult = "";
        String result = instance.getFileContent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContent method, of class File.
     */
    @Test
    public void testGetContent() throws Exception {
        System.out.println("getContent");
        File instance = new File();
        ArrayList<FileSystemItem> expResult = null;
        ArrayList<FileSystemItem> result = instance.getContent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isDirectory method, of class File.
     */
    @Test
    public void testIsDirectory() {
        System.out.println("isDirectory");
        File instance = new File();
        boolean expResult = false;
        boolean result = instance.isDirectory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumberOfContainedFiles method, of class File.
     */
    @Test
    public void testGetNumberOfContainedFiles() {
        System.out.println("getNumberOfContainedFiles");
        File instance = new File();
        int expResult = 0;
        int result = instance.getNumberOfContainedFiles();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumberOfContainedDirectories method, of class File.
     */
    @Test
    public void testGetNumberOfContainedDirectories() {
        System.out.println("getNumberOfContainedDirectories");
        File instance = new File();
        int expResult = 0;
        int result = instance.getNumberOfContainedDirectories();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSize method, of class File.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        File instance = new File();
        int expResult = 0;
        int result = instance.getSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
