package ru.ripod.tests.driverwrappers;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * Абстрактная синглтон обертка для WebDriver-а
 */
public abstract class RemoteSingletonDriver {
    protected RemoteWebDriver remoteWebDriver;
    protected WebDriverWait wait;
    Properties properties = new Properties();

    public void openPage(String url) {
        remoteWebDriver.get("http://" + url);
    }

    Logger infoLogger;


    private WebElement getElementByXpath(String xpath) {
        By xpathSelector = By.xpath(xpath);
        return Objects.requireNonNull(elementReceiving(xpathSelector));
    }

    private WebElement getElementByCSS(String CSS) {
        By cssSelector = By.cssSelector(CSS);
        return Objects.requireNonNull(elementReceiving(cssSelector));
    }

    private WebElement elementReceiving(By selector){
        WebElement element ;
        for (int i = 0; i < 5; i++) {
            try {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(selector));
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
                element = wait.until(ExpectedConditions.elementToBeClickable(selector));
                return element;
            } catch (StaleElementReferenceException e) {
                infoLogger.error("Stale exception" + e.toString());
            } catch (Exception exception) {
                infoLogger.error("Caught exception" + exception.toString());
            }
        }
        return null;
    }

    public void click(String xpath) {
        Objects.requireNonNull(getElementByXpath(xpath)).click();
    }

    public void sendKeys(String xpath, String value) {
        Objects.requireNonNull(getElementByXpath(xpath)).sendKeys(value);
    }

    public void sendKeysByCSS(String CSS, String value) {
        Objects.requireNonNull(getElementByCSS(CSS)).sendKeys(value);
    }

    public void switchToNextTab() {
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windows = remoteWebDriver.getWindowHandles();
        for (String id :
                windows) {
            if (!id.equals(remoteWebDriver.getWindowHandle())) {
                remoteWebDriver.switchTo().window(id);
                break;
            }
        }
    }

    public void checkElementIsPresent(String xpath) {
        Assert.assertNotNull(getElementByXpath(xpath));
    }

    public void checkElementNotPresent(String xpath) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
        } catch (TimeoutException exception) {
            Assert.fail("Элемент по локатору xpath виден", exception);
        }
    }

    public void waitText(String xpath, String text) {
        wait.until(ExpectedConditions.textToBe(By.xpath(xpath), text));
    }

    public void checkElementText(String xpath, String expectedValue) {
        String actualValue = Objects.requireNonNull(getElementByXpath(xpath)).getText();
        Assert.assertEquals(actualValue, expectedValue);
    }

    public void checkElementValue(String xpath, String expectedValue) {
        String actualValue = Objects.requireNonNull(getElementByXpath(xpath)).getAttribute("value");
        Assert.assertEquals(actualValue, expectedValue);
    }

    public byte[] takeScreenshot(){
        return remoteWebDriver.getScreenshotAs(OutputType.BYTES);
    }
    public void close() {
        remoteWebDriver.quit();
    }
}
