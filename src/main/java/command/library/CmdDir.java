/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package command.library;

import java.util.ArrayList;
import interfaces.IDrive;
import interfaces.IOutputter;
import command.framework.Command;
import filesystem.Directory;
import filesystem.FileSystemItem;
import java.io.File;

class CmdDir extends Command {
    private static final String SYSTEM_CANNOT_FIND_THE_PATH_SPECIFIED = "File Not Found"; 
    private Directory directoryToPrint;

    public CmdDir(String name, IDrive drive) {
        super(name, drive);
    }

    @Override
    protected boolean checkNumberOfParameters(int numberOfParameters) {
        return numberOfParameters == 0 || numberOfParameters == 1;
    }

    @Override
    protected boolean checkParameterValues(IOutputter outputter) {
        if (getParameterCount() == 0)
        {
            directoryToPrint = getDrive().getCurrentDirectory();
        }
        else
        {
            this.directoryToPrint = checkAndPreparePathParameter(getParameterAt(0), outputter);
        }
        return this.directoryToPrint != null;
    }

    private Directory checkAndPreparePathParameter(String pathName, IOutputter outputter) {
        FileSystemItem fsi = getDrive().getItemFromPath(pathName);
        if (fsi == null)
        {
            outputter.printLine(SYSTEM_CANNOT_FIND_THE_PATH_SPECIFIED);
            return null;
        }
        if (!fsi.isDirectory())
        {
            return fsi.getParent();
        }
        return (Directory)fsi;
    }

    @Override
    public void execute(IOutputter outputter) {
        File folder = new File(this.directoryToPrint.getPath());
        File[] listOfFiles = folder.listFiles();
        
        printHeader(this.directoryToPrint, outputter);
        printContent(listOfFiles, outputter);
        printFooter(listOfFiles, outputter);
    }

    private static void printHeader(Directory directoryToPrint, IOutputter outputter) {
        outputter.printLine("Directory of " + directoryToPrint.getPath());
        outputter.newLine();
    }

    private static void printContent(File[] directoryContent, IOutputter outputter) {
        for (int i = 0; i < directoryContent.length; i++) {
            java.util.Date cDate = new java.util.Date(directoryContent[i].lastModified());
            if (directoryContent[i].isDirectory()) {
                outputter.print(cDate.toString());
                outputter.print("<DIR>");
                outputter.print(directoryContent[i].getName());
            }else{
                outputter.print(cDate.toString());
                outputter.print("     " + directoryContent[i].getName());
            }
                outputter.newLine();
            }        
    }

    private static void printFooter(File[] directoryToPrint, IOutputter outputter) {
        int countFile = 0;
        int countDir = 0;
        for (int i = 0; i < directoryToPrint.length; i++) {
            if (directoryToPrint[i].isDirectory()) {
                countDir = countDir +1;
            }else{
                countFile = countFile +1;
            }
            }        
        outputter.printLine("\t" + countFile + " File(s)");
        outputter.printLine("\t" + countDir + " Dir(s)");
    }
}
