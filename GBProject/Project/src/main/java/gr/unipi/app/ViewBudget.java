package gr.unipi.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewBudget {

    private Connection conn;  // Αποθηκεύουμε τη σύνδεση

    // Κατασκευαστής ανοίγει τη σύνδεση
    public ViewBudget() {
        this.conn = DatabaseConnection.getConnection();
    }

    // Κεντρική μέθοδος που καλείς από το Menu
    public void showBudgetDetails() {
        int year = 2026;
        if (conn == null) {
            System.out.println("Δεν είναι δυνατή η σύνδεση στη βάση.");
            return;
        }

        System.out.println("=".repeat(87));
        System.out.println("                ΑΝΑΛΥΤΙΚΗ ΠΡΟΒΟΛΗ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ (ΕΛΛΑΔΑ " + year + ")");
        System.out.println("=".repeat(87));

        System.out.println("\n Έσοδα");
        System.out.println("-".repeat(87));
        System.out.printf("%-60s | %15s\n", "Κατηγορία", "Ποσό (€)");
        System.out.println("-".repeat(87));

        double totalRevenue = printCategoryDetails(year, "Revenue");

        System.out.println("-".repeat(87));
        System.out.printf("%-60s | %15.2f €\n", "Σύνολο εσόδων:", totalRevenue);

        System.out.println("\n\n Έξοδα");
        System.out.println("-".repeat(87));
        System.out.printf("%-60s | %15s\n", "Κατηγορία", "Ποσό (€)");
        System.out.println("-".repeat(87));

        double totalExpenses = printCategoryDetails(year, "Expenditure");

        System.out.println("-".repeat(87));
        System.out.printf("%-60s | %15.2f €\n", "Σύνολο εξόδων:", totalExpenses);

        double balance = totalRevenue - totalExpenses;

        System.out.println("=".repeat(87));
        System.out.print("Αποτέλεσμα Κρατικού Προϋπολογισμού (Έσοδα - Έξοδα): ");

        if (balance > 0) {
            System.out.printf("Πλεονασματικός  +%,.2f €\n", balance);
        } else if (balance < 0) {
            System.out.printf("Έλλειμματικός   %,.2f €\n", balance);
        } else {
            System.out.println("Ισοσκελισμένος (0.00 €)");
        }
        System.out.println("=".repeat(87));

        updateBudget(year, totalRevenue, totalExpenses, balance);


        closeConnection(); // Κλείνουμε τη σύνδεση στο τέλος
    }

    // Βοηθητική μέθοδος για τα queries
    private double printCategoryDetails(int year, String type) {
        double totalSum = 0;

        String sql = "SELECT C.Category_Name, I.Amount " +
                "FROM BudgetItem I " +
                "JOIN BudgetItemCategory C ON I.Category_ID = C.Category_ID " +
                "JOIN Budget B ON I.Budget_ID = B.Budget_ID " +
                "WHERE B.Year = ? AND C.Type = ? AND B.Country_ID = 1 " +
                "ORDER BY C.Category_ID ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, year);
            pstmt.setString(2, type);

            ResultSet rs = pstmt.executeQuery();

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                String name = rs.getString("Category_Name");
                double amount = rs.getDouble("Amount");
                totalSum += amount;

                System.out.printf("%-60s | %,15.2f\n", name, amount);
            }

            if (!hasData) {
                System.out.println("(Δεν βρέθηκαν καταχωρημένα κονδύλια για αυτή την κατηγορία)");
            }

        } catch (SQLException e) {
            System.out.println("Σφάλμα βάσης δεδομένων: " + e.getMessage());
            e.printStackTrace();
        }

        return totalSum;
    }

    // Κλείσιμο σύνδεσης
    private void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Η σύνδεση με τη βάση έκλεισε.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateBudget(int year, double totalRevenue, double totalExpenses, double balance) {
        String sql =
                "UPDATE Budget " +
                        "SET Total_Revenue = ?, " +
                        "    Total_Expenses = ?, " +
                        "    Balance = ? " +
                        "WHERE Year = ? AND Country_ID = 1";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, totalRevenue);
            pstmt.setDouble(2, totalExpenses);
            pstmt.setDouble(3, balance);
            pstmt.setInt(4, year);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Ο προϋπολογισμός ενημερώθηκε στη βάση.");
            } else {
                System.out.println("Δεν βρέθηκε εγγραφή Budget για ενημέρωση.");
            }

        } catch (SQLException e) {
            System.out.println("Σφάλμα ενημέρωσης Budget: " + e.getMessage());
        }
    }
}
