package andrew;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ru.yandex.qatools.htmlelements.annotations.Timeout;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

@Timeout(10)
public class NoPage {

	@FindBy(css = ".NotThere")
	public WebElement immediate;
	
	@FindBy(css = ".NotThere")
	@Timeout(5)
	public WebElement timeout;
	
	public NoPage(WebDriver driver, boolean useHtmlElementsDecorator) {
		if (useHtmlElementsDecorator) {
			PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)), this);
		} else {
			PageFactory.initElements(driver, this);
		}
	}
	
}
