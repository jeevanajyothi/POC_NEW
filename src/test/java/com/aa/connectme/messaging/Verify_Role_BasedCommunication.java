package com.aa.connectme.messaging;



import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aa.connectme.pom.DashboardPage;
import com.aa.connectme.pom.LoginPage;
import com.aa.connectme.pom.LogoutPage;
import com.aa.connectme.pom.TopicsPanel;
import com.aa.connectme.pom.MessagingPanel;
import com.aa.connectme.util.Report;
import com.aa.connectme.util.UtilityFunction;
import com.aa.connectme.util.Xls_Reader;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Verify_Role_BasedCommunication extends UtilityFunction {


	//WebDriver driver;
	public static WebDriver driver,driver_browser,passeddriver;
	private UtilityFunction utilityFunction = new UtilityFunction();
	private LoginPage loginPage = new LoginPage();
	private DashboardPage dashboardPage = new DashboardPage();
	private TopicsPanel TopicsPanel = new TopicsPanel();
	private Report report = new Report();
	private Xls_Reader xlsReader = new Xls_Reader();
	private ExtentReports extent = utilityFunction.getExtent();
	private LogoutPage logoutPage = new LogoutPage();
	private MessagingPanel MP = new MessagingPanel();
	boolean res = false;
	String str = "Exception";
	String UserID,UN,Station;
	String Password,PSW,Gate;
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
			UN = xlsReader.getCellData("Send_Messages","Username",3);
			PSW = xlsReader.getCellData("Send_Messages","Password",3);
			Msg = xlsReader.getCellData("Send_Messages","Message",2);
			Station = xlsReader.getCellData("Send_Messages","Station",2);
			Gate = xlsReader.getCellData("Send_Messages","GateNumber",2);
			System.out.println("*****Verify_Role_BasedCommunication******");	
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
				
				//Verify the topic panel and click
				if(loginPage.changeGatenumber(Station,Gate)==true)
				{	
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - changeGatenumber", "Gatenumber changed successfully ").pass("Gatenumber changed successfully ");
					res = true;
					System.out.println("Topic panel verified and clicked on it.");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - changeGatenumber", "Gate number not changed").fail("Failed to changed Gatenumber Gatenumber successfully .");
					res = false;
					System.out.println("Unable to Verify the topic panel and unable to click");
				}
				
				//Verify the topic panel and click
				if(TopicsPanel.verifyTopicPanelandClick()==true)
				{	
					ExtentTest childTest1 = parentTest.createNode("STEP 4 - Verify topic panel and click ", "Verifying topic panel and click").pass("Topic panel is verified and clicked successfully");
					res = true;
					System.out.println("Topic panel verified and clicked on it.");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 4 - Verify topic panel and click ", "Verifying topic panel and click").fail("Failed to verify the topic panel and failed to click topic panel.");
					res = false;
					System.out.println("Unable to Verify the topic panel and unable to click");
				}
				
				
				
				//Selecting GateAgent role and click
				
				if(TopicsPanel.SelectingGAteAgentRole()==true)
				{	
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - SelectingGAteAgentRole", "Selecting GateAgent Role click").pass("Selecting GateAgent Role clicked successfully");
					res = true;
					System.out.println("Selecting GateAgent Role clicked successfully");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - SelectingGAteAgentRole", " Not able to Selecting GateAgent Role").fail("Failed to verify the topic panel and failed to click topic panel.");
					res = false;
					System.out.println("Failed to verify the topic panel and failed to click topic panel.");
				}
				
				if (MP.verifyAndclickPrioritybtn()== true)
				{
					System.out.println(" Verified and clicked the priority button ");
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - Click priority button", "Verify and click priority button.").pass("Verified and clicked the priority button");
					res = true;
				}else {
					System.out.println("Failed to Verify and clicking the priority button");
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - Click priority button", "Verify and click priority button.").fail("Failed to Verify and clicking the priority button.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				if (MP.sendMessage(Msg)== true)
				{
					System.out.println(" Message sent succesfully.");
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Send message", "Sending message to the conversation.").pass("Message sent succesfully");
					res = true;
				}else {
					System.out.println("Failed to send messages.");
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Send message", "Sending message to the conversation.").fail("Message not sent");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				
				utilityFunction.addDelay(3000);
				driver_browser= utilityFunction.initilizeDriver("Chrome","CCH_StageURL");
				
				utilityFunction.addDelay(9000);
				
				if (loginPage.login_CCH(UN,PSW,Station,driver_browser) == true){
					System.out.println("Login succesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 7 - CCH Launch & Login ", "Launching the application and login").pass("CCH Launch and login is successfull");
					res = true;
				}else {
					System.out.println("Login Unsuccesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 7 - CCH Launch & Login", "Launching the application and login").fail("CCH fails to login");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				
				if(loginPage.selectWorkGroup(driver_browser)==true)
				{
					System.out.println("Worgroup and Role retained");
					ExtentTest childTest1 = parentTest.createNode("STEP 8 - Selecting workgroup ", "Selecting the workgroup from available workgroup list").pass("Workgroup selection successfull");
					res = true;

				}else {
					System.out.println("Worgroup and Role not retained");
					ExtentTest childTest1 = parentTest.createNode("STEP 8 - Selecting workgroup ", "Selecting the workgroup from available workgroup list").fail("Failed to select the Workgroup from the list.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				if(loginPage.selectGateAgentRole(driver_browser)==true)
				{
					System.out.println("Role is changed succesfully");
					ExtentTest childTest1 = parentTest.createNode("STEP 9 - Selecting Role ", "Selecting the role from available role list").pass("Role selection successfull");
					res = true;
				}else{
					System.out.println("Role is not changed succesfully");
					ExtentTest childTest1 = parentTest.createNode("STEP 9 - Selecting workgroup ", "Selecting the workgroup from available workgroup list").fail("Failed to select the role from the list.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}

				//role selection
				if(loginPage.clickContinue(driver_browser)==true)
				{
					System.out.println("Save button clicked");
					ExtentTest childTest1 = parentTest.createNode("STEP 10 - Click on save button ", "Clicking on save button").pass("Save button clicked successfully");
					res = true;
				}else{
					System.out.println("Save button is not clicked");	
					ExtentTest childTest1 = parentTest.createNode("STEP 10 - Click on save button ", "Clicking on save button").fail("Failed to click the save button");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				
		
				if(loginPage.clickSelectAll(driver_browser)==true)
				{	
					ExtentTest childTest1 = parentTest.createNode("STEP 11 - Select all gate ", "Selecting all the gates for the terminal").pass("All the gates for the terminal is selected");
					res = true;
					System.out.println("Clicked on select all radion button");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 11 - Select all gate ", "Selecting all the gates for the terminal").fail("Failed to select all the gates for the terminal.");
					res = false;
					System.out.println("unable to select all radion button");
				}
				//Click on the Save button
				if(loginPage.clickApply(driver_browser)==true)
				{	
					System.out.println("Save button clicked");
					ExtentTest childTest1 = parentTest.createNode("STEP 12 - Click on save button ", "Clicking on save button").pass("Save button clicked successfully");
					res = true;
				}else{
					System.out.println("Save button is not clicked");	
					ExtentTest childTest1 = parentTest.createNode("STEP 12 - Click on save button ", "Clicking on save button").fail("Failed to click the save button");
					res = false;
				}
				
			
				//Verify the topic panel and click
				if(loginPage.verifyTopicPanelandClick(driver_browser)==true)
				{	
					ExtentTest childTest1 = parentTest.createNode("STEP 13 - Verify topic panel and click ", "Verifying topic panel and click").pass("Topic panel is verified and clicked successfully");
					res = true;
					System.out.println("Topic panel verified and clicked on it.");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 13 - Verify topic panel and click ", "Verifying topic panel and click").fail("Failed to verify the topic panel and failed to click topic panel.");
					res = false;
					System.out.println("Unable to Verify the topic panel and unable to click");
				}
		   
				
				if(loginPage.searchMessage(Msg,driver_browser) == true)
	             {
	           	  System.out.println("Search message succesful");
	 				ExtentTest childTest1 = parentTest.createNode("STEP 14 - Search messages", "Search message").pass("Searched message displayed successfully.");
	 				res = true;
	 			}else{
	 				System.out.println("Failed to search the message");
	 				ExtentTest childTest1 = parentTest.createNode("STEP 14 - Search messages", "Search message's").fail("Searched message not displayed.");
	 				str = utilityFunction.captureScreenshot();
	 				childTest1.addScreenCaptureFromPath(str);
	 				res = false; 
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
