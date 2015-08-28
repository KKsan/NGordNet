package ngordnet;

import java.util.TreeMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 *  @author Xizhao Deng
 */



public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {

    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
        super();
        for (Integer K : ts.keySet()) {
            this.put(K, ts.get(K));
        }
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR. inclusive
     * of both end points.
     */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
        super();
        for (Integer K : ts.keySet()) {
            if ((K >= startYear) && (K <= endYear)) {
                this.put(K, ts.get(K));
            }
        }
    }

    /**
     * Returns the years in which this time series is valid. Doesn't really need
     * to be a NavigableSet. This is a private method and you don't have to
     * implement it if you don't want to.
     */
    private NavigableSet<Integer> validYears(int startYear, int endYear) {
        NavigableSet<Integer> nvSet = new TreeSet<Integer>();
        for (Integer K : this.keySet()) {
            if ((K >= startYear) && (K <= endYear)) {
                nvSet.add(K);
            }
        }

        return (NavigableSet<Integer>) nvSet;
    }

    /**
     * Returns the quotient of this time series divided by the relevant value in
     * ts. If ts is missing a key in this time series, return an
     * IllegalArgumentException.
     */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        if (ts.size() < this.size()) {
            throw new IllegalArgumentException("ts is missing a key");
        } else {
            TimeSeries<Double> quotients = new TimeSeries<Double>();

            for (Integer K : this.keySet()) {
                if (!ts.containsKey(K)) {
                    throw new IllegalArgumentException("ts is missing a key");
                }
                double result = this.get(K).doubleValue()
                        / ts.get(K).doubleValue();
                quotients.put(K, result);
            }

            return quotients;
        }

    }

    /**
     * Returns the sum of this time series with the given ts. The result is a a
     * Double time series (for simplicity).
     */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> sums = new TimeSeries<Double>();

        for (Integer K : this.keySet()) {
            if (ts.containsKey(K)) {
                double result = this.get(K).doubleValue()
                        + ts.get(K).doubleValue();
                sums.put(K, result);
            } else {
                sums.put(K, this.get(K).doubleValue());
            }
        }

        for (Integer K : ts.keySet()) {
            if (!this.containsKey(K)) {
                sums.put(K, ts.get(K).doubleValue());
            }
        }

        return sums;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
        Collection<Number> allYears = new ArrayList<Number>();

        for (Integer K : this.keySet()) {
            allYears.add((Number) K);
        }

        return (Collection<Number>) allYears;
    }

    /** Returns all data for this time series (in any order). */
    public Collection<Number> data() {
        Collection<Number> allValues = new ArrayList<Number>();

        for (Integer K : this.keySet()) {
            allValues.add((Number) this.get(K));
        }

        return (Collection<Number>) allValues;
    }
}
