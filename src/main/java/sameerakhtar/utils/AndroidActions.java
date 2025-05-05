package sameerakhtar.utils;

import java.util.NoSuchElementException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

// https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md
public class AndroidActions extends AppiumUtils {

	AndroidDriver driver;

	public AndroidActions(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public void longPressAction(WebElement element) {
		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()));
	}

	public void scrollToEndAction(WebElement element) {
		boolean canScrollMore;
		do {
			canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap
					.of("left", 100, "top", 100, "width", 200, "height", 200, "direction", "down", "percent", 3.0));
		} while (canScrollMore);
	}

	public void scrollToElement(WebElement element) {
		int maxScrolls = 10; // Prevent infinite loop
		int scrollAttempts = 0;

		while (scrollAttempts < maxScrolls && !element.isDisplayed()) {
			((JavascriptExecutor) driver).executeScript("mobile: scrollGesture",
					ImmutableMap.of("left", 100, "top", 100, "width", 200, "height", 200, "direction", "down",
							"percent", 3.0, "elementId", ((RemoteWebElement) element).getId()));
			scrollAttempts++;
		}

		if (!element.isDisplayed()) {
			throw new NoSuchElementException("Element not visible after scrolling.");
		}
	}

	public void swipeAction(String direction) {
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of("left", 100, "top", 100,
				"width", 200, "height", 200, "direction", direction, "percent", 0.75));
	}

	public void swipeToElementAction(WebElement element, String direction) {
		boolean canSwipeMore;
		do {
			canSwipeMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture",
				ImmutableMap.of("left", 100, "top", 100, "width", 200, "height", 200, "direction", direction, "percent",
						0.75, "elementId", ((RemoteWebElement) element).getId()));
		} while (canSwipeMore);
	}

	public WebElement scrollToElementWithText(String text) {
		return driver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));"));
	}

}
