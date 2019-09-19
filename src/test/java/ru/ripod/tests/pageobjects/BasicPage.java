package ru.ripod.tests.pageobjects;


import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ripod.tests.driverwrappers.RemoteSingletonDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class BasicPage {
    private Logger logger;

    protected static String locale;
    protected RemoteSingletonDriver driver;


    public BasicPage(String browserName) {
        driver = RemoteSingletonDriver.getInstance(browserName);
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

    public void closeBrowser() {
        driver.close();
    }

    @Attachment("Скриншот после шага")
    public byte[] takeScreenshot(){
        return driver.takeScreenshot();
    }
}
