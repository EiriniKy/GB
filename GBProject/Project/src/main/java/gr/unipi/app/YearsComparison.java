package gr.unipi.app;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class YearsComparison {

    // Κύρια μέθοδος που θα καλεί η main (case "4")
    public static void comparison() {
        System.out.println("Σύγκριση προϋπολογισμού Ελλάδας 2026 με τα έτη 2025, 2024 και 2023.");
        System.out.println("------------------------------------------------------------------");

        // Map<Έτος, BudgetRow>
        Map<Integer, BudgetRow> budgets = new HashMap<>();

        String sql =
                "SELECT b.Year, b.Total_Revenue, b.Total_Expenses, b.Balance " +
                        "FROM Budget b " +
                        "JOIN Country c ON b.Country_ID = c.Country_ID " +
                        "WHERE c.Country_Name = 'Ελλάδα' " +
                        "AND b.Year IN (2023, 2024, 2025, 2026) " +
                        "ORDER BY b.Year;";

        // Χρήση της κλάσης DatabaseConnection
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                System.out.println("Δεν ήταν δυνατή η σύνδεση στη βάση δεδομένων.");
                return;
            }

            while (rs.next()) {
                int year = rs.getInt("Year");
                double revenue  = rs.getDouble("Total_Revenue");
                double expenses = rs.getDouble("Total_Expenses");
                String balanceStr = rs.getString("Balance");

                double balance = 0.0;
                try {
                    balance = Double.parseDouble(balanceStr);
                } catch (NumberFormatException e) {
                    // Αν δεν γίνεται parse, το αφήνουμε 0.0
                }

                budgets.put(year, new BudgetRow(year, revenue, expenses, balance));
            }

        } catch (SQLException e) {
            System.out.println("Σφάλμα βάσης δεδομένων: " + e.getMessage());
            return;
        }

        // Πρέπει οπωσδήποτε να υπάρχει το 2026
        BudgetRow y2026 = budgets.get(2026);
        if (y2026 == null) {
            System.out.println("Δεν βρέθηκαν δεδομένα για το έτος 2026 για την Ελλάδα.");
            return;
        }

        // Εκτύπωση όλων των ετών
        System.out.println("\nΠροϋπολογισμός Ελλάδας ανά έτος:");
        System.out.println("Έτος | Συνολικά Έσοδα      | Συνολικά Έξοδα      | Ισοζύγιο");
        System.out.println("---------------------------------------------------------------");
        printBudgetLine(budgets.get(2023));
        printBudgetLine(budgets.get(2024));
        printBudgetLine(budgets.get(2025));
        printBudgetLine(budgets.get(2026));

        // Σύγκριση 2026 με κάθε προηγούμενο
        int[] previousYears = {2025, 2024, 2023};

        System.out.println("\nΣυγκρίσεις σε σχέση με το 2026:");
        for (int year : previousYears) {
            BudgetRow prev = budgets.get(year);
            if (prev == null) {
                System.out.println("\nΔεν υπάρχουν δεδομένα για το έτος " + year + ".");
                continue;
            }

            System.out.println("\n--- Σύγκριση 2026 με " + year + " ---");
            System.out.printf("Έτος βάσης: %d%n", y2026.year);
            System.out.printf("Έτος σύγκρισης: %d%n", prev.year);

            double diffRevenue  = y2026.totalRevenue  - prev.totalRevenue;
            double diffExpenses = y2026.totalExpenses - prev.totalExpenses;
            double diffBalance  = y2026.balance       - prev.balance;

            System.out.printf("Διαφορά Εσόδων   (2026 - %d): %, .2f%n", year, diffRevenue);
            System.out.printf("Διαφορά Εξόδων   (2026 - %d): %, .2f%n", year, diffExpenses);
            System.out.printf("Διαφορά Ισοζυγίου(2026 - %d): %, .2f%n", year, diffBalance);
        }
    }

    // ---------- Βοηθητικές μέθοδοι / κλάση ----------

    public static void printBudgetLine(BudgetRow row) {
        if (row == null) return;
        System.out.printf("%4d | %,18.2f | %,18.2f | %,10.2f%n",
                row.year, row.totalRevenue, row.totalExpenses, row.balance);
    }

    // Κρατά τα δεδομένα ενός έτους
    private static class BudgetRow {
        int year;
        double totalRevenue;
        double totalExpenses;
        double balance;

        public BudgetRow(int year, double totalRevenue, double totalExpenses, double balance) {
            this.year = year;
            this.totalRevenue = totalRevenue;
            this.totalExpenses = totalExpenses;
            this.balance = balance;
        }
    }
}

