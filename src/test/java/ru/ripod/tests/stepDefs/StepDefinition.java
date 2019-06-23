package ru.ripod.tests.stepDefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ripod.tests.pageobjects.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;


public class StepDefinition {
    //thread locals for variables used throughout while test
    private static ThreadLocal<String> usedBrowser = new ThreadLocal<>();
    private static ThreadLocal<String> mailDate = new ThreadLocal<>();
    private static ThreadLocal<String> mailTheme = new ThreadLocal<>();
    private static ThreadLocal<String> mailBody = new ThreadLocal<>();
    private Logger logger;

    //page object declaration
    private BasicPage basicPage;
    private SearchPage searchPage;
    private AuthorizationPage authorizationPage;
    private MailPage mailPage;
    private DraftPage draftPage;

    public static void setUsedBrowser(String browserName) {
        usedBrowser.set(browserName);
    }

    /**
     * Инициализация браузера и базовой страницы
     */
    @Before
    public void browserInit() {
        basicPage = new BasicPage(usedBrowser.get());
        searchPage = new SearchPage(usedBrowser.get());
        authorizationPage = new AuthorizationPage(usedBrowser.get());
        mailPage = new MailPage(usedBrowser.get());
        draftPage = new DraftPage(usedBrowser.get());
        logger = LogManager.getLogger(usedBrowser.get());
    }

    @Когда("открываем страницу {string} в браузере")
    public void openPageInBrowser(String url) {
        basicPage.openPage(url);
        logger.info("Открыта страница " + url);
    }


    @After
    public void closeBrowser() {
        basicPage.closeBrowser();
    }

    @И("нажимаем кнопку {string} на главной странице")
    public void pressMailButtonSearchPage(String buttonName) {
        searchPage.clickHeaderButton(buttonName);
    }

    @И("нажимаем кнопку \"Войти\"")
    public void clickSignInButton() {
        searchPage.clickSignInButton();
    }


    @И("вводим логин из файла {string}")
    public void inputLoginFromFile(String fileName) {
        Properties credProperties = new Properties();
        try {
            InputStream credInputStream = new FileInputStream(fileName);
            credProperties.load(credInputStream);
        } catch (IOException e) {
            logger.warn("Problem reading properties file");
        }
        String login = credProperties.getProperty(usedBrowser.get() + "login");
        authorizationPage.switchToNextTab();
        authorizationPage.inputLogin(login);
    }

    @И("нажимаем кнопку {string} на странице авторизации")
    public void pressAuthPageButton(String buttonName) {
        authorizationPage.pressButton(buttonName);
    }

    @И("вводим пароль из файла {string}")
    public void inputPasswordFromFile(String fileName) {
        Properties credProperties = new Properties();
        try {
            InputStream credInputStream = new FileInputStream(fileName);
            credProperties.load(credInputStream);
        } catch (IOException e) {
            logger.warn("Problem reading properties file");
        }
        String password = credProperties.getProperty(usedBrowser.get() + "password");
        authorizationPage.inputPassword(password);
    }

    @Тогда("открывается главная страница почты")
    public void checkMailPage() {
        mailPage.checkPageOpened();
    }

    @Когда("нажимаем кнопку создания письма")
    public void pressButtonCreateLetter() {
        mailPage.pressCreateLetter();
    }

    @И("указываем получателя из файла {string}")
    public void inputReceiverEmailFromFile(String fileName) {
        Properties credProperties = new Properties();
        try {
            InputStream credInputStream = new FileInputStream(fileName);
            credProperties.load(credInputStream);
        } catch (IOException e) {
            logger.warn("Problem reading properties file");
        }
        String email = credProperties.getProperty(usedBrowser.get() + "receiver");
        mailPage.inputReceiverEmail(email);
    }

    @И("указываем тему с указанием даты и времени")
    public void inputThemeWithCurrentTime() {
        Date mailDateRaw = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm::ss", Locale.UK);
        mailDate.set(dateFormat.format(mailDateRaw));
        mailTheme.set(String.format("%s mail %s", usedBrowser.get(), mailDate.get()));
        mailPage.inputMailTheme(mailTheme.get());
    }

    @И("вводим текст письма")
    public void inputMailBody() {
        String bodyBase = "Это письмо написано в браузере %s. Дата и время: %s";
        mailBody.set(String.format(bodyBase, usedBrowser.get(), mailDate.get()));
        mailPage.inputMailBody(mailBody.get());
    }


    @И("закрываем окно создания письма")
    public void closeNewLetterWindow() {
        mailPage.closeWhenDraftSaved();
    }

    @И("открываем страницу {string} в почте")
    public void openPagePartInMail(String partName) {
        mailPage.openMailsPagePart(partName);
    }

    @Тогда("в списке писем содержится созданный нами черновик")
    public void checkCreatedDraftVisible() {
        draftPage.checkCreatedDraftVisible(mailTheme.get());
    }

    @Когда("нажимаем на созданный черновик")
    public void openCreatedDraft() {
        draftPage.openCreatedDraft(mailTheme.get());
    }

    @Тогда("открывается окно создания или редактирования письма")
    public void checkLetterCreateEditIsOpened() {
        mailPage.checkLetterCreateEditIsOpened();
    }

    @И("в поле получателя указан адрес из файла {string}")
    public void checkReceiverEmailFromFile(String fileName) {
        Properties credProperties = new Properties();
        try {
            InputStream credInputStream = new FileInputStream(fileName);
            credProperties.load(credInputStream);
        } catch (IOException e) {
            logger.warn("Problem reading properties file");
        }
        String email = credProperties.getProperty(usedBrowser.get() + "receiver");
        mailPage.checkEmailValue(email);
    }

    @И("тема письма соответствует теме созданного черновика")
    public void checkMailTheme() {
        mailPage.checkThemeValue(mailTheme.get());
    }

    @И("текст письма соответствует тексту созданного письма")
    public void checkMailText() {
        mailPage.checkBodyValue(mailBody.get());
    }
}
