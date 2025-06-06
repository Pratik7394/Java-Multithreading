package pratik.learning.section3;

import java.math.BigInteger;

public class InterruptDemo {
    public static void main(String[] args) {

//        boolean isBlocking = true; // Change to false to test long computation task
        boolean isBlocking = false; // Change to true to test blocking computation task
        Thread thread;
        if (isBlocking) {
            thread = new Thread(new BlockingTask());

        } else {
            thread = new Thread(new LongComputationTask(new BigInteger("2"), new BigInteger("1000000000000")));
        }
        boolean isDaemon = thread.isDaemon();
        if(!isDaemon) thread.setDaemon(!isDaemon);
        thread.start();
        thread.interrupt();
    }

    private static class BlockingTask implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("Blocking task started on thread " + Thread.currentThread().getName());
                Thread.sleep(10000); // Simulate a long-running task
            } catch (InterruptedException e) {
                System.out.println("Blocking task interrupted on thread " + Thread.currentThread().getName());
            }
        }
    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;
        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }
        @Override
        public void run() {
            System.out.println(base+"^"+power+" = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread is prematurely interrupted");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }
}
