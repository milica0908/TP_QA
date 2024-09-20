package pages;

import data.Time;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.PropertiesUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class GridPage extends BasePageClass {

    private final String GRID_PAGE_URL = PropertiesUtils.getBaseUrl() + "kendo-angular-ui/demos/grid/filter-all-columns?theme=default-main";

    private final By countryColumnMenuLocator = By.xpath("//a[@title='Country Column Menu']");
    private final By contactNameColumnMenuLocator = By.xpath("//a[@title='Contact Name Column Menu']");
    private final By statusColumnMenuLocator = By.xpath("//a[@title='Status Column Menu']");
    private final By columnMenuFilterLocator = By.xpath("//div[text()=' Filter ']");
    private final By columnColumnsFilterLocator = By.xpath("//kendo-grid-columnmenu-item[@icon='columns']");
    private final By filterOperatorLocator = By.xpath("//kendo-dropdownlist[@aria-label='Country Filter Operators']//button[@aria-label='Select']");
    private final By filterOptionIsEqualToLocator = By.xpath("//span[text()='Is equal to']");
    private final By filterInputTextLocator = By.xpath("//input[@aria-label='Country Filter']");
    private final By filterButtonLocator = By.xpath("//button[@type='submit']");
    private final By employeeRowLocator = By.xpath("//tr[@role='row' and contains(@class,'k-master-row')]");
    private final By columnsFilterCountryLocator = By.xpath("//span[text()='Country']/../input");
    private final By columnsFilterStatusLocator = By.xpath("//span[text()='Status']/../input");
    private final By columnsFilterRatingLocator = By.xpath("//span[text()='Rating']/../input");
    private final By columnsFilterEngagementLocator = By.xpath("//span[text()='Engagement']/../input");
    private final By columnsFilterBudgetLocator = By.xpath("//span[text()='Budget']/../input");
    private final By applyButtonLocator = By.xpath("//button[text()='Apply']");
    private final By statusFilterIsTrueLocator = By.xpath("//label[text()='Is True']/../input");
    private final By exportToExcelButtonLocator = By.xpath("//button[@kendogridexcelcommand]");

    public GridPage(WebDriver driver) {
        super(driver);
    }

    public GridPage open() {
        return open(true);
    }

    public GridPage open(boolean bVerify) {
        openUrl(GRID_PAGE_URL);
        log.info("Open GridPage(" + GRID_PAGE_URL + ")");
        if(bVerify) {
            verifyGridPage();
        }
        return this;
    }

    public void verifyGridPage() {
        log.info("verifyGridPage()");
        waitForExactUrl(GRID_PAGE_URL, Time.TIME_SHORT);
        waitUntilPageIsReady(Time.TIME_MEDIUM);
    }

    public void openFilters() {
        log.info("openFilters()");
        if(isElementPresent(countryColumnMenuLocator))
            clickOnElement(countryColumnMenuLocator);
        else clickOnElement(contactNameColumnMenuLocator);
    }

    public void filterUSEmployees() {
        log.info("filterUSEmployees()");
        openFilters();
        waitUntilClickable(columnMenuFilterLocator);
        clickOnElement(columnMenuFilterLocator);
        clickOnElement(filterOperatorLocator);
        clickOnElement(filterOptionIsEqualToLocator);
        typeTextToWebElement(filterInputTextLocator, "US");
        clickOnElement(filterButtonLocator);
    }

    public void switchColumns() {
        log.info("filterColumns()");
        openFilters();
        waitUntilClickable(columnColumnsFilterLocator);
        clickOnElement(columnColumnsFilterLocator);
        clickOnElement(columnsFilterCountryLocator);
        clickOnElement(columnsFilterStatusLocator);
        clickOnElement(columnsFilterRatingLocator);
        clickOnElement(columnsFilterEngagementLocator);
        clickOnElement(columnsFilterBudgetLocator);
        clickOnElement(applyButtonLocator);
    }

    public void printUSEmployeeInfo() {
        log.info("printUSEmployeeInfo()");
        switchColumns();

        List<WebElement> employees = driver.findElements(employeeRowLocator);
        for(WebElement employee : employees) {
            System.out.println("Employee info: " + employee.getText() + "\n");
        }
    }

    public void filterOnlineEmployees() {
        log.info("filterOnlineEmployees()");
        waitUntilClickable(statusColumnMenuLocator);
        clickOnElement(statusColumnMenuLocator);
        clickOnElement(columnMenuFilterLocator);
        clickOnElement(statusFilterIsTrueLocator);
        clickOnElement(filterButtonLocator);
    }

    public void exportOnlineUSEmployees() {
        log.info("exportOnlineUSEmployees()");
        switchColumns();
        filterOnlineEmployees();
        clickOnElement(exportToExcelButtonLocator);
    }

    public String getExcelData(String sheetName, int rowNumber, int cellNumber) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\stojk\\Downloads\\Employees.xlsx"); //Need to input your download path, where your files will be downloaded!
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowNumber);
        Cell cell = row.getCell(cellNumber);
        String cellText = cell.toString();

        workbook.close();
        fileInputStream.close();

        return cellText;
    }

    public boolean verifyExportedTable() throws IOException {
        log.info("verifyExportedTable()");
        String country = "US";
        String status = "TRUE";

        for(int i = 2; i<14; i++) {
            String cell = getExcelData("Sheet1", i, 2);
            if(!cell.equals(country))
                return false;
        }

        for(int i = 2; i<14; i++) {
            String cell = getExcelData("Sheet1", i, 3);
            if(!cell.equals(status))
                return false;
        }
        return true;
    }
}
