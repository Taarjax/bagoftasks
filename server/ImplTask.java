
package bagoftasks.server;

import bagoftasks.proto.*;

import java.util.*;
import java.rmi.server.*;
import java.net.*;
import java.rmi.*;
import java.io.*;

import javax.sql.rowset.*;

import java.sql.*;

/**
 * La tache est en serializable car nous souhaitons que ce soit le client qui
 * supporte la charge de la requete et non le serveur avec Remote.
 * Ce sont les workers qui vont faire le travail.
 */
public class ImplTask implements Serializable, Task {
    private Integer id;
    private String request = "";
    private Callback callback;

    public ImplTask( String _request, Callback _callback) {
        this.request = _request;
        this.callback = _callback;
    }

    public ImplTask(Integer _id, String _request, Callback _callback) {
        this.id = _id;
        this.request = _request;
        this.callback = _callback;
    }

    /**
     * This method is called by the worker to execute the task.
     */
    public boolean run() {
        boolean success = false;
        try {

            // If the request is a select
            if (request.trim().toLowerCase().startsWith("select")) {
                // DB connection
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection connection = DatabaseConnection.getConnection();

                // Query
                PreparedStatement preparedStmt = connection.prepareStatement(this.request);
                ResultSet rs = preparedStmt.executeQuery();

                CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
                crs.populate(rs);

                rs.close();

                // Callback the client with the result
                this.callback.sendResult(new QueryResult(crs));
            } else { // INSERT, UPDATE, DELETE
                PreparedStatement preparedStmt = DatabaseConnection.getConnection().prepareStatement(this.request);
                int result = preparedStmt.executeUpdate();

                // Call back with affected rows
                this.callback.sendResult(new QueryResult(result));

            }

            DatabaseConnection.closeConnection();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion à la base de données");
        }

        return success;
    }

    public Integer getId() {
        return this.id;
    }
}
