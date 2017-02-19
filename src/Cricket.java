import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlException;

import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;

import java.util.ArrayList;
import java.util.Map;

public class Cricket {
    public String match, date, team1, team2, tossDecision;
    public int[][] firstInnings, secondInnings;
    public int[] firstInnings_overTotals, secondInnings_overTotals;
    public int firstInnings_total, secondInnings_total;
    public double firstInnings_runRate, secondInnings_runRate, requiredRunRate;
    public double firstInnings_giniIndex, secondInnings_giniIndex;

    public Cricket(File yamlFile) {
        try {
            FileReader fileReader = new FileReader(yamlFile);
            YamlReader reader = new YamlReader(fileReader);
            try {
                Map match = (Map) reader.read();
                ArrayList innings = (ArrayList) match.get("innings");
                try {
                    Map firstInnings = (Map) ((Map) innings.get(0)).get("1st innings");
                    Map secondInnings = (Map) ((Map) innings.get(1)).get("2nd innings");
                    this.firstInnings = inningsBreakdown(firstInnings);
                    this.secondInnings = inningsBreakdown(secondInnings);
                } catch (IndexOutOfBoundsException e) { }

                ArrayList<String> teams = (ArrayList<String>)((Map) match.get("info")).get("teams");
                team1 = teams.get(0);
                team2 = teams.get(1);
                this.match = team1 + " vs. " + team2;

                date = ((ArrayList<String>)((Map)match.get("info")).get("dates")).get(0);
                tossDecision = (String)((Map)((Map) match.get("info")).get("toss")).get("decision");
            } catch (YamlException e) {
                // e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // System.out.println("File not found!\n");
        }

        firstInnings_overTotals = overTotals(firstInnings);
        secondInnings_overTotals = overTotals(secondInnings);

        firstInnings_total = inningsTotal(firstInnings);
        secondInnings_total = inningsTotal(secondInnings);

        firstInnings_runRate = (double) firstInnings_total / Main.matchLength;
        requiredRunRate = (double) (firstInnings_total + 1) / firstInnings_total;
        secondInnings_runRate = (double) secondInnings_total / Main.matchLength;

        firstInnings_giniIndex = getGiniIndex(firstInnings_overTotals);
        secondInnings_giniIndex = getGiniIndex(secondInnings_overTotals);
    }
    private static int[][] inningsBreakdown(Map innings) {
        int[][] inningsBreakdown = new int[Main.matchLength][6];
        ArrayList deliveries = (ArrayList) innings.get("deliveries");
        int totalBalls = 0;
        for (int o = 0; o < Main.matchLength; o++) {
            int[] over = new int[6];
            int realBalls = 1;
            // System.out.print("#" + o + ":\t");
            try {
                for (int b = 1; b <= 6; b++) {
                    int wides = 0;
                    // System.out.print("(" + realBalls + ")(" + b + ") ");
                    Map delivery = (Map)((Map)deliveries.get(totalBalls++)).get(o + "." + realBalls++);
                    try {
                        Map extras = (Map) delivery.get("extras");
                        if (extras.get("wides") != null) {
                            wides = Integer.parseInt((String) extras.get("wides"));
                        } else if (extras.get("noballs") != null) {
                            wides = Integer.parseInt((String) extras.get("noballs"));
                        }
                    } catch (NullPointerException e) { }
                    int runs = Integer.parseInt((String) ((Map) delivery.get("runs")).get("total"));
                    if (wides != 0) {
                        // System.out.print(wides + "(wd)\t");
                        if (b == 6) {
                            over[--b] += wides;
                            over[b] += runs-wides;
                        } else {
                            over[b--] += wides;
                            over[b + 1] += runs - wides;
                        }
                    } else {
                        // System.out.print(runs + "\t\t");
                        over[b - 1] = over[b - 1] + runs;
                    }
                }
                // System.out.println();
                inningsBreakdown[o] = over;
            } catch (IndexOutOfBoundsException e) {
                inningsBreakdown[o] = over;
                break;
            }
        }
        return inningsBreakdown;
    }
    public static void printInnings(int[][] innings) {
        int noOfOvers = 0;
        for (int[] over : innings) {
            System.out.print("Over #" + (noOfOvers+1) + ":\t");
            for (Integer ball : over) {
                System.out.print(ball + "\t\t");
            }
            System.out.print("(" + overTotals(innings)[noOfOvers++] + ")");
            System.out.println();
        }
        System.out.println();
    }
    public static int[] overTotals(int[][] innings) {
        int[] overTotals = new int[Main.matchLength];
        for (int over = 0; over < Main.matchLength; over++) {
            for (int ball : innings[over])
                 overTotals[over] += ball;
        }
        return overTotals;
    }
    public static int inningsTotal(int[][] innings) {
        int total = 0;
        for (int overTotal : overTotals(innings))
            total += overTotal;
        return total;
    }
    @Override
    public String toString() {
        String s = match + " (" + date + ")\n";
        s = s + "Innings #1: \t" + "Score: " + firstInnings_total + "\t\tRun Rate: " + firstInnings_runRate + "\n";
        s = s + "Innings #2: \t" + "Score: " + secondInnings_total + "\t\tRun Rate: " + secondInnings_runRate + "\n";
        return s;
    }
    public static double getGiniIndex(int[] overTotals) {
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(6);
        WeightedObservedPoints points = new WeightedObservedPoints();

        overTotals = sort(overTotals);
        int[] cumulativeOverTotals = new int[overTotals.length];
        for (int i = 1; i < overTotals.length; i++)
            cumulativeOverTotals[i] = cumulativeOverTotals[i-1] + overTotals[i];

        for (int i = 0; i < Main.matchLength; i++) {
            points.add((double)(i+1) / Main.matchLength,
                       (double)cumulativeOverTotals[i] / cumulativeOverTotals[Main.matchLength-1]);
        }
        PolynomialFunction lorenzCurve = new PolynomialFunction(fitter.fit(points.toList()));
        TrapezoidIntegrator integrator = new TrapezoidIntegrator();
        return (1 - 2 * integrator.integrate(Integer.MAX_VALUE, lorenzCurve, 0, 1));
    }
    public static int[] sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j] > array[j+1]) {
                    int temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }
}