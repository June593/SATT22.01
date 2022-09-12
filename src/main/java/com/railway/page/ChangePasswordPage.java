package com.railway.page;

import com.railway.DataObject.ChangePasswordInfo;
import com.railway.element.Element;
import org.openqa.selenium.By;

public class ChangePasswordPage extends BasePage {

    private final Element textCurrentPassword = new Element(By.id("currentPassword"));
    private final Element textNewPassword = new Element(By.id("newPassword"));
    private final Element textConfirmPassword = new Element(By.id("confirmPassword"));
    private final Element buttonChangePassword = new Element(By.xpath("//input[@type='submit']"));
    private final Element labelGeneralErrorMessage = new Element(By.xpath("//p[@class='message error']"));
    private final Element labelGeneralChangePassMessage = new Element(By.xpath("//p[@class='message success']"));
    private final Element labelCurrentPasswordErrorMessage = new Element(By.xpath("//label[@for='currentPassword' and @class='validation-error']"));
    private final Element labelNewPasswordErrorMessage = new Element(By.xpath("//label[@for='newPassword' and @class='validation-error']"));
    private final Element labelConfirmPasswordErrorMessage = new Element(By.xpath("//label[@for='confirmPassword' and @class='validation-error']"));

    public void fillChangePasswordForm(ChangePasswordInfo changePasswordInfo) {
        textCurrentPassword.enter(changePasswordInfo.getCurrentPassword());
        textNewPassword.enter(changePasswordInfo.getNewPassword());
        textConfirmPassword.enter(changePasswordInfo.getConfirmPassword());
    }

    public void clickChangePasswordButton() {
        buttonChangePassword.scrollToView();
        buttonChangePassword.click();
    }

    public void changePassword(ChangePasswordInfo changePasswordInfo) {
        fillChangePasswordForm(changePasswordInfo);
        clickChangePasswordButton();
    }

    public String getGeneralError() {
        return labelGeneralErrorMessage.getText();
    }

    public String getCurrentPasswordError() {
        return labelCurrentPasswordErrorMessage.getText();
    }

    public String getNewPasswordError() {
        return labelNewPasswordErrorMessage.getText();
    }

    public String getConfirmPasswordError() {
        return labelConfirmPasswordErrorMessage.getText();
    }

    public String getCurrentPassword() {
        return textCurrentPassword.getText();
    }

    public String getNewPassword() {
        return textNewPassword.getText();
    }

    public String getConfirmPassword() {
        return textConfirmPassword.getText();
    }

    public String getGeneralChangePassMessage() {
        return labelGeneralChangePassMessage.getText();
    }
}
