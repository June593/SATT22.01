package com.railway.page;

import com.railway.DataType.TabName;
import com.railway.common.Constants;
import com.railway.element.Element;
import org.openqa.selenium.By;

public class BasePage {

    public BasePage() {
    }

    String locatorTab = "//span[text()='%s']";

    private static final Element textPageTitle = new Element(By.xpath("//div[@id='content']//h1"));
    private static final Element textWelcomeUserMessage = new Element(By.xpath("//div[@class='account']/strong"));
    private static final Element lblLogOutTab = new Element(By.xpath("//span[text()='Log out']"));

    public String getPageTitle() {
        return textPageTitle.getText();
    }

    public String getWelComeUserText() {
        return textWelcomeUserMessage.getText();
    }

    public Element getTabElement(String tabName) {
        return new Element(By.xpath(String.format(locatorTab, tabName)));
    }

    public void gotoPage(TabName tabName) {
        getTabElement(tabName.name).waitForVisibilityOfElementLocated(Constants.MEDIUM_TIMEOUT_IN_SECONDS);
        getTabElement(tabName.name).click();
    }

    public boolean checkDisplayedTab(TabName tabName) {
        return getTabElement(tabName.name).isDisplayed();
    }

    public boolean isDisplayedLogOutTab() {
        return lblLogOutTab.isDisplayed();
    }

    public void clickLogoutTab() {
        lblLogOutTab.click();
    }
}
