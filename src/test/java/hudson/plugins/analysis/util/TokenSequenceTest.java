package hudson.plugins.analysis.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gogic on 09.02.17.
 */
public class TokenSequenceTest {
    String TEST_SEQUENCE="Test1 test2";


    @Test
    public void testLength() {
        TokenSequence tokenSequence=new TokenSequence(TEST_SEQUENCE);
        assertEquals(TEST_SEQUENCE.length(),tokenSequence.length());
    }

    @Test
    public void testAppendSequence() {
        TokenSequence tokenSequence=new TokenSequence(TEST_SEQUENCE);
        tokenSequence.appendSequence(TEST_SEQUENCE);
        String expected=TEST_SEQUENCE+TEST_SEQUENCE;
        assertEquals(expected,tokenSequence.toString());
    }

    @Test
    public void testPrependStringToToken() {
        TokenSequence tokenSequence=new TokenSequence(TEST_SEQUENCE);
        String prepependedString="TEST";

        //prepend string to the first token
        tokenSequence.prependStringToToken(prepependedString,0);
        String expected="TESTTest1 test2";
        assertEquals(expected,tokenSequence.toString());

        //prepend string to the last token
        tokenSequence=new TokenSequence(TEST_SEQUENCE);
        tokenSequence.prependStringToToken(prepependedString,10);
        expected="Test1 testTEST2";
        assertEquals(expected,tokenSequence.toString());

        //prepend anywhere
        tokenSequence=new TokenSequence(TEST_SEQUENCE);
        tokenSequence.prependStringToToken(prepependedString,2);
        expected="TeTESTst1 test2";
        assertEquals(expected,tokenSequence.toString());
    }

    @Test
    public void testAppendStringToToken() {
        TokenSequence tokenSequence=new TokenSequence(TEST_SEQUENCE);
        String appendedString="TEST";

        //append string to the first token
        tokenSequence.appendStringToToken(appendedString,0);
        String expected="TTESTest1 test2";
        assertEquals(expected,tokenSequence.toString());

        //append string to the last token
        tokenSequence=new TokenSequence(TEST_SEQUENCE);
        tokenSequence.appendStringToToken(appendedString,10);
        expected="Test1 test2TEST";
        assertEquals(expected,tokenSequence.toString());

        // append anywhere
        tokenSequence=new TokenSequence(TEST_SEQUENCE);
        tokenSequence.appendStringToToken(appendedString,2);
        expected="TesTESTt1 test2";
        assertEquals(expected,tokenSequence.toString());
    }

    @Test
    public void testGetToken() {
        TokenSequence tokenSequence=new TokenSequence("ABCDEFGH");
        char expectedAt0='A';
        char expectedAt3='D';

        assertEquals(expectedAt0,tokenSequence.getToken(0).getCharacter());
        assertEquals(expectedAt3,tokenSequence.getToken(3).getCharacter());
    }

    @Test
    public void testToString() {
        TokenSequence tokenSequence=new TokenSequence(TEST_SEQUENCE);
        String append="*****";

        tokenSequence.appendStringToToken(append,0);
        tokenSequence.prependStringToToken(append,0);
        tokenSequence.appendStringToToken(append,1);
        tokenSequence.prependStringToToken(append, 10);

        String expected="*****T*****e*****st1 test*****2";
        assertEquals(expected,tokenSequence.toString());
    }

}