package com.basePackage;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class BaseClass {
	public WebDriver driver;
	public static ExtentHtmlReporter htmlReporter; 
	public static ExtentReports extent;
	public ExtentTest test;
	
	@BeforeSuite
	public void setExtent() {
		extent = new ExtentReports();
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/ExtentReport/MyReport.html");
		extent.attachReporter(htmlReporter);

		htmlReporter.config().setDocumentTitle("Automation Test Report");
		htmlReporter.config().setReportName("OrangeHRM Test Automation Report");
		htmlReporter.config().setTheme(Theme.STANDARD);
		
		extent.setSystemInfo("HostName", "MyHost");
		extent.setSystemInfo("ProjectName", "OrangeHRM");
		extent.setSystemInfo("Tester", "Anushri");
		extent.setSystemInfo("OS", "Win11");
		extent.setSystemInfo("Browser", "Firefox");
	
	// Initialize WebDriver
    System.setProperty("webdriver.gecko.driver", "C:\\Automation\\geckodriver-v0.34.0-win64\\geckodriver.exe");
    driver = new FirefoxDriver();
    driver.manage().window().maximize();
}
	@AfterSuite
	public void endReport() {
		extent.flush();
	}


	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.gecko.driver","C:\\Automation\\geckodriver-v0.34.0-win64\\geckodriver.exe");
 	   driver = new FirefoxDriver();
 	   driver.manage().window().maximize();
 	   driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		
	}
	@AfterMethod
	public void teardown(ITestResult result) throws IOException {
		if(result.getStatus()==ITestResult.FAILURE) {
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " -Test Case Failed", ExtentColor.RED));
			
			//String pathString=BaseClass.screenshot(driver,result.getName());
				//test.addScreenCaptureFromPath(pathString);
			
			
			  String screenshotPath = captureScreenshot(driver, result.getName());
			  //System.out.println("Screenshot Path: " + screenshotPath);
			  test.addScreenCaptureFromPath(screenshotPath); // Attach screenshot to report
			 
			
		}else if(result.getStatus()==ITestResult.SKIP) {
			test.log(Status.SKIP,"Skipped Test case is: " +result.getName());
		}else if(result.getStatus()==ITestResult.SUCCESS) {
			test.log(Status.PASS,"Pass Test case is: "+result.getName());
			
		}
		driver.quit();
	}
	
	public static String captureScreenshot(WebDriver driver, String screenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/test-output/Screenshots/" + screenshotName + ".jpg"; // Corrected path
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }   
	 
	
		
		
		
	
	

}
