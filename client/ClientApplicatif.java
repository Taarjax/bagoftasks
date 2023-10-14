package bagoftasks.client;

import java.rmi.*;
import bagoftasks.server.*;
import bagoftasks.proto.*;

/**
 * Application client class
 */
public class ClientApplicatif {
  public static void main(String[] args) {
    try {
        // Get the reference of the BagOfTasks object load in the RMI registry (To comunicate with the application client and the server)
        BagOfTasks bot = (BagOfTasks) Naming.lookup("bot");
        // Instanciate the callback to get the result of the task
        ImplCallback callback = new ImplCallback();

        ImplTask task = new ImplTask("Select * from client", callback);
        bot.submitTask(task);

    } catch(Exception e) {
        e.printStackTrace();
    }
}
}
