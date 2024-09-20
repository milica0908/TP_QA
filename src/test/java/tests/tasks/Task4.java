package tests.tasks;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DropdownsPage;
import tests.BaseTestClass;

public class Task4 extends BaseTestClass {

    private WebDriver driver;

    @BeforeMethod
    public void setUpTest() {
        log.debug("[SETUP TEST] ");
        driver = setUpDriver();
    }

    @Test
    public void test4() {

        DropdownsPage dropdownsPage = new DropdownsPage(driver).open();

        dropdownsPage.chooseAutocompleteSports();
        Assert.assertTrue(dropdownsPage.verifyChosenSport(), "Sport is not chosen");
        dropdownsPage.clearChosenSport();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST]");
        tearDown(driver, testResult);
    }
}
