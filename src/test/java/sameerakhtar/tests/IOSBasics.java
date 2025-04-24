package sameerakhtar.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import sameerakhtar.TestComponents.IOSBaseTest;
import sameerakhtar.pageObjects.ios.AlertViewsPage;

public class IOSBasics extends IOSBaseTest {

	/**
	 * 
	 * 
	 */
	@Test
	public void iOSTestPOM() {
		AlertViewsPage alertViewsPage = homePage.goToAlertViewsPage();
		alertViewsPage.handleAlertViews("Sameer Akhtar");
	}
	
	@Test
	public void iOSBasicTest() {
		driver.findElement(AppiumBy.accessibilityId("Alert Views")).click();
		driver.findElement(AppiumBy.accessibilityId("Text Entry")).click();
		driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeCell")).sendKeys("Sameer Akhtar");
		driver.findElement(AppiumBy.accessibilityId("OK")).click();
		driver.findElement(
				AppiumBy.iOSNsPredicateString("type = 'XCUIElementTypeStaticText' AND label = 'Confirm / Cancel'"))
				.click();
//		AppiumBy.iOSNsPredicateString("type = 'XCUIElementTypeStaticText' AND value BEGINSWITH[c] 'Confirm'");
//		AppiumBy.iOSNsPredicateString("type = 'XCUIElementTypeStaticText' AND value ENDSWITH[c] 'Cancel'");
		String alertMsg = driver
				.findElement(AppiumBy
						.iOSNsPredicateString("type = 'XCUIElementTypeStaticText' AND name BEGINSWITH[c] 'A message'"))
				.getText();
		System.out.println(alertMsg);
		driver.findElement(AppiumBy.xpath("//XCUIElementTypeButton[@name='Confirm']")).click();
	}

	@Test
	public void longPressTest() {
		driver.findElement(AppiumBy.accessibilityId("Steppers")).click();
		WebElement e = driver.findElement(AppiumBy.xpath("(//XCUIElementTypeButton[@name=\"Increment\"])[3]"));
		longPressAction(e);
	}

	@Test
	public void scrollTest() {
		WebElement e = driver.findElement(AppiumBy.xpath("//XCUIElementTypeStaticText[@name='Web View']"));
		scrollToElement(e);
		e.click();
		driver.findElement(AppiumBy.xpath("//XCUIElementTypeButton[@name=\"UIKitCatalog\"]")).click();
	}

	@Test
	public void pickerViewTest() {
		driver.findElement(AppiumBy.accessibilityId("Picker View")).click();
		driver.findElement(AppiumBy.xpath("//XCUIElementTypePickerWheel[@name='Red color component value']"))
				.sendKeys("30");
		driver.findElement(AppiumBy.xpath("//XCUIElementTypePickerWheel[@name='Green color component value']"))
				.sendKeys("30");
		driver.findElement(AppiumBy.xpath("//XCUIElementTypePickerWheel[@name='Blue color component value']"))
				.sendKeys("30");
		System.out.println(
				driver.findElement(AppiumBy.xpath("//XCUIElementTypePickerWheel[@name='Blue color component value']"))
						.getDomAttribute("value"));
	}

	@Test
	public void sliderTest() {
		driver.findElement(AppiumBy.accessibilityId("Sliders")).click();
		WebElement slider = driver.findElement(AppiumBy.xpath("(//XCUIElementTypeSlider)[3]"));
		slider.sendKeys("0.92%"); // --- 0-1 range
		System.out.println(slider.getText());
	}

	@Test
	public void swipeTest() throws IOException {
		// ---Bundle ID
		driver.activateApp("com.apple.mobileslideshow");
//		driver.execute("mobile:launchApp", ImmutableMap.of("bundleId", ""));
		driver.findElement(AppiumBy.xpath("//XCUIElementTypeButton[contains(@name, 'album-ObjectIdentifier')]"))
				.click();
		List<WebElement> images = driver.findElements(AppiumBy.iOSClassChain("**/XCUIElementTypeImage"));
		System.out.println(images.size() - 2);
		images.get(0).click();
		for (int i = 0; i < images.size(); i++) {
			swipeAction();
		}
		driver.findElement(AppiumBy.accessibilityId("chevron.backward")).click();
		driver.findElement(AppiumBy.accessibilityId("chevron.backward")).click();
		driver.terminateApp("com.apple.mobileslideshow");
	}
}
