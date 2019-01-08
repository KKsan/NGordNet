package ngordnet;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.ArrayList;

/**
 * @author Xizhao Deng
 */

public class YearlyRecord {

    private HashMap<String, Integer> wcMap; // key: word value: count
    private HashMap<String, Integer> wrMap; // key: word value: rank
    private TreeMap<Integer, ArrayList<String>> cwMap; // key: count value: word
                                                       // arraylist
    private int totalSize; // total number of words recorded
    private boolean putFlag; // true if put method is used

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        wcMap = new HashMap<String, Integer>();
        wrMap = new HashMap<String, Integer>();
        cwMap = new TreeMap<Integer, ArrayList<String>>();
        totalSize = 0;
        putFlag = false;
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        wcMap = new HashMap<String, Integer>();
        wrMap = new HashMap<String, Integer>();
        cwMap = new TreeMap<Integer, ArrayList<String>>();
        totalSize = 0;
        putFlag = false;

        for (String K : otherCountMap.keySet()) {
            this.put(K, otherCountMap.get(K));
        }
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return wcMap.get(word);
    }

    /**
     * Returns rank of WORD. Most common word is rank 1. If two words have the
     * same rank, break ties arbitrarily. No two words should have the same
     * rank.
     */
    public int rank(String word) {
        if (putFlag) {
            int tempRank = totalSize;
            wrMap = new HashMap<String, Integer>();

            for (Integer K : cwMap.keySet()) {
                for (String W : cwMap.get(K)) {
                    wrMap.put(W, tempRank);
                    tempRank--;
                }
            }
        }
        putFlag = false;
        return wrMap.get(word);
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return totalSize;
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        ArrayList<String> wordArray = new ArrayList<String>();

        for (Integer K : cwMap.keySet()) {
            wordArray.addAll(cwMap.get(K));
        }

        return wordArray;
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        ArrayList<Number> cArray = new ArrayList<Number>();
        for (Integer K : cwMap.keySet()) {
            for (int i = 0; i < cwMap.get(K).size(); i++) {
                cArray.add((Number) K);
            }
        }

        return (Collection<Number>) cArray;
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {

        if (!wcMap.containsKey(word)) {
            totalSize += 1;
        } else {
            int oldCount = wcMap.get(word);
            if (cwMap.get(oldCount).size() == 1) {
                cwMap.remove(oldCount);
            } else {
                cwMap.get(oldCount).remove(word);
            }
        }

        putFlag = true;
        wcMap.put(word, count);

        if (!cwMap.containsKey(count)) {
            ArrayList<String> wArray = new ArrayList<String>();
            wArray.add(word);
            cwMap.put(count, wArray);
        } else {
            cwMap.get(count).add(0, word);
        }
    }
}
