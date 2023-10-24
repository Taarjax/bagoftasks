package bagoftasks.client;

import java.rmi.*;
import bagoftasks.server.*;
import bagoftasks.proto.*;
import java.util.Scanner;

/**
 * Application client class
 */
public class ClientApplicatif {
//  Pour justifier l'asynchronisme du callback, on va lancer 10000 requetes en meme temps
// On va voir que si on lance un worker durant la reception des requetes par le serveur, 
// le worker va traiter la requete et envoyé le callback au client alors que le serveur n'a pas encore fini de recevoir les requetes

// C'est aussi asynchrone, parceque l'envoie des taches est non bloquant, le client peut envoyer des taches sans attendre que le serveur ait fini de recevoir les taches

    public static void main(String[] args) {
        try {
            // Get the reference of the BagOfTasks object loaded in the RMI registry
            BagOfTasks bot = (BagOfTasks) Naming.lookup("bot");
            System.out.println("Entrez votre requête SQL ou appuyez sur 'entrée' pour quitter :");

            Scanner sc = new Scanner(System.in);
            while (true) {
                String userInput = sc.nextLine();

                if (userInput.isEmpty()) {
                    System.out.println("Au revoir !");
                    System.exit(0);
                }

                // Check if the SQL query is valid
                if (isValidSQL(userInput)) {
                    // Create the task with the callback
                    ImplCallback callback = new ImplCallback();
                    ImplTask task = new ImplTask(userInput, callback);
                    bot.submitTask(task);
                } else {
                    System.out.println("Requête invalide. Veuillez essayer à nouveau.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Pour montrer que le serveur gère l'asynchronisme 
    //    public static void main(String[] args) { 
    //         try{
    //             BagOfTasks bot = (BagOfTasks) Naming.lookup("bot");
    //             // Instanciate the callback to get the result of the task
    //             ImplCallback callback = new ImplCallback();
                
    //             //Send 10000 tasks
    //             for (int i = 0; i < 10; i++) {
    //                 ImplTask task = new ImplTask(i,"Select * from client", callback);
    //                 bot.submitTask(task);
    //             }
                
    //             // Submit the task to the BagOfTasks
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //    }


    private static boolean isValidSQL(String query) {
        String lowerCaseQuery = query.trim().toLowerCase();
        return lowerCaseQuery.startsWith("select") || lowerCaseQuery.startsWith("update") || 
               lowerCaseQuery.startsWith("delete") || lowerCaseQuery.startsWith("insert");
    }
}
