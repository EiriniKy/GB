import java.time.LocalDateTime;

public class ChangeHistory {
    private String country;
    private int year;
    private String category;
    private double oldAmount;
    private double newAmount;
    private String username;
    private LocalDateTime changeDate;

    public ChangeHistory(String country, int year, String category,
                         double oldAmount, double newAmount, String username) {
        this.country = country;
        this.year = year;
        this.category = category;
        this.oldAmount = oldAmount;
        this.newAmount = newAmount;
        this.username = username;
        this.changeDate = LocalDateTime.now(); 
    }

    @Override
    public String toString() {
        return "[" + changeDate + "] " + username +
                " άλλαξε στην κατηγορία '" + category +
                "' για (" + country + " - " + year + ") από " +
                oldAmount + " σε " + newAmount;
    }

   
}
