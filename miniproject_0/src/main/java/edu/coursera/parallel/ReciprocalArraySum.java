package edu.coursera.parallel;

import static edu.rice.pcdp.PCDP.async;
import static edu.rice.pcdp.PCDP.finish;

public class ReciprocalArraySum {
    static double sum1, sum2;

    public static double seqArraySum(double[] x) {
        long startTime = System.nanoTime();

        sum1 = 0;
        sum2 = 0;

        for (int i = 1; i < x.length / 2; i++) {
            sum1 += 1 / x[i];
        }

        for (int i = x.length / 2; i < x.length; i++) {
            sum2 += 1 / x[i];
        }

        double sum = sum1 + sum2;
        long timeInNanos = System.nanoTime() - startTime;

        printResults("seqArraySum", timeInNanos, sum);
        return sum;
    }

    public static double parArraySum(double[] x) {
        long startTime = System.nanoTime();

        sum1 = 0;
        sum2 = 0;

        finish(() -> {
            async(() -> {
                for (int i = 1; i < x.length / 2; i++) {
                    sum1 += 1 / x[i];
                }
            });

            for (int i = x.length / 2; i < x.length; i++) {
                sum2 += 1 / x[i];
            }
        });
        double sum = sum1 + sum2;
        long timeInNanos = System.nanoTime() - startTime;

        printResults("parArraySum", timeInNanos, sum);
        return sum;
    }


    public static void printResults(String name, long timeInNanos, double sum) {
        System.out.printf("%s completed in %.3f milliseconds, with sum = %.5f%n", name, timeInNanos / 1e6, sum);
    }

}
