package sameerakhtar.tests;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import sameerakhtar.TestComponents.BaseTest;

public class ECommerceTest extends BaseTest {

	/**
	 * 
	 */
	@Test
	public void endToEndTest() {
		String[] expectedProduct = { "Air Jordan 4 Retro", "Jordan Lift Off" };
		List<String> expectedProductList = Arrays.asList(expectedProduct);
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry")).click();
		driver.findElement(
				AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Algeria\"));"))
				.click();
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/nameField")).sendKeys("Sameer Akhtar");
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/radioMale")).click();
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop")).click();

		for (String product : expectedProductList) {
			driver.findElement(AppiumBy.androidUIAutomator(
					"new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + product + "\"));"));
			List<WebElement> productList = driver.findElements(AppiumBy.xpath(
					"//android.support.v7.widget.RecyclerView[@resource-id='com.androidsample.generalstore:id/rvProductList']/android.widget.RelativeLayout"));
			for (WebElement productEle : productList) {
				String productName = productEle.findElement(AppiumBy.xpath("//android.widget.TextView")).getText();
				if (product.equals(productName)) {
					productEle.findElement(AppiumBy.id("com.androidsample.generalstore:id/productAddCart")).click();
					break;
				}
			}
		}
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		driver.findElement(AppiumBy.xpath("//android.widget.CheckBox")).click();
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnProceed")).click();
	}

	@Test
	public void errorValidationTest() {
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry")).click();
		driver.findElement(
				AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Algeria\"));"))
				.click();
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/radioMale")).click();
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		String toastMsg = driver.findElement(AppiumBy.xpath("(//android.widget.Toast)[1]")).getDomAttribute("name");
		Assert.assertEquals(toastMsg, "Please enter your name");

	}
}
