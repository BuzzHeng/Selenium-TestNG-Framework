// ========================================
// CartPage.java
// ========================================
package ui.rahulshetty.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.component.BaseComponent;

import java.util.List;

public class CartPage extends BaseComponent {

    @FindBy(css = ".cartSection h3")
    private List<WebElement> cartProducts;

    @FindBy(css = ".totalRow button")
    private WebElement checkoutBtn;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean verifyProductDisplay(String productName) {
        return cartProducts.stream()
                .anyMatch(cartProduct -> cartProduct.getText()
                        .equalsIgnoreCase(productName));
    }

    public List<String> getCartProducts() {
        return cartProducts.stream()
                .map(WebElement::getText)
                .toList();
    }

    public int getCartItemsCount() {
        return cartProducts.size();
    }

    public CheckoutPage goToCheckout() {
        click(checkoutBtn);
        return new CheckoutPage(driver);
    }

    public boolean isCartEmpty() {
        return cartProducts.isEmpty();
    }
}