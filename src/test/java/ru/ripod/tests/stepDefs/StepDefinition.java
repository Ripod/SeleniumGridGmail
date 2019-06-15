package ru.ripod.tests.stepDefs;

import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;

public class StepDefinition {

    @Когда("открываем страницу {string} в браузере")
    public void openPageInBrowser(String arg0) {
    }

    @Тогда("открывается страница поисковой системы google")
    public void checkPageIsOpened() {
    }
}
