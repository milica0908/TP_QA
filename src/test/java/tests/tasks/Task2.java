package tests.tasks;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.DialogsPage;
import tests.BaseTestClass;

public class Task2 extends BaseTestClass {

    private WebDriver driver;

    @BeforeMethod
    public void setUpTest() {
        log.debug("[SETUP TEST] ");
        driver = setUpDriver();
    }

    @Test
    public void test2() {

        SoftAssert softAssert = new SoftAssert();

        DialogsPage dialogsPage = new DialogsPage(driver).open();

        dialogsPage.clickOnOpenWindowButton();
        softAssert.assertTrue(dialogsPage.verifyWindowElements(), "Some window elements are changed and/or missing");
        dialogsPage.maximizeWindow();
        softAssert.assertTrue(dialogsPage.verifyMaximizeButtonIsDisabled(), "Maximize button is not disabled");
        dialogsPage.closeWindow();
        softAssert.assertTrue(dialogsPage.verifyWindowIsClosed(), "Window is not closed");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST]");
        tearDown(driver, testResult);
    }
}
