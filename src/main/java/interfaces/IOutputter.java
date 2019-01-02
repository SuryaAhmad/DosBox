/*
 * DOSBox, Scrum.org, Professional Scrum Developer Training
 * Authors: Rainer Grau, Daniel Tobler, Zuehlke Technology Group
 * Copyright (c) 2013 All Right Reserved
 */ 

package interfaces;

/**This interface needs to be implemented by a class that outputs the passed
 * strings to either a console, a TCP/IP-Port, to a file, etc.
 * Currently, output to a console is supported only.
 * This interface is used to inverse the dependency from invoker to commands. With
 * this interface, the commands do not need to know a particular invoker to output strings.
 */
public interface IOutputter
{
    /**Outputs the string and adds a newline at the end.
     * If a newline is already passed, two newlines are output.
     * @param name="line">string to output. No ending newline character required.
     */
    void printLine(String line);

    /**Outputs the string. Does not add a newline at the end.
     * @param name="text">text string to output.
     */
    void print(String text);

    /**Outputs a newline.
     */
    void newLine();

    /**Reads a single character from the channel.
     * May be used to ask the user Yes/No questions.
     * Note: This function does not return until user entered a character.
     * It may be that calling this function causes a deadlock!
     * @return character read
     */
    char readSingleCharacter();

    /**Returns the numbers of characters printed since last ResetStatistics().
     * Newlines and spaces do not count.
     */
    int numberOfCharactersPrinted();

    /**Returns whether characters where printed since last ResetStatistics().
     * Newlines and spaces are not taken into account.
     */
    boolean hasCharactersPrinted();

    /**Resets the character counter.
     */
    void resetStatistics();
}
