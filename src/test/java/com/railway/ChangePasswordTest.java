package com.railway;

import com.railway.DataObject.ChangePasswordInfo;
import com.railway.DataObject.RegisterInformation;
import com.railway.DataObject.User;
import com.railway.DataType.TabName;
import com.railway.common.Log;
import com.railway.page.ChangePasswordPage;
import com.railway.page.HomePage;
import com.railway.page.LoginPage;
import com.railway.page.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ChangePasswordTest extends BaseTest {

    private static final String NEW_PASSWORD = "123456789";

    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();
    RegisterPage registerPage = new RegisterPage();
    ChangePasswordPage changePasswordPage = new ChangePasswordPage();

    @Test(testName = "RW_009 User can change password")
    public void RW_009_UserCanChangePassword() {
        RegisterInformation registerInformation = new RegisterInformation();

        Log.info("Navigate to register page and Create account");
        homePage.gotoPage(TabName.REGISTER);
        registerPage.register(registerInformation);

        Log.info("Navigate to login page and Login");
        registerPage.gotoPage(TabName.LOGIN);
        User user = User.builder()
                .email(registerInformation.getEmail())
                .password(registerInformation.getPassword())
                .build();
        loginPage.login(user);

        Log.info("Click on Change password tab");
        loginPage.gotoPage(TabName.CHANGE_PASSWORD);

        Log.info("Change password");
        ChangePasswordInfo changePasswordInfo = ChangePasswordInfo.builder()
                .currentPassword(user.getPassword())
                .newPassword(NEW_PASSWORD)
                .confirmPassword(NEW_PASSWORD)
                .build();
        changePasswordPage.changePassword(changePasswordInfo);

        Log.info("VP. Message 'Your password has been updated' appears.");
        Assert.assertEquals(changePasswordPage.getGeneralChangePassMessage(), "Your password has been updated!");
    }
}
