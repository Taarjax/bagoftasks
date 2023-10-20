package bagoftasks.client;

import java.rmi.*;
import bagoftasks.proto.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Worker class
 * 
 * C'est le worker qui fait la tache pas le serveur, ducoup ca permet de pas surchagé le serveur comparé au pattern object factory
 */
public class Worker {
    public static void main(String[] args) {
        try {
            // Get the reference of the BagOfTasks object loaded in the RMI registry
            BagOfTasks bot = (BagOfTasks) Naming.lookup("bot");
            ExecutorService executor = Executors.newCachedThreadPool(); // Create a thread pool

            while (true) {
                Task task = bot.getTask();
                if (task != null) {
                    // Execute the task with a thread from the thread pool
                    executor.submit(() -> {
                        boolean success = task.run();
                        try {
                            bot.addResult(task, success);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    System.out.println("La liste des tâches est vide");
                    break;
                }
            }
            // Stop the thread pool
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

