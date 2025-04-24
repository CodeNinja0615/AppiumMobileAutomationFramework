package sameerakhtar.pageObjects.ios;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import sameerakhtar.utils.IOSActions;

public class AlertViewsPage extends IOSActions {
	IOSDriver driver;
	public AlertViewsPage(IOSDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@iOSXCUITFindBy(accessibility = "Text Entry")
	private WebElement textEntry;

	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell")
	private WebElement inputCell;

	@iOSXCUITFindBy(accessibility = "OK")
	private WebElement okButton;

	@iOSXCUITFindBy(iOSNsPredicate = "type = 'XCUIElementTypeStaticText' AND label = 'Confirm / Cancel'")
	private WebElement confirmCancel;

	@iOSXCUITFindBy(iOSNsPredicate = "type = 'XCUIElementTypeStaticText' AND name BEGINSWITH[c] 'A message'")
	private WebElement alertMessage;

	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Confirm']")
	private WebElement confirmButton;

	public void handleAlertViews(String text) {
		textEntry.click();
		inputCell.sendKeys(text);
		okButton.click();
		confirmCancel.click();

		String msg = alertMessage.getText();
		System.out.println("Alert Message: " + msg);

		confirmButton.click();
	}
}
