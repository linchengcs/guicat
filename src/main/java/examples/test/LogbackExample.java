package examples.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by oliver on 12/06/16.
 */
public class LogbackExample {
    private static Logger logger = LoggerFactory.getLogger(LogbackExample.class);

    public static void main(String[] args) {
        logger.error("logback error");
        logger.warn("logback warn");
        logger.info("logback info");
        logger.debug("logback debug");
        logger.trace("logback trace");
        logger.info(logger.toString());
    }
}
