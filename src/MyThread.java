import java.util.Random;

/**
 * @author Alexander Kayuda
 * Class: CISC 3320 ET6
 * Last Date Modified: 10/17/2018
 *
 * Description: The purpose of this class is to simulate a running process that requires
 * random number of time to complete. Class implements Runnable interface to create a
 * separate thread, so each process can run independently from each other. Class overrides
 * run() method to implement logic of what thread should do on a background.
 *
 */

public class MyThread implements Runnable {
    private PidManager pidManager = PidManager.getInstance();
    private static Random randomObject = new Random();
    private Thread currentThread;
    private String threadName;
    private int randomNumber;


    /**
     * Constructor. Instantiates and assigns values to instance variables.
     * Creates and starts a thread.
     *
     * @param threadName String representation of pid number assigned for a started process
     *                   Will be used for giving a name to each instance of Thread class.
     */
    public MyThread(String threadName){
        this. randomNumber = randomObject.nextInt(55) + 1; // random number between 1 and 55
        this.threadName = threadName;
        this.currentThread = new Thread(this, this.threadName);

        currentThread.start();
    }

    /**
     * All classes that extend Thread class (or implement Runnable interface) must override
     * the default run() method in order to specify the behavior of the thread.
     *
     * For the purpose of this assignment, we are putting thread to sleep for
     * a random (1 to 15) number of seconds to simulate running processes.
     */
    @Override
    public void run() {
        try{
            Thread.sleep(this.randomNumber * 1000);
        }catch (Exception e){
            System.out.println("Something went wrong");
        } finally {
            //this will kick in only when thread has finished.
            pidManager.release_pid(Integer.parseInt(this.threadName));
        }
    }
}
