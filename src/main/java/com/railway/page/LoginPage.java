package com.railway.page;

import com.railway.DataObject.User;
import com.railway.element.Element;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final Element textUsername = new Element(By.id("username"));
    private final Element textPassword = new Element(By.id("password"));
    private final Element buttonLogin = new Element(By.xpath("//input[@title='Login']"));
    private final Element labelGeneralErrorMessage = new Element(By.xpath("//p[@class='message error LoginForm']"));
    private final Element labelUsernameErrorMessage = new Element(By.xpath("//label[@for='username' and @class='validation-error']"));
    private final Element labelPasswordErrorMessage = new Element(By.xpath("//label[@for='password' and @class='validation-error']"));

    public String getGeneralErrorMessage() {
        return labelGeneralErrorMessage.getText();
    }

    public String getUsernameErrorMsg() {
        return labelUsernameErrorMessage.getText();
    }

    public String getPasswordErrorMsg() {
        return labelPasswordErrorMessage.getText();
    }

    public void fillLoginForm(User user) {
        textUsername.enter(user.getEmail());
        textPassword.enter(user.getPassword());
    }

    public void clickLoginButton() {
        buttonLogin.scrollToView();
        buttonLogin.click();
    }

    public void login(User user) {
        fillLoginForm(user);
        clickLoginButton();
    }

    public void clearLoginForm() {
        textUsername.scrollToView();
        textUsername.clear();
        textPassword.scrollToView();
        textPassword.clear();
    }

    public void loginMultipleTimes(User user, int numberOfLogins) {
        for (int i = 0; i < numberOfLogins; i++) {
            login(user);
            clearLoginForm();
        }
    }

    public String getTxtPassword() {
        return textPassword.getText();
    }
}
