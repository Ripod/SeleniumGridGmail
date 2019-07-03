package ru.ripod.tests.pageobjects;


import io.qameta.allure.Attachment;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.ripod.tests.driverwrappers.ChromeSingletonDriver;
import ru.ripod.tests.driverwrappers.FirefoxSingletonDriver;
import ru.ripod.tests.driverwrappers.RemoteSingletonDriver;
@Test
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

    @AfterMethod(alwaysRun = true)
    @Attachment
    public byte[] takeScreenshot(){
        return driver.takeScreenshot();
    }

    public void closeBrowser() {
        driver.close();
    }


}
