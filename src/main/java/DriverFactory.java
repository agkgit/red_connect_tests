import org.omg.CORBA.StringHolder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class DriverFactory {

	public enum BrowserType { FIREFOX, CHROME, IE, SAFARI, PHANTOMJS }

	static WebDriver getDriver(BrowserType browserType) {

		WebDriver driver = null;

		switch (browserType) {

			case FIREFOX:
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				firefoxProfile.setPreference("browser.private.browsing.autostart", true);
				driver = new FirefoxDriver(firefoxProfile);
				driver.manage().deleteAllCookies();
				break;

			case CHROME:

				String localDriver = null;
				//путь к ChromeDriver в зависимости от ОС и разрядности
				if (isWindows())	{ localDriver = "src/main/resources/drivers/chromedriver_win32/chromedriver.exe"; }
				else if (isMac())	{ localDriver = "src/main/resources/drivers/chromedriver_mac32/chromedriver"; }
				else if (is64bit())	{ localDriver ="src/main/resources/drivers/chromedriver_linux64/chromedriver"; }
				else				{ localDriver ="src/main/resources/drivers/chromedriver_linux32/chromedriver"; }

				System.setProperty("webdriver.chrome.driver", localDriver);
				driver = new ChromeDriver();
				driver.manage().deleteAllCookies();
				break;

			case PHANTOMJS:
				driver = new PhantomJSDriver();
				driver.manage().deleteAllCookies();
				break;

			default:
				driver = new FirefoxDriver();
		}

		return driver;

	}

//Проверка версии ОС------------------------------
	public static boolean isWindows(){

		String os = System.getProperty("os.name").toLowerCase();
		//windows
		return (os.indexOf( "win" ) >= 0);

	}
	public static boolean isMac(){

		String os = System.getProperty("os.name").toLowerCase();
		//Mac
		return (os.indexOf( "mac" ) >= 0);

	}
	public static boolean isUnix(){

		String os = System.getProperty("os.name").toLowerCase();
		//linux or unix
		return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);

	}
//------------------------------------------------

//Проверка архитектуры----------------------------
	public static boolean is64bit() {

		String arch = System.getProperty("os.arch").toLowerCase();
		//linux or unix
		return (arch.indexOf("64") >= 0);
	}
//------------------------------------------------
}