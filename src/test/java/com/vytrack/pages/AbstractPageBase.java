
package com.vytrack.pages;


import com.vytrack.utilities.BrowserUtilities;
import com.vytrack.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class will be extended by page classes
 * Ant common webelements/locators can be stored here
 * Since navigation menu doesn't belong to particular page
 * We cannot really create a dedicated page class to store
 * elements from that menu
 */

public abstract class AbstractPageBase {

    protected WebDriver driver = Driver.getDriver();
    //driver is another reference for our same WebDriver
    protected WebDriverWait wait = new WebDriverWait(driver, 15);
    //we added it inhere so subclasses can reuse the same Wait object

    @FindBy (css = "#user-menu > a")
    protected WebElement currentUser;

    @FindBy(css = "[class='btn-group pull-right'] > button")
    protected WebElement saveAndClose;

    public AbstractPageBase(){
        PageFactory.initElements(driver, this );
    }

    //We come up with method that will wrap up locator not to have duplication
    // by using webElements directly in test class

    public String getCurrentUserName(){
        BrowserUtilities.waitForPageToLoad(10);
        wait.until(ExpectedConditions.visibilityOf(currentUser));
        return currentUser.getText().trim();
    }
    public void clickOnSaveAndClose() {
        BrowserUtilities.wait(3);
        wait.until(ExpectedConditions.elementToBeClickable(saveAndClose)).click();
        waitForLoaderMask();
    }

    /**
     * Method for VYtruck navigation. Provide tab name and module name to navigate
     * @param tabName like Dashboards, Fleet, or Customers
     * @param moduleName like Vehicles, Vehicles Odometer and Vehicles Costs
     *                   locator in here is flexible .it depends on what user wants to open
     */
    public void navigateTo(String tabName, String moduleName) {
        String tabNameXpath = "//span[@class='title title-level-1' and contains(text(),'"+tabName+"')]";
        String moduleXpath = "//span[@class='title title-level-2' and text()='" + moduleName + "']";

        WebElement tabElement = driver.findElement(By.xpath(tabNameXpath));
        WebElement moduleElement = driver.findElement(By.xpath(moduleXpath));

        Actions actions = new Actions(driver);
        BrowserUtilities.wait(4);
        actions.moveToElement(tabElement).
                pause(2000).
                click(moduleElement).
                build().perform();

        //increase this wait rime if test is still failing
        BrowserUtilities.wait(4);
        waitForLoaderMask();
    }

    /**
     * this method can be used to wait until that terrible loader mask (spinning wheel) will be gone
     * if loader mask is present, website is loading some data and you cannot perform any operations
     */

    public void waitForLoaderMask() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[class*='loader-mask']")));
    }

}