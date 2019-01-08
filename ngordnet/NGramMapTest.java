package ngordnet;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/** Provides examples of using the NGramMap class.
 *  @author Xizhao Deng
 */

public class NGramMapTest {

    private NGramMap ngm = new NGramMap("./ngrams/words_that_start_with_q.csv",
            "./ngrams/total_counts.csv");

    @Test
    public void countInYearTest() {
        assertEquals(139, ngm.countInYear("quantity", 1736));
    }

    @Test
    public void countTest() {
        YearlyRecord yr = ngm.getRecord(1736);
        assertEquals(139, yr.count("quantity"));
    }

    @Test
    public void countHistoryTest() {
        TimeSeries<Integer> countHistory = ngm.countHistory("quantity");
        assertEquals(139, (int) countHistory.get(1736)); // should print 139

        TimeSeries<Integer> countHistory2 = ngm.countHistory("quantity", 1589,
                1800);
        assertEquals(139, (int) countHistory2.get(1736)); // should print 139
    }

    @Test
    public void totalCountInYearTest() {
        TimeSeries<Long> totalCountHistory = ngm.totalCountHistory();
        assertEquals(8049773, (long) totalCountHistory.get(1736)); // should
                                                                   // print
                                                                   // 8049773
    }

    @Test
    public void WeightHistoryTest() {
        TimeSeries<Double> weightHistory = ngm.weightHistory("quantity");
        TimeSeries<Long> totalCountHistory = ngm.totalCountHistory();
        TimeSeries<Integer> countHistory = ngm.countHistory("quantity");

        assertEquals(1.7267E-5, (double) weightHistory.get(1736), 0.0001); // should
                                                                           // print
                                                                           // roughly
        // 1.7267E-5

        assertEquals(
                1.7267E-5,
                (double) ((double) countHistory.get(1736) / (double) totalCountHistory
                        .get(1736)), 0.0001); // should print roughly
        // 1.7267E-5

        TimeSeries<Double> weightHistory2 = ngm.weightHistory("quantity", 1570,
                1800);
        assertEquals(1.7267E-5, (double) weightHistory2.get(1736), 0.0001); // should
                                                                            // print
                                                                            // roughly
        // 1.7267E-5
    }

    @Test
    public void summedWeightHistoryTest() {
        ArrayList<String> words = new ArrayList<String>();
        words.add("quantity");
        words.add("quality");

        TimeSeries<Double> sum = ngm.summedWeightHistory(words);
        assertEquals(3.875E-5, (double) sum.get(1736), 0.001); // should print
                                                               // roughly
                                                               // 3.875E-5

        TimeSeries<Double> sum2 = ngm.summedWeightHistory(words, 1560, 1800);
        assertEquals(3.875E-5, (double) sum2.get(1736), 0.001); // should print
                                                                // roughly
                                                                // 3.875E-5
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(NGramMapTest.class);
    }
}
