import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class ChangeHistoryDB {
     private static final String DB_URL = "LINKKKKKK BASIIIS";

    public static void insertChange(String country, int year, String category,
                                    double oldAmount, double newAmount, String username) {

        String sql = "INSERT INTO change_history(country, year, category, old_amount, new_amount, username, change_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, country);
            pstmt.setInt(2, year);
            pstmt.setString(3, category);
            pstmt.setDouble(4, oldAmount);
            pstmt.setDouble(5, newAmount);
            pstmt.setString(6, username);
            pstmt.setString(7, LocalDateTime.now().toString());

            pstmt.executeUpdate();
            System.out.println("Η αλλαγή αποθηκεύτηκε!");

        } catch (SQLException e) {
            System.out.println("SQL ERROR: " + e.getMessage());
        }
    }

    public static void printHistory() {
        String sql = "SELECT * FROM change_history ORDER BY change_date DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("--------- ΙΣΤΟΡΙΚΟ ΑΠΟΦΑΣΕΩΝ ΠΡΩΘΥΠΟΥΡΓΟΥ ---------");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + ". "
                        + rs.getString("country") + " (" + rs.getInt("year") + ") - "
                        + rs.getString("category") + ": "
                        + rs.getDouble("old_amount") + " → " + rs.getDouble("new_amount")
                        + " | " + rs.getString("username")
                        + " | " + rs.getString("change_date")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
