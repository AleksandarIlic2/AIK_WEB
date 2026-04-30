package rs.aik.testautomation.AIKTestAutomation.Selectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import rs.aik.testautomation.AIKTestAutomation.Action.ActionApiHelpers;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import rs.aik.testautomation.AIKTestAutomation.Core.Base;

import java.util.List;

/**
 * Class for searching web elements by attribute ID
 */
public class SelectById {
    static ActionApiHelpers h = new ActionApiHelpers();

    //region - Basic create methods -
    /**
     * method CreateElementById gets
     * @param ID used for finding element with that id name
     * @return WebElement
     */
    public static WebElement CreateElementById(String ID) throws Throwable {
        try {
            By element = By.id(ID);
            h.isElementDisplayed(element);
            return Base.driver.findElement(element);
        } catch (NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by ID \"" + ID + "\"  NoSuchElementException: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by ID \"" + ID + "\"  Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * method CreateElementById gets
     * @param ID used for finding element with that id name
     * @return List of WebElement
     */
    public static List<WebElement> CreateElementsById(String ID) throws Throwable {
        try {
            By element = By.id(ID);
            h.isElementDisplayed(element);
            return Base.driver.findElements(By.id(ID));
        } catch (NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by ID \"" + ID + "\"  NoSuchElementException: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by ID \"" + ID + "\"  Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Method that creates WebElement by ID using xpath
     * Pattern: //*[@id='payment-options']
     * @param ID ID of the element
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpath(String ID) throws Throwable {
        String xPath = "//*[@id=\"" + ID + "\"]";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    /**
     * Method that creates WebElement by ID using xpath
     * Pattern: //*[@id='payment-options']
     * @param ID ID of the element
     * @return List of WebElement
     * @throws Throwable
     */
    public static List<WebElement> CreateElementsByXpath(String ID) throws Throwable {
        String xPath = "//*[@id=\"" + ID + "\"]";
        return SelectByXpath.CreateElementsByXpath(xPath);
    }
    //endregion - Basic create methods -

    // region - Custom methods -

    /**
     * Returns element with Id in parameter @elementId and text in parameter @text
     * @param elementId Id of element
     * @param text Text equals to text in parameter @text
     * @return WeElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathIdAndText(String elementId, String text) throws Throwable {
        String xPath = "//*[@id='" + elementId + "' and text()='" + text + "']";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    /**
     * Returns web element by id that has a descendant tag with a class
     * @param id
     * @param tag
     * @param className
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByIdAndDescendantTagWithClass(String id, String tag, String className) throws Throwable {
        String xPath = "//*[@id='" + id + "']//descendant::" + tag + "[@class='" + className + "']";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    public static By CreateByElementById(String elementId) {
        By element = null;
        element = By.id(elementId);
        return element;
    }

    //endregion - Custom methods -
}
