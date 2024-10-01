package com.appium.challenges;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

public class SampleTestAndroid {
    AndroidDriver androidDriver;

    /**
     * appium server --use-plugins=element-wait --allow-insecure chromedriver_autodownload
     * @throws MalformedURLException
     * @throws InterruptedException
     */
    @Test
    public void switchToChrome() throws MalformedURLException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options();
        uiAutomator2Options.setAutomationName("UiAutomator2");
        uiAutomator2Options.setPlatformVersion("14.0");
        uiAutomator2Options.setPlatformName("Android");
        uiAutomator2Options.setDeviceName("SamSung");
        uiAutomator2Options.setUdid("10BE170QKL001EY");
        //uiAutomator2Options.setAppPackage("com.google.android.apps.messaging");
       // uiAutomator2Options.setAppWaitActivity("com.google.android.apps.messaging.ui.ConversationListActivity");
        //desiredCapabilities.setCapability("appPackage", "com.google.android.apps.messaging");
        uiAutomator2Options.setApp("C:\\Users\\Siva\\IdeaProjects\\Appium2.xChallenges\\src\\main\\resources\\test_app.apk");
        uiAutomator2Options.merge(desiredCapabilities);
        androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), uiAutomator2Options);
        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        Thread.sleep(5000);

        //androidDriver.activateApp("com.google.android.apps.messaging");

        //androidDriver.findElement(AppiumBy.className("android.widget.EditText")).click();

        androidDriver.findElement(AppiumBy.className("android.widget.EditText")).sendKeys("leo@testmail.com");
        System.out.println();

    }

    public void longPressOld(){
        Dimension size = androidDriver.manage().window().getSize();
        System.out.println("size === " + size);

        int start_x = (int) ((size.width)*(0.90));
        int end_x = (int) ((size.width)*(0.10));

        int y = (size.height)/2;

        TouchAction touchAction = new TouchAction(androidDriver);
        touchAction.longPress(PointOption.point(start_x, y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5))).perform().release();
        System.out.println("Test");
    }

    public Point getLocationOfElement(Point location, Dimension size) {
        return new Point(location.getX() + size.getWidth() / 2,
                location.getY() + size.getHeight() / 2);
    }

    public void longPressOnElement(AndroidDriver driver, WebElement element) {
        Point location = element.getLocation();
        Dimension size = element.getSize();

        Point locationOfElement =
                new Point(location.getX() + size.getWidth() / 2,
                          location.getY() + size.getHeight() / 2);

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence sequence = new Sequence(finger1, 1)
                .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), locationOfElement))
                .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger1, Duration.ofSeconds(5)))
                .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(sequence));
    }

    public void switchToWebView() {
        Set<String> contexts = androidDriver.getContextHandles();
        for (String s : contexts) {
            System.out.println("context name=======" + contexts);
            if (s.contains("WEBVIEW")) {
                System.out.println("Mobile Web View found");
                androidDriver.context(s);
            }
        }
    }

    public void switchToNativeView() {
        Set<String> contexts = androidDriver.getContextHandles();
        for (String s : contexts) {
            System.out.println("context name=======" + contexts);
            if (s.contains("NATIVE")) {
                System.out.println("Mobile Native View found");
                androidDriver.context(s);
            }
        }
    }
}
