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

  public void sendResult(QueryResult result) throws RemoteException {
    if (result.isSelectResult()) {
      try {
        CachedRowSet crs = result.getResultSet();
        while (crs.next()) {
          int id = crs.getInt("id");
          String solde = crs.getString("solde");
          System.out.println("id : " + id + ", solde : " + solde);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Nombre de lignes affectées : " + result.getAffectedRows());
    }
  }

}
