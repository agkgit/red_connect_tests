import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class PeriodTest {
	public static void main(String[] args) throws InterruptedException {

		setOperatorForTestRC( new RCOperator("4824245255", "00:00", "00:00") );

		Runnable task1 = () -> {
			//setOperatorForTestRC( new RCOperator("4824245255", "00:00", "00:00") );

			int i = 0;
			try {
				for (;;) {
					testRC("79094065104");
					System.out.println("task1: иттерация №" + ++i + " пройдена");
					Thread.currentThread().sleep(5000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		Runnable task2 = () -> {
			//setOperatorForTestRC( new RCOperator("4824245255", "00:00", "00:00") );

			int i = 0;
			try {
				for (;;) {
					testRC("79266588290");
					System.out.println("task2: иттерация №" + ++i + " пройдена");
					Thread.currentThread().sleep(5000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		Runnable task3 = () -> {
			//setOperatorForTestRC( new RCOperator("4824245255", "00:00", "00:00") );

			int i = 0;
			try {
				for (;;) {
					testRC("79607088020");
					System.out.println("task3: иттерация №" + ++i + " пройдена");
					Thread.currentThread().sleep(2000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

//		Runnable task4 = () -> {
//			//setOperatorForTestRC( new RCOperator("4824245255", "00:00", "00:00") );
//
//			int i = 0;
//			try {
//				for (;;) {
//					testRC();
//					System.out.println("task4: иттерация №" + ++i + " пройдена");
//					Thread.currentThread().sleep(5000);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		};

//		task1.run();
//		task2.run();
//		task3.run();
//		task4.run();

		Thread thread1 = new Thread(task1);     thread1.start();
		Thread thread2 = new Thread(task2);     thread2.start();
		Thread thread3 = new Thread(task3);     thread3.start();
//		Thread thread4 = new Thread(task4);     thread4.start();



	}


	//установка оператора для testRC()
	private static void setOperatorForTestRC(RCOperator operator) {
		WebDriver driver = DriverFactory.getDriver(DriverFactory.BrowserType.PHANTOMJS);
		My cabinet = new My(driver, true);
		cabinet.manage(5, 5);
		cabinet.openMy();
		cabinet.openRedConnectMenu();
		cabinet.setBusinessTariff();
		cabinet.deleteOperators();
		cabinet.setOperator( operator );
		cabinet.close();
		System.out.println("Оператор установлен");
	}

	//тест сервиса RedConnect
	private static void testRC(String number) throws Exception {

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
			rcWidgetPage.openSite( property.getProperty("urlTestSite") );
			rcWidgetPage.clickWidgetButton();
			//rcWidgetPage.inputNumber(number);
			//rcWidgetPage.clickThePhoneButton();
			rcWidgetPage.waitPhoneDialElements();
			rcWidgetPage.close();

		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
