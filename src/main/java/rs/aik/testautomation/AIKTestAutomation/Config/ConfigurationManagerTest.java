package rs.aik.testautomation.AIKTestAutomation.Config;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Enumeration;

public class ConfigurationManagerTest {
    @Test
    public void testLoadProperties() {
        // Arrange
        ConfigurationManager cm = new ConfigurationManager();

        // Act
        cm.loadProperties();

        ArrayList<String> arrayList = new ArrayList<>();
        Enumeration enu = cm.props.elements();
        while(enu.hasMoreElements()){
            arrayList.add(enu.nextElement().toString());
        }
        // Assert
        Assert.assertTrue(arrayList.size()>0);
    }

    @Test(enabled = false)
    public void testLoadGlobalConfiguration() {
    }

    @Test(enabled = false)
    public void testLoadFirefoxOptions() {
    }
}
