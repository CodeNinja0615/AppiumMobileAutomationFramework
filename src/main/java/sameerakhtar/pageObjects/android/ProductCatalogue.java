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

public class ProductCatalogue extends AndroidActions {
	AndroidDriver driver;

	public ProductCatalogue(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this); // ---do not forget "AppiumFieldDecorator"
	}

	@AndroidFindBy(xpath = "//android.support.v7.widget.RecyclerView[@resource-id='com.androidsample.generalstore:id/rvProductList']/android.widget.RelativeLayout")
	List<WebElement> productList;
	@AndroidFindBy(id="com.androidsample.generalstore:id/appbar_btn_cart")
	WebElement cartBtn;
	
	By productTextView = By.xpath("//android.widget.TextView");
	By addToCartBtn = By.id("com.androidsample.generalstore:id/productAddCart");

	public void searchAndAddProductToCart(String[] expectedProductList) {
		for (String product : expectedProductList) {
			scrollToElementWithText(product);
//			List<WebElement> productList = driver.findElements(AppiumBy.xpath(
//					"//android.support.v7.widget.RecyclerView[@resource-id='com.androidsample.generalstore:id/rvProductList']/android.widget.RelativeLayout"));
			for (WebElement productEle : productList) {
				String productName = productEle.findElement(productTextView).getText();
				if (product.equals(productName)) {
					productEle.findElement(addToCartBtn).click();
					break;
				}
			}
		}
	}
	
	public void navigateToCart() {
		cartBtn.click();
	}
}
