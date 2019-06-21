package ru.ripod.tests.driverwrappers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;
import java.util.Properties;

public abstract class RemoteSingletonDriver {
    protected RemoteWebDriver remoteWebDriver;
    protected WebDriverWait wait;
    Properties properties = new Properties();

    public void openPage(String url){
        remoteWebDriver.get("http://" + url);
    }
    Logger infoLogger;
    private WebElement getElement(String xpath){
        WebElement element = null;
        for(int i = 0; i < 5; i++){
            try{
                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
                element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                return element;
            } catch (StaleElementReferenceException e){
                infoLogger.error("Stale exception" + e.toString());
            } catch (Exception exception){
                infoLogger.error("Caught exception" + exception.toString());
            }
        }
        return null;
    }

    public void click(String xpath){
        Objects.requireNonNull(getElement(xpath)).click();
    }

    public void sendKeys(String xpath, String value){
        Objects.requireNonNull(getElement(xpath)).sendKeys(value);
    }

    public void close(){
        remoteWebDriver.quit();
    }
}
