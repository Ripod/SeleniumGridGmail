package ru.ripod.tests.pageobjects;

public class SearchPage extends BasicPage {

    private String headerButtonXpath = "//div[@id='gb']//a[text()='Почта' or text()='Gmail']";
    private String signInXpath = "//ul[@class = 'h-c-header__cta-list header__nav--ltr']//a[contains(text(),'Войти')]";

    public SearchPage(String browserName) {
        super(browserName);
    }

    public void clickHeaderButton(String buttonName) {
        driver.click(headerButtonXpath);
    }

    public void clickSignInButton() {
        driver.click(signInXpath);
    }


}
