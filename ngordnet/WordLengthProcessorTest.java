package ngordnet;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  @author Xizhao Deng
 */


public class WordLengthProcessorTest {

    @Test
    public void processTest() {
        YearlyRecord yr = new YearlyRecord();
        yr.put("sheep", 100);
        yr.put("dog", 300);
        WordLengthProcessor wlp = new WordLengthProcessor();

        assertEquals(3.5, wlp.process(yr), 0.1); // prints 3.5
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(WordLengthProcessorTest.class);
    }
}
