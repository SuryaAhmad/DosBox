/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package console;

import java.io.IOException;
import interfaces.IDrive;
import interfaces.IExecuteCommand;
import interfaces.IOutputter;

/**Implements a console. The user is able to input command strings
 * and receives the output directly on that console.
 * Configures the Invoker, the Commands and the Filesystem.
 */
public class Console {
	private IExecuteCommand invoker;
	private IDrive drive;
	private IOutputter outputter;

	public Console(IExecuteCommand invoker, IDrive drive) {
		this.invoker = invoker;
		this.drive = drive;
		this.outputter = new ConsoleOutputter();
	}

	/**Processes input from the console and invokes the invoker until 'exit' is typed.
	 */
	public void processInput() {
		String line = new String();
        this.outputter.printLine("DOSBox, Scrum.org, Professional Scrum Developer Training");
        this.outputter.printLine("Copyright (c) Rainer Grau and Daniel Tobler. All rights reserved.");

		while(line.trim().compareToIgnoreCase("exit") != 0) {
			int readChar = 0;
			StringBuilder input = new StringBuilder();

			this.outputter.newLine();
			this.outputter.print(this.drive.getPrompt());
			try {
				while (readChar != '\n') {
					readChar = System.in.read();
					input.append((char)readChar);
				}
				line = input.toString();
			} catch (IOException e) {
				// do nothing by intention
			}
			
			this.outputter.resetStatistics();
			invoker.executeCommand(line, this.outputter);
		}
		this.outputter.printLine("\nGoodbye!");
		this.drive.save();
	}
}
