/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package invoker;

import interfaces.IDrive;
import interfaces.IOutputter;
import command.framework.Command;

public class TestCommand extends Command {

	public boolean executed = false;
	public int numberOfParamsPassed = -1;
	public boolean checkNumberOfParametersReturnValue = true;
	public boolean checkParameterValuesReturnValue = true;

	protected TestCommand(String cmdName, IDrive drive) {
		super(cmdName, drive);
	}
	
	public int getNumberOfParameters() {
		return this.getParameterCount();
	}
	
	@Override
	protected boolean checkNumberOfParameters(int number) {
		this.numberOfParamsPassed  = number;
		return this.checkNumberOfParametersReturnValue;
	}
	
	@Override
	protected boolean checkParameterValues(IOutputter outputter) {
		return this.checkParameterValuesReturnValue;
	}

	@Override
	public void execute(IOutputter outputter) {
		this.executed  = true;
	}
}
