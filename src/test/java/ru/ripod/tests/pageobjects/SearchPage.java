package ru.ripod.tests.pageobjects;

public class SearchPage extends BasicPage {

    private String headerButtonXpath = "//div[@id='gb']//a[text()='%s']";
    private String signInXpath = "//ul[@class = 'h-c-header__cta-list header__nav--ltr']//a[contains(text(),'Войти')]";
    private String accountButton = "//a[contains(@aria-label, 'Аккаунт Google:')]";
    private String signOutButtonXpath = "//div[@aria-label='Информация об аккаунте']//a[text()='Выйти']";

    public SearchPage(String browserName) {
        super(browserName);
    }

    public void clickHeaderButton(String buttonName){
        driver.click(String.format(headerButtonXpath, buttonName));
    }

    public void clickSignInButton(){
        driver.click(signInXpath);
    }


    public void pressSignOutButton(){
        driver.click(accountButton);
        driver.click(signOutButtonXpath);
    }


}
