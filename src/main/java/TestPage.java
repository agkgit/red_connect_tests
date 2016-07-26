import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.testng.Assert.fail;

public class TestPage {

	WebDriver driver;
	Boolean testEnviroment;

	//конструкторы-----------------------------------------------------------------------------------------
	public TestPage(WebDriver driver){
		this.driver = driver;
	}
	public TestPage(){
		this(new FirefoxDriver());
	};
//---------------------------------------------------------------------------------------------------------

	//установка параметров driver--------------------------------------------------------------------------
	public void manage(int implicitlyWait, int pageLoadTimeout) {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
	}
	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}
//---------------------------------------------------------------------------------------------------------

	//-----------------------------------------------------------------------------------------------------
	@Step("открытие тестируемого сайта с виджетом RC")
	public void openSite(String url) {
		this.driver.get(url);
	}

	@Step("обновление страницы")
	public void reload() {
		driver.navigate().refresh();
	}

	@Step("закрытие страницы")
	public void close() {
		driver.close();
	}
//---------------------------------------------------------------------------------------------------------


	//Ожидания элементов-----------------------------------------------------------------------------------
	@Step("ожидание элемента")
	public void wait(By byElement, int seconds, String failText) {
		try {
			(new WebDriverWait(driver, seconds)).until(ExpectedConditions.visibilityOf(driver.findElement(byElement)));
		} catch (TimeoutException e) {
			fail(failText);
		} catch (NoSuchElementException e) {
			fail(failText);
		}
	}
	@Step("ожидание элемента")
	public void wait(By byElement, String failText) {
		this.wait(byElement, 5, failText);
	}
	@Step("ожидание элемента")
	public void wait(By byElement, int seconds) {
		this.wait(byElement, seconds, "элемент не найден");
	}
	@Step("ожидание элемента")
	public void wait(By byElement) {
		this.wait(byElement, 5, "элемент не найден");
	}
//---------------------------------------------------------------------------------------------------------

}