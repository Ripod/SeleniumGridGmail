package ru.ripod.tests.pageobjects;

import io.qameta.allure.Attachment;
import org.testng.annotations.AfterMethod;

import java.util.HashMap;
import java.util.Map;

public class AuthorizationPage extends BasicPage {

    private String loginFieldCSS = "input#identifierId";
    private String nextButtonRuXpath = "//span[text() = 'Далее']";
    private String nextButtonEnXpath = "//span[text() = 'Next']";
    private String passwordXpath = "//input[@name = 'password']";
    private String passwordCSS = "input[name = 'password']";
    private String continueButton = "//input[@id = 'submitChallenge']";

    public AuthorizationPage(String browserName) {
        super(browserName);
        elements = new HashMap<>();
        switch (locale){
            case ("ru"):
                elements.put("Далее", nextButtonRuXpath);
                break;
            case ("en"):
                elements.put("Далее", nextButtonEnXpath);
                break;
        }
        elements.put("Продолжить", continueButton);
    }

    public void inputLogin(String login) {
        driver.sendKeysByCSS(loginFieldCSS, login);
    }

    public void switchToNextTab() {
        driver.switchToNextTab();
    }

    public void pressButton(String buttonName) {
        driver.click(elements.get(buttonName));
    }

    public void inputPassword(String password) {
        driver.sendKeysByCSS(passwordCSS, password);
    }

    public void checkSignedOut() {
        driver.checkElementIsPresent(passwordXpath);
    }
}
