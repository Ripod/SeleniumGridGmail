package ru.ripod.tests;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import io.qameta.allure.Epic;
import org.testng.annotations.BeforeTest;
import ru.ripod.tests.stepdefs.StepDefinition;

/**
 * Запуск тестов в firefox
 */
@Epic("Запуск firefox")
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "ru.ripod.tests.stepdefs"
)
public class FirefoxRunner extends AbstractTestNGCucumberTests {
    @BeforeTest
    public void setup() {
        StepDefinition.setUsedBrowser("firefox");
    }
}
