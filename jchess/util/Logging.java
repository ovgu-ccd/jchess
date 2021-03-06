package jchess.util;

import jchess.Application;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.net.URL;

/**
 * Logging framework
 *
 * Wrapper around log4j
 *
 * Created by robert on 07.12.14.
 */
public enum Logging {


    GUI(Logger.getLogger("GUI")),
    GAME(Logger.getLogger("GAME"));

    private static final String configFileName = "resources/log4j.properties";
    private final Logger logger;

    Logging(Logger logger) {
        this.logger = logger;
    }

    public static void setup() {
        URL url = Application.class.getResource(configFileName);
        PropertyConfigurator.configureAndWatch(url.getFile());
        Logging.GAME.debug("Logging initialized.");
    }

    public void debug(String message) {
        logger.debug(message);
    }
}
