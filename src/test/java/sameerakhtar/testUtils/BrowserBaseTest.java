package sameerakhtar.testUtils;

//----https://github.com/appium/appium-uiautomator2-driver/?tab=readme-ov-file
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BrowserBaseTest {

	public AppiumDriverLocalService service;
	public AndroidDriver driver;

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
			// ---node execution for Windows Machine here
//			String currentUser = System.getProperty("user.name");
//			service = new AppiumServiceBuilder()
//					.withAppiumJS(new File("C://Users//" + currentUser
//							+ "//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
//					.withIPAddress("127.0.0.1").usingPort(4723).build();

			// ---node execution for MAC Machine here
			// Define environment variables for Appium
			Map<String, String> env = new HashMap<>(System.getenv());
			env.put("ANDROID_HOME", "/Users/sameerakhtar/Library/Android/sdk");
			env.put("ANDROID_SDK_ROOT", "/Users/sameerakhtar/Library/Android/sdk");

			service = new AppiumServiceBuilder().usingDriverExecutable(new File("/opt/homebrew/opt/node@22/bin/node")) // Explicit
																														// Node.js
																														// path
					.withAppiumJS(new File("/opt/homebrew/lib/node_modules/appium/build/lib/main.js")) // Appium path
					.withEnvironment(env) // ✅ Pass environment variables explicitly
					.withIPAddress("127.0.0.1").usingPort(4723)
					.withArgument(() -> "--allow-insecure", "chromedriver_autodownload") // --Adding to handle web
																							// context
					.build();

			service.start();
//			service = AppiumUtils.startAppiumServer();
			UiAutomator2Options options = new UiAutomator2Options();
			options.setDeviceName(deviceName);
			options.setPlatformName(platformName);
			options.setNoReset(true); // ----- set true else app will be reset on start
			options.setAppWaitForLaunch(true);
			options.setGpsEnabled(true);
			options.autoGrantPermissions();
//			options.setChromedriverExecutable(""); //---Can set driver path but I have added arguments while creating service above ^
			options.setCapability("browserName", "Chrome");
			driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
			driver.unlockDevice();
		} else if (platformName.equalsIgnoreCase("iOS")) {
			// ---iOS code here
		}
	}

	@BeforeMethod
	public void setup() throws URISyntaxException, IOException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@AfterMethod
	public void tearDown() {
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
