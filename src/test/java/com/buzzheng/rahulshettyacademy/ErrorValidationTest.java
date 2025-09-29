// ========================================
// ErrorValidationTest.java
// ========================================
package com.buzzheng.rahulshettyacademy;

import com.buzzheng.TestComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import ui.rahulshetty.pages.*;

public class ErrorValidationTest extends BaseTest {

    @Test(priority = 1, groups = {"smoke", "ui", "login", "error"},
            description = "Verify login fails with incorrect password")
    public void testLoginWithIncorrectPassword() {
        String username = "scrashers@gmail.com";
        String incorrectPassword = "@1234";

        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        landingPage.loginApplication(username, incorrectPassword);

        Assert.assertTrue(landingPage.isErrorDisplayed(),
                "Error message should be displayed");
        Assert.assertEquals(landingPage.getErrorMessage(),
                "Incorrect email or password.",
                "Error message should indicate incorrect credentials");
    }

    @Test(priority = 2, groups = {"regression", "ui", "login", "error"},
            description = "Verify login fails with incorrect email")
    public void testLoginWithIncorrectEmail() {
        String incorrectUsername = "invalid@gmail.com";
        String password = "@QWE12345qwe";

        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        landingPage.loginApplication(incorrectUsername, password);

        Assert.assertTrue(landingPage.isErrorDisplayed(),
                "Error message should be displayed");
        Assert.assertEquals(landingPage.getErrorMessage(),
                "Incorrect email or password.",
                "Error message should indicate incorrect credentials");
    }

    @Test(priority = 3, groups = {"regression", "ui", "cart", "error"},
            description = "Verify product validation in cart")
    public void testProductNotInCart() {
        String username = "hengheng@gmail.com";
        String password = "Iamki000";
        String addedProduct = "ZARA COAT 3";
        String searchProduct = "ZARA COAT 33";

        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        ProductCataloguePage productCatalogue = landingPage.loginApplication(username, password);
        productCatalogue.addProductToCart(addedProduct);

        CartPage cartPage = productCatalogue.goToCart();
        boolean match = cartPage.verifyProductDisplay(searchProduct);

        Assert.assertFalse(match,
                "Product '" + searchProduct + "' should not be in cart");
    }

    @Test(priority = 4, groups = {"regression", "ui", "login", "error"},
            description = "Verify login fails with empty credentials")
    public void testLoginWithEmptyCredentials() {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        landingPage.loginApplication("", "");

        Assert.assertTrue(landingPage.isPageLoaded(),
                "Should remain on login page with empty credentials");
    }

    @Test(priority = 5, groups = {"regression", "ui", "product"},
            description = "Verify product catalogue loads")
    public void testProductCatalogueLoad() {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        ProductCataloguePage productCatalogue = landingPage.loginApplication(
                "scrashers@gmail.com", "@QWE12345qwe");

        Assert.assertTrue(productCatalogue.isPageLoaded(),
                "Product catalogue should be loaded");
        Assert.assertFalse(productCatalogue.getProductList().isEmpty(),
                "Product list should not be empty");
    }

    @Test(priority = 6, groups = {"regression", "ui", "product", "error"},
            description = "Verify searching for non-existent product")
    public void testNonExistentProduct() {
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo();
        ProductCataloguePage productCatalogue = landingPage.loginApplication(
                "scrashers@gmail.com", "@QWE12345qwe");

        boolean productExists = productCatalogue.isProductAvailable("NON EXISTENT PRODUCT");

        Assert.assertFalse(productExists,
                "Non-existent product should not be found");
    }
}