package bagoftasks.server;

import java.sql.*;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            String login = "ra669654";
            String password = "ra669654";
            String url = "jdbc:oracle:thin:@172.31.16.53:1521:eluard2023";

            connection = DriverManager.getConnection(url, login, password);
        }

        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}
