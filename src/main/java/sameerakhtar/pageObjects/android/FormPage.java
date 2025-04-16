package sameerakhtar.pageObjects.android;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;

public class FormPage {

	AndroidDriver driver;
	public FormPage() {
		PageFactory.initElements(driver, this);
	}

}
