package ui.rahulshetty.component;

import ui.component.BaseComponent;
import org.openqa.selenium.WebDriver;

public class rahulAbstractComponent extends BaseComponent {

    WebDriver driver;

    public rahulAbstractComponent(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

}
