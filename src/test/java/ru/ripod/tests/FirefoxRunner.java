package ru.ripod.tests;

import cucumber.api.CucumberOptions;
import cucumber.api.java.Before;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.BeforeTest;
import ru.ripod.tests.stepDefs.StepDefinition;

/**
 * Запуск тестов в firefox
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "ru.ripod.tests.stepDefs"
)
public class FirefoxRunner extends AbstractTestNGCucumberTests {
    @BeforeTest
    public void setup() {
        StepDefinition.setUsedBrowser("firefox");
    }
}
