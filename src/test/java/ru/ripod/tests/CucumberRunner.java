package ru.ripod.tests;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

@Test
@CucumberOptions(features = "src/test/resources/features", glue = "stepDefs")
public class CucumberRunner  extends AbstractTestNGCucumberTests {
}
