package rs.aik.testautomation.AIKTestAutomation.Selectors;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;


import rs.aik.testautomation.AIKTestAutomation.Core.BaseMobile;
import rs.aik.testautomation.AIKTestAutomation.Data.DataManager;
import rs.aik.testautomation.AIKTestAutomation.Test.Hooks;

public class SelectMobileByXpath {
    //Hooks.resourceId = envData.get("resource_id");

    //region - Methods -

    /**
     * Returns By element by string parameter xPath
     *
     * @param xPath xPath for creating element.
     * @return By element
     */
    public static By createByXpath(String xPath) {
        By element = null;
        if (BaseMobile.testPlatform.equals("iOS")) {
            element = MobileBy.xpath(xPath);
        }
        if (BaseMobile.testPlatform.equals("Android")) {
            element = MobileBy.xpath(xPath);
        }
        return element;
    }

    //region - Base methods -
    /**
     * Returns By element with parameter id
     * @param id Id of element
     * @return By element
     */
    public static By createElementById(String id) {
        Hooks.resourceId = DataManager.getDataFromConfiguration("1","resource_id");
        By element = null;
        if (BaseMobile.testPlatform.equals("iOS")) {
            element = MobileBy.AccessibilityId(id);
        }
        if (BaseMobile.testPlatform.equals("Android")) {
            element = MobileBy.id(Hooks.resourceId + id);
        }
        return element;
    }
}