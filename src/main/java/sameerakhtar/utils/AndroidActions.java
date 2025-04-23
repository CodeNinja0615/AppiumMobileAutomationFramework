package sameerakhtar.utils;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AndroidActions {

	AndroidDriver driver;

	public AndroidActions(AndroidDriver driver) {
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
	        ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
	                "left", 100, 
	                "top", 100, 
	                "width", 200, 
	                "height", 200, 
	                "direction", "down", 
	                "percent", 3.0,
	                "elementId", ((RemoteWebElement) element).getId()
	        ));
	        scrollAttempts++;
	    }

	    if (!element.isDisplayed()) {
	        throw new NoSuchElementException("Element not visible after scrolling.");
	    }
	}

	public WebElement scrollToElementWithText(String text) {
		return driver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));"));
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
