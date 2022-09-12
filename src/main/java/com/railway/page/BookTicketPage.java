package com.railway.page;

import com.railway.DataObject.TicketInformation;
import com.railway.DataType.TabName;
import com.railway.common.Constants;
import com.railway.common.DateHelper;
import com.railway.common.Helper;
import com.railway.element.Element;
import org.openqa.selenium.By;

public class BookTicketPage extends BasePage {

    private final Element selectDepartDate = new Element(By.name("Date"));
    private final Element selectDepartStation = new Element(By.name("DepartStation"));
    private final Element selectArriveStation = new Element(By.name("ArriveStation"));
    private final Element selectSeatType = new Element(By.name("SeatType"));
    private final Element selectTicketAmount = new Element(By.name("TicketAmount"));
    private final Element buttonBookTicket = new Element(By.xpath("//input[@type='submit']"));
    private final Element labelGeneralErrorMessage = new Element(By.xpath("//p[@class='message error']"));
    private final Element labelTicketAmountErrorMessage = new Element(By.xpath("//label[@class='validation-error']"));

    private final int MAX_NUMBER_OF_TICKETS = 10;
    private final int NUMBER_OF_DAYS_UNTIL_EXPIRED = 3;

    public String getDepartStation() {
        selectDepartStation.waitForVisibilityOfElementLocated(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        return selectDepartStation.getSelected();
    }

    public String getArriveStation() {
        return selectArriveStation.getSelected();
    }

    public String getSeatType() {
        return selectSeatType.getSelected();
    }

    public int getTicketAmount() {
        return Integer.parseInt(selectTicketAmount.getSelected());
    }

    public String getDepartDate() {
        return selectDepartDate.getSelected();
    }

    public void clickBookTicketButton() {
        buttonBookTicket.scrollToView();
        buttonBookTicket.click();
    }

    public void selectRandomSeatType() {
        int numberOfSeatTypes = selectSeatType.getOptionsSize();
        int randomIndex = Helper.getRandomIndex(0, numberOfSeatTypes - 1);
        selectSeatType.select(randomIndex);
    }

    public void selectRandomDepartDate() {
        selectDepartDate.waitForVisibilityOfElementLocated(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        int numberOfDepartDates = selectDepartDate.getOptionsSize();
        int randomIndex = Helper.getRandomIndex(0, numberOfDepartDates - 1);
        selectDepartDate.select(randomIndex);
    }

    public void selectRandomDepartAndArriveStation() {
        int numberOfDepartStations = selectDepartStation.getOptionsSize();
        int randomDepartFromIndex = Helper.getRandomIndex(0, numberOfDepartStations - 1);
        selectDepartStation.select(randomDepartFromIndex);
        if (randomDepartFromIndex != 0) {
            selectArriveStation.waitForElementIsChanged(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        }
        int numberOfArriveStations = selectArriveStation.getOptionsSize();
        int randomArriveAtIndex = Helper.getRandomIndex(0, numberOfArriveStations - 1);
        selectArriveStation.select(randomArriveAtIndex);
    }

    public void selectRandomNumberOfTickets() {
        int randomIndex = Helper.getRandomIndex(0, MAX_NUMBER_OF_TICKETS - 1);
        selectTicketAmount.select(randomIndex);
    }

    public void selectDepartDate(String text) {
        selectDepartDate.waitForVisibilityOfElementLocated(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        selectDepartDate.select(text);
    }

    public void selectDepartStation(String text) {
        selectDepartStation.select(text);
    }

    public void selectArriveStation(String text) {
        selectArriveStation.select(text);
    }

    public void selectSeatType(String text) {
        selectSeatType.select(text);
    }

    public void selectTicketAmount(int numberTickets) {
        selectTicketAmount.select(Integer.toString(numberTickets));
    }

    public void setTicketInfo(TicketInformation ticketInformation, int numberTicket) {
        selectDepartDate.waitForVisibilityOfElementLocated(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        selectDepartDate(ticketInformation.getDepartDate());
        selectDepartStation(ticketInformation.getDepartStation());
        selectArriveStation(ticketInformation.getArriveStation());
        selectSeatType(ticketInformation.getSeatType());
        selectTicketAmount(numberTicket);
    }

    public void setTicketInfo(int numberTicket) {
        selectRandomDepartDate();
        selectRandomDepartAndArriveStation();
        selectRandomSeatType();
        selectTicketAmount(numberTicket);
    }

    public void setTicketInfo() {
        selectDepartDate.waitForVisibilityOfElementLocated(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        selectRandomDepartDate();
        selectRandomDepartAndArriveStation();
        selectRandomSeatType();
        selectRandomNumberOfTickets();
    }

    public void bookTicket(TicketInformation ticketInformation, int numberTickets) {
        setTicketInfo(ticketInformation, numberTickets);
        clickBookTicketButton();
    }

    public void bookTicket(int numberTickets) {
        setTicketInfo(numberTickets);
        clickBookTicketButton();
    }

    public void bookTicket() {
        setTicketInfo();
        clickBookTicketButton();
    }

    public void bookTicketMultipleTimes(int numberOfBookTicketTimes) {
        for (int i = 0; i < numberOfBookTicketTimes; i++) {
            bookTicket(1);
            if (i != numberOfBookTicketTimes - 1) gotoPage(TabName.BOOK_TICKET);
        }
    }

    public TicketInformation getTicketInfo() {
        return TicketInformation.builder()
                .departDate(getDepartDate())
                .departStation(getDepartStation())
                .arriveStation(getArriveStation())
                .seatType(getSeatType())
                .bookDate(getBookDate())
                .expiredDate(getExpiredDate())
                .ticketAmount(getTicketAmount())
                .build();
    }

    public String getBookDate() {
        int numberOfDaysUntilBookDate;
        if (DateHelper.getHourCurrent() < 12) numberOfDaysUntilBookDate = -1;
        else numberOfDaysUntilBookDate = 0;
        return DateHelper.getDateFromCurrentDate(Constants.M_D_YYYY_DATE_FORMAT,
                numberOfDaysUntilBookDate);
    }

    public String getExpiredDate() {
        return DateHelper.getDateFromCurrentDate(Constants.M_D_YYYY_DATE_FORMAT,
                NUMBER_OF_DAYS_UNTIL_EXPIRED);
    }

    public boolean verifyCantBookMoreThan10Tickets(int numberOfTickets) {
        String errorMessageTicketAmountActual = labelTicketAmountErrorMessage.getText();
        String errorMessageTicketAmountExpected =
                MAX_NUMBER_OF_TICKETS - numberOfTickets == 0 ? "You have booked 10 tickets. You can book no more."
                        : String.format("You have booked %s ticket(s). You can only book %s more.", numberOfTickets, MAX_NUMBER_OF_TICKETS - numberOfTickets);
        System.out.println("Actual message: " + errorMessageTicketAmountActual);
        System.out.println("Expected message: " + errorMessageTicketAmountExpected);
        return errorMessageTicketAmountActual.equals(errorMessageTicketAmountExpected);
    }

    public String getGeneralErrorMessage() {
        labelGeneralErrorMessage.waitForVisibilityOfElementLocated(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        return labelGeneralErrorMessage.getText();
    }
}
