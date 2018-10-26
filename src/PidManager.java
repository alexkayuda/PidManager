import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexander Kayuda
 * Class: CISC 3320 ET6
 * Last Date Modified: 10/17/2018
 *
 * Description: The purpose of this class is to simulate an operating system's pid manager
 * that is responsible for managing process identifiers. 3 functions implemented below will manage
 * processes and perform basic validation.
 *
 * Note: This class was designed as a singlton because there is only 1 pid manager in the system.
 */
public class PidManager {

    private int MIN_PID = 300;
    private int MAX_PID = 5000;

    private static PidManager instance = new PidManager();

    private ConcurrentHashMap<Integer,Integer> map;

    private PidManager(){  }

    public static PidManager getInstance() { return instance; }

    /**
     * Allocates memory for pid manager to track what processes and how many of them are running.
     * Data Structure Used: HashMap where both keys and values are of type Integer.
     * Note: Our program can not reach HashMap capacity limit ( which is 2^31 -1 ) because there are
     *       only 4701 (5000 - 300 + 1) possible processes.
     *
     * @return value of 1 if the process completes successfully and -1 otherwise.
     */
    int allocate_map(){
        if(map == null) {
            map = new ConcurrentHashMap<>();
            System.out.println("Memory for Pid Manager was successfully allocated!");
            return 1;
        } else {
            System.out.println("\n\t############### ERROR ###############\n");
            System.out.println("\tMemory was already allocated for pid manager.\n");
            System.out.println("\t#####################################\n");
            return -1;
        }
    }

    /**
     * Starts the process and assigns a unique pid id number (300 <= pid number <= 5000)
     * Each process runs on a separate thread from random number of time.
     *
     * @return value of 1 if the process completes successfully and -1 otherwise.
     */
    int allocate_pid(){
        if(map.isEmpty()){
            map.put(MIN_PID, 1);
            int firstKey = map.keySet().stream().findFirst().get();
            new MyThread( ("" + (firstKey)) );
            System.out.println("Process with ID: " + firstKey + " has started");
            return firstKey;
        }

        if(map.size() == (MAX_PID - MIN_PID + 1)){
            System.out.println("Unable to allocate. All pids are in use.");
            return -1;
        }

        //find the smallest available key between 300 and 5000
        // that is not taken and assigned task to it.
        int i = 0;
        for (; i < map.size(); i++){
            if(!map.containsKey(MIN_PID + i)) break;
        }

        new MyThread( ("" + (MIN_PID + i)) );

        map.put(MIN_PID + i, 1);

        System.out.println("Process with ID: " + (MIN_PID + i) + " has started");

        return MIN_PID + i;
    }

    /**
     * Stops and releases pid of the process whose id will be provided by a user.
     *
     * @implNote According to the assignment requirements, the return type of this
     * function should be <code>void</code>. However, I took different approach and
     * implemented this function returning type <code>int</code>. The returned value
     * represents the status of the operation (1 if operation succeeded; otherwise, -1)
     *
     * @param pidNumber is a unique key what was assigned to a process when it was started.
     * @return value of 1 if the process completes successfully and -1 otherwise.
     */
    int release_pid(int pidNumber){
        if(map.get(pidNumber) == null){
            System.out.println("No such process is running.");
            return -1;
        } else {
            map.remove(pidNumber);
            System.out.println("Process with ID: "+ pidNumber + " was successfully stopped.");
            return 1;
        }
    }


    /**
     * FOR TESTING PURPOSE (Not a part of the requirement)
     */
    public int pidManagerCapacity(){
        return (MAX_PID - MIN_PID + 1);
    }

    /**
     * FOR TESTING PURPOSE (Not a part of the requirement)
     */
    public void printHashMap(){
        if(map.size() == 0){
            System.out.println("\n\tMap is Empty. Nothing to print.\n");
        } else {
            System.out.println("\n-----------------------------------");

            for (Integer element: map.keySet())
                System.out.println("Process with ID: "+ element + " is currently running");

            System.out.println("-----------------------------------\n");
        }
    }
}
