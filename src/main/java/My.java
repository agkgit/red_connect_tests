import com.sun.deploy.Environment;
import com.sun.deploy.security.ruleset.DRSHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;

import java.lang.String;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.testng.Assert.fail;



public class My extends TestPage {

	// константы типа By
	public final By		BY_DELETE_PHONE,		BY_DO_DELETE_PHONE_YES,		BY_ADD_NUMBER,
						BY_NUMBER_IMPUT,		BY_ADD_WORK_TIME,			BY_START_WORK_TIME,
						BY_STOP_WORK_TIME,		BY_SAVE_SETTINGS,			BY_REDCONNECT_MENU,
						BY_BUSINESS_TARIFF,		BY_FREE_TARIFF;

	String login, password;
	String urlMy;
	FileInputStream fis = null;
	Boolean testEnvironment = false;


	public void setTestEnvironment(Boolean testEnvironment) {
		this.testEnvironment = testEnvironment;
	}
	public Boolean isTestEnvironment() {
		return testEnvironment;
	}

	public My(WebDriver driver, Boolean isTestEnvironment) {

		super(driver);

		Properties property = new Properties();
		try {
			fis = new FileInputStream("src/main/resources/config.properties");
			property.load(fis);
		} catch (IOException e) {
			System.err.println("ОШИБКА: Файл свойств отсуствует!");
		}

		this.setTestEnvironment(isTestEnvironment);

		//установка переменных типа By
		BY_DELETE_PHONE			= By.xpath( property.getProperty("BY_DELETE_PHONE") );
		BY_DO_DELETE_PHONE_YES	= By.xpath( property.getProperty("BY_DO_DELETE_PHONE_YES") );
		BY_REDCONNECT_MENU		= By.xpath( property.getProperty("BY_REDCONNECT_MENU") );
		BY_BUSINESS_TARIFF		= By.xpath( property.getProperty("BY_BUSINESS_TARIFF") );
		BY_FREE_TARIFF			= By.xpath( property.getProperty("BY_FREE_TARIFF") );

		//в зависимости от среды
		if (isTestEnvironment) {

			this.urlMy		= property.getProperty("urlMyTest");
			this.login		= property.getProperty("rcLoginTest");
			this.password	= property.getProperty("rcPassTest");

			BY_NUMBER_IMPUT			= By.xpath( property.getProperty("BY_NUMBER_IMPUT_Test") );
			BY_ADD_WORK_TIME		= By.xpath( property.getProperty("BY_ADD_WORK_TIME_Test") );
			BY_START_WORK_TIME		= By.xpath( property.getProperty("BY_START_WORK_TIME_Test") );
			BY_STOP_WORK_TIME		= By.xpath( property.getProperty("BY_STOP_WORK_TIME_Test") );
			BY_SAVE_SETTINGS		= By.xpath( property.getProperty("BY_SAVE_SETTINGS_Test") );
			BY_ADD_NUMBER			= By.xpath( property.getProperty("BY_ADD_NUMBER_Test") );

		} else {
			this.urlMy		= property.getProperty("urlMyProd");
			this.login		= property.getProperty("rcLoginProd");
			this.password	= property.getProperty("rcPassProd");

			BY_NUMBER_IMPUT			= By.xpath( property.getProperty("BY_NUMBER_IMPUT_Prod") );
			BY_ADD_WORK_TIME		= By.xpath( property.getProperty("BY_ADD_WORK_TIME_Prod") );
			BY_START_WORK_TIME		= By.xpath( property.getProperty("BY_START_WORK_TIME_Prod") );
			BY_STOP_WORK_TIME		= By.xpath( property.getProperty("BY_STOP_WORK_TIME_Prod") );
			BY_SAVE_SETTINGS		= By.xpath( property.getProperty("BY_SAVE_SETTINGS_Prod") );
			BY_ADD_NUMBER			= By.xpath( property.getProperty("BY_ADD_NUMBER_Prod") );

		}
	}
	public My(boolean isTestEnvironment) { this(new FirefoxDriver(), isTestEnvironment); }


	@Step("открытие личного кабинета")
	public void openMy(String login, String password) {
		driver.findElement(By.id("name")).sendKeys(login);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.className("login-button")).click();
	}
	public void openMy() {
		this.openMy(this.login, this.password);
	}

	@Step("открытие меню RedConnect")
	public void openRedConnectMenu() {
		this.wait(BY_REDCONNECT_MENU, 3, "не найден элемент BY_REDCONNECT_MENU");
		driver.findElement(BY_REDCONNECT_MENU).click();
	}

	@Step("установка free-тарифа")
	public void setFreeTariff() {
		this.wait(BY_FREE_TARIFF);
		driver.findElement(BY_FREE_TARIFF).click();
	}

	@Step("установка business-тарифа")
	public void setBusinessTariff() {
		this.wait(BY_BUSINESS_TARIFF, 10, "не найден элемент BY_BUSINESS_TARIFF");
		driver.findElement(BY_BUSINESS_TARIFF).click();
	}

	@Step("удаление операторов")
	public void deleteOperators() {

		// ожидание для тестовой среды

		this.wait(BY_REDCONNECT_MENU, "не найден элемент BY_REDCONNECT_MENU");
		driver.findElement(BY_REDCONNECT_MENU).click();

		for (; ; ) {
			try {
				driver.findElement(BY_DELETE_PHONE).click();
				driver.findElement(BY_DO_DELETE_PHONE_YES).click();
			} catch (Exception e) {
				break;
			}
		}
	}

	@Step("установка оператора")
	public void setOperator(RCOperator operator) {

		this.wait(BY_ADD_NUMBER, 5, "не найден BY_ADD_NUMBER");
		driver.findElement(BY_ADD_NUMBER).click();

		this.wait(BY_NUMBER_IMPUT, "не найден BY_NUMBER_IMPUT");
		driver.findElement(BY_NUMBER_IMPUT).sendKeys(operator.number);

		driver.findElement(BY_ADD_WORK_TIME).click();

		WebElement timeStartElement = driver.findElement(BY_START_WORK_TIME);
		timeStartElement.clear();
		timeStartElement.sendKeys(operator.timeStart);

		WebElement timeStopElement = driver.findElement(BY_STOP_WORK_TIME);
		timeStopElement.clear();
		timeStopElement.sendKeys(operator.timeStop);

		driver.findElement(BY_SAVE_SETTINGS).click();
	}

	@Step("установка операторов")
	public void setOperators(RCOperator... operators) {

		Integer i = 2;

		for (RCOperator operator : operators) {

			driver.findElement(BY_ADD_NUMBER);
			String numberXpathString = "/html/body/div[3]/div[3]/div[3]/div[2]/div[3]/div[4]/div/div/div[2]/div[2]/ul[" + i + "]/li[1]/div[2]/div[1]/div[1]/div[1]/div/input";

			try {
				WebElement numberElement = (new WebDriverWait(driver, 3))
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(numberXpathString)));
				numberElement.sendKeys(operator.number);

			} catch (org.openqa.selenium.TimeoutException e) {
				fail("Не появился элемент -> " + i);
			}


			By by_addWorkTime = By.xpath("/html/body/div[3]/div[3]/div[3]/div[2]/div[3]/div[4]/div/div/div[2]/div[2]/ul[" + i + "]/li[1]/div[2]/div[2]/a[1]");
			driver.findElement(by_addWorkTime).click();

			By by_timeStartElement = By.xpath("/html/body/div[3]/div[3]/div[3]/div[2]/div[3]/div[4]/div/div/div[2]/div[2]/ul[" + i + "]/li[1]/div[2]/div[4]/div[2]/input[1]");
			WebElement timeStartElement = driver.findElement(by_timeStartElement);
			timeStartElement.clear();
			timeStartElement.sendKeys(operator.timeStart);

			By by_timeStopElement = By.xpath("/html/body/div[3]/div[3]/div[3]/div[2]/div[3]/div[4]/div/div/div[2]/div[2]/ul[" + i + "]/li[1]/div[2]/div[4]/div[2]/input[1]");
			WebElement timeStopElement = driver.findElement(by_timeStopElement);
			timeStopElement.clear();
			timeStopElement.sendKeys(operator.timeStop);

			driver.findElement(BY_SAVE_SETTINGS).click();

			i += 3;
		}
	}



}
