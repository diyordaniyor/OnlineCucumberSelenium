package com.vytrack.step_definitions;

import com.vytrack.utilities.Driver;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    //we do not use Extends Report like in testNg in Cucumber
    // hooks are like before and after method in testNG

    @Before  (order = 2)//these are from cucumber
    public void setup(){
        System.out.println("Test setup");
        //Driver.getDriver().manage().window().maximize();
        Driver.getDriver();

    }

    @Before (value = "@driver", order = 1) //this hook will be executed ONLY for scenarios that have tag @driver
    public void specialSetup(){
        System.out.println("Setup for driver only");
    }


    @After ("@driver")
    public void specialTearDown(){

        System.out.println("Test clean up for driver only");

        Driver.closeDriver();
    }


    @After
    public void tearDown(Scenario scenario){
        //Scenario class that is helping us to attach screenshot to the report
        //how to check if scenario failed
    if (scenario.isFailed()) {
        //TakeScreenSHot interface comes from Selenium WebDriver
        //output is in Bytes
        //method that attaches image to the report requires array of Bytes (.embed () method)
        TakesScreenshot takesScreenshot = (TakesScreenshot) Driver.getDriver();
        byte[] image = takesScreenshot.getScreenshotAs(OutputType.BYTES);
        //attach screenshot to the report
        scenario.embed(image, "image/png", scenario.getName());
    }
        System.out.println("Test clean up");

        Driver.closeDriver();
    }

    //Background => common steps you can add in here => common steps for all scenarios
    //one feature file => one background ..if we have dif. feature file then we need to create dif. backgrounds

    //order
    //before hook => background => scenario => after hook

    //whatever is in background is more like Pre-condition
    //hooks => are general ..setting up , maximize window.. etc.

    //usually background is short , but there is no Limit! you can add as well


    //===========TestNG======
    //Parameter in xml and the pass to test scenario
    //in before or after method in abstract test class
    //why exactly we needed that ? read

}