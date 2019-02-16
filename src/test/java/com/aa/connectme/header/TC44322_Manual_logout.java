package com.aa.connectme.header;

/******************************************************************************************************************************************************
 * 
 * Short Description :: Test to verify manual logout functionality. 
 *                       
 * 
 * ****************************************************************************************************************************************************/

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.server.handler.GetElementText;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aa.connectme.util.Report;
import com.aa.connectme.util.TestData;
import com.aa.connectme.util.UtilityFunction;
import com.aa.connectme.util.Xls_Reader;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aa.connectme.pom.Constants;
import com.aa.connectme.pom.DashboardPage;
import com.aa.connectme.pom.LoginPage;
import com.aa.connectme.pom.LogoutPage;
import com.aa.connectme.pom.MessagingPanel;


public class TC44322_Manual_logout extends UtilityFunction {

	WebDriver driver;
	private UtilityFunction utilityFunction = new UtilityFunction();
	private LoginPage loginPage = new LoginPage();
	private LogoutPage logoutPage = new LogoutPage();
	private MessagingPanel MP = new MessagingPanel();
	private DashboardPage dashboardPage = new DashboardPage();
	private Report report = new Report();
	private Xls_Reader xlsReader = new Xls_Reader();
	private ExtentReports extent = utilityFunction.getExtent();
    boolean res = false;
    String str = "Exception";
	String Gate;
	String Station;
	String UserID;
	String Password;
	@Parameters({"BrowserName","Environment","TestResult"})
	@Test
	
	public void Test(String BrowserName, String Environment,String TestResult) throws Exception
	{	
	utilityFunction.strTestCaseName = this.getClass().getSimpleName();
	utilityFunction.ModuleName = this.getClass().getPackage().getName();
	utilityFunction.DateAndTime = currentDateAndTime();

		int intLoop=xlsReader.rowCount("Manual_logout");
	
		System.out.println("The number of rows present on the "+"Manual_logout"+" is = "+intLoop);
		driver = utilityFunction.initilizeDriver(BrowserName,Environment);	
		for (int i=1; i<2; i++)
		{
			Station = xlsReader.getCellData("Manual_logout","Station",i+1);
			Gate = xlsReader.getCellData("Manual_logout","GateNumber",i+1);
			UserID = xlsReader.getCellData("Manual_logout","UserID",i+1);
			Password = xlsReader.getCellData("Manual_logout","Password",i+1);
			//driver = utilityFunction.initilizeDriver(BrowserName,Environment);
			System.out.println("*****TC44322_Manual_logout******");	
			System.out.println("--------------------------------");
			try {
				// Method call to Login to the application and check whether login is successful or not
				 parentTest = extent.createTest(this.getClass().getSimpleName());
				extent.setAnalysisStrategy(AnalysisStrategy.CLASS);

				if (dashboardPage.dashboardVisibility() == true){
					ExtentTest childTest1 = parentTest.createNode("STEP 1 - Verify dashboard ", "Verify the dashboard component.").pass("Dashboard components is verified successfully");
					res = true;
					System.out.println("Dashboard is displayed succesfully");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 1 - Verify dashboard", "Verify the dashboard component.").fail("Failed to verify the dashboard components");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
					System.out.println("Failed to display the Dashboard page.");
				}	
	
				if (dashboardPage.clickOnLoginButton() == true){
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Click login button ", "Verify and click login button.").pass("Login button clicked succesfully");
					res = true;
					System.out.println("Login button clicked succesfully");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Click login button", "Verify and click login button.").fail("Failed to click login button.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
					System.out.println("Failed to click Login button.");
				}	

				if (loginPage.Personnelogin(UserID,Password) == true){
					System.out.println("Login succesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - Personal login ", "Personal login with user credentials").pass("Personal login successful");
					res = true;
				}else {
					System.out.println("Login Unsuccesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - Personal login", "Personal login with user credentials").fail("Personal login failed.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				
				if (logoutPage.logout() == true){
					System.out.println("Logout succesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 4 - Personal logout ", "Personal logout from the application").pass("Personal logout successful");
					res = true;
				}else {
					System.out.println("Logout Unsuccesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - Personal logout ", "Personal logout from the application").fail("Personal logout failed.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	
		}
	@Parameters({"TestResult" })
	@AfterMethod()
	public void afterMethod(String TestResult) {
		ArrayList<String> report = new ArrayList<>();
		try {
			String testCaseName = this.getClass().getSimpleName();
			report.add(TestResult);
			report.add(DateAndTime);
			report.add(ModuleName);
			report.add(testCaseName);
			report.add(parentTest.getStatus().toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(report.get(4).equals(Status.PASS.toString())) {
				UtilityFunction.testResult(report);
		}else {
			UtilityFunction.testResult(report);
		}
			extent.flush();
		}
	}
}