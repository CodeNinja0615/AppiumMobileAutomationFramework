package sameerakhtar.pageObjects.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import sameerakhtar.utils.AndroidActions;

public class FormPage extends AndroidActions {

	AndroidDriver driver;

	public FormPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this); //---do not forget "AppiumFieldDecorator"
	}

//	@iOSXCUITFindBy
	@AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
	private WebElement country;
	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	private WebElement nameField;
	@AndroidFindBy(id = "com.androidsample.generalstore:id/radioMale")
	private WebElement maleOption;
	@AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
	private WebElement femaleOption;
	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	private WebElement letShopBtn;

	public void setCountry(String countryName) {
		country.click();
		scrollToElementWithText(countryName).click();
	}

	public void setNameField(String Name) {
		nameField.sendKeys(Name);
	}

	public void setGender(String gender) {
		if (gender.contains("Male")) {
			maleOption.click();
		} else {
			femaleOption.click();
		}
	}

	public ProductCatalogue submitForm() {
		letShopBtn.click();
		return new ProductCatalogue(driver);
	}
}
