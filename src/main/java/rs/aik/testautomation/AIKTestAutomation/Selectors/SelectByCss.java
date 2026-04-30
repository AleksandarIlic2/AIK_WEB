package rs.aik.testautomation.AIKTestAutomation.Selectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import rs.aik.testautomation.AIKTestAutomation.Action.ActionApiHelpers;
import rs.aik.testautomation.AIKTestAutomation.Core.Base;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;

import java.util.List;

/**
 * Class for selecting element by css selector
 */
public class SelectByCss {
    static ActionApiHelpers h = new ActionApiHelpers();

    //region - Basic methods -
    /**
     * Method that creates WebElement by element css using search by css
     * @param cssSelector CSS selector
     * @return WebElement
     * @throws Exception
     */
    public static WebElement CreateElementByCss(String cssSelector) throws Throwable {
        try {
            By element = By.cssSelector(cssSelector);
            h.isElementDisplayed(element);
            return Base.driver.findElement(By.cssSelector(cssSelector));
        } catch (NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Css Selector \"" + cssSelector + "\"  NoSuchElementException: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Css Selector \"" + cssSelector + "\"  Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Method that creates list of WebElement by element css using search by css
     * @param cssSelector Css selector
     * @return List of WebElement
     * @throws Throwable
     */
    public static List<WebElement> CreateElementsByCss(String cssSelector) throws Throwable {
        try {
            By element = By.cssSelector(cssSelector);
            h.isElementDisplayed(element);
            return Base.driver.findElements(By.cssSelector(cssSelector));
        } catch (NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Css Selector \"" + cssSelector + "\"  NoSuchElementException: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by Css Selector \"" + cssSelector + "\"  Exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Returns WebElement from list of WebElements creates by css selector in parameter @cssSelector with index in
     * parameter @index
     * @param cssSelector Css selector
     * @param index index of element
     * @return
     * @throws Throwable
     */
    public static WebElement CreateElementByCss(String cssSelector, String index) throws Throwable {
        List<WebElement> list = CreateElementsByCss(cssSelector);
        int ndx = Integer.parseInt(index);
        return list.get(ndx);
    }
    //endregion - Basic methods -

    //region - Custom methods -


    //endregion - Custom methods -

    //region - Examples from old project -

    /**
     * method CreateElementByCssNOTSelectingDivs gets
     * @param contains_class
     * @param not_contains_class
     *  all divs containing contains_class but not containing not_contains_class
     */

    public static WebElement CreateElementByCssNOTSelectingDivs(String contains_class, String not_contains_class) throws Throwable {
        return CreateElementByCss("div[class*=" + contains_class + "]:not([class*='" + not_contains_class + "'])");
    }

    /**
     * method CreateElementByCssClassAndStyle gets
     * @param class_name
     *          class to be present on div
     * @param class_style
     *          style to be present on div
     */

    public static WebElement CreateElementByCssClassAndStyle(String class_name, String class_style) throws Throwable {
        return CreateElementByCss("div[class='" + class_name + "'] [style='" + class_style + "']");
    }


    /**
     * Multiple Child Elements
     * multiple child elements within the same parent element such as list elements
     */

    public static WebElement CreateElementByCssFirstInListID(String ul_id) throws Throwable {
        return CreateElementByCss("ul#" + ul_id + " li:first-child");
    }

    public static WebElement CreateElementByCssLastInListID(String ul_id) throws Throwable {
        return CreateElementByCss("ul#" + ul_id + " li:last-child");
    }

    public static WebElement CreateElementByCssPositionInListID(String ul_id, int position) throws Throwable {
        return CreateElementByCss("ul#" + ul_id + " li:nth-of-type(" + position + ")");
    }

    public static WebElement CreateElementByCssFirstInListClass(String ul_class) throws Throwable {
        return CreateElementByCss("." + ul_class + ">li:first-of-type");
    }

    public static WebElement CreateElementByCssLastInListClass(String ul_class) throws Throwable {
        return CreateElementByCss("." + ul_class + ">li:last-of-type");
    }

    public static WebElement CreateElementByCssPositionInListClass(String ul_class, int position) throws Throwable {
        return CreateElementByCss("." + ul_class + ">li:nth-of-type(" + position + ")");
    }

    /**
     * method CreateElementByCssIDStartsWith gets
     * @param id_starts_with
     */

    public static WebElement CreateElementByCssIDStartsWith(String id_starts_with) throws Throwable {
        return CreateElementByCss("div[id^='" + id_starts_with + "']");
    }

    /**
     * method CreateElementByCssIDEndsWith gets
     * @param id_ends_with
     */
    public static WebElement CreateElementByCssIDEndsWith(String id_ends_with) throws Throwable {
        return CreateElementByCss("div[id$='" + id_ends_with + "']");
    }

    /**
     * methods CreateElementByCssIDSubString and CreateElementByCssIDContains is used to generate xpath with contains
     */

    public static WebElement CreateElementByCssIDSubString(String id_sub_string) throws Throwable {
        return CreateElementByCss("div[id*='" + id_sub_string + "']");
    }

    public static WebElement CreateElementByCssIDContains(String div_contains) throws Throwable {
        return CreateElementByCss("div:contains('" + div_contains + "')");
    }

    //endregion - Examples from old project -
}
