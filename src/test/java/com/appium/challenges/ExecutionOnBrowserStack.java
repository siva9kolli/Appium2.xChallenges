package com.appium.challenges;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class ExecutionOnBrowserStack {
    AndroidDriver androidDriver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() throws MalformedURLException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options();
        uiAutomator2Options.setAutomationName("UiAutomator2");
        uiAutomator2Options.setPlatformVersion("12.0");
        uiAutomator2Options.setPlatformName("Android");
        uiAutomator2Options.setDeviceName("Google Pixel 5");
        //uiAutomator2Options.setApp(System.getProperty("user.dir")+"/src/main/resources/NoBroker.apk");
        uiAutomator2Options.setApp("bs://8db1bebd3507ab1eb0fb5e857fdb9fcab3428019");
        uiAutomator2Options.setAppPackage("com.nobroker.app");
        uiAutomator2Options.setAppWaitActivity("com.nobroker.app.activities.NBLauncherActivity");


        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("appiumVersion", "2.0.0");
        desiredCapabilities.setCapability("bstack:options", browserstackOptions);
        uiAutomator2Options.merge(desiredCapabilities);
        androidDriver = new AndroidDriver(new URL("https://sivareddy_cnbmzj:HCpYpbNsUFNNsByoRp3S@hub.browserstack.com/wd/hub"), uiAutomator2Options);
        //androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), uiAutomator2Options);
        wait = new WebDriverWait(androidDriver, Duration.ofSeconds(30));
        Thread.sleep(5000);
    }

    @Test
    public void searchLocation(){

        wait
                .until(ExpectedConditions
                        .elementToBeClickable(AppiumBy
                                .xpath("//*[ends-with(@resource-id,'yesPhoneState')]")))
                .click();

        //*[@text='Allow']
        wait
                .until(ExpectedConditions
                        .elementToBeClickable(AppiumBy
                                .androidUIAutomator("new UiSelector().text(\"While using the app\")")))
                .click();
        wait
                .until(ExpectedConditions
                        .elementToBeClickable(AppiumBy
                                .androidUIAutomator("new UiSelector().text(\"Allow\")")))
                .click();

        wait
                .until(ExpectedConditions
                        .elementToBeClickable(AppiumBy
                                .androidUIAutomator("new UiSelector().text(\"Allow\")")))
                .click();

        wait
                .until(ExpectedConditions
                        .elementToBeClickable(AppiumBy
                                .id("searchEditHome")))
                .click();

        wait
                .until(ExpectedConditions
                        .elementToBeClickable(AppiumBy
                                .className("android.widget.Spinner")))
                .click();

        wait
                .until(ExpectedConditions
                        .elementToBeClickable(AppiumBy
                                .androidUIAutomator("new UiSelector().text(\"Hyderabad\")")))
                .click();

        wait
                .until(ExpectedConditions
                        .elementToBeClickable(AppiumBy
                                .androidUIAutomator("new UiSelector().resourceId(\"com.nobroker.app:id/localityAutoCompleteTxt\")")))
                .sendKeys("Dallas Centre");

        androidDriver.pressKey(new KeyEvent(AndroidKey.TAB));
        //androidDriver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }
}
