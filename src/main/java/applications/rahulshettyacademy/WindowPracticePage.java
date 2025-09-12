// src/main/java/applications/rahulshettyacademy/WindowPracticePage.java
package applications.rahulshettyacademy;

import core.pages.BasePage;
import org.openqa.selenium.WebDriver;

public class WindowPracticePage extends BasePage {
    public WindowPracticePage(WebDriver driver) {
        super(driver);
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    public void openGoogle() {
        navigateTo("http://google.com");
    }

    public void openRahulShettyAcademy() {
        navigateTo("https://rahulshettyacademy.com");
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public void navigateForward() {
        driver.navigate().forward();
    }
}
