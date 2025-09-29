// ========================================
// LandingPage.java
// ========================================
package ui.rahulshetty.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.component.BaseComponent;

public class LandingPage extends BaseComponent {

    private static final String PAGE_URL = "https://rahulshettyacademy.com/client";

    @FindBy(id = "userEmail")
    private WebElement userEmail;

    @FindBy(id = "userPassword")
    private WebElement passwordEle;

    @FindBy(id = "login")
    private WebElement submitBtn;

    @FindBy(css = "[class*='flyInOut']")
    private WebElement errorMessage;

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public LandingPage goTo() {
        navigateTo(PAGE_URL);
        return this;
    }

    public ProductCataloguePage loginApplication(String email, String password) {
        type(userEmail, email);
        type(passwordEle, password);
        click(submitBtn);
        return new ProductCataloguePage(driver);
    }

    public String getErrorMessage() {
        waitForElementToAppear(errorMessage);
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        try {
            waitForElementToAppear(errorMessage, 3);
            return isDisplayed(errorMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPageLoaded() {
        return isDisplayed(userEmail) &&
                isDisplayed(passwordEle) &&
                isDisplayed(submitBtn);
    }
}