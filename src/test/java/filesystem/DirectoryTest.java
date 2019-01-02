/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package filesystem;

import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class DirectoryTest {
    private Directory rootDir;
    private Directory subDir1;
    private Directory subDir2;
    private File file1InSubDir1;
    private File file2InSubDir1;

    @Before
    public void setUp()
    {
        this.rootDir = new Directory("root");
        this.subDir1 = new Directory("subDir1");
        rootDir.add(this.subDir1);
        this.subDir2 = new Directory("subDir2");
        rootDir.add(this.subDir2);
        this.file1InSubDir1 = new File("file1InSubDir1", "content1");
        subDir1.add(this.file1InSubDir1);
        this.file2InSubDir1 = new File("file2InSubDir1", "content2");
        subDir1.add(this.file2InSubDir1);
    }

    @Test
    public void constructor_CreateWithName_IsCorrectlyCreated() {
        String testname = "root";
        Directory testdir = new Directory(testname);
        assertEquals(testname, testdir.getName());
        assertEquals(0, testdir.getNumberOfContainedDirectories());
        assertEquals(0, testdir.getNumberOfContainedFiles());
        assertNull(testdir.getParent());
    }

    @Test
    public void content_Subdirectory_RootContainsValidData_BaseForNextTests() {
        ArrayList<FileSystemItem> content = rootDir.getContent();
        assertNotNull(content);
        assertEquals(2, content.size());
    }

    @Test
    public void content_Subdirectory_Element0HasCorrectName() {
        FileSystemItem item = rootDir.getContent().get(0);
        assertTrue(item.isDirectory());
        assertEquals(subDir1.getName(), item.getName());
        assertSame(this.subDir1, item);
    }

    @Test
    public void Content_Subdirectory_ParentIsSetCorrectly()
    {
        FileSystemItem item = rootDir.getContent().get(0);
        FileSystemItem parent = item.getParent();
        assertTrue(parent.isDirectory());
        assertSame(this.rootDir, parent);
    }

    @Test
    public void Content_Subdirectory_ParentIsSetCorrectly1()
    {
        FileSystemItem item = rootDir.getContent().get(0);
        FileSystemItem parent = item.getParent();
        assertNotNull(parent.getParent() == null);
        String path = item.getPath();
        assertTrue(path.compareTo(rootDir.getName() + "\\" + subDir1.getName()) == 0);
    }

    @Test
    public void testContainingFiles() {
        Directory dir = (Directory)rootDir.getContent().get(0);
        FileSystemItem file1 = dir.getContent().get(0);
        assertNotNull(file1);
        assertEquals(this.file1InSubDir1, file1);
    }

    @Test
    public void testForDirectory() {
        assertTrue(rootDir.isDirectory());
        assertTrue(subDir2.isDirectory());
    }

    @Test
    public void testRename() {
        subDir1.setName("NewName");
        assertTrue(subDir1.getName().compareTo("NewName") == 0);
    }

    @Test
    public void testNumberOfFilesAndDirectories() {
        // TODO
    }
}
