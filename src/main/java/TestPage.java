import org.openqa.selenium.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.concurrent.*;

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

	@Step("Ожидание элемента")
	public void waitPresence(By byElement, int seconds, String failText) {
		try {
			WebElement dynamicElement = (new WebDriverWait(driver, seconds))
					.until(ExpectedConditions.presenceOfElementLocated(byElement));
		} catch (TimeoutException e) {
			fail(failText);
		} catch (NoSuchElementException e) {
			fail(failText);
		}
	}
	@Step("Ожидание элемента")
	public void waitPresence(By byElement, String failText) {
		this.waitPresence(byElement, 20, failText);
	}
	@Step("Ожидание элемента")
	public void waitPresence(By byElement, int seconds) {
		this.waitPresence(byElement, seconds, "элемент не найден");
	}
	@Step("Ожидание элемента")
	public void waitPresence(By byElement) {
		this.waitPresence(byElement, 20, "элемент не найден");
	}
	//-----------------------------------------------------------------------------------------------------
	@Step("ожидание видимости элемента")
	public void waitElementVisibility(By byElement, int seconds, String failText) {
		try {
			(new WebDriverWait(driver, seconds)).until(ExpectedConditions.visibilityOf(driver.findElement(byElement)));
		} catch (TimeoutException e) {
			fail(failText);
		} catch (NoSuchElementException e) {
			fail(failText);
		}
	}
	@Step("ожидание видимости элемента")
	public void waitElementVisibility(By byElement, String failText) {
		this.waitElementVisibility(byElement, 20, failText);
	}
	@Step("ожидание видимости элемента")
	public void waitElementVisibility(By byElement, int seconds) {
		this.waitElementVisibility(byElement, seconds, "элемент не найден");
	}
	@Step("ожидание видимости элемента")
	public void waitElementVisibility(By byElement) {
		this.waitElementVisibility(byElement, 20, "элемент не найден");
	}
//---------------------------------------------------------------------------------------------------------

}