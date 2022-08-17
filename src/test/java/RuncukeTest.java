
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions
		(
				features = "src/test/resources",
				glue = "stepDefinitions",
				dryRun = false,
				monochrome = true,
				tags= "@Vishnu",
				plugin = {
				"pretty",
				"html:target/cucumber-reports/cucumber-html-report.html",
				"json:target/cucumber-reports/cucumber.json",
				"junit:target/cucumber-reports/cucumber.xml"
				}
		)
public class RuncukeTest extends AbstractTestNGCucumberTests {
}

