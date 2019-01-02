package command.framework;

import static org.junit.Assert.*;
import org.junit.Test;
import interfaces.IOutputter;

public class OutputterTest {
    private IOutputter outputter = new TestOutput();
    
	@Test
    public void hasCharactersPrinted_ByDefault_IsFalse() {
        assertFalse(outputter.hasCharactersPrinted());
        assertEquals(0, outputter.numberOfCharactersPrinted());
    }

	@Test
    public void hasCharactersPrinted_PrintAString_IsTrue() {
        outputter.print("a text");
        assertTrue(outputter.hasCharactersPrinted());
        assertEquals(6, outputter.numberOfCharactersPrinted());
    }

	@Test
    public void hasCharactersPrinted_PrintSpaces_IsFalse() {
        outputter.print("     ");
        assertFalse(outputter.hasCharactersPrinted());
        assertEquals(0, outputter.numberOfCharactersPrinted());
    }

	@Test
    public void hasCharactersPrinted_PrintEmptyNewLine_IsFalse() {
        outputter.printLine("");
        assertFalse(outputter.hasCharactersPrinted());
        assertEquals(0, outputter.numberOfCharactersPrinted());
    }

	@Test
    public void hasCharactersPrinted_PrintSpacesAndNewLines_IsFalse() {
        outputter.print("   ");
        outputter.printLine("");
        outputter.print("   ");
        outputter.printLine("");
        assertFalse(outputter.hasCharactersPrinted());
        assertEquals(0, outputter.numberOfCharactersPrinted());
    }

	@Test
    public void numberOfCharactersPrinted_TwoLines_IsTrue() {
        outputter.printLine("line 1");
        outputter.printLine("line 2");
        assertTrue(outputter.hasCharactersPrinted());
        assertEquals(12, outputter.numberOfCharactersPrinted());
    }

	@Test
    public void ResetStatistics_ContainingCharacters_NoCharactersPrintedReported() {
        outputter.printLine("a text");
        assertTrue(outputter.hasCharactersPrinted());
        outputter.resetStatistics();
        assertFalse(outputter.hasCharactersPrinted());
        assertEquals(0, outputter.numberOfCharactersPrinted());
    }
}
