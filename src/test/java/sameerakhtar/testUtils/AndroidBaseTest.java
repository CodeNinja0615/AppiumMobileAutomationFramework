package sameerakhtar.testUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import sameerakhtar.pageObjects.android.FormPage;
import sameerakhtar.utils.AppiumUtils;

public class AndroidBaseTest {
	public AppiumDriverLocalService service;
	public AndroidDriver driver;
	public FormPage formPage;
	String packageName = "com.androidsample.generalstore";

	@BeforeClass(alwaysRun = true)
	public void configureAppiumMobile() throws URISyntaxException, IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//sameerakhtar//resources//GlobalData.properties");
		prop.load(fis);
		String deviceName = System.getProperty("deviceName") != null ? System.getProperty("deviceName")
				: prop.getProperty("deviceName");
		String platformName = System.getProperty("platformName") != null ? System.getProperty("platformName")
				: prop.getProperty("platformName");
		String ipAddress = System.getProperty("ipAddress") != null ? System.getProperty("ipAddress")
				: prop.getProperty("ipAddress");
		String port = System.getProperty("port") != null ? System.getProperty("port") : prop.getProperty("port");
		if (platformName.equalsIgnoreCase("Android")) {
			int portNo = Integer.parseInt(port);
			service = AppiumUtils.startAppiumServer(ipAddress, portNo);
			UiAutomator2Options options = new UiAutomator2Options();
			options.setDeviceName(deviceName);
			options.setPlatformName(platformName);
			// -----------------------------------------------------------------------------------------------------------------------//
//			options.setApp("C:/Users/HP/Downloads/General-Store.apk");  //---Optional for demo
//			options.setApp("/Users/sameerakhtar/Downloads/General-Store.apk");  //---Optional for demo
//			adb shell dumpsys window | findstr "mCurrentFocus" --WIN
//			adb shell dumpsys window | grep "mCurrentFocus"  --MAC
//			options.setAppActivity("com.instagram.barcelona.mainactivity.BarcelonaActivity");
//			options.setAppPackage("com.instagram.barcelona");
			// -----------------------------------------------------------------------------------------------------------------------//
			options.setNoReset(true); // ----- set true else app will be reset on start
			options.setAppWaitForLaunch(true);
			options.setGpsEnabled(true);
			options.autoGrantPermissions();
//			driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
			driver = new AndroidDriver(service.getUrl(), options);
			driver.unlockDevice();
//			options.setChromedriverExecutable(""); //---Can set driver path but I have added arguments while creating service above ^
		} else if (platformName.equalsIgnoreCase("iOS")) {
			// ---iOS code here
		}
	}

	public void longPressAction(WebElement element) {
		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()));
	}

	public double getFormattedAmount(String amountStr) {
		return Double.parseDouble(amountStr);
	}

	@BeforeMethod(alwaysRun = true)
	public void setup() throws URISyntaxException, IOException {
		driver.activateApp(packageName);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		formPage = new FormPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.terminateApp(packageName);
	}

	@AfterClass(alwaysRun = true)
	public void stopAppium() {
		driver.quit();
		service.stop();
	}
}
