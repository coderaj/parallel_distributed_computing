package edu.coursera.parallel;

import junit.framework.TestCase;

public class ForkJoinReciprocalSumTest extends TestCase {
    public void testSetup(){
        int LARGE_SIZE = 1000000;
        double x[] = new double[LARGE_SIZE];

        for(int i = 0; i < LARGE_SIZE; i++){
            x[i] = (double)(Math.random() * LARGE_SIZE);
        }

        ForkJoinReciprocalSum.seqArraySum(x);
        ForkJoinReciprocalSum.parArraySum(x);
    }
}
