package tests.tasks;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.DropdownsPage;
import tests.BaseTestClass;

public class Task5 extends BaseTestClass {

    private WebDriver driver;

    @BeforeMethod
    public void setUpTest() {
        log.debug("[SETUP TEST] ");
        driver = setUpDriver();
    }

    @Test
    public void test5() {

        SoftAssert softAssert = new SoftAssert();

        DropdownsPage dropdownsPage = new DropdownsPage(driver).open();

        softAssert.assertTrue(dropdownsPage.verifyOptionsFromMultiSelectDropdown(), "Dropdown options are not as expected");
        dropdownsPage.chooseSports();
        softAssert.assertTrue(dropdownsPage.verifyChosenSports(), "Sports are not chosen as expected");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST]");
        tearDown(driver, testResult);
    }
}
