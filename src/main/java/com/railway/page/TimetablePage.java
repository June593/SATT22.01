package com.railway.page;

import com.railway.DataObject.TicketInformation;
import com.railway.DataType.header_name.TimetableTableHeader;
import com.railway.common.Constants;
import com.railway.element.Element;
import com.railway.element.TableHelper;
import org.openqa.selenium.By;

public class TimetablePage {

    private final Element elementTable = new Element(By.xpath("//table[@class='MyTable WideTable']"));
    private final TableHelper tableHelper = new TableHelper(elementTable);

    public void clickCheckPriceLink(int rowIndex) {
        tableHelper.clickCell(rowIndex, TimetableTableHeader.CHECK_PRICE.name);
    }

    public void clickBookTicketLink(int rowIndex) {
        tableHelper.clickCell(rowIndex, TimetableTableHeader.BOOK_TICKET.name);
    }

    public int getRandomRowIndex() {
        return tableHelper.getRandomRowIndex();
    }

    public TicketInformation getTicketInfo(int rowIndex) {
        elementTable.waitForVisibilityOfElementLocated(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        String departStation = tableHelper.getCellText(rowIndex, TimetableTableHeader.DEPART_STATION.name);
        String arriveStation = tableHelper.getCellText(rowIndex, TimetableTableHeader.ARRIVE_STATION.name);
        return TicketInformation.builder()
                .departStation(departStation)
                .arriveStation(arriveStation)
                .build();
    }
}
