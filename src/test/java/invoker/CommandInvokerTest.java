/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package invoker;

import static org.junit.Assert.*;
import helpers.TestHelper;
import interfaces.IDrive;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import command.framework.TestOutput;
import filesystem.Drive;

public class CommandInvokerTest {
        private CommandInvoker commandInvoker;
        private TestOutput output;
        private TestCommand testcmd;

        @Before
        public void setUp() {
            IDrive drive = new Drive("C");
            commandInvoker = new CommandInvoker();
            testcmd = new TestCommand("dIR", drive);
            commandInvoker.addCommand(testcmd);

            output = new TestOutput();
        }

        @Test
        public void ParseCommandName_EmptyString() {
            assertEquals(commandInvoker.parseCommandName(""), "");
        }

        @Test
        public void ParseCommandName_OnlyCommandName() {
            assertEquals(commandInvoker.parseCommandName("dir"), "dir");
        }

        @Test
        public void ParseCommandName_UpperCaseCommandName_ConvertsToLowerCase() {
            assertEquals(commandInvoker.parseCommandName("DIR"), "dir");
        }

        @Test
        public void ParseCommandName_OneParameter_ExtractsCommandName() {
            assertEquals(commandInvoker.parseCommandName("dir param1"), "dir");
        }

        @Test
        public void ParseCommandName_SeparationByCommand_ExtractsCommandName() {
            assertEquals(commandInvoker.parseCommandName("dir,param1, param2"), "dir");
        }

        @Test
        public void ParseCommandName_EndsWithComma_ExtractsCommandName() {
            assertEquals(commandInvoker.parseCommandName("dir,"), "dir");
        }

        @Test
        public void ParseCommandName_EndingWhiteSpaces_ExtractsCommandName() {
            assertEquals(commandInvoker.parseCommandName("dir   "), "dir");
        }

        @Test
        public void ParseCommandName_SingleLetterParameter_ExtractsCommandName() {
            assertEquals(commandInvoker.parseCommandName("dir o"), "dir");
        }

        @Test
        public void ParseCommandParameters_NoParameters_EmptyList() {
           ArrayList<String> parameters = commandInvoker.parseCommandParameters("dir");
            assertTrue(parameters.size() == 0);
        }

        @Test
        public void testParseParameters_OneParameter_OneEntryInList() {
            ArrayList<String> parameters = commandInvoker.parseCommandParameters("dir /p");
            assertTrue(parameters.size() == 1);
            assertTrue(parameters.get(0).compareTo("/p") == 0);
        }

        @Test
        public void testParseParameters_TwoParameters_TwoEntriesInList() {
           ArrayList<String> parameters = commandInvoker.parseCommandParameters("dir /p param2");
            assertEquals(2, parameters.size());
            assertEquals("/p", parameters.get(0));
            assertEquals("param2", parameters.get(1));
        }

        @Test
        public void testParseParameters_OneParameterWithSeveralSpaces_OneEntryInList() {
           ArrayList<String> parameters = commandInvoker.parseCommandParameters("dir    /p");
            assertEquals(1, parameters.size());
            assertEquals("/p", parameters.get(0));
        }

        @Test
        public void testParseParameters_TwoParametersWithSeveralSpaces_TwoEntriesInList() {
           ArrayList<String> parameters = commandInvoker.parseCommandParameters("dir  param1  param2   ");
            assertEquals(2, parameters.size());
            assertEquals("param1", parameters.get(0));
            assertEquals("param2", parameters.get(1));
        }

        @Test
        public void testParseParameters_TwoParametersSingleCharacters_TwoEntriesInList() {
           ArrayList<String> parameters = commandInvoker.parseCommandParameters("d 1 2");
            assertEquals(2, parameters.size());
            assertEquals("1", parameters.get(0));
            assertEquals("2", parameters.get(1));
        }

        @Test
        public void testCommandExecuteSimple() {
            commandInvoker.executeCommand("DIR", output);
            assertTrue(testcmd.executed);
        }

        @Test
        public void testCommandExecuteWithLeadingSpace() {
            commandInvoker.executeCommand("   DIR", output);
            assertTrue(testcmd.executed);
        }

        @Test
        public void testCommandExecuteWithEndingSpace() {
            commandInvoker.executeCommand("DIR   ", output);

            assertTrue(testcmd.executed);
        }

        @Test
        public void ExecuteCommand_CommandOnly_IsExecuted() {
            commandInvoker.executeCommand("dir", output);
            assertTrue(testcmd.executed);
        }

        @Test
        public void ExecuteCommand_CommandWithParameters_IsExecutedParametersPassed() {
            commandInvoker.executeCommand("dir param1 param2", output);
            assertTrue(testcmd.executed);
            assertEquals(2, testcmd.getNumberOfParameters());
        }

        @Test
        public void ExecuteCommand_CommandWithInvalidSyntax_NotExecutedErrorPrinted() {
            testcmd.checkNumberOfParametersReturnValue = false;
            commandInvoker.executeCommand("dir param1 param2", output);
            assertFalse(testcmd.executed);
            assertEquals(2, testcmd.getNumberOfParameters());
            TestHelper.assertContainsNoCase("syntax of the command is incorrect", output);
        }

        @Test
        public void ExecuteCommand_CommandWithWrongNumberOfParameters_NotExecutedErrorPrinted() {
            testcmd.checkParameterValuesReturnValue = false;
            commandInvoker.executeCommand("dir param1 param2", output);
            assertFalse(testcmd.executed);
            assertEquals(2, testcmd.getNumberOfParameters());
            TestHelper.assertContainsNoCase("wrong", output);
            TestHelper.assertContainsNoCase("parameter", output);
        }
}
