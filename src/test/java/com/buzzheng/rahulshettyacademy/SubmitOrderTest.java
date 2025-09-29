// ========================================
// SubmitOrderTest.java
// ========================================
package com.buzzheng.rahulshettyacademy;

import com.buzzheng.TestComponents.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.rahulshetty.pages.*;

import java.util.List;

public class SubmitOrderTest extends BaseTest {

    private static final String USERNAME = "scrashers@gmail.com";
    private static final String PASSWORD = "@QWE12345qwe";
    private static final String PRODUCT_NAME = "ZARA COAT 3";

    @Test(priority = 1, groups = {"smoke", "e2e", "purchase"},
            description = "Complete order submission workflow")
    public void testSubmitOrder() {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        ProductCataloguePage productCatalogue = landingPage.loginApplication(USERNAME, PASSWORD);

        List<WebElement> products = productCatalogue.getProductList();
        Assert.assertFalse(products.isEmpty(), "Product catalogue should not be empty");
        Assert.assertTrue(productCatalogue.isProductAvailable(PRODUCT_NAME),
                "Product '" + PRODUCT_NAME + "' should be available");

        productCatalogue.addProductToCart(PRODUCT_NAME);

        CartPage cartPage = productCatalogue.goToCart();
        boolean match = cartPage.verifyProductDisplay(PRODUCT_NAME);
        Assert.assertTrue(match, "Product should be displayed in cart");

        CheckoutPage checkoutPage = cartPage.goToCheckout();
        Assert.assertTrue(checkoutPage.isPageLoaded(), "Checkout page should be loaded");

        checkoutPage.completeCountrySelection("india");
        ConfirmationPage confirmationPage = checkoutPage.placeOrder();

        String confirmMessage = confirmationPage.getConfirmMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."),
                "Order confirmation message should be displayed");
        Assert.assertTrue(confirmationPage.isOrderConfirmed(),
                "Order should be confirmed");
    }

    @Test(priority = 2, groups = {"smoke", "e2e", "purchase"},
            dependsOnMethods = {"testSubmitOrder"},
            description = "Verify order appears in order history")
    public void testOrderHistory() {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        ProductCataloguePage productCatalogue = landingPage.loginApplication(USERNAME, PASSWORD);

        OrderPage orderPage = productCatalogue.goToOrders();

        Assert.assertTrue(orderPage.verifyOrderDisplay(PRODUCT_NAME),
                "Product '" + PRODUCT_NAME + "' should appear in order history");
    }

    @Test(priority = 3, groups = {"regression", "ui", "cart"},
            description = "Verify multiple products can be added to cart")
    public void testMultipleProductsInCart() {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        ProductCataloguePage productCatalogue = landingPage.loginApplication(USERNAME, PASSWORD);

        productCatalogue.addProductToCart("ZARA COAT 3");
        productCatalogue.addProductToCart("ADIDAS ORIGINAL");

        CartPage cartPage = productCatalogue.goToCart();

        Assert.assertTrue(cartPage.verifyProductDisplay("ZARA COAT 3"),
                "First product should be in cart");
        Assert.assertTrue(cartPage.verifyProductDisplay("ADIDAS ORIGINAL"),
                "Second product should be in cart");
        Assert.assertEquals(cartPage.getCartItemsCount(), 2,
                "Cart should contain 2 items");
    }

    @Test(priority = 4, groups = {"regression", "ui", "checkout"},
            description = "Verify checkout page loads correctly")
    public void testCheckoutPageLoad() {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        ProductCataloguePage productCatalogue = landingPage.loginApplication(USERNAME, PASSWORD);
        productCatalogue.addProductToCart(PRODUCT_NAME);

        CartPage cartPage = productCatalogue.goToCart();
        CheckoutPage checkoutPage = cartPage.goToCheckout();

        Assert.assertTrue(checkoutPage.isPageLoaded(),
                "Checkout page should be loaded");
        Assert.assertTrue(checkoutPage.isPlaceOrderEnabled(),
                "Place order button should be enabled");
    }

    @Test(priority = 5, groups = {"e2e"},
            description = "Complete purchase workflow with different user")
    public void testSubmitOrderDifferentUser() {
        String username = "hengheng@gmail.com";
        String password = "Iamki000";
        String productName = "ADIDAS ORIGINAL";

        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        ProductCataloguePage productCatalogue = landingPage.loginApplication(username, password);

        productCatalogue.addProductToCart(productName);

        CartPage cartPage = productCatalogue.goToCart();
        Assert.assertTrue(cartPage.verifyProductDisplay(productName));

        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.completeCountrySelection("india");

        ConfirmationPage confirmationPage = checkoutPage.placeOrder();
        Assert.assertTrue(confirmationPage.isOrderConfirmed());
    }
}