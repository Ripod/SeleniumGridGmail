package ru.ripod.tests.pageobjects;


import java.util.HashMap;

public class AuthorizationPage extends BasicPage {

    private String loginFieldCSS = "input#identifierId";
    private String nextButtonXpath = "//span[text() = 'Далее' or text() = 'Next']";
    private String passwordXpath = "//input[@name = 'password']";
    private String passwordCSS = "input[name = 'password']";
    private String accountTileEmail = "//div[@id = 'profileIdentifier' and @data-email = '%s']";

    public AuthorizationPage(String browserName) {
        super(browserName);
    }

    public void inputLogin(String login) {
        driver.sendKeysByCSS(loginFieldCSS, login);
    }

    public void switchToNextTab() {
        driver.switchToNextTab();
    }

    public void pressButton(String buttonName) {
        driver.click(nextButtonXpath);
    }

    public void inputPassword(String password) {
        driver.sendKeysByCSS(passwordCSS, password);
    }

    public void checkSignedOut(String email) {
        driver.checkElementIsPresent(String.format(accountTileEmail, email));
    }
}
