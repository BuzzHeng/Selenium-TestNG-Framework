// src/test/java/applications/rahulshettyacademy/tests/AutomaticPracticePageTest.java
package applications.rahulshettyacademy.tests;

import core.driver.WebDriverFactory;
import applications.rahulshettyacademy.AutomaticPracticePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class AutomaticPracticePageTest {
    private WebDriver driver;
    private AutomaticPracticePage page;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.createDriver("chrome");
        page = new AutomaticPracticePage(driver);
    }

    @Test
    public void testSiblingAndParentSiblingButtonText() {
        page.open();
        String siblingText = page.getSiblingButtonText();
        String parentSiblingText = page.getParentSiblingButtonText();

        System.out.println("Sibling Button: " + siblingText);
        System.out.println("Parent Sibling Button: " + parentSiblingText);

        Assert.assertNotNull(siblingText);
        Assert.assertNotNull(parentSiblingText);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
