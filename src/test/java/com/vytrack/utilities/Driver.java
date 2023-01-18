package com.vytrack.utilities;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Driver {

    //you can have inner classes.. but public class has to be one and it has to match with the name you have inside your package
    //same for everyone
    //private static WebDriver driver;
    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();
    //it is still considered SINGELTON! but when it is needed it will be duplicated / if you work with multiple threads then it will allow it

    //so no one can create object of Driver class
    //everyone should call static getter method instead
    private Driver() {

        //IMPORTANT! webdriver  has to configured to RUN in parallel ! - 1st impediment!!!
        //that's why we wrap it into thread local and
        //test execution happens withinone thread - main -
        //if we wantto run 2 test at the same time we need to create another thread/ to run Simultaniously
        //static webdriver is sharable! if we have two tasks but only one worker can be at one place at the same time
        //2-impediment : methods have to me synchronized = to prevent the concurrent usage . that do not allow 2 workes to touch it at the same time
        //so for example the variable is not overriden by another thread

    }

    /**synchronized makes method thread safe. It ensures that only 1 thread can use it at the time.
     *
     * Thread safety reduces performance but it makes everything safe.
     *
     * @return
     */
    /*public synchronized static WebDriver getDriver(String browser) {
        //if webdriver object doesn't exist
        //create it
        if (driverPool.get() == null) {
            //specify browser type in configuration.properties file
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().version("108").setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;
                case "chromeheadless":
                    //to run chrome without interface (headless mode)
                    WebDriverManager.chromedriver().version("79").setup();
                    ChromeOptions options = new ChromeOptions();
                    options.setHeadless(true);
                    driverPool.set(new ChromeDriver(options));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
                    break;
                default:
                    throw new RuntimeException("Wrong browser name!");
            }
        }
        return driverPool.get();
    }
    public static void closeDriver() {
        if (driverPool != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }

     */

    /**synchronized makes method thread safe. It ensures that only 1 thread can use it at the time.
     *
     * Thread safety reduces performance but it makes everything safe.
     *
     * @return
     */
    public synchronized static WebDriver getDriver() {
        //if webdriver object doesn't exist
        //create it
        if (driverPool.get() == null) {
            //specify browser type in configuration.properties file
            String browser = COnfigurationReader.getProperty("browser").toLowerCase();
            //environment variable : we can use to specify browser type (in our case)
            //-Dbrowser=firefox => if someone specifies browser like this / then IGNORE below switch command
            if (System.getProperty("browser")!= null) {
                browser = System.getProperty("browser");
            }
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    driverPool.set(new ChromeDriver(chromeOptions));
                    break;
                case "chromeheadless":
                    //to run chrome without interface (headless mode)
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.setHeadless(true);
                    driverPool.set(new ChromeDriver(options));
                    break;

                //for grid that has docker installed is this :
                case "chrome-remote-grid":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions1 = new ChromeOptions();
                    try {
                        URL url = new URL ("http://52.86.64.216:4444/wd/hub");
                        driverPool.set(new RemoteWebDriver(url, chromeOptions1));

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                case "chrome-remote":
                    try {
                        //we create object of URL and specify
                        //selenium grid hub as a parameter
                        //make sure it ends with /wd/hub
                        URL url = new URL ("http://107.21.82.125:4444/wd/hub"); //this is vasyls IP 34.236.36.95:4444 (this should be the powerful one) and another 3.90.175.72 and mine is http://107.21.82.125:4444/
                        //desiredCapabilities used to specify what kind of node
                        //is required for testing
                        //such as: OS type, browser, version, etc...
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName(BrowserType.CHROME);
                        desiredCapabilities.setPlatform(Platform.ANY);

                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
                    break;
                default:
                    throw new RuntimeException("Wrong browser name!");
            }
        }
        return driverPool.get();
    }

   public static void closeDriver() {

     if (driverPool!= null) {

            driverPool.get().quit();

           driverPool.remove();
        }
    }
}