package com.buzzheng.rahulshettyacademy;

import com.buzzheng.TestComponents.BaseTest;
import ui.rahulshetty.AutomaticPracticePage;
import org.testng.Assert;
import org.testng.annotations.*;

public class AutomaticPracticePageTest extends BaseTest {
    @Test
    public void getLoginButtonText() {
        AutomaticPracticePage page = new AutomaticPracticePage(driver);
        page.navigateTo("https://rahulshettyacademy.com/AutomationPractice/");
        String siblingText = page.getSiblingButtonText();
        String parentSiblingText = page.getParentSiblingButtonText();

        System.out.println("Sibling Button: " + siblingText);
        System.out.println("Parent Sibling Button: " + parentSiblingText);

        Assert.assertNotNull(siblingText);
        Assert.assertNotNull(parentSiblingText);
        Assert.assertNotEquals(siblingText, parentSiblingText);
    }
}
