package ru.ripod.tests.stepDefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import ru.ripod.tests.pageobjects.BasicPage;


public class StepDefinition {
    private static ThreadLocal<String> usedBrowser = new ThreadLocal<>();
    private Logger logger;


    private BasicPage basicPage;
    public static void setUsedBrowser(String browserName){
        usedBrowser.set(browserName);
    }

    @Before
    public void browserInit(){
        basicPage = new BasicPage(usedBrowser.get());
        logger = LogManager.getLogger(usedBrowser.get());
    }

    @Когда("открываем страницу {string} в браузере")
    public void openPageInBrowser(String url) {
        basicPage.openPage(url);
        logger.info("Открыта страница " + url);
    }

    @Тогда("открывается страница поисковой системы google")
    public void checkPageIsOpened() {
        Assert.assertTrue(basicPage.checkPageShow());
        logger.info("Проверено отображения страницы");
    }

    @After
    public void closeBrowser(){
        basicPage.closeBrowser();
    }
}
