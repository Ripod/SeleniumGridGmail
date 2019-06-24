package ru.ripod.tests.pageobjects;


import ru.ripod.tests.driverwrappers.ChromeSingletonDriver;
import ru.ripod.tests.driverwrappers.FirefoxSingletonDriver;
import ru.ripod.tests.driverwrappers.RemoteSingletonDriver;

public class BasicPage {

    protected RemoteSingletonDriver driver;

    public BasicPage(String browserName) {
        switch (browserName) {
            case ("chrome"):
                driver = ChromeSingletonDriver.getInstance();
                break;
            case ("firefox"):
                driver = FirefoxSingletonDriver.getInstance();
                break;
        }
    }

    public void openPage(String url) {
        driver.openPage(url);
    }


    public void closeBrowser() {
        driver.close();
    }


}
