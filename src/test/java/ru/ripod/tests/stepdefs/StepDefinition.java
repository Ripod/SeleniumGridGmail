package ru.ripod.tests.stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ripod.tests.pageobjects.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;


public class StepDefinition {
    //thread locals for variables used throughout whole test

    private static class TestDataManager {
        private static ThreadLocal<HashMap<String, String>> storage = new ThreadLocal<>();
        private static Logger managerLogger = LogManager.getLogger();

        public static void init(){
            storage.set(new HashMap<>());
        }

        public static String getValue(String key) {

            String value = storage.get().get(key);
            if(value != null) {
                managerLogger.info("Получение данных по ключу {}. Получено значение {}", key, value);
                return value;
            }else{
                managerLogger.warn("Получение данных по ключу {}. Значение отсутствует или пустое" , key);
                return value;
            }
        }

        public static void setValue(String key, String value){
            String prevValue = storage.get().put(key, value);
            if(prevValue == null){
                managerLogger.info("Добавлены данные. Ключ: {}, значение: {}",key, value);
            }else {
                managerLogger.info("Заменены данные. Ключ: {}, прошлое значение: {}, новое значение: {}", key, prevValue, value);
            }

        }
    }


    private Logger logger;

    //page object declaration
    private BasicPage basicPage;
    private SearchPage searchPage;
    private AuthorizationPage authorizationPage;
    private MailPage mailPage;
    private DraftPage draftPage;
    private SentPage sentPage;

    public static void setUsedBrowser(String browserName) {
        TestDataManager.init();
        TestDataManager.setValue("usedBrowser", browserName);
    }

    public static String getUsedBrowser() {
        return TestDataManager.getValue("usedBrowser");
    }

    /**
     * Инициализация браузера и страничных объектов
     */
    @Step("Инициализация браузера и страничных объектов")
    @Before
    public void browserInit() {
        String browserString = getUsedBrowser();
        basicPage = new BasicPage(browserString);
        searchPage = new SearchPage(browserString);
        authorizationPage = new AuthorizationPage(browserString);
        mailPage = new MailPage(browserString);
        draftPage = new DraftPage(browserString);
        sentPage = new SentPage(browserString);
        logger = LogManager.getLogger(browserString);
    }

    @Step("Скриншот после шага")
    @AfterStep
    public void attachScreenshotAsStep() {
        basicPage.takeScreenshot();
    }

    @Step(value = "Открытие страницы {0}")
    @Когда("открываем страницу {string} в браузере")
    public void openPageInBrowser(String url) {
        basicPage.openPage(url);
    }

    @Step("Нажатие кнопки {0} на главной странице")
    @И("нажимаем кнопку {string} на главной странице")
    public void pressMailButtonSearchPage(String buttonName) {
        searchPage.clickHeaderButton(buttonName);
    }

    @Step("Нажатие кнопки \"Войти\"")
    @И("нажимаем кнопку \"Войти\"")
    public void clickSignInButton() {
        searchPage.clickSignInButton();
    }

    @Step("Ввод логина из файла \"{0}\"")
    @И("вводим логин из файла {string}")
    public void inputLoginFromFile(String fileName) {
        Properties credProperties = new Properties();
        try {
            InputStream credInputStream = new FileInputStream(fileName);
            credProperties.load(credInputStream);
        } catch (IOException e) {
            logger.warn("Problem reading properties file");
        }
        TestDataManager.setValue("login", credProperties.getProperty(getUsedBrowser() + "login"));
        authorizationPage.switchToNextTab();
        authorizationPage.inputLogin(TestDataManager.getValue("login"));
    }

    @Step("Нажатие кнопки \"{0}\" на странице авторизации")
    @И("нажимаем кнопку {string} на странице авторизации")
    public void pressAuthPageButton(String buttonName) {
        authorizationPage.pressButton(buttonName);
    }

    @Step("Ввод пароля из файла \"{0}\"")
    @И("вводим пароль из файла {string}")
    public void inputPasswordFromFile(String fileName) {
        Properties credProperties = new Properties();
        try {
            InputStream credInputStream = new FileInputStream(fileName);
            credProperties.load(credInputStream);
        } catch (IOException e) {
            logger.warn("Problem reading properties file");
        }
        String password = credProperties.getProperty(getUsedBrowser() + "password");
        authorizationPage.inputPassword(password);
    }

    @Step("Проверка открытия страницы почты")
    @Тогда("открывается главная страница почты")
    public void checkMailPage() {
        mailPage.checkPageOpened();
    }

    @Step("Нажатие кнопки создания письма")
    @Когда("нажимаем кнопку создания письма")
    public void pressButtonCreateLetter() {
        mailPage.pressCreateLetter();
    }

    @Step("Ввод получателя из файла {0}")
    @И("указываем получателя из файла {string}")
    public void inputReceiverEmailFromFile(String fileName) {
        Properties credProperties = new Properties();
        try {
            InputStream credInputStream = new FileInputStream(fileName);
            credProperties.load(credInputStream);
        } catch (IOException e) {
            logger.warn("Problem reading properties file");
        }
        String email = credProperties.getProperty(getUsedBrowser() + "receiver");
        mailPage.inputReceiverEmail(email);
    }

    @Step("Ввод темы письма")
    @И("указываем тему с указанием даты и времени")
    public void inputThemeWithCurrentTime() {
        Date mailDateRaw = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
        TestDataManager.setValue("mailDate", dateFormat.format(mailDateRaw));
        TestDataManager.setValue("mailTheme",String.format("%s mail %s", getUsedBrowser(), TestDataManager.getValue("mailDate")));
        mailPage.inputMailTheme(TestDataManager.getValue("mailTheme"));
    }

    @Step("Ввод текста письма")
    @И("вводим текст письма")
    public void inputMailBody() {
        String bodyBase = "Это письмо написано в браузере %s. Дата и время: %s";
        TestDataManager.setValue("mailBody", String.format(bodyBase, getUsedBrowser(), TestDataManager.getValue("mailDate")));
        mailPage.inputMailBody(TestDataManager.getValue("mailBody"));
    }

    @Step("Закрытие окна создания письма")
    @И("закрываем окно создания письма")
    public void closeNewLetterWindow() {
        mailPage.closeWhenDraftSaved();
    }

    @Step("Открытие страницы \"{0}\" в почте")
    @И("открываем страницу {string} в почте")
    public void openPagePartInMail(String partName) {
        mailPage.openMailsPagePart(partName);
    }

    @Step("Проверка наличия созданного черновика")
    @Тогда("в списке писем содержится созданный нами черновик")
    public void checkCreatedDraftVisible() {
        draftPage.checkCreatedDraftVisible(TestDataManager.getValue("mailTheme"));
    }

    @Step("Выбор созданного черновика")
    @Когда("нажимаем на созданный черновик")
    public void openCreatedDraft() {
        draftPage.openCreatedDraft(TestDataManager.getValue("mailTheme"));
    }

    @Step("Проверка открытия окна создания письма")
    @Тогда("открывается окно создания или редактирования письма")
    public void checkLetterCreateEditIsOpened() {
        mailPage.checkLetterCreateEditIsOpened();
    }

    @Step("Проверка адреса получателя из файла \"{0}\"")
    @И("в поле получателя указан адрес из файла {string}")
    public void checkReceiverEmailFromFile(String fileName) {
        Properties credProperties = new Properties();
        try {
            InputStream credInputStream = new FileInputStream(fileName);
            credProperties.load(credInputStream);
        } catch (IOException e) {
            logger.warn("Problem reading properties file");
        }
        String email = credProperties.getProperty(getUsedBrowser() + "receiver");
        mailPage.checkEmailValue(email);
    }

    @Step("Проверка темы письма созданного черновика")
    @И("тема письма соответствует теме созданного черновика")
    public void checkMailTheme() {
        mailPage.checkThemeValue(TestDataManager.getValue("mailTheme"));
    }

    @Step("Проверка текста письма созданного черновика")
    @И("текст письма соответствует тексту созданного письма")
    public void checkMailText() {
        mailPage.checkBodyValue(TestDataManager.getValue("mailBody"));
    }

    @Step("Отправка созданного черновика")
    @Когда("нажимаем кнопку \"Отправить\" в окне нового письма")
    public void pressSendInMailWindow() {
        mailPage.pressSendButton();
    }

    @Step("Проверка отсутствия отправленного письма в списке черновиков")
    @Тогда("в списке черновиков не содержится созданный нами черновик")
    public void checkDraftNotShown() {
        draftPage.checkCreatedDraftNotVisible(TestDataManager.getValue("mailTheme"));
    }

    @Step("Проверка наличия отправленного письма в списке отправленных писем")
    @Тогда("в списке писем содержится отправленное нами письмо")
    public void checkSentLetterIsShown() {
        sentPage.checkSentMailIsVisible(TestDataManager.getValue("mailTheme"));
    }

    @Step("Выход из аккаунта")
    @Когда("нажимаем на кнопку аккаунта и нажимаем \"Выйти\"")
    public void pressSignOutButton() {
        mailPage.pressSignOutButton();
    }

    @Step("Проверка выхода из аккаунта")
    @Тогда("выходим из аккаунта")
    public void checkSignedOut() {
        authorizationPage.checkSignedOut(TestDataManager.getValue("login"));
    }

    @Step("Закрытие браузера")
    @After
    public void closeBrowser() {
        basicPage.closeBrowser();
    }

}
