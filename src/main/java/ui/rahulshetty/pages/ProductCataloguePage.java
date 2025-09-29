// ========================================
// ProductCataloguePage.java
// ========================================
package ui.rahulshetty.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.component.BaseComponent;

import java.util.List;

public class ProductCataloguePage extends BaseComponent {

    @FindBy(css = ".mb-3")
    private List<WebElement> products;

    @FindBy(css = "[routerlink*='cart']")
    private WebElement cartHeader;

    @FindBy(css = "[routerlink*='myorders']")
    private WebElement orderHeader;

    private By productsBy = By.cssSelector(".mb-3");
    private By addToCartBy = By.cssSelector(".card-body button:last-of-type");
    private By toastMessage = By.cssSelector("#toast-container");
    private By spinner = By.cssSelector(".ng-animating");

    public ProductCataloguePage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getProductList() {
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement getProductByName(String productName) {
        return products.stream()
                .filter(product -> product.findElement(By.cssSelector("b"))
                        .getText().equals(productName))
                .findFirst()
                .orElse(null);
    }

    public void addProductToCart(String productName) {
        WebElement prod = getProductByName(productName);
        if (prod != null) {
            waitForElementToAppear(addToCartBy);
            prod.findElement(addToCartBy).click();
            waitForElementToAppear(toastMessage);
            waitForElementToDisappear(spinner);
        }
    }

    public CartPage goToCart() {
        waitForElementToAppear(cartHeader);
        click(cartHeader);
        return new CartPage(driver);
    }

    public OrderPage goToOrders() {
        click(orderHeader);
        return new OrderPage(driver);
    }

    public boolean isProductAvailable(String productName) {
        return getProductByName(productName) != null;
    }

    public boolean isPageLoaded() {
        return !products.isEmpty();
    }
}
