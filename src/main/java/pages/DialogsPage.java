package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.PropertiesUtils;


public class DialogsPage extends BasePageClass {

    private final String DIALOG_PAGE_URL = PropertiesUtils.getBaseUrl() + "kendo-angular-ui/demos/dialogs/preview?theme=default-main";

    private final By dialogLocator = By.xpath("//div[@role='dialog']");
    private final By openDialogButtonLocator = By.xpath("//button//span[text()='Open dialog']");
    private final By dialogTitleLocator = By.xpath("//span[contains(@id,'kendo-dialog-title')]");
    private final By dialogCloseButtonLocator = By.xpath("//button[@title='Close']");
    private final By dialogContentLocator = By.xpath("//div[contains(@id,'kendo-dialog-content')]");
    private final By dialogNoButtonLocator = By.xpath("//button//span[text()='No']");
    private final By dialogYesButtonLocator = By.xpath("//button[@themecolor='primary']");

    private final By windowLocator = By.xpath("//kendo-window[@role='dialog']");
    private final By openWindowButtonLocator = By.xpath("//button//span[text()='Open window']");
    private final By windowTitleLocator = By.xpath("//span[contains(@id,'kendo-window-title')]");
    private final By windowMinimizeButtonLocator = By.xpath("//button[@title='Minimize']");
    private final By windowMaximizeButtonLocator = By.xpath("//button[@title='Maximize']");
    private final By windowCloseButtonLocator = By.xpath("//button[@title='Close']");
    private final By windowContentLocator = By.xpath("//div[@class='k-window-content']//p[text()='Additional info']");

    public DialogsPage(WebDriver driver) {
        super(driver);
    }

    public DialogsPage open() {
        return open(true);
    }

    public DialogsPage open(boolean bVerify) {
        openUrl(DIALOG_PAGE_URL);
        log.info("Open DialogsPage(" + DIALOG_PAGE_URL + ")");
        if(bVerify) {
            verifyDialogsPage();
        }
        return this;
    }

    public void verifyDialogsPage() {
        log.info("verifyDialogsPage()");
        waitForExactUrl(DIALOG_PAGE_URL, Time.TIME_SHORT);
        waitUntilPageIsReady(Time.TIME_MEDIUM);
    }

    public void clickOnOpenDialogButton(){
        log.info("clickOnOpenDialogButton()");
        clickOnElement(openDialogButtonLocator);
    }

    public boolean verifyDialogElements() {
        log.info("verifyDialogElements()");
        boolean dialogTitleDisplayed = verifyElement(dialogTitleLocator);
        boolean dialogCloseButtonDisplayed = verifyElement(dialogCloseButtonLocator);
        boolean dialogContentDisplayed = verifyElement(dialogContentLocator);
        boolean dialogNoButtonDisplayed = verifyElement(dialogNoButtonLocator);
        boolean dialogYesButtonDisplayed = verifyElement(dialogYesButtonLocator);

        if(dialogTitleDisplayed & dialogCloseButtonDisplayed & dialogContentDisplayed & dialogNoButtonDisplayed & dialogYesButtonDisplayed)
            return true;
        return false;
    }

    public boolean verifyYesButtonColor() {
        log.info("verifyYesButtonColor()");
        WebElement element = getWebElement(dialogYesButtonLocator);
        String color = element.getCssValue("background-color");
        String hexacolor = Color.fromString(color).asHex();

        System.out.println("ColorRGBA: " + color);
        System.out.println("ColorHex: " + hexacolor);

        return true;
    }

    public void focusOnXButton() {
        log.info("focusOnXButton()");
        WebElement element = getWebElement(dialogCloseButtonLocator);
        new Actions(driver).moveToElement(element).perform();
    }

    public void pressEnter() {
        log.info("pressEnter()");
        WebElement element = getWebElement(dialogCloseButtonLocator);
        element.sendKeys(Keys.ENTER);
    }

    public boolean verifyDialogIsClosed() {
        log.info("verifyDialogIsClosed()");
        boolean isPresent = !isElementPresent(dialogLocator);
        return isPresent;
    }

    public void clickOnOpenWindowButton(){
        log.info("clickOnOpenWindowButton()");
        clickOnElement(openWindowButtonLocator);
    }

    public boolean verifyWindowElements() {
        log.info("verifyWindowElements()");
        boolean windowTitleDisplayed = verifyElement(windowTitleLocator);
        boolean windowMinimizeButtonDisplayed = verifyElement(windowMinimizeButtonLocator);
        boolean windowMaximizeButtonDisplayed = verifyElement(windowMaximizeButtonLocator);
        boolean windowCloseButtonDisplayed = verifyElement(windowCloseButtonLocator);
        boolean windowContentDisplayed = verifyElement(windowContentLocator);

        if(windowTitleDisplayed & windowMinimizeButtonDisplayed & windowMaximizeButtonDisplayed & windowCloseButtonDisplayed & windowContentDisplayed)
            return true;
        return false;
    }

    public void maximizeWindow() {
        log.info("maximizeWindow()");
        clickOnElement(windowMaximizeButtonLocator);
    }

    public boolean verifyMaximizeButtonIsDisabled() {
        log.info("verifyMaximizeButtonIsDisabled()");
        WebElement element = getWebElement(windowMaximizeButtonLocator);
        boolean isDisabled = ExpectedConditions.attributeContains(element, "style", "display: none").apply(driver);
        return isDisabled;
    }

    public void closeWindow() {
        log.info("closeWindow()");
        clickOnElement(windowCloseButtonLocator);
    }

    public boolean verifyWindowIsClosed() {
        log.info("verifyWindowIsClosed()");
        boolean isClosed = !isElementPresent(windowLocator);
        return isClosed;
    }

}
