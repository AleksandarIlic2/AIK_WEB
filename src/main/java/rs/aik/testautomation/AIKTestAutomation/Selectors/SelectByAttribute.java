package rs.aik.testautomation.AIKTestAutomation.Selectors;

import rs.aik.testautomation.AIKTestAutomation.Action.ActionApiHelpers;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Class for selecting element by attribute, searching by xpath
 */
public class SelectByAttribute {
        static ActionApiHelpers h = new ActionApiHelpers();

        //region - Basic methods -
        /**
         * Returns web element for attribute - value pair
         * Pattern: //*[@data-bind='CardCustomName'][2]
         * @param attributeName Attribute name
         * @param value Value of attribute
         * @return WebElement
         * @throws Throwable
         */
        public static WebElement CreateElementByXpath(String attributeName, String value) throws Throwable {
                String text = "//*[@" + attributeName + "=\"" + value + "\"]";
                return SelectByXpath.CreateElementByXpath(text);
        }

        /**
         * Returns list of web element for attribute - value pair
         * Pattern: (//*[@data-bind='CardCustomName'])[2]
         * @param attributeName Attribute name
         * @param value Value of attribute
         * @return List of WebElement
         * @throws Throwable forward from SelectByXpath.CreateElementsByXpath()
         */
        public static List<WebElement> CreateElementsByXpath(String attributeName, String value) throws Throwable {
                String text = "//*[@" + attributeName + "=\"" + value + "\"]";
                return SelectByXpath.CreateElementsByXpath(text);
        }

        /**
         * For attrib in parameter attrib, with value in parameter value gets set of web element.
         * Function returns WebElement with index in parameter ndx from set.
         * Pattern: (//*[@data-bind='CardCustomName'])[2]
         * @param attributeName Attribute for searching element
         * @param value Value for searching element
         * @param index Index of WebElement in set
         * @return WebElement
         * @throws Throwable
         */
        public static WebElement CreateElementByXpathIndex(String attributeName, String value, String index) throws Throwable {
                String text = "//*[@" + attributeName + "=\"" + value + "\"]";
                List<WebElement> list = SelectByXpath.CreateElementsByXpath(text);
                int ndx = Integer.parseInt(index);
                return list.get(ndx);
        }
        //endregion - Basic methods -

        //region - Custom methods -
        /**
         * Returns WebElement with value attribute equals parameter @value
         * Pattern: //*[@value='test1']
         * @param value Value to search
         * @return WebElement
         * @throws Throwable
         */
        public static WebElement CreateElementByXpathValue(String value) throws Throwable {
                String attributeName = "value";
                return CreateElementByXpath(attributeName, value);
        }

        /**
         * Returns WebElement with value attribute equals parameter @value
         * Pattern: //*[@title='A1 Srbija']
         * @param value Value of title
         * @return WebElement
         * @throws Throwable
         */
        public static WebElement CreateElementByXpathTitle(String value) throws Throwable {
                String attributeName = "title";
                return CreateElementByXpath(attributeName, value);
        }

        /**
         * Returns web element for two attributes - values pairs
         * @param attributeName1 Attribute 1 name
         * @param value1 Value of attribute 1
         * @param attributeName2 Attribute 2 name
         * @param value2 Value of attribute 2
         * @return WebElement
         * @throws Throwable
         */
        public static WebElement CreateElementByTwoAttributes(String attributeName1, String value1, String attributeName2, String value2) throws Throwable {
                String xPath = "//*[@" + attributeName1 + "=\"" + value1 + "\" and @" + attributeName2 + "=\"" + value2 + "\"]";
                return SelectByXpath.CreateElementByXpath(xPath);
        }
        //endregion - Custom methods -
}
