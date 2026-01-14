import org.junit.jupiter.api.Test;
import gr.unipi.app.DatabaseConnection;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void testGetConnection() {
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null) {
            System.out.println("Δεν ήταν δυνατή η σύνδεση στη βάση.");
        } else {
            System.out.println("Η σύνδεση είναι επιτυχής.");
        }

        assertNotNull(conn, "Η σύνδεση στη βάση πρέπει να είναι επιτυχής");

        // Κλείνουμε τη σύνδεση
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            fail("Σφάλμα κατά το κλείσιμο της σύνδεσης: " + e.getMessage());
        }
    }
}