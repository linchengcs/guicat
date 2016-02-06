package examples.ticket;

import catg.CATG;
import org.apache.log4j.Logger;
import java.util.*;
import java.io.*;

public class SingleSym {
    private static Logger logger = Logger.getLogger(SingleSym.class);
    private static String sym = null;
    public static SingleSym singleSym = new SingleSym();
    private HashMap<String, Object> symVariable ;
    private Properties conf;

    public SingleSym() {
        String conffile = "./conf/ticket/symagent/ticket.properties";
        try {
            FileInputStream is = new FileInputStream(conffile);
            conf = new Properties();
            conf.load(is);
            is.close();
            String myconf = conf.getProperty("sym");
            System.out.println(conf.toString());
            System.out.println(conf.getProperty("Enter your name:"));
            System.out.println(conf.getProperty("Enter your age:"));
        }
        catch(IOException ioe) {
            logger.error("IOException in loadProps");
            for(StackTraceElement ste : ioe.getStackTrace())
                logger.error(ste.toString());
        }
        symVariable = new HashMap<String, Object>();
    }

    public static String getSingleSym() {
        logger.info("get a sym");
        if (sym == null)
            sym = CATG.readString("1");
        return sym;
    }



}
