package rs.aik.testautomation.AIKTestAutomation.Wait;

import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import rs.aik.testautomation.AIKTestAutomation.Core.Base;
import rs.aik.testautomation.AIKTestAutomation.Core.BaseMobile;

/**
 * Class for helpers for waiting
 */
public class WaitHelpers {
    public static WebDriver driver;
    private static String getErrorMessage = "";
    protected static WebDriverWait waitVar = null;

    /**
     * Wait to element become displayed
     * @param element WebElement for waiting
     * @param elementName Text to log
     * @throws InterruptedException
     */
    public static void WaitForElement(WebElement element, String elementName) throws InterruptedException {
        for (int i = 1; i <= 30; i++) {
            try {
                element.isDisplayed();
                break;
            } catch (NoSuchElementException e) {
                Thread.sleep(1000);
                if (i == 30) {
                    getErrorMessage = e.getMessage();
                    Log.error("Element " + elementName + "is not displayed!!!");
                    Log.error(getErrorMessage);
                }
            }
        }
    }

    /**
     * Wait to element become displayed
     * @param xPath By element for waiting
     * @param waitInSec Time to wait in seconds
     * @throws InterruptedException
     */
    public static void WaitForElement(By xPath, int waitInSec) throws InterruptedException {
        for (int i = 1; i <= waitInSec; i = i + 1) {
            try {
                WebElement element = Base.driver.findElement(xPath);
                boolean displayed = element.isDisplayed();

                if (displayed) {
                    Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Element: " + xPath.toString() + " is visible !!!!!!!!!!!!!!!!!!!!!!!!!");
                    return;
                }
            } catch (java.util.NoSuchElementException |org.openqa.selenium.NoSuchElementException e) {

            }
            Thread.sleep(1000);
        }
        return;
    }

    /**
     * Overloaded method wait to element become displayed. With default value for waiting
     * @param xPath By element for waiting
     * @throws InterruptedException
     */
    public static void WaitForElement(By xPath) throws InterruptedException {
        WaitForElement(xPath, 15);
    }

    /**
     * Method for idle waiting
     * @param time - seconds for waiting
     */
    public static void waitForSeconds(int time)
    {
        try {
            Thread.sleep(time * 1000);
        }
        catch (Exception e){
            Log.error("Error "+e);
        }
    }

    public static void waitForElement(By ByElement, int time) {
        try {
            waitVar = new WebDriverWait(BaseMobile.driver, time);
            waitVar.until(ExpectedConditions.presenceOfElementLocated(ByElement));
        } catch (Exception e) {
            Log.error("Error " + e);
        }
    }

}
