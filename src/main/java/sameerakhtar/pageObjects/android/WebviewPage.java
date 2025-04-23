package sameerakhtar.pageObjects.android;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import sameerakhtar.utils.AndroidActions;

public class WebviewPage extends AndroidActions {

	AndroidDriver driver;

	public WebviewPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this); // ---do not forget "AppiumFieldDecorator"
	}
	@FindBy(name="q")
	private WebElement searchBox;
	
	public void switchContextAndPerformSearchAction() {
		Set<String> contexts = driver.getContextHandles();
		for (String context : contexts) {
			System.out.println(context);
		}
		Iterator<String> it = contexts.iterator();
		String nativeContext = it.next();
		String webContext = it.next();
		driver.context(webContext); // WEBVIEW_com.androidsample.generalstore
		searchBox.sendKeys("Rahul Shetty Academy",Keys.ENTER); //---Do not use AppiumBy for web context
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		driver.context(nativeContext);
	}

}
