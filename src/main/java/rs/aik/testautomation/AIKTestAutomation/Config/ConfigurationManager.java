package rs.aik.testautomation.AIKTestAutomation.Config;

import org.openqa.selenium.firefox.FirefoxOptions;
import rs.aik.testautomation.AIKTestAutomation.Core.BrowserTypes;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Configuration manager used to load configuration information about the framework itself
 * By default loaded from properties!
 * If other source of configuration required, configure (in properties) and load from different source
 * NOTE: Simple implementation for now, supporting only property file
 */

public class ConfigurationManager {
    public static String CONFIG_FILE_NAME = "./src/main/resources/config.properties";
    public static final String BROWSER_NOT_SUPPORTED ="Browser %s not supported yet!!!";

    // --- Global Configuration Properties --------------------------------
    /**
     * Default browser used for testing loaded prom properies
     */
    public static BrowserTypes DEFAULT_BROWSER = BrowserTypes.CHROME;
    public static int DEFAULT_IMPLICIT_WAIT = 15;
    public static boolean SCREENSHOT_ENABLED = true;
    public static String PATH2_SCREENSHOTS = "";
    public static String CURRENT_PLATFORM = "Desktop";

    // --- Other configuration information ...todo
    // public static boolean ACCEPT_SSL_CERT = false;

    public static FirefoxOptions ffOptions;

    protected Properties props;

    /* -- In case that we might need this!?
     static {
        try{
            ConfigurationManager config = new ConfigurationManager();
            config.loadProperties();
            config.loadGlobalConfiguration();
            // todo: Load other configuration values; use separate method(s) for each type of properties
        }
        catch (Exception ex){
            // todo: Log exception here
            throw new RuntimeException("Failed to load configuration properties", ex);
        }
    }*/

    /**
     * Default constructor
     */
    public ConfigurationManager(){ }

    /**
     * Constructor which loads properties on request
     * @param load If true, load properties on initialization
     */
    public ConfigurationManager(boolean load){
        if(load){
            loadProperties();
            loadGlobalConfiguration();
        }
    }

    /**
     * Load property file keys and values
     */
    public void loadProperties(){
        try {
            File src = new File(CONFIG_FILE_NAME);
            FileInputStream fis = new FileInputStream(src);
            props = new Properties();
            props.load(fis);
        }
        catch(Exception ex){
            Log.error(ex.getMessage());
        }
    }

    /**
     * Load all global configuration props values from configuration file
     */
    public void loadGlobalConfiguration(){
        String btype = props.getProperty("defaultBrowser");
        if(BrowserTypes.isSupportedBrowser(btype)){
            DEFAULT_BROWSER = BrowserTypes.valueOf(btype.toUpperCase());
        }
        else{
            Log.warn(String.format(BROWSER_NOT_SUPPORTED, btype));
        }
        DEFAULT_IMPLICIT_WAIT = getPropertyInteger("defaultImplicitWait");
        SCREENSHOT_ENABLED =  getPropertyBoolean("screenShotFlag");
        PATH2_SCREENSHOTS = getProperty("path2screenShotFolder");
        CURRENT_PLATFORM = getProperty("platform");
    }

    /**
     * If property file loaded, load string property by name
     * todo: handle situation when there's no such property
     * @param propertyName Name of the property we need to load
     * @return Property value or empty string if property file not loaded
     */
    public String getProperty(String propertyName){
        return (props!=null) ? props.getProperty(propertyName): "";
    }

    /**
     * If property file loaded, load boolean property by name
     * todo: handle situation when there's no such property
     * @param propertyName Name of the property we need to load
     * @return Property value or false if property file not loaded
     */
    public Boolean getPropertyBoolean(String propertyName){
        return (props!=null) ? Boolean.parseBoolean(props.getProperty(propertyName)) : false;
    }

    /**
     * If property file loaded, load integer property by name
     * todo: handle situation when there's no such property
     * @param propertyName Name of the property we need to load
     * @return Property value or ZERO if property file not loaded
     */
    public Integer getPropertyInteger(String propertyName){
        return (props!=null) ? Integer.parseInt(props.getProperty(propertyName)) : 0;
    }

    /**
     * Load all (defined) FF options/properties from property file.
     * <p>
     * For list of available options:
     * @see  <a https://www.selenium.dev/selenium/docs/api/py/webdriver_firefox/selenium.webdriver.firefox.options.html</a>
     * </p>
     */
    public void loadFirefoxOptions(){
        ffOptions = new FirefoxOptions();
        // todo: get properties related to firefox options
    }
}
