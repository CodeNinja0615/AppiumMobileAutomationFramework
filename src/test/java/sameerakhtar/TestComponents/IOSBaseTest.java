package sameerakhtar.TestComponents;

//----https://appium.github.io/appium-xcuitest-driver/8.3/guides/gestures/
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import sameerakhtar.pageObjects.ios.HomePage;

public class IOSBaseTest {

	public AppiumDriverLocalService service;
	public IOSDriver driver;
	public HomePage homePage;
	String packageName = "com.example.apple-samplecode.UICatalog"; // xcrun simctl listapps booted //---For Simulator
	String appPath = System.getProperty("user.dir") + "/src/main/java/sameerakhtar/resources/UIKitCatalog.app";

	@BeforeClass
	public void configureAppiumMobile() throws URISyntaxException, IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//sameerakhtar//resources//GlobalData.properties");
		prop.load(fis);
		String deviceName = System.getProperty("deviceName") != null ? System.getProperty("deviceName")
				: prop.getProperty("deviceName");
		String platformName = System.getProperty("platformName") != null ? System.getProperty("platformName")
				: prop.getProperty("platformName");
		if (platformName.equalsIgnoreCase("Android")) {
		} else if (platformName.equalsIgnoreCase("iOS")) {
			service = new AppiumServiceBuilder().usingDriverExecutable(new File("/opt/homebrew/opt/node@22/bin/node")) // Explicit
																														// Node.js
																														// path
					.withAppiumJS(new File("/opt/homebrew/lib/node_modules/appium/build/lib/main.js")) // Appium path
					.withIPAddress("127.0.0.1").usingPort(4723)
//					.withArgument(() -> "--allow-insecure", "chromedriver_autodownload") // --Adding to handle web context
					.build();

			service.start();

			XCUITestOptions options = new XCUITestOptions();
			// Appium -> WebDriver Agent -> iOS App
			options.setDeviceName(deviceName);
			options.setPlatformName(platformName);
//			options.setPlatformVersion("18.4");
//			options.setApp(appPath); //---To open app with .app file
			options.setNoReset(true); // ----- set true else app will be reset on start
			options.setWdaLaunchTimeout(Duration.ofSeconds(10));
			// options.setChromedriverExecutable(""); //---Can set driver path but I have
			// added arguments while creating service above ^
			driver = new IOSDriver(new URI("http://127.0.0.1:4723").toURL(), options);
			driver.unlockDevice();
		}
	}

	@BeforeMethod
	public void setup() throws URISyntaxException, IOException {
		//---Bundle ID For simulator--- xcrun simctl listapps booted. 
		driver.activateApp(packageName);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		homePage = new HomePage(driver);
	}

	public void longPressAction(WebElement element) {
		driver.executeScript("mobile: touchAndHold",
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "duration", 10.0));
	}

	public void scrollToElement(WebElement element) {
		driver.executeScript("mobile: scroll",
				ImmutableMap.of("direction", "down", "elementId", ((RemoteWebElement) element).getId()));
	}

	public void swipeAction() {
		driver.executeScript("mobile: swipe", ImmutableMap.of("velocity", 2500, "direction", "left"));
	}

	@AfterMethod
	public void tearDown() {
		driver.terminateApp(packageName);
	}

	@AfterClass
	public void stopAppium() {
		driver.quit();
		service.stop();
	}
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {// ----Goes to extent report
																							// in Listeners
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File filePath = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(src, filePath);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}
}
