/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package command.library;

import interfaces.IDrive;
import interfaces.IOutputter;
import command.framework.Command;
import filesystem.Directory;
import filesystem.FileSystemItem;

/**Command to change current directory.
 * Example for a command with optional parameters.
 */
class CmdCd extends Command {
	
	private static final String SYSTEM_CANNOT_FIND_THE_PATH_SPECIFIED = "The system cannot find the path specified";
    private static final String DESTINATION_IS_FILE = "The directory name is invalid.";
	private Directory destinationDirectory;

	protected CmdCd(String name, IDrive drive) {
		super(name, drive);
	}

	@Override
	public boolean checkNumberOfParameters(int numberOfParametersEntered) {
		return (numberOfParametersEntered == 0 || numberOfParametersEntered == 1); 
	}
	
	@Override
	public boolean checkParameterValues(IOutputter outputter) {
        if(getParameterCount() > 0) {
            this.destinationDirectory = extractAndCheckIfValidDirectory(this.getParameterAt(0), this.getDrive(), outputter);
            return this.destinationDirectory != null;
        }
        else
        {
            this.destinationDirectory = null;
            return true;
        }
	}
	
	@Override
    public void execute(IOutputter outputter) {
        if (getParameterCount() == 0)
        {
            printCurrentDirectoryPath(this.getDrive().getCurrentDirectory().getPath(), outputter);
        }
        else
        {
            changeCurrentDirectory(this.destinationDirectory, this.getDrive(), outputter);
        }
    }

    private static void changeCurrentDirectory(Directory destinationDirectory, IDrive drive, IOutputter outputter) {
        boolean success = drive.changeCurrentDirectory(destinationDirectory);
        if (!success)
        {
            outputter.printLine(SYSTEM_CANNOT_FIND_THE_PATH_SPECIFIED);
        }
    }

    private static void printCurrentDirectoryPath(String currentDirectoryName, IOutputter outputter) {
        outputter.printLine(currentDirectoryName);
    }

    private static Directory extractAndCheckIfValidDirectory(String destinationDirectoryName, IDrive drive, IOutputter outputter) {
        FileSystemItem tempDestinationDirectory = drive.getItemFromPath(destinationDirectoryName);
        if (tempDestinationDirectory == null)
        {
            outputter.printLine(SYSTEM_CANNOT_FIND_THE_PATH_SPECIFIED);
            return null;
        }
        if (!tempDestinationDirectory.isDirectory())
        {
            outputter.printLine(DESTINATION_IS_FILE);
            return null;
        }
        return (Directory)tempDestinationDirectory;
    }
}
