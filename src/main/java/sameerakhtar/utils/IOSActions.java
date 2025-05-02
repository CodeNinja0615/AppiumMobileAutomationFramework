package sameerakhtar.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.ios.IOSDriver;

public class IOSActions extends AppiumUtils {

	IOSDriver driver;

	public IOSActions(IOSDriver driver) {
		super(driver);
		this.driver = driver;
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
}
