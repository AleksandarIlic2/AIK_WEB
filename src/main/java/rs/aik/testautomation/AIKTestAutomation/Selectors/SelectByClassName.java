package rs.aik.testautomation.AIKTestAutomation.Selectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import rs.aik.testautomation.AIKTestAutomation.Action.ActionApiHelpers;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import rs.aik.testautomation.AIKTestAutomation.Core.Base;

import java.util.List;

/**
 * Class for selecting element by class name attribute
 */
public class SelectByClassName {
    static ActionApiHelpers h = new ActionApiHelpers();

    //region - Basic methods -
    /**
     * Method CreateElementByClassName gets @className used for finding element with that class name using search by
     * class name
     * @param className Name of the class for searching
     * @return WebElement
     * @throws Throwable error NoSuchElementException or Exception
     */
    public static WebElement CreateElementByClassName(String className) throws Throwable {
        try {
            By element = By.className(className);
            h.isElementDisplayed(element);
            return Base.driver.findElement(element);
        } catch (NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name \"" + className + "\"  NoSuchElementException: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name \"" + className + "\"  Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    public static By CreateByElementByClassName(String className) throws Throwable {
        By element = null;
        element = By.className(className);
        return element;
    }

    /**
     * Method CreateElementByClassName gets @className used for finding element with that class name using search by
     * xpath
     * Pattern: //*[@class='payment-options']
     * @param className Name of the class for searching.
     * @return WebElement
     * @throws Throwable error NoSuchElementException or Exception
     */
    public static WebElement CreateElementByXpath(String className) throws Throwable {
        try {
            By el = By.xpath("//*[@class='" + className + "']");
            h.isElementDisplayed(el);
            return Base.driver.findElement(By.xpath("//*[@class='" + className + "']"));
        } catch (java.util.NoSuchElementException | org.openqa.selenium.NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name: " + className + " Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name: " + className + " Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Method CreateElementByClassName gets @className used for finding list of elements with that class name using search by
     * class name
     * Pattern: //*[@class='payment-options']
     * @param className Name of the class for searching
     * @return List of WebElement
     * @throws Throwable error NoSuchElementException or Exception
     */
    public static List<WebElement> CreateElementsByClassName(String className) throws Throwable {
        try {
            By element = By.className(className);
            h.isElementDisplayed(element);
            return Base.driver.findElements(element);
        } catch (NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name \"" + className + "\"  NoSuchElementException: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name \"" + className + "\"  Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Method CreateElementByClassName gets @className used for finding list of elements with that class name using search by
     * xpath
     * Pattern: //*[@class='payment-options']
     * @param className Name of the class for searching.
     * @return List of WebElement
     * @throws Throwable error NoSuchElementException or Exception
     */
    public static List<WebElement> CreateElementsByXpath(String className) throws Throwable {
        try {
            By el = By.xpath("//*[@class='" + className + "']");
            h.isElementDisplayed(el);
            return Base.driver.findElements(By.xpath("//*[@class='" + className + "']"));
        } catch (java.util.NoSuchElementException | org.openqa.selenium.NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name: " + className + " Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name: " + className + " Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Methods returns element with index from list of element find by classname
     * @param className Name of the class
     * @param index Index of element
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByClassName(String className, String index) throws Throwable {
        int ndx = Integer.parseInt(index);
        return CreateElementsByXpath(className).get(ndx);
    }

    /**
     * Methods returns element with index from list of element find by xpath
     * Pattern: (//*[@class='payment-options'])[3]
     * @param className Name of the class
     * @param index Index of element
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpath(String className, String index) throws Throwable {
        String xPath = "(//*[@class='" + className + "'])[" + index + "]";
        return SelectByXpath.CreateElementByXpath(xPath);
    }
    //endregion - Basic methods -

    //region - Custom methods -
    /**
     * Returns web element class name in parameter @className that descendant class in parameter parentClass using
     * search by xPath
     * @param className Name of descendant class
     * @param parentClassName Name of parent class
     * @return WebElement
     * @throws Throwable forwarded from CrateElementByXpath
     */
    public static WebElement CreateElementByXpathInClassName(String className, String parentClassName) throws Throwable {
        String xPath = "//*[@class='" + parentClassName + "']//descendant::*[@class='" + className + "']";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    /**
     * Returns web element class name in parameter @className that descendant link
     * @param className Name of the class
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByLinkInClass(String className) throws Throwable {
        String xPath = "//*[@class='" + className + "']//descendant::a";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByXpathClassFirstOnly(String className) throws Throwable {
        String xPath = "(//*[@class='" + className + "'])[1]";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByXpathClassOnly(String className, String appearanceNumber) throws Throwable{
        String xPath = "(//*[@class='" + className + "'])[" + appearanceNumber +"]";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    /**
     * Returns web element that contains class containedClass and doesn't contain class notContainedClass
     * @param containtedClass
     * @param notContainedClass
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathContainsClassAndNotContainsClass(String containtedClass, String notContainedClass) throws Throwable {
        String xPath = "//*[contains(@class,'" + containtedClass +"') and not (contains(@class, '" + notContainedClass + "'))]";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    /**
     * Returns web element that contains a class and has a descendant which contains text
     * @param className
     * @param tag
     * @param text
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathContainsClassWithDescendantTagContainingText(String className, String tag, String text) throws Throwable {
        String xPath = "//*[contains(@class,'" + className + "')]//descendant::" + tag + "[contains(text(),'" + text + "')]";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    /**
     * Returns web element that contains a class and has a descendant tag which has an id
     * @param className
     * @param tag
     * @param id
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathContainsClassWithDescendantTagWithId(String className, String tag, String id) throws Throwable {
        String xPath = "//*[contains(@class,'" + className + "')]//descendant::" + tag + "[@id='" + id + "']";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    /**
     * Returns web element that contains a class with a descendant tag that has a class
     * @param className
     * @param tag
     * @param descendantClassName
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathContainsClassWithDescendantTagWithClass(String className, String tag, String descendantClassName) throws Throwable {
        String xPath = "//*[contains(@class,'" + className + "')]//descendant::" + tag + "[@class='" + descendantClassName + "']";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    public static List<WebElement> CreateElementsByContainingClassName(String className) throws Throwable {
        try {
            By el = By.xpath("//*[contains(@class,'"+className+"')]");
            h.isElementDisplayed(el);
            return Base.driver.findElements(By.xpath("//*[contains(@class,'"+className+"')]"));
        } catch (java.util.NoSuchElementException | org.openqa.selenium.NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name: " + className + " Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Throwable e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Class Name: " + className + " Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    public static By CreateByElementByContainsClassName(String className) {
        String xPath = "//*[contains(@class, '" + className + "')]";
        By element = null;
        element = By.xpath(xPath);
        return element;
    }
    //endregion - Custom methods -
}