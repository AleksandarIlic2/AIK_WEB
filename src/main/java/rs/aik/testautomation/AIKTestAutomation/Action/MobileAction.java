package rs.aik.testautomation.AIKTestAutomation.Action;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import rs.aik.testautomation.AIKTestAutomation.Data.DataManager;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;
import rs.aik.testautomation.AIKTestAutomation.Selectors.SelectMobileByXpath;
import rs.aik.testautomation.AIKTestAutomation.Wait.WaitHelpers;
import rs.aik.testautomation.AIKTestAutomation.Core.BaseMobile;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;

import static io.appium.java_client.touch.offset.PointOption.point;

public class MobileAction {

    //region - Element present -
    /**
     * Check if element is present
     * @param xpath Element xpath for check
     * @return true/false
     * @throws Throwable
     * Move to Action helper
     */
    public boolean isElementPresent(By xpath, int cycle, int wtime) throws Throwable {
        for (int i = 1; i <= cycle; i = i + 1) {
            try {
                WebElement element = BaseMobile.driver.findElement(xpath);
                boolean displayed = element.isDisplayed();

                if (displayed) {
                    return true;
                }
            } catch (NoSuchElementException e) {

            }
            Thread.sleep(wtime);
        }
        return false;
    }

    /**
     * Check if element is present
     * @param xpath Element xpath for check
     * @return true/false
     * @throws Throwable
     * Move to Action helper
     */
    public boolean isElementPresent(By xpath) throws Throwable {
        return isElementPresent(xpath, 25, 1000);
    }

    /**
     * Check if element is present with short wait time
     * @param xpath Element xpath for check
     * @return true/false
     * @throws Throwable
     * Move to action helper
     */
    public boolean isElementPresentShortly(By xpath) throws Throwable {
        return isElementPresent(xpath, 5, 50);
    }

    /**
     * Check if element is present
     * @param element WebElement for check
     * @return true/false
     * @throws Throwable
     * Move to Action helper
     */
    public boolean isElementPresent(WebElement element, int cycle, int wtime) throws Throwable {
        for (int i = 1; i <= cycle; i = i + 1) {
            try {
                boolean displayed = element.isDisplayed();

                if (displayed) {
                    return true;
                }
            } catch (NoSuchElementException e) {

            }
            Thread.sleep(wtime);
        }
        return false;
    }

    public boolean isElementPresent(WebElement element) throws Throwable {
        return isElementPresent(element, 10, 1000);
    }

    public Boolean isPresentBehindElement(By by) {
        Boolean a = true;
        if (!BaseMobile.driver.findElement(by).getAttribute("visible").equals("false")) {
            a = false;
        }
        return a;
    }

    /**
     * Check if element is present
     * @param element WebElement for check
     * @return true - not present, false - present
     * @throws Throwable
     * Move to Action helper
     */
    public boolean isNotElementPresent(By element) {
        try {
            BaseMobile.driver.findElement(element);
            return false;
        } catch (java.util.NoSuchElementException | org.openqa.selenium.NoSuchElementException e) {
            // Log.info("!!!!!!!!! Element " + element + " doesn't exists !!!!!!!!!!!!!!!!");
            return true;
        }
    }

    //endregion - Element present -

    //region - Base methods -
    /**
     * Opens application
     * @throws Throwable
     */
    public void goToApp() throws Throwable {
        unlockIfDeviceIsLocked();
        //Thread.sleep(5000);
    }

    /**
     * Goes back.
     * @throws InterruptedException
     */
    public void goBackToApp() throws InterruptedException {
        BaseMobile.driver.navigate().back();
        Thread.sleep(5000);
    }
    /**
     * Unlock device
     * @throws InterruptedException
     * Move to Action Helpers
     */
    public void unlockIfDeviceIsLocked() throws InterruptedException {
        Thread.sleep(3000);
        if (BaseMobile.testPlatform.equals("Android")) {
            if (((AndroidDriver) BaseMobile.driver).isDeviceLocked())
                ((AndroidDriver) BaseMobile.driver).unlockDevice();
        }
        if (BaseMobile.testPlatform.equals("iOS")) {

        }
    }

    /**
     * Click on element by xpath
     * @param xpath - Element xpath
     * @throws Exception
     * Move to Action helpers
     */
    public void clickElement(By xpath) throws Exception {
        BaseMobile.waitVar = new WebDriverWait(BaseMobile.driver, 90);
        boolean elementPresent = BaseMobile.waitVar.until(ExpectedConditions.elementToBeClickable(xpath)).isEnabled();
        try {
            if (elementPresent) {
                Thread.sleep(2000);
                BaseMobile.driver.findElement(xpath).click();
                Log.info("!!!!!!!!!!!!!!!!!!!!!!!!! Tap on " + xpath.toString() + " !!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        } catch (StaleElementReferenceException elementUpdated) {
            BaseMobile.driver.findElement(xpath);
            BaseMobile.driver.findElement(xpath).click();
        } catch (Exception e) {
            System.out.println("Exception! - could not click on link: " + e.toString());
            throw (e);
        }
        Thread.sleep(3000);
    }

    /**
     * Enters text in parameter @text to element by xpath
     * @param text - Text to enter.
     * @param id - Element ID
     * @throws Throwable
     * Change logic
     */
    public void inputTextInFieldById(String text, String id) throws Throwable {
        By element = SelectMobileByXpath.createElementById(id);
        WaitHelpers.waitForElement(element, 30);
        Assert.assertTrue(isElementPresent(element));
        clickElement(element);
        WebElement el = BaseMobile.driver.findElement(element);
        BaseMobile.driver.getKeyboard().sendKeys(text);
        //clearInputField(el);
        //el.sendKeys(text);
    }

    public String getAttribute(By element, String attrib){
        WaitHelpers.waitForElement(element, 3);
        String value = BaseMobile.driver.findElement(element).getAttribute(attrib);
        return value.trim();
    }

    public void getMobileOTP(String pin) throws Throwable {

        if(DataManager.isDeviceVirtual.equals("1")){
            goToApp();
            String xPath = "//*[@text='One-time password OTP']";
            By element = SelectMobileByXpath.createByXpath(xPath);
            clickElement(element);
            inputTextInFieldById(pin, "background");
            //By elementPIN = SelectMobileByXpath.createElementById("textOtpValue");
            //String otpCode = getAttribute(elementPIN, "text");
            //otpCode = otpCode.replace(" ", "");
            WaitHelpers.waitForSeconds(5);
            new TouchAction(BaseMobile.driver).tap(point(1015,255)).perform();
            String cliboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            String otpCode = cliboard.replaceAll(" ","");
            DataManager.userObject.put("OTP", otpCode);
        }
        else{
            goToApp();
            String xPath = "//*[@text='One-time password OTP']";
            By element = SelectMobileByXpath.createByXpath(xPath);
            clickElement(element);
            inputTextInFieldById(pin, "background");
            WaitHelpers.waitForSeconds(5);
            By element1 = SelectMobileByXpath.createByXpath("//*[@resource-id='co.infinum.nlb.uat:id/textOtpValue']");
            String otp = BaseMobile.driver.findElement(element1).getAttribute("text");
            String otpCode = otp.replaceAll(" ","");
            DataManager.userObject.put("OTP", otpCode);
            BaseMobile.driver.quit();
        }
    }

    public void blockAccessToWebFromMobile() throws Throwable {
        goToApp();
        String xPathForLogin = "//*[@text='Login to NLB Klik - BETA']";
        By elementForLogin = SelectMobileByXpath.createByXpath(xPathForLogin);
        clickElement(elementForLogin);
        inputTextInFieldById("1379","background");
        By elementSetings = SelectMobileByXpath.createElementById("product_summary_toolbar_icon");
        clickElement(elementSetings);
        String xPathForSecurityAndPrivacy = "//*[@text='Security and privacy']";
        By elementForSecurityAndPrivacy = SelectMobileByXpath.createByXpath(xPathForSecurityAndPrivacy);
        clickElement(elementForSecurityAndPrivacy);
        String xPathForBlockWebAndMobileBank = "//*[@text='Block mobile or online bank']";
        By elementForBlockWebAndMobileBank = SelectMobileByXpath.createByXpath(xPathForBlockWebAndMobileBank);
        clickElement(elementForBlockWebAndMobileBank);
        By elementForSwitch = SelectMobileByXpath.createElementById("webSwitch");
        clickElement(elementForSwitch);
        WaitHelpers.waitForSeconds(5);
        BaseMobile.teardown();
        WaitHelpers.waitForSeconds(5);
        BaseMobile.createDriver();
    }

    public void getMobileOTPWhileAppAlreadyOpened(String pin) throws Throwable {
        goToApp();
        new TouchAction(BaseMobile.driver).tap(point(1015,255)).perform();
        String cliboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        String otpCode = cliboard.replaceAll(" ","");
        DataManager.userObject.put("OTP", otpCode);
    }
}
