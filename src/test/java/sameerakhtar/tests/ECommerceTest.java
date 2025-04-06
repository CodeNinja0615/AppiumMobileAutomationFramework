package sameerakhtar.tests;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import sameerakhtar.TestComponents.BaseTest;

public class ECommerceTest extends BaseTest {

	/**
	 * 
	 */
	@Test
	public void endToEndTest() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
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
		WebElement cartPage = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Cart']"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(cartPage));
		double sum = 0.0;
		List<WebElement> cartProducts = driver.findElements(AppiumBy.xpath(
				"//android.support.v7.widget.RecyclerView[@resource-id='com.androidsample.generalstore:id/rvCartProductList']/android.widget.RelativeLayout/android.widget.LinearLayout"));
		for (WebElement cartProduct : cartProducts) {
			String productName = cartProduct.findElement(AppiumBy.xpath("//android.widget.TextView")).getText();
			Assert.assertTrue(expectedProductList.contains(productName));
			String amountStr = cartProduct
					.findElement(AppiumBy.xpath("//android.widget.LinearLayout[2]/android.widget.TextView")).getText()
					.replace("$", "");
			double intAmount = getFormattedAmount(amountStr);
			sum += intAmount;
		}
		String totalAmountStr = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/totalAmountLbl"))
				.getText().replace("$", "");
		
		double totalAmount = getFormattedAmount(totalAmountStr);
		Assert.assertEquals(sum, totalAmount);
		driver.findElement(AppiumBy.xpath("//android.widget.CheckBox")).click();
		WebElement tnC = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/termsButton"));
		longPressAction(tnC);
		wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.androidsample.generalstore:id/alertTitle")));
		driver.findElement(AppiumBy.id("android:id/button1")).click();
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
