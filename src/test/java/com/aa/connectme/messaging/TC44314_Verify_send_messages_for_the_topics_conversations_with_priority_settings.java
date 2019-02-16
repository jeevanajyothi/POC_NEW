package com.aa.connectme.messaging;

/******************************************************************************************************************************************************
 * 
 * Short Description :: Verify_send_messages_for_the_topics_conversations_with_priority_settings
                      
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

public class TC44314_Verify_send_messages_for_the_topics_conversations_with_priority_settings extends UtilityFunction
{
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
	String MessageText;
	@Parameters({"BrowserName","Environment","TestResult"})
	@Test
	
	public void Test(String BrowserName, String Environment,String TestResult) throws Exception
	{	
	utilityFunction.strTestCaseName = this.getClass().getSimpleName();
	utilityFunction.ModuleName = this.getClass().getPackage().getName();
	utilityFunction.DateAndTime = currentDateAndTime();
	
	    int intLoop=xlsReader.rowCount("Send_Priority_Msg");
		driver = utilityFunction.initilizeDriver(BrowserName,Environment);
		for (int i=1; i<2; i++)
		{
			UserID = xlsReader.getCellData("Send_Priority_Msg","Username",i+1);
			Password = xlsReader.getCellData("Send_Priority_Msg","Password",i+1);
			Msg = xlsReader.getCellData("Send_Priority_Msg","Message",i+1);
			System.out.println("*****TC44314_Verify_send_messages_for_the_topics_conversations_with_priority_settings******");	
			System.out.println("-------------------------------------------------------------------------------------------");
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
				if (dashboardPage.verifyFlightsRefresh(driver) == true) {
					System.out.println("Flights refreshed according to the time frame selected");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - Verify fights for different the time range ", "Flights refreshed according to the time range.").pass("Flights refreshed according to the time frame selected");
					res = true;
				}else {
					System.out.println("Issue displaying the flights within the selected time frame");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - Verify fights for different time range", "Flights refreshed according to the time range.").fail("Issue displaying the flights within the selected time frame");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				
				if (MP.verifyAndclickPrioritybtn()== true)
				{
					System.out.println(" Verified and clicked the priority button ");
					ExtentTest childTest1 = parentTest.createNode("STEP 4 - Click priority button", "Verify and click priority button.").pass("Verified and clicked the priority button");
					res = true;
				}else {
					System.out.println("Failed to Verify and clicking the priority button");
					ExtentTest childTest1 = parentTest.createNode("STEP 4 - Click priority button", "Verify and click priority button.").fail("Failed to Verify and clicking the priority button.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				
				for (int j = 1; j < 2; j++) {
					// Send a message
					MessageText = MP.sendMessageWithRetrieveText("Test" + j);
					if (MessageText != null) {
						System.out.println("Message is sent successfully");
						ExtentTest childTest1 = parentTest.createNode("STEP 5 - Send message", "Send a message")
								.pass("Message sent");
						res = true;
					} else {
						System.out.println("Issue sending the message");
						ExtentTest childTest1 = parentTest.createNode("STEP 5 - Send message", "Send a message")
								.fail("Message not sent");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						res = false;
					}
				}
				if (MP.searchsendMessage(MessageText)){
					System.out.println("Searched the previous sent messaage.");
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Verify Send message", "Verifying the sent message.").pass("Sent message verified.");
					res = true;
				}else {
					System.out.println("Failed to Search previous sent messaage.");
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Verify Send message", "Verifying the sent message.").fail("Sent message not verified.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				/*if (MP.sendMessage(Msg)== true)
				{
					System.out.println(" Message sent succesfully.");
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - Send message", "Sending message to the conversation.").pass("Message sent succesfully");
					res = true;
				}else {
					System.out.println("Failed to send messages.");
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - Send message", "Sending message to the conversation.").fail("Message not sent");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				Thread.sleep(3000);
				if (dashboardPage. verifyFlightTopicsandMsgs() == true){
					System.out.println(" The flights are listed in the gate tab ");
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Verify fligts", "Verify the flights in the subscribed gate.").pass("Fligts for the subscribed gate is displayed successfully");
					res = true;
				}else {
					System.out.println("Flights are not yet loaded/displayed.");
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Verify fligts", "Verify the flights in the subscribed gate.").fail("all  flights departing from User logged gate  not Displayed");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}*/
				Thread.sleep(3000);
				if (dashboardPage. verifyIconOnPriorityMessages() == true){
					System.out.println(" The flights are listed in the gate tab ");
					ExtentTest childTest1 = parentTest.createNode("STEP 7 - Verify the Priority Icon on Message", "Verify the Priority Icon on Message.").pass("Verify the Priority Icon on Message is displayed successfully");
					res = true;
				}else {
					System.out.println("Flights are not yet loaded/displayed.");
					ExtentTest childTest1 = parentTest.createNode("STEP 7 - Verify the Priority Icon on Message", "Verify the Priority Icon on Message.").fail("all  flights departing from User logged gate  not Displayed");
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