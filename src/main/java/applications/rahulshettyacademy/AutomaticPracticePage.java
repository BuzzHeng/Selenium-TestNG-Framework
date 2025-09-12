// src/main/java/applications/rahulshettyacademy/AutomaticPracticePage.java
package applications.rahulshettyacademy;

import core.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AutomaticPracticePage extends BasePage {
    private By siblingButton = By.xpath("//header/div/button[1]/following-sibling::button[1]");
    private By parentSiblingButton = By.xpath("//header/div/button[1]/parent::div/button[2]");

    public AutomaticPracticePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        navigateTo("https://rahulshettyacademy.com/AutomationPractice/");
    }

    public String getSiblingButtonText() {
        return getText(siblingButton);
    }

    public String getParentSiblingButtonText() {
        return getText(parentSiblingButton);
    }
}
