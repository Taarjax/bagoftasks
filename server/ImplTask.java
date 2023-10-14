
package bagoftasks.server;

import bagoftasks.proto.*;

import java.util.*;
import java.rmi.server .*;
import java.net .*;
import java.rmi .*;
import java.io.*;

import javax.sql.rowset.*;

import java.sql.*;



/**
 * La tache est en serializable car nous souhaitons que ce soit le client qui supporte la charge de la requete et non le serveur avec Remote.
 * Ce sont les workers qui vont faire le travail.
 */
public class ImplTask implements Serializable, Task {
    private String request = "";
    private Callback callback;

    public ImplTask(String _request, Callback _callback) {
        this.request = _request;
        this.callback = _callback;
    }

    /**
     * This method is called by the worker to execute the task.
     */
    public void run() {
        try{
            // DB connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DatabaseConnection.getConnection();


            // Query
            PreparedStatement preparedStmt = connection.prepareStatement(this.request);
            ResultSet rs = preparedStmt.executeQuery();

            // Store the result in an array to callback the client
            ArrayList<Object> result = new ArrayList<Object>();

            while(rs.next()) { // While there is a result (a row in the table)

                Integer id = rs.getInt("id");
                String solde = rs.getString("solde");

                result.add(id);
                result.add(solde);
                
            }
            
            // Callback the client with the result
            try {
                this.callback.sendResult(result);
            } catch (RemoteException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de l'appel de la méthode sendResult sur l'objet Callback");
            }

            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(rs);

            rs.close();
            DatabaseConnection.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion à la base de données");
        }
    }
}
