package hudson.plugins.analysis.util;

/**
 * Created by gogic on 28.11.16.
 * This class represents a token that is part of token sequence.
 */
public class Token {

    /* Is part of a string that got transformed into a token sequence.*/
    private final char character;

    /* A string that is prepended to this token*/
    private String prependedString;

    /* A string that is appended to this token*/
    private String appendedString;

    public Token(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    public String getPrependedString() {
        return prependedString;
    }

    public void setPrependedString(String prependedString) {
        this.prependedString = prependedString;
    }

    public String getAppendedString() {
        return appendedString;
    }

    public void setAppendedString(String appendedString) {
        this.appendedString = appendedString;
    }

}
