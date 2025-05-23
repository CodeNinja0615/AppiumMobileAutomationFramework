package sameerakhtar.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import sameerakhtar.pageObjects.android.CartPage;
import sameerakhtar.pageObjects.android.ProductCatalogue;
import sameerakhtar.pageObjects.android.WebviewPage;
import sameerakhtar.testData.DataReader;
import sameerakhtar.testUtils.AndroidBaseTest;

public class ECommerceTest extends AndroidBaseTest {

	/**
	 * @throws InterruptedException
	 * 
	 */
	@Test(dataProvider = "getData", groups = { "Regression" })
//	public void endToEndPOMTest(String name, String country, String gender, List<String> expectedProductList) throws InterruptedException {
	public void endToEndPOMTest(HashMap<String, String> input) throws InterruptedException {
//		String[] expectedProduct = { "Air Jordan 4 Retro", "Jordan Lift Off" };
//		List<String> expectedProductList = Arrays.asList(expectedProduct);
		List<String> expectedProductList = new ArrayList<String>();
		expectedProductList.add(input.get("product1"));
		expectedProductList.add(input.get("product2"));
		formPage.setCountry(input.get("country"));
		formPage.setNameField(input.get("name"));
		formPage.setGender(input.get("gender"));
		ProductCatalogue productCatalogue = formPage.submitForm();
		productCatalogue.searchAndAddProductToCart(expectedProductList);
		CartPage cartPage = productCatalogue.navigateToCartPage();
		boolean status = cartPage.verifyProductInCart(expectedProductList);
		AssertJUnit.assertTrue(status);
		double calculatedSum = cartPage.calculateTotalAmountFromProducts();
		double totalAmount = cartPage.getTotalAmount();
		AssertJUnit.assertEquals(calculatedSum, totalAmount);
		WebviewPage webviewPage = cartPage.completePurchase();
		Thread.sleep(6000);
		webviewPage.switchContextAndPerformSearchAction();
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	@Test(groups = { "Regression" })
	public void endToEndTest() throws InterruptedException {
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
			AssertJUnit.assertTrue(expectedProductList.contains(productName));
			String amountStr = cartProduct
					.findElement(AppiumBy.xpath("//android.widget.LinearLayout[2]/android.widget.TextView")).getText()
					.replace("$", "");
			double intAmount = getFormattedAmount(amountStr);
			sum += intAmount;
		}
		String totalAmountStr = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/totalAmountLbl"))
				.getText().replace("$", "");

		double totalAmount = getFormattedAmount(totalAmountStr);
		AssertJUnit.assertEquals(sum, totalAmount);
		driver.findElement(AppiumBy.xpath("//android.widget.CheckBox")).click();
		WebElement tnC = driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/termsButton"));
		longPressAction(tnC);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(AppiumBy.id("com.androidsample.generalstore:id/alertTitle")));
		driver.findElement(AppiumBy.id("android:id/button1")).click();
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnProceed")).click();
		Thread.sleep(6000);
		Set<String> contexts = driver.getContextHandles();
		for (String context : contexts) {
			System.out.println(context);
		}
		Iterator<String> it = contexts.iterator();
		String nativeContext = it.next();
		String webContext = it.next();
		driver.context(webContext); // WEBVIEW_com.androidsample.generalstore
		driver.findElement(By.name("q")).sendKeys("Rahul Shetty Academy", Keys.ENTER); // ---Do not use AppiumBy for web
																						// context
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		driver.context(nativeContext);
	}

	@Test(groups = { "Regression" })
	public void errorValidationTest() {
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/spinnerCountry")).click();
		driver.findElement(
				AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Algeria\"));"))
				.click();
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/radioMale")).click();
		driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		String toastMsg = driver.findElement(AppiumBy.xpath("(//android.widget.Toast)[1]")).getDomAttribute("name");
		AssertJUnit.assertEquals(toastMsg, "Please enter your name");
	}

	@DataProvider
	public Object[][] getData() throws IOException {
//		"products": ["Air Jordan 4 Retro", "Jordan Lift Off"] //---Test tomorrow
		List<HashMap<String, Object>> data = DataReader.getJsonDataToMap(
				System.getProperty("user.dir") + "//src//test//java//sameerakhtar//testData//eCommerce.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };// data.get(0) -- first set of parameters
																	// data.get(1) -- second set of parameters
	}

//	@DataProvider
//	public Object[][] getData() {
//		List<String> products = new ArrayList<String>();
//		products.add("Air Jordan 4 Retro");
//		products.add("Jordan Lift Off");
//		return new Object[][] {{"Sameer Akhtar", "Algeria", "Male", products}, {"Sameer Akhtar", "India", "Male", products}};
//	}
}
