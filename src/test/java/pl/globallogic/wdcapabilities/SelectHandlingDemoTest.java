package pl.globallogic.wdcapabilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SelectHandlingDemoTest extends BaseCapabilitiesDemoTest{
    @Test
    public void shouldSelectOptionByVisibleTextInDropdown(){
        driver.get(WEB_FORM);
        WebElement selectElement = driver.findElement(By.name("my-select"));
        Select select = new Select(selectElement);
        String requiredOptionText = "Three";
        //select.selectByVisibleText(requiredOptionText);
        select.selectByIndex(3);
        Assert.assertEquals(requiredOptionText, select.getFirstSelectedOption().getText());
        sleep();
    }

}
