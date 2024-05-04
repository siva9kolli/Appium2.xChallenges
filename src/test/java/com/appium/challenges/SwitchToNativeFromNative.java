package com.appium.challenges;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static java.util.Collections.singletonList;

public class SwitchToNativeFromNative {
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
        uiAutomator2Options.setApp(System.getProperty("user.dir")+"/src/main/resources/NoBroker.apk");
        uiAutomator2Options.setAppPackage("com.nobroker.app");
        uiAutomator2Options.setAppWaitActivity("com.nobroker.app.activities.NBLauncherActivity");
        uiAutomator2Options.merge(desiredCapabilities);
        androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), uiAutomator2Options);
        wait = new WebDriverWait(androidDriver, Duration.ofSeconds(30));
        Thread.sleep(5000);
    }

    public void scrollToBottomLatest() {
        Dimension size = androidDriver.manage().window().getSize();
        int start_y = (int) ((size.height) * (0.80));
        int end_y = (int) ((size.height) * (0.20));
        int x = (size.width) / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scroll = new Sequence(finger, 1);

        scroll.addAction(
                finger.createPointerMove(Duration.ofMillis(0),
                        PointerInput.Origin.viewport(), x, start_y));

        scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        scroll.addAction(new Pause(finger, Duration.ofMillis(600)));
        scroll.addAction(
                finger.createPointerMove(Duration.ofMillis(600),
                        PointerInput.Origin.viewport(), x, end_y));
        scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        androidDriver.perform(singletonList(scroll));
    }

    @Test
    public void searchLocation() throws InterruptedException {

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

        for (int i = 0; i < 5; i++) {
            try {
                androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Need Assistance?\")")).isDisplayed();
                androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Need Assistance?\")")).click();
                break;
            } catch (NoSuchElementException nse) {
                scrollToBottomLatest();
            }
        }

        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*dialpad_floating_action_button\")"))).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("End call"))).click();
        Thread.sleep(3000);
        androidDriver.activateApp("com.nobroker.app");
        Thread.sleep(2000);
        androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Need Assistance?\")")).isDisplayed();
        String number = androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"We are just a call away\")")).getText();
        System.out.println(number);
        Thread.sleep(3000);

    }
}
