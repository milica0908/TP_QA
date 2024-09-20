package pages;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.PropertiesUtils;

import java.util.List;

public class DropdownsPage extends BasePageClass {

    private final String DROPDOWN_PAGE_URL = PropertiesUtils.getBaseUrl() + "kendo-angular-ui/demos/dropdowns/overview?theme=default-main";

    private final By autocompleteInputFieldLocator = By.xpath("//input[@placeholder='Your favorite sport']");
    private final By clearAutocompleteInputFieldLocator = By.xpath("//input[@placeholder='Your favorite sport']/../span");
    private final By multiSelectInputFieldLocator = By.xpath("//kendo-multiselect[contains(@class,'k-multiselect')]//input");
    private final By multiSelectDropdownOptionsLocator = By.xpath("//span[@class='k-list-item-text']");
    private final By multiSelectDropdownFootballLocator = By.xpath("//li//span[text()='Football']");
    private final By multiSelectDropdownTennisLocator = By.xpath("//li//span[text()='Tennis']");
    private final By multiSelectClearButtonLocator = By.xpath("//kendo-multiselect//span[@title='clear']");
    private final By multiSelectSelectedFootballLocator = By.xpath("//kendo-multiselect//span[text()='Football']");
    private final By multiSelectSelectedTennisLocator = By.xpath("//kendo-multiselect//span[text()='Tennis']");

    public DropdownsPage(WebDriver driver) {
        super(driver);
    }

    public DropdownsPage open() {
        return open(true);
    }

    public DropdownsPage open(boolean bVerify) {
        openUrl(DROPDOWN_PAGE_URL);
        log.info("Open DropdownsPage(" + DROPDOWN_PAGE_URL + ")");
        if(bVerify) {
            verifyDropdownsPage();
        }
        return this;
    }

    public void verifyDropdownsPage() {
        log.info("verifyDropdownsPage()");
        waitForExactUrl(DROPDOWN_PAGE_URL, Time.TIME_SHORT);
        waitUntilPageIsReady(Time.TIME_MEDIUM);
    }

    public void chooseAutocompleteSports() {
        log.info("chooseAutocompleteSports()");
        String sport = "Volleyball";

        typeTextToWebElement(autocompleteInputFieldLocator, sport);
        if(isElementPresent(By.xpath("//li//span[text()='" + sport + "']")))
            clickOnElement(By.xpath("//li//span[text()='" + sport + "']"));
    }

    public boolean verifyChosenSport() {
        log.info("verifyChosenSport()");
        boolean isSportChosen = isElementPresent(clearAutocompleteInputFieldLocator);
        return isSportChosen;
    }

    public void clearChosenSport() {
        log.info("clearChosenSport()");
        clickOnElement(clearAutocompleteInputFieldLocator);
    }

    public boolean verifyOptionsFromMultiSelectDropdown() {
        log.info("verifyOptionsFromMultiSelectDropdown()");
        String[] expectedSports = {"Baseball", "Basketball", "Cricket", "Field Hockey", "Football", "Table Tennis", "Tennis", "Volleyball"};
        boolean verified = false;

        waitUntilClickable(multiSelectInputFieldLocator);
        clickOnElement(multiSelectInputFieldLocator);

        waitUntilVisible(multiSelectDropdownOptionsLocator);
        List<WebElement> dropdownOptions = driver.findElements(multiSelectDropdownOptionsLocator);
        for(WebElement option : dropdownOptions) {
            verified = false;
            for(int i = 0; i < expectedSports.length; i++) {
                if(option.getText().equals(expectedSports[i])){
                    verified = true;
                    break;
                }
            }
            if(verified == false)
                return verified;
        }
        return verified;
    }

    public void chooseSports() {
        log.info("chooseSports()");
        //clear any previously chosen options
        if(isElementPresent(multiSelectClearButtonLocator))
            clickOnElement(multiSelectClearButtonLocator);

        clickOnElement(multiSelectInputFieldLocator);
        clickOnElement(multiSelectDropdownFootballLocator);

        clickOnElement(multiSelectInputFieldLocator);
        clickOnElement(multiSelectDropdownTennisLocator);
    }

    public boolean verifyChosenSports() {
        log.info("verifyChosenSports()");
        boolean sportsChosen = false;

        if(isElementPresent(multiSelectSelectedFootballLocator) && isElementPresent(multiSelectSelectedTennisLocator))
            sportsChosen = true;

        return sportsChosen;
    }

}
