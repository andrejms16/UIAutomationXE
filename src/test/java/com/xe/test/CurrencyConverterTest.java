package com.xe.test;

import com.xe.data.ConversionDAO;
import com.xe.model.Conversion;
import com.xe.page.CurrencyConverterPage;
import com.xe.support.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
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

    @DataProvider(name = "conversions")
    public static Object[] ConversionsData() throws IOException {
        return new ConversionDAO().getCurrencies().toArray();
    };


    @DataProvider(name = "conversion")
    public static Object[][] Conversion() throws IOException {
        return new Object[][]{{new ConversionDAO().getCurrencies().get(0)}};
    }

    @Test(dataProvider = "conversions")
    public void getCurrencyConversionQuote(Conversion conversion) {

        fillCurrencyConversion(conversion.getAmount(),conversion.getCurrencies()[0].getSymbol(),conversion.getCurrencies()[1].getSymbol());

        Double amount = extractAmountValue(currencyConverterPage.getAmountInputValue());
        String fromCurrencyDescription = extractCurrencyDescription(currencyConverterPage.getFromCurrencyValue());
        String toCurrencyDescription = extractCurrencyDescription(currencyConverterPage.getToCurrencyValue());

        currencyConverterPage.ConvertButtonClick();

        verifyConversionResult(amount,fromCurrencyDescription,toCurrencyDescription);
    }

    @Test(dataProvider = "conversion")
    public void swapCurrencyConversion(Conversion conversion) {

        fillCurrencyConversion(conversion.getAmount(),conversion.getCurrencies()[0].getSymbol(),conversion.getCurrencies()[1].getSymbol());

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