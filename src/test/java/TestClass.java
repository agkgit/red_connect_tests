
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


public class TestClass {

	@Test
	public void setUnvalidNumber() throws InterruptedException {

		Properties property = new Properties();
		try {
			FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
			property.load(fis);
		} catch (IOException e) {
			System.err.println("ОШИБКА: Файл свойств отсуствует!");
		}
		ArrayList<String> invalidNumbers = new ArrayList<String>();
		int i = 0;
		for (;;) {
			String number;
			number = property.getProperty("invalid_number." + i);
			if (number != null) {
				invalidNumbers.add(number);
				i++;
			} else {
				break;
			}
		}

		RCWidgetPage rcWidgetPage = new RCWidgetPage(DriverFactory.getDriver(DriverFactory.BrowserType.FIREFOX));
		rcWidgetPage.openSite("http://www.vernee.ru/qa");


		for (String number : invalidNumbers) {
			rcWidgetPage.reload();
			rcWidgetPage.clickWidgetButton();
			rcWidgetPage.inputNumber(number);
			rcWidgetPage.clickThePhoneButton();
			rcWidgetPage.waitWarningInvalidNumber();
		}
	rcWidgetPage.close();
	}

	@Test
	public void testRC() throws InterruptedException {

		this.setNewOperator(new RCOperator("9999864875", "00:00", "00:00"));

		RCWidgetPage rcWidgetPage = new RCWidgetPage(DriverFactory.getDriver(DriverFactory.BrowserType.CHROME));

		rcWidgetPage.manage(5, 5);
		rcWidgetPage.openSite("http://vernee.ru/qa");
		rcWidgetPage.clickWidgetButton();
		rcWidgetPage.inputNumber("79999864875");
		rcWidgetPage.clickThePhoneButton();
		rcWidgetPage.waitElementsOperatorVisitor(false, false);
		rcWidgetPage.close();

	}

	//ввод оператора
	public void setNewOperator(RCOperator operator) {
		WebDriver driver = DriverFactory.getDriver(DriverFactory.BrowserType.PHANTOMJS);
		My cabinet = new My(driver, false);
		cabinet.manage(5, 5);
		cabinet.openMy();
		cabinet.openRedConnectMenu();
		cabinet.setBusinessTariff();
		cabinet.deleteOperators();
		cabinet.setOperator(operator);
		cabinet.close();
	}
}