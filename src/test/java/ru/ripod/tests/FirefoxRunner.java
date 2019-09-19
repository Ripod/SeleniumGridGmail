package ru.ripod.tests;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import io.qameta.allure.Epic;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
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

    @Override
    @Test(description = "Запуск теста на firefox", dataProvider = "scenarios")
    public void runScenario(PickleEventWrapper pickleWrapper, CucumberFeatureWrapper featureWrapper) throws Throwable {
        super.runScenario(pickleWrapper, featureWrapper);
    }
}
