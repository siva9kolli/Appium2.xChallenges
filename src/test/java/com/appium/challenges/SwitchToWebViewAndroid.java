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
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import static java.util.Collections.singletonList;

public class SwitchToWebViewAndroid {
    AndroidDriver androidDriver;

    @Test
    public void switchToWebViewTest() throws MalformedURLException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options();
        uiAutomator2Options.setAutomationName("UiAutomator2");
        uiAutomator2Options.setPlatformVersion("12.0");
        uiAutomator2Options.setPlatformName("Android");
        uiAutomator2Options.setDeviceName("Google Pixel 5");
       /* uiAutomator2Options.setUdid("192.168.0.105:5555");
        desiredCapabilities.setCapability("appPackage","naukriApp.appModules.login");
        desiredCapabilities.setCapability("appWaitActivity","naukriApp.appModules.login.com.naukri.splash.SplashActivity");
        uiAutomator2Options.setApp("bs://b2ca1a84bac4228dba1d1667f63cd6a9791c4281");*/
        uiAutomator2Options.setApp(System.getProperty("user.dir") + "/src/main/resources/naukri.apk");

        /*HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("appiumVersion", "2.0.0");
        desiredCapabilities.setCapability("bstack:options", browserstackOptions);*/
        uiAutomator2Options.merge(desiredCapabilities);
       // androidDriver = new AndroidDriver(new URL("https://sivareddy_cnbmzj:HCpYpbNsUFNNsByoRp3S@hub.browserstack.com/wd/hub"), uiAutomator2Options);
        androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), uiAutomator2Options);
        //androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(androidDriver, Duration.ofSeconds(10));
        Thread.sleep(7000);
        androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
        for (int i = 0; i < 5; i++) {
            try {
                androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Learn more\")")).isDisplayed();
                break;
            } catch (NoSuchElementException nse) {
                scrollToBottomLatest();
            }
        }
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageButton\")"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().text(\"Naukri blog\")"))).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//*[@resource-id='site-main']/descendant::android.view.View[@content-desc])[1]"))).click();

        switchToWebView();
        androidDriver.activateApp("com.android.chrome");
        switchToWebView();
        String url = androidDriver.getCurrentUrl();
        System.out.println("URL --- " + url);
        String heading = androidDriver.findElement(AppiumBy.cssSelector(".post-title.post-full-title")).getText();
        System.out.println(heading);

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

    public void scrollDown() {
        Dimension size = androidDriver.manage().window().getSize();
        int StartX = size.getWidth() / 2;
        int endX = StartX;

        int StartY = (int) (size.getHeight() * 0.90);
        int endY = (int) (size.getHeight() * 0.20);

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "Fingername");
        Sequence sequence = new Sequence(finger1, 1)
                .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), StartX, StartY))
                .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger1, Duration.ofMillis(200)))
                .addAction(finger1.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        androidDriver.perform(Collections.singletonList(sequence));
    }

    public void scrollToElement() {
        androidDriver.findElement(AppiumBy
                .androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"Learn more\"))"));
    }


    public void switchToWebView() throws InterruptedException {
        Thread.sleep(5000);
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
