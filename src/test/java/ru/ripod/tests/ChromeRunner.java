package ru.ripod.tests;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import io.qameta.allure.Epic;
import org.testng.annotations.BeforeTest;
import ru.ripod.tests.stepDefs.StepDefinition;

/**
 * Запуск тестов на chrome
 */
@Epic("Запуск chrome")
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "ru.ripod.tests.stepDefs"
)
public class ChromeRunner extends AbstractTestNGCucumberTests {

    @BeforeTest
    public void setup() {
        StepDefinition.setUsedBrowser("chrome");
    }
}
