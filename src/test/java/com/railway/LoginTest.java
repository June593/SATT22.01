package com.railway;

import com.railway.DataObject.RegisterInformation;
import com.railway.DataObject.User;
import com.railway.DataType.PageName;
import com.railway.DataType.TabName;
import com.railway.common.Constants;
import com.railway.common.Log;
import com.railway.page.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    public static final String INVALID_PASSWORD = "abcd1234";
    public static final int numberOfLoginTimes = 3;
    HomePage homePage = new HomePage();
    RegisterPage registerPage = new RegisterPage();
    LoginPage loginPage = new LoginPage();
    ManageTicketPage manageTicketPage = new ManageTicketPage();
    ChangePasswordPage changePasswordPage = new ChangePasswordPage();

    User validUser = new User(Constants.VALID_USERNAME, Constants.VALID_PASSWORD);

    @Test(testName = "RW_001 Verify user can login with valid username and password")
    public void RW_001_VerifyUserCanLoginWithValidUsernameAndPassword() {

        Log.info("Step. Click on login tab");
        homePage.gotoPage(TabName.LOGIN);

        Log.info("Step. Log in with valid email and password");
        loginPage.login(validUser);

        Log.info("VP. User is logged into Railway. Welcome user message is displayed.");
        Assert.assertEquals(loginPage.getWelComeUserText(), "Welcome " + Constants.VALID_USERNAME,
                loginPage.getWelComeUserText());
    }

    @Test(testName = "RW_002 User cant login with blank username and valid password")
    public void RW_002_UserCantLoginWithBlankUsernameAndValidPassword() {

        Log.info("Step. Click on login tab");
        homePage.gotoPage(TabName.LOGIN);

        Log.info("Step. Log in with blank email and valid password");
        User user = new User("", Constants.VALID_PASSWORD);
        loginPage.login(user);

        Log.info("VP. User can't login");
        Assert.assertFalse(loginPage.getTabElement(TabName.LOG_OUT.name).isDisplayed(), "Log-out tab is not displayed");

        Log.info("VP. A general error message appears");
        Assert.assertEquals(loginPage.getGeneralErrorMessage(),
                "There was a problem with your login and/or errors exist in your form.");
    }

    @Test(testName = "RW_003 Verify user cant login without entering username and password")
    public void RW_003_VerifyUserCantLoginWithoutEnteringUsernameAndPassword() {

        Log.info("Step. Click on login tab");
        homePage.gotoPage(TabName.LOGIN);

        Log.info("Step. Log in with valid email and invalid password");
        User user = new User(Constants.VALID_USERNAME, INVALID_PASSWORD);
        loginPage.login(user);

        Log.info("VP. User can't login");
        Assert.assertFalse(loginPage.getTabElement(TabName.LOG_OUT.name).isDisplayed(), "Log-out tab is not displayed");

        Log.info("VP. A general error message appears");
        Assert.assertEquals(loginPage.getGeneralErrorMessage(),
                "There was a problem with your login and/or errors exist in your form.");
    }

    @Test(testName = "RW_005 System shows message when user enters wrong password several times")
    public void RW_005_SystemShowsMessageWhenUserEntersWrongPasswordSeveralTimes() {
        User user = new User(Constants.VALID_USERNAME, INVALID_PASSWORD);

        Log.info("Step. Click on login tab");
        homePage.gotoPage(TabName.LOGIN);

        Log.info(String.format("Step. Log in %s times with valid username and invalid password", numberOfLoginTimes));
        loginPage.loginMultipleTimes(user, numberOfLoginTimes);

        Log.info("VP. User can't login");
        Assert.assertFalse(loginPage.getTabElement(TabName.LOG_OUT.name).isDisplayed(), "Log-out tab is not displayed");

        Log.info("VP. A general error message appears");
        Assert.assertEquals(loginPage.getGeneralErrorMessage(),
                "You have used 4 out of 5 login attempts. " +
                        "After all 5 have been used, you will be unable to login for 15 minutes");
    }

    @Test(testName = "RW_006 additional pages display once user logged in")
    public void RW_006_AdditionalPagesDisplayOnceUserLoggedin() {

        Log.info("Step. Click on login tab");
        homePage.gotoPage(TabName.LOGIN);

        Log.info("Step. Log in with valid email and password");
        loginPage.login(validUser);

        Log.info("VP. 'My ticket' tab is displayed.");
        Assert.assertTrue(loginPage.checkDisplayedTab(TabName.MY_TICKET), "My ticket tab are not displayed");
        Log.info("VP. 'Change password' tab is displayed.");
        Assert.assertTrue(loginPage.checkDisplayedTab(TabName.CHANGE_PASSWORD), "Change password tab are not displayed");
        Log.info("VP. 'Logout' tab is displayed.");
        Assert.assertTrue(loginPage.isDisplayedLogOutTab(), "Logout tab are not displayed");

        Log.info("Step. CLick on My ticket tab");
        loginPage.gotoPage(TabName.MY_TICKET);
        Log.info("VP. Navigate to My ticket page");
        Assert.assertEquals(manageTicketPage.getPageTitle(), PageName.MY_TICKET.name);

        Log.info("Step. CLick on Change password tab");
        manageTicketPage.gotoPage(TabName.CHANGE_PASSWORD);
        Log.info("VP. Navigate to Change password page");
        Assert.assertEquals(changePasswordPage.getPageTitle(), PageName.CHANGE_PASSWORD.name);
    }

    @Test(testName = "User can't login with an account hasn't been activated")
    public void RW_008_UserCanNotLoginWithAnAccountHasNotBeenActivated() {
        RegisterInformation registerInformation = new RegisterInformation();
        User user = User.builder()
                .email(registerInformation.getEmail())
                .password(registerInformation.getPassword())
                .build();

        Log.info("Pre-condition: Create a account and do not active account");
        homePage.gotoPage(TabName.REGISTER);
        registerPage.register(registerInformation);

        Log.info("Step. Click on login tab");
        homePage.gotoPage(TabName.LOGIN);

        Log.info("Step. Log in with un-active user");
        loginPage.login(user);

        Log.info("VP. User can't login");
        Assert.assertFalse(loginPage.getTabElement(TabName.LOG_OUT.name).isDisplayed(), "Log-out tab is not displayed");

        Log.info("VP. 'Invalid username or password. Please try again.' an error message appears.");
        Assert.assertEquals(loginPage.getGeneralErrorMessage(), "Invalid username or password. Please try again.",
                "The error message is not displayed as expected");
    }
}
