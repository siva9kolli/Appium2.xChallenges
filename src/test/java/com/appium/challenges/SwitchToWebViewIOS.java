package com.appium.challenges;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

import static java.util.Collections.singletonList;

public class SwitchToWebViewIOS {
    public IOSDriver iosDriver;

    @Test
    public void SwitchToWebView() throws MalformedURLException, InterruptedException {
        XCUITestOptions xcuiTestOptions = new XCUITestOptions();
        xcuiTestOptions.setAutomationName("XCUITest");
        xcuiTestOptions.setPlatformVersion("17.2");
        xcuiTestOptions.setPlatformName("iOS");
        xcuiTestOptions.setDeviceName("iPhone 15 Pro");
        xcuiTestOptions.setCapability("includeSafariInWebviews", true);
        xcuiTestOptions.autoAcceptAlerts();
        xcuiTestOptions.setApp(System.getProperty("user.dir") + "/src/main/resources/UICatalog.app");
        iosDriver = new IOSDriver(new URL("http://127.0.0.1:4723"), xcuiTestOptions);

        WebDriverWait wait = new WebDriverWait(iosDriver, Duration.ofSeconds(10));

        for (int i = 0; i < 5; i++) {
            try {
                iosDriver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[$value == 'Web View'$]")).click();
                break;
            } catch (NoSuchElementException nse) {
                scrollToBottomLatest();
            }
        }

        Thread.sleep(5000);
        switchToWebView();
        String message = iosDriver.findElement(By.cssSelector(".ac-ls-copy")).getText();
        System.out.println(message);
    }


    public void scrollToBottomLatest() {
        Dimension size = iosDriver.manage().window().getSize();
        int start_y = (int) ((size.height) * (0.70));
        int end_y = (int) ((size.height) * (0.30));
        int x = (size.width) / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scroll = new Sequence(finger, 1);
        scroll.addAction(
                finger.createPointerMove(
                        Duration.ofMillis(0),
                        PointerInput.Origin.viewport(),
                        x, start_y));

        scroll.addAction(
                finger.createPointerDown(
                        PointerInput.MouseButton.LEFT.asArg()));

        scroll.addAction(new Pause(finger, Duration.ofMillis(600)));

        scroll.addAction(
                finger.createPointerMove(Duration.ofMillis(600),
                        PointerInput.Origin.viewport(),
                        x, end_y));

        scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        iosDriver.perform(singletonList(scroll));
    }


    public void switchToWebView() {
        for (int j=0; j<50; j++){
            System.out.println("Iteration :: " + j);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (iosDriver.getContextHandles().size() == 2){
                break;
            }
        }

        Set<String> contexts = iosDriver.getContextHandles();
        for (String s : contexts) {
            if (s.contains("WEBVIEW")) {
                System.out.println("Mobile Web View found");
                iosDriver.context(s);
            }
        }
    }
}
