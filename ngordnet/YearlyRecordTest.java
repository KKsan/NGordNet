package ngordnet;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  @author Xizhao Deng
 */

public class YearlyRecordTest {

    @Test
    public void allTests() {
        YearlyRecord yr = new YearlyRecord();
        yr.put("quayside", 95);
        yr.put("surrogate", 340);
        yr.put("surrogate", 345);
        yr.put("merchantman", 181);
        yr.put("merchantman1", 181);
        yr.put("merchantman2", 181);
        yr.put("what", 120);

        assertEquals(1, yr.rank("surrogate")); // should print 1
        assertEquals(2, yr.rank("merchantman")); // should print 2
        assertEquals(3, yr.rank("merchantman1")); // should print 3
        assertEquals(4, yr.rank("merchantman2")); // should print 4
        assertEquals(5, yr.rank("what")); // should print 5
        assertEquals(6, yr.rank("quayside")); // should print 6
        assertEquals(6, yr.size()); // should print 6
        assertEquals(345, yr.count("surrogate"));// 345
        assertEquals(345, yr.count("surrogate"));// 345
        assertEquals(345, yr.count("surrogate"));// 345
        assertEquals(345, yr.count("surrogate"));// 345

        Collection<String> words = yr.words();
        Collection<String> result1 = new ArrayList<String>();

        result1.add("quayside");
        result1.add("what");
        result1.add("merchantman2");
        result1.add("merchantman1");
        result1.add("merchantman");
        result1.add("surrogate");

        assertEquals(result1.size(), words.size());

        Iterator<String> it1 = words.iterator();
        Iterator<String> it2 = result1.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            assertEquals(it1.next(), it2.next());
        }

        Collection<Number> counts = yr.counts();
        Collection<Number> result2 = new ArrayList<Number>();
        result2.add(95);
        result2.add(120);
        result2.add(181);
        result2.add(181);
        result2.add(181);
        result2.add(345);

        assertEquals(result2.size(), counts.size());

        Iterator<Number> it3 = counts.iterator();
        Iterator<Number> it4 = result2.iterator();

        while (it3.hasNext() && it4.hasNext()) {
            assertEquals(it3.next(), it4.next());
        }

    }

    @Test
    public void copyConstructorTest() {
        HashMap<String, Integer> rawData = new HashMap<String, Integer>();
        rawData.put("berry", 1290);
        rawData.put("auscultating", 6);
        rawData.put("temporariness", 20);
        rawData.put("puppetry", 191);
        YearlyRecord yr2 = new YearlyRecord(rawData);
        assertEquals(4, yr2.rank("auscultating")); // should print 4
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(YearlyRecordTest.class);
    }
}

// public class YearlyRecordTest {
// public static void main(String[] args) {
// YearlyRecord yr = new YearlyRecord();
// yr.put("quayside", 95);
// yr.put("surrogate", 340);
// yr.put("surrogate", 345);
// yr.put("merchantman", 181);
// yr.put("merchantman1", 181);
// yr.put("merchantman2", 181);
// yr.put("what", 120);
// System.out.println("Test rank and size");
// System.out.println(yr.rank("surrogate")); // should print 1
// System.out.println(yr.rank("merchantman")); // should print 2
// System.out.println(yr.rank("merchantman1")); // should print 3
// System.out.println(yr.rank("merchantman2")); // should print 4
// System.out.println(yr.rank("what")); // should print 5
// System.out.println(yr.rank("quayside")); // should print 6
// System.out.println(yr.size()); // should print 6
// System.out.println(yr.count("surrogate"));//340
// System.out.println(yr.count("surrogate"));//340
// System.out.println(yr.count("surrogate"));//340
// System.out.println(yr.count("surrogate"));

// Collection<String> words = yr.words();
// System.out.println(words);

// Collection<Number> counts = yr.counts();
// System.out.println(counts);

// HashMap<String, Integer> rawData = new HashMap<String, Integer>();
// rawData.put("berry", 1290);
// rawData.put("auscultating", 6);
// rawData.put("temporariness", 20);
// rawData.put("puppetry", 191);
// YearlyRecord yr2 = new YearlyRecord(rawData);
// System.out.println(yr2.rank("auscultating")); // should print 4
// }
// }
