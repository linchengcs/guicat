package examples.ticket;



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
    public void SameSingle() {
        Object o1 = SingleSym.getSingleSym();
        Object o2 = SingleSym.getSingleSym();
        assertEquals(o1, o2);
    }

    @Test
    public void logTest() {
        logger.info("This is debug message.");
        assertEquals(1, 1);
    }
}
