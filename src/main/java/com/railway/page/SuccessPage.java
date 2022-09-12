package com.railway.page;

import com.railway.DataObject.TicketInformation;
import com.railway.DataType.header_name.BookTicketTableHeader;
import com.railway.common.Constants;
import com.railway.common.Log;
import com.railway.driver.DriverUtils;
import com.railway.element.Element;
import com.railway.element.TableHelper;
import org.openqa.selenium.By;

public class SuccessPage extends BasePage {

    private final Element elementTable = new Element(By.xpath("//table[@class='MyTable WideTable']"));
    private final TableHelper tableHelper = new TableHelper(elementTable);

    private final int SELECTED_ROW_INDEX = 2;

    public String getDepartStation() {
        elementTable.waitForVisibilityOfElementLocated(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        return tableHelper.getCellText(SELECTED_ROW_INDEX, BookTicketTableHeader.DEPART_STATION.name);
    }

    public String getArriveStation() {
        return tableHelper.getCellText(SELECTED_ROW_INDEX, BookTicketTableHeader.ARRIVE_STATION.name);
    }

    public String getSeatType() {
        return tableHelper.getCellText(SELECTED_ROW_INDEX, BookTicketTableHeader.SEAT_TYPE.name);
    }

    public String getTotalPrice() {
        return tableHelper.getCellText(SELECTED_ROW_INDEX, BookTicketTableHeader.TOTAL_PRICE.name);
    }

    public String getBookDate() {
        return tableHelper.getCellText(SELECTED_ROW_INDEX, BookTicketTableHeader.BOOK_DATE.name);
    }

    public String getDepartDate() {
        return tableHelper.getCellText(SELECTED_ROW_INDEX, BookTicketTableHeader.DEPART_DATE.name);
    }

    public String getAmount() {
        return tableHelper.getCellText(SELECTED_ROW_INDEX, BookTicketTableHeader.AMOUNT.name);
    }

    public String getExpiredDate() {
        return tableHelper.getCellText(SELECTED_ROW_INDEX, BookTicketTableHeader.EXPIRED_DATE.name);
    }

    public String getIdTicket() {
        String url = DriverUtils.getCurrentUrl();
        if (url.contains("id=")) {
            String[] arrUrl = url.split("=");
            return arrUrl[1];
        }
        return null;
    }

    public boolean checkBookedTicketInformationWithSelectedInformationBookTicket(TicketInformation ticketInformation) {
        if (!getDepartStation().equals(ticketInformation.getDepartStation())) {
            Log.info("Depart Station is not displayed as expected");
            Log.info("Actual: " + getDepartStation());
            Log.info("Expected: " + ticketInformation.getDepartStation());
            return false;
        }
        if (!getArriveStation().equals(ticketInformation.getArriveStation())) {
            Log.info("Depart Station is not displayed as expected");
            Log.info("Actual: " + getArriveStation());
            Log.info("Expected: " + ticketInformation.getArriveStation());
            return false;
        }
        if (!getSeatType().equals(ticketInformation.getSeatType())) {
            Log.info("Depart Station is not displayed as expected");
            Log.info("Actual: " + getSeatType());
            Log.info("Expected: " + ticketInformation.getSeatType());
            return false;
        }
        if (!getAmount().equals(Integer.toString(ticketInformation.getTicketAmount()))) {
            Log.info("Depart Station is not displayed as expected");
            Log.info("Actual: " + getAmount());
            Log.info("Expected: " + ticketInformation.getTicketAmount());
            return false;
        }
        return true;
    }
}
