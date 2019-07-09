package ru.ripod.tests.pageobjects;

import io.qameta.allure.Attachment;
import org.testng.annotations.AfterMethod;

public class SearchPage extends BasicPage {

    private String headerButtonRuXpath = "//div[@id='gb']//a[text()='Почта']";
    private String headerButtonEnXpath = "//div[@id='gb']//a[text()='Gmail']";
    private String signInXpath = "//ul[@class = 'h-c-header__cta-list header__nav--ltr']//a[contains(text(),'Sign in')]";

    public SearchPage(String browserName) {
        super(browserName);
        switch (locale){
            case ("ru"):
                elements.put("Почта", headerButtonRuXpath);
                break;
            case ("en"):
                elements.put("Почта", headerButtonEnXpath);
                break;
        }
    }

    public void clickHeaderButton(String buttonName) {
        driver.click(elements.get(buttonName));
    }

    public void clickSignInButton() {
        driver.click(signInXpath);
    }


}
