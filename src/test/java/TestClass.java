
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;


public class TestClass {

	//	@Test
//	public void setUnvalidNumber() throws InterruptedException {
//		RCWidgetPage rcWidgetPage = new RCWidgetPage();
//		rcWidgetPage.openSite("http://www.vernee.ru/qa");
//
//		for (String number : TestSettings.numbers) {
//			rcWidgetPage.reload();
//			rcWidgetPage.clickWidgetButton();
//			rcWidgetPage.inputNumber(number);
//			rcWidgetPage.clickThePhoneButton();
//			rcWidgetPage.waitWarningInvalidNumber();
//		}
//		rcWidgetPage.close();
//	}

	@Test
	public void mainTest() throws InterruptedException {

		WebDriver driver = DriverFactory.getDriver(DriverFactory.BrowserType.PHANTOMJS);

		My cabinet = new My(driver, false);
		cabinet.manage(5, 5);
		cabinet.openMy();
		cabinet.openRedConnectMenu();
		cabinet.setBusinessTariff();
		cabinet.deleteOperators();
		cabinet.setOperator(new RCOperator("9999864875", "11:12", "12:11"));
		cabinet.close();


		RCWidgetPage rcWidgetPage = new RCWidgetPage(DriverFactory.getDriver(DriverFactory.BrowserType.CHROME));

		rcWidgetPage.manage(5, 5);
		rcWidgetPage.openSite("http://vernee.ru/qa");
		rcWidgetPage.clickWidgetButton();
		rcWidgetPage.inputNumber("79999864875");
		rcWidgetPage.clickThePhoneButton();
		rcWidgetPage.waitPhoneDialElements();
		rcWidgetPage.close();

	}
}