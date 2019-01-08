package ngordnet;

import edu.princeton.cs.introcs.In;
import java.util.HashMap;
import java.util.Collection;

/**
 * @author Xizhao Deng
 */

public class NGramMap {

    private HashMap<String, HashMap<Integer, Integer>> wycMap;
    private TimeSeries<Long> ycSeries;

    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        wycMap = new HashMap<String, HashMap<Integer, Integer>>();
        ycSeries = new TimeSeries<Long>();

        In inW = new In(wordsFilename);
        In inC = new In(countsFilename);
        String[] aLine;
        Integer theYear;
        Integer theCount;
        Long theCount2;

        while (!inW.isEmpty()) {
            aLine = inW.readLine().split("\t");
            theYear = Integer.parseInt(aLine[1]);
            theCount = Integer.parseInt(aLine[2]);

            if (wycMap.containsKey(aLine[0])) {
                wycMap.get(aLine[0]).put(theYear, theCount);
            } else {
                HashMap<Integer, Integer> ycMap = new HashMap<Integer, Integer>();
                ycMap.put(theYear, theCount);
                wycMap.put(aLine[0], ycMap);
            }
        }

        while (!inC.isEmpty()) {
            aLine = inC.readLine().split(",");
            theYear = Integer.parseInt(aLine[0]);
            theCount2 = Long.parseLong(aLine[1]);
            ycSeries.put(theYear, theCount2);
        }
    }

    /**
     * Returns the absolute count of WORD in the given YEAR. If the word did not
     * appear in the given year, return 0.
     */
    public int countInYear(String word, int year) {
        if (wycMap.containsKey(word)) {
            if (wycMap.get(word).containsKey(year)) {
                return wycMap.get(word).get(year);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /** Returns a defensive copy of the YearlyRecord of WORD. */
    public YearlyRecord getRecord(int year) {
        YearlyRecord yrRecord = new YearlyRecord();

        for (String K : wycMap.keySet()) {
            if (wycMap.get(K).containsKey(year)) {
                yrRecord.put(K, wycMap.get(K).get(year));
            }
        }
        return yrRecord;
    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        return ycSeries;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear,
            int endYear) {
        TimeSeries<Integer> wcyySeries = new TimeSeries<Integer>();

        if (wycMap.containsKey(word)) {
            for (Integer K : wycMap.get(word).keySet()) {
                if ((K >= startYear) && (K <= endYear)) {
                    wcyySeries.put(K, wycMap.get(word).get(K));
                }
            }
        }
        return wcyySeries;
    }

    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        TimeSeries<Integer> wcSeries = new TimeSeries<Integer>();

        for (Integer K : wycMap.get(word).keySet()) {
            wcSeries.put(K, wycMap.get(word).get(K));
        }

        return wcSeries;
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear,
            int endYear) {
        return countHistory(word, startYear, endYear).dividedBy(ycSeries);
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        return countHistory(word).dividedBy(ycSeries);
    }

    /**
     * Provides the summed relative frequency of all WORDS between STARTYEAR and
     * ENDYEAR.
     */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words,
            int startYear, int endYear) {
        TimeSeries<Double> swHisSeries = new TimeSeries<Double>();

        for (String W : words) {
            if (swHisSeries.isEmpty()) {
                swHisSeries = weightHistory(W, startYear, endYear);
            } else {
                swHisSeries = swHisSeries.plus(weightHistory(W, startYear,
                        endYear));
            }
        }

        return swHisSeries;

    }

    /** Returns the summed relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words) {
        TimeSeries<Double> swHisSeries = new TimeSeries<Double>();

        for (String W : words) {
            swHisSeries = swHisSeries.plus(weightHistory(W));
        }

        return swHisSeries;
    }

    /**
     * Provides processed history of all words between STARTYEAR and ENDYEAR as
     * processed by YRP.
     */
    public TimeSeries<Double> processedHistory(int startYear, int endYear,
            YearlyRecordProcessor yrp) {
        TimeSeries<Double> proHisSeries = new TimeSeries<Double>();

        for (int yr = startYear; yr <= endYear; yr++) {
            if (ycSeries.containsKey(yr)) {
                proHisSeries.put(yr, yrp.process(getRecord(yr)));
            }
        }
        return proHisSeries;
    }

    /** Provides processed history of all words ever as processed by YRP. */
    public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp) {
        TimeSeries<Double> proHisSeries = new TimeSeries<Double>();

        for (int yr : ycSeries.keySet()) {
            proHisSeries.put(yr, yrp.process(getRecord(yr)));
        }

        return proHisSeries;
    }
}
