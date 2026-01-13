package gr.unipi.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_NAME = "budget (2).db";

    public static Connection getConnection() {
        try {
            String url = "jdbc:sqlite:" + DB_NAME;
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Σύνδεση στη βάση " + DB_NAME + " ΟΚ!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Σφάλμα σύνδεσης στη βάση!");
            e.printStackTrace();
            return null;
        }
    }
}
