package rs.aik.testautomation.AIKTestAutomation.JS;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import rs.aik.testautomation.AIKTestAutomation.Core.Base;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Class for executing java scripts
 */
public class JSHelpers {

    //region - Base methods -
    /**
     * Provide base method to execute java scripts
     * @param script Java script for execution
     * @param value List of objects for execution
     */
    public static void Execute(String script, Object... value){
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        executor.executeScript(script, value);
    }

    /**
     * Provide asynchronous base method to execute java scripts
     * @param script Java script for execution
     * @param value List of objects for execution
     */
    public static void ExecuteAsync(String script, Object... value){
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        executor.executeAsyncScript(script, value);
    }
    //endregion - Base methods -

    //region - Scroll -
    /**
     * Scrolling half screen down
     */
    public static void scrollScreenDown(){
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        int contentHeight = ((Number) executor.executeScript("return window.innerHeight")).intValue();
        executor.executeScript("window.scrollBy(0,"+ String.valueOf( contentHeight /2) +")");
    }
    /**
     * Scrolling half screen down by number
     * @param numberOfScreens Number of how many times scroll screen should be done
     */
    public static void scrollScreenDown(int numberOfScreens){
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        int contentHeight = ((Number) executor.executeScript("return window.innerHeight")).intValue();
        executor.executeScript("window.scrollBy(0,"+ String.valueOf( contentHeight * numberOfScreens) +")");
    }

    /**
     * Scroll half screen up
     */
    public static void scrollScreenUp(){
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        int contentHeight = ((Number) executor.executeScript("return window.innerHeight")).intValue();
        executor.executeScript("window.scrollBy(0,"+ String.valueOf( - contentHeight /2) +")");
    }

    /**
     * Scroll screen up by number
     * @param numberOfScreens Number of how many times scroll screen should be done
     */
    public static void scrollScreenUp(int numberOfScreens){
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        int contentHeight = ((Number) executor.executeScript("return window.innerHeight")).intValue();
        executor.executeScript("window.scrollBy(0,"+ String.valueOf( - contentHeight * numberOfScreens) +")");
    }

    /**
     * method ScrollIntoView gets @param element and scroll to that element
     */
    public static void ScrollIntoView(WebElement element) throws Throwable {
        ((JavascriptExecutor) Base.driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
    }

    public static void ScrollIntoViewBottom(WebElement element) throws InterruptedException {
        ((JavascriptExecutor) Base.driver).executeScript("arguments[0].scrollIntoView(false);", element);
        Thread.sleep(500);
    }

    public static void ScrollToTheBottomOfPage(){
        ((JavascriptExecutor) Base.driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }

    public static void ScrollToTheBottomOfDynamicLoadingPage(){
        JavascriptExecutor js = (JavascriptExecutor) Base.driver;
        try {
            long lastHeight=((Number)js.executeScript("return document.body.scrollHeight")).longValue();
            while (true) {
                ((JavascriptExecutor) Base.driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(4000);

                long newHeight = ((Number)js.executeScript("return document.body.scrollHeight")).longValue();
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void ScrollXPagesOnDynamicLoadingpage(int x){
        JavascriptExecutor js = (JavascriptExecutor) Base.driver;
        try {
            long lastHeight=((Number)js.executeScript("return document.body.scrollHeight")).longValue();
            for(int i=0; i<x; i++) {
                ((JavascriptExecutor) Base.driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(6000);

                long newHeight = ((Number)js.executeScript("return document.body.scrollHeight")).longValue();
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //endregion - Scroll -

    //region - Examples from old projects -
    /**
     * method AssertColorByRGBA
     *
     * @param rgba  used for asserting elements with it color
     * @param xpath used for finding element
     */

    public static void AssertColorByRGBA(String rgba, String xpath) throws Throwable {
        WebElement element = Base.driver.findElement(By.xpath(xpath));
        String color = element.getCssValue("background-color");
        Log.info("Backgound color is: " + color + "!!!!!!!!!!!!!!!!!!");
        assertTrue(element.getCssValue("background-color").contains("rgba(" + rgba + ")"));

    }

    /**
     * method SelectTextInFieldByElementID checks if text is in options
     * @param text
     * @param id   used for getting element by id
     */
    public static void SelectTextInFieldByElementID(String text, String id) {
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        executor.executeScript("var sel = document.getElementById('" + id + "');\n" +
                "for(var i = 0; i < sel.options.length; i++) {\n" +
                "    if(sel.options[i].text == \"" + text + "\"){\n" +
                "        sel.selectedIndex = i;\n" +
                "    }\n" +
                "}");
    }

    /**
     * method checks if text is in options
     *
     * @param text
     * @param elementName used for getting element by class name
     */

    public static void SelectTextInFieldByElementClassName(String text, String elementName) {
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        executor.executeScript("var sel = document.getEelementsByClassName('" + elementName + "')[0];\n" +
                "for(var i = 0; i < sel.options.length; i++) {\n" +
                "    if(sel.options[i].text == \"" + text + "\"){\n" +
                "        sel.selectedIndex = i;\n" +
                "    }\n" +
                "}");
    }

    public static void waitForJavaScriptToLoad(){
        //
    }

    public static void setTextToElementByTagName(String tagName, String index, String text){
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        String script = "document.getElementsByTagName('" + tagName + "')[" + index + "].innerHTML = \"" + text + "\"";
        executor.executeScript(script);
    }
    //endregion - Examples from old projects -
    public static void openNewTabAndSwitchToIt(){
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        executor.executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(Base.driver.getWindowHandles());
        Base.driver.switchTo().window(tabs.get(1));
    }

    public static void closeCurrentTab(){
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        executor.executeScript("window.close()");
    }

    public static void checkIfEditContactCheckBoxIsEnabled() throws InterruptedException {
        //String script = "return window.getComputedStyle(document.querySelector('.analyzer_search_inner.tooltipstered'),':after').getPropertyValue('content')";
        //String script = "return window.getComputedStyle(document.querySelector('.checkmark'),':after').getPropertyValue('content')";
        String script = "return window.getComputedStyle(document.querySelector('.checkmark'),':after').getPropertyValue('display')";
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) Base.driver;
        String content = (String) js.executeScript(script);
        Assert.assertEquals("block",content);
        System.out.println(content);
    }

    public static void clickNextOnDashboardAccount() {
        String script = "document.querySelector(\"nlb-web-retail > div > bb-root > bb-area > bb-chrome > nlb-web-retail-layout-container > nlb-web-retail-page-layout-ui > div > div > div > div.w-100.main-container-wrapper > bb-chrome.w-100 > bb-panel-container > bb-area > bb-chrome > bb-deck-container > bb-route > bb-chrome > bb-panel-container > bb-area > bb-chrome > bb-deck-container > bb-route > bb-chrome > bb-panel-container > bb-area > bb-chrome > bb-column-container > div > bb-column:nth-child(1) > bb-chrome > bb-panel-container > bb-area > bb-chrome:nth-child(2) > nlb-dashboard-my-balances-widget > div > div > nlb-my-balances > div > div.sp-card-stack > div.buttons.next > i\").click()";
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        executor.executeScript(script);
    }

    public static void clickPrevOnDashboardAccount() {
        String script = "document.querySelector(\"nlb-web-retail > div > bb-root > bb-area > bb-chrome > nlb-web-retail-layout-container > nlb-web-retail-page-layout-ui > div > div > div > div.w-100.main-container-wrapper > bb-chrome.w-100 > bb-panel-container > bb-area > bb-chrome > bb-deck-container > bb-route > bb-chrome > bb-panel-container > bb-area > bb-chrome > bb-deck-container > bb-route > bb-chrome > bb-panel-container > bb-area > bb-chrome > bb-column-container > div > bb-column:nth-child(1) > bb-chrome > bb-panel-container > bb-area > bb-chrome:nth-child(2) > nlb-dashboard-my-balances-widget > div > div > nlb-my-balances > div > div.sp-card-stack > div.buttons.prev > i\").click()";
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        executor.executeScript(script);
    }

    public static void closeCurrentTabNewVersion() {
        JavascriptExecutor executor = (JavascriptExecutor) Base.driver;
        executor.executeScript("top.window.close()");
    }

    public static boolean checkIfWebElementIsClickable(WebElement element){
        // Check if the element is visible, enabled, and within the viewport using JavaScript
        JavascriptExecutor js = (JavascriptExecutor) Base.driver;
        Boolean isClickable = (Boolean) js.executeScript(
                "var elem = arguments[0];" +
                        "var rect = elem.getBoundingClientRect();" +
                        "var isInViewport = rect.top >= 0 && rect.left >= 0 && rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && rect.right <= (window.innerWidth || document.documentElement.clientWidth);" +
                        "return (elem.offsetWidth > 0 && elem.offsetHeight > 0 && window.getComputedStyle(elem).display !== 'none' && isInViewport);",
                element
        );

        if (isClickable) {
            System.out.println("The link is clickable.");
        } else {
            System.out.println("The link is not clickable.");
        }
        return isClickable;
    }
}
    
