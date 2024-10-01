import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static java.util.Collections.singletonList;

public class LaunchAndroidApplication {
    /**
     *  AndoridDriver
     *
     *  - Platform name - Andorid/iOS
     *  - Platform verison - 13.0/14.0
     *  - Device name - Samsung
     *  - Automation name - UiAutomator2/XCUItest
     *  - app - .apk/.ipa/.app
     *  - .apk : android - Emulators/Real Devices
     *  - .ipa : iOS - only real devices
     *  - .app : iOS - work for both simulators
     *
     */

    AndroidDriver androidDriver;

    @Test
    public void launchApp() throws MalformedURLException, InterruptedException {
        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options();
        uiAutomator2Options.setPlatformVersion("14.0");
        uiAutomator2Options.setPlatformName("Android");
        uiAutomator2Options.setDeviceName("Samsung");
        uiAutomator2Options.setAutomationName("UiAutomator2");
        uiAutomator2Options.setApp("C:\\Users\\Siva\\IdeaProjects\\Appium2.xChallenges\\src\\main\\resources\\app-debug.apk");
        androidDriver = new AndroidDriver(new URL(" http://127.0.0.1:4723"), uiAutomator2Options);

        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        WebDriverWait wait = new WebDriverWait(androidDriver, Duration.ofSeconds(20));
        androidDriver.findElement(AppiumBy.className("android.widget.EditText")).click();

        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.className("android.widget.EditText"))).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.className("android.widget.EditText"))).sendKeys("test.com");
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.className("android.widget.CheckBox"))).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Continue"))).click();
        Thread.sleep(4000);
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//android.widget.EditText)[2]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("(//android.widget.EditText)[2]"))).sendKeys("pwd");
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Login"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(1)"))).click();

        //Task Name
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)"))).sendKeys("AutoTestTask");

        // Priority Radio Button
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("(//*[contains(@content-desc,'Priority')]/android.widget.RadioButton)[1]"))).click();

        // Details - write a note
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)"))).sendKeys("AutoWriteANote");

        //RoleBase Only

        //wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Role-based only\\nYes"))).click();

        // Category
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc,'Category')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Accounting"))).click();

        // Sub-Category
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc,'Subcategory')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Accounting"))).click();

        // Recurrence
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//*[contains(@content-desc,'Recurrence')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Daily"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Submit"))).click();




    }

    public void scrollToBottomLatest() {
        Dimension size = androidDriver.manage().window().getSize();
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

        androidDriver.perform(singletonList(scroll));
    }
}
