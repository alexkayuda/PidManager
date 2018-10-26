
/**
 * @author Alexander Kayuda
 * Class: CISC 3320 ET6
 * Last Date Modified: 9/11/2018
 *
 * Description: This is a driver class that tests PidManager functionality.
 */

public class PidManagerDemo {
    public static void main(String[] args) {

        PidManager pidManager = PidManager.getInstance();
        pidManager.allocate_map();

        for (int i = 0; i < 100; i++) {
            pidManager.allocate_pid();
        }

        pidManager.printHashMap();

        try{
            Thread.sleep(60000); //60 sec
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            pidManager.printHashMap();
        }

    }
}
