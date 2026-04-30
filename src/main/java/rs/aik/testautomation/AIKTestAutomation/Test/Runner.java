package rs.aik.testautomation.AIKTestAutomation.Test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="Features",
        glue={"si.nlb.testautomation.NLBTestAutomation.Test"},
        plugin = {
                "pretty",
                "html:target/cucumber/report.html"
        }
)
public class Runner {

}
