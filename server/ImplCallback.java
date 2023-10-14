package bagoftasks.server;

import bagoftasks.proto.*;
import java.rmi.*;

import java.rmi.server.*;
import java.util.*;

import java.sql.*;
import javax.sql.rowset.*;


public class ImplCallback extends UnicastRemoteObject implements Callback {
// C'est au worker d'appeller d'appeller cette fonction 

  private CachedRowSet crs;

  public ImplCallback() throws RemoteException {
    try {
        this.crs = RowSetProvider.newFactory().createCachedRowSet();
    } catch (Exception e) {
        e.printStackTrace();
    }
  }

  public void sendResult(ArrayList<Object> result) throws RemoteException {
    System.out.println("Resultat recu");
    System.out.println(result);
  }

}
