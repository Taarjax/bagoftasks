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
    private static int workerId;


    public static void main(String[] args) {
        
        if(args.length > 0 ){
            workerId = Integer.parseInt(args[0]);
        } else {
            workerId = 0;
        }


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
                            System.out.println("Worker " + workerId + " a fini sa tache");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

