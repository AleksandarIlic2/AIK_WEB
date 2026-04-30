package rs.aik.testautomation.AIKTestAutomation.Helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used for logging purposes, has methods for different log levels
 * and method to log start and end of test case execution
 *
 * */
public class Log {

    public static String lastError;


    private static final Logger log = LoggerFactory.getLogger(Log.class);

    public static void info(String message){log.info(message);}

    public static void error(String message) {
        lastError = message;
        log.error(message);
    }

    public static void warn(String message) {log.warn(message);}

    public static void debug(String message) {log.debug(message);}

    public static void startTestCase(String sTestCaseName){
        Log.info("****************************************************************************************");
        Log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$                 "+sTestCaseName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        Log.info("****************************************************************************************");
    }

    public static void endTestCase(String sTestCaseName){
        Log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "  " + sTestCaseName + "             XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        Log.info("XXXXXXXXXXXXXXXXXXXXX");
        Log.info(".");
    }
}
