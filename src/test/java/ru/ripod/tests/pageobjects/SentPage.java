package ru.ripod.tests.pageobjects;

public class SentPage extends BasicPage {

    private String sentMailXpath = "//tr//span[@class = 'bog']/span[text() = '%s']";

    public SentPage(String browserName) {
        super(browserName);
    }

    public void checkSentMailIsVisible(String mailTheme) {
        driver.checkElementIsPresent(String.format(sentMailXpath, mailTheme));
    }
}
