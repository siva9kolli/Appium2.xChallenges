package com.appium.challenges;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.By;
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

public class TBSwitchToBrowserIOS {

    public IOSDriver iosDriver;

    @Test
    public void switchToBrowser() throws MalformedURLException, InterruptedException {
        XCUITestOptions xcuiTestOptions = new XCUITestOptions();
        xcuiTestOptions.setAutomationName("XCUITest");
        xcuiTestOptions.setPlatformVersion("17.2");
        xcuiTestOptions.setPlatformName("iOS");
        xcuiTestOptions.setDeviceName("iPhone 15 Pro");
        xcuiTestOptions.setCapability("includeSafariInWebviews", true);
        xcuiTestOptions.autoAcceptAlerts();
        xcuiTestOptions.setApp(System.getProperty("user.dir") + "/src/main/resources/TestBrigade.app");
        iosDriver = new IOSDriver(new URL("http://127.0.0.1:4723"), xcuiTestOptions);
        iosDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        iosDriver.findElement(AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeTextField'")).clear();
        iosDriver.findElement(AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeTextField'")).sendKeys("skolli@testbrigade.com");
        iosDriver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeSecureTextField[$value == 'password'$]")).sendKeys("test@123");
        iosDriver.findElement(AppiumBy.iOSNsPredicateString("name == 'Return'")).click();
        iosDriver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeButton[$name == 'Submit'$]")).click();

        Thread.sleep(2000);
        switchToView("WEBVIEW");
        iosDriver.activateApp("com.apple.mobilesafari");
        //iosDriver.executeScript("mobile: launchApp", ImmutableMap.of("bundleId", "com.Zaggle.staging"));
        Thread.sleep(2000);
        switchToView("WEBVIEW");
        Thread.sleep(2000);
        System.out.println(iosDriver.getCurrentUrl());

        iosDriver.activateApp("com.testbrigade.ios");
        switchToView("NATIVE_APP");
        String message = iosDriver.findElement(AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeImage'")).getAttribute("name");
        System.out.println(message);
    }

    public void switchToView(String view){
        Set<String> contexts = iosDriver.getContextHandles();

        switch (view){
            case "NATIVE_APP":
                System.out.println("Mobile Native View found");
                iosDriver.context("NATIVE_APP");
                break;
            case"WEBVIEW":
                for (String s : contexts) {
                    if (s.contains("WEBVIEW")) {
                        System.out.println("s value :: " + s);
                        System.out.println("Mobile Web View found");
                        iosDriver.context(s);
                    }
                }
                break;
        }
    }
}


