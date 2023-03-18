package com.test;

import com.xe.page.CurrencyConverterPage;
import com.suporte.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;


public class CurrencyConverterTest {

    private WebDriver driver;
    private CurrencyConverterPage currencyConverterPage;

    @BeforeMethod
    public void setUp(){
        driver = Driver.createChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

        currencyConverterPage = new CurrencyConverterPage(driver);
        currencyConverterPage.closePopup();
    }

    @DataProvider(name = "test1")
    public static Object[][] ExchangeData() {
        return new Object[][] {{10000.0 , "USD", "GBP"}, {11000.0 , "EUR", "GBP"}, {12000.0 , "CAD", "GBP"}, {13000.0 , "AUD", "CHF"}, {14000.0 , "NOK", "CHF"}};
    }

    @DataProvider(name = "test2")
    public static Object[][] Exchange() {
        return new Object[][] {{10000.0 , "USD", "GBP"}};
    }

    @Test(dataProvider = "test1")
    public void getCurrencyConversionQuote(Double amountInput, String currencyFrom, String currencyTo) {

        fillCurrencyConversion(amountInput,currencyFrom,currencyTo);

        Double amount = extractAmountValue(currencyConverterPage.getAmountInputValue());
        String fromCurrencyDescription = extractCurrencyDescription(currencyConverterPage.getFromCurrencyValue());
        String toCurrencyDescription = extractCurrencyDescription(currencyConverterPage.getToCurrencyValue());

        currencyConverterPage.ConvertButtonClick();

        verifyConversionResult(amount,fromCurrencyDescription,toCurrencyDescription);
    }

    @Test(dataProvider = "test2")
    public void swapCurrencyConversion(Double amountInput, String currencyFrom, String currencyTo) {

        fillCurrencyConversion(amountInput,currencyFrom,currencyTo);

        Double amount = extractAmountValue(currencyConverterPage.getAmountInputValue());
        String fromCurrencyDescription = extractCurrencyDescription(currencyConverterPage.getFromCurrencyValue());
        String toCurrencyDescription = extractCurrencyDescription(currencyConverterPage.getToCurrencyValue());

        currencyConverterPage.ConvertButtonClick();
        currencyConverterPage.SwapCurrenciesButtonClick();

        verifyConversionResult(amount,toCurrencyDescription,fromCurrencyDescription);
    }

    private void fillCurrencyConversion (Double amountInput, String currencyFrom, String currencyTo){
        currencyConverterPage.typeAmount(amountInput.toString());
        currencyConverterPage.selectFromCurrency(currencyFrom);
        currencyConverterPage.selectToCurrency(currencyTo);
    }

    private void verifyResultHeader (Double amount, String fromCurrencyDescription){
        String resultHeader = currencyConverterPage.getResultHeader();
        Assert.assertTrue(resultHeader.replace(",","").contains(amount.toString()));
        Assert.assertTrue(resultHeader.contains(fromCurrencyDescription));
    }

    private void verifyConversionResult (Double amount, String fromCurrencyDescription, String toCurrencyDescription){
        verifyResultHeader(amount, fromCurrencyDescription);
        verifyConvertedAmount(toCurrencyDescription);
        verifyConversionOptionButtons();

        Double convertedAmount = extractConvertedAmount();
        Double value = amount * extractConversionQuote();

        Assert.assertTrue(Math.abs(value - convertedAmount) < 1);
    }

    private void verifyConvertedAmount(String toCurrencyDescription){
        Assert.assertTrue(currencyConverterPage.getResultAmount().contains(toCurrencyDescription));
    }

    private void verifyConversionOptionButtons(){
        Assert.assertTrue(currencyConverterPage.trackCurrencyButtonVisible());
        Assert.assertTrue(currencyConverterPage.viewTransferQuoteButtonVisible());
    }

    private Double extractConvertedAmount(){
        return Double.valueOf(currencyConverterPage.getResultAmount().replace(",","").split(" ")[0]);
    }

    private Double extractAmountValue(String amount){
        return Double.valueOf(amount.substring(0,amount.trim().length()).replace(",",""));
    }

    private Double extractConversionQuote(){
        String currencyExchange = currencyConverterPage.getToCurrencyQuote();
        return Double.valueOf(currencyConverterPage.getToCurrencyQuote().substring(currencyExchange.indexOf('=') + 1, currencyExchange.length()-4).replace(",",""));
    }

    private String extractCurrencyDescription(String currency){
        return currency.substring(6,currency.trim().length());
    }

    @AfterMethod
    public void closeWebDriver(){
        driver.quit();
    }
}