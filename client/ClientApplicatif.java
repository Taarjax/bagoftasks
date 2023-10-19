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

            //  Pour justifier l'asynchronisme du callback, on va lancer 10000 requetes en meme temps
            // On va voir que si on lance un worker durant la reception des requetes par le serveur, 
            // le worker va traiter la requete et envoyé le callback au client alors que le serveur n'a pas encore fini de recevoir les requetes
            for(int i = 1; i <= 10000; i++){
               bot.submitTask(task, i);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    
}
