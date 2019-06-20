package ru.ripod.tests.driverwrappers;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ChromeSingletonDriver extends RemoteSingletonDriver{

    private static ChromeSingletonDriver chromeSingletonDriver;

    public static ChromeSingletonDriver getInstance(){
        if(chromeSingletonDriver == null){
            chromeSingletonDriver = new ChromeSingletonDriver();
        }
        return chromeSingletonDriver;
    }

    private ChromeSingletonDriver(){
        try {
            InputStream propInputStream = new FileInputStream("selenium.config");
            properties.load(propInputStream);
        } catch (IOException e) {
            infoLogger.warn("Problem reading properties file");
        }
        System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver", "bin/chromedriver.exe"));
        boolean remoteFlag = Boolean.valueOf(properties.getProperty("remote", "false"));
        if (remoteFlag) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setPlatform(Platform.WIN10);
            capabilities.setBrowserName("chrome");
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
            remoteWebDriver = new ChromeDriver();
        }
        remoteWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(remoteWebDriver, 5, 250);
    }



}
