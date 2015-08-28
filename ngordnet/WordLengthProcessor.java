package ngordnet;

import java.util.ArrayList;

/**
 * @author Xizhao Deng
 */

/*
 * average length = (digit_len_w1 * count_w1 + digit_len_w2 * count_w2 + ...)
 * /(sum of the counts)
 */

public class WordLengthProcessor implements YearlyRecordProcessor {
    public double process(YearlyRecord yearlyRecord) {
        ArrayList<String> wArray = (ArrayList<String>) yearlyRecord.words();
        ArrayList<Number> cArray = (ArrayList<Number>) yearlyRecord.counts();
        long totalDigit = 0;
        long totalCount = 0;
        int arraySize = wArray.size();
        double result = 0;

        for (int i = 0; i < arraySize; i++) {
            totalDigit = totalDigit
                    + (long) (cArray.get(i).intValue() * wArray.get(i).length());
            totalCount += (long) (cArray.get(i).intValue());
        }

        result = (double) totalDigit / (double) totalCount;
        return result;
    }
}