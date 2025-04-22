package sameerakhtar.pageObjects.android;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import sameerakhtar.utils.AndroidActions;

public class CartPage extends AndroidActions {
	AndroidDriver driver;

	public CartPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this); // ---do not forget "AppiumFieldDecorator"
	}

	public void verifyProductAndGetTotalAmount(String[] expectedProductList) {
		waitForElementToBeVisible(null);
	}
}
