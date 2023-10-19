package bagoftasks.server;

import bagoftasks.proto.*;
import java.rmi.*;

import java.rmi.server.*;
import java.util.*;
import java.sql.*;
import javax.sql.rowset.*;

public class ImplCallback extends UnicastRemoteObject implements Callback {

  public ImplCallback() throws RemoteException {
    super();
  }

  public void sendResult(CachedRowSet crs) throws RemoteException {
    System.out.println("Résultat de la requête : ");
    try {
      while (crs.next()) {
        int id = crs.getInt("id");
        String solde = crs.getString("solde");
        System.out.println("id : " + id + ", solde : " + solde);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
  }

}
