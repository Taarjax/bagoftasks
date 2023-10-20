package bagoftasks.proto;

import java.rmi.*;


public interface BagOfTasks extends Remote {
    public void addResult(Task t, boolean success) throws RemoteException;
    public Task getTask() throws RemoteException;
    public Task submitTask(Task t) throws RemoteException;
}   
