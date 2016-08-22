import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class PeriodTest {
	public static void main(String[] args) throws InterruptedException {

		EmailSender.sendEmail("a.g.krupen@yandex.ru", "НАЧАЛО");

		Runnable task1, task2, task3, task4;

		//оператор недоступен
		task1 = () -> {
			setOperatorForTestRC(DriverFactory.BrowserType.CHROME ,"oldfree", "qweasd", new RCOperator("4824245255", "00:00", "00:00") );
			int i = 0;
			try {
				for (;;) {
					testRC("http://www.vernee.ru/oldfree", "79999864875", false, false);
					System.out.println(new Date().toString() + " task1: иттерация №" + ++i + "\tпройдена");
					Thread.currentThread().sleep(5000);
				}
			} catch (Exception e) {
				System.err.println(new Date().toString() + " " + e);
				EmailSender.sendEmail("a.g.krupen@yandex.ru", "ХУЕВАЯ ERROR");
			}
		};


//--------------------------------------------------------------------------------------------------
		setOperatorForTestRC(DriverFactory.BrowserType.CHROME, "rcfree", "qweasd", new RCOperator("9038099128", "00:00", "00:00") );

		//оператор доступен, посетитель недоступен
		task2 = () -> {

			int i = 0;
			try {
				for (;;) {
					testRC("http://www.tsyopa.ru/ark", "79094065104", true, false);
					System.out.println(new Date().toString() + " task2: иттерация №" + ++i + "\tпройдена");
					Thread.currentThread().sleep(5000);
				}
			} catch (Exception e) {
				System.err.println(new Date().toString() + " " + e);
				EmailSender.sendEmail("a.g.krupen@yandex.ru", "ERROR");
			}
		};

		//оператор доступен, посетитель доступен
		task3 = () -> {

			int i = 0;
			try {
				for (;;) {
					testRC("http://www.tsyopa.ru/ark", "79266588290", true, true);
					System.out.println(new Date().toString() + " task3: иттерация №" + ++i + " пройдена");
					Thread.currentThread().sleep(5000);
				}
			} catch (Exception e) {
				System.err.println(new Date().toString() + " " + e);
				EmailSender.sendEmail("a.g.krupen@yandex.ru", "ХУЕВАЯ ERROR");
			}
		};

		task4 = () -> {

			int i = 0;
			try {
				for (;;) {
					RHWidgetPage rhWidgetPage = new RHWidgetPage();
					rhWidgetPage.sendMessagesVisitorToOperator();
					System.out.println(new Date().toString() + " task4: иттерация №" + ++i + " пройдена");
				}
			} catch (InterruptedException e) {
				System.err.println(new Date().toString() + " " + e);
				EmailSender.sendEmail("a.g.krupen@yandex.ru", "ХУЕВАЯ ERROR");
			}
		};
//--------------------------------------------------------------------------------------------------

		Thread thread1 = new Thread(task1);		thread1.start();
		Thread thread2 = new Thread(task2);		thread2.start();
		Thread thread3 = new Thread(task3);		thread3.start();
		Thread thread4 = new Thread(task4);		thread4.start();

	}


	//установка оператора для testRC()
	private static void setOperatorForTestRC(DriverFactory.BrowserType browser,
											 String login,
											 String password,
											 RCOperator operator) {
		//WebDriver driver = DriverFactory.getDriver(DriverFactory.BrowserType.PHANTOMJS);
		WebDriver driver = DriverFactory.getDriver(browser);
		My cabinet = new My(driver, true);
		cabinet.manage(15, 15);
		cabinet.openMy(login, password);
		cabinet.openRedConnectMenu();
		//cabinet.setBusinessTariff();
		cabinet.deleteOperators();
		cabinet.setOperator( operator );
		cabinet.close();
	}

	//тест сервиса RedConnect
	private static void testRC(String site, String number,
							   Boolean isOperatorAvailable,
							   Boolean isVisitorAvailable) throws Exception {

		try {

			Properties property = new Properties();
			FileInputStream fis;
			try {
				fis = new FileInputStream("src/main/resources/config.properties");
				property.load(fis);
			} catch (IOException e) {
				System.err.println("ОШИБКА: Файл свойств отсуствует!");
			}

			RCWidgetPage rcWidgetPage = new RCWidgetPage(DriverFactory.getDriver(DriverFactory.BrowserType.PHANTOMJS));
			rcWidgetPage.manage(5, 5);
			rcWidgetPage.deleteAllCookies();
			rcWidgetPage.openSite(site);
			rcWidgetPage.clickWidgetButton();
			rcWidgetPage.inputNumber(number);
			rcWidgetPage.clickThePhoneButton();

			if (isOperatorAvailable == false)	{ rcWidgetPage.waitElementsOperatorVisitor(false, false); }
			else if (isVisitorAvailable)		{ rcWidgetPage.waitElementsOperatorVisitor(true, true); }
			else								{ rcWidgetPage.waitElementsOperatorVisitor(true, false); }

			rcWidgetPage.close();

		} catch (Exception e) {
			System.err.println(e);
	}
	}

}
;