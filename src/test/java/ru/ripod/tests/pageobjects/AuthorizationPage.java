package ru.ripod.tests.pageobjects;

public class AuthorizationPage extends BasicPage{

    String loginFieldXpath = "//input[@id = 'identifierId']";

    public AuthorizationPage(String browserName) {
        super(browserName);
    }

    public void inputLogin(String login){
        driver.sendKeys(loginFieldXpath, login);
    }
}
