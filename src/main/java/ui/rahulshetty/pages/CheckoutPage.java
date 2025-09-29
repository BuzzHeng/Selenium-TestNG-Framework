// ========================================
// CheckoutPage.java
// ========================================
package ui.rahulshetty.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import ui.component.BaseComponent;

public class CheckoutPage extends BaseComponent {

    @FindBy(css = "[placeholder='Select Country']")
    private WebElement selectCountryTextBox;

    @FindBy(xpath = "//button[contains(@class,'ta-item list-group-item ng-star-inserted')][2]")
    private WebElement secondSelection;

    @FindBy(css = ".action__submit")
    private WebElement placeOrderBtn;

    private By dropdownResult = By.cssSelector(".ta-results");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void selectCountry(String countryName) {
        Actions a = new Actions(driver);
        a.sendKeys(selectCountryTextBox, countryName).build().perform();
        waitForElementToAppear(dropdownResult);
    }

    public void selectCountryFromDropdown() {
        click(secondSelection);
    }

    public void completeCountrySelection(String countryName) {
        selectCountry(countryName);
        selectCountryFromDropdown();
    }

    public ConfirmationPage placeOrder() {
        click(placeOrderBtn);
        return new ConfirmationPage(driver);
    }

    public boolean isPlaceOrderEnabled() {
        return placeOrderBtn.isEnabled();
    }

    public boolean isPageLoaded() {
        return isDisplayed(selectCountryTextBox) &&
                isDisplayed(placeOrderBtn);
    }
}