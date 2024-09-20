package tests.tasks;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.GridPage;
import tests.BaseTestClass;

import java.io.IOException;

public class Task3 extends BaseTestClass {

    private WebDriver driver;

    @BeforeMethod
    public void setUpTest() {
        log.debug("[SETUP TEST]");
        driver = setUpDriver();
    }

    @Test
    public void test3() throws IOException {

        GridPage gridPage = new GridPage(driver).open();

        gridPage.filterUSEmployees();
        gridPage.printUSEmployeeInfo();
        gridPage.exportOnlineUSEmployees();
        Assert.assertTrue(gridPage.verifyExportedTable(), "Exported Employee table is not correct");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult testResult) {
        log.debug("[END TEST]");
        tearDown(driver, testResult);
    }
}
