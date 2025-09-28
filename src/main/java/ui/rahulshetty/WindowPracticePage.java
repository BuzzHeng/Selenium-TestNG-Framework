package ui.rahulshetty;

import ui.component.BaseComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class WindowPracticePage extends BaseComponent {
    WebDriver driver;

    public WindowPracticePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public void navigateForward() {
        driver.navigate().forward();
    }
}
