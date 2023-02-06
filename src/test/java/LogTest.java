import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogTest {
    @Test
    void shouldReturnZeroOrGreaterNumberWhenCallsignExistsInLogbook() {
        var logbook = new Log("SQ7N");
        logbook.loadLogbook();
        assertTrue(logbook.findLogbookEntry("SQ7N") >= 0);
    }

    @Test
    void shouldReturnMinusOneWhenCallsignNotExistsInLogbook() {
        var logbook = new Log("SQ7N");
        logbook.loadLogbook();
        assertEquals(-1, logbook.findLogbookEntry("!@#!@#4123!@#!@3"));
    }
}