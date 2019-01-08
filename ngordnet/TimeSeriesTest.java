package ngordnet;

import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  @author Xizhao Deng
 */

public class TimeSeriesTest {

    @Test
    public void allTests() {
        TimeSeries<Double> ts = new TimeSeries<Double>();
        ts.put(1992, 3.6);
        ts.put(1993, 9.2);
        ts.put(1994, 15.2);
        ts.put(1995, 16.1);
        ts.put(1996, -15.7);
        ts.put(1997, 17.7);
        ts.put(1998, -10.7);
        ts.put(1999, -12.7);
        TimeSeries<Double> ts_cp = new TimeSeries<Double>(ts);

        assertEquals(-15.7, (double) ts.get(1996), 0.1);
        assertEquals(15.2, (double) ts_cp.get(1994), 0.1);

        Collection<Number> years = ts.years();
        Collection<Number> data = ts.data();

        System.out.println(years);
        System.out.println(data);

        TimeSeries<Integer> ts2 = new TimeSeries<Integer>();
        ts2.put(1991, 10);
        ts2.put(1992, -5);
        ts2.put(1993, 1);

        TimeSeries<Double> tSum = ts.plus(ts2);
        assertEquals(10, (double) tSum.get(1991), 0.1); // should print 10
        assertEquals(-1.4, (double) tSum.get(1992), 0.1); // should print -1.4

        TimeSeries<Double> ts3 = new TimeSeries<Double>();
        ts3.put(1991, 5.0);
        ts3.put(1992, 1.0);
        ts3.put(1993, 100.0);
        ts3.put(1994, 200.0);

        TimeSeries<Double> tQuotient = ts2.dividedBy(ts3);

        assertEquals(2.0, (double) tQuotient.get(1991), 0.1); // should print
                                                              // 2.0
        assertEquals(-5.0, (double) tQuotient.get(1992), 0.1); // should print
                                                               // -5.0
        assertEquals(0.01, (double) tQuotient.get(1993), 0.01); // should print
                                                                // 0.01

        TimeSeries<Double> ts4 = new TimeSeries<Double>();
        TimeSeries<Double> ts5 = new TimeSeries<Double>();
        TimeSeries<Double> tSum2 = ts4.plus(ts5);
        assertEquals(0, tSum2.size());
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(TimeSeriesTest.class);
    }
}
