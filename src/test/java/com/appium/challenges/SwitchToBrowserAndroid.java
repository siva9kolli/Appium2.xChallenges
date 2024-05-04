package com.appium.challenges;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
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

import static java.util.Collections.singletonList;

public class SwitchToBrowserAndroid {
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
        uiAutomator2Options.setPlatformVersion("12.0");
        uiAutomator2Options.setPlatformName("Android");
        uiAutomator2Options.setDeviceName("SamSung");
        uiAutomator2Options.setUdid("emulator-5554");
        //uiAutomator2Options.setAppPackage("com.google.android.apps.messaging");
       // uiAutomator2Options.setAppWaitActivity("com.google.android.apps.messaging.ui.ConversationListActivity");
        desiredCapabilities.setCapability("appPackage", "com.google.android.apps.messaging");
        uiAutomator2Options.merge(desiredCapabilities);
        androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), uiAutomator2Options);
        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        Thread.sleep(5000);

        androidDriver.activateApp("com.google.android.apps.messaging");

        try {
              //androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Agree\")")).click();
        } catch (Exception e) {

        }

        androidDriver.findElement(AppiumBy.accessibilityId("Start chat")).click();

        try {
            androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*recipient_text_view\")")).sendKeys("9885952948");

        } catch (Exception e) {
            androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"ContactSearchField\")")).sendKeys("9885952948");
        }


        androidDriver.pressKey(new KeyEvent(AndroidKey.ENTER));

        androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Text message\")")).sendKeys("https://appium.io/docs/en/latest/");
        androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*send_message_button_icon\")")).click();

        androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"https://appium.io/docs/en/latest/\").instance(0)")).click();
        Thread.sleep(10000);

        androidDriver.activateApp("com.android.chrome");
        Thread.sleep(10000);
        switchToWebView();

        String url = androidDriver.getCurrentUrl();
        System.out.println("URL --- " + url);

        /*JavascriptExecutor jse = (JavascriptExecutor)androidDriver;
        WebElement closeButton = androidDriver.findElement(By.xpath("(//icon[contains(@class,'contextual')]/*[@class='artdeco-icon lazy-loaded'])[1]"));
        jse.executeScript("return arguments[0].click()", closeButton);*/

      /*String message = androidDriver.findElement(By.cssSelector("#suggestions > [href='https://githubstatus.com']")).getText();
        System.out.println(message);
        androidDriver.findElement(By.id("email-or-phone")).sendKeys("9885952948");*/
        Thread.sleep(1000);
        androidDriver.activateApp("com.google.android.apps.messaging");
        Thread.sleep(10000);
        System.out.println("After switching to Message App");
        switchToNativeView();
        String msg = androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"https://appium.io/docs/en/latest/\")")).getText();
        System.out.println(msg);

        androidDriver.findElement(AppiumBy.accessibilityId("Navigate up")).click();
        longPressOnElement(androidDriver, androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceIdMatches(\".*conversation_name\")")));
        androidDriver.findElement(AppiumBy.accessibilityId("Delete")).click();
        androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Delete\")")).click();
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
