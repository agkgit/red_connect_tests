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
				break;

			case CHROME:
				System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver_win32/chromedriver.exe");
				driver = new ChromeDriver();
				break;

			case PHANTOMJS:
				driver = new PhantomJSDriver();
				break;

			default:
				driver = new FirefoxDriver();
		}

		return driver;

	}
}