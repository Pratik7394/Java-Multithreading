package pratik.learning.section3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadJoinDemo {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 35435L, 2324L, 4656L, 23L, 5566L);
        List<FactorialThread> threads = new ArrayList<>();
        for(long inputNum : inputNumbers) {
            threads.add(new FactorialThread(inputNum));
        }
        for(Thread thread : threads) {
            thread.start();
        }
        for(Thread thread : threads) {
            thread.join();
        }
        for(int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if(factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }

    private static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ONE;
        private boolean isFinished = false;
        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }
        public BigInteger factorial(long n) {
            BigInteger temp = BigInteger.ONE;
            for(long i = n; i > 0; i--) {
                temp = temp.multiply(BigInteger.valueOf(i));
            }
            return temp;
        }
        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }
        public boolean isFinished() {
            return isFinished;
        }
        public BigInteger getResult() {
            return result;
        }
    }
}
