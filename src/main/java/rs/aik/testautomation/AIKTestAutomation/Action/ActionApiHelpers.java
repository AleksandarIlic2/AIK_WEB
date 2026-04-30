package rs.aik.testautomation.AIKTestAutomation.Action;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import rs.aik.testautomation.AIKTestAutomation.Core.Base;
import rs.aik.testautomation.AIKTestAutomation.Data.DataManager;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Utilities;
import rs.aik.testautomation.AIKTestAutomation.JS.JSHelpers;
import rs.aik.testautomation.AIKTestAutomation.Selectors.SelectByText;
import rs.aik.testautomation.AIKTestAutomation.Selectors.SelectByXpath;
import rs.aik.testautomation.AIKTestAutomation.Wait.WaitHelpers;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ActionApiHelpers {

    //region - Web driver -
    /**
     * Open wep page with url in parameter @url
     * @param url url address of the page
     */
    public static void OpenURL(String url) {
        Base.driver.navigate().to(url);
        Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!! Navigate to url: " + url + " !!!!!!!!!!!!!!!!!!!!!!!!");
    }

    /**
     * Get current URL from web driver
     * @return String url
     */
    public static String getUrl(){
        String url = Base.driver.getCurrentUrl();
        Log.info("!!!!!!!!Url: "+url+" !!!!!!");
        return url;
    }

    /**
     * Switch to next window
     */
    public static void switchToWindow(){
        List<String> windows = new ArrayList<String>(Base.driver.getWindowHandles());
        Base.driver.switchTo().window(windows.get(windows.size()-1));
        Log.info("!!!!!!!!Switch to window!!!!!!!");
    }

    public static void switchToTabWithIndex(int index){
        int i = index-1;
        List<String> tabs = new ArrayList<String>(Base.driver.getWindowHandles());
        Base.driver.switchTo().window(tabs.get(i));
        Log.info("!!!!!!!!Switch to window!!!!!!!");
    }

    //endregion - Web driver -

    //region - Is element displayed -
    /**
     * Custom method for checking is element displayed or not. For element as By parameter xPath, number of
     * cycles for checking, sleeping time per one cycle
     * @param xPath By element
     * @param cycle Number of cycles
     * @param sleepTime Sleeping time per one cycle
     * @return Boolean, true - element is present, false - element is not displayed
     * @throws Throwable
     */
    public static boolean isElementDisplayedCustom(By xPath, int cycle, int sleepTime) throws Throwable {
        for (int i = 1; i <= cycle; i = i + 1) {
            try {
                WebElement element = Base.driver.findElement(xPath);
                boolean displayed = element.isDisplayed();

                if (displayed) {
                    Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Element: " + xPath.toString() + " is visible !!!!!!!!!!!!!!!!!!!!!!!!!");
                    return true;
                }
            } catch (java.util.NoSuchElementException |org.openqa.selenium.NoSuchElementException e) {

            }
            Thread.sleep(sleepTime);
        }
        return false;
    }

    public static boolean isElementDisplayedCustom(By element, int cycle, long timeOut, long sleepTime) throws InterruptedException {
        for (int i = 1; i <= cycle; i = i + 1) {
            try {
                Base.waitVar = new WebDriverWait(Base.driver, timeOut);
                Base.waitVar.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
                JSHelpers.waitForJavaScriptToLoad();
                return true;

            } catch (Exception e) { }
            Thread.sleep(sleepTime);
        }
        return false;
    }

    public static boolean isElementDisplayedShort(By xPath) throws Throwable {
        return isElementDisplayedCustom(xPath, 2, 500, 500);
    }

    /**
     * Method for checking is element displayed or not. For element as By parameter xPath. Default number of cycles and
     * default sleeping time per cycle
     * @param xPath By element
     * @return Boolean, true - element is present, false - element is not displayed
     * @throws Throwable
     */
    public static boolean isElementDisplayed(By xPath) throws Throwable {
        return isElementDisplayedCustom(xPath, 15, 1000);
    }
    //endregion - Is element displayed -

    //region - Is element clickable -
    /**
     * Overloaded method for checking is element clickable with custom parameter for waiting to element load on page
     * @param element WebElement for checking
     * @param cycle Number of cycles
     * @param timeOut Timeout for waiting on WebDriverWait
     * @param sleepTime Sleeping time for one cycle
     * @return Boolean, true - element is clickable, false - element is not clickable
     * @throws InterruptedException
     */
    public static boolean isClickable(WebElement element, int cycle, long timeOut, long sleepTime) throws InterruptedException {
        for (int i = 1; i <= cycle; i = i + 1) {
            try {
                Base.waitVar = new WebDriverWait(Base.driver, timeOut);
                Base.waitVar.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
                JSHelpers.waitForJavaScriptToLoad();
                return true;

            } catch (Exception e) { }
            Thread.sleep(sleepTime);
        }
        return false;
    }

    public static boolean isClickable(By element, int cycle, long timeOut, long sleepTime) throws InterruptedException {
        for (int i = 1; i <= cycle; i = i + 1) {
            try {
                Base.waitVar = new WebDriverWait(Base.driver, timeOut);
                Base.waitVar.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
                JSHelpers.waitForJavaScriptToLoad();
                return true;

            } catch (Exception e) { }
            Thread.sleep(sleepTime);
        }
        return false;
    }

    /**
     * Overloaded method for checking is element clickable with custom parameter @sleepTime
     * @param element WebElement for checking
     * @param sleepTime Sleeping time for one cycle
     * @return Boolean, true - element is clickable, false - element is not clickable
     * @throws InterruptedException
     */
    public static boolean isClickable(WebElement element, long sleepTime) throws InterruptedException {
        return isClickable(element, 5, 10, sleepTime);
    }

    /**
     * Overloaded method for checking is element clickable with default parameter for waiting
     * @param element WebElement for checking
     * @return
     * @throws InterruptedException
     */
    public static boolean isClickable(WebElement element) throws InterruptedException {
        return isClickable(element, 5, 10, 500);
    }
    //endregion - Is element clickable -

    //region - Click on element -

    /**
     * Overloaded method for click on element with waiting.
     * @param element Web element on which should be clicked
     * @throws Throwable
     */
    public static void ClickOnElement(WebElement element) throws Throwable {
        WaitHelpers.WaitForElement(element, "Element");
        try{
            if (isClickable(element)) {
                //Thread.sleep(1800);
                element.click();
                Log.info("!!!!!!!!!!!!!!!!!!!!!! Clicked on the WebElement: " + element.toString() + " successfully.!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
        catch (StaleElementReferenceException elementUpdated) {
            //Thread.sleep(3600);
            element.click();
        } catch (Exception e) {
            Utilities.checkIfPopupInterruptTesting();
            try{
                element.click();
            } catch (Exception ex){
                Log.error("Exception! - could not click on element: " + e.toString());
                throw (e);
            }
        }
        //Thread.sleep(5000);

    }

    /**
     * Overloaded method for click on element with waiting. With custom text for log.
     * @param element Web element on which should be clicked
     * @param text Text for log
     * @throws Throwable
     */
    public static void ClickOnElement(WebElement element, String text) throws Throwable {
        WaitHelpers.WaitForElement(element, "Element");
        try{
            if (isClickable(element)) {
                //Thread.sleep(1800);
                element.click();
                Log.info("!!!!!!!!!!!!!!!!!!!!!! Clicked on the WebElement: " + text + " successfully.!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
        catch (StaleElementReferenceException elementUpdated) {
            //Thread.sleep(3600);
            element.click();
        } catch (Exception e) {
            Utilities.checkIfPopupInterruptTesting();
            try{
                element.click();
            } catch (Exception ex){
                Log.error("Exception! - could not click on element: " + e.toString());
                throw (e);
            }
        }
        //Thread.sleep(5000);
    }

    public static void ClickOnElementFast(WebElement element) throws Throwable {
        try{
            if (isClickable(element)) {
                Thread.sleep(1000);
                element.click();
                Log.info("!!!!!!!!!!!!!!!!!!!!!! Clicked on the WebElement: " + element.toString() + " successfully.!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
        catch (StaleElementReferenceException elementUpdated) {
            Thread.sleep(3600);
            element.click();
        } catch (Exception e) {
            Log.error("Exception! - could not click on element: " + e.toString());
            throw (e);
        }
        Thread.sleep(1000);
        // B.T: Jovan izmenio 5000 -> 1000 proveriti da li možda treba nova funkcija

    }


    public static void ClickOnElementFast(WebElement element, String text) throws Throwable {
        try{
            if (isClickable(element)) {
                Thread.sleep(1000);
                element.click();
                Log.info("!!!!!!!!!!!!!!!!!!!!!! Clicked on the WebElement: " + text + " successfully.!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
        catch (StaleElementReferenceException elementUpdated) {
            Thread.sleep(3600);
            element.click();
        } catch (Exception e) {
            Log.error("Exception! - could not click on element: " + e.toString());
            throw (e);
        }
        Thread.sleep(1000);
    }
    //endregion - Click on element -

    //region - Enter text to element -
    /**
     * Enter simple text in parameter @text to web element in parameter @element
     * @param element Web element
     * @param text Text for entering to web element
     * @throws InterruptedException
     */
    public static void EnterTextToElement(WebElement element, String text) throws InterruptedException {
        Thread.sleep(500);
        element.clear();
        element.sendKeys(text);
        Thread.sleep(500);
        Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!! In WebElement enter text: " + text + ".!!!!!!!!!!!!!!!!!!!!!!!!");
    }



    /**
     * Add text in parameter @text to web element in parameter @element
     * @param element Web element
     * @param text Text for entering to web element
     * @throws InterruptedException
     */
    public static void AddTextToElement(WebElement element, String text) throws InterruptedException {
        Thread.sleep(500);
        element.clear();
        element.sendKeys(text);
        Thread.sleep(500);
        Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!! In WebElement enter text: " + text + ".!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    /**
     * Click on element, then enter simple text in parameter @text to web element in parameter @element
     * @param element Web element
     * @param text Text for entering to web element
     * @throws InterruptedException
     */
    public static void EnterTextToElementWithClick(WebElement element, String text) throws InterruptedException {
        element.click();
        Thread.sleep(500);
        element.clear();
        element.sendKeys(text);
        Thread.sleep(500);
        Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!! In WebElement enter text: " + text + ".!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    /**
     * Click on element, then enter simple text in parameter @text to web element in parameter @element and type tab
     * @param element
     * @param text
     * @throws InterruptedException
     */
    public static void EnterTextToElementWithClickAndTab(WebElement element, String text) throws InterruptedException {
        element.click();
        Thread.sleep(500);
        element.clear();
        element.sendKeys(text);
        element.sendKeys(Keys.TAB);
        Thread.sleep(500);
        Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!! In WebElement enter text: " + text + ".!!!!!!!!!!!!!!!!!!!!!!!!");
    }
    //endregion - Enter text to element -

    //region - Assert -
    /**
     * Asserts if first equal with second
     * @param first first int parameter
     * @param second second int parameter
     * @throws Throwable
     */
    public static void assertEquals(int first, int second) throws Throwable {
        assertTrue(first == second);
    }

    /**
     * Asserts if first equal with second
     * @param first first string parameter
     * @param second second string parameter
     * @throws Throwable
     */
    public static void AssertEquals(String first, String second)  {
        Assert.assertEquals(first,second);
        Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!! Text: " + first + " Asserted successfully. Text " + second + "!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    /**
     * Method assert if element is displayed
     * @param element Web element for assert.
     * @throws Throwable
     */
    public void assertElementIsDisplayed(WebElement element) throws Throwable {
        assertTrue((element.isDisplayed()));
    }

    /**
     * Method assert if element is enabled
     * @param element Web element for assert.
     * @throws Throwable
     */
    public void assertElementIsEnabled(WebElement element) throws Throwable {
        assertTrue((element.isEnabled()));
    }

    /**
     * Method assert if exists element with text equals text in parameter @text
     * @param text Text for search.
     * @throws Throwable
     */
    public void assertElementByText(String text) throws Throwable {
        WebElement element = SelectByText.CreateElementByXpathText(text);
        assertTrue((element.isDisplayed()));
    }

    /**
     * Method assert if exists element with text contains text in parameter @text
     * @param text Text for search.
     * @throws Throwable
     */
    public void assertElementContainingText(String text) throws Throwable {
        WebElement element = SelectByText.CreateElementByXpathContainingText(text);
        assertTrue((element.isDisplayed()));
    }

    /**
     * Method assert if element doesn't have text
     * @param element WebElement for search
     * @param text Text to search
     */
    public static void assertDoesntHaveText(WebElement element, String text) {
        if(element.getAttribute("textContent").contains(text))
            Log.error("!!!!!!!!!!!! Element does have text " +text+ " !!!!!!!");
        else
            Log.info("!!!!!!!!!!!! Element doesn't have text " +text+ " !!!!!!!");
        assertFalse(element.getAttribute("textContent").contains(text));
    }

    /**
     * Method assert if element doesn't have value
     * @param element WebElement for search
     * @param text Text to search
     */
    public static void assertDoesntHaveValue(WebElement element, String text) {
        if(element.getAttribute("value").contains(text))
            Log.error("!!!!!!!!!!!! Element does have text " +text+ " !!!!!!!");
        else
            Log.info("!!!!!!!!!!!! Element doesn't have text " +text+ " !!!!!!!");
        assertFalse(element.getAttribute("value").contains(text));
    }

    /**
     * Method assert if element have value
     * @param element WebElement for search
     * @param text Text to search
     */
    public static void assertHaveValue(WebElement element, String text) {
        if(element.getAttribute("value").contains(text))
            Log.info("!!!!!!!!!!!! Element does have text " +text+ " !!!!!!!");
        else
            Log.error("!!!!!!!!!!!! Element doesn't have text " +text+ " !!!!!!!");
        assertTrue(element.getAttribute("value").contains(text));
    }

    /**
     * Method assert if element have value
     * @param element WebElement for search
     * @param length Length of the value
     */
    public static void assertLengthValueInElement(WebElement element, int length) throws Throwable {
        int lengthValue = element.getAttribute("value").trim().length();
        if(lengthValue==length)
            Log.info("!!!!!!!!!!!! Lengths are the same !!!!!!!");
        else
            Log.error("!!!!!!!!!!!!  Lengths aren't the same !!!!!!!");
        assertEquals(lengthValue, length);
    }
    //endregion - Assert -

    //region - Hoover -
    /**
     * Method to hover over element activate method onHover and click
     * @param element WebElement for hover on
     * @throws Throwable
     */
    public static void HoverElement(WebElement element) throws Throwable {
        WaitHelpers.WaitForElement(element, element.toString());
        Actions actions = new Actions(Base.driver);
        actions.moveToElement(element).click().build().perform();
        Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!! Hover WebElement: " + element.toString() + " .!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    /**
     * Method to hover over element activate method onHover
     * @param element WebElement for hover on
     * @throws Throwable
     */
    public static void HoverWithoutClickElement(WebElement element) throws Throwable {
        WaitHelpers.WaitForElement(element, element.toString());
        Actions actions = new Actions(Base.driver);
        actions.moveToElement(element).build().perform();
        Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!! Hover WebElement: " + element.toString() + " .!!!!!!!!!!!!!!!!!!!!!!!!");
    }
    //endregion - Hoover -

    //region - Simple select command -
    /**
     * Convert WebElement to Select and select option by value
     * @param element Select element for convert
     * @param value Value to select
     */
    public static void SelectByValue(WebElement element, String value){
        Select selectElement = new Select(element);
        selectElement.selectByValue(value);
    }

    /**
     * Convert WebElement to Select and select option by visible
     * @param element Select element for convert
     * @param text Text to select
     */
    public static void SelectByText(WebElement element, String text){
        Select selectElement = new Select(element);
        selectElement.selectByVisibleText(text);
    }

    /**
     * Convert WebElement to Select and select option by index
     * @param element Select element for convert
     * @param ndx Index to select
     */
    public static void SelectByIndex(WebElement element, int ndx){
        Select selectElement = new Select(element);
        selectElement.selectByIndex(ndx);
    }
    //endregion - Simple select command -

    //region - Get value -

    public static String getInnerText(WebElement element){
        return element.getAttribute("value").trim();
    }

    public static void deleteTextFromField(WebElement element){
        element.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
    }

    public static void deleteTextFromFieldLonger(WebElement element){
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.BACK_SPACE);
    }

    //region - Press -
    public void pressAButton(WebElement element, String buttonName){
        element.sendKeys(Keys.valueOf(buttonName));
        Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!! Pressed button "+ buttonName + ".!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public static void pressESCAPE() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
    }
    //endregion - Press -


    //endregion - Get value -

    //region - Example from old project -
    public void assertElementByTextIsNotEmpty(WebElement element) {
        String value = element.getAttribute("textContent");
        assertFalse(value.isEmpty());
        Log.info("!!!!!Text in element: " +value+ " !!!!!!!!!!!!");
    }
    public String getTextContentFromElement(WebElement element) {
        String value = element.getAttribute("textContent");
        Log.info("!!!!!Text in element: " +value+ " !!!!!!!!!!!!");
        return value;
    }
    public void saveTheValueToMap(String dataBind, String paramName) {
        By element = SelectByXpath.createSelectorByDataBind(dataBind);
        List<WebElement> forms = null;
        try {
            forms = SelectByXpath.CreateElementsBy(element);
        } catch (Exception e) {
        }
        ArrayList<String> formTexts = new ArrayList<>();
        for(WebElement form : forms) {
            formTexts.add(getTextContentFromElement(form));
        }
        DataManager.userObject.put(paramName, formTexts);
    }

    public void saveTheValueToMapBasic(String savedValue, String key){
        DataManager.userObject.put(key,savedValue);
    }

    /**
     * Add text in parameter @text to web element in parameter @element and send Enter key
     * @param element
     * @param text
     * @throws InterruptedException
     */
    public static void EnterTextToElementWithEnter(WebElement element, String text) throws InterruptedException {
        Thread.sleep(500);
        element.clear();
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
        Thread.sleep(500);
        Log.info("!!!!!!!!!!!!!!!!!!!!!!!!!! In WebElement enter text: " + text + ".!!!!!!!!!!!!!!!!!!!!!!!!");
    }
    //endregion

    /**
     * Returns today date in format DD.MM.YYYY
     * @return String
     */
    public static String getTodayDate() {
        /*String todayDate = LocalDate.now().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("DD.MM.YYYY");
        return formatter.format(todayDate);*/

        String pattern = "dd.MM.YYYY";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        return dateInString;
    }

    /**
     * Returns today date in specified format
     * @param format Date format
     * @return String
     */
    public static String getTodayDateInFormat(String format) {
        //String todayDate = LocalDate.now().toString();
        //SimpleDateFormat formatter = new SimpleDateFormat(format);
        //return formatter.format(todayDate);
        String dateInString = new SimpleDateFormat(format).format(new Date());
        return dateInString;
    }

    public static String getTodayDatePlusXDays(int x) {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        String pattern = "dd.MM.YYYY";
        String dateInString = new SimpleDateFormat(pattern).format(dt);
        return dateInString;
    }

    public static String getTodayDatePlusXYears(int x) {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.YEAR, 1);
        dt = c.getTime();
        String pattern = "dd.MM.YYYY";
        String dateInString = new SimpleDateFormat(pattern).format(dt);
        return dateInString;
    }

    public static String getTodayDatePlusXDaysYMonths(int x, int y) {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONTH, y);
        c.add(Calendar.DATE, x);
        dt = c.getTime();
        String pattern = "dd.MM.YYYY";
        String dateInString = new SimpleDateFormat(pattern).format(dt);
        return dateInString;
    }

    public static boolean existsElement(String xPath) {
        try {
            Base.driver.findElement(By.xpath(xPath));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

}