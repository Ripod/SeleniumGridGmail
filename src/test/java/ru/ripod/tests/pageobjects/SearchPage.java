package ru.ripod.tests.pageobjects;

import java.util.HashMap;

public class SearchPage extends BasicPage {

    private String headerButtonRuXpath = "//div[@id='gb']//a[text()='Почта']";
    private String headerButtonEnXpath = "//div[@id='gb']//a[text()='Gmail']";
    private String signInXpath = "//ul[@class = 'h-c-header__cta-list header__nav--ltr']//a[contains(text(),'Войти')]";
    private String signInEnXpath = "//ul[@class = 'h-c-header__cta-list header__nav--ltr']//a[contains(text(),'Sign in')]";

    public SearchPage(String browserName) {
        super(browserName);
        elements = new HashMap<>();
        switch (locale){
            case ("ru"):
                elements.put("Почта", headerButtonRuXpath);
                elements.put("Войти", signInXpath);
                break;
            case ("en"):
                elements.put("Почта", headerButtonEnXpath);
                elements.put("Войти", signInEnXpath);
                break;
        }
    }

    public void clickHeaderButton(String buttonName) {
        driver.click(elements.get(buttonName));
    }

    public void clickSignInButton(String buttonName) {
        driver.click(elements.get(buttonName));
    }


}
