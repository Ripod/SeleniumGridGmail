package ru.ripod.tests.pageobjects;

public class AuthorizationPage extends BasicPage{

    String loginFieldXpath = "//input[@id = 'identifierId']";
    String buttonXpath = "//span[text() = '%s']";
    String passwordXpath = "//input[@name = 'password']";

    public AuthorizationPage(String browserName) {
        super(browserName);
    }

    public void inputLogin(String login){
        driver.sendKeys(loginFieldXpath, login);
    }

    public void  switchToNextTab(){
        driver.switchToNextTab();
    }

    public void pressButton(String buttonName){
        driver.click(String.format(buttonXpath, buttonName));
    }

    public void inputPassword(String password){
        driver.sendKeys(passwordXpath, password);
    }
}
