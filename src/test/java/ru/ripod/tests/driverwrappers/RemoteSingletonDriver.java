package ru.ripod.tests.driverwrappers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Абстрактная синглтон обертка для WebDriver-а
 */
public class RemoteSingletonDriver {
    private RemoteWebDriver remoteWebDriver;
    private static ThreadLocal<RemoteSingletonDriver> remoteSingletonDriver = new ThreadLocal<>();
    private WebDriverWait wait;
    Properties properties = new Properties();

    public void openPage(String url) {
        remoteWebDriver.get("http://" + url);
    }

    Logger infoLogger;

    public static RemoteSingletonDriver getInstance(String browserName) {
        if (remoteSingletonDriver.get() == null) {
            remoteSingletonDriver.set(new RemoteSingletonDriver(browserName));
        }
        return remoteSingletonDriver.get();
    }

    private RemoteSingletonDriver(String browserName) {
        infoLogger = LogManager.getLogger(browserName);
        try {
            InputStream propInputStream = new FileInputStream("selenium.config");
            properties.load(propInputStream);
        } catch (IOException e) {
            infoLogger.warn("Problem reading properties file");
        }
        System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver", "bin/chromedriver.exe"));
        System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.gecko.driver", "bin/geckodriver.exe"));
        boolean remoteFlag = Boolean.valueOf(properties.getProperty("remote", "false"));
        if (remoteFlag) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browserName);
            String baseUrl = properties.getProperty("wdhost", "127.0.0.1");
            String port = properties.getProperty("wdport", "4444");
            String fullURL = String.format("http://%s:%s/wd/hub", baseUrl, port);
            try {
                remoteWebDriver = new RemoteWebDriver(new URL(fullURL), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                infoLogger.error("wrong URL format");
            }
        } else {
            switch (browserName) {
                case "firefox":
                    remoteWebDriver = new FirefoxDriver();
                case "chrome":
                default:
                    remoteWebDriver = new ChromeDriver();
            }
        }
        remoteWebDriver.manage().window().maximize();
        remoteWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(remoteWebDriver, 5, 250);
    }

    private WebElement getElementByXpath(String xpath) {
        By xpathSelector = By.xpath(xpath);
        return Objects.requireNonNull(elementReceiving(xpathSelector));
    }

    private WebElement getElementByCSS(String CSS) {
        By cssSelector = By.cssSelector(CSS);
        return Objects.requireNonNull(elementReceiving(cssSelector));
    }

    private WebElement elementReceiving(By selector) {
        WebElement element;
        for (int i = 0; i < 5; i++) {
            try {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(selector));
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
                element = wait.until(ExpectedConditions.elementToBeClickable(selector));
                return element;
            } catch (StaleElementReferenceException e) {
                infoLogger.info("Stale exception", e);
            } catch (Exception exception) {
                infoLogger.info("Caught exception", exception);
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
        windows.stream().filter(o -> !o.equals(remoteWebDriver.getWindowHandle()))
                .findFirst().map(o -> remoteWebDriver.switchTo().window(o));

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

    public byte[] takeScreenshot() {
        return remoteWebDriver.getScreenshotAs(OutputType.BYTES);
    }

    public void close() {
        remoteWebDriver.quit();
    }
}
