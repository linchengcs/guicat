package examples.ticket;



import guicat.SymbolicMirror;
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.log4j.Logger;

public class SingleSymTest {
    private static Logger logger = Logger.getLogger(Test.class);
    @Test
    public void firstTest() {
        assertEquals(1, 1);
    }

    @Test
    public void logTest() {
        logger.info("This is debug message.");
        assertEquals(1, 1);
    }
}
