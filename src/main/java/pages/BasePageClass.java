package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.LoggerUtils;
import utils.WebDriverUtils;
import java.time.Duration;
import static data.Time.*;

public abstract class BasePageClass extends LoggerUtils {

    protected WebDriver driver;

    public BasePageClass(WebDriver driver) {
        Assert.assertFalse(WebDriverUtils.hasDriverQuit(driver), "Driver instance has quit!");
        this.driver = driver;
    }

    protected void openUrl(String url) {
        log.trace("getPageRrl(" + url + ")");
        driver.get(url);
    }

    protected boolean waitForExactUrl(String url, int timeout) {
        log.trace("waitForExactUrl(" + url + ", " + timeout + ")");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.urlToBe(url));
    }

    protected boolean waitUntilPageIsReady(int timeout) {
        log.trace("waitUntilPageIsReady(" + timeout + ")");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

        return wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    protected boolean isElementDisplayed(WebElement element) {
        log.trace("isElementDisplayed(" + element.toString() + ")");
        return element.isDisplayed();
    }

    protected boolean isElementPresent(By by) {
        log.trace("isElementPresent()");
        try {
            driver.findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    protected void clickOnElement(By by) {
        log.trace("isElementPresent()");
        WebElement element = getWebElement(by, TIME_MEDIUM);
        if(isElementPresent(by)){
            element.click();
        }
    }

    protected boolean verifyElement(By by) {
        log.info("verifyElement(" + by.toString() + ")");
        WebElement element = getWebElement(by);
        return isElementDisplayed(element);
    }

    protected WebElement getWebElement(By locator) {
        log.trace("getWebElement(" + locator +")");
        return driver.findElement(locator);
    }

    protected WebElement getWebElement(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void waitUntilClickable(By locator) {
        log.trace("waitUntilClickable()");
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitUntilVisible(By locator) {
        log.trace("waitUntilVisible()");
        new WebDriverWait(driver, Duration.ofSeconds(TIME_LONG)).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void typeTextToWebElement(By by, String text) {
        log.trace("GetWebElement(" + by.toString() + ", " + text + ")");
        WebElement element = getWebElement(by, TIME_MEDIUM);
        element.sendKeys(text);
    }
}
