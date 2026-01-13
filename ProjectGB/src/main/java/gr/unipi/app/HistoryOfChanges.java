package gr.unipi.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryOfChanges {

    public static void showHistory() {
        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                System.out.println("Αποτυχία σύνδεσης στη βάση δεδομένων!");
                return;
            }

            String sql = """
                SELECT u.Name AS UserName,
                       bic.Category_Name,
                       ch.Old_Amount,
                       ch.New_Amount,
                       ch.Change_Date
                FROM Changes ch
                LEFT JOIN User u ON ch.User_ID = u.User_ID
                LEFT JOIN BudgetItem bi ON ch.Item_ID = bi.Item_ID
                LEFT JOIN BudgetItemCategory bic ON bi.Category_ID = bic.Category_ID
                ORDER BY ch.Change_Date DESC
                """;

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                System.out.printf("%-25s | %-57s | %-17s | %-17s | %-27s |%n",
                        "Όνομα Χρήστη", "Κατηγορία", "Παλιό Ποσό", "Νέο Ποσό", "Ημερομηνία Αλλαγής");
                System.out.println("-".repeat(135));

                while (rs.next()) {
                    String userName = rs.getString("UserName");
                    String categoryName = rs.getString("Category_Name");
                    double oldAmount = rs.getDouble("Old_Amount");
                    double newAmount = rs.getDouble("New_Amount");
                    String date = rs.getString("Change_Date");

                    System.out.printf("%-25s | %-57s | %-17.2f | %-17.2f | %-27s |%n",
                            userName != null ? userName : "Unknown",
                            categoryName != null ? categoryName : "Unknown",
                            oldAmount, newAmount, date);
                }
                System.out.println("-".repeat(135));
            }

        } catch (SQLException e) {
            System.out.println("Σφάλμα κατά την ανάκτηση ιστορικού: " + e.getMessage());
        }
    }
}