package ru.ripod.tests.driverwrappers;

import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class FirefoxSingletonDriver extends RemoteSingletonDriver {

    private static FirefoxSingletonDriver firefoxSingletonDriver;

    public static FirefoxSingletonDriver getInstance() {
        if (firefoxSingletonDriver == null) {
            firefoxSingletonDriver = new FirefoxSingletonDriver();
        }
        return firefoxSingletonDriver;
    }

    private FirefoxSingletonDriver() {
        try {
            InputStream propInputStream = new FileInputStream("selenium.config");
            properties.load(propInputStream);
        } catch (IOException e) {
            infoLogger.warn("Problem reading properties file");
        }
        System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.gecko.driver", "bin/geckodriver.exe"));
        boolean remoteFlag = Boolean.valueOf(properties.getProperty("remote", "false"));
        if (remoteFlag) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setPlatform(Platform.WIN10);
            capabilities.setBrowserName("firefox");
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
            remoteWebDriver = new FirefoxDriver();
        }
        remoteWebDriver.manage().window().maximize();
        remoteWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(remoteWebDriver, 5, 250);
    }
}

