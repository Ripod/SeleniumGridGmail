package ru.ripod.tests.pageobjects;

public class AuthorizationPage extends BasicPage{

    private String loginFieldXpath = "//input[@id = 'identifierId']";
    private String loginFieldCSS = "input#identifierId";
    private String buttonXpath = "//span[text() = '%s']";
    private String passwordXpath = "//input[@name = 'password']";
    String passwordCSS = "input[name = 'password']";

    public AuthorizationPage(String browserName) {
        super(browserName);
    }

    public void inputLogin(String login){
        driver.sendKeysByCSS(loginFieldCSS, login);
    }

    public void  switchToNextTab(){
        driver.switchToNextTab();
    }

    public void pressButton(String buttonName){
        driver.click(String.format(buttonXpath, buttonName));
    }

    public void inputPassword(String password){
        driver.sendKeysByCSS(passwordCSS, password);
    }
}
