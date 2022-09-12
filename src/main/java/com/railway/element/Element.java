package com.railway.element;

import com.railway.driver.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Element {

    private final By byLocator;

    private Element parent;

    public Element(By byLocator) {
        this.byLocator = byLocator;
    }

    public Element(Element parentElement, By byLocator) {
        this.byLocator = byLocator;
        this.parent = parentElement;
    }

    protected WebDriver getDriver() {
        return DriverUtils.getWebDriver();
    }

    protected JavascriptExecutor jsExecutor() {
        return (JavascriptExecutor) getDriver();
    }

    public WebElement getElement() {
        WebElement element = null;
        if (parent != null) {
            WebElement eleParent = parent.getElement();
            element = eleParent.findElement(getLocator());
        } else {
            element = getDriver().findElement(getLocator());
        }
        return element;
    }

    public List<WebElement> getElements() {

        if (parent != null) {
            return parent.getElement().findElements(getLocator());
        }
        return getDriver().findElements(getLocator());
    }


    public By getLocator() {
        return byLocator;
    }

    public void enter(String value) {
        getElement().sendKeys(value);
    }

    public void click() {
        getElement().click();
    }

    public void clear() {
        getElement().clear();
    }

    public String getText() {
        return getElement().getText();
    }

    public boolean isDisplayed() {
        return getElements().size() > 0 && getElement().isDisplayed();
    }

    public void scrollToView() {
        jsExecutor().executeScript("arguments[0].scrollIntoView(true);", getElement());
    }

    //Method for select
    public int getOptionsSize() {
        Select select = new Select(getElement());
        List<WebElement> options = select.getOptions();
        return options.size();
    }

    public String getSelected() {
        Select select = new Select(getElement());
        return select.getFirstSelectedOption().getText();
    }

    public void select(int index) {
        Select select = new Select(getElement());
        select.selectByIndex(index);
    }

    public void select(String visibleText) {
        Select select = new Select(getElement());
        select.selectByVisibleText(visibleText);
    }

    public List<WebElement> getOptionsOfSelect() {
        Select select = new Select(getElement());
        return select.getOptions();
    }

    public void waitForElementIsChanged(int timeOutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutInSeconds));
            wait.until(ExpectedConditions.stalenessOf(getElement()));
        } catch (NullPointerException ex) {
            System.out.println("Element is not changed");
        }
    }

    public void waitForVisibilityOfElementLocated(int timeOutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator()));
        } catch (Exception ex) {
            System.out.println("Element is not displayed");
        }
    }
}
