package pratik.learning.section3;

import java.math.BigInteger;

public class ComplexCalculation {
    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        BigInteger result;
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        PowerCalculatingThread calc1  = new PowerCalculatingThread(base1, power1);
        PowerCalculatingThread calc2  = new PowerCalculatingThread(base2, power2);
        calc1.start();
        calc2.start();
        calc1.join();
        calc2.join();

        result = calc1.getResult().add(calc2.getResult());
        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;
        private boolean isFinished = false;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            result = calculate();
            isFinished = true;
        }

        private BigInteger calculate() {
            BigInteger result = BigInteger.ONE;
            for(BigInteger i=power; i.compareTo(BigInteger.ZERO) != 0; i=i.subtract(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ComplexCalculation calc = new ComplexCalculation();
        BigInteger base1 = BigInteger.valueOf(2);
        BigInteger power1 = BigInteger.valueOf(3);
        BigInteger base2 = BigInteger.valueOf(3);
        BigInteger power2 = BigInteger.valueOf(2);

        BigInteger result = calc.calculateResult(base1, power1, base2, power2);
        System.out.println("Result: " + result); // Should print 8 + 9 = 17

        if (result.equals(BigInteger.valueOf(17))) {
            System.out.println("Test passed.");
        } else {
            System.out.println("Test failed.");
        }
    }
}