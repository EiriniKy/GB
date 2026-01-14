import gr.unipi.app.Main;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class MainExitTest {

    @Test
    void testProgramTerminatesWhenUserPresses6() {

        String input = String.join("\n",
                "user",
                "1",
                "mail@test.com",
                "admin",
                "6"
        ) + "\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertTimeoutPreemptively(
                Duration.ofSeconds(1),
                () -> Main.main(new String[]{})
        );
    }
}