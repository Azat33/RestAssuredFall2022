package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/feature",
        glue = "steps",
        tags = "@getAllUsers or @createUser or @deleteUser",
        dryRun = false,
        publish = true
)
public class Runner {

}
