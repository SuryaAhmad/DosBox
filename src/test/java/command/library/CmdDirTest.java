/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package command.library;

import helpers.TestHelper;
import org.junit.Before;
import org.junit.Test;

public class CmdDirTest extends CmdTest {
	
	@Before
    public void setUp()
    {
        // Check this file structure in base class: crucial to understand the tests.
        this.createTestFileStructure();

        // Add all commands which are necessary to execute this unit test
        // Important: Other commands are not available unless added here.
        commandInvoker.addCommand(new CmdDir("dir", drive));
    }

    @Test
    public void CmdDir_WithoutParameter_PrintPathOfCurrentDirectory()
    {
        drive.changeCurrentDirectory(rootDir);
        executeCommand("dir");
        TestHelper.assertContains(rootDir.getPath(), testOutput);
    }

    @Test
    public void CmdDir_WithoutParameter_PrintFiles()
    {
        drive.changeCurrentDirectory(rootDir);
        executeCommand("dir");
        TestHelper.assertContains(fileInRoot1.getName(), testOutput);
        TestHelper.assertContains(fileInRoot2.getName(), testOutput);
    }

    @Test
    public void CmdDir_WithoutParameter_PrintDirectories()
    {
        drive.changeCurrentDirectory(rootDir);
        executeCommand("dir");
        TestHelper.assertContains(subDir1.getName(), testOutput);
        TestHelper.assertContains(subDir2.getName(), testOutput);
    }

    @Test
    public void CmdDir_WithoutParameter_PrintsFooter()
    {
        drive.changeCurrentDirectory(rootDir);
        executeCommand("dir");
        TestHelper.assertContains("2 File(s)", testOutput);
        TestHelper.assertContains("2 Dir(s)", testOutput);
    }

    @Test
    public void CmdDir_PathAsParameter_PrintGivenPath()
    {
        drive.changeCurrentDirectory(rootDir);
        executeCommand("dir c:\\subDir1");
        TestHelper.assertContains(subDir1.getPath(), testOutput);
    }

    @Test
    public void CmdDir_PathAsParameter_PrintFilesInGivenPath()
    {
        drive.changeCurrentDirectory(rootDir);
        executeCommand("dir c:\\subDir1");
        TestHelper.assertContains(file1InDir1.getName(), testOutput);
        TestHelper.assertContains(file2InDir1.getName(), testOutput);
    }

    @Test
    public void CmdDir_PathAsParameter_PrintsFooter()
    {
        drive.changeCurrentDirectory(rootDir);
        executeCommand("dir c:\\subDir1");
        TestHelper.assertContains("2 File(s)", testOutput);
        TestHelper.assertContains("0 Dir(s)", testOutput);
    }

    @Test
    public void CmdDir_FileAsParameter_PrintGivenPath()
    {
        drive.changeCurrentDirectory(rootDir);
        executeCommand("dir " + this.file1InDir1.getPath());
        TestHelper.assertContains(subDir1.getPath(), testOutput);
    }

    @Test
    public void CmdDir_FileAsParameter_PrintFilesInGivenPath()
    {
        drive.changeCurrentDirectory(rootDir);
        executeCommand("dir " + this.file1InDir1.getPath());
        TestHelper.assertContains(file1InDir1.getName(), testOutput);
        TestHelper.assertContains(file2InDir1.getName(), testOutput);
    }

    @Test
    public void CmdDir_FileAsParameter_PrintsFooter()
    {
        drive.changeCurrentDirectory(rootDir);
        executeCommand("dir " + this.file1InDir1.getPath());
        TestHelper.assertContains("2 File(s)", testOutput);
        TestHelper.assertContains("0 Dir(s)", testOutput);
    }

    @Test
    public void CmdDir_NotExistingDirectory_PrintsError()
    {
        executeCommand("dir NotExistingDirectory");
        TestHelper.assertContainsNoCase("File Not Found", this.testOutput);
    }

    @Test
    public void CmdDir_AllParametersAreReset()
    {
        drive.changeCurrentDirectory(subDir1);
        executeCommand("dir c:\\subDir2");
        TestHelper.assertContains(subDir2.getPath(), testOutput);
        this.testOutput.empty();
        executeCommand("dir");
        TestHelper.assertContains(subDir1.getPath(), testOutput);
    }
}
