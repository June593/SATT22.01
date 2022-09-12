package com.railway.driver;

import com.railway.common.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverUtils {
    private static WebDriver driver;

    public static void startBrowser(String browser) {
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            default:
                System.out.println("Browser: " + browser + " is invalid, Launching Chrome as browser of choice...");
                driver = new ChromeDriver();

        }
        navigate(Constants.RAILWAY_HOMEPAGE_URL);
        maximizeWindow();
    }

    public static WebDriver getWebDriver() {
        return driver;
    }

    public static void maximizeWindow() {
        getWebDriver().manage().window().maximize();
    }

    public static void navigate(String url) {
        getWebDriver().get(url);
    }

    public static void minimizeWindow() {
        getWebDriver().manage().window().minimize();
    }

    public static void quitBrowser() {
        getWebDriver().quit();
    }

    public static String getCurrentUrl() {
        return DriverUtils.getWebDriver().getCurrentUrl();
    }
}
