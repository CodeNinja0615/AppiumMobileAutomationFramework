package sameerakhtar.utils;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumUtils {
	AppiumDriver driver;

	public AppiumUtils(AppiumDriver driver) {
		this.driver = driver;
	}

	public static AppiumDriverLocalService startAppiumServer(String ipAddress, int port) {
		// ---node execution for Windows Machine here
//		String currentUser = System.getProperty("user.name");
//		service = new AppiumServiceBuilder()
//				.withAppiumJS(new File("C://Users//" + currentUser
//						+ "//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
//				.withIPAddress("127.0.0.1").usingPort(4723).build();

		// ---node execution for MAC Machine here
		// Define environment variables for Appium
		Map<String, String> env = new HashMap<>(System.getenv());
		env.put("ANDROID_HOME", "/Users/sameerakhtar/Library/Android/sdk");
		env.put("ANDROID_SDK_ROOT", "/Users/sameerakhtar/Library/Android/sdk");

		AppiumDriverLocalService service = new AppiumServiceBuilder()
				.usingDriverExecutable(new File("/opt/homebrew/opt/node@22/bin/node")) // Explicit Node.js path
				.withAppiumJS(new File("/opt/homebrew/lib/node_modules/appium/build/lib/main.js")) // Appium path
				.withEnvironment(env) // Pass environment variables explicitly
				.withIPAddress(ipAddress).usingPort(port)
				.withArgument(() -> "--allow-insecure", "chromedriver_autodownload") // --Adding to handle web context
				.build();

		service.start();
		return service;
	}

	public void waitForElementToBeVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementToBeVisible(By by) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
	}

	public double getFormattedAmount(String amountStr) {
		return Double.parseDouble(amountStr);
	}
}
