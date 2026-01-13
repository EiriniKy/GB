package gr.unipi.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountriesComparison {

    public static void comparison() {
        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                System.out.println("Αποτυχία σύνδεσης");
                return;
            }
        System.out.println("Σύγκριση με άλλες χώρες (έτος 2025).");
        System.out.println("--------------------------------------------------");

        String sql =
                "SELECT C.Country_Name, B.Total_Revenue, B.Total_Expenses, B.Balance " +
                        "FROM Budget B " +
                        "JOIN Country C ON B.Country_ID = C.Country_ID " +
                        "WHERE B.Year = 2025 " +
                        "ORDER BY B.Country_ID";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            BudgetRow greece = null;
            List<BudgetRow> others = new ArrayList<>();

            while (rs.next()) {
                String name = rs.getString("Country_Name");
                double revenue = rs.getDouble("Total_Revenue");
                double expenses = rs.getDouble("Total_Expenses");

                double balance = 0.0;
                try {
                    balance = Double.parseDouble(rs.getString("Balance"));
                } catch (NumberFormatException ignored) {}

                BudgetRow row = new BudgetRow(name, revenue, expenses, balance);

                if ("Ελλάδα".equalsIgnoreCase(name)) {
                    greece = row;
                } else {
                    others.add(row);
                }
            }

            if (greece == null) {
                System.out.println("Δεν βρέθηκαν στοιχεία για την Ελλάδα (2025).");
                return;
            }

            printTable(greece, others);
            printComparisons(greece, others);

        } catch (SQLException e) {
            System.out.println("Σφάλμα βάσης δεδομένων: " + e.getMessage());
        }
    }

    // ---------------- Βοηθητικές μέθοδοι ----------------

    private static void printTable(BudgetRow greece, List<BudgetRow> others) {
        System.out.println("\nΠροϋπολογισμοί χωρών (2025):");
        System.out.println("Χώρα           | Έσοδα                | Έξοδα                | Ισοζύγιο");
        System.out.println("---------------------------------------------------------------------------");

        printBudgetLine(greece);
        for (BudgetRow r : others) {
            printBudgetLine(r);
        }
    }

    private static void printComparisons(BudgetRow greece, List<BudgetRow> others) {
        System.out.println("\nΣυγκρίσεις με την Ελλάδα:");

        for (BudgetRow r : others) {
            System.out.println("\n--- " + r.countryName + " ---");

            double diffRevenue = r.totalRevenue - greece.totalRevenue;
            double diffExpenses = r.totalExpenses - greece.totalExpenses;
            double diffBalance  = r.balance - greece.balance;

            System.out.printf("Διαφορά Εσόδων:    %, .2f%n", diffRevenue);
            System.out.printf("Διαφορά Εξόδων:    %, .2f%n", diffExpenses);
            System.out.printf("Διαφορά Ισοζυγίου: %, .2f%n", diffBalance);
        }
    }

    private static void printBudgetLine(BudgetRow r) {
        System.out.printf("%-13s | %,16.2f | %,16.2f | %,12.2f%n",
                r.countryName, r.totalRevenue, r.totalExpenses, r.balance);
    }

    // Εσωτερική βοηθητική κλάση
    private static class BudgetRow {
        String countryName;
        double totalRevenue;
        double totalExpenses;
        double balance;

        public BudgetRow(String c, double rev, double exp, double bal) {
            this.countryName = c;
            this.totalRevenue = rev;
            this.totalExpenses = exp;
            this.balance = bal;
        }
    }
}
