package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"step", "hooks"},
        plugin = {"pretty"},
        features = {"src/test/resources/"},
        dryRun = false,
        tags = {"@ponto"})
public class RunnerTest {

}
