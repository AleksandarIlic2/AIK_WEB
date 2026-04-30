package rs.aik.testautomation.AIKTestAutomation.Selectors;

import rs.aik.testautomation.AIKTestAutomation.Action.ActionApiHelpers;
import rs.aik.testautomation.AIKTestAutomation.Core.Base;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;

/**
 * Class for selecting element by tag name
 */
public class SelectByTagName {
    static ActionApiHelpers h = new ActionApiHelpers();

    //region - Basic methods -
    /**
     * Method that creates WebElement by tag name using search by tag name
     * @param tagName Name of tag for search
     * @return WebElement
     * @throws Exception
     */
    public static WebElement CreateElementByTagName(String tagName) throws Throwable {
        try {
            By element = By.id(tagName);
            h.isElementDisplayed(element);
            return Base.driver.findElement(By.tagName(tagName));
        } catch (NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Tag Name \"" + tagName + "\"  NoSuchElementException: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Tag Name \"" + tagName + "\"  Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Method that creates WebElement by tag name using search by tag name
     * @param tagName Name of tag for search
     * @return List of WebElement
     * @throws Exception
     */
    public static List<WebElement> CreateElementsByTagName(String tagName) throws Throwable {
        try {
            By element = By.id(tagName);
            h.isElementDisplayed(element);
            return Base.driver.findElements(By.tagName(tagName));
        } catch (NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Tag Name \"" + tagName + "\"  NoSuchElementException: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Tag Name \"" + tagName + "\"  Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Returns WebElement by index from list of WebElement creates by tag name
     * @param tagName Name of the tag
     * @param index Index of element
     * @return WebElement
     * @throws Throwable Forwarded from CreateElementsByTagName
     */
    public static WebElement CreateElementByTagNameIndex(String tagName, String index) throws Throwable {
        List<WebElement> list = CreateElementsByTagName(tagName);
        int ndx = Integer.parseInt(index);
        return list.get(ndx);
    }
    //endregion - Basic methods -

    //region - Custom methods -

    /**
     * Returns WebElement by text in tag
     * pattern: //nlb-language-selector//descendant::*[text()='ENG ']
     * @param tagName Name of the tag
     * @param text Text equals to text in sub element
     * @return WebElement
     */
    public static WebElement CreateElementByCertainTextInTag(String tagName, String text) throws Throwable {
        String xPath = "//" + tagName + "//descendant::*[text()='" + text + "']";
        return SelectByXpath.CreateElementByXpath(xPath);
    }
    //

    /**
     * Returns WebElement by class and tag
     * pattern: //form[@class='ng-untouched ng-pristine ng-invalid']
     * @param tagName Name of the tag
     * @param className Name of all classes
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByClassAndTag(String tagName, String className) throws Throwable {
        String xPath = "//" + tagName + "[@class='" + className + "']";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByTextInTag(String tagName, String text) throws Throwable {
        String xPath = "//" + tagName + "[contains(text(),'" + text + "')]";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    //endregion - Custom methods -
}
