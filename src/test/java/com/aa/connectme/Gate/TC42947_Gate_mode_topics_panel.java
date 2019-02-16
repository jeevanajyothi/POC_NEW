package com.aa.connectme.Gate;

/******************************************************************************************************************************************************
 * 
 * Short Description :: verifying Dash board visibility & Gate tab presence 
 *                       
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

public class TC42947_Gate_mode_topics_panel extends UtilityFunction {

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
			UserID = xlsReader.getCellData("Send_Messages","Username",2);
			Password = xlsReader.getCellData("Send_Messages","Password",2);
			Msg = xlsReader.getCellData("Send_Messages","Message",2);
			try {
				 parentTest = extent.createTest(this.getClass().getSimpleName());
				extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
				System.out.println("*****DE61358_Not_able_to_search_using_sender_role_name_message_content******");	
				System.out.println("----------------------------------------------------------------------------");

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

				if (dashboardPage.verifyGateNumber() == true){
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Verify the gate number", "Verify the gate number in gate tab").pass("Gate number verified");
					res = true;
					System.out.println("Gate number verified");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Verify the gate number", "Verify the gate number in gate tab").fail("Gate number not verified");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
					System.out.println("Gate number not verified");
				}	
				
			} catch (Exception e) {
				e.printStackTrace();
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