package com.railway;

import com.railway.driver.DriverUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    @Parameters({"browser"})
    @BeforeMethod
    public void beforeMethod(@Optional("chrome") String browser) {
        System.out.println("Pre-condition");
        DriverUtils.startBrowser(browser);

    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        if (DriverUtils.getWebDriver() != null) {
            DriverUtils.quitBrowser();
        }
    }
}

