package com.appium.challenges;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

public class SwitchToBrowserIOS {

    public IOSDriver iosDriver;

    @Test
    public void SwitchToBrowser() throws MalformedURLException, InterruptedException {
        XCUITestOptions xcuiTestOptions = new XCUITestOptions();
        xcuiTestOptions.setAutomationName("XCUITest");
        xcuiTestOptions.setPlatformVersion("17.2");
        xcuiTestOptions.setPlatformName("iOS");
        xcuiTestOptions.setDeviceName("iPhone 15 Pro");
        //xcuiTestOptions.autoWebview();
      //  xcuiTestOptions.autoWebview();
        xcuiTestOptions.setCapability("includeSafariInWebviews", true);
        //xcuiTestOptions.setCapability("allowProvisioningUpdates", true);

        xcuiTestOptions.autoAcceptAlerts();
        xcuiTestOptions.setApp(System.getProperty("user.dir") + "/src/main/resources/Zaggle.app");
        iosDriver = new IOSDriver(new URL("http://127.0.0.1:4723"), xcuiTestOptions);
        iosDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        iosDriver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[$label == 'Sign Up'$]")).click();
        iosDriver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[$label == 'Privacy Policy'$]")).click();
        Thread.sleep(3000);
        switchToWebView();
        iosDriver.activateApp("com.apple.mobilesafari");
        //iosDriver.executeScript("mobile: launchApp", ImmutableMap.of("bundleId", "com.apple.mobilesafari"));
        Thread.sleep(2000);
        switchToWebView();
        Thread.sleep(2000);
        System.out.println(iosDriver.getCurrentUrl());

        iosDriver.activateApp("com.Zaggle.staging");
        switchToNativeView();
        String message = iosDriver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[$label == 'Privacy Policy'$]")).getText();
        System.out.println(message);
    }
    
    public void scrollTest(){
        Dimension size = iosDriver.manage().window().getSize();
        int StartX = size.getWidth() / 2;
        int StartY = size.getHeight() / 2;
        int endX = StartX;
        int endY = (int) (size.getHeight() * 0.25);

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "Fingername");

        Sequence sequence = new Sequence(finger1, 1)
                .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), StartX, StartY))
                .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger1, Duration.ofMillis(200)))
                .addAction(finger1.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        iosDriver.perform(Collections.singletonList(sequence));
    }


    public void switchToWebView() {
        Set<String> contexts = iosDriver.getContextHandles();
        for (String s : contexts) {
            System.out.println("context name=======" + contexts);
            if (s.contains("WEBVIEW")) {
                System.out.println("Mobile Web View found");
                iosDriver.context(s);
            }
        }
    }

    public void switchToNativeView(){
        Set<String> contexts = iosDriver.getContextHandles();
        for (String s : contexts) {
            System.out.println("context name=======" + contexts);
            if (s.contains("NATIVE")) {
                System.out.println("Mobile Native View found");
                iosDriver.context(s);
            }
        }
    }
}


