
package bagoftasks.server;

import bagoftasks.proto.*;

import java.util.*;

import java.rmi.server.*;
import java.net.*;
import java.rmi.*;

public class ImplBagOfTasks extends UnicastRemoteObject implements BagOfTasks {
    
    // List of the tasks to do
    private Queue<Task> listTask;
    // List of the task which was get by a worker and not finished yet
    private Queue<Task> listInqueuTask;

    public ImplBagOfTasks() throws RemoteException {
        this.listTask = new LinkedList<Task>();
        this.listInqueuTask = new LinkedList<Task>();
    }

    /**
     * Fonction qui est executer une fois que le worker a fini sa tache
     * 
     */
    public void addResult(Task t) throws RemoteException { 
        listInqueuTask.remove(t);
        System.out.println("Tache fini");
    }

    /**
     * Fonction qui permet d'ajouter une tache dans la liste des taches
     * et de la retourner au worker
     */
    public Task getTask() throws RemoteException {
        Task task = null;

        // If there is a task in the list of tasks to dos
        if(listTask.size() > 0){
            // We get the first task of the list
            task = listTask.poll();

            // We add the task in the list of tasks to do
            listInqueuTask.add(task);

            // We print the size of the list of tasks to do
            System.out.println("Nombre de taches restantes : " + listTask.size());
        } else {
            System.out.println("Liste des taches vide");
        }

        return (Task) task;
    }

    /** 
     * Function to add a task in the list of tasks, this function is called by the client when 
     * he want to submit a task to the server
     */
    public Task submitTask(Task task) throws RemoteException {
        System.out.println("Tache reçu par le serveur");

        listTask.add(task);
        return (Task) task;
    }

}
