package ru.ripod.tests.pageobjects;

import io.qameta.allure.Attachment;
import org.testng.annotations.AfterMethod;

public class SentPage extends BasicPage {

    String sentMailXpath = "//tr//span[@class = 'bog']/span[text() = '%s']";

    @AfterMethod(alwaysRun = true)
    @Attachment
    public byte[] takeScreenshot(){
        return driver.takeScreenshot();
    }

    public SentPage(String browserName) {
        super(browserName);
    }

    public void checkSentMailIsVisible(String mailTheme) {
        driver.checkElementIsPresent(String.format(sentMailXpath, mailTheme));
    }
}
