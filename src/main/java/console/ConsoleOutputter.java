/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package console;

import interfaces.IOutputter;
import java.io.IOException;


/**Implements the IOutputter interface that all text is sent to
 * the console (System.out).
 */
public class ConsoleOutputter implements IOutputter {

	private int numberOfPrintedCharacters;

	public void newLine() {
		System.out.println("");
	}

	public void print(String text) {
		System.out.print(text);
		analyzePrintedCharacters(text);
	}

	public void printLine(String line) {
		System.out.println(line);
		analyzePrintedCharacters(line);
	}

	public char readSingleCharacter() {
		int in = 0;
		int readChar = 0;

		try {
			while (in != '\n') {
				if(in != '\n' && in != '\r')  // do not consider \r and \n
					readChar = in;
				in = System.in.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
			readChar = 0;
		}
		return (char)readChar;
	}
	
    public int numberOfCharactersPrinted()
    {
        return numberOfPrintedCharacters;
    }

    public boolean hasCharactersPrinted()
    {
        return numberOfPrintedCharacters > 0;
    }

    public void resetStatistics()
    {
        numberOfPrintedCharacters = 0;
    }

    protected void analyzePrintedCharacters(String printedString)
    {
        String tempString = printedString.trim();
        numberOfPrintedCharacters += tempString.length();
    }
}
