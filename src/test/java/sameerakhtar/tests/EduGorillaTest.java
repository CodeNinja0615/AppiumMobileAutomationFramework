package sameerakhtar.tests;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class EduGorillaTest {
	public AppiumDriverLocalService service;
	public AndroidDriver driver;

	@BeforeTest
	public void setup() throws MalformedURLException, URISyntaxException {
		Map<String, String> env = new HashMap<>(System.getenv());
		env.put("ANDROID_HOME", "/Users/sameerakhtar/Library/Android/sdk");
		env.put("ANDROID_SDK_ROOT", "/Users/sameerakhtar/Library/Android/sdk");

		service = new AppiumServiceBuilder().usingDriverExecutable(new File("/opt/homebrew/opt/node@22/bin/node")) // Explicit
																													// Node.js
																													// path
				.withAppiumJS(new File("/opt/homebrew/lib/node_modules/appium/build/lib/main.js")) // Appium path
				.withEnvironment(env) // Pass environment variables explicitly
				.withIPAddress("127.0.0.1").usingPort(4723)
				.withArgument(() -> "--allow-insecure", "chromedriver_autodownload") // --Adding to handle web context
				.build();

		service.start();
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("emulator-5554");
		options.setPlatformName("Android");
		options.setNoReset(true); // ----- set true else app will be reset on start
		options.setAppWaitForLaunch(true);
		options.setGpsEnabled(true);
		options.autoGrantPermissions();
//		options.setApp("/Users/sameerakhtar/Downloads/edugorilla.interviewapp.apk");
		driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.unlockDevice();
		driver.activateApp("com.edugorilla.interviewapp");
	}

	@Test
	public void loginTest() throws InterruptedException, IOException {
		scrollToEndAction();
		scrollToEndAction();
		scrollToEndAction();
		scrollToEndAction();
		WebElement loginBtn = driver.findElement(AppiumBy.xpath("//android.widget.Button[@resource-id='com.edugorilla.interviewapp:id/btnGotoLogin']"));
//		scrollToEndAction(loginBtn);
		loginBtn.click();
		driver.findElement(AppiumBy.id("com.edugorilla.interviewapp:id/edtUsername")).sendKeys("admin");
		driver.findElement(AppiumBy.id("com.edugorilla.interviewapp:id/edtPassword")).sendKeys("password123");
		driver.findElement(AppiumBy.id("com.edugorilla.interviewapp:id/btnLogin")).click();
		Boolean tickDisplayed = driver.findElement(AppiumBy.id("com.edugorilla.interviewapp:id/ivTick")).isDisplayed();
		Assert.assertTrue(tickDisplayed);
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(" success_login.png.png"));
	}

	@Test
	public void invalidLoginTest() throws InterruptedException, IOException {
		Thread.sleep(5000);
		scrollToEndAction();
		scrollToEndAction();
		scrollToEndAction();
		scrollToEndAction();
		WebElement loginBtn = driver.findElement(AppiumBy.xpath("//android.widget.Button[@resource-id='com.edugorilla.interviewapp:id/btnGotoLogin']"));
//		scrollToEndAction(loginBtn);
		loginBtn.click();
		driver.findElement(AppiumBy.id("com.edugorilla.interviewapp:id/edtUsername")).sendKeys("wronguser");
		driver.findElement(AppiumBy.id("com.edugorilla.interviewapp:id/edtPassword")).sendKeys("wrongpass");
		driver.findElement(AppiumBy.id("com.edugorilla.interviewapp:id/btnLogin")).click();
		String toastMsg = driver.findElement(AppiumBy.xpath("//android.widget.Toast[1]")).getDomAttribute("name");
		Assert.assertEquals("Invalid Credentials", toastMsg);
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(" failed_login.png.png"));
	}

	public void scrollToEndAction(WebElement element) {
		boolean canScrollMore;
		do {
			canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap
					.of("left", 100, "top", 100, "width", 200, "height", 200, "direction", "left", "percent", 3.0));
		} while (canScrollMore);
	}
	public void scrollToEndAction() throws InterruptedException {
//		((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of("left", 300, "top", 200,
//				"width", 300, "height", 300, "direction", "right", "percent", 20.0));
		// Java
		Thread.sleep(1000);
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
		    "left", 100, "top", 100, "width", 200, "height", 200,
		    "direction", "left",
		    "percent", 0.75
		));

	}

	@AfterTest
	public void teardown() {
		driver.terminateApp("com.edugorilla.interviewapp");
		driver.close();
	}
}
