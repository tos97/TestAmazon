package it.tosiani;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "it.tosiani.step.cucumber",
        plugin = {"html:testreportdir","json:testreportdir/testreport.json"}
)
public class RunCucumberTest {
}
