package ru.ripod.tests.stepDefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import ru.ripod.tests.pageobjects.AuthorizationPage;
import ru.ripod.tests.pageobjects.BasicPage;
import ru.ripod.tests.pageobjects.SearchPage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class StepDefinition {
    private static ThreadLocal<String> usedBrowser = new ThreadLocal<>();
    private Logger logger;


    private BasicPage basicPage;
    private SearchPage searchPage;
    private AuthorizationPage authorizationPage;

    public static void setUsedBrowser(String browserName){
        usedBrowser.set(browserName);
    }

    /**
     * Инициализация браузера и базовой страницы
     */
    @Before
    public void browserInit(){
        basicPage = new BasicPage(usedBrowser.get());
        searchPage = new SearchPage(usedBrowser.get());
        authorizationPage = new AuthorizationPage(usedBrowser.get());
        logger = LogManager.getLogger(usedBrowser.get());
    }

    @Когда("открываем страницу {string} в браузере")
    public void openPageInBrowser(String url) {
        basicPage.openPage(url);
        logger.info("Открыта страница " + url);
    }


    @After
    public void closeBrowser(){
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
        String login = credProperties.getProperty(usedBrowser.get()+"login");
    }
}
