package ru.ripod.tests.pageobjects;


import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.ripod.tests.driverwrappers.ChromeSingletonDriver;
import ru.ripod.tests.driverwrappers.FirefoxSingletonDriver;
import ru.ripod.tests.driverwrappers.RemoteSingletonDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Test
public class BasicPage {
    private Logger logger;

    protected static String locale;
    protected Map<String,String> elements;
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
        logger = LogManager.getLogger(browserName);
        Properties credProperties = new Properties();
        try {
            InputStream credInputStream = new FileInputStream("credential.config");
            credProperties.load(credInputStream);
        } catch (IOException e) {
            logger.warn("Problem reading properties file");
        }
        locale = credProperties.getProperty("locale", "ru");
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
