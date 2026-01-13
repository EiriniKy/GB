package gr.unipi.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ChangeBudget {

    public static void updateBudgetCategoryAmount(int userId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                throw new SQLException("Αποτυχία σύνδεσης στη βάση δεδομένων.");
            }
            Scanner scanner = new Scanner(System.in);
            if (userId != 5617) {
                System.out.println("Δεν έχετε δικαίωμα να κάνετε αλλαγές στον προϋπολογισμό.");
                return;
            }
            int budgetId = 1;
            System.out.print("Δώσε το όνομα της κατηγορίας (το όνομα της κατηγορίας πρέπει να είναι ακριβώς ίδιο με αυτό που εμφανίζεται στην προβολή του Προϋπολογισμού): ");
            String categoryName = scanner.nextLine();
            System.out.print("Δώσε το νέο ποσό: ");
            double newAmount = scanner.nextDouble();
            // 1. Βρίσκουμε το Item_ID και το παλιό ποσό από το όνομα κατηγορίας
            String selectSql = "SELECT bi.Item_ID, bi.Amount " +
                    "FROM BudgetItem bi " +
                    "JOIN BudgetItemCategory bic ON bi.Category_ID = bic.Category_ID " +
                    "WHERE bi.Budget_ID = ? AND bic.Category_Name = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setInt(1, budgetId);
                selectStmt.setString(2, categoryName);
                ResultSet rs = selectStmt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("Δεν βρέθηκε κατηγορία με το όνομα: " + categoryName + " για τον προϋπολογισμό ID " + budgetId);
                }

                int itemId = rs.getInt("Item_ID");
                double oldAmount = rs.getDouble("Amount");

                // 2. Κάνουμε update στον πίνακα BudgetItem
                String updateSql = "UPDATE BudgetItem SET Amount = ? WHERE Item_ID = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setDouble(1, newAmount);
                    updateStmt.setInt(2, itemId);
                    updateStmt.executeUpdate();
                }

                // 3. Αποθηκεύουμε την αλλαγή στον πίνακα Changes
                String insertChangeSql = "INSERT INTO Changes (Item_ID, User_ID, Old_Amount, New_Amount, Change_Date) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertChangeSql)) {
                    insertStmt.setInt(1, itemId);
                    insertStmt.setInt(2, userId);
                    insertStmt.setDouble(3, oldAmount);
                    insertStmt.setDouble(4, newAmount);
                    // SQLite δεν έχει native datetime τύπο, αποθηκεύουμε σαν TEXT ISO 8601
                    String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    insertStmt.setString(5, now);
                    insertStmt.executeUpdate();
                }
            }
        }
    }
}