package edu.coursera.parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinReciprocalSum {

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
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4");

        SumArray t = new SumArray(x, 0, x.length);
        ForkJoinPool.commonPool().invoke(t);

        double sum = t.ans;

        long timeInNanos = System.nanoTime() - startTime;
        printResults("parArraySum", timeInNanos, sum);

        return sum;
    }

    public static void printResults(String name, long timeInNanos, double sum) {
        System.out.printf("%s completed in %8.3f milliseconds, with sum = %8.5f%n", name, timeInNanos / 1e6, sum);
    }

    private static class SumArray extends RecursiveAction {
        static int SEQUENTIAL_THRESHOLD = 1000000;

        int lo;
        int hi;
        double[] arr;

        double ans = 0;

        SumArray(double[] a, int l, int h) {
            lo = l;
            hi = h;
            arr = a;
        }

        protected void compute() {
            if (hi - lo <= SEQUENTIAL_THRESHOLD) {
                for (int i = lo; i < hi; ++i) {
                    ans += 1 / arr[i];
                }
            } else {
                SumArray left = new SumArray(arr, lo, (hi + lo) / 2);
                SumArray right = new SumArray(arr, (hi + lo) / 2, hi);

                left.fork();
                right.compute();
                left.join();
                ans = left.ans + right.ans;
            }
        }
    }
}
