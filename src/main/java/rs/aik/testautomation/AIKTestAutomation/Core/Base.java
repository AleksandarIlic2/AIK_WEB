package rs.aik.testautomation.AIKTestAutomation.Core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.apache.log4j.BasicConfigurator;
import rs.aik.testautomation.AIKTestAutomation.Config.ConfigurationManager;
import rs.aik.testautomation.AIKTestAutomation.Data.DataManager;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * This is a base class for automation framework which performs the following:
 * > Static driver initialization (load configuration, instantiate driver and options,...)
 * > Static driver release
 * > todo: What other initialization tasks should go here?
 * Simple usage:
 <pre>{@code
 *   Base.createDriver();
 *   Base.driver.get(url);
 *   // Do something here...
 *   if(Base.driver != null){ Base.closeDriver(); }
 * }</pre>
 * Note: Before call, update config.properties according to your needs
 */

public class Base {
    public static WebDriver driver = null;
    public static ConfigurationManager config = null;
    public static BrowserTypes browserType;
    public static WebDriverWait waitVar = null;
    /**
     * If driver has been successfully initialized return TRUE, in case of error it's FALSE
     */
    public static boolean driverInitialized = false;

    /**
     * Quick and dirty driver start.
     * Load configuration info and create instance of driver based on config data
     */
    public static void createDriver(){
        Base base = new Base();
        // Load framework configuration information
        config = new ConfigurationManager(true);
        if(!DataManager.webBrowser.isEmpty()){
            browserType = BrowserTypes.getBrowserByName(DataManager.webBrowser);
            driverInitialized = base.createDriver(browserType);
        }
        else
            driverInitialized = base.createDriver(ConfigurationManager.DEFAULT_BROWSER);
    }

    /**
     * Release (any potential) resources associated with "framework run" and quit driver
     */
    public static void closeDriver(){
        // Release (any) resources
        // Try to quit and close driver
        if(driver !=null) {
            //driver.quit();
        }
    }

    /**
     * Create instance of web driver based on driver's type loaded from configuration
     * @param browserType Type of a browser from supported enumeration
     * @return true if driver created successfully, otherwise return false
     */
    protected boolean createDriver(BrowserTypes browserType){
        boolean success = true;
        try {
            switch (browserType) {
                case FIREFOX: startFirefoxDriver(); break;
                case CHROME:  startChromeDriver(); break;
                case EDGE:    startEdgeDriver(); break;
                case IE:      startIEDriver(); break;
                case OPERA:   startOperaDriver(); break;
                //case SAFARI:   startSafariDriver(); break;
                default:
                    // determine default browser
                    break;
            }
            if(driver != null) {
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                driver.manage().window().maximize();
            }
        }
        catch (Exception ex){
            success = false;
            Log.error(ex.getMessage());
        }
        return success;
    }

    /**
     * Start default chrome driver
     */
    private void startOperaDriver() {
        WebDriverManager.operadriver().setup();
        // todo: Set some properties loaded from configuration
        Base.driver = new OperaDriver();

    }

    /**
     * Start default internet explorer driver
     */
    private void startIEDriver() {
        WebDriverManager.iedriver().setup();
        Base.driver = new InternetExplorerDriver();

    }

    /**
     * Start default edge driver
     */
    private void startEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        // todo: Set some properties loaded from configuration
        //EdgeOptions options = new EdgeOptions();
        //options.addArgument ("headless");
        //Base.driver = new EdgeDriver(options);
        Base.driver = new EdgeDriver();
    }

    /**
     * Start default safari driver
     */
    /*private void startSafariDriver() {
        WebDriverManager.seleniumServerStandalone().setup();
        // todo: Set some properties loaded from configuration
        Base.driver = new SafariDriver();

    }*/

    /**
     * Start default chrome driver
     */
    //Added ignore of certificate and ssl errors so that privacy error doesn't show --Jovan
    private void startChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-ssl-errors=yes");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-search-engine-choice-screen");

        Map<String, Object> prefs = new HashMap<>();
        String downloadPath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "testing";
        prefs.put("download.default_directory", downloadPath);
        prefs.put("download.prompt_for_download", false);
        prefs.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", prefs);

        Base.driver = new ChromeDriver(options);
    }

    /**
     * Start firefox driver including options if available
     */
    private void startFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        config.loadFirefoxOptions();
        // If there are configuration options for firefox, use them for instantiation
        Base.driver = (ConfigurationManager.ffOptions != null)
                ? new FirefoxDriver(ConfigurationManager.ffOptions)
                : new FirefoxDriver();
    }
}
