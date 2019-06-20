package ru.ripod.tests.pageobjects;

import cucumber.api.java.Before;
import org.testng.annotations.Test;
import ru.ripod.tests.driverwrappers.ChromeSingletonDriver;
import ru.ripod.tests.driverwrappers.FirefoxSingletonDriver;
import ru.ripod.tests.driverwrappers.RemoteSingletonDriver;

public class BasicPage {

    protected RemoteSingletonDriver driver;
    private String loginXpath = "//a[@id = 'gb_70']";

    public BasicPage(String browserName){
        switch (browserName){
            case("chrome"):
                driver = ChromeSingletonDriver.getInstance();
                break;
            case("firefox"):
                driver = FirefoxSingletonDriver.getInstance();
                break;
        }
    }

    public void openPage(String url){
        driver.openPage(url);
    }

    public boolean checkPageShow(){
        return driver.getElement(loginXpath) != null;
    }

    public void closeBrowser(){
        driver.close();
    }
}
