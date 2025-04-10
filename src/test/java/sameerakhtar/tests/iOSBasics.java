package sameerakhtar.tests;

import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import sameerakhtar.TestComponents.iOSBaseTest;

public class iOSBasics extends iOSBaseTest {

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
				.findElement(AppiumBy.iOSNsPredicateString("type = 'XCUIElementTypeStaticText' AND name BEGINSWITH[c] 'A message'"))
				.getText();
		System.out.println(alertMsg);
		driver.findElement(AppiumBy.xpath("//XCUIElementTypeButton[@name='Confirm']")).click();
	}
}
