package com.aa.connectme.personallogin;

/******************************************************************************************************************************************************
 * 
 * Short Description :: verifying for dash board visibility personal login ,the flights in the subscribed gate & personal logged out application.
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
import com.aa.connectme.pom.MessagingPanel;
import com.aa.connectme.util.Report;
import com.aa.connectme.util.UtilityFunction;
import com.aa.connectme.util.Xls_Reader;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Launch_GATE_application_and_verify_the_full_page_content extends UtilityFunction {

	WebDriver driver;
	private UtilityFunction utilityFunction = new UtilityFunction();
	private LoginPage loginPage = new LoginPage();
	private DashboardPage dashboardPage = new DashboardPage();
	private Report report = new Report();
	private Xls_Reader xlsReader = new Xls_Reader();
	private ExtentReports extent = utilityFunction.getExtent();
	private LogoutPage logoutPage = new LogoutPage();
	private MessagingPanel MP = new MessagingPanel();
    boolean res = false;
    String str = "Exception";
    String UserID;
	String Password;
	String Msg;
	@Parameters({"BrowserName","Environment","TestResult"})
	@Test
	
	public void Test(String BrowserName, String Environment,String TestResult) throws Exception
	{	
	utilityFunction.strTestCaseName = this.getClass().getSimpleName();
	utilityFunction.ModuleName = this.getClass().getPackage().getName();
	utilityFunction.DateAndTime = currentDateAndTime();
	

	    int intLoop=xlsReader.rowCount("Send_Messages");
		driver = utilityFunction.initilizeDriver(BrowserName,Environment);
		for (int i=1; i<2; i++)
		{
			UserID = xlsReader.getCellData("Send_Messages","Username",i+1);
			Password = xlsReader.getCellData("Send_Messages","Password",i+1);
			Msg = xlsReader.getCellData("Send_Messages","Message",i+1);
				try {
				 parentTest = extent.createTest(this.getClass().getSimpleName());
				extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
				System.out.println("*****Launch_GATE_application_and_verify_the_full_page_content******");	
				System.out.println("-----------------------------------------");

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

				if (loginPage.loginFunctionVerification(UserID,Password) == true){
					System.out.println("Login page verification succesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Personal login function verification ", "Personal login function verification ").pass("Personal login function verified successful");
					res = true;
				}else {
					System.out.println("Login pae verification Unsuccesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Personal login function verification ", "Personal login function verification ").fail("Personal login function verified unsuccessful");
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