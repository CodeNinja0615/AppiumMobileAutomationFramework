package sameerakhtar.pageObjects.android;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import sameerakhtar.utils.AndroidActions;

public class CartPage extends AndroidActions {
	AndroidDriver driver;

	public CartPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this); // ---do not forget "AppiumFieldDecorator"
	}

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Cart']")
	private WebElement cartPage;
	@AndroidFindBy(xpath = "//android.support.v7.widget.RecyclerView[@resource-id='com.androidsample.generalstore:id/rvCartProductList']/android.widget.RelativeLayout/android.widget.LinearLayout")
	private List<WebElement> cartProducts;
	@AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
	private WebElement totalAmount;
	@AndroidFindBy(xpath = "//android.widget.CheckBox")
	private WebElement checkBox;
	@AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
	private WebElement tnC;
	@AndroidFindBy(id = "android:id/button1")
	private WebElement closeAlert;
	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
	private WebElement completePurchase;

	private By eachProdName = AppiumBy.xpath("//android.widget.TextView");
	private By eachProdAmount = AppiumBy.xpath("//android.widget.LinearLayout[2]/android.widget.TextView");
	private By alert = AppiumBy.id("com.androidsample.generalstore:id/alertTitle");
	public Boolean verifyProductInCart(List<String> expectedProductList) {
		waitForElementToBeVisible(cartPage);
		boolean status = false;
		for (WebElement cartProduct : cartProducts) {
			String productName = cartProduct.findElement(eachProdName).getText();
//			Assert.assertTrue(expectedProductList.contains(productName));
			if (expectedProductList.contains(productName)) {
				status = true;
			} else {
				return status;
			}
		}
		return status;
	}

	public double calculateTotalAmountFromProducts() {
		double sum = 0.0;
		for (WebElement cartProduct : cartProducts) {
			String amountStr = cartProduct.findElement(eachProdAmount).getText().replace("$", "");
			double intAmount = getFormattedAmount(amountStr);
			sum += intAmount;
		}
		return sum;
	}

	public double getTotalAmount() {
		String totalAmountStr = totalAmount.getText().replace("$", "");
		return getFormattedAmount(totalAmountStr);
	}

	public WebviewPage completePurchase() {
		checkBox.click();
		longPressAction(tnC);
		waitForElementToBeVisible(alert);
		closeAlert.click();
		completePurchase.click();
		return new WebviewPage(driver);
	}
}
