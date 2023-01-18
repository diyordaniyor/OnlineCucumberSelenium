

package com.vytrack.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        glue = "com/vytrack/step_definitions",
        features = "@target/rerun.txt",
        plugin = {
                "html:target/failed-default-report", //separate report from regular text execution = > to not mix them up
                "json:target/failed_report.json"
        }
)
public class FailedRunner {
    //this class will rerun the failed tests
    // NO TAGS needed
    // NO dryRun needed
}