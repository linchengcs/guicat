package examples.ticket;

import catg.CATG;
import org.apache.log4j.Logger;

public class SingleSym {
    private static Logger logger = Logger.getLogger(SingleSym.class);
    private static String sym = null;

    public static String getSingleSym() {
        logger.info("get a sym");
        if (sym == null)
            sym = CATG.readString("1");
        return sym;
    }

}
