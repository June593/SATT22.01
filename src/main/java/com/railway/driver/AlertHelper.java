package com.railway.driver;

import com.railway.common.Constants;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AlertHelper {

    public static void dismissAlert() {
        waitForAlertVisible();
        DriverUtils.getWebDriver().switchTo().alert().dismiss();
    }

    public static void acceptAlert() {
        waitForAlertVisible();
        DriverUtils.getWebDriver().switchTo().alert().accept();
    }

    public static String getTextAlert() {
        waitForAlertVisible();
        return DriverUtils.getWebDriver().switchTo().alert().getText();
    }

    public static void waitForAlertVisible() {
        WebDriverWait wait = new WebDriverWait(DriverUtils.getWebDriver(), Duration.ofSeconds(Constants.MEDIUM_TIMEOUT_IN_SECONDS));
        wait.until(ExpectedConditions.alertIsPresent());
    }
}
