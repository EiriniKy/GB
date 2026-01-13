package gr.unipi.app;

import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;
public class TestMain {
    public static void main (String[] args) {
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            System.out.println("Η βάση λειτουργεί και η σύνδεση είναι έτοιμη!");
        } else {
            System.out.println("Κάτι πήγε στραβά στη σύνδεση.");
        }
        Scanner scanner = new Scanner(System.in);

        System.out.print("Δώσε όνομα χρήστη: ");
        String name = scanner.nextLine();

        System.out.print("Δώσε κωδικό χρήστη: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Δώσε email χρήστη: ");
        String mail = scanner.nextLine();

        System.out.print("Δώσε ιδιότητα χρήστη: ");
        String role = scanner.nextLine();
        while (true) {
            showMainMenu();
            System.out.println("Επιλογή:");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showGreekBudget();
                    break;
                case "2":
                    showEditBudget(userId);
                    break;
                case "3":
                    showCountryComparison();
                    break;
                case "4":
                    showYearComparison();
                    break;
                case "5":
                    showHistory();
                    break;
                case "6":
                    System.out.println ("Έξοδος από το πρόγραμμα!");
                default:
                    System.out.println ("Μη έγκυρη επιλογή!\nΔοκίμασε ξανά!");
                    return;
            }
        }
    }
    public static void showMainMenu() {
        System.out.println ("Πρωθυπουργός για μια Μέρα!");
        System.out.println ("____________________________________________________________");
        System.out.println("1. Προβολή των δεδομένων του προϋπολογισμού της Ελλάδας 2026");
        System.out.println("2. Επεξεργασία προϋπολογισμού 2026");
        System.out.println("3. Σύγκριση με άλλες χώρες (2025)");
        System.out.println("4. Σύγκριση με παλαιότερα έτη (2023-2025)");
        System.out.println("5. Ιστορικό αλλαγών");
        System.out.println("6. Έξοδος\n");
    }
    public static void showGreekBudget() {
        ViewBudget vb = new ViewBudget();
        vb.showBudgetDetails();
        pause();
    }
    public static void showEditBudget(int userId) {
        try {
            ChangeBudget.updateBudgetCategoryAmount(userId);
        } catch (SQLException e) {
            System.out.println("Αποτυχία ενημέρωσης προϋπολογισμού: " + e.getMessage());
        }
        pause();
    }
    public static void showCountryComparison() {
        CountriesComparison.comparison();
        pause();
    }
    public static void showYearComparison() {
        YearsComparison.comparison();
        pause();
    }
    public static void showHistory() {
        HistoryOfChanges.showHistory();
        pause();
    }
    public static void pause() {
        System.out.println("Πατήστε ENTER για επιστροφή στο μενού...");
        new Scanner(System.in).nextLine();
    }
}

