import java.util.ArrayList;
import java.util.List;

public class Budget {
    private String country;
    private int year;
    private String category;
    private double amount;
    private List<ChangeHistory> history = new ArrayList<>();

    public Budget(String country, int year, String category, double amount) {
        this.country = country;
        this.year = year;
        this.category = category;
        this.amount = amount;
    }

    public void updateAmount(double newAmount, String username) {
        ChangeHistory change = new ChangeHistory(country, year, category, this.amount, newAmount, username);
        history.add(change);
        this.amount = newAmount;

        System.out.println("Αλλαγή καταχωρήθηκε: " + change);
    }

    public void printHistory() {
        System.out.println("\nΙΣΤΟΡΙΚΟ ΓΙΑ: " + country + " - " + category + " (" + year + ")");
        for (ChangeHistory h : history) {
            System.out.println(h);
        }
    }
}
