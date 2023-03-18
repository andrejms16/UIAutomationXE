package com.xe.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CurrencyConverterPage {
    private WebDriver driver;
    private By amountInput = By.id("amount");
    private By campaignPopup = By.tagName("yld-tag-host-campaign");

    private By fromCurrencySelect = By.id("midmarketFromCurrency");

    private By fromCurrencySelectText = By.id("midmarketFromCurrency-descriptiveText");

    private By toCurrencySelect = By.id("midmarketToCurrency");

    private By toCurrencySelectText = By.id("midmarketToCurrency-descriptiveText");

    private By swapCurrenciesButton = By.xpath("//button[@aria-label='Swap currencies']");
    private By convertButton = By.xpath("//button[text()='Convert']");

    private By resultHeader = By.xpath("//main/div/div[2]/div/p");

    private By resultAmount = By.xpath("//main/div/div[2]/div/p[2]");

    private By toCurrencyQuote = By.xpath("//main/div/div[2]/div/div/p[1]");

    private By trackCurrencyButton = By.xpath("//button[text()='Track currency']");

    private By viewTransferQuoteButton = By.xpath("//a[text()='View transfer quote']");


    public CurrencyConverterPage(WebDriver driver){
        this.driver = driver;
    }

    public CurrencyConverterPage typeAmount(String amount){
        driver.findElement(amountInput).sendKeys(amount);
        driver.findElement(amountInput).sendKeys(Keys.TAB);
        return this;
    }

    public CurrencyConverterPage closePopup(){
        driver.findElement(campaignPopup).sendKeys(Keys.ESCAPE);
        return this;
    }

    public String getAmountInputValue(){
        return driver.findElement(amountInput).getAttribute("value");
    }

    public CurrencyConverterPage selectFromCurrency(String currency){
        driver.findElement(fromCurrencySelect).click();
        driver.findElement(fromCurrencySelect).sendKeys(currency);
        driver.findElement(fromCurrencySelect).sendKeys(Keys.ENTER);

        return this;
    }

    public String getFromCurrencyValue(){
        return driver.findElement(fromCurrencySelectText).getText();
    }

    public CurrencyConverterPage selectToCurrency(String currency){
        driver.findElement(toCurrencySelect).click();
        driver.findElement(toCurrencySelect).sendKeys(currency);
        driver.findElement(toCurrencySelect).sendKeys(Keys.ENTER);

        return this;
    }

    public String getToCurrencyValue(){
        return driver.findElement(toCurrencySelectText).getText();
    }

    public CurrencyConverterPage ConvertButtonClick(){
        driver.findElement(convertButton).click();
        return this;
    }

    public CurrencyConverterPage SwapCurrenciesButtonClick(){
        WebElement swapButton =  new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(swapCurrenciesButton));
        swapButton.click();
        return this;
    }

    public boolean trackCurrencyButtonVisible(){
        return driver.findElement(trackCurrencyButton).isDisplayed();
    }

    public boolean viewTransferQuoteButtonVisible(){
        return driver.findElement(viewTransferQuoteButton).isDisplayed();
    }

    public String getResultHeader(){
        return driver.findElement(resultHeader).getText();
    }

    public String getResultAmount(){
        return driver.findElement(resultAmount).getText();
    }

    public String getToCurrencyQuote(){
        return driver.findElement(toCurrencyQuote).getText();
    }
}
