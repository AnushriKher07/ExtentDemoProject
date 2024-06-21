package com.test;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.basePackage.BaseClass;

import junit.framework.Assert;

public class OrangeHRMTest extends BaseClass {
	
	 public OrangeHRMTest() {
	        super();
	    }

	    @BeforeMethod
	    public void setupTest() {
	        super.setup();
	    }

	    @AfterMethod
	    public void teardownTest(ITestResult result) throws Exception {
	        super.teardown(result);
	    }

	    @Test
	    public void loginPageTest() {
	        test = extent.createTest("loginPageTest");
	        WebElement imgElement = driver.findElement(By.xpath("//img[@alt='company-branding']"));
	        Assert.assertTrue(imgElement.isDisplayed());
	    }

	    @Test
	    public void loginTest() {
	        test = extent.createTest("loginTest");
	        driver.findElement(By.xpath("//input[@class='oxd-input oxd-input--active' and @name='username']")).sendKeys("Admin");
	        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Admin123");
	        driver.findElement(By.name("submit")).click();

	        String actualTitle = driver.getTitle();
	        String expectedTitle = "OrangeHRM";
	        Assert.assertEquals(actualTitle, expectedTitle);
	    }
	@Override
    public void teardown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Skipped Test case is: " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Pass Test case is: " + result.getName());
        }
        driver.quit();
    }
	

}
