package com.railway;

import com.railway.DataObject.RegisterInformation;
import com.railway.DataType.TabName;
import com.railway.common.Constants;
import com.railway.common.Log;
import com.railway.page.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    public static final String MISMATCHED_PASSWORD = Constants.VALID_PASSWORD + "_";

    RegisterPage registerPage = new RegisterPage();

    @Test(testName = "RW_007 User can register new account")
    public void RW_007_UserCanRegisterNewAccount() {

        Log.info("Step. Click on Register tab");
        registerPage.gotoPage(TabName.REGISTER);

        Log.info("Step. Register account");
        RegisterInformation registerInformation = new RegisterInformation();
        registerPage.register(registerInformation);

        Log.info("VP. New account is created and message 'Thank you for registering your account' appears.");
        Assert.assertEquals(registerPage.getSuccessfullyRegisterMessage(), "Thank you for registering your account");
    }

    @Test(testName = "RW_010 User cant create account with confirm password is not the same with password")
    public void RW_010_UserCantCreateAccountWithConfirmPasswordIsNotTheSameWithPassword() {

        Log.info("Step. Click on Register tab");
        registerPage.gotoPage(TabName.REGISTER);

        Log.info("Step. Register account with valid information");
        RegisterInformation registerInformation = RegisterInformation.builder()
                .email(Constants.VALID_USERNAME)
                .password(Constants.VALID_PASSWORD)
                .confirmPassword(MISMATCHED_PASSWORD)
                .pid(Constants.VALID_PID)
                .build();
        registerPage.register(registerInformation);

        Log.info("VP. Message 'There're errors in the form. Please correct the errors and try again.' appears.");
        Assert.assertEquals(registerPage.getLabelGeneralErrorMessage(),
                "There're errors in the form. Please correct the errors and try again.");
    }

    @Test(testName = "RW_011 User can't create account while password and PID fields are empty")
    public void RW_011_UserCantCreateAccountWhilePasswordAndPIDFiledsAreEmpty() {
        RegisterInformation registerInformation = RegisterInformation.builder()
                .email(Constants.VALID_USERNAME)
                .password("")
                .confirmPassword("")
                .pid("")
                .build();

        Log.info("Step. Click on Register tab");
        registerPage.gotoPage(TabName.REGISTER);

        Log.info("Step. Register account");
        registerPage.register(registerInformation);

        Log.info("VP. Message 'There're errors in the form. Please correct the errors and try again.' appears.");
        Assert.assertEquals(registerPage.getLabelGeneralErrorMessage(),
                "There're errors in the form. Please correct the errors and try again.");
        Log.info("VP. Next to password fields, error message 'Invalid password length.' displays.");
        Assert.assertEquals(registerPage.getLabelPasswordErrorMessage(),
                "Invalid password length");
        Log.info("VP. Next to password fields, error message 'Invalid PID length.' displays.");
        Assert.assertEquals(registerPage.getLabelPidErrorMessage(),
                "Invalid ID length");
    }
}
