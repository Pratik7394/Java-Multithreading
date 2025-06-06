package pratik.learning.section6;

public class MinMaxMetrics {

    volatile long min, max;

    /**
     * Initializes all member variables
     */
    public MinMaxMetrics() {
        min = Long.MAX_VALUE;
        max = Long.MIN_VALUE;
    }

    /**
     * Adds a new sample to our metrics.
     */
    public synchronized void addSample(long newSample) {
        min = Math.min(min, newSample);
        max = Math.max(max, newSample);
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        return min;
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        return max;
    }

    public static void main(String[] args) {
        MinMaxMetrics metrics = new MinMaxMetrics();
        metrics.addSample(10);
        metrics.addSample(5);
        metrics.addSample(30);
        metrics.addSample(7);

        System.out.println("Min: " + metrics.getMin()); // Expected: 5
        System.out.println("Max: " + metrics.getMax()); // Expected: 30
    }
}
