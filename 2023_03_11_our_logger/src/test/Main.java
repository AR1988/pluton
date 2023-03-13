package test;

import logger.ConsoleLogger;

/**
 * @author Andrej Reutow
 * created on 11.03.2023
 */
public class Main {
    public static void main(String[] args) {
        ConsoleLogger logger = new ConsoleLogger(Main.class);
        logger.info("INFO");
        System.out.println("test");
        logger.debug("DEBUG");
        logger.warn("WARN");
        logger.error("ERROR");
    }
}
