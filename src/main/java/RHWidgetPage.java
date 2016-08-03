import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RHWidgetPage extends TestPage {

	//variables
	Thread botThread1;
	Thread botThread2;
	WebElement rhIFrame;

	XMPPBot bot1 = new XMPPBot("krupeninadmin", "qweasd", "xmpp.redhelper.ru", "xmpp.redhelper.ru", 5222);
	XMPPBot bot2 = new XMPPBot("krupenin", "qweasd", "xmpp.redhelper.ru", "xmpp.redhelper.ru", 5222);

	public static List<String> messages = Arrays.asList("Сообщение",
			"Привет",
			"Hello",
			"1234123412341234");


	public void sendMessagesVisitorToOperator() throws InterruptedException {

		FirefoxProfile firefoxProfile = new FirefoxProfile();
		firefoxProfile.setPreference("browser.private.browsing.autostart", true);

		driver = new FirefoxDriver();

		botThread1 = new Thread(bot1);
		botThread2 = new Thread(bot2);

		botThread1.start();
		botThread2.start();

		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		driver.get("http://www.vernee.ru/t");

		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(By.id("rh-badge")).click();

		final Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);

		rhIFrame = driver.findElement(By.id("rh-chatFrame"));
		driver.switchTo().frame(rhIFrame);
		WebElement rhTextArea = driver.findElement(By.id("chatTextarea"));
		WebElement rhSendButton = driver.findElement(By.id("chatSend"));

		for (String message : messages) {
			rhTextArea.sendKeys(message);
			rhSendButton.click();

			try {
				String xpathVar = ".//div[@class='msgBlock fromOperator'][last()]/div[@class='msg']/div[@class='textWrapper']/div[text()='" + message + "']";
				WebElement dynamicElement = (new WebDriverWait(driver, 4))
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathVar)));
			} catch (Exception e) {
				System.out.print("ОШИБКА");
			}
		}

		Thread.sleep(5000);
		driver.close();

	}

}
