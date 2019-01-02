/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package invoker;

import interfaces.IExecuteCommand;
import interfaces.IOutputter;

import java.util.ArrayList;
import java.util.Iterator;

import command.framework.Command;

/**Invokes commands from a command string passed.<br>
 * Command-Pattern: Invoker<br>
 * <br>
 * Responsibilities:
 * <li> asks the command to carry out the request.
 * This is performed by executeCommand.
 * <li> this class in independent of any concrete output channel like console, terminal, ...
 * This is ensured by the IOutputter interface.
 */
public class CommandInvoker implements IExecuteCommand {
	
	private ArrayList<Command> commands;
	
	public CommandInvoker() {
		this.commands = new ArrayList<Command>();
	}

	/**Sets the list of active commands.<br>
	 * Important: The current list is overwritten. This operation is intended to be used
	 *            by the CommandFactory only at startup to set the reference to the
	 *            current list. Use addCommand() for further expansions.
	 * @param commands Reference to the list of active commands created by the factory.
	 */
	public void setCommands(ArrayList<Command> commands) {
		this.commands = commands;
	}
	
	/**Adds a new command to the list of active commands.
	 * @param cmd New command which should be available by now.
	 */
	public void addCommand(Command cmd) {
		commands.add(cmd);
	}

	@Override
	public void executeCommand(String command, IOutputter outputter) {
		String cmdName = this.parseCommandName(command);
		ArrayList<String> params = this.parseCommandParameters(command);
		
		try {
			Iterator<Command> it = commands.iterator();
			Command cmd;
			while(it.hasNext()) {
				cmd = it.next();
				if(cmd.compareCmdName(cmdName) == true) {
					cmd.setParameters(params);
					if(cmd.checkParameters(outputter) == false) {
						outputter.printLine("Wrong parameter entered");
						return;
					}
					cmd.execute(outputter);
					return;
				}
			}
			outputter.printLine("\'" + cmdName + "\' is not recognized as an internal or external command,");
			outputter.printLine("operable program or batch file.");
		}
		catch(Exception e) {
			if(e.getMessage() != null) {
				outputter.printLine("Unexpected exception while execution command: " + e.getMessage());
			}
			else {
				outputter.printLine("Unknown exception caught");
				outputter.printLine(e.toString());
				e.printStackTrace();
			}
		}
	}

	/**Extracts the command name from a given command string.
	 * The first word, before any space or comma is the command name<br>
	 * Example:<br>
	 * dir /w c:\temp<br>
	 * Command name: dir 
	 * 
	 * @param command Command string
	 * @return Name of the command
	 */
	String parseCommandName(String command) {
		String cmd = command.toLowerCase();
		String cmdName = null;
		
		cmd = cmd.trim();
		cmd = cmd.replace(',', ' ');
		cmd = cmd.replace(';', ' ');

		cmdName = cmd;
		for(int i=0 ; i<cmd.length() ; i++) {
			if(cmd.charAt(i) == ' ') {
				cmdName = cmd.substring(0, i);
				break;
			}
		}
		
		return cmdName;
		
	}
	
	/**Extracts the parameters from a given command string and returns a list of parameters.
	 * Parameters are separated by spaces, commas, semicolons.<br>
	 * Example:<br>
	 * dir /w c:\temp<br>
	 * Parameter 1: /w<br>
	 * Parameter 2: c:\temp<br>
	 * 
	 * @param command Command string
	 * @return List of parameters
	 */
	ArrayList<String> parseCommandParameters(String command) {
		ArrayList<String> params = new ArrayList<String>();
		
		String cmd = command;
		cmd = cmd.trim();
		cmd = cmd.replace(',', ' ');
		cmd = cmd.replace(';', ' ');
		
		int lastSpace = 0;
		for(int i=0 ; i<cmd.length() ; i++) {
			if(cmd.charAt(i) == ' ' || i+1==cmd.length()) {
				params.add(cmd.substring(lastSpace, i+1).trim());
				lastSpace = i;
			}
		}
		
		if(params.isEmpty() == false) {
			
			// Remove command name
			params.remove(0);
			
			// Remove empty arguments
			Iterator<String> it = params.iterator();
			String param;
			while(it.hasNext()) {
				param = it.next();
				if(param.compareTo("") == 0) {
					it.remove();
				}
			}
		}
		
		return params;
	}
}
