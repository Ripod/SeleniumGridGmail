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
 * Запуск тестов на chrome
 */
@Epic("Запуск chrome")
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "ru.ripod.tests.stepdefs"
)
public class ChromeRunner extends AbstractTestNGCucumberTests {

    @BeforeTest
    public void setup() {
        StepDefinition.setUsedBrowser("chrome");
    }

    @Override
    @Test(description = "Запуск теста на chrome", dataProvider = "scenarios")
    public void runScenario(PickleEventWrapper pickleWrapper, CucumberFeatureWrapper featureWrapper) throws Throwable {
        super.runScenario(pickleWrapper, featureWrapper);
    }
}
