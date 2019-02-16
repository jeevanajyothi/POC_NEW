package com.aa.connectme.defects;

/******************************************************************************************************************************************************
 * 
 * Short Description :: verifying Searched messages for any topic not vanishing after clicking different topic by following scenarios
 *                        1 . Verifying for dash board presence.
 *                        2 . logging to Gate application with User id & Password.
 *                        3 . Verifying flights are refreshing not
 *                        4 . selecting Gate thread , sending few messages & verifying messages are displyed or not
 *                        5.Clicking and verifying with FLight number
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

public class DE67095_Searched_messages_for_any_topic_not_vanishing_after_clicking_different_topic extends UtilityFunction{

	WebDriver driver;
	private UtilityFunction utilityFunction = new UtilityFunction();
	private LoginPage loginPage = new LoginPage();
	private DashboardPage dashboardPage = new DashboardPage();
	private Report report = new Report();
	private LogoutPage logoutPage = new LogoutPage();
	private Xls_Reader xlsReader = new Xls_Reader();
	private MessagingPanel MP = new MessagingPanel();
	private ExtentReports extent = utilityFunction.getExtent();
    boolean res = false;
    String str = "Exception";
    String UserID;
	String Password;
	String Gate;
	String Msg;
	@Parameters({"BrowserName","Environment","TestResult"})
	@Test
	
	public void Test(String BrowserName, String Environment,String TestResult) throws Exception
	{	
	utilityFunction.strTestCaseName = this.getClass().getSimpleName();
	utilityFunction.ModuleName = this.getClass().getPackage().getName();
	utilityFunction.DateAndTime = currentDateAndTime();

	System.out.println("*****DE67095_Searched_messages_for_any_topic_not_vanishing_after_clicking_different_topic******");
	System.out.println("----------------------------------------------------------------------------");
	    int intLoop=xlsReader.rowCount("Send_Messages");
		System.out.println("The number of rows present on the "+"Send_Messages"+" is = "+intLoop);
		driver = utilityFunction.initilizeDriver(BrowserName,Environment);
		for (int i=1; i<2; i++)
		{
			UserID = xlsReader.getCellData("Send_Messages","Username",i+1);
			Password = xlsReader.getCellData("Send_Messages","Password",i+1);
			Msg = xlsReader.getCellData("Send_Messages","Message",i+1);
					
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
				if (dashboardPage.verifyFlightsRefresh(driver) == true) {
					System.out.println("Flights refreshed according to the time frame selected");
					ExtentTest childTest1 = parentTest.createNode("STEP 4 - Verify fights for different the time range ", "Flights refreshed according to the time range.").pass("Flights refreshed according to the time frame selected");
					res = true;
				}else {
					System.out.println("Issue displaying the flights within the selected time frame");
					ExtentTest childTest1 = parentTest.createNode("STEP 4 - Verify fights for different time range", "Flights refreshed according to the time range.").fail("Issue displaying the flights within the selected time frame");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				Thread.sleep(1000);
				if(dashboardPage.slectingGatethread()==true){
					System.out.println("gate thread is selected");
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - gate thread is selected ", "gate thread is selected.").pass("gate thread is selected is success");
					res = true;
				}else {
					System.out.println("gate thread is not selected");
					ExtentTest childTest1 = parentTest.createNode("STEP 5- gate thread is not selected", "gate thread is not selected.").fail("gate thread is selected is failed");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				if (MP.sendMessage(Msg)== true)
				{
					System.out.println(" The flights are listed in the gate tab ");
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Send message", "Sending message to the conversation.").pass("Message sent succesfully");
					res = true;
				}else {
					System.out.println("Messaging panel not verified");
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Send message", "Sending message to the conversation.").fail("Message not sent");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				if (MP.totalNoMsgBeforeSearch()>0)
				{
					System.out.println(" Messages are displayed ");
					ExtentTest childTest1 = parentTest.createNode("STEP 7 - Before search", "Total No of Msgs.").pass("Total No of Msgs are displayed.");
					res = true;
				}else {
					System.out.println("There is no messages.");
					ExtentTest childTest1 = parentTest.createNode("STEP 7 - Before search", "Total No of Msgs.").fail("There were no Msgs.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				String Rname = MP.returnRoleName();
				System.out.println(Rname);
				if (MP.searchRoleMessage(Rname)){
					System.out.println(" The flights are listed in the gate tab ");
					ExtentTest childTest1 = parentTest.createNode("STEP 8 - Verify Send message", "Verifying the sent message.").pass("Sent message verified.");
					res = true;
				}else {
					System.out.println("Messaging panel not verified");
					ExtentTest childTest1 = parentTest.createNode("STEP 8 - Verify Send message", "Verifying the sent message.").fail("Sent message not verified.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				Thread.sleep(2000);
				utilityFunction.clickElement("CURRENT_FLIGHT");
				Thread.sleep(2000);
				MP.ackMsgPopUp();
				Thread.sleep(2000);
				if (MP.clickAndVerifyCurFlightNum() == true)
				{
					System.out.println("Searched messages are vanishing for clicking different topic ");
					ExtentTest childTest1 = parentTest.createNode("STEP 9 - Click flight no", "Click flight no after search").pass("Searched messages are vanishing for clicking different topic");
					res = true;
				}else {
					System.out.println("Searched messages are not vanishing for clicking different topic");
					ExtentTest childTest1 = parentTest.createNode("STEP 9 - Click flight no", "Click flight no after search").fail("Searched messages are not vanishing for clicking different topic");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				
				if (logoutPage.logout() == true){
					System.out.println("Logout succesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 10 - Personal logout ", "Personal logout from the application").pass("Personal logout successful");
					res = true;
				}else {
					System.out.println("Logout Unsuccesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 10 - Personal logout ", "Personal logout from the application").fail("Personal logout failed.");
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


