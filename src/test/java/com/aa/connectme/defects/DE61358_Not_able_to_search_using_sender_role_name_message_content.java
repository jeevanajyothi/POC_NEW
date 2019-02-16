package com.aa.connectme.defects;

/******************************************************************************************************************************************************
 * 
 * Short Description :: verify the following scenarios 
 *                         1 . Launching and login to the CCH application.
 *                         2 . Verifying the dash board visibility and logging in Gate application with UserId & password.
 *                         3 . Sending few messages to selected Gate / Flight topic and it's verifying total no.of messages before searching.
 *                         4 . searching message with Rolename and verifying before search & after search message count is matching or not.
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

public class DE61358_Not_able_to_search_using_sender_role_name_message_content extends UtilityFunction {

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
	String MessageText;
	@Parameters({"BrowserName","Environment","TestResult"})
	@Test
	
	public void Test(String BrowserName, String Environment,String TestResult) throws Exception
	{	
	utilityFunction.strTestCaseName = this.getClass().getSimpleName();
	utilityFunction.ModuleName = this.getClass().getPackage().getName();
	utilityFunction.DateAndTime = currentDateAndTime();
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
				System.out.println("*****DE61358_Not_able_to_search_using_sender_role_name_message_content****");	
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

				for (int j = 1; j < 2; j++) {
					// Send a message
					MessageText = MP.sendMessageWithRetrieveText("Test" + j);
					if (MessageText != null) {
						System.out.println("Message is sent successfully");
						ExtentTest childTest1 = parentTest.createNode("STEP 3 - Send message", "Send a message")
								.pass("Message sent");
						res = true;
					} else {
						System.out.println("Issue sending the message");
						ExtentTest childTest1 = parentTest.createNode("STEP 3 - Send message", "Send a message")
								.fail("Message not sent");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						res = false;
					}
				}
				if (MP.totalNoMsgBeforeSearch()>0)
				{
					System.out.println(" Messages are displayed ");
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - Before search", "Total No of Msgs.").pass("Total No of Msgs are displayed.");
					res = true;
				}else {
					System.out.println("There is no messages.");
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - Before search", "Total No of Msgs.").fail("There were no Msgs.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				String Rname = MP.returnRoleName();
				System.out.println(Rname);
				
				if (MP.searchRoleMessage(Rname)){
					System.out.println(" The flights are listed in the gate tab ");
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Verify Send message", "Verifying the sent message.").pass("Sent message verified.");
					res = true;
				}else {
					System.out.println("Messaging panel not verified");
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Verify Send message", "Verifying the sent message.").fail("Sent message not verified.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				
				if (MP.totalNoMsgAfterSearch()  <= MP.totalNoMsgBeforeSearch())
				{
					System.out.println(" Messages are displayed ");
					ExtentTest childTest1 = parentTest.createNode("STEP 7 - After search", "Total No of Msgs.").pass("Messages are verified by role search");
					res = true;
				}else {
					System.out.println("There is no messages.");
					ExtentTest childTest1 = parentTest.createNode("STEP 7 - After search", "Total No of Msgs.").fail("Failed to verify the role search.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				
				if (logoutPage.logout() == true){
					System.out.println("Logout succesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 8 - Personal logout ", "Personal logout from the application").pass("Personal logout successful");
					res = true;
				}else {
					System.out.println("Logout Unsuccesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 8 - Personal logout ", "Personal logout from the application").fail("Personal logout failed.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			break;
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