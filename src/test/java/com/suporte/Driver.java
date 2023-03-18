package com.suporte;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Driver {

    private static String url = PropertiesCache.getInstance().getProperty("url");
    private static String chromeDriverPath = PropertiesCache.getInstance().getProperty("chrome.driver.path");
    public static WebDriver createChromeDriver(){
        System.setProperty("webdriver.chrome.driver" , chromeDriverPath);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.MINUTES);
        driver.get(url);

        return driver;
    }
}
