package com.aa.connectme.messaging;

/******************************************************************************************************************************************************
 * 
 * Short Description :: Test to verify the messaging panel
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

public class TC60696_Verify_only_last_4hoursmessages_arebeingdisplayed_onmessage_panel extends UtilityFunction {

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
			System.out.println("*****Verifyonlythelast24hoursmessagesarebeingdisplayedonthemessagepanel******");	
			System.out.println("--------------------------------------");
			try {
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

				if (loginPage.Personnelogin(UserID,Password) == true){
					System.out.println("Login succesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Personal login ", "Personal login with user credentials").pass("Personal login successful");
					res = true;
				}else {
					System.out.println("Login Unsuccesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Personal login", "Personal login with user credentials").fail("Personal login failed.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				if (dashboardPage.slectingGatethread() == true){
					System.out.println("slected Gatethread displayed successfully.");
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Gate selecting ", "slected Gatethread displayed.").pass("slected Gatethread displayed successfully.");
					res = true;
				}else {
					System.out.println("Login Unsuccesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Gate selecting ", "slected Gatethread displayed.").fail("slected Gatethread displayed not successfully..");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				
				if (dashboardPage. verifyFlightsRefresh(driver)==true){
					System.out.println(" The flights are listed in the gate tab ");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - Verify fligts", "Verify the flights in the subscribed gate.").pass("Fligts for the subscribed gate is displayed successfully");
					res = true;
				}else {
					System.out.println("Flights are not yet loaded/displayed.");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - Verify fligts", "Verify the flights in the subscribed gate.").fail("all  flights departing from User logged gate  not Displayed");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}

				if (MP.verifyMsgPanel() == true){
					ExtentTest childTest1 = parentTest.createNode("STEP 4 - Verify the message panel", "Verify the message panel").pass("Message panel verified");
					res = true;
					System.out.println("Message panel verified");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 4 - Verify the message panel", "Verify the message panel").fail("Message panel not verified");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
					System.out.println("Message panel not verified");
				}	
				if (MP.verifyFirstMsgTimeWithin4hours() == true){
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - Verify the message time within 24 hour", "Verify the message time").pass("Message time verified");
					res = true;
					System.out.println("Message time verified");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - Verify the message time within 24 hour", "Verify the message time").fail("Message time verified fail");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
					System.out.println("Message time not verified");
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