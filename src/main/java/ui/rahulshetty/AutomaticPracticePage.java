package ui.rahulshetty;

import ui.component.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AutomaticPracticePage extends BaseComponent {
    WebDriver driver;

    private final By siblingButton = By.xpath("//header/div/button[1]/following-sibling::button[1]");
    private final By parentSiblingButton = By.xpath("//header/div/button[1]/parent::div/button[2]");

    public AutomaticPracticePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public String getSiblingButtonText() {
        return getText(siblingButton);
    }

    public String getParentSiblingButtonText() {
        return getText(parentSiblingButton);
    }
}
