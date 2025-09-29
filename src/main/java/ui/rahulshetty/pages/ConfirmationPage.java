// ========================================
// ConfirmationPage.java
// ========================================
package ui.rahulshetty.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.component.BaseComponent;

public class ConfirmationPage extends BaseComponent {

    @FindBy(css = ".hero-primary")
    private WebElement confirmMessageEle;

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmMessage() {
        waitForElementToAppear(confirmMessageEle);
        return getText(confirmMessageEle);
    }

    public boolean isOrderConfirmed() {
        String message = getConfirmMessage();
        return message.contains("THANKYOU FOR THE ORDER");
    }

    public boolean isPageLoaded() {
        return isDisplayed(confirmMessageEle);
    }
}