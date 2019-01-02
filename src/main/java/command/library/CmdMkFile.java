/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package command.library;

import interfaces.IDrive;
import interfaces.IOutputter;
import command.framework.Command;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

class CmdMkFile extends Command {

	public CmdMkFile(String cmdName, IDrive drive) {
		super(cmdName, drive);
	}

	@Override
	public void execute(IOutputter outputter) {
		String fileName = this.getParameterAt(0);
		String fileContent = ".txt";
                String filePath = this.getDrive().getCurrentDirectory().getPath();
                BasicFileAttributes attributes = null;
		
                File newFile = new File(filePath + fileName + fileContent);
                //File newFile = new File("C:\\test.txt");
                
                try{
                    newFile.createNewFile();
                    
                    Path filePaths = newFile.toPath();
                    
                    attributes =
                    Files.readAttributes(filePaths, BasicFileAttributes.class);
                    
                    System.out.println("Berhasil Membuat File =" + newFile );
                    System.out.println("Tanggal Pembuatan =" + attributes.creationTime());
                    
                }catch(Exception e){
                    System.err.println(e.getMessage());
                }               
	}
}
