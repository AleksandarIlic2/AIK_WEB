package rs.aik.testautomation.AIKTestAutomation.Test;
import jakarta.mail.MessagingException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import rs.aik.testautomation.AIKTestAutomation.Action.ActionApiHelpers;
import rs.aik.testautomation.AIKTestAutomation.Action.HTTPAction;
import rs.aik.testautomation.AIKTestAutomation.Action.MobileAction;
import rs.aik.testautomation.AIKTestAutomation.Action.RoutineHelper;
import rs.aik.testautomation.AIKTestAutomation.Helpers.EmailReaderService;
import rs.aik.testautomation.AIKTestAutomation.JS.JSHelpers;
import rs.aik.testautomation.AIKTestAutomation.Selectors.*;
import rs.aik.testautomation.AIKTestAutomation.Action.*;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Utilities;
import rs.aik.testautomation.AIKTestAutomation.Selectors.*;
import rs.aik.testautomation.AIKTestAutomation.Wait.WaitHelpers;
import rs.aik.testautomation.AIKTestAutomation.Core.Base;
import rs.aik.testautomation.AIKTestAutomation.Data.DataManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static rs.aik.testautomation.AIKTestAutomation.Core.Base.driver;

public class Steps {
    //region - Parameters -
    ActionApiHelpers hp = new ActionApiHelpers();
    RoutineHelper rh = new RoutineHelper();
    HTTPAction ha = new HTTPAction();
    MobileAction ma = new MobileAction();
    Base b = new Base();
    //endregion - Parameters -

    //region - Basic methods -
    @Given("Open Login page")
    public void openLoginPage() {
        //String url = DataManager.testWebSite;
        String url = DataManager.getDataFromConfiguration("1","WebSite");
        ActionApiHelpers.OpenURL(url);
    }

    @Given("Open {string} page")
    public void openPage(String webAddress) {
        ActionApiHelpers.OpenURL(webAddress);
    }

    @And("Breakpoint")
    public void breakpoint() {
        WaitHelpers.waitForSeconds(1);
    }

    @And("Wait for {string} seconds")
    public void waitForSeconds(String timeText) {
        int sec = Integer.parseInt(timeText);
        WaitHelpers.waitForSeconds(sec);
    }

    @Then("Switch to active window")
    public void switchToActiveWindow() {
        hp.switchToWindow();
    }

    @And("Scroll screen down")
    public void scrollScreenDown() {
        JSHelpers.scrollScreenDown();
    }

    @And("Scroll screen up")
    public void scrollScreenUp() {
        JSHelpers.scrollScreenUp();
    }


    @When("Scroll screen {string} down")
    public void scrollScreenDown(String number) {
        int n = Integer.parseInt(number);
        JSHelpers.scrollScreenDown(n);
    }

    @When("Scroll screen {string} up")
    public void scrollScreenUp(String number) {
        int n = Integer.parseInt(number);
        JSHelpers.scrollScreenUp(n);
    }

    @And("Save from Excel {string} columnName {string}")
    public void saveFromExcelColumnName(String rowIndex, String columnName) {
        String text = DataManager.getDataFromHashDatamap(rowIndex, columnName);
        DataManager.userObject.put(columnName, text);
    }
    //endregion - Basic methods -

    //region - Enter text methods -
    @Then("Enter text from Excel {string} columnName {string} in element by class {string} save parameter")
    public void enterTextFromExcelColumnNameInInput(String rowindex, String columnName, String elementName) throws Throwable {
        String text = DataManager.getDataFromHashDatamap(rowindex, columnName);
        DataManager.userObject.put(columnName, text);
        WebElement element = SelectByClassName.CreateElementByClassName(elementName);
        ActionApiHelpers.EnterTextToElementWithClick(element, text);
    }

    @And("Enter text from Excel {string} columnName {string} in field class {string}")
    public void enterTextFromExcelColumnNameInFieldClass(String rowIndex, String columnName, String className) throws Throwable {
        String text = DataManager.getDataFromHashDatamap(rowIndex, columnName);
        WebElement element = SelectByClassName.CreateElementByClassName(className);
        hp.EnterTextToElementWithClick(element, text);
    }

    @And("Enter text from Excel {string} columnName {string} in field name {string}")
    public void enterTextFromExcelColumnNameInFieldName(String rowIndex, String columnName, String nameName) throws Throwable {
        String text = DataManager.getDataFromHashDatamap(rowIndex, columnName);
        WebElement element = SelectByName.CreateElementByName(nameName);
        hp.EnterTextToElementWithClick(element, text);
    }

    @And("Enter text {string} in field by name {string}")
    public void enterTextInFieldByName(String text, String nameName) throws Throwable {
        WebElement element = SelectByName.CreateElementByName(nameName);
        hp.EnterTextToElementWithClick(element, text);
    }

    @And("Enter text {string} in field by class {string}")
    public void enterTextInFieldByClass(String text, String className) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByClassName(className);
        hp.EnterTextToElementWithClick(element, text);
    }

    @And("Enter text {string} in field by class {string} and press enter")
    public void enterTextInFieldByClassAndPressEnter(String text, String className) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByClassName(className);
        text = text + "\n";
        hp.EnterTextToElementWithClick(element, text);
    }

    @And("Enter text {string} in field by data-bind {string}")
    public void enterTextInFieldByDataBind(String text, String dataBind) throws Throwable {
        WebElement element = SelectByXpath.createElementByDataBind(dataBind);
        hp.EnterTextToElementWithClick(element, text);
    }
    //endregion - Enter text methods -

    //region - Click -
    @Then("Click on element by id {string}")
    public void clickOnElementById(String Id) throws Throwable {
        WebElement element = SelectById.CreateElementById(Id);
        hp.ClickOnElement(element, Id);
    }

    @Then("Click on element by text {string}")
    public void clickOnElementByText(String text) throws Throwable {
        WebElement element = SelectByText.CreateElementByXpathText(text);
        hp.ClickOnElement(element, text);
    }

    @Then("Click on span text {string}")
    public void clickOnSpanText(String text) throws Throwable {
        WebElement element = SelectByText.createElementByXpathSpanText(text);
        hp.ClickOnElement(element, text);
    }

    @Then("Click on element by text {string} index {string}")
    public void clickOnElementByTextIndex(String text, String index) throws Throwable {
        WebElement element = SelectByText.CreateElementByXpathIndex(text, index);
        hp.ClickOnElement(element, text);
    }

    @And("Click on element by text {string} if exists")
    public void clickOnElementByTextIfExists(String text) {
        try {
            WebElement element = SelectByText.CreateElementByXpathText(text);
            hp.ClickOnElement(element, text);
        } catch (Throwable ex) {
        }
    }

    @And("Click on element by name {string}")
    public void clickOnElementByName(String elementName) throws Throwable {
        WebElement element = SelectByName.CreateElementByName(elementName);
        hp.ClickOnElement(element, elementName);
    }

    @And("Click on element by data bind {string}")
    public void clickOnElementByDataBind(String dataBind) throws Throwable {
        WebElement element = SelectByXpath.createElementByDataBind(dataBind);
        hp.ClickOnElement(element, dataBind);
    }

    @When("Click on element by class {string}")
    public void clickOnElementByClass(String className) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByClassName(className);
        hp.ClickOnElement(element, className);
    }
    //endregion - Click -

    //region - Select -
    @And("Select visible text {string} by element name {string}")
    public void selectTextByElementName(String text, String elementName) throws Throwable {
        WebElement element = SelectByName.CreateElementByName(elementName);
        hp.SelectByValue(element, text);
    }

    @And("Select value {string} by element name {string}")
    public void selectValueByName(String value, String elementName) throws Throwable {
        WebElement element = SelectByName.CreateElementByName(elementName);
        hp.SelectByValue(element, value);
    }

    @And("Select index {string} by element name {string}")
    public void selectIndexByName(String text, String elementName) throws Throwable {
        WebElement element = SelectByName.CreateElementByName(elementName);
        int index = Integer.parseInt(text);
        hp.SelectByIndex(element, index);
    }
    //endregion - Select -

    //region - Click -
    @And("Assert element by name {string} is enabled")
    public void assertElementByNameIsEnabled(String nameName) throws Throwable {
        WebElement element = SelectByName.CreateElementByName(nameName);
        Assert.assertTrue(element.isEnabled());
    }

    @And("Assert element by name {string} is displayed")
    public void assertElementByNameIsDisplayed(String elementName) throws Throwable {
        WebElement element = SelectByName.CreateElementByName(elementName);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Assert element by value {string}")
    public void assertElementByValue(String value) throws Throwable {
        WebElement element = SelectByXpath.CreateSelectorByXpathValue(value);
        Assert.assertTrue(element.isEnabled());
    }

    @And("Assert element by data-bind {string}")
    public void assertElementByDataBind(String dataBind) throws Throwable {
        WebElement element = SelectByXpath.createElementByDataBind(dataBind);
        Assert.assertTrue(element.isDisplayed());
    }

    @Then("Assert element by text {string}")
    public void assertByText(String text) throws Throwable {
        By elWait = SelectByText.CreateByElementByText(text);
        WaitHelpers.WaitForElement(elWait);

        hp.assertElementByText(text);
    }

    @Then("Assert element by class {string}")
    public void assertElementByClass(String className) throws Throwable {
        By elWait = SelectByClassName.CreateByElementByClassName(className);
        WaitHelpers.WaitForElement(elWait);

        WebElement element = SelectByClassName.CreateElementByClassName(className);
        Assert.assertTrue(element.isDisplayed());
    }

    @Then("Assert element by class {string} with hoover")
    public void assertElementByClassWithHoover(String className) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByClassName(className);
        hp.HoverWithoutClickElement(element);
        assertTrue(element.isDisplayed());
    }

    @Then("Assert element by id {string}")
    public void assertElementById(String elementId) throws Throwable {
        By elWait = SelectById.CreateByElementById(elementId);
        WaitHelpers.WaitForElement(elWait);

        WebElement element = SelectById.CreateElementByXpath(elementId);
        assertTrue(element.isDisplayed());
    }
    //endregion - Assert -

    @And("Enter text {string} in field by id {string}")
    public void enterTextInFieldById(String text, String elementId) throws Throwable {
        WebElement element = SelectById.CreateElementById(elementId);
        hp.EnterTextToElementWithClick(element, text);
    }

    @Then("Assert element by id {string} and text {string}")
    public void assertElementByIdAndText(String elementId, String text) throws Throwable {
        WebElement element = SelectById.CreateElementById(elementId);
        String value = hp.getInnerText(element);
        Assert.assertTrue(text.equals(value));
    }

    @When("Enter text from Excel {string} columnName {string} in field id {string}")
    public void enterTextFromExcelColumnNameInFieldId(String rowIndex, String columnName, String elementId) throws Throwable {
            String text = DataManager.getDataFromHashDatamap(rowIndex, columnName);
            WebElement element = SelectById.CreateElementById(elementId);
            hp.EnterTextToElementWithClick(element, text);
    }

    @And("Enter text {string} in field class {string} and descendant input id {string}")
    public void enterTextInFieldClassAndDescendantInputId(String text, String xClass, String descendantId) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathClassAndDescendantInputId(xClass, descendantId);
        hp.EnterTextToElement(element, text);
    }

    @And("Click on element from Excel {string} contains text columnName {string}")
    public void clickOnElementFromExcelContainsTextColumnName(String rowIndex, String columnName) throws Throwable {
        String text = DataManager.getDataFromHashDatamap(rowIndex, columnName);
        WebElement element = SelectByXpath.CreateElementByXpathTagContainsText("*", text);
        hp.ClickOnElement(element);
    }

    @And("Click on element from Excel {string} contains only text columnName {string}")
    public void clickOnElementFromExcelContainsOnlyTextColumnName(String rowIndex, String columnName) throws Throwable {
        String text = DataManager.getDataFromHashDatamap(rowIndex, columnName);
        WebElement element = SelectByXpath.CreateElementByXpathTagContainsText("*", text);
        hp.ClickOnElement(element);
    }

    @And("Scroll element id {string} into view")
    public void scrollElementIdIntoView(String id) throws Throwable {
        WebElement element = SelectById.CreateElementById(id);
        JSHelpers.ScrollIntoView(element);
    }


    @And("Scroll element attribute {string} and value {string} into view")
    public void scrollElementAttributeAndValueIntoView(String attribute, String value) throws Throwable {
        WebElement element = SelectByAttribute.CreateElementByXpath(attribute, value);
        JSHelpers.ScrollIntoView(element);
    }


    @And("Click on element by attribute {string} and value {string}")
    public void clickOnElementByAttributeAndValue(String attribute, String value) throws Throwable {
        WebElement element = SelectByAttribute.CreateElementByXpath(attribute, value);
        hp.ClickOnElement(element);
    }

    @And("Click on element by attribute {string} and value {string} fast")
    public void clickOnElementByAttributeAndValueFast(String attribute, String value) throws Throwable {
        WebElement element = SelectByAttribute.CreateElementByXpath(attribute, value);
        hp.ClickOnElementFast(element);
    }

    @And("Hover over element with id {string}")
    public void hoverOverElementWithClass(String id) throws Throwable {
        WebElement element = SelectById.CreateElementById(id);
        hp.HoverElement(element);
    }

    @Then("Click on element by text {string} and has ancestor {string}")
    public void clickOnElementByTextAndHasAncestor(String text, String ancestorTag) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathTagContainsTextAndHasAncestor(text, ancestorTag);
        hp.ClickOnElement(element);
    }


    @And("Assert element by class {string} is clickable")
    public void assertElementByClassIsClickable(String className) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByClassName(className);
        hp.isClickable(element);
    }

    @And("Assert element by xPath class {string} is clickable")
    public void assertElementByXpathClassIsClickable(String className) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathClassName(className);
        hp.isClickable(element);
    }

    @And("Assert element by text {string} is clickable")
    public void assertElementByTextIsClickable(String text) throws Throwable {
        WebElement element = SelectByText.CreateElementByXpathText(text);
        hp.isClickable(element);
    }

    @And("Assert element by class {string} and descendant button is clickable")
    public void assertElementByClassAndDescendantButtonIsClickable(String className) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathClassAndDescendantButton(className);
        hp.isClickable(element);
    }

    @And("Assert element by class {string} and ancestor button is clickable")
    public void assertElementByClassAndAncestorButtonIsClickable(String className) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathClassAndAncestorButton(className);
        hp.isClickable(element);
    }

    @And("Assert element by tag {string} containing text {string} is clickable")
    public void assertElementByTagContainingTextIsClickable(String tag, String text) throws Throwable {
        WebElement element = SelectByTagName.CreateElementByTextInTag(tag, text);
        hp.isClickable(element);
    }

    @And("Assert element by class {string} and descendant {string} is clickable")
    public void assertElementByClassAndDescendantIsClickable(String className, String descendantTag) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathClassAndDescendantTag(className, descendantTag);
        hp.isClickable(element);
    }

    @When("Click on element by title {string}")
    public void clickOnElementByTitle(String titleValue) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathTitle(titleValue);
        hp.ClickOnElement(element, titleValue);
    }

    @And("Click on button {string}")
    public void clickOnButton(String buttonName) throws Throwable {
        String tag = "button";
        WebElement element = SelectByXpath.CreateElementByXpathTagContainsText(tag, buttonName);
        hp.ClickOnElementFast(element);
    }

    @And("Click on button {string} slow")
    public void clickOnButtonSlow(String buttonName) throws Throwable {
        String tag = "button";
        WebElement element = SelectByXpath.CreateElementByXpathTagContainsText(tag, buttonName);
        hp.ClickOnElement(element);
    }

    @And("Assert element by contains text {string}")
    public void assertElementByContainsText(String text) throws Throwable {
        String xPath = "//*[contains(text(),'" + text + "')]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpathContainingText(text);
        Assert.assertTrue(element.isDisplayed());
    }


    @And("Assert element by class {string} containing text {string}")
    public void assertElementByClassContainingText(String className, String text) throws Throwable {
        String xPath = "//*[@class='" + className + "' and contains (text(), '" + text + "')]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpathClassContainsText(className, text);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Click on element by containing text {string}")
    public void clickOnElementByContainingText(String text) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathContainingText(text);
        hp.ClickOnElement(element);
    }

    @And("Click on element by tag name {string}")
    public void clickOnElementByTagName(String tagName) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathTag(tagName);
        hp.ClickOnElement(element);
    }

    @And("Send key Backspace {int} times in field by id {string}")
    public void sendKeyBackspaceTimesInFieldById(int n, String id) throws Throwable {
        String key = "BACK_SPACE";
        WebElement element = SelectById.CreateElementById(id);
        rh.sendKeyNTimes(element, n, key);
    }

    @And("Click fast on element by attribute {string} and value {string}")
    public void clickFastOnElementByAttributeAndValue(String attribute, String value) throws Throwable {
        WebElement element = SelectByAttribute.CreateElementByXpath(attribute, value);
        hp.ClickOnElementFast(element);
    }

    @And("Remove text from a field by id {string}")
    public void removeTextFromAFieldById(String id) throws Throwable {
        WebElement element = SelectById.CreateElementById(id);
        hp.deleteTextFromField(element);
    }

    @And("Send key Backspace in field by id {string} till all is deleted")
    public void sendKeyBackspaceInFieldByIdStringTillAllIsDeleted(String id) throws Throwable {
        String key = "BACK_SPACE";
        WebElement element = SelectById.CreateElementById(id);
        int n = element.getAttribute("value").length();
        rh.sendKeyNTimes(element, n, key);
    }

    @Then("Send key Backspace in field by name {string} till all is deleted")
    public void sendKeyBackspaceInFieldByNameTillAllIsDeleted(String name) throws Throwable {
        String key = "BACK_SPACE";
        WebElement element = SelectByName.CreateElementByName(name);
        int n = element.getAttribute("value").length();
        rh.sendKeyNTimes(element, n, key);
    }

    @And("Click on element {int} by text {string}")
    public void clickOnElementByText(int index, String text) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathTextWithIndex(index, text);
        hp.ClickOnElement(element);
    }

    @And("Enter text {string} in field by text {string} has sibling input")
    public void enterTextStringInFieldByTextHasSiblingInput(String fromText, String text) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathContainsTextAndHasSiblingInput(text);
        hp.EnterTextToElement(element, fromText);
    }

    @And("Click on element by tag {string} containing text {string}")
    public void clickOnElementByTagContainingText(String tag, String text) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathTagContainsText(tag, text);
        hp.ClickOnElement(element);
    }

    @And("Click on element by tag {string} and contains text {string}")
    public void clickOnElementByTagAndContainsText(String tag, String text) throws Throwable {
        WebElement element = SelectByTagName.CreateElementByTextInTag(tag, text);
        hp.ClickOnElementFast(element, "Ovde smo kliknuli");
    }

    @And("Click on element by class xpath {string} and appears first")
    public void clickOnElementByClassXpathAndAppearsFirst(String className) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByXpathClassFirstOnly(className);
        JSHelpers.ScrollIntoViewBottom(element);
        hp.ClickOnElement(element);
    }

    @And("Remember attribute {string} inside class {string} that has descendant tag {string} with class {string} under key {string}")
    public void rememberAttributeInsideClassThatHasDescendantTagWithClassUnderKey(String attribute, String classNameAncestor, String descendantTag, String classNameDescendant, String key) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathClassAndDescendantTagWithClass(classNameAncestor, descendantTag, classNameDescendant);
        String content = element.getAttribute(attribute);

        hp.saveTheValueToMapBasic(content, key);
    }

    @And("Check if elements by class {string} have {string} in alphabetical order")
    public void checkIfElementsByClassHaveInAlphabeticalOrder(String className, String attribute) throws Throwable {
        rh.assertElementsByClassHaveInAlphabeticalOrder(className, attribute);
    }

    @And("Assert element by id {string} is not enabled")
    public void assertElementByIdIsNotEnabled(String id) throws Throwable {
        WebElement element = SelectById.CreateElementById(id);
        Assert.assertTrue(element.isEnabled());
    }

    @And("Compare attribute value {string} in xPath {string} to value under key {string}")
    public void compareAttributeValueInXPathToValueUnderKey(String attribute, String xPath, String key) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        Assert.assertEquals(element.getAttribute(attribute), DataManager.userObject.get(key));
    }

    @And("Compare attribute value {string} in class {string} to value under key {string}")
    public void compareAttributeValueInClassToValueUnderKey(String attribute, String xPath, String key) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByClassName(xPath);
        Assert.assertEquals(element.getAttribute(attribute), DataManager.userObject.get(key));
    }

    @And("Assert element by id {string} is enabled")
    public void assertElementByIdIsEnabled(String id) throws Throwable {
        By waitEl = SelectById.CreateByElementById(id);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectById.CreateElementById(id);
        hp.assertElementIsEnabled(element);
    }

    @And("Assert element by xPath class {string}")
    public void assertElementByXPathClass(String className) throws Throwable {
        SelectByClassName.CreateElementByXpath(className);
    }

    @And("Scroll element by xpath {string} into view")
    public void scrollElementByXpathIntoView(String xpath) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpath(xpath);
        JSHelpers.ScrollIntoView(element);
    }

    @And("Remember attribute {string} in xPath {string} under key {string}")
    public void rememberAttributeInXPathUnderKey(String attribute, String xPath, String key) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        hp.saveTheValueToMapBasic(element.getAttribute(attribute), key);
    }

    @And("Remember attribute {string} in class {string} under key {string}")
    public void rememberAttributeInClassUnderKey(String attribute, String className, String key) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByXpath(className);
        hp.saveTheValueToMapBasic(element.getAttribute(attribute), key);
    }

    @And("Click on element by xpath {string}")
    public void clickOnElementByXpath(String xPath) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        hp.ClickOnElement(element);
    }

    @And("Assert element by tag {string} containing text {string}")
    public void assertElementByTagContainingText(String tag, String text) throws Throwable {
        String xPath = "//" + tag + "[contains(text(),'" + text + "')]";
        By elWait = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(elWait);

        WebElement element = SelectByTagName.CreateElementByTextInTag(tag, text);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Click on first element with class {string}")
    public void clickOnFirstElementWithClass(String className) throws Throwable {
        WebElement element = rh.getElementByClassAndIndex(className, 0);
        hp.ClickOnElement(element);
    }

    @And("Click on element by class xpath {string}")
    public void clickOnElementByClassXpath(String xClass) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathClassName(xClass);
        hp.ClickOnElement(element);
    }

    @And("Check if searched text {string} exists in list of elements with class {string}")
    public void checkIfSearchedTextExistsInListOfElementsWithClass(String searchedText, String className) throws Throwable {
        rh.assertSearchedTextExistsInListOfElementsWithClass(searchedText, className);
    }

    @Then("Click on element by class {string} and descendant button")
    public void clickOnElementByClassAndDescendantButton(String className) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathClassAndDescendantButton(className);
        hp.ClickOnElement(element);
    }

    @And("Assert button {string} is not clickable")
    public void assertButtonIsNotClickable(String buttonName) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathTagContainsText("button", buttonName);
        Assert.assertFalse(element.isEnabled());
    }

    @And("Click {string} on element with id {string}")
    public void clickOnElementWithId(String buttonName, String elementId) throws Throwable {
        WebElement element = SelectById.CreateElementById(elementId);
        hp.pressAButton(element, buttonName);
    }

    @And("Assert element by class {string} is not clickable")
    public void assertElementByClassIsNotClickable(String className) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathContainingClass(className);
        Assert.assertTrue(element.isDisplayed());
        hp.ClickOnElement(element);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Assert element by contains class {string}")
    public void assertElementByContainsClass(String className) throws Throwable {
        By elWait = SelectByClassName.CreateByElementByContainsClassName(className);
        WaitHelpers.WaitForElement(elWait);

        WebElement element = SelectByXpath.CreateElementByXpathContainingClass(className);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Click on element by containing class {string}")
    public void clickOnElementByContainingClass(String className) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathContainingClass(className);
        hp.ClickOnElement(element);
    }

    @And("Assert selected creditor country is {string}")
    public void assertSelectedCreditorCountryIs(String country) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpath("(//*[contains(@class, 'dropdown-select-search-input')])[1]");
        String actualCountry = (element.getAttribute("value")).replaceAll(" *$", "");
        Assert.assertTrue(country.equals(actualCountry));
    }

    @And("Assert selected bank country is {string}")
    public void assertSelectedBankCountryIs(String bankCountry) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpath("(//*[contains(@class, 'dropdown-select-search-input')])[2]");
        String actualCountry = (element.getAttribute("value")).replaceAll(" *$", "");
        Assert.assertTrue(bankCountry.equals(actualCountry));
    }

    @And("Remember text of element with class {string} under the key {string}")
    public void rememberTextOfElementWithClassUnderTheKey(String className, String key) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByClassName(className);
        String text = element.getText();
        hp.saveTheValueToMapBasic(text, key);
    }

    @And("Scroll element id {string} into bottom view")
    public void scrollElementIdIntoBottomView(String id) throws Throwable {
        WebElement element = SelectById.CreateElementById(id);
        JSHelpers.ScrollIntoViewBottom(element);
    }

    @And("Assert element by contains class {string} containing text {string}")
    public void assertElementByContainsClassContainingText(String className, String text) throws Throwable {
        String xPath = "//*[contains(@class,'" + className + "') and contains (text(), '" + text +"')]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpathContainsClassContainingText(className, text);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Assert element by contains class {string} with descendant tag {string} containing text {string}")
    public void assertElementByContainsClassWithDescendantTagContainingText(String className, String tag, String text) throws Throwable {
        String xPath = "//*[contains(@class,'" + className + "')]//descendant::" + tag + "[contains(text(),'" + text + "')]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByClassName.CreateElementByXpathContainsClassWithDescendantTagContainingText(className, tag, text);
        Assert.assertTrue(element.isDisplayed());
    }


    @And("Click on element by contains class {string} with descendant tag {string} with id {string}")
    public void clickOnElementByContainsClassWithDescendantTagWithId(String className, String tag, String id) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByXpathContainsClassWithDescendantTagWithId(className, tag, id);
        hp.ClickOnElement(element);
    }

    @And("Click on element by contains class {string} with descendant tag {string} with class {string}")
    public void clickOnElementByContainsClassWithDescendantTagWithClass(String className, String tag, String descendantClassName) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByXpathContainsClassWithDescendantTagWithClass(className, tag, descendantClassName);
        hp.ClickOnElement(element);
    }

    @And("Compare attribute {string} in class {string} to value under key {string}")
    public void compareAttributeInClassToValueUnderKey(String attribute, String className, String key) throws Throwable {
        WebElement element = SelectByClassName.CreateElementByXpath(className);
        Assert.assertEquals(element.getAttribute(attribute), DataManager.userObject.get(key));
    }

    @Then("Compare attribute {string} in id {string} to value under key {string}")
    public void compareAttributeInIdToValueUnderKey(String attribute, String id, String key) throws Throwable {
        WebElement element = SelectById.CreateElementByXpath(id);
        Assert.assertEquals(element.getAttribute(attribute), DataManager.userObject.get(key));
    }

    @And("Click on element by id {string} and descendant tag {string} with class {string}")
    public void clickOnElementByIdAndDescendantTagWithClass(String id, String tag, String className) throws Throwable {
        WebElement element = SelectById.CreateElementByIdAndDescendantTagWithClass(id, tag, className);
        hp.ClickOnElement(element);
    }

    @And("Scroll button {string} into view")
    public void scrollButtonIntoView(String button) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathTagContainsText("button", button);
        JSHelpers.ScrollIntoViewBottom(element);
    }

    @And("Scroll element by contains text {string} into bottom view")
    public void scrollElementByContainsTextIntoBottomView(String text) throws Throwable {
        WebElement element = SelectByText.CreateElementByXpathContainingText(text);
        JSHelpers.ScrollIntoViewBottom(element);
    }

    @And("Scroll element by contains text {string} into view")
    public void scrollElementByContainsTextIntoView(String text) throws Throwable {
        WebElement element = SelectByText.CreateElementByXpathContainingText(text);
        JSHelpers.ScrollIntoView(element);
    }

    @And("Assert element by class {string} and contains text {string}")
    public void assertElementByClassAndContainsText(String className, String text) throws Throwable {
        String xPath = "//*[@class='" + className +"' and contains(text(), '" + text + "')]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpathClassAndContainsText(className, text);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Remember value of element by id {string} under key {string}")
    public void rememberValueOfElementByIdUnderKey(String id, String key) throws Throwable {
        WebElement element = SelectById.CreateElementById(id);
        hp.saveTheValueToMapBasic(element.getAttribute("value"), key);
    }

    @And("Assert element by class {string} and contains text {string} has remembered value from excel {string} columnName {string}")
    public void assertElementByClassAndContainsTextHasRememberedValueFromExcelColumnName(String className, String text, String rowindex, String columnName) throws Throwable {
        String expectedAccountNumber = DataManager.getDataFromHashDatamap(rowindex, columnName);
        WebElement element = SelectByXpath.CreateElementByXpathClassAndContainsText(className, text);
        String actualAccountNumber = element.getAttribute("innerText");
        Assert.assertEquals(expectedAccountNumber, actualAccountNumber);
    }

    @And("Assert element by label with text content {string} that has sibling has remembered value from excel {string} columnName {string}")
    public void assertElementByLabelWithTextContentThatHasSiblingHasRememberedValueFromExcelColumnName(String text, String rowindex, String columnName) throws Throwable {
        String expected = DataManager.getDataFromHashDatamap(rowindex, columnName);
        String xPath = "//label[contains(text(),'" + text + "')]//following-sibling::*[1]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        String actual = element.getAttribute("innerText");
        Assert.assertEquals(expected, actual);
    }

    @And("Assert element by class {string} and contains text {string} has text remembered under key {string}")
    public void assertElementByClassAndContainsTextHasTextRememberedUnderKey(String className, String text, String key) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathClassAndContainsText(className, text);
        String actualValue = element.getAttribute("innerText");
        String expectedValue = DataManager.userObject.get(key).toString();
        Assert.assertEquals(actualValue, expectedValue);
    }

    @And("Assert element by label with text content {string} that has sibling has text remembered under key {string}")
    public void assertElementByLabelWithTextContentThatHasSiblingHasTextRememberedUnderKey(String text, String key) throws Throwable {
        String xPath = "//label[contains(text(),'" + text + "')]//following-sibling::span";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        String actual = element.getAttribute("innerText");
        String expected = DataManager.userObject.get(key).toString();
        Assert.assertEquals(expected, actual);
    }

    @And("Switch to tab with index {int}")
    public void switchToTabWithIndex(int index) {
        hp.switchToTabWithIndex(index);
    }

    @And("Scroll till the end of transactions")
    public void scrollTillTheEndOfTransactions() throws Throwable {
        rh.scrollTillTheEndOfTransactions();
    }

    @And("Scroll button {string} into bottom view")
    public void scrollButtonIntoBottomView(String text) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByButtonText(text);
        JSHelpers.ScrollIntoViewBottom(element);
    }
    @And("Scroll button contains text {string} into bottom view")
    public void scrollButtonContainsTextIntoBottomView(String text) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByButtonContainsText(text);
        JSHelpers.ScrollIntoViewBottom(element);
    }

    @Then("Assert element by class {string} is displayed")
    public void assertElementByClassIsDisplayed(String className) throws Throwable {
        By waitEl = By.xpath("//*[@class='" + className + "']");
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByClassName.CreateElementByXpath(className);
        Assert.assertTrue(element.isDisplayed());
    }

    @Then("Assert element by contains class {string} is displayed")
    public void assertElementByContainsClassIsDisplayed(String className) throws Throwable {
        String xPath = "//*[contains(@class, '" + className + "')]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpathContainingClass(className);
        Assert.assertTrue(element.isDisplayed());
    }

    @Then("Assert element by text {string} is displayed")
    public void assertElementByTextIsDisplayed(String text) throws Throwable {
        By elWait = SelectByText.CreateByElementByText(text);
        WaitHelpers.WaitForElement(elWait);

        WebElement element = SelectByText.CreateElementByXpathText(text);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Assert element by id {string} is empty")
    public void assertElementByIdIsEmpty(String id) throws Throwable {
        By waitEl = SelectById.CreateByElementById(id);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectById.CreateElementById(id);
        Assert.assertEquals("", element.getAttribute("value"));
    }

    @Then("Assert element by tag {string} containing text {string} is displayed")
    public void assertElementByTagContainingTextIsDisplayed(String tag, String text) throws Throwable {
        String xPath = "//" + tag + "[contains(text(),'" + text + "')]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByText.CreateElementByXpathTagContainingText(tag, text);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Scroll to element by contains class {string} containing text under key {string}")
    public void scrollToElementByContainsClassContainingTextUnderKey(String className, String key) throws Throwable {
        String text = DataManager.userObject.get(key).toString();
        WebElement element = SelectByXpath.CreateElementByXpathContainsClassContainingText(className, text);
        JSHelpers.ScrollIntoViewBottom(element);
    }

    @And("Scroll till you find element under key {string} from txt file and click")
    public void scrollTillYouFindElementUnderKeyFromTxtFileAndClick(String key) throws Throwable {
        String text = Utilities.getDataFromTxtFileUnderKey(key);
        //rh.scrollToBottomFindTextAndClick(text);
        rh.scrollXtimesFindTextAndClick(3, text);
    }

    @And("Scroll till you find text {string} and click currency")
    public void scrollTillYouFindTextAndClickCurrency(String text) {
        rh.scrollTillYouFindTextAndClick(text);
    }

    @And("Scroll element containing class {string} into bottom view")
    public void scrollElementContainingClassIntoBottomView(String className) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathContainingClass(className);
        JSHelpers.ScrollIntoViewBottom(element);
    }

    @And("Scroll bottom to element by contains class {string} containing text under key {string}")
    public void scrollBottomToElementByContainsClassContainingTextUnderKey(String className, String key) throws Throwable {
        String text = DataManager.userObject.get(key).toString();
        WebElement element = SelectByXpath.CreateElementByXpathContainsClassContainingText(className, text);
        JSHelpers.ScrollIntoViewBottom(element);
    }

    @And("Assert element by id {string} is displayed")
    public void assertElementByIdIsDisplayed(String id) throws Throwable {
        WebElement element = SelectById.CreateElementById(id);
        Assert.assertTrue(element.isDisplayed());
    }

    @Then("Assert element by title {string}")
    public void assertElementByTitle(String title) throws Throwable {
        String xPath = "//*[@title='" + title + "']";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        Assert.assertTrue(element.isDisplayed());
    }

    @When("Click on element by {string} contains class {string} and tag {string} with text {string}")
    public void clickOnElementByContainsClassAndDescendantTagWithText(String tag, String className, String descendant, String text) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathTagContainsClassWithDescendantContainsText(tag, className,descendant,text);
        hp.ClickOnElement(element);
    }

    @And("Assert value {string} in element by id {string}")
    public void assertValueInElementById(String expected, String id) throws Throwable {
        By waitEl = SelectById.CreateByElementById(id);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectById.CreateElementById(id);
        Assert.assertEquals(expected, element.getAttribute("value"));
    }

    @And("Assert {string} in element by class {string} is from excel {string} columnName {string}")
    public void assertInElementByClassIsFromExcelColumnName(String attribute, String className, String rowindex, String columnName) throws Throwable {
        String expected = DataManager.getDataFromHashDatamap(rowindex,columnName);
        By waitEl = SelectByClassName.CreateByElementByClassName(className);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByClassName.CreateElementByXpath(className);
        Assert.assertEquals(expected, element.getAttribute(attribute));
    }

    @And("Assert tag {string} is displayed")
    public void assertTagIsDisplayed(String tag) throws Throwable {
        String xPath = "//"+tag;
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Assert element by tag {string} containing text {string} and text {string}")
    public void assertElementByTagContainingTextAndText(String tag, String textOne, String textTwo) throws Throwable {
        String xPath = "//"+tag+"[contains(text(),'"+textOne+"') and contains(text(),'"+textTwo+"')]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpathTagContainingTextAndText(tag,textOne,textTwo);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Assert list of elements containing class {string} are displayed")
    public void assertListOfElementsContainingClassAreDisplayed(String className) throws Throwable {
        List<WebElement> webElementList = SelectByClassName.CreateElementsByContainingClassName(className);
        for (WebElement element : webElementList){
            Assert.assertTrue(element.isDisplayed());
        }
    }

    @And("Assert {string} in element by contains class {string} ends with {string}")
    public void assertInElementByContainsClassEndsWith(String attribute, String className, String text) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathContainingClass(className);
        String content = element.getAttribute("innerText");
        Assert.assertTrue(content.endsWith(text));
    }

    @And("Assert values under keys {string} and {string} are different")
    public void assertValuesUnderKeysAndAreDifferent(String key1, String key2) {
        String otp1 = (String) DataManager.userObject.get(key1);
        String otp2 = (String) DataManager.userObject.get(key2);

        Assert.assertNotEquals(otp1,otp2);
    }

    @And("Refresh page")
    public void refreshPage() {
        driver.navigate().refresh();
    }

    @And("Close current tab")
    public void closeCurrentTab() {
        JSHelpers.closeCurrentTab();
    }

    @And("Assert text {string} does not exist")
    public void assertTextDoesNotExist(String text) throws Throwable {
        String xPath = "//*[contains(text(),'"+text+"')]";
        By el = By.xpath(xPath);
        boolean notifExists = ActionApiHelpers.isElementDisplayedCustom(el,3,300);
        Assert.assertFalse(notifExists);
    }

    @And("Assert value {string} in element by contains class {string}")
    public void assertValueInElementByContainsClass(String expected, String className) throws Throwable {
        By waitEl = SelectByClassName.CreateByElementByClassName(className);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpathContainingClass(className);
        Assert.assertEquals(expected,element.getAttribute("value"));
    }

    @And("Assert property {string} has {string} in element by contains class {string}")
    public void assertPropertyHasInElementByContainsClass(String property, String expected, String className) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathContainingClass(className);
        Assert.assertEquals(expected,element.getAttribute(property));
    }

    @And("Assert inner text of element that contains class {string} is from excel {string} columnName {string}")
    public void assertInnerTextOfElementThatContainsClassIsFromExcelColumnName(String className, String rowindex, String columnName) throws Throwable {
        String expected = DataManager.getDataFromHashDatamap(rowindex,columnName);
        WebElement element = SelectByXpath.CreateElementByXpathContainingClass(className);
        Assert.assertEquals(expected,element.getAttribute("innerText"));
    }

    @And("Click on button with title {string}")
    public void clickOnButtonWithTitle(String title) throws Throwable {
        String xPath = "//button[@title='" + title + "']";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        hp.ClickOnElement(element);
    }

    @And("Wait for element by class {string}")
    public void waitForElementByClass(String className) throws Throwable {
        String xPath = "//*[contains(@class,'" + className + "')]";
        By el = By.xpath(xPath);
        WaitHelpers.WaitForElement(el);
    }

    @And("Close previous tab")
    public void closePreviousTab() {
        hp.switchToTabWithIndex(1);
        //JSHelpers.closeCurrentTab();
        JSHelpers.closeCurrentTabNewVersion();
        hp.switchToTabWithIndex(1);
    }

    @And("Scroll dynamic page down")
    public void scrollDynamicPageDown() {
        JSHelpers.ScrollXPagesOnDynamicLoadingpage(1);
    }

    @And("Assert element from excel {string} columnName {string} is not displayed")
    public void assertElementFromExcelColumnNameIsNotDisplayed(String rowindex, String columnName) throws Throwable {
        String text = DataManager.getDataFromHashDatamap(rowindex, columnName);
        String xPath = "//*[contains(text(),'"+text+"')]";
        By el = By.xpath(xPath);
        boolean messageExists = ActionApiHelpers.isElementDisplayedCustom(el,5,1000);
        Assert.assertFalse(messageExists);
    }

    @And("Assert occurence number {string} of tag {string} has innerText from excel {string} columnName {string}")
    public void assertOccurenceNumberOfTagHasInnerTextFromExcelColumnName(String num, String tag, String rowindex, String columnName) throws Throwable {
        String xPath = "(//"+tag+")["+num+"]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        String expected = DataManager.getDataFromHashDatamap(rowindex,columnName);
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        Assert.assertEquals(expected,element.getAttribute("innerText"));
    }

    @And("Assert outer text {string} in elemeny by id {string}")
    public void assertOuterTextInElemenyById(String expected, String id) throws Throwable {
        WebElement element = SelectById.CreateElementById(id);
        Assert.assertEquals(expected, element.getAttribute("outerText"));
    }

    @And("Remember text {string} under key {string} in txt file")
    public void rememberTextUnderKeyInTxtFile(String text, String key) {
        Utilities.saveTheValueToFile(text,key);
    }

    @And("Click on tag {string}")
    public void clickOnTag(String tag) throws Throwable {
        String xPath = "//"+tag+"";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        hp.ClickOnElement(element);
    }

    @Then("Assert element by id {string} is disabled")
    public void assertElementByIdIsDisabled(String id) throws Throwable {
        WebElement element = SelectById.CreateElementById(id);
        Assert.assertEquals("true", element.getAttribute("disabled"));
    }

    @And("Assert value from excel {string} columnName {string} in element by id {string}")
    public void assertValueFromExcelColumnNameInElementById(String rowindex, String columnName, String id) throws Throwable {
        WebElement element = SelectById.CreateElementById(id);
        String expected = DataManager.getDataFromHashDatamap(rowindex,columnName);
        Assert.assertEquals(expected,element.getAttribute("value"));
    }

    @Then("Assert url {string} is opened")
    public void assertUrlIsOpened(String expected) {
        hp.switchToTabWithIndex(2);
        WaitHelpers.waitForSeconds(10);
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(expected,URL);
        JSHelpers.closeCurrentTab();
        hp.switchToTabWithIndex(1);
    }

    @And("Assert element by contains class {string} is disabled")
    public void assertElementByContainsClassIsDisabled(String className) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathContainingClass(className);
        Assert.assertEquals("true",element.getAttribute("disabled"));
    }

    @And("Click on first element by contains class {string}")
    public void clickOnFirstElementByContainsClass(String className) throws Throwable {
        String xPath = "(//*[contains(@class,'"+className+"')])[1]";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        hp.ClickOnElement(element);
    }

    @And("Assert document with name {string} is downloaded")
    public void assertDocumentWithNameIsDownloaded(String name) {
        String path = DataManager.getDataFromHashDatamap("1","pdf_download_path");
        Assert.assertTrue(Utilities.isFileDownloadStartName(path,name));
    }

    @And("Assert text {string} can be found in pdf document in location from excel {string} {string} with file name {string}")
    public void assertTextCanBeFoundInPdfDocumentInLocationFromExcelWithFileName(String text, String rowindex, String columnName, String name) {
        String downloadPath = DataManager.getDataFromHashDatamap(rowindex,columnName);
        String filePath = "file:///"+downloadPath+"/"+name;
        rh.verifyContentInPdfFile(text,filePath);
    }

    @And("Scroll element by contains text from excel {string} columnName {string} into bottom view")
    public void scrollElementByContainsTextFromExcelColumnNameIntoBottomView(String rowindex, String columnName) throws Throwable {
        String text = DataManager.getDataFromHashDatamap(rowindex,columnName);
        WebElement element = SelectByText.CreateElementByXpathContainingText(text);
        JSHelpers.ScrollIntoViewBottom(element);
    }

    @Then("Assert text under key {string} is displayed")
    public void assertTextUnderKeyIsDisplayed(String key) throws Throwable {
        String text = (String) DataManager.userObject.get(key);
        WebElement element = SelectByText.CreateElementByXpathContainingText(text);
        Assert.assertTrue(element.isDisplayed());
    }

    @Then("Assert list of elements with tag {string} contain {string}")
    public void assertListOfElementsWithTagContain(String tag, String expectedCurrency) throws Throwable {
        String xPath = "//" + tag;
        List<WebElement> webElementList = SelectByXpath.CreateElementsByXpath(xPath);
        List<String> actualList = new ArrayList<>();
        for (WebElement element : webElementList){
            actualList.add(element.getAttribute("innerText"));
        }
        Assert.assertTrue(actualList.size()>0);
        for (String s : actualList){
            Assert.assertTrue(s.contains(expectedCurrency));
        }
    }

    @Then("Assert list of elements with tag {string} does not contain {string}")
    public void assertListOfElementsWithTagDoesNotContain(String tag, String expectedCurrency) throws Throwable {
        String xPath = "//" + tag;
        List<WebElement> webElementList = SelectByXpath.CreateElementsByXpath(xPath);
        List<String> actualList = new ArrayList<>();
        for (WebElement element : webElementList){
            actualList.add(element.getAttribute("innerText"));
        }
        Assert.assertTrue(actualList.size()>0);
        for (String s : actualList){
            Assert.assertFalse(s.contains(expectedCurrency));
        }
    }

    @And("Assert element by class {string} and index {string}")
    public void assertElementByClassAndIndex(String className, String index) throws Throwable {
        String xPath = "(//*[@class='" + className + "'])[" + index + "]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Assert element by class {string} and index {string} has attribute {string} with value {string}")
    public void assertElementByClassAndIndexHasAttributeWithValue(String className, String index, String attributeName, String expectedValueOfAttribute) throws Throwable {
        String xPath = "(//*[@class='" + className + "'])[" + index + "]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        String actualValueOfAttribute = element.getAttribute(attributeName);
        Assert.assertEquals(expectedValueOfAttribute, actualValueOfAttribute);
    }

    @And("Assert element by tag {string} has text {string}")
    public void assertElementByTagHasText(String tag, String text) throws Throwable {
        String xPath = "//" + tag + "[text()= '" + text + "']";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Assert element by text from excel {string} columnName {string} is displayed")
    public void assertElementByTextFromExcelColumnNameIsDisplayed(String rowindex, String columnName) throws Throwable {
        String text = DataManager.getDataFromHashDatamap(rowindex, columnName);
        By waitEl = SelectByText.CreateByElementByText(text);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByText.CreateElementByXpathText(text);
        Assert.assertTrue(element.isDisplayed());
    }

    @Then("Assert list of elements with tag {string} and class {string} contains {string}")
    public void assertListOfElementsWithTagAndClassContains(String tag, String className, String value) throws Throwable {
        String xPath = "//" + tag + "[@class='" + className + "']";
        List<WebElement> webElementList = SelectByXpath.CreateElementsByXpath(xPath);
        List<String> actualList = new ArrayList<>();
        for (WebElement element : webElementList){
            actualList.add(element.getAttribute("innerText"));
        }
        Assert.assertTrue(actualList.size()>0);
        for (String s : actualList){
            Assert.assertTrue(s.contains(value));
        }
    }

    @And("Click on element by containing class {string} with index {string}")
    public void clickOnElementByContainingClassWithIndex(String className, String index) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathContainingClassWithIndex(className, index);
        hp.ClickOnElement(element);
    }

    @And("Assert element by tag {string} containing text {string} has innerText {string}")
    public void assertElementByTagContainingTextHasInnerText(String tag, String text, String innerText) throws Throwable {
        WebElement element = SelectByTagName.CreateElementByTextInTag(tag, text);
        Assert.assertTrue(element.isDisplayed());
        Assert.assertEquals(innerText, element.getAttribute("innerText"));
    }

    @And("Assert element with tag {string} and class {string} is not clickable")
    public void assertElementWithTagAndClassIsNotClickable(String tag, String className) throws Throwable {
        String xPath = "//" + tag + "[@class='" + className + "']";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        Assert.assertFalse(element.isEnabled());
    }

    @And("Assert element with text {string} is not displayed")
    public void assertElementWithTextIsNotDisplayed(String text) throws Throwable {
        String xPath = "//*[contains(text(),'"+text+"')]";
        By el = By.xpath(xPath);
        boolean messageExists = ActionApiHelpers.isElementDisplayedCustom(el,5,1000);
        Assert.assertFalse(messageExists);
    }

    @And("Assert button {string} is not displayed")
    public void assertButtonIsNotDisplayed(String text) throws Throwable {
        String xPath = "//button[contains(text(), '" + text + "')]";
        By el = By.xpath(xPath);
        boolean messageExists = ActionApiHelpers.isElementDisplayedCustom(el,5,1000);
        Assert.assertFalse(messageExists);
    }

    @And("Assert element by id {string} contains value {string}")
    public void assertElementByIdContainsValue(String id, String innerText) throws Throwable {
        By waitEl = SelectById.CreateByElementById(id);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectById.CreateElementById(id);
        String actualText = element.getAttribute("value");
        Assert.assertTrue(actualText.contains(innerText));

    }

    @And("Wait for element by text {string}")
    public void waitForElementByText(String text) throws Throwable {
        WebElement element = SelectByText.CreateElementByXpathText(text);
        WaitHelpers.WaitForElement(element, "Element");
    }


    @And("Assert element by contains class {string} with index {string}")
    public void assertElementByContainsClassWithIndex(String className, String index) throws Throwable {
        String xPath = "(//*[contains(@class, '" + className + "')])[" + index + "]";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Click on button containing text {string}")
    public void clickOnButtonContainingText(String text) throws Throwable {
        String xPath = "//button[contains(text(), '" + text + "')]";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        hp.ClickOnElement(element);
    }

    @And("Assert element with tag {string} containing text {string} has full text {string}")
    public void assertElementWithTagContainingTextHasFullText(String tag, String containsText, String expected) throws Throwable {
        String xPath = "//" + tag + "[contains(text(),'" + containsText + "')]";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        Assert.assertEquals(expected, element.getAttribute("innerText"));
    }


    @And("Wait for element by title {string}")
    public void waitForElementByTitle(String title) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpathTitle(title);
        WaitHelpers.WaitForElement(element, "Element");
    }

    @And("Assert element with tag {string} attribute {string} and attribute value {string} has text {string}")
    public void assertElementWithTagAttributeAndAttributeValueHasText(String tag, String attribute, String value, String text) throws Throwable {
        String xPath = "//" + tag + "[@" + attribute + "='" + value + "'and text()='" + text + "']";
        By waitEl = SelectByXpath.CreateByElementByXpath(xPath);
        WaitHelpers.WaitForElement(waitEl);

        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Enter text {string} in field by tag {string} attribute {string} and attribute value {string}")
    public void enterTextInFieldByTagAttributeAndAttributeValue(String text, String tag, String attributeName, String attributeValue) throws Throwable {
        String xPath = "//" + tag + "[@" + attributeName + "='" + attributeValue + "']";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        hp.EnterTextToElement(element, text);
    }

    @And("Enter text from Excel {string} columnName {string} in input field by id {string}")
    public void enterTextFromExcelColumnNameInInputFieldById(String rowIndex, String columnName, String elementId) throws Throwable {
        String text = DataManager.getDataFromHashDatamap(rowIndex, columnName);
        WebElement element = SelectById.CreateElementById(elementId);
        hp.EnterTextToElementWithClick(element, text);
    }

    @And("Wait for login page to load")
    public void waitForLoginPageToLoad() throws InterruptedException {
        By el = SelectById.CreateByElementById("panelUsernamePassword");
        WaitHelpers.WaitForElement(el);
    }

    @And("Enter username from excel {string}")
    public void enterUsernameFromExcel(String rowindex) throws Throwable {
        String username = DataManager.getDataFromHashDatamap(rowindex, "username");
        WebElement element = SelectById.CreateElementById("username");
        hp.EnterTextToElementWithClick(element, username);
    }

    @And("Enter password from excel {string}")
    public void enterPasswordFromExcel(String rowindex) throws Throwable {
        String password = DataManager.getDataFromHashDatamap(rowindex, "password");
        WebElement element = SelectById.CreateElementById("password");
        hp.EnterTextToElementWithClick(element, password);
    }

    @And("Login to application using credentials from excel")
    public void loginToApplicationUsingCredentialsFromExcel() throws Throwable {
        String username = DataManager.getDataFromHashDatamap("1", "username");
        String password = DataManager.getDataFromHashDatamap("1", "password");
        WebElement elementUsername = SelectById.CreateElementById("usernameInput");
        hp.EnterTextToElementWithClick(elementUsername, username);

        WebElement elementLoginButton = SelectByXpath.CreateElementByXpath("//*[@data-bind='click: onLoginUP']");
        hp.ClickOnElement(elementLoginButton);


//
//        WebElement elementPassword = SelectById.CreateElementById("password");
//        hp.EnterTextToElementWithClick(elementPassword, password);
//
//        WebElement elementLoginButton = SelectById.CreateElementById("kc-login");
//        hp.ClickOnElement(elementLoginButton);
    }

    @When("Assert welcome back page has loaded")
    public void assertWelcomeBackPageHasLoaded() throws InterruptedException {
        By el = SelectByXpath.CreateByElementByXpath("//*[contains(@data-resource-key, 'AccountTurnoverTitle')]");
        WaitHelpers.WaitForElement(el);
    }

    @And("Select {string} as space to continue")
    public void selectAsSpaceToContinue(String space) throws Throwable {
        if (space.equals("Operation")){
            WebElement element = SelectById.CreateElementById("space_OPERATION");
            hp.ClickOnElement(element);
        } else if (space.equals("Configuration")){
            WebElement element = SelectById.CreateElementById("space_CONFIGURATION");
            hp.ClickOnElement(element);
        } else {
            System.out.println("Space name provided does not match with available spaces");
        }
    }

    @And("Click on origination menu item")
    public void clickOnOriginationMenuItem() throws Throwable {
        By el = SelectById.CreateByElementById("menuitem_origination");
        WaitHelpers.WaitForElement(el);
        WebElement element = SelectById.CreateElementById("menuitem_origination");
        hp.ClickOnElement(element);
    }

    @And("Click on New application")
    public void clickOnNewApplication() throws Throwable {
        By el = SelectById.CreateByElementById("menuitem_origination_new_application");
        WaitHelpers.WaitForElement(el);
        WebElement element = SelectById.CreateElementById("menuitem_origination_new_application");
        hp.ClickOnElement(element);
    }

    @And("Select {string} process")
    public void selectProcess(String process) throws Throwable {
        By el = SelectByXpath.CreateByElementByXpath("//tlf-start-application");
        WaitHelpers.WaitForElement(el);
        if (process.equals("SBB & PI lending")){
            WebElement element = SelectById.CreateElementById("process_SBB_PI");
            hp.ClickOnElement(element);
        } else {
            System.out.println("Process name provided does not match with available processes");
        }
    }

    @And("Assert {string} is selected")
    public void assertIsSelected(String process) throws Throwable {
        if (process.equals("SBB & PI lending")){
            WebElement element = SelectByXpath.CreateElementByXpath("//*[@id='process_SBB_PI']//button[contains(@class,'selected-process')]");
            Assert.assertTrue(element.isDisplayed());
        } else {
            System.out.println("Process name provided does not match with available processes");
        }
    }

    @And("Click on create button for process")
    public void clickOnCreateButtonForProcess() throws Throwable {
        WebElement element = SelectById.CreateElementById("save-button");
        hp.ClickOnElement(element);
    }

    @And("Select {string} as identifier type")
    public void selectAsIdentifierType(String identifier) throws Throwable {
        WaitHelpers.waitForSeconds(5);
        if (identifier.equals("TIN")){
            WebElement element = driver.findElement(By.xpath("//div[contains(text(),'TIN')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } else if (identifier.equals("CRN")){
            driver.switchTo().frame(0);
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement element = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[contains(@class,'toggle-option') and normalize-space(text())='CRN']")
                    )
            );
            element.click();
        } else {
            System.out.println("Identifier type provided does not match with available identifier types");
        }
    }

    @And("Enter {string} from Excel")
    public void enterFromExcel(String columnName) throws Throwable {
        String crn = DataManager.getDataFromHashDatamap("1", columnName);
        WebElement element = SelectById.CreateElementById("field");
        hp.EnterTextToElementWithClick(element, crn);
    }

    @And("Assert company review page is loaded")
    public void assertCompanyReviewPageIsLoaded() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Step 1: Wait for page to fully load
        wait.pollingEvery(Duration.ofMillis(500));

        wait.until(webDriver -> {
            ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete");
            return webDriver.findElements(By.tagName("iframe")).size() > 0;
        });


        // Step 2: Wait for iframe to appear in DOM
        WebElement iframe = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.tagName("iframe"))
        );

        // Step3: Switch into it
        driver.switchTo().frame(iframe);
        By el = SelectByXpath.CreateByElementByXpath("//*[contains(text(),'Please review your company details')]");
        WaitHelpers.WaitForElement(el);
        WaitHelpers.waitForSeconds(3);
        //driver.switchTo().defaultContent();
    }

    @And("Assert field {string} in company review page has value from excel")
    public void assertFieldInCompanyReviewPageHasValueFromExcel(String fieldName) throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 10);
//
//        // Step 1: Wait for page to fully load
//        wait.pollingEvery(Duration.ofMillis(500));
//
////        wait.until(webDriver -> {
////            ((JavascriptExecutor) webDriver)
////                    .executeScript("return document.readyState").equals("complete");
////            return webDriver.findElements(By.tagName("iframe")).size() > 0;
////        });
//
//
//        // Step 2: Wait for iframe to appear in DOM
//        WebElement iframe = wait.until(
//                ExpectedConditions.presenceOfElementLocated(By.tagName("iframe"))
//        );
//
//        // Step3: Switch into it
//        driver.switchTo().frame(iframe);
        if (fieldName.equals("Company name")){
            String expected = DataManager.getDataFromHashDatamap("1", "company_name");
            wait.until(ExpectedConditions.textToBe(
                    By.xpath("//label[text()='Company name']/following-sibling::div"),
                    expected
            ));
        } else if (fieldName.equals("Registration number")){
            String expected = DataManager.getDataFromHashDatamap("1", "company_registration_number");
            wait.until(ExpectedConditions.textToBe(
                    By.xpath("//label[text()='Registration number']/following-sibling::div"),
                    expected
            ));
        } else if (fieldName.equals("Tax identification number")){
            String expected = DataManager.getDataFromHashDatamap("1", "tax_identification_number");
            wait.until(ExpectedConditions.textToBe(
                    By.xpath("//label[text()='Tax identification number']/following-sibling::div"),
                    expected
            ));
        } else if (fieldName.equals("Company address")){
            String expected = DataManager.getDataFromHashDatamap("1", "company_address");
            wait.until(ExpectedConditions.textToBe(
                    By.xpath("//label[text()='Company address']/following-sibling::div"),
                    expected
            ));
        } else {
            System.out.println("Field name provided does not match with available fields in company review page");
            Assert.fail();
        }
    }

    @And("Assert input field for {string} in company review page has value from excel")
    public void assertInputFieldForInCompanyReviewPageHasValueFromExcel(String id) throws Throwable {
        String expected = DataManager.getDataFromHashDatamap("1", id);
        WebElement element = SelectById.CreateElementById(id);
        String actual = element.getAttribute("value");
        Assert.assertEquals(expected, actual);
        System.out.println("Expected value: " + expected + " Actual value: " + actual);
    }

    @And("Assert offered mobile phone prefix is {string}")
    public void assertOfferedMobilePhonePrefixIs(String expected) throws Throwable {
        WebElement element = SelectByXpath.CreateElementByXpath("//span[@class='phone-prefix']");
        String actual = element.getAttribute("innerText");
        Assert.assertEquals(expected, actual);
    }

    @And("Enter valid phone number in company review page")
    public void enterValidPhoneNumberInCompanyReviewPage() throws Throwable {
        WebElement element = SelectById.CreateElementById("mobile");
        hp.EnterTextToElementWithClick(element, "51234567");
    }

    @And("Validate mobile phone in company review page")
    public void validateMobilePhoneInCompanyReviewPage() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //Entering less than 7 numbers to trigger validation error
        WebElement elementForEnteringSixNumbers = SelectById.CreateElementById("mobile");
        hp.EnterTextToElementWithClick(elementForEnteringSixNumbers, "123456");
        wait.until(ExpectedConditions.textToBe(
                By.xpath("(//*[@class='invalid-text'])[2]"),
                "Please enter a valid mobile number"
        ));
        rh.checkContinueButtonDisabled();

        //Entering more than 8 numbers to trigger validation error
        WebElement elementForEnteringNineNumbers = SelectById.CreateElementById("mobile");
        hp.EnterTextToElementWithClick(elementForEnteringNineNumbers, "123456789");
        wait.until(ExpectedConditions.textToBe(
                By.xpath("(//*[@class='invalid-text'])[2]"),
                "Please enter a valid mobile number"
        ));
        rh.checkContinueButtonDisabled();
    }

    @And("Validate email field in company review page")
    public void validateEmailFieldInCompanyReviewPage() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //Entering blank email to trigger validation error
        WebElement element1 = SelectById.CreateElementById("email");
        hp.EnterTextToElement(element1, "");
        wait.until(ExpectedConditions.textToBe(
                By.xpath("(//*[@class='invalid-text'])[1]"),
                "Email address is required"
        ));
        rh.checkContinueButtonDisabled();

        //no @ or domain
        WebElement element2 = SelectById.CreateElementById("email");
        hp.EnterTextToElement(element2, "plainaddress");
        wait.until(ExpectedConditions.textToBe(
                By.xpath("(//*[@class='invalid-text'])[1]"),
                "Email address is required"
        ));
        rh.checkContinueButtonDisabled();

        //missing local part
        WebElement element3 = SelectById.CreateElementById("email");
        hp.EnterTextToElement(element3, "@missinglocal.com");
        wait.until(ExpectedConditions.textToBe(
                By.xpath("(//*[@class='invalid-text'])[1]"),
                "Email address is required"
        ));
        rh.checkContinueButtonDisabled();

        //missing domain
        WebElement element4 = SelectById.CreateElementById("email");
        hp.EnterTextToElement(element4, "user@");
        wait.until(ExpectedConditions.textToBe(
                By.xpath("(//*[@class='invalid-text'])[1]"),
                "Email address is required"
        ));
        rh.checkContinueButtonDisabled();
    }

    @And("Enter valid email in company review page")
    public void enterValidEmailInCompanyReviewPage() throws Throwable {
        String text = DataManager.getDataFromHashDatamap("1", "email");
        WebElement element = SelectById.CreateElementById("email");
        hp.EnterTextToElementWithClick(element, text);

        WebElement clickElement = SelectByXpath.CreateElementByXpath("//*[@class='legal-text']");
        hp.ClickOnElement(clickElement);
    }

    @And("Assert company data page is loaded")
    public void assertCompanyDataPageIsLoaded() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Step 1: Wait for page to fully load
        wait.pollingEvery(Duration.ofMillis(500));

        wait.until(webDriver -> {
            ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete");
            return webDriver.findElements(By.tagName("iframe")).size() > 0;
        });


        // Step 2: Wait for iframe to appear in DOM
        WebElement iframe = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.tagName("iframe"))
        );

        // Step3: Switch into it
        driver.switchTo().frame(iframe);
        By el = SelectByXpath.CreateByElementByXpath("//*[text()='Company data']");
        WaitHelpers.WaitForElement(el);
    }

    @And("Assert company owner in company data page has value from excel")
    public void assertCompanyOwnerInCompanyDataPageHasValueFromExcel() throws Throwable {
        String expectedFirstName = DataManager.getDataFromHashDatamap("1", "company_owner_first_name");
        String expectedLastName = DataManager.getDataFromHashDatamap("1", "company_owner_last_name");

        WebElement elementForFirstName = SelectByXpath.CreateElementByXpath("//*[@data-bind='text: firstname']");
        WebElement elementForLastName = SelectByXpath.CreateElementByXpath("//*[@data-bind='text: lastname']");

        String actualFirstname = elementForFirstName.getAttribute("textContent");
        String actualLastName = elementForLastName.getAttribute("textContent");

        Assert.assertEquals(expectedFirstName,actualFirstname);
        Assert.assertEquals(expectedLastName,actualLastName);
    }

    @And("Check if continue button is disabled")
    public void checkIfContinueButtonIsDisabled() throws Throwable {
        rh.checkContinueButtonDisabled();
    }

    @And("Assert I have read Notice on the processing of personal data is valid")
    public void assertIHaveReadNoticeOnTheProcessingOfPersonalDataIsValid() throws Throwable {
        String xPath = "//a[text()='Notice on the processing of personal data']";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        assertTrue(element.isDisplayed());
        assertTrue(JSHelpers.checkIfWebElementIsClickable(element));
        hp.ClickOnElement(element);
        hp.switchToTabWithIndex(2);
        WaitHelpers.waitForSeconds(3);
        String URL = driver.getCurrentUrl();
        assertEquals("https://aik-group.com/personal-data-processing/", URL);
        JSHelpers.closeCurrentTab();
        hp.switchToTabWithIndex(1);
    }

    @And("Click on the toggle slider to confirm that I have read Notice on the processing of personal data")
    public void clickOnTheToggleSliderToConfirmThatIHaveReadNoticeOnTheProcessingOfPersonalData() throws Throwable {
        String xPath = "//*[@class='toggle-slider']";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        hp.ClickOnElement(element);
    }

    @And("Assert contact details page is loaded")
    public void assertContactDetailsPageIsLoaded() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Step 1: Wait for page to fully load
        wait.pollingEvery(Duration.ofMillis(500));

        wait.until(webDriver -> {
            ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete");
            return webDriver.findElements(By.tagName("iframe")).size() > 0;
        });


        // Step 2: Wait for iframe to appear in DOM
        WebElement iframe = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.tagName("iframe"))
        );

        // Step3: Switch into it
        driver.switchTo().frame(iframe);
        By el = SelectByXpath.CreateByElementByXpath("//*[text()='Let's continue with mobile and email verification']");
        WaitHelpers.WaitForElement(el);
    }

    @And("Enter valid user email in company review page")
    public void enterValidUserEmailInCompanyReviewPage() throws Throwable {
        String text = DataManager.getDataFromHashDatamap("1", "username");
        WebElement element = SelectById.CreateElementById("email");
        hp.EnterTextToElementWithClick(element, text);

        WebElement clickElement = SelectByXpath.CreateElementByXpath("//*[@class='legal-text']");
        hp.ClickOnElement(clickElement);
    }

    @And("Get redirect link with sms otp from email")
    public void getRedirectLinkWithSmsOtpFromEmail() throws MessagingException, InterruptedException {
        String email = DataManager.getDataFromHashDatamap("1","username");
        EmailReaderService emailService = new EmailReaderService(
                "mail.dtc.rs",
                email,
                "Dobrodosao2022",
                143
        );


        String latestEmail = emailService.waitForEmail(
                "no-reply@ocieu01.circeo.today",
                "Confirm your email address",
                4
        );

        Pattern patternForLink = Pattern.compile("\\[https.*]");
        Matcher matcherForLink = patternForLink.matcher(latestEmail);

        String linkForConfirmation = null;
        String smsCode = null;

        if (matcherForLink.find()) {
            linkForConfirmation = matcherForLink.group();
        }

        Pattern patternForSMS = Pattern.compile("\\d{6}$");
        Matcher matcherForSMS = patternForSMS.matcher(latestEmail);

        if (matcherForSMS.find()) {
            smsCode = matcherForSMS.group();
        }

        linkForConfirmation = linkForConfirmation.replaceAll("^\\[", "");
        linkForConfirmation = linkForConfirmation.replaceAll("\\]$", "");

        DataManager.userObject.put("link", linkForConfirmation);
        DataManager.userObject.put("smsOtp", smsCode);

        System.out.println(linkForConfirmation);
        System.out.println(smsCode);
    }

    @And("Open link under key {string}")
    public void openLinkUnderKey(String key) {
        String link = DataManager.userObject.get(key).toString();
        ActionApiHelpers.OpenURL(link);
    }

    @And("Assert email confirmed page is loaded")
    public void assertEmailConfirmedPageIsLoaded() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Step 1: Wait for page to fully load
        wait.pollingEvery(Duration.ofMillis(500));

        wait.until(webDriver -> {
            ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete");
            return webDriver.findElements(By.tagName("iframe")).size() > 0;
        });


        // Step 2: Wait for iframe to appear in DOM
        WebElement iframe = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.tagName("iframe"))
        );

        // Step3: Switch into it
        driver.switchTo().frame(iframe);
        By el = SelectByXpath.CreateByElementByXpath("//*[text()='Email confirmed']");
        WaitHelpers.WaitForElement(el);
    }

    @And("Assert phone number verification page is loaded")
    public void assertPhoneNumberVerificationPageIsLoaded() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Step 1: Wait for page to fully load
        wait.pollingEvery(Duration.ofMillis(500));

        wait.until(webDriver -> {
            ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete");
            return webDriver.findElements(By.tagName("iframe")).size() > 0;
        });


        // Step 2: Wait for iframe to appear in DOM
        WebElement iframe = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.tagName("iframe"))
        );

        // Step3: Switch into it
        driver.switchTo().frame(iframe);
        By el = SelectByXpath.CreateByElementByXpath("//*[text()='Phone number verification']");
        WaitHelpers.WaitForElement(el);
    }

    @And("Enter otp from key {string}")
    public void enterOtpFromKey(String key) {
        String otp = (String) DataManager.userObject.get(key);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".otp-input")
        ));

        List<WebElement> otpInputs = driver.findElements(By.cssSelector(".otp-input"));

        for (int i = 0; i < otp.length(); i++) {
            WebElement input = otpInputs.get(i);
            input.clear();
            input.sendKeys(String.valueOf(otp.charAt(i)));
        }
    }

    @And("Assert consent page is loaded")
    public void assertConsentPageIsLoaded() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Step 1: Wait for page to fully load
        wait.pollingEvery(Duration.ofMillis(500));

        wait.until(webDriver -> {
            ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete");
            return webDriver.findElements(By.tagName("iframe")).size() > 0;
        });


        // Step 2: Wait for iframe to appear in DOM
        WebElement iframe = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.tagName("iframe"))
        );

        // Step3: Switch into it
        driver.switchTo().frame(iframe);
        By el = SelectByXpath.CreateByElementByXpath("//*[text()='Consent']");
        WaitHelpers.WaitForElement(el);
    }

    @And("Click on the toggle slider to consent to receive future offers from the bank regarding its products and services")
    public void clickOnTheToggleSliderToConsentToReceiveFutureOffersFromTheBankRegardingItsProductsAndServices() throws Throwable {
        String xPath = "(//*[@class='toggle-slider'])[2]";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        hp.ClickOnElement(element);
    }

    @And("Click on the toggle slider to consent to electronic communication with the bank")
    public void clickOnTheToggleSliderToConsentToElectronicCommunicationWithTheBank() throws Throwable {
        String xPath = "(//*[@class='toggle-slider'])[1]";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        hp.ClickOnElement(element);
    }

    @And("Assert process is complete")
    public void assertProcessIsComplete() throws Throwable {
        //WebDriverWait wait = new WebDriverWait(driver, 10);

        // Step 1: Wait for page to fully load
        //wait.pollingEvery(Duration.ofMillis(500));

//        wait.until(webDriver -> {
//            ((JavascriptExecutor) webDriver)
//                    .executeScript("return document.readyState").equals("complete");
//            return webDriver.findElements(By.tagName("iframe")).size() > 0;
//        });


        // Step 2: Wait for iframe to appear in DOM
//        WebElement iframe = wait.until(
//                ExpectedConditions.presenceOfElementLocated(By.tagName("iframe"))
//        );

        // Step3: Switch into it
//        driver.switchTo().frame(iframe);
        WaitHelpers.waitForSeconds(5);
        driver.switchTo().defaultContent();
        By el = SelectByXpath.CreateByElementByXpath("//*[text()='COMPANY - REN 021']");
        WaitHelpers.WaitForElement(el);

        WebElement element = SelectByXpath.CreateElementByXpath("//*[text()='COMPANY - REN 021']");
        Assert.assertTrue(element.isDisplayed());
    }

    @And("Select first representative in company data page")
    public void selectFirstRepresentativeInCompanyDataPage() throws Throwable {
        String xPath = "(//*[@class='checkbox-custom'])[1]";
        WebElement element = SelectByXpath.CreateElementByXpath(xPath);
        hp.ClickOnElement(element);
    }

    @And("Assert that the application cannot be completed online")
    public void assertThatTheApplicationCannotBeCompletedOnline() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 10);
//
//        // Step 1: Wait for page to fully load
//        wait.pollingEvery(Duration.ofMillis(500));
//
//        wait.until(webDriver -> {
//            ((JavascriptExecutor) webDriver)
//                    .executeScript("return document.readyState").equals("complete");
//            return webDriver.findElements(By.tagName("iframe")).size() > 0;
//        });
//
//
//        // Step 2: Wait for iframe to appear in DOM
//        WebElement iframe = wait.until(
//                ExpectedConditions.presenceOfElementLocated(By.tagName("iframe"))
//        );
//
//        // Step3: Switch into it
//        driver.switchTo().frame(iframe);
        WaitHelpers.waitForSeconds(5);
        driver.switchTo().defaultContent();
        driver.switchTo().frame(0);
        System.out.println(driver.getPageSource());

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'This application cannot be completed online')]")
                )
        );

        Assert.assertTrue(element.isDisplayed());

    }

    @And("Assert element by iconName {string}")
    public void assertElementByIconName(String iconName) {

        String xpath = "//*[local-name()='use' and @*[local-name()='href']='#" + iconName + "']";

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement icon = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))
        );

        Assert.assertTrue("Icon not displayed: " + iconName, icon.isDisplayed());
    }

    @And("Click on element by iconName {string}")
    public void clickOnElementByIconName(String iconName) throws Throwable {
        String xpath = "//*[local-name()='use' and @*[local-name()='href']='#" + iconName + "']";
        WebElement element = SelectByXpath.CreateElementByXpath(xpath);
        hp.ClickOnElement(element);
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//
//        WebElement icon = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))
//        );
//
//        Assert.assertTrue("Icon not displayed: " + iconName, icon.isDisplayed());
    }

    @And("Assert sorted by date desc")
    public void assertSortedByDateDesc() {

        WebElement table = driver.findElement(
                By.xpath("//div[contains(@class,'gvStatementList')]//table[contains(@class,'clientGrid')]")
        );

        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        List<LocalDate> dates = new ArrayList<>();

        for (WebElement row : rows) {
            String dateStr = row.findElement(By.xpath("./td[3]")).getText().trim();
            System.out.println("DATE STR: " + dateStr);

            dates.add(LocalDate.parse(dateStr, formatter));
        }

        List<LocalDate> sorted = new ArrayList<>(dates);
        sorted.sort(Comparator.reverseOrder());

        Assert.assertEquals(dates, sorted);
    }


    @And("Assert right currency is displayed")
    public void assertRightCurrencyIsDisplayed() {
        WebElement eurSlide = driver.findElement(
                By.xpath("//div[contains(@class,'swiper-slide')][.//p[contains(text(),'EUR')]]")
        );
        WebElement rsdSlide = driver.findElement(
                By.xpath("//div[contains(@class,'swiper-slide')][.//p[contains(text(),'RSD')]]")
        );
        assertEquals("false", eurSlide.getAttribute("aria-hidden"));
        assertEquals("true", rsdSlide.getAttribute("aria-hidden"));
    }

    @And("Assert field value {string}")
    public void assertFieldValue(String label) {
        String expectedValue = "300,03 EUR";
        String xpath = "//p[normalize-space()='" + label + "']" +
                "/ancestor::div[contains(@class,'inner-loan-detail')]" +
                "/div[contains(@class,'right-inner-loan-detail')]//p";

        WebElement el = driver.findElement(By.xpath(xpath));

        String actual = el.getText().trim();
        Assert.assertEquals(actual, expectedValue);

    }

    @And("Assert field value {string} has value from excel {string} columnName {string}")
    public void assertFieldValueHasValueFromExcelColumnName(String label, String rowindex, String columnName) {
        String expectedValue = DataManager.getDataFromHashDatamap(rowindex, columnName);
        String xpath = "//p[normalize-space()='" + label + "']" +
                "/ancestor::div[contains(@class,'inner-loan-detail')]" +
                "/div[contains(@class,'right-inner-loan-detail')]//p";

        WebElement el = driver.findElement(By.xpath(xpath));

        String actual = el.getText().trim();
        Assert.assertEquals(actual, expectedValue);
    }

    @And("Click on body")
    public void clickOnBody() {
        driver.findElement(By.tagName("body")).click();
    }
}