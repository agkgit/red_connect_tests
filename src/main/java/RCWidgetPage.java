import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

import static org.testng.Assert.assertThrows;
import static org.testng.Assert.fail;

public class RCWidgetPage extends TestPage {

	//элементы виджета на тестируемой странице
	By rc_phone					= By.id("rc-phone");
	By rc_connector_frame		= By.className("rc-connector-frame");
	By rc_phone_input			= By.id("rc-phone-input");
	By rc_phone_button			= By.id("rc-phone-button");
	By rc_phone_input_warning	= By.id("rc-phone-input-warning");

	//конструктор
	public RCWidgetPage(WebDriver firefoxDriver) {
		super(firefoxDriver);
	}

	@Step("открытие виджета")
	public void clickWidgetButton() {
		this.waitElementVisibility(rc_phone, "не найден элемент rc_phone");
		driver.findElement(rc_phone).click();
	}

	@Step("ввод номера")
	public void inputNumber(String number) {
		this.waitElementVisibility(rc_connector_frame, 20, "не найден элемент rc_connector_frame");
		driver.switchTo().frame(driver.findElement(rc_connector_frame));

		this.waitElementVisibility(rc_phone_input, "не найден элемент rc_phone_input");
		WebElement rcPhoneInput = driver.findElement(rc_phone_input);

		rcPhoneInput.clear();
		rcPhoneInput.sendKeys(number);
		driver.switchTo().defaultContent();
	}

	@Step("нажатие кнопки \"Позвонить\"")
	public void clickThePhoneButton() {

		this.waitElementVisibility(rc_phone_button, "не найден элемент rc_phone_button");
		driver.findElement(rc_phone_button).click();

	}

	@Step("ожидание появления предупреждения 'Внимание! Проверьте правильность набранного номера'")
	public void waitWarningInvalidNumber() {
		String failText = "не появилось предупреждение 'Внимание! Проверьте правильность набранного номера'";
		this.waitElementVisibility(rc_phone_input_warning, failText);
	}

	@Step("ожидания элементов")
	public void waitElementsOperatorVisitor(Boolean isOperatorAvailable, Boolean isVisitorAvailable) {

		if (isOperatorAvailable == false) {

			this.waitElementVisibility(By.id("rc-phone-dial"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-snake"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-snake-curtain"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-snake-curtain2"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-half-circle"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-circle"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-fail"), 150);

		} else if (isVisitorAvailable) {

			this.waitElementVisibility(By.id("rc-phone-dial"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-snake"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-snake-curtain"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-snake-curtain2"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-half-circle"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-circle"), 3);
			//ожидание при доступном операторе и доступном посетителе
		} else {

			this.waitElementVisibility(By.id("rc-phone-dial"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-snake"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-snake-curtain"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-snake-curtain2"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-half-circle"), 3);
			this.waitElementVisibility(By.id("rc-phone-dial-circle"), 3);
			//ожидания при недоступном операторе
		}
	}
}