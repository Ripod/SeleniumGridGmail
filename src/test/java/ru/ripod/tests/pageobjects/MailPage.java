package ru.ripod.tests.pageobjects;


import java.util.HashMap;

public class MailPage extends BasicPage {
    private String newLetterButtonXpath = "//div[@role='button'][@gh='cm']";
    private String receiverEmailFieldXpath = "//textarea[@name='to']";
    private String themeInputFieldXpath = "//input[@name='subjectbox']";
    private String bodyInputFieldXpath = "//div[@role = 'textbox']";
    private String newLetterWindowTitleXpath = "//h2//div[text()='Написать:' or text()='Compose:']//following-sibling::div";
    private String newLetterWindowCloseXpath = "//img[@aria-label='Сохранить и закрыть' or @aria-label='Save & close']";
    private String partPageXpath = "//a[@title='%s']";
    private String filledEmailFieldXpath = "//span[contains(@email,'@')][contains(text(),'@')]";
    private String sendButtonXpath = "//div[text()='Отправить' or text()='Send']";
    private String accountButton = "//a[contains(@aria-label, 'Аккаунт Google:')]";
    private String signOutButtonXpath = "//div[@aria-label='Информация об аккаунте']//a[text()='Выйти']";


    public MailPage(String browserName) {
        super(browserName);
    }

    public void checkPageOpened() {
        driver.checkElementIsPresent(newLetterButtonXpath);
    }

    public void pressCreateLetter() {
        driver.click(newLetterButtonXpath);
    }

    public void inputReceiverEmail(String email) {
        driver.sendKeys(receiverEmailFieldXpath, email);
    }

    public void inputMailTheme(String theme) {
        driver.sendKeys(themeInputFieldXpath, theme);
    }

    public void inputMailBody(String body) {
        driver.sendKeys(bodyInputFieldXpath, body);
    }

    public void closeWhenDraftSaved() {
        String expectedText;
        switch (locale) {
            case "en":
                expectedText = "Draft saved";
                break;
            case "ru":
            default:
                expectedText = "Черновик сохранен";
        }
        driver.waitText(newLetterWindowTitleXpath, expectedText);
        driver.click(newLetterWindowCloseXpath);
    }

    public void openMailsPagePart(String partName) {
        driver.click(String.format(partPageXpath, partName));
    }

    public void checkLetterCreateEditIsOpened() {
        driver.checkElementIsPresent(newLetterWindowTitleXpath);
    }

    public void checkEmailValue(String expectedValue) {
        driver.checkElementText(filledEmailFieldXpath, expectedValue);
    }

    public void checkThemeValue(String expectedValue) {
        driver.checkElementValue(themeInputFieldXpath, expectedValue);
    }

    public void checkBodyValue(String expectedValue) {
        driver.checkElementText(bodyInputFieldXpath, expectedValue);
    }

    public void pressSendButton() {
        driver.click(sendButtonXpath);
    }

    public void pressSignOutButton() {
        driver.click(accountButton);
        driver.click(signOutButtonXpath);
    }

}
