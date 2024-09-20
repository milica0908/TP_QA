package tests.tasks;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DialogsPage;
import tests.BaseTestClass;
import org.testng.asserts.SoftAssert;

public class Task1 extends BaseTestClass {

    private WebDriver driver;

    @BeforeMethod
    public void setUpTest() {
        log.debug("[SETUP TEST] ");
        driver = setUpDriver();
    }

    @Test
    public void test1() {

        SoftAssert softAssert = new SoftAssert();

        DialogsPage dialogsPage = new DialogsPage(driver).open();

        dialogsPage.clickOnOpenDialogButton();
        softAssert.assertTrue(dialogsPage.verifyDialogElements(), "Some dialog elements are changed and/or missing");
        dialogsPage.verifyYesButtonColor();
        dialogsPage.focusOnXButton();
        dialogsPage.pressEnter();
        softAssert.assertTrue(dialogsPage.verifyDialogIsClosed(), "Dialog is not closed");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST]");
        tearDown(driver, testResult);
    }


}
