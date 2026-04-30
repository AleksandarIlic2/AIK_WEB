package rs.aik.testautomation.AIKTestAutomation.Selectors;

import rs.aik.testautomation.AIKTestAutomation.Action.ActionApiHelpers;
import rs.aik.testautomation.AIKTestAutomation.Core.Base;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;

/**
 * Class for selecting element by name attribute
 */
public class SelectByName {
    static ActionApiHelpers h = new ActionApiHelpers();

    //region - Basic methods -
    /**
     * Method that creates WebElement by element name using search by name
     * @param elementName Name of the element
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByName(String elementName) throws Throwable {
        try {
            By element = By.id(elementName);
            h.isElementDisplayed(element);
            return Base.driver.findElement(By.name(elementName));
        } catch (NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Name \"" + elementName + "\"  NoSuchElementException: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Name \"" + elementName + "\"  Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Method that creates WebElement by element name using search by xpath
     * Pattern: //*[@name='payment-options']
     * @param elementName Name of the element
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpath(String elementName) throws Throwable {
        try {
            ActionApiHelpers ap = new ActionApiHelpers();
            By el = By.xpath("//*[@name='" + elementName + "']");
            ap.isElementDisplayed(el);
            return Base.driver.findElement(By.xpath("//*[@name='" + elementName + "']"));
        } catch (java.util.NoSuchElementException | org.openqa.selenium.NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name: " + elementName + " Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name: " + elementName + " Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Method that creates list of WebElement by element name using search by name
     * @param elementName Name of the element
     * @return list of WebElement
     * @throws Throwable
     */
    public static List<WebElement> CreateElementsByName(String elementName) throws Throwable {
        try {
            By element = By.id(elementName);
            h.isElementDisplayed(element);
            return Base.driver.findElements(By.name(elementName));
        } catch (NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Name \"" + elementName + "\"  NoSuchElementException: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Name \"" + elementName + "\"  Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Method that creates list of  WebElement by element name using search by xpath
     * Pattern: //*[@name='payment-options']
     * @param elementName Name of the element
     * @return list of WebElement
     * @throws Throwable
     */
    public static List<WebElement> CreateElementsByXpath(String elementName) throws Throwable {
        try {
            ActionApiHelpers ap = new ActionApiHelpers();
            By el = By.xpath("//*[@name='" + elementName + "']");
            ap.isElementDisplayed(el);
            return Base.driver.findElements(By.xpath("//*[@name='" + elementName + "']"));
        } catch (java.util.NoSuchElementException | org.openqa.selenium.NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name: " + elementName + " Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name: " + elementName + " Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Method that creates WebElement by element name and index of element using search by xpath
     * Pattern: (//*[@name='payment-options'])[3]
     * @param elementName Name of the element
     * @param index Index of the element
     * @return WebElement
     * @throws Throwable Forwarded from SelectByXpath.CreateElementByXpath
     */
    public static WebElement CreateElementByXpathIndex(String elementName, String index) throws Throwable {
        String xPath = "(//*[@name='" + elementName + "'])[" + index + "]";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    /**
     * Method that creates list of WebElement using element name and find WebElement by index from list
     * @param elementName Name of the element
     * @param index Index of the element
     * @return WebElement
     * @throws Throwable Forwarded from CreateElementsByName
     */
    public static WebElement CreateElementByNameIndex(String elementName, String index) throws Throwable {
        List<WebElement> list = CreateElementsByName(elementName);
        int ndx = Integer.parseInt(index);
        return list.get(ndx);
    }
    //endregion - Basic methods -

    //region - Custom methods -


    //endregion - Custom methods -
}
