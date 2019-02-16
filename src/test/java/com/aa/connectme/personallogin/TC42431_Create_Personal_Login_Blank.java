package com.aa.connectme.personallogin;

/******************************************************************************************************************************************************
 * 
 * Short Description :: Verifying for personal logging error messages while entering blank data.
 *                       
 * ****************************************************************************************************************************************************/

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aa.connectme.pom.DashboardPage;
import com.aa.connectme.pom.LoginPage;
import com.aa.connectme.pom.LogoutPage;
import com.aa.connectme.util.Report;
import com.aa.connectme.util.UtilityFunction;
import com.aa.connectme.util.Xls_Reader;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TC42431_Create_Personal_Login_Blank extends UtilityFunction
{

	WebDriver driver;
	private UtilityFunction utilityFunction = new UtilityFunction();
	private LoginPage loginPage = new LoginPage();
	private DashboardPage dashboardPage = new DashboardPage();
	private LogoutPage logoutPage = new LogoutPage();
	private Report report = new Report();
	private Xls_Reader xlsReader = new Xls_Reader();
	private ExtentReports extent = utilityFunction.getExtent();
    boolean res = false;
    String str = "Exception";
    String Errmsg1;
	String Errmsg2;
	String Errmsg3;
	@Parameters({"BrowserName","Environment","TestResult"})
	@Test
	
	public void Test(String BrowserName, String Environment,String TestResult) throws Exception
	{	
	utilityFunction.strTestCaseName = this.getClass().getSimpleName();
	utilityFunction.ModuleName = this.getClass().getPackage().getName();
	utilityFunction.DateAndTime = currentDateAndTime();
	
	
		
	int intLoop=xlsReader.rowCount("Personal_Login_Blank");
	driver = utilityFunction.initilizeDriver(BrowserName,Environment);
	for (int i=1; i<2; i++)
	{
		Errmsg1 = xlsReader.getCellData("Personal_Login_Blank","UserIdErrorMsg",i+1);
		Errmsg2 = xlsReader.getCellData("Personal_Login_Blank","PwdErrMsg",i+1);
		Errmsg3 = xlsReader.getCellData("Personal_Login_Blank","UserIdErrorMsg",i+1);
		System.out.println("*****TC42431_Create_Personal_Login_Blank******");	
		System.out.println("---------------------------------------------------");
		
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

			if (loginPage.Personnelogin_Blank(Errmsg1,Errmsg2) == true){
				System.out.println("Personal Login error message verified");
				ExtentTest childTest1 = parentTest.createNode("STEP 2 - Personal login ", "Personal login with user credentials").pass("Personal Blank Login failed error message verified");
				res = true;
			}else {
				System.out.println("Personal Login error message not verified");
				ExtentTest childTest1 = parentTest.createNode("STEP 2 - Personal login", "Personal login with user credentials").fail("Personal blank Login failed error message not verified");
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