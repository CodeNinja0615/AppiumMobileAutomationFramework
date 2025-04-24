package sameerakhtar.pageObjects.ios;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import sameerakhtar.utils.IOSActions;

public class HomePage extends IOSActions{
	IOSDriver driver;
	public HomePage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@iOSXCUITFindBy(accessibility="Alert Views")
	private WebElement alertViews;
	
	public AlertViewsPage goToAlertViewsPage() {
		alertViews.click();
		return new AlertViewsPage(driver);
	}
}
