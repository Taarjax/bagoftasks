package bagoftasks.client;

import java.rmi.*;
import bagoftasks.proto.*;

/**
 * Worker class
 */
public class Worker{
    public static void main(String[] args) {
        try {
            // Get the reference of the BagOfTasks object load in the RMI registry (To comunicate with the application client and the server)
            BagOfTasks bot = (BagOfTasks)Naming.lookup("bot");
            // Get the task to do
            Task task = bot.getTask();

            // If there is a task, run it and remove it from the list of tasks to do
            if (task != null) {
                task.run();
                bot.addResult(task);
            } else {
                System.out.println("La liste des tâches est vide");
            }

        } catch(Exception e) { // Catch all exceptions
            e.printStackTrace();
        }
    }
}
