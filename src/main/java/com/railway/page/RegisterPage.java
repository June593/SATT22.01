package com.railway.page;

import com.railway.DataObject.RegisterInformation;
import com.railway.element.Element;
import org.openqa.selenium.By;

public class RegisterPage extends BasePage {

    public RegisterPage() {
    }

    private final Element textEmail = new Element(By.id("email"));
    private final Element textPassword = new Element(By.id("password"));
    private final Element textConfirmPassword = new Element(By.id("confirmPassword"));
    private final Element textPid = new Element(By.id("pid"));
    private final Element buttonRegister = new Element(By.xpath("//input[@title='Register']"));
    private final Element labelEmailErrorMessage = new Element(By.xpath("//label[@for='email' and @class='validation-error']"));
    private final Element labelPasswordErrorMessage = new Element(By.xpath("//label[@for='password' and @class='validation-error']"));
    private final Element labelConfirmPasswordErrorMessage = new Element(By.xpath("//label[@for='confirmPassword' and @class='validation-error']"));
    private final Element labelPidErrorMessage = new Element(By.xpath("//label[@for='pid' and @class='validation-error']"));
    private final Element labelGeneralErrorMessage = new Element(By.xpath("//p[@class='message error']"));
    private final Element textSuccessfullyRegister = new Element(By.id("content"));

    public void fillSignUpForm(RegisterInformation registerInformation) {
        enterEmail(registerInformation.getEmail());
        enterPassword(registerInformation.getPassword());
        enterConfirmPassword(registerInformation.getConfirmPassword());
        enterPid(registerInformation.getPid());
    }

    public void enterEmail(String email) {
        textEmail.enter(email);
    }

    public void enterPassword(String password) {
        textPassword.enter(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        textConfirmPassword.enter(confirmPassword);
    }

    public void enterPid(String pid) {
        textPid.enter(pid);
    }

    public void register(RegisterInformation registerInformation) {
        fillSignUpForm(registerInformation);
        buttonRegister.scrollToView();
        clickRegisterButton();
    }

    public void clickRegisterButton() {
        buttonRegister.click();
    }

    public String getSuccessfullyRegisterMessage() {
        return textSuccessfullyRegister.getText();
    }

    public String getLabelGeneralErrorMessage() {
        return labelGeneralErrorMessage.getText();
    }

    public String getLabelEmailErrorMessage() {
        return labelEmailErrorMessage.getText();
    }

    public String getLabelPasswordErrorMessage() {
        return labelPasswordErrorMessage.getText();

    }

    public String getLabelConfirmPassErrorMsg() {
        return labelConfirmPasswordErrorMessage.getText();
    }

    public String getLabelPidErrorMessage() {
        return labelPidErrorMessage.getText();
    }
}
