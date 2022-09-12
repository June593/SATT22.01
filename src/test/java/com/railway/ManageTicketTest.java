package com.railway;

import com.railway.DataObject.Filter;
import com.railway.DataObject.RegisterInformation;
import com.railway.DataObject.TicketInformation;
import com.railway.DataObject.User;
import com.railway.DataType.SeatType;
import com.railway.DataType.StationName;
import com.railway.DataType.StatusName;
import com.railway.DataType.TabName;
import com.railway.DataType.header_name.ManageTicketTableHeader;
import com.railway.common.Constants;
import com.railway.common.DateHelper;
import com.railway.common.Log;
import com.railway.driver.AlertHelper;
import com.railway.page.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ManageTicketTest extends BaseTest {

    HomePage homePage = new HomePage();
    RegisterPage registerPage = new RegisterPage();
    LoginPage loginPage = new LoginPage();
    ManageTicketPage manageTicketPage = new ManageTicketPage();
    BookTicketPage bookTicketPage = new BookTicketPage();
    SuccessPage successPage = new SuccessPage();

    String departDateOfTicket1 = DateHelper.getDateFromCurrentDate(Constants.M_D_YYYY_DATE_FORMAT, 4);

    String departDateOfTicket2 = DateHelper.getDateFromCurrentDate(Constants.M_D_YYYY_DATE_FORMAT, 5);

    @Test(testName = "RW_016_UserCancelABookTicket")
    public void RW_016_UserCancelABookTicket() {

        Log.info("Create a new account");
        homePage.gotoPage(TabName.REGISTER);
        RegisterInformation registerInformation = new RegisterInformation();
        registerPage.register(registerInformation);

        Log.info("Navigate to login page and Login");
        registerPage.gotoPage(TabName.LOGIN);
        User user = User.builder()
                .email(registerInformation.getEmail())
                .password(registerInformation.getPassword())
                .build();
        loginPage.login(user);

        Log.info("Click on Book ticket tab");
        loginPage.gotoPage(TabName.BOOK_TICKET);

        Log.info("Book ticket with random info and 1 number ticket in 3 times");
        bookTicketPage.bookTicket(1);
        String idTicket = successPage.getIdTicket();
        successPage.gotoPage(TabName.BOOK_TICKET);
        bookTicketPage.bookTicketMultipleTimes(2);

        Log.info("Click on My Ticket tab");
        successPage.gotoPage(TabName.MY_TICKET);

        Log.info("Step. Click on 'Cancel' button of ticket which user want to cancel.");
        int rowPosition = manageTicketPage.getTicketRow(idTicket);
        manageTicketPage.cancelTicket(rowPosition);

        Log.info(" Click on 'OK' button on Confirmation message 'Are you sure?'");
        AlertHelper.acceptAlert();

        Log.info("VP. The canceled ticket is removed");
        Assert.assertFalse(manageTicketPage.isExistedTicket(idTicket), "The ticket is not removed");
    }

    @Test(testName = "RW_019 Verify filter function is working correctly")
    public void RW_019_VerifyFilterFunctionIsWorkingCorrectly() {

        Log.info("Pre-condition: Create a new account");
        homePage.gotoPage(TabName.REGISTER);
        RegisterInformation registerInformation = new RegisterInformation();
        registerPage.register(registerInformation);

        Log.info("Navigate to login page and Login");
        registerPage.gotoPage(TabName.LOGIN);
        User user = User.builder()
                .email(registerInformation.getEmail())
                .password(registerInformation.getPassword())
                .build();
        loginPage.login(user);

        TicketInformation ticket1 = TicketInformation.builder()
                .departDate(departDateOfTicket1)
                .departStation(StationName.SAI_GON.name)
                .arriveStation(StationName.PHAN_THIET.name)
                .seatType(SeatType.HARD_SEAT.name)
                .build();

        TicketInformation ticket2 = TicketInformation.builder()
                .departDate(departDateOfTicket2)
                .departStation(StationName.SAI_GON.name)
                .arriveStation(StationName.PHAN_THIET.name)
                .seatType(SeatType.HARD_SEAT.name)
                .build();

        Filter filter1 = Filter.builder()
                .departStation(StationName.SAI_GON.name)
                .arriveStation(StationName.PHAN_THIET.name)
                .departDate(departDateOfTicket1)
                .status(StatusName.NEW.name)
                .build();

        Filter filter2 = Filter.builder()
                .departStation(StationName.IGNORED.name)
                .arriveStation(StationName.PHAN_THIET.name)
                .departDate(departDateOfTicket2)
                .status(StatusName.IGNORED.name)
                .build();

        Filter filter3 = Filter.builder()
                .departStation(StationName.SAI_GON.name)
                .arriveStation(StationName.IGNORED.name)
                .departDate("")
                .status(StatusName.IGNORED.name)
                .build();

        Log.info("Book tickets with different random information");
        loginPage.gotoPage(TabName.BOOK_TICKET);
        bookTicketPage.bookTicketMultipleTimes(4);
        successPage.gotoPage(TabName.BOOK_TICKET);
        bookTicketPage.bookTicket(ticket1, 2);
        successPage.gotoPage(TabName.BOOK_TICKET);
        bookTicketPage.bookTicket(ticket2, 1);

        Log.info("Step. Click on My ticket tab");
        bookTicketPage.gotoPage(TabName.MY_TICKET);

        Log.info("Select the filter options and check the results after the filter is active");
        Assert.assertTrue(manageTicketPage.selectFilterOptionsAndCheckResultOfFilter(filter1, filter2, filter3),
                "The result of filter is not correct");
    }

    @Test(testName = " FTTC01 User can filter 'Manager ticket' table with Depart Station")
    public void FTTC01() {
        RegisterInformation registerInformation = new RegisterInformation();
        User user = User.builder()
                .email(registerInformation.getEmail())
                .password(registerInformation.getPassword())
                .build();

        Log.info("Create a new account");
        homePage.gotoPage(TabName.REGISTER);
        registerPage.register(registerInformation);

        Log.info("Step. Navigate to login page and Login");
        registerPage.gotoPage(TabName.LOGIN);
        loginPage.login(user);

        Log.info("Step. Book more than 6 tickets");
        loginPage.gotoPage(TabName.BOOK_TICKET);
        bookTicketPage.bookTicketMultipleTimes(7);

        Log.info("Step. Click on My ticket tab");
        successPage.gotoPage(TabName.MY_TICKET);

        Log.info("Step. Select one of booked Depart Station in 'Depart Station' dropdown list");
        int departStationRowIndex = manageTicketPage.getRandomRowIndexOfManageTicketTable();
        String departStationFilterInput = manageTicketPage.getCellText(departStationRowIndex, ManageTicketTableHeader.DEPART_STATION.name);
        int numberOfExpectedTicketResult = manageTicketPage.getNumberOfValuesInManageTable(ManageTicketTableHeader.DEPART_STATION.name, departStationFilterInput);
        manageTicketPage.selectDepartStation(departStationFilterInput);

        Log.info("Step. Click 'Apply Filter' button");
        manageTicketPage.clickApplyFilterButton();

        Log.info(String.format("VP. 'Manage ticket' table shows correct ticket(s) with result has Depart Station: %s is %s ticket(s)", departStationFilterInput, numberOfExpectedTicketResult));
        Assert.assertTrue(manageTicketPage.checkResultDisplaysCorrectlyWithSelectedFilter(ManageTicketTableHeader.DEPART_STATION.name, departStationFilterInput, numberOfExpectedTicketResult),
                "The result of filter is not displayed correctly");
    }
}

