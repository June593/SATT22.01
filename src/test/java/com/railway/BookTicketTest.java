package com.railway;

import com.railway.DataObject.RegisterInformation;
import com.railway.DataObject.TicketInformation;
import com.railway.DataObject.User;
import com.railway.DataType.TabName;
import com.railway.common.Log;
import com.railway.page.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BookTicketTest extends BaseTest {
    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();
    RegisterPage registerPage = new RegisterPage();
    BookTicketPage bookTicketPage = new BookTicketPage();
    ManageTicketPage manageTicketPage = new ManageTicketPage();
    SuccessPage successPage = new SuccessPage();

    private static final String RW_017_FIRST_DATA_DESCRIPTION = "First and second booking with total amount less than 10";
    private static final int RW_017_LESS_THAN_10_FIRST_AMOUNT = 5;
    private static final int RW_017_LESS_THAN_10_SECOND_AMOUNT = 3;
    private static final String RW_017_SECOND_DATA_DESCRIPTION = "First and second booking with total amount equal to 10";
    private static final int RW_017_EQUAL_TO_10_FIRST_AMOUNT = 6;
    private static final int RW_017_EQUAL_TO_10_SECOND_AMOUNT = 4;
    private static final String RW_18_FIRST_DATA_DESCRIPTION = "First booking 10 tickets and the sum of two times is more than 10";
    private static final int RW_18_EQUAL_TO_10_FIRST_AMOUNT = 10;
    private static final int RW_18_EQUAL_TO_10_SECOND_AMOUNT = 1;
    private static final String RW_18_SECOND_DATA_DESCRIPTION = "First booking less than 10 tickets and total twice more than 10";
    private static final int RW_18_LESS_THAN_10_FIRST_AMOUNT = 8;
    private static final int RW_18_LESS_THAN_10_SECOND_AMOUNT = 3;

    @Test(testName = "RW_004 Login page displays when un-logged user clicks on book ticket tab")
    public void RW_004_LoginPageDisplaysWhenUn_logged_UserClicksOnBookTicketTab() {

        Log.info("Step. Click on login tab");
        homePage.gotoPage(TabName.BOOK_TICKET);

        Log.info("VP. Login page displays");
        Assert.assertEquals(loginPage.getPageTitle(), "Login Page");
    }

    @Test(testName = "RW_014 User can book 1 ticket at a time")
    public void RW_014_UserCanBook1TicketAtATime() {
        RegisterInformation registerInformation = new RegisterInformation();


        Log.info("Create a new account");
        homePage.gotoPage(TabName.REGISTER);
        registerPage.register(registerInformation);

        Log.info("Navigate to login page and Login");
        registerPage.gotoPage(TabName.LOGIN);
        User user = User.builder().email(registerInformation.getEmail()).password(registerInformation.getPassword()).build();
        loginPage.login(user);

        Log.info("Step. Click on Book ticket tab and select any ticket information");
        loginPage.gotoPage(TabName.BOOK_TICKET);
        bookTicketPage.setTicketInfo(1);

        Log.info("Step. Get information ticket before Clicking on Book ticket button");
        TicketInformation ticketInformation = bookTicketPage.getTicketInfo();

        Log.info("Step. Click on Book Ticket button");
        bookTicketPage.clickBookTicketButton();

        Log.info("VP. 'Ticket booked successfully!' displays");
        Assert.assertEquals(bookTicketPage.getPageTitle(), "Ticket Booked Successfully!");

        Log.info("VP. Booked ticket should have the correct info: " + "Depart Station, Arrive Station, Seat Type, Depart Date, Book Date, Expired Date");
        Assert.assertTrue(successPage.checkBookedTicketInformationWithSelectedInformationBookTicket(ticketInformation), "Depart Station is not displayed as expected");
    }


    @DataProvider(name = "RW_017")
    public Object[][] RW_017_UserCanBookMultipleTickets() {
        return new Object[][]{{RW_017_FIRST_DATA_DESCRIPTION, RW_017_LESS_THAN_10_FIRST_AMOUNT, RW_017_LESS_THAN_10_SECOND_AMOUNT}, {RW_017_SECOND_DATA_DESCRIPTION, RW_017_EQUAL_TO_10_FIRST_AMOUNT, RW_017_EQUAL_TO_10_SECOND_AMOUNT}};
    }

    @Test(dataProvider = "RW_017", testName = "RW_017_UserCanBookMultipleTickets")
    public void RW_017_UserCanBookMultipleTickets(String dataDescription, int firstTicketAmount, int secondTicketAmount) {
        RegisterInformation registerInformation = new RegisterInformation();

        Log.info("Pre-condition: Create a new account");
        homePage.gotoPage(TabName.REGISTER);
        registerPage.register(registerInformation);

        Log.info("Navigate to login page and Login");
        registerPage.gotoPage(TabName.LOGIN);
        User user = User.builder().email(registerInformation.getEmail()).password(registerInformation.getPassword()).build();
        loginPage.login(user);

        Log.info(dataDescription);
        Log.info(String.format("Click on Book ticket tab and book %s with random information", firstTicketAmount));
        loginPage.gotoPage(TabName.BOOK_TICKET);
        bookTicketPage.bookTicket(firstTicketAmount);

        Log.info(String.format("Click on Book ticket tab and book %s with random information", secondTicketAmount));
        successPage.gotoPage(TabName.BOOK_TICKET);
        bookTicketPage.bookTicket(secondTicketAmount);

        Log.info("Click on My Ticket tab");
        successPage.gotoPage(TabName.MY_TICKET);

        Log.info("VP. On note section on the left this page displays:\n" + "- if <x + y> = 10: You currently book 10 tickets, you can book no more.\n" + "- if <x + y> < 10: You currently book <x + y> tickets, you can book <10 - x - y> more.");
        int numberOfTickets = manageTicketPage.getTotalTicket();
        System.out.println(numberOfTickets);
        Assert.assertTrue(manageTicketPage.checkMessageNoteDisplayIsCorrect(numberOfTickets), "Note message are not displayed or displayed incorrectly");
    }


    @DataProvider(name = "data")
    public Object[][] dp() {
        return new Object[][]{{RW_18_FIRST_DATA_DESCRIPTION, RW_18_EQUAL_TO_10_FIRST_AMOUNT, RW_18_EQUAL_TO_10_SECOND_AMOUNT},
                {RW_18_SECOND_DATA_DESCRIPTION, RW_18_LESS_THAN_10_FIRST_AMOUNT, RW_18_LESS_THAN_10_SECOND_AMOUNT}};
    }

    @Test(dataProvider = "data", testName = "RW_018 User can not book more than 10 tickets")
    public void RW_018_UserCanNotBookMoreThan10Tickets(String dataDescription, int firstTicketAmount, int secondTicketAmount) {
        RegisterInformation registerInformation = new RegisterInformation();

        Log.info("Pre-condition: Create a new account");
        homePage.gotoPage(TabName.REGISTER);
        registerPage.register(registerInformation);

        Log.info("Navigate to login page and Login");
        registerPage.gotoPage(TabName.LOGIN);
        User user = User.builder().email(registerInformation.getEmail()).password(registerInformation.getPassword()).build();
        loginPage.login(user);

        Log.info(dataDescription);
        Log.info(String.format("Click on Book ticket tab and Book %s tickets with random information", firstTicketAmount));
        loginPage.gotoPage(TabName.BOOK_TICKET);
        bookTicketPage.bookTicket(firstTicketAmount);

        Log.info(String.format("Click on Book ticket tab and Book %s tickets with random information", secondTicketAmount));
        successPage.gotoPage(TabName.BOOK_TICKET);
        bookTicketPage.bookTicket(secondTicketAmount);

        Log.info("VP. A general error message is displayed");
        Assert.assertEquals(bookTicketPage.getGeneralErrorMessage(), "There're errors in the form. Please correct the errors and try again.");

        Log.info("VP. On the right Ticket Amount dropdown, an error message is displayed:");
        Assert.assertTrue(bookTicketPage.verifyCantBookMoreThan10Tickets(firstTicketAmount), "The error message is not displays as expected");
    }
}
