package rs.aik.testautomation.AIKTestAutomation.Selectors;

import org.openqa.selenium.*;
import rs.aik.testautomation.AIKTestAutomation.Action.ActionApiHelpers;
import rs.aik.testautomation.AIKTestAutomation.Core.Base;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class for selecting element by explicit xpath
 */
public class SelectByXpath {
    static ActionApiHelpers h = new ActionApiHelpers();

    //region - Basic create methods -
    /**
     * Base method for creating web element by xpath in parameter @xPath
     * @param xPath Xpath for searching element
     * @return WebElement
     * @throws Throwable NoSuchElementException or Exception
     */
    public static WebElement CreateElementByXpath(String xPath) throws Throwable {
        try {
            By el = By.xpath(xPath);
            h.isElementDisplayed(el);
            return Base.driver.findElement(By.xpath(xPath));
        } catch (NoSuchElementException | org.openqa.selenium.NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by XPath: " + xPath + " exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by XPath: " + xPath + " exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Base method for creating web element by xpath in parameter @xPath
     * @param xPath Xpath for searching element
     * @return WebElement
     * @throws Throwable NoSuchElementException or Exception
     */
    public static List<WebElement> CreateElementsByXpath(String xPath) throws Throwable {
        try {
            By el = By.xpath(xPath);
            h.isElementDisplayed(el);
            return Base.driver.findElements(By.xpath(xPath));
        } catch (NoSuchElementException | org.openqa.selenium.NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by XPath: " + xPath + " exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by XPath: " + xPath + " exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Base method for creating web element by xpath in parameter @xPath with index in parameter @index
     * @param xPath Xpath for searching element
     * @param index index of searched web element
     * @return WebElement
     * @throws Throwable NoSuchElementException or Exception
     */
    public static WebElement CreateElementByXpathIndex(String xPath, String index) throws Throwable {
        try {
            int ndx = Integer.parseInt(index);
            By el = By.xpath(xPath);
            h.isElementDisplayed(el);
            List<WebElement> elements = Base.driver.findElements(By.xpath(xPath));
            return elements.get(ndx);
        } catch (NoSuchElementException | org.openqa.selenium.NoSuchElementException e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by XPath: " + xPath + " exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        } catch (Exception e) {
            Log.error("!!!!!!!!!!!!!!!!!!!!!!!!!! Create WebElement by XPath: " + xPath + " exception: " + e + ".!!!!!!!!!!!!!!!!!!!!!!!!");
            throw e;
        }
    }

    /**
     * Returns WebElement searched by By parameter @element
     * @param element By element
     * @return WebElement
     */
    public static WebElement CreateElementBy(By element){
        return Base.driver.findElement(element);
    }

    /**
     * Returns list of WebElement searched by By parameter @element
     * @param element By element
     * @return List of WebElement
     */
    public static List<WebElement> CreateElementsBy(By element){
        return Base.driver.findElements(element);
    }
    //endregion - Basic create methods -

    //region - Custom methods -
    /**
     * Returns WebElement with value attribute equals with text in parameter @value
     * @param value String value
     * @return WebElement
     * @throws Throwable Forwarded from CreateElementByXpath
     */
    public static WebElement CreateSelectorByXpathValue(String value) throws Throwable {
        String xPath = "//*[@value='" + value + "']";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by name of a tag and text which that tag contains
     * @param tag Tag name
     * @param text Text which tag contains
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathTagContainsText(String tag, String text) throws Throwable {
        String xPath = "//" + tag + "[contains(text(), '" + text + "')]";
        return CreateElementByXpath(xPath);
    }


    /**
     * Pattern : //*[text()='Neki tekst']/ancestor::a
     * @param text
     * @param ancestorTag
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathTagContainsTextAndHasAncestor(String text, String ancestorTag) throws Throwable {
        String xPath = "//*[text()='" + text + "']/ancestor::" + ancestorTag + "";
        return CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByXpathTagContainingTextAndHasAncestor(String tag, String text, String ancestorTag) throws Throwable {
        String xPath = "//" + tag + "[contains(text(),'" + text + "')]//ancestor::" + ancestorTag + "";
        return CreateElementByXpath(xPath);
    }

    /**
     * Pattern: //*[@class='imeKlase']/descendant::button
     * @param className
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathClassAndDescendantButton(String className) throws Throwable {
        String xPath = "//*[@class='" + className + "']/descendant::button";
        return CreateElementByXpath(xPath);
    }
    /**
     * Pattern: //*[@class='imeKlase']/ancestor::button
     * @param className
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathClassAndAncestorButton(String className) throws Throwable {
        String xPath = "//*[@class='" + className + "']/ancestor::button";
        return CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByXpathClassName(String className) throws Throwable {
        String xPath = "//*[@class='" + className + "']";
        return CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByXpathClassAndDescendantTag(String className, String descendantTag) throws Throwable {
        String xPath = "//*[@class='" + className + "']//" + descendantTag + "";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by value of title attribute
     * @param titleValue Value of title attribute
     * @return  WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathTitle(String titleValue) throws Throwable {
        String xPath = "//*[@title='" + titleValue + "']";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns Web element by attribute and attribute's value of tag which has descendant which contains text we set for search
     * @param attribute Attribute name
     * @param attributeValue Attribute value
     * @param text  Text which descendant contains
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathAttributeValueAndDescendantContainingText(String attribute, String attributeValue, String text) throws Throwable {
        String xPath = "//*[@" + attribute + "='" + attributeValue + "']//descendant::*[contains(text(),'" + text + "')]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by tag containing text and it's ancestor's attribute name and attribute value
     * @param text Text which tag contains
     * @param attribute Attribute name of ancestor
     * @param attributeName Attribute value of ancestor
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathTagContainsTextAndAncestorWithAttributeAndValue(String text, String attribute, String attributeName) throws Throwable {
        String xPath = "//*[contains(text(), '" + text + "')]//ancestor::*[@" + attribute + "='" + attributeName + "']";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by containing text in a tag
     * @param text Text
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathContainingText(String text) throws Throwable {
        String xPath = "//*[contains(text(),'" + text + "')]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by Tag
     * @param tag Tag name
     * @return WebElement
     */
    public static WebElement CreateElementByXpathTag(String tag) throws Throwable {
        String xPath= "//" + tag;
        return CreateElementByXpath(xPath);
    }

    /**
     * Retruns Web element by Tag and ID
     * @param tag Tag name
     * @param id ID name
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByTagAndId(String tag, String id) throws Throwable {
        String xPath= "//" + tag + "[@id='" + id + "']";
        return CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByXpathClassAndDescendantInputId(String xClass,String descendantId) throws Throwable {
        //TODO @Jovan  Impement Method
        String xPath = "//*[@class='" + xClass + "']//descendant::input[@id='" + descendantId + "']";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by Class name and inner text
     * @param className Class name
     * @param text Text
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathClassContainsText(String className, String text) throws Throwable {
        String xPath = "//*[@class='" + className + "' and contains (text(), '" + text + "')]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by part of a class name and inner text
     * @param className
     * @param text
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathContainsClassContainingText(String className, String text) throws Throwable{
        String xPath = "//*[contains(@class,'" + className + "') and contains (text(), '" + text +"')]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by text and index of element
     * @param index element index
     * @param text Element text
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathTextWithIndex(int index, String text) throws Throwable {
        String xPath = "(//*[text() = '" + text + "'])[" + index + "]";
        return CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByXpathContainsTextAndHasSiblingInput(String text) throws Throwable {
        String xPath = "//*[contains(text(),'" + text + "')]//following-sibling::input";
        return CreateElementByXpath(xPath);
    }

    public static List<WebElement> CreateElementsByXpathClassWithPreceedingSiblingWithAttribute(String className, String attribute) throws Throwable {
        String xPath = "//*[@class='"+ className + "']//preceding-sibling::*[@" + attribute + "]";
        return CreateElementsByXpath(xPath);
    }

    public static WebElement CreateElementByXpathClassAndDescendantTagWithClass(String classNameAncestor, String descendantTag, String classNameDescendant) throws Throwable {
        String xPath = "//*[@class='" + classNameAncestor + "']/descendant::" + descendantTag + "[@class='" + classNameDescendant + "']";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by Tag, attribute, part of attribute's value and text which descendant contains
     * @param tag Tag name
     * @param attribute Attribute name
     * @param attributeValue Part of or whole attribute value
     * @param text Descendant's text
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathTagAttributeContainsValueWithDescendantContainingText(String tag, String attribute, String attributeValue, String text) throws Throwable {
        String xPath = "//" + tag + "[contains(@" + attribute + ", '" + attributeValue + "')]//descendant::*[contains(text(), '" + text + "')]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by tag name and text within it's descendant
     * @param tag Tag name
     * @param text Text which descendant contains
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathTagWithDescendantThatContainsText(String tag, String text) throws Throwable {
        String xPath = "//" + tag + "//descendant::*[contains(text(), '" + text + "')]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by tag name and class of that tag and by text of it's descendant
     * @param tag Tag name
     * @param classValue Class value
     * @param text Text of the descendant
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathTagClassWithDescendantThatContainsText(String tag, String classValue, String text) throws Throwable {
        String xPath = "//" + tag + "[@class='" + classValue + "']//descendant::*[contains(text(), '" + text + "')]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by tag name, text which tag contains and by index of element
     * @param tag Tag name
     * @param text Text
     * @param index Element index
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathTagContainsTextWithIndex(String tag, String text, int index) throws Throwable {
        String xPath = "(//" + tag + "[contains(text(), '" + text + "')])[" + index + "]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElements by Tag name containing Class and by descendant containing Class
     * @param tag Tag name
     * @param className1 Class name of tag
     * @param descendant Descendant name
     * @param className2 Class name of descendant
     * @return List of WebElement
     * @throws Throwable
     */
    public static List<WebElement> CreateElementsByXpathTagContainsClassWithDescendantContainsClass(String tag, String className1, String descendant, String className2) throws Throwable {
        String xPath = "//" + tag + "[contains(@class, '" + className1 + "')]//descendant::" + descendant + "[contains(@class, '" + className2 + "')]";
        return CreateElementsByXpath(xPath);
    }

    /**
     * Returns WebElements by Tag name containing Class and by descendant
     * @param tag Tag name
     * @param className1 Class name of tag
     * @param descendant Descendant name
     * @return List of WebElement
     * @throws Throwable
     */
    public static List<WebElement> CreateElementsByXpathTagContainsClassWithDescendant(String tag, String className1, String descendant) throws Throwable {
        String xPath = "//" + tag + "[contains(@class, '" + className1 + "')]//descendant::" + descendant;
        return CreateElementsByXpath(xPath);
    }

    /**
     * Returns WebElement by containing class
     * @param className Class name
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathContainingClass(String className) throws Throwable {
        String xPath = "//*[contains(@class, '" + className + "')]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by Class name and descendant which contains text
     * @param className Class name
     * @param text Text of descendant
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathClassAndDescendantContainsText(String className, String text) throws Throwable {
        String xPath = "//*[@class='" + className + "']//descendant::*[contains(text(),'" + text + "')]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElements by tag containing class and descendant with class
     * @param tag Tag name
     * @param className1 Class name of Tag
     * @param descendant Descendant name
     * @param className2 Class name of Descendant
     * @return List of WebElement
     * @throws Throwable
     */
    public static List<WebElement> CreateElementsByXpathTagContainsClassWithDescendantWithClass(String tag, String className1, String descendant, String className2) throws Throwable {
        String xPath = "//" + tag + "[contains(@class, '" + className1 + "')]//descendant::" + descendant + "[@class = '" + className2 + "']";
        return CreateElementsByXpath(xPath);
    }

    /**
     * Returns WebElement by tag name, attribute name, attribute value and name and index of descendant
     * @param tag Tag name
     * @param attribute Attribute name
     * @param attributeValue Attribute value
     * @param descendant Descendant tag
     * @param descendantIndex Index of the descendant
     * @return WebElement
     */
    public static WebElement CreateElementByXpathTagAttributeAttributeValueWithDescendantByIndex(String tag, String attribute, String attributeValue, String descendant, int descendantIndex) throws Throwable {
        String xPath = "//" + tag + "[@" + attribute + "='" + attributeValue + "']//descendant::" + descendant + "[" + descendantIndex + "]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by attribute, it's value and preceding sibling
     * @param attribute Attribute name
     * @param attributeValue Attribute value
     * @param precedingSibling Tag of preceding sibling
     * @return WebElement
     */
    public static WebElement CreateElementByXpathAttributeValueAndPrecedingSibling(String attribute, String attributeValue, String precedingSibling) throws Throwable {
        String xPath = "//*[@" + attribute + "='" + attributeValue + "']//preceding-sibling::" + precedingSibling;
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by text and following sibling
     * @param text Text
     * @param followingSibling Following sibling
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathTextFollowingSibling(String text, String followingSibling) throws Throwable {
        String xPath = "//*[text() = '" + text + "']//following-sibling::" + followingSibling;
        return CreateElementByXpath(xPath);
    }

    /**
     * Retruns WebElement by attribute name and value which attribute contains and by element index
     * @param attribute Attribute name
     * @param attributeValue Attribute value
     * @param index Index of the element
     * @return WebElement
     */
    public static WebElement CreateElementByXpathAttributeContainsValueIndexed(String attribute, String attributeValue, int index) throws Throwable {
        String xPath = "(//*[contains(@" + attribute + ", '" + attributeValue + "')])[" + index + "]";
        return CreateElementByXpath(xPath);
    }
    //endregion - Custom methods -

    //region - Examples from old project -
    public static By CreateSelectorByXpathText(String text) throws Throwable {
        return By.xpath("//*[text()='" + text + "']");
    }

    public static WebElement createElementClassNextText(String className, String text) throws Throwable {
        return CreateElementByXpath("//*[@class='" + className + "']//*[text()='" + text + "']");
    }

    /**
     * Returns WebElement for xPath pattern
     * //*[@id='panelUsernamePassword']//descendant::*[contains(@data-bind, 'value: username')]
     * Element with data-bind value equals value in id
     * @param value Value of attribute "value" in data-bind
     * @param elementId Id of element where is data bind encapsulate
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathDataBindValueInId(String value, String elementId) throws Throwable {
        String text = "//*[@id='" + elementId + "']//descendant::*[contains(@data-bind, 'value: " + value + "')]";
        return CreateElementByXpath(text);
    }

    /**
     * Returns WebElement for xPath pattern
     * //*[@data-bind='value: username')]
     * Data-bind parameter value equals method parameter
     * @param value value of attribute parameter "value"
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathDataBindValue(String value) throws Throwable {
        String text = "//*[@data-bind=\"value: " + value + "\"]";

        return CreateElementByXpath(text);
    }

    /**
     * Returns WebElement for xPath pattern
     * //*[@data-bind='paramName: username']
     * @param paramName
     * @param paramValue
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathDataBindParamValue(String paramName, String paramValue) throws Throwable {
        String text = "//*[@data-bind='" + paramName + ": " + paramValue + "']";
        return CreateElementByXpath(text);
    }

    /**
     * Returns WebElement for xPath pattern
     * //*[contains(@data-bind, 'value: mobile')]
     * Data-bind parameter value contains method parameter
     * @param value value of attribute parameter "value"
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathDataBindContainsValue(String value) throws Throwable {
        String text = "//*[contains(@data-bind, 'value: " + value + "')]";
        return CreateElementByXpath(text);
    }


    /**
     * Returns WebElement for xPath pattern
     * //*[@id='panelUsernamePassword']//descendant::button
     * Button in id. Expects only one button
     * @param elementId Od of element where is button encapsulate
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathButtonInId(String elementId) throws Throwable {
        String text = "//*[@id='" + elementId + "']//descendant::button";
        return CreateElementByXpath(text);
    }




    public static By createSelectorByDataBind(String dataBind) {
        return  By.xpath("//*[@data-bind ='"+dataBind+"']");
    }

    public static WebElement createElementByDataBind(String dataBind) throws Throwable {
        String xPath = "//*[@data-bind =\""+dataBind+"\"]";
        return  CreateElementByXpath(xPath);
    }

    //*[@data-bind="visible: accountData().OverdraftUsed==='0'"]//..//p[1]/span[1]
    public static WebElement createElementByDataBind(String index, String dataBind) throws Throwable {
        return CreateElementByXpath("(//*[@data-bind= '"+dataBind+"'])["+index+"]");
    }
    public static WebElement createElementByDataBindFolowingDataBind(String index, String dataBind1,String dataBind2) throws Throwable {
        return CreateElementByXpath("(//*[@data-bind= '"+dataBind1+"']//following::*[@data-bind= \""+dataBind2+"\"])["+index+"]");
    }

    /**
     * Returns web element for class with name in parameter className
     * pattern //[@class='date-picker']
     * @param className Name of the class for search
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement createElementByXpathClassName(String className) throws Throwable {
        String text = "//*[@class='" + className + "']";
        return CreateElementByXpath(text);
    }

    /**
     * Return table header fof text in header as parameter text
     * pattern //th/div[text()='Statement year'
     * @param text Header text
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement createElementByXpathTableHeaderText(String text) throws  Throwable {
        String xPath = "//th/div[text()='" + text + "']";
        return CreateElementByXpath(xPath);
    }

    /**
     * Search tab in widget. Ta is in certain class whr text i parameter tabName
     * @param tabName Name of the tab
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement createElementByXpathWidgetTab(String tabName) throws Throwable {
        String xPath = "//*[@class='k-tabstrip-items k-reset']//descendant::a[text()='" + tabName + "']";
        return CreateElementByXpath(xPath);
    }

    /**
     * Pattern //*[@id='CardStatementsContent']//table/tbody/tr[1]/td[4]/span
     * @param Id id of parent element
     * @param columnIndex index of column
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathRowDetailInId(String Id, String columnIndex) throws Throwable {
        String xPath = "//*[@id='" + Id + "']//table/tbody/tr[1]/td[" + columnIndex + "]/span";
        return CreateElementByXpath(xPath);
    }

    public static boolean ExistsElementByXpathId(String ID) throws Throwable {
        String xPath = "//*[@id=" + ID + "]";
        WebElement element;
        boolean res = true;
        try{
            element = CreateElementByXpath(xPath);
        }
        catch (Exception ex){
            res = false;
        }
        return res;
    }

    /**
     * Custom method returns list of products
     * @return List of WebElement
     * @throws Throwable
     */
    public static List<WebElement> CreateElementsByXpathProductList() throws Throwable {
        String xPath = "//img[contains(@src, 'product-icon')]";
        return CreateElementsByXpath(xPath);
    }

    /**
     * Returns WebElement by tag and class name
     * @param tag Tag
     * @param className Class name
     * @return WebElement
     */
    public static WebElement CreateElementByXpathTagAndClass(String tag, String className) throws Throwable {
        String xPath = "//" + tag + "[@class='" + className + "']";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by Tag, Class name and text
     * @param tag Tag
     * @param className Class name
     * @param text Text
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathTagAndClassContainsText(String tag, String className, String text) throws Throwable {
        String xPath = "//" + tag + "[@class='" + className + "' and contains(text(), '" + text + "')]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by Tag, by part of or whole Class name and by part of or whole text
     * @param tag Tag name
     * @param className Class name
     * @param text Text
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathTagContainsClassAndContainsText(String tag, String className, String text) throws Throwable {
        String xPath = "//" + tag + "[contains(@class, '" + className + "') and contains(text(), '" + text + "')]";
        return CreateElementByXpath(xPath);
    }

    /**
     * Example: //*[@class='details-label' and contains(text(), 'Creditor')]
     * @param className
     * @param text
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathClassAndContainsText(String className, String text) throws Throwable {
        String xPath = "//*[@class='" + className +"' and contains(text(), '" + text + "')]";
        return CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByButtonText(String text) throws Throwable {
        String xPath = "//button[text()='" + text + "']";
        return CreateElementByXpath(xPath);
    }
    public static WebElement CreateElementByButtonContainsText(String text) throws Throwable {
        String xPath = "//button[contains(text(),'" + text + "')]";
        return CreateElementByXpath(xPath);
    }

    /**
     * This method returns WebElement by Tag name and Aria Label value
     * @param tag Tag name
     * @param ariaLabel Aria label value
     * @return WebElement
     */
    public static WebElement CreateElementByXpathTagAndAriaLabel(String tag, String ariaLabel) throws Throwable {
        String xPath = "//" + tag + "[@aria-label = '" + ariaLabel + "']";
        return CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByXpathContainsTextAndHasParent(String text, String tag) throws Throwable {
        String xPath = "//*[contains(text(), '" + text + "')]//parent::" + tag;
        return  CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by containing Class name and by following sibling
     * @param className CLass name
     * @param followingSibling Following sibling
     * @return WebElement
     * @throws Throwable
     */
    public static WebElement CreateElementByXpathContainingClassAndFollowingSibling(String className, String followingSibling) throws Throwable {
        String xPath = "//*[contains(@class, '" + className + "')]//following-sibling::" + followingSibling;
        return CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByXpathDataRole(String dataRole) throws Throwable {
        String xPath = "//*[@data-role='" + dataRole + "']";
        return CreateElementByXpath(xPath);
    }

    /**
     * This method returns WebElement by tag name, descendant and descendant class name.
     * @param tag Tag name
     * @param descendant Descendant
     * @param className Class name of descendant
     * @return WebELement
     */
    public static WebElement CreateElementByXpathTagWithDescendanThatHasClass(String tag, String descendant, String className) throws Throwable {
        String xPath = "//" + tag + "//descendant::" + descendant + "[@class='" + className + "']";
        return CreateElementByXpath(xPath);
    }

    /**
     * Returns WebElement by Tag and containing class with descendant that contains text.
     * @param tag Tag name
     * @param className Class name
     * @param descendant Descendant tag
     * @param text Descendant text
     * @return WebElement
     */
    public static WebElement CreateElementByXpathTagContainsClassWithDescendantContainsText(String tag, String className, String descendant, String text) throws Throwable {
        String xPath = "//" + tag + "[contains(@class, '" + className + "')]//descendant::" + descendant + "[contains(text(), '" + text + "')]";
        return CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByXpathTagContainingTextAndText(String tag, String textOne, String textTwo) throws Throwable {
        String xPath = "//"+tag+"[contains(text(),'"+textOne+"') and contains(text(),'"+textTwo+"')]";
        return CreateElementByXpath(xPath);
    }

    public static WebElement CreateElementByXpathContainsClassWithDescendantClass(String className,String descendantClass) throws Throwable {
        String xPath = "//*[contains(@class,'"+className+"')]//*[@class='"+descendantClass+"']";
        return CreateElementByXpath(xPath);
    }

    public static List<WebElement> CreateElementsByXpathContainsClass(String className) throws Throwable {
        String xPath = "//*[contains(@class,'"+className+"')]";
        return CreateElementsByXpath(xPath);
    }

    public static WebElement CreateElementByXpathContainingClassWithIndex(String className, String index) throws Throwable {
        String xPath = "(//*[contains(@class, '" + className + "')])[" + index + "]";
        return CreateElementByXpath(xPath);
    }

    public static By CreateByElementByXpath(String xpath) {
        By element = null;
        element = By.xpath(xpath);
        return element;
    }

    //endregion - Examples from old project -
}