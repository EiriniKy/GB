import java.util.Scanner;
public class Menu {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMainMenu();
            System.out.println("Επιλογή:");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    showGreekBudget();
                    break;
                case "2":
                    showEditBudget();
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
        System.out.println("Προυπολογισμός της Ελλάδας.");
        pause();
    }
    public static void showEditBudget() {
        System.out.println("Επεξεργασία Προυπολογισμού.");
        pause();
    }
    public static void showCountryComparison() {
        System.out.println("Σύγκριση με άλλες χώρες.");
        pause();
    }
    public static void showYearComparison() {
        System.out.println("Σύγκριση με άλλα έτη.");
        pause();
    }
    public static void showHistory() {
        System.out.println("Ιστορικό αλλαγών.");
        pause();
    }
    public static void pause() {
        System.out.println("Πατήστε ENTER για επιστροφή στο μενού...");
        new Scanner(System.in).nextLine();
    }
}