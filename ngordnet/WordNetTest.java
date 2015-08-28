package ngordnet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  @author Xizhao Deng
 */

public class WordNetTest {
    private WordNet wn = new WordNet("./wordnet/synsets.txt",
            "./wordnet/hyponyms.txt");

    @Test
    public void isNounTest() {
        assertEquals(true, wn.isNoun("jump"));
        assertEquals(true, wn.isNoun("leap"));
        assertEquals(true, wn.isNoun("nasal_decongestant"));
    }

    @Test
    public void nounsTest() {
        assertEquals(119188, wn.nouns().size());
    }

    @Test
    public void hyponymsTest() {
        assertEquals(141, wn.hyponyms("increase").size());
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(WordNetTest.class);
    }
}
