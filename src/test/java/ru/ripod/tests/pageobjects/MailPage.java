package ru.ripod.tests.pageobjects;


import java.util.HashMap;

public class MailPage extends BasicPage {
    private String newLetterButtonXpath = "//div[@role='button'][@gh='cm']";
    private String receiverEmailFieldXpath = "//textarea[@name='to']";
    private String themeInputFieldXpath = "//input[@name='subjectbox']";
    private String bodyInputFieldXpath = "//div[@role = 'textbox']";
    private String newLetterWindowTitleRuXpath = "//h2//div[text()='Написать:']//following-sibling::div";
    private String newLetterWindowTitleEnXpath = "//h2//div[text()='Compose:']//following-sibling::div";
    private String newLetterWindowCloseRuXpath = "//img[@aria-label='Сохранить и закрыть']";
    private String newLetterWindowCloseEnXpath = "//img[@aria-label='Save & close']";
    private String partPageXpath = "//a[@title='%s']";
    private String filledEmailFieldXpath = "//span[contains(@email,'@')][contains(text(),'@')]";
    private String sendButtonRuXpath = "//div[text()='Отправить']";
    private String sendButtonEnXpath = "//div[text()='Send']";
    private String accountButton = "//a[contains(@aria-label, 'Аккаунт Google:')]";
    private String signOutButtonXpath = "//div[@aria-label='Информация об аккаунте']//a[text()='Выйти']";


    public MailPage(String browserName) {
        super(browserName);
        elements = new HashMap<>();
        switch (locale) {
            case ("en"):
                elements.put("Заголовок", newLetterWindowTitleEnXpath);
                elements.put("Закрыть", newLetterWindowCloseEnXpath);
                elements.put("Отправить", sendButtonEnXpath);
                elements.put("Сохранение", "Draft saved");
                break;
            default:
                elements.put("Заголовок", newLetterWindowTitleRuXpath);
                elements.put("Закрыть", newLetterWindowCloseRuXpath);
                elements.put("Отправить", sendButtonRuXpath);
                elements.put("Сохранение", "Черновик сохранен");
                break;
        }
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
        driver.waitText(elements.get("Заголовок"), elements.get("Сохранение"));
        driver.click(elements.get("Закрыть"));
    }

    public void openMailsPagePart(String partName) {
        driver.click(String.format(partPageXpath, partName));
    }

    public void checkLetterCreateEditIsOpened() {
        driver.checkElementIsPresent(elements.get("Заголовок"));
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
        driver.click(elements.get("Отправить"));
    }

    public void pressSignOutButton() {
        driver.click(accountButton);
        driver.click(signOutButtonXpath);
    }

}
