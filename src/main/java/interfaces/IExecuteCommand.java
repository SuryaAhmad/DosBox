/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package interfaces;

public interface IExecuteCommand {

	/**Interprets a command string, executes if an appropriate command is found
	 * and returns all output via the outputter interface.
	 * 
	 * @param command String which is entered, containing the command and the parameters.
	 * @param outputter Implementation of the outputter interface to which the output text is sent.
	 */
	void executeCommand(String command, IOutputter outputter);

}