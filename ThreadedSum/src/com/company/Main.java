package com.company;
import java.util.Arrays;
import java.util.Random;

public class Main {
    static class Addition extends Thread
    {
        int[] intArray;
        int startIndex;
        int endIndex;
        int total;
        Addition(int[] intArray, int startIndex, int endIndex){
            this.intArray = intArray;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }
        public void run()
        {
            try
            {
                for(int i = startIndex; i < endIndex; i++){
                    total += intArray[i];
            }

            }
            catch (Exception e)
            {
                // Throwing an exception
                System.out.println ("Exception is caught");
            }
        }
        public int getTotal(){
            return total;
        }
    }

    public static void main(String[] args) {
        int oneThreadTotal = 0;
        int multiThreadTotal = 0;

        // create instance of Random class
        Random rand = new Random();
        // create int array of 200,000,000 size
        int[] intArray = new int[200000000];
        // for each int intArray set to random number 1-10
        for(int i = 0; i < 200000000; i++){
            intArray[i] = rand.nextInt(10) + 1;
        }
        // call getSingleThreadTotal
        oneThreadTotal = getSingleThreadTotal(intArray);
        multiThreadTotal = getMultiThreadTotal(intArray);
        System.out.println("Single Thread Total: " + oneThreadTotal);
        System.out.println("Multi Thread Total: " + multiThreadTotal);
    }


    private static int getMultiThreadTotal(int[] intArray) {
        long milliStartTime = System.currentTimeMillis();
        long timeResult;
        int result = 0;

        Addition addOne = new Addition(intArray,0,50000000);
        Addition addTwo = new Addition(intArray,50000000,100000000);
        Addition addThree = new Addition(intArray,100000000,150000000);
        Addition addFour = new Addition(intArray,150000000,200000000);
        addOne.start();
        addTwo.start();
        addThree.start();
        addFour.start();
        try {
            addOne.join();
            addTwo.join();
            addThree.join();
            addFour.join();
        } catch (InterruptedException ie) {}

        result = addOne.getTotal() + addTwo.getTotal() + addThree.getTotal() + addFour.getTotal();

        long milliEndTime = System.currentTimeMillis();
        timeResult = milliEndTime - milliStartTime;
        System.out.println("Multi Thread Time in Milliseconds: "+timeResult);
        return result;
    }

    private static int getSingleThreadTotal(int[] intArray) {
        long milliStart = System.currentTimeMillis();
        int result = 0;
        for (int j: intArray) {
            result += j;
        }
        long milliEnd = System.currentTimeMillis();
        System.out.println("Single Thread Time in Milliseconds: " + (milliEnd - milliStart));
        return result;
    }
}
