package edu.coursera.parallel;

import junit.framework.TestCase;

public class ReciprocalArraySumTest extends TestCase {
    public void testSetup(){
        int LARGE_SIZE = 100000;
        double x[] = new double[LARGE_SIZE];

        for(int i = 0; i < LARGE_SIZE; i++){
            x[i] = (double)(Math.random() * LARGE_SIZE);
        }
        
        ReciprocalArraySum.seqArraySum(x);
        ReciprocalArraySum.parArraySum(x);
    }
}
