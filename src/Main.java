import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main {
    public static ArrayList<Cricket> matches = new ArrayList<>();
    public static final int matchLength = 20;
    public static final File directory = new File("res/t20s/");

    public static void main(String[] args) {
        readData();
        DecimalFormat numberFormatter = new DecimalFormat("#0.000");
        for (double i : firstInnings_meanScoreFrequency(matches))
            // System.out.println(i);
            System.out.println(numberFormatter.format(i));
        // Cricket c = matches.get(0);
        // System.out.println(c.toString());
        // Cricket.printInnings(c.firstInnings);
    }
    private static void readData() {
        for (File file : directory.listFiles()) {
            try {
                if (file.getName().endsWith(".yaml")) {
                    matches.add(new Cricket(file));
                }
            } catch (NullPointerException e) { }
        }
    }
    private static ArrayList<Integer> firstInnings_totals(ArrayList<Cricket> matches) {
        ArrayList<Integer> totals = new ArrayList<>();
        for (Cricket c : matches) {
            totals.add(c.firstInnings_total);
        }
        return totals;
    }
    private static ArrayList<Integer> secondInnings_totals(ArrayList<Cricket> matches) {
        ArrayList<Integer> totals = new ArrayList<>();
        for (Cricket c : matches) {
            totals.add(c.secondInnings_total);
        }
        return totals;
    }
    private static ArrayList<Double> firstInnings_giniIndex(ArrayList<Cricket> matches) {
        ArrayList<Double> gini = new ArrayList<>();
        for (Cricket c : matches) {
            gini.add(c.firstInnings_giniIndex);
        }
        return gini;
    }
    private static ArrayList<Double> secondInnings_giniIndex(ArrayList<Cricket> matches) {
        ArrayList<Double> gini = new ArrayList<>();
        for (Cricket c : matches) {
            gini.add(c.secondInnings_giniIndex);
        }
        return gini;
    }
    private static ArrayList<Character> secondInnings_outcome(ArrayList<Cricket> matches) {
        ArrayList<Character> outcomes = new ArrayList<>();
        for (Cricket c : matches) {
            if (c.secondInnings_total > c.firstInnings_total) { outcomes.add('W'); }
            else if (c.firstInnings_total > c.secondInnings_total) { outcomes.add('L'); }
            else { outcomes.add('D'); }
        }
        return outcomes;
    }
    private static int[] winFrequencyDistribution(ArrayList<Cricket> matches) {
        int[] winFrequency = new int[20];
        for (Cricket c : matches) {
            if (c.secondInnings_total < c.firstInnings_total) {
                if (c.secondInnings_giniIndex <= 0.05) { winFrequency[0]++; }
                else if (c.secondInnings_giniIndex <= 0.1) { winFrequency[1]++; }
                else if (c.secondInnings_giniIndex <= 0.15) { winFrequency[2]++; }
                else if (c.secondInnings_giniIndex <= 0.2) { winFrequency[3]++; }
                else if (c.secondInnings_giniIndex <= 0.25) { winFrequency[4]++; }
                else if (c.secondInnings_giniIndex <= 0.3) { winFrequency[5]++; }
                else if (c.secondInnings_giniIndex <= 0.35) { winFrequency[6]++; }
                else if (c.secondInnings_giniIndex <= 0.4) { winFrequency[7]++; }
                else if (c.secondInnings_giniIndex <= 0.45) { winFrequency[8]++; }
                else if (c.secondInnings_giniIndex <= 0.5) { winFrequency[9]++; }
                else if (c.secondInnings_giniIndex <= 0.55) { winFrequency[10]++; }
                else if (c.secondInnings_giniIndex <= 0.6) { winFrequency[11]++; }
                else if (c.secondInnings_giniIndex <= 0.65) { winFrequency[12]++; }
                else if (c.secondInnings_giniIndex <= 0.7) { winFrequency[13]++; }
                else if (c.secondInnings_giniIndex <= 0.75) { winFrequency[14]++; }
                else if (c.secondInnings_giniIndex <= 0.8) { winFrequency[15]++; }
                else if (c.secondInnings_giniIndex <= 0.85) { winFrequency[16]++; }
                else if (c.secondInnings_giniIndex <= 0.9) { winFrequency[17]++; }
                else if (c.secondInnings_giniIndex <= 0.95) { winFrequency[18]++; }
                else if (c.secondInnings_giniIndex <= 1.0) { winFrequency[19]++; }
            }
        }
        return winFrequency;
    }
    private static int[] lossFrequencyDistribution(ArrayList<Cricket> matches) {
        int[] lossFrequency = new int[20];
        for (Cricket c : matches) {
            if (c.secondInnings_total > c.firstInnings_total) {
                if (c.secondInnings_giniIndex <= 0.05) { lossFrequency[0]++; }
                else if (c.secondInnings_giniIndex <= 0.1) { lossFrequency[1]++; }
                else if (c.secondInnings_giniIndex <= 0.15) { lossFrequency[2]++; }
                else if (c.secondInnings_giniIndex <= 0.2) { lossFrequency[3]++; }
                else if (c.secondInnings_giniIndex <= 0.25) { lossFrequency[4]++; }
                else if (c.secondInnings_giniIndex <= 0.3) { lossFrequency[5]++; }
                else if (c.secondInnings_giniIndex <= 0.35) { lossFrequency[6]++; }
                else if (c.secondInnings_giniIndex <= 0.4) { lossFrequency[7]++; }
                else if (c.secondInnings_giniIndex <= 0.45) { lossFrequency[8]++; }
                else if (c.secondInnings_giniIndex <= 0.5) { lossFrequency[9]++; }
                else if (c.secondInnings_giniIndex <= 0.55) { lossFrequency[10]++; }
                else if (c.secondInnings_giniIndex <= 0.6) { lossFrequency[11]++; }
                else if (c.secondInnings_giniIndex <= 0.65) { lossFrequency[12]++; }
                else if (c.secondInnings_giniIndex <= 0.7) { lossFrequency[13]++; }
                else if (c.secondInnings_giniIndex <= 0.75) { lossFrequency[14]++; }
                else if (c.secondInnings_giniIndex <= 0.8) { lossFrequency[15]++; }
                else if (c.secondInnings_giniIndex <= 0.85) { lossFrequency[16]++; }
                else if (c.secondInnings_giniIndex <= 0.9) { lossFrequency[17]++; }
                else if (c.secondInnings_giniIndex <= 0.95) { lossFrequency[18]++; }
                else if (c.secondInnings_giniIndex <= 1.0) { lossFrequency[19]++; }
            }
        }
        return lossFrequency;
    }
    private static double[] firstInnings_meanScoreFrequency(ArrayList<Cricket> matches) {
        int[] scoreSumFrequency = new int[20];
        int[] matchFrequency = new int[20];
        for (Cricket c : matches) {
            if (c.firstInnings_giniIndex <= 0.05) {
                matchFrequency[0]++;
                scoreSumFrequency[0] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.1) {
                matchFrequency[1]++;
                scoreSumFrequency[1] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.15) {
                matchFrequency[2]++;
                scoreSumFrequency[2] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.2) {
                matchFrequency[3]++;
                scoreSumFrequency[3] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.25) {
                matchFrequency[4]++;
                scoreSumFrequency[4] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.3) {
                matchFrequency[5]++;
                scoreSumFrequency[5] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.35) {
                matchFrequency[6]++;
                scoreSumFrequency[6] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.4) {
                matchFrequency[7]++;
                scoreSumFrequency[7] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.45) {
                matchFrequency[8]++;
                scoreSumFrequency[8] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.5) {
                matchFrequency[9]++;
                scoreSumFrequency[9] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.55) {
                matchFrequency[10]++;
                scoreSumFrequency[10] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.6) {
                matchFrequency[11]++;
                scoreSumFrequency[11] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.65) {
                matchFrequency[12]++;
                scoreSumFrequency[12] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.7) {
                matchFrequency[13]++;
                scoreSumFrequency[13] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.75) {
                matchFrequency[14]++;
                scoreSumFrequency[14] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.8) {
                matchFrequency[15]++;
                scoreSumFrequency[15] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.85) {
                matchFrequency[16]++;
                scoreSumFrequency[16] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.9) {
                matchFrequency[17]++;
                scoreSumFrequency[17] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 0.95) {
                matchFrequency[18]++;
                scoreSumFrequency[18] += c.firstInnings_total;
            }
            else if (c.firstInnings_giniIndex <= 1.0) {
                matchFrequency[18]++;
                scoreSumFrequency[18] += c.firstInnings_total;
            }
        }
        double[] meanScoreFrequency = new double[20];
        for (int i = 0; i < 20; i++) {
            try {
                meanScoreFrequency[i] = (double) scoreSumFrequency[i] / matchFrequency[i];
            } catch (ArithmeticException e) {
                meanScoreFrequency[i] = 0;
            }
        }
        return meanScoreFrequency;
    }
    private static double[] secondInnings_meanScoreFrequency(ArrayList<Cricket> matches) {
        int[] scoreSumFrequency = new int[20];
        int[] matchFrequency = new int[20];
        for (Cricket c : matches) {
            if (c.secondInnings_giniIndex <= 0.05) {
                matchFrequency[0]++;
                scoreSumFrequency[0] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.1) {
                matchFrequency[1]++;
                scoreSumFrequency[1] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.15) {
                matchFrequency[2]++;
                scoreSumFrequency[2] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.2) {
                matchFrequency[3]++;
                scoreSumFrequency[3] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.25) {
                matchFrequency[4]++;
                scoreSumFrequency[4] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.3) {
                matchFrequency[5]++;
                scoreSumFrequency[5] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.35) {
                matchFrequency[6]++;
                scoreSumFrequency[6] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.4) {
                matchFrequency[7]++;
                scoreSumFrequency[7] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.45) {
                matchFrequency[8]++;
                scoreSumFrequency[8] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.5) {
                matchFrequency[9]++;
                scoreSumFrequency[9] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.55) {
                matchFrequency[10]++;
                scoreSumFrequency[10] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.6) {
                matchFrequency[11]++;
                scoreSumFrequency[11] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.65) {
                matchFrequency[12]++;
                scoreSumFrequency[12] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.7) {
                matchFrequency[13]++;
                scoreSumFrequency[13] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.75) {
                matchFrequency[14]++;
                scoreSumFrequency[14] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.8) {
                matchFrequency[15]++;
                scoreSumFrequency[15] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.85) {
                matchFrequency[16]++;
                scoreSumFrequency[16] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.9) {
                matchFrequency[17]++;
                scoreSumFrequency[17] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 0.95) {
                matchFrequency[18]++;
                scoreSumFrequency[18] += c.secondInnings_total;
            }
            else if (c.secondInnings_giniIndex <= 1.0) {
                matchFrequency[18]++;
                scoreSumFrequency[18] += c.secondInnings_total;
            }
        }
        double[] meanScoreFrequency = new double[20];
        for (int i = 0; i < 20; i++) {
            try {
                meanScoreFrequency[i] = (double) scoreSumFrequency[i] / matchFrequency[i];
            } catch (ArithmeticException e) {
                meanScoreFrequency[i] = 0;
            }
        }
        return meanScoreFrequency;
    }
    private static int[] tossDecision_Frequency(ArrayList<Cricket> matches) {
        int[] frequency = new int[2];
        for (Cricket c : matches) {
            if (c.tossDecision.equals("bat")) { frequency[0]++; }
            else if (c.tossDecision.equals("field")) { frequency[1]++; }
        }
        return frequency;
    }
    private static int[] tossDecision_outcomeFrequency(ArrayList<Cricket> matches) {
        int[] frequency = new int[4];
        for (Cricket c : matches) {
            if (c.tossDecision.equals("bat")) {
                if (c.firstInnings_total > c.secondInnings_total)
                    frequency[0]++;
                else
                    frequency[1]++;
            } else if (c.tossDecision.equals("field")) {
                if (c.firstInnings_total > c.secondInnings_total)
                    frequency[2]++;
                else
                    frequency[3]++;
            }
        }
        return frequency;
    }
}
