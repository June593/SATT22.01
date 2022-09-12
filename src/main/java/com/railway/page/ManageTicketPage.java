package com.railway.page;

import com.railway.DataObject.Filter;
import com.railway.DataType.header_name.ManageTicketTableHeader;
import com.railway.common.Constants;
import com.railway.common.Helper;
import com.railway.common.Log;
import com.railway.driver.DriverUtils;
import com.railway.element.Element;
import com.railway.element.TableHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class ManageTicketPage extends BasePage {

    private final Element elementTable = new Element(By.xpath("//table[@class='MyTable']"));
    private final By byLocatorRows = By.xpath("./tbody/tr[td]");
    private final String locatorCell = ".//tr[td][%s]//td[%s]";
    private final Element expiredXpathElement = new Element(elementTable, By.xpath("//tr[td]//td[text()='Expired']"));
    private final TableHelper tableHelper = new TableHelper(elementTable);
    private final Element noteMessageElement = new Element(By.xpath("//div[@class='message']/li[1]"));

    private final Element selectDepartStation = new Element(By.name("FilterDpStation"));
    private final Element selectArriveStation = new Element(By.xpath("//select[@name='FilterArStation']"));
    private final Element selectStatus = new Element(By.xpath("//select[@name='FilterStatus']"));
    private final Element textDepartDate = new Element(By.xpath("//input[@name='FilterDpDate']"));
    private final Element buttonApplyFilter = new Element(By.xpath("//input[@type='submit']"));
    private final Element labelFilterError = new Element(By.xpath("//span[contains(text()),'No result found!'))]"));
    private final Element labelMiddleError = new Element(By.xpath("//div[@class='error message']"));

    private final int numberOfTicketsCanBeBooked = 10;

    public ManageTicketPage() {
        tableHelper.setTableRowElement(new Element(tableHelper.getTableElement(), byLocatorRows));
        tableHelper.setCellXpathLocator(locatorCell);
    }

    public int getTotalTicket() {
        elementTable.waitForVisibilityOfElementLocated(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        int result = 0;
        List<WebElement> amountExpiredElement = expiredXpathElement.getElements();
        List<WebElement> amountElement = tableHelper.getRowElement();
        int ticketRowIsNotExpired = amountElement.size() - amountExpiredElement.size();
        if (ticketRowIsNotExpired == 0) {
            return 0;
        }
        for (int i = 1; i < ticketRowIsNotExpired + 1; i++) {
            String amountText = tableHelper.getCellText(i, ManageTicketTableHeader.AMOUNT.name);
            result += Integer.parseInt(amountText);
        }
        return result;
    }

    public void cancelTicket(int rowIndex) {
        tableHelper.clickCell(rowIndex, ManageTicketTableHeader.OPERATION.name);
    }

    public int getTicketRow(String idTicket) {
        elementTable.waitForVisibilityOfElementLocated(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        tableHelper.setCellXpathLocator(locatorCell + "//input");
        List<WebElement> RowElementsAfterCancel = tableHelper.getRowElement();
        int operationColumnIndex = tableHelper.getHeaderIndex(ManageTicketTableHeader.OPERATION.name);
        for (int i = 1; i <= RowElementsAfterCancel.size(); i++) {
            String temp = tableHelper.getCellElement(i, operationColumnIndex).getAttribute("onclick");
            System.out.println(temp);
            if (temp.contains(idTicket)) {
                tableHelper.setCellXpathLocator(locatorCell);
                return i;
            }
        }
        throw new RuntimeException("Can't not find Ticket ID: " + idTicket);
    }

    public boolean isExistedTicket(String idTicket) {
        elementTable.waitForVisibilityOfElementLocated(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        tableHelper.setCellXpathLocator(locatorCell + "//input");
        int operationColumnIndex = tableHelper.getHeaderIndex(ManageTicketTableHeader.OPERATION.name);
        List<WebElement> RowElements = tableHelper.getRowElement();
        for (int i = 1; i <= RowElements.size(); i++) {
            String temp = tableHelper.getCellElement(i, operationColumnIndex).getAttribute("onclick");
            System.out.println(temp);
            if (temp.contains(idTicket)) {
                return true;
            }
        }
        tableHelper.setCellXpathLocator(locatorCell);
        return false;
    }

    public boolean checkMessageNoteDisplayIsCorrect(int numberOfTickets) {
        String noteMessageActual = noteMessageElement.getText();
        int unbookedTickets = numberOfTicketsCanBeBooked - numberOfTickets;
        String noteMessageExpect = unbookedTickets == 0 ? "You currently book 10 tickets, you can book no more."
                : unbookedTickets == numberOfTicketsCanBeBooked - 1
                ? String.format("You currently book %s ticket, you can book %s more.", numberOfTickets, unbookedTickets)
                : String.format("You currently book %s tickets, you can book %s more.", numberOfTickets, unbookedTickets);
        System.out.println(noteMessageActual);
        System.out.println(noteMessageExpect);
        return noteMessageActual.equals(noteMessageExpect);
    }

    /**
     * @param headerName                    : column name that the user wants to filter
     * @param filterInput                   : information input that the user wants to filter
     * @param numberOfExpectedTicketsResult : number of expected tickets result
     * @return true/false
     * <p>
     * If header name is Depart Date the input is "", then no need to check, immediately return true
     * If header name is not Depart Date, Date the input is "Ignore", then no need to check, immediately return true
     */

    public boolean checkResultDisplaysCorrectlyWithSelectedFilter(String headerName, String filterInput
            , int numberOfExpectedTicketsResult) {
        if (headerName.equals(ManageTicketTableHeader.DEPART_DATE.name)
                && filterInput.equals(""))
            return true;
        if (!headerName.equals(ManageTicketTableHeader.DEPART_DATE.name)
                && filterInput.equals("Ignore")) return true;
        List<WebElement> elements = tableHelper.getRowElement();
        if (elements.size() != numberOfExpectedTicketsResult) return false;
        for (int i = 1; i <= elements.size(); i++) {
            if (!filterInput.equals(tableHelper.getCellText(i, headerName))) {
                return false;
            }
        }
        Log.info(String.format("Filter result with %s Depart Station is %s ticket(s)",
                tableHelper.getCellText(1, headerName), elements.size()));
        return true;
    }

    public boolean checkOptionsFilteredBySelectedCollum(String headerName, String filterInput) {
        List<WebElement> elements = tableHelper.getRowElement();
        for (int i = 1; i <= elements.size(); i++) {
            if (!filterInput.equals(tableHelper.getCellText(i, headerName))) {
                return false;
            }
        }
        return true;
    }

    public String getCellText(int indexRow, String headerName) {
        return tableHelper.getCellText(indexRow, headerName);
    }

    public void clickApplyFilterButton() {
        buttonApplyFilter.click();
    }

    public int getNumberOfValuesInManageTable(String headerName, String value) {
        int count = 0;
        List<WebElement> elements = tableHelper.getRowElement();
        for (int i = 1; i <= elements.size(); i++) {
            if (tableHelper.getCellText(i, headerName).equals(value)) {
                count++;
            }
        }
        return count;
    }

    public void selectOptionsOfFilter(Filter filter) {
        selectDepartStation.select(filter.getDepartStation());
        selectArriveStation.select(filter.getArriveStation());
        textDepartDate.clear();
        textDepartDate.enter(filter.getDepartDate());
        selectStatus.select(filter.getStatus());
    }

    public int getTotalNumberOfRow() {
        return tableHelper.getRowElement().size();
    }

    public int getRandomRowIndexOfManageTicketTable() {
        return Helper.getRandomIndex(1, getTotalNumberOfRow());
    }


    public void selectDepartStation(String value) {
        selectDepartStation.select(value);
    }

    public void selectFilterStatus(String value) {
        selectStatus.select(value);
    }

    public boolean selectFilterOptionsAndCheckResultOfFilter(Filter... filter) {
        int filterNumber = 0;
        for (Filter ft : Arrays.asList(filter)) {
            filterNumber = filterNumber + 1;
            Log.info(String.format("Step. Select any options from the Filter%s table", filterNumber));
            selectOptionsOfFilter(ft);
            Log.info(String.format("Step. Click on Apply Filter with Filter%s Data", filterNumber));
            clickApplyFilterButton();
            Log.info("VP. Only ticket that matches the condition should be displayed");
            Assert.assertTrue(checkOptionsFilteredBySelectedCollum(
                    "Depart Station", ft.getDepartStation()), "Depart station filter is wrong");
            Assert.assertTrue(checkOptionsFilteredBySelectedCollum(
                    "Arrive Station", ft.getArriveStation()), "Arrive Station filter is wrong");
            Assert.assertTrue(checkOptionsFilteredBySelectedCollum(
                    "Depart Date", ft.getDepartDate()), "Depart Date filter is wrong");
            Assert.assertTrue(checkOptionsFilteredBySelectedCollum(
                    "Status", ft.getStatus()), "Status filter is wrong");
        }
        return true;
    }
}
