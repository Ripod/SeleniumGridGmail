package ru.ripod.tests.pageobjects;

import io.qameta.allure.Attachment;
import org.testng.annotations.AfterMethod;

public class SearchPage extends BasicPage {

    private String headerButtonXpath = "//div[@id='gb']//a[text()='%s']";
    private String signInXpath = "//ul[@class = 'h-c-header__cta-list header__nav--ltr']//a[contains(text(),'Sign in')]";

    public SearchPage(String browserName) {
        super(browserName);
    }

    public void clickHeaderButton(String buttonName) {
        driver.click(String.format(headerButtonXpath, buttonName));
    }

    public void clickSignInButton() {
        driver.click(signInXpath);
    }


}
