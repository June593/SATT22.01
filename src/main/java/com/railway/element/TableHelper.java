package com.railway.element;

import com.railway.common.Helper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TableHelper {

    private Element tableElement;
    private Element tableRowElement;
    private Element tableColumnElement;

    private By byRowLocator = By.xpath("./tbody/tr");
    private By byColumnLocator = By.xpath(".//tr/th");

    private String cellXpathLocator = ".//tbody/tr[%s]/td[%s]";
    private List<WebElement> rowElement;
    private List<WebElement> columnElement;

    public TableHelper(Element element) {
        tableElement = element;
        tableRowElement = new Element(tableElement, byRowLocator);
        tableColumnElement = new Element(tableElement, byColumnLocator);
    }

    public int getHeaderIndex(String headerName) {
        columnElement = tableColumnElement.getElements();
        for (int i = 0; i < columnElement.size(); i++) {
            if (columnElement.get(i).getText().equals(headerName)) {
                return ++i;
            }
        }
        throw new RuntimeException("Can't find header name: " + headerName);
    }

    public int getRandomRowIndex() {
        int max = tableRowElement.getElements().size();
        return Helper.getRandomIndex(1, max);
    }

    public WebElement getCellElement(int rowIndex, int columnIndex) {
        Element element = new Element(tableElement, By.xpath(String.format(cellXpathLocator, rowIndex, columnIndex)));
        return element.getElement();
    }

    public String getCellText(int rowIndex, String headerName) {
        int columnIndex = getHeaderIndex(headerName);
        return getCellElement(rowIndex, columnIndex).getText();
    }


    public void clickCell(int rowIndex, String headerName) {
        int columnIndex = getHeaderIndex(headerName);
        Element tableCellElement = new Element(By.xpath(String.format(cellXpathLocator, rowIndex, columnIndex)));
        tableCellElement.scrollToView();
        getCellElement(rowIndex, columnIndex).click();
    }

    public List<WebElement> getRowElement() {
        return tableRowElement.getElements();
    }
}
