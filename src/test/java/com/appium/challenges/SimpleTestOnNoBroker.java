package com.appium.challenges;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SimpleTestOnNoBroker {

    AndroidDriver androidDriver;

    @Test
    public void searchLocation() throws MalformedURLException {
        UiAutomator2Options automator2Options = new UiAutomator2Options();
        automator2Options.setPlatformName("Android");
        automator2Options.setPlatformVersion("12.0");
        automator2Options.setAutomationName("UiAutomator2");
        automator2Options.setDeviceName("Samsung");
        automator2Options.setAppPackage("com.nobroker.app");
        automator2Options.setAppWaitActivity("com.nobroker.app.activities.NBLauncherActivity");

        automator2Options.setApp(System.getProperty("user.dir")+"/src/main/resources/NoBroker.apk");

        androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), automator2Options);
        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Continue\")")).click();
        androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"While using the app\")")).click();
        androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Allow\")")).click();
        androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Allow\")")).click();
        androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*searchEditHome\")")).click();

        ////*[@resource-id='android:id/text1']
        //new UiSelector().text("Hyderabad")

    }

}
