package bagoftasks.proto;

import java.rmi.*;
import java.util.*;
import java.sql.*;
import javax.sql.rowset.*;


/** Pour que le callback devienne un objet distribué => extends remote pour avoir la bonne réference pour le client applicatif*/
public interface Callback extends Remote {
  public void sendResult(QueryResult result) throws RemoteException;
}
