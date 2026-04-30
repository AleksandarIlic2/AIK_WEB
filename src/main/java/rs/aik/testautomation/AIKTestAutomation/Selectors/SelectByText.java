package rs.aik.testautomation.AIKTestAutomation.Selectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import rs.aik.testautomation.AIKTestAutomation.Action.ActionApiHelpers;

import java.util.List;

/**
 * Class for selecting element by text
 */
public class SelectByText {
    static ActionApiHelpers h = new ActionApiHelpers();

    //region - Basic create methods -
    /**
     * Find element which matches with text in parameter text
     * @param text String parameter
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathText(String text) throws Throwable {
        return SelectByXpath.CreateElementByXpath("//*[text()='" + text + "']");
    }

    /**
     * Find web element which contains text in parameter @text
     * @param text String parameter
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathContainingText(String text) throws Throwable {
        return SelectByXpath.CreateElementByXpath("//*[contains(text(),'" + text + "')]");
    }

    /**
     * Find list of element which matches with text in parameter text
     * @param text String parameter
     * @return List of WebElement
     * @throws Throwable
     */
    public static List<WebElement> CreateElementsByXpathText(String text) throws Throwable {
        return SelectByXpath.CreateElementsByXpath("//*[text()='" + text + "']");
    }

    /**
     * Find list of web elements which contains text in parameter @text
     * @param text String parameter
     * @return List of WebElement
     * @throws Throwable
     */
    public static List<WebElement> CreateElementsByXpathContainingText(String text) throws Throwable {
        return SelectByXpath.CreateElementsByXpath("//*[contains(text(),'" + text + "')]");
    }

    /**
     * Creates web element matches with text in parameter text with index in parameter
     * @param text Test to search
     * @param index Index of the element
     * @return WebElement
     * @throws Throwable Forwarded from SelectByXpath.CreateElementByXpath
     */
    public static WebElement CreateElementByXpathIndex(String text, String index) throws Throwable {
        return SelectByXpath.CreateElementByXpath("(//*[text()='" + text + "'])[" + index + "]");
    }
    //endregion - Basic create methods -

    //region - Custom methods -
    /**
     * Find element which matches with text in parameter text
     * @param text String parameter
     * @return List of WebElement
     * @throws Throwable
     */
    public static WebElement createElementByXpathSpanText(String text) throws Throwable {
        return SelectByXpath.CreateElementByXpath("//span[text()='"+text+"']");
    }

    public static WebElement CreateElementByXpathTagContainingText(String tag, String text) throws Throwable {
        String xPath = "//" + tag + "[contains(text(),'" + text + "')]";
        return SelectByXpath.CreateElementByXpath(xPath);
    }

    public static By CreateByElementByText(String text) {
        return SelectByXpath.CreateByElementByXpath("//*[text()='" + text + "']");

    }

    //endregion - Custom methods -
}
