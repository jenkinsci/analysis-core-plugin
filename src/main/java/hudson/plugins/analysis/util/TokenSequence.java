package hudson.plugins.analysis.util;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by gogic on 28.11.16.
 * This class divides a string into a token sequence und manages it.
 * This class is used to insert strings to a string without changing the index of its letters.
 */
public class TokenSequence {


    private ArrayList<Token> sequence;

    /**
     * Transforms a string to a token sequence.
     * @param sequence String that has to be transformed into a token sequence.
     */
    public TokenSequence(String sequence) {
        this.sequence=new ArrayList<Token>();
        for(int i=0;i<sequence.length();i++){
            this.sequence.add(new Token(sequence.charAt(i)));
        }
    }

    /**
     * Appends a character to the token sequence.
     * @param c character that has to be appended to the token sequence
     */
    private void appendToken(char c) {
        sequence.add(new Token(c));
    }

    /**
     * Returns the length of this token sequence.
     * @return length of the sequence
     */
    public int length(){
        return sequence.size();
    }

    /**
     * Appends a string to the end of this sequece.
     * @param string string that has to be appended to the token sequence
     */
    public void appendSequence(String string){
        for(int i=0;i<string.length();i++){
            appendToken(string.charAt(i));
        }
    }

    /**
     * Prepends a string to the token with index i.
     * @param string string that has to be prepended to the token with index i.
     * @param index index of the token.
     */
    public void prependStringToToken(String string,int index){
        getToken(index).setPrependedString(string);
    }

    /**
     * Appends a string to the token with index i.
     * @param string string that has to be appended to the token with index i.
     * @param index
     */
    public void appendStringToToken(String string,int index){
        getToken(index).setAppendedString(string);
    }

    /**
     * Returns a token from token sequence which has index i. First element has index=0.
     * @param index i
     * @return token with index i
     */
    public Token getToken(int index) {
        return sequence.get(index);
    }

    /**
     * Transforms the token sequence into a string.
     * @return token sequence as a string
     */
    public String toString(){
        StringBuilder sb=new StringBuilder();
        Iterator iterator=sequence.iterator();

        while(iterator.hasNext()){

            Token currentToken=(Token)iterator.next();
            String prependedString=currentToken.getPrependedString();
            String appendedString=currentToken.getAppendedString();

            if(prependedString!=null){
                sb.append(prependedString);
            }
            sb.append(currentToken.getCharacter());
            if(appendedString!=null){
                sb.append(appendedString);
            }
        }
        return sb.toString();
    }
}
