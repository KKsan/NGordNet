package ngordnet;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.Stopwatch;
import edu.princeton.cs.introcs.In;

/**
 * Provides a simple user interface for exploring WordNet and NGram data.
 * 
 * @author Xizhao Deng
 */

public class NgordnetUI {
    public static void main(String[] args) {
        int startDate = 0;
        int endDate = 2020;
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        System.out
                .println("\nBased on ngordnetui.config, using the following: \n"
                        + wordFile
                        + ", "
                        + countFile
                        + ", \n"
                        + synsetFile
                        + ", and " + hyponymFile + ".");
        System.out.println("");
        System.out.println(">>>>>>>>>>>>>>>>>>>LOADING<<<<<<<<<<<<<<<<<<<");
        System.out.println("");

        Stopwatch sw = new Stopwatch();

        NGramMap theNGMap = new NGramMap(wordFile, countFile);
        WordNet theWNet = new WordNet(synsetFile, hyponymFile);

        System.out.println(sw.elapsedTime());

        System.out.println(">>>>>>>>>>>>>>>>>>>ONLINE<<<<<<<<<<<<<<<<<<<<");
        System.out.println("");

        while (true) {
            try {
                System.out.print("> ");
                String line = StdIn.readLine();
                String[] rawTokens = line.split(" ");
                String command = rawTokens[0];
                String[] tokens = new String[rawTokens.length - 1];
                System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);

                switch (command) {
                case "quit":
                    return;
                case "help":
                    in = new In("./ngordnet/help.txt");
                    String helpStr = in.readAll();
                    System.out.println(helpStr);
                    break;
                case "range":
                    if ((Integer.parseInt(tokens[0]) < 0)
                            || (Integer.parseInt(tokens[1]) < 0)) {
                        System.out.println("Invalid date inputs");
                        break;
                    }

                    startDate = Integer.parseInt(tokens[0]);
                    endDate = Integer.parseInt(tokens[1]);
                    System.out.println("Start date: " + startDate);
                    System.out.println("End date: " + endDate);
                    break;
                case "count":
                    Integer theYear = Integer.parseInt(tokens[1]);

                    if (theYear < 0) {
                        System.out.println("Invalid date inputs");
                        break;
                    }

                    System.out
                            .println(theNGMap.countInYear(tokens[0], theYear));
                    break;
                case "hyponyms":
                    System.out.println(theWNet.hyponyms(tokens[0]));
                    break;
                case "history":
                    Plotter.plotWeightsHistory(theNGMap, tokens, startDate,
                            endDate);
                    break;
                case "hypohist":
                    Plotter.plotCategoryWeights(theNGMap, theWNet, tokens,
                            startDate, endDate);
                    break;
                case "wordlength":
                    YearlyRecordProcessor yrp = new WordLengthProcessor();
                    Plotter.plotProcessedHistory(theNGMap, startDate, endDate,
                            yrp);
                    break;
                case "zipf":
                    int year = Integer.parseInt(tokens[0]);
                    Plotter.plotZipfsLaw(theNGMap, year);
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("ArrayIndexOutOfBoundsException "
                        + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err
                        .println("IllegalArgumentException " + e.getMessage());
            } catch (NullPointerException e) {
                System.err.println("NullPointerException " + e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.err.println("IndexOutOfBoundsException "
                        + e.getMessage());
            }
        }

    }
}
