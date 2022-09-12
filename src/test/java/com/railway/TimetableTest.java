package com.railway;

import com.railway.DataObject.TicketInformation;
import com.railway.DataObject.User;
import com.railway.DataType.TabName;
import com.railway.common.Constants;
import com.railway.common.Log;
import com.railway.page.BookTicketPage;
import com.railway.page.HomePage;
import com.railway.page.LoginPage;
import com.railway.page.TimetablePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TimetableTest extends BaseTest {

    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();
    TimetablePage timetablePage = new TimetablePage();
    BookTicketPage bookTicketPage = new BookTicketPage();

    @Test(testName = "RW_015 User can go to book ticket page by clicking on book ticket link")
    public void RW_015_UserCanGoToBookTicketPageByClickingOnBookTicketLink() {

        Log.info("Log in with a valid account");
        homePage.gotoPage(TabName.LOGIN);
        User user = new User(Constants.VALID_USERNAME, Constants.VALID_PASSWORD);
        loginPage.login(user);

        Log.info("Go to Timetable Page");
        loginPage.gotoPage(TabName.TIMETABLE);

        Log.info("Select and get info ticket after Clicking on any Book ticket link");
        int selectedRowIndex = timetablePage.getRandomRowIndex();
        TicketInformation ticketInformation = timetablePage.getTicketInfo(selectedRowIndex);
        timetablePage.clickBookTicketLink(selectedRowIndex);

        Log.info("VP. Navigate to Book ticket page corresponding with Depart Station, Arrive Station");
        Assert.assertEquals(bookTicketPage.getDepartStation(), ticketInformation.getDepartStation());
        Assert.assertEquals(bookTicketPage.getArriveStation(), ticketInformation.getArriveStation());
    }
}
