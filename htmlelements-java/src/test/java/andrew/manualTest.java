package andrew;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class manualTest {
	NoPage noPage; 
	WebDriver driver;
	
	@Test
	public void timeout() {
		boolean useHtmlElementsDecorator = true;
		
		System.setProperty("webdriver.timeouts.implicitlywait", "5");
		
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		driver = new FirefoxDriver(capabilities);
		noPage = new NoPage(driver, useHtmlElementsDecorator);
		
		try {
			noPage.immediate.getText();
		} catch (NoSuchElementException e) {
			// First call can sometimes take longer than subsequent calls
		}
		
		noWait();
		implicitWait();
		explicitWait();
		fluentWait();
				
		driver.close();
	}

	private void noWait() {
		ActionTimer timer = ActionTimer.start();
		
		try {
			noPage.immediate.getText();
		} catch (NoSuchElementException e) {
			System.out.println("No Wait: Not Annotated failed in " + timer.duration().getSeconds());
		}
		
		timer = ActionTimer.start();
		
		try {
			noPage.timeout.getText();
		} catch (NoSuchElementException e) {
			System.out.println("No Wait: @Timeout(5) failed in " + timer.duration().getSeconds());
		}		
	}

	private void implicitWait() {
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		
		ActionTimer timer = ActionTimer.start();
		
		try {
			noPage.immediate.getText();
		} catch (NoSuchElementException e) {
			System.out.println("Implicit Wait (3): Not Annotated failed in " + timer.duration().getSeconds() + " *** Expected 3***");
		}
		
		timer = ActionTimer.start();
		
		try {
			noPage.timeout.getText();
		} catch (NoSuchElementException e) {
			System.out.println("Implicit Wait (3): @Timeout(5) failed in " + timer.duration().getSeconds() + " *** Expected unkown, not advised to mix***");
		}
		
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
	}

	private void explicitWait() {
		WebDriverWait wait = new WebDriverWait(driver, 7);
		
		ActionTimer timer = ActionTimer.start();
		
		try {
			wait.until(ExpectedConditions.visibilityOf(noPage.immediate));
		} catch (TimeoutException e) {
			System.out.println("Explicit Wait (3): Not Annotated failed in " + timer.duration().getSeconds() + " *** Expected 3***");
		}
		
		timer = ActionTimer.start();
		
		try {
			wait.until(ExpectedConditions.visibilityOf(noPage.immediate));
		} catch (TimeoutException e) {
			System.out.println("Explicit Wait (3): @Timeout(5) failed in " + timer.duration().getSeconds());
		}
	}
	
	private void fluentWait() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		    .withTimeout(7, TimeUnit.SECONDS)
		    .pollingEvery(1, TimeUnit.SECONDS)
		    .ignoring(NoSuchElementException.class);

		ActionTimer timer = ActionTimer.start();
		
		try {
			wait.until(ExpectedConditions.visibilityOf(noPage.immediate));
		} catch (TimeoutException e) {
			System.out.println("Fluent Wait (3): Not Annotated failed in " + timer.duration().getSeconds());
		}
		
		timer = ActionTimer.start();
		
		try {
			wait.until(ExpectedConditions.visibilityOf(noPage.immediate));
		} catch (TimeoutException e) {
			System.out.println("Fluent Wait (3): @Timeout(5) failed in " + timer.duration().getSeconds());
		}
	}
}
