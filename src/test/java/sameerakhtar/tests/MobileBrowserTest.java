package sameerakhtar.tests;

import org.testng.annotations.Test;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import sameerakhtar.TestComponents.BrowserBaseTest;

public class MobileBrowserTest extends BrowserBaseTest {

	@Test
	public void browserTest() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		System.out.println(driver.getTitle());
		driver.findElement(By.cssSelector(".navbar-toggler-icon")).click();
		driver.findElement(By.cssSelector(".nav-link[routerlink='/products']")).click();
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1000)");
		driver.findElement(By.xpath("//a[text()='Devops']")).click();
	}
}
