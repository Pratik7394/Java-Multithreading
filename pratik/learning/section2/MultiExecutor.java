package pratik.learning.section2;
import java.util.*;


public class MultiExecutor {

    // Add any necessary member variables here
    List<Thread> taskThreads;

    /*
     * @param tasks to be executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        taskThreads = new ArrayList<>();
        for(Runnable t: tasks) {
            taskThreads.add(new Thread(t));
        }
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {

        for(Thread t: this.taskThreads) {
            t.start();
        }
    }

    public static void main(String[] args) {
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            tasks.add(() -> {
                System.out.println("Task " + taskId + " is running on thread " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        MultiExecutor executor = new MultiExecutor(tasks);
        executor.executeAll();
    }
}