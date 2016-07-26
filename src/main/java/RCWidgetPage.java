import org.openqa.selenium.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Date;

import static org.testng.Assert.assertThrows;
import static org.testng.Assert.fail;

public class RCWidgetPage extends TestPage {

	//элементы виджета на тестируемой странице
	By rc_phone					= By.id("rc-phone");
	By rc_connector_frame		= By.className("rc-connector-frame");
	By rc_phone_input			= By.id("rc-phone-input");
	By rc_phone_button			= By.id("rc-phone-button");
	By rc_phone_input_warning	= By.id("rc-phone-input-warning");

	public RCWidgetPage(WebDriver firefoxDriver) {
		super(firefoxDriver);
	}

	@Step("открытие виджета")
	public void clickWidgetButton() {
		this.wait(rc_phone, "не найден элемент rc_phone");
		driver.findElement(rc_phone).click();
	}

	@Step("ввод номера")
	public void inputNumber(String number) {
		this.wait(rc_connector_frame, 10, "не найден элемент rc_connector_frame");
		driver.switchTo().frame(driver.findElement(rc_connector_frame));

		this.wait(rc_phone_input, "не найден элемент rc_phone_input");
		WebElement rcPhoneInput = driver.findElement(rc_phone_input);

		rcPhoneInput.clear();
		rcPhoneInput.sendKeys(number);
		driver.switchTo().defaultContent();
	}

	@Step("нажатие кнопки \"Позвонить\"")
	public void clickThePhoneButton() {

		this.wait(rc_phone_button, "не найден элемент rc_phone_button");

//		try {
//			WebElement byWidgetButton = (new WebDriverWait(driver, 1))
//					.until(ExpectedConditions.presenceOfElementLocated(rc_phone_button));
//		} catch (TimeoutException e) {
//			fail("rc_connector_frame недоступен");
//		}

		driver.findElement(rc_phone_button).click();
	}

	@Step("ожидание появления предупреждения 'Внимание! Проверьте правильность набранного номера'")
	public void waitWarningInvalidNumber() {
		String failText = "не появилось предупреждение 'Внимание! Проверьте правильность набранного номера'";
		this.wait(rc_phone_input_warning, failText);
	}

	@Step("ожидания при состоявшемся звонке")
	public void waitPhoneDialElements() {

		this.wait(By.id("rc-phone-form-close"));
//		this.wait(By.id("rc-phone-dial"), 1);
//		this.wait(By.id("rc-phone-dial-snake"), 1);
//		this.wait(By.id("rc-phone-dial-snake-curtain"), 1);
//		this.wait(By.id("rc-phone-dial-snake-curtain2"),1);
//		this.wait(By.id("rc-phone-dial-half-circle"), 1);
//		this.wait(By.id("rc-phone-dial-circle"), 1);
//		this.wait(By.id("rc-phone-dial-fail"), 300);
	}

}