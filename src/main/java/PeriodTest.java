import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class PeriodTest {
	public static void main(String[] args) throws InterruptedException {

		WebDriver driver = DriverFactory.getDriver(DriverFactory.BrowserType.PHANTOMJS);
		My cabinet = new My(driver, false);
		cabinet.manage(5, 5);
		cabinet.openMy();
		cabinet.openRedConnectMenu();
		cabinet.setBusinessTariff();
		cabinet.deleteOperators();
		cabinet.setOperator( new RCOperator("9999864875", "00:00", "00:00") );
		cabinet.close();
		System.out.println("Оператор установлен");

		Runnable task = () -> {

			int i = 0;
			try {
				for (;;) {
					testRC();
					System.out.println("Иттерация №" + ++i + " пройдена");
					Thread.currentThread().sleep(10000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		task.run();

		Thread thread = new Thread(task);
		thread.start();



	}

	//тест сервиса RedConnect
	private static void testRC() throws Exception {

		try {

			RCWidgetPage rcWidgetPage = new RCWidgetPage(DriverFactory.getDriver(DriverFactory.BrowserType.PHANTOMJS));
			rcWidgetPage.manage(5, 5);

			Properties property = new Properties();
			FileInputStream fis;
			try {
				fis = new FileInputStream("src/main/resources/config.properties");
				property.load(fis);
			} catch (IOException e) {
				System.err.println("ОШИБКА: Файл свойств отсуствует!");
			}

			rcWidgetPage.openSite( property.getProperty("urlProdSite") );
			rcWidgetPage.clickWidgetButton();
			rcWidgetPage.inputNumber("79999864875");
			rcWidgetPage.clickThePhoneButton();
			rcWidgetPage.waitPhoneDialElements();
			rcWidgetPage.close();

		} catch (Exception e) {
			System.err.println("Ошибка теста RC");
		}
	}

}
