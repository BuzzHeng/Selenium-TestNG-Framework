// ========================================
// OrderPage.java
// ========================================
package ui.rahulshetty.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.component.BaseComponent;

import java.util.List;

public class OrderPage extends BaseComponent {

    @FindBy(css = "tr td:nth-child(3)")
    private List<WebElement> orderProducts;

    @FindBy(css = ".totalRow button")
    private WebElement checkoutBtn;

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    public boolean verifyOrderDisplay(String productName) {
        return orderProducts.stream()
                .anyMatch(orderProduct -> orderProduct.getText()
                        .equalsIgnoreCase(productName));
    }

    public List<String> getOrderedProducts() {
        return orderProducts.stream()
                .map(WebElement::getText)
                .toList();
    }

    public int getOrdersCount() {
        return orderProducts.size();
    }

    public boolean hasOrders() {
        return !orderProducts.isEmpty();
    }

    public boolean isPageLoaded() {
        return getCurrentUrl().contains("myorders");
    }
}