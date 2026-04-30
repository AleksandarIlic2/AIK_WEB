package rs.aik.testautomation.AIKTestAutomation.Core;

import org.testng.Assert;
import org.testng.annotations.Test;


public class BaseTest {
    private String url = "";
    private String test = "test";

    @Test
    public void testCreateDriver() {
        // Arrange & Act
        Base.createDriver();
        Base.driver.get(url);
        // Assert
        Assert.assertTrue(Base.driverInitialized);
        // Cleanup
        if(Base.driver != null){ Base.closeDriver(); }
    }
}
