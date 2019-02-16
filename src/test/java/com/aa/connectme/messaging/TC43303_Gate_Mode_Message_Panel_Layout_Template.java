package com.aa.connectme.messaging;

/******************************************************************************************************************************************************
 * 
 * Short Description :: Test To Verify If Gate mode message panel layout template.
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

public class TC43303_Gate_Mode_Message_Panel_Layout_Template extends UtilityFunction {

	WebDriver driver;
	private UtilityFunction utilityFunction = new UtilityFunction();
	private LoginPage loginPage = new LoginPage();
	private DashboardPage dashboardPage = new DashboardPage();
	private Report report = new Report();
	private LogoutPage logoutPage = new LogoutPage();
	private Xls_Reader xlsReader = new Xls_Reader();
	private ExtentReports extent = utilityFunction.getExtent();
	private MessagingPanel MP = new MessagingPanel();
    boolean res = false;
    String str = "Exception";
    String UserID;
	String Password;
	String Gate;
	String Station;
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
			System.out.println("*****TC43303_Gate_Mode_Message_Panel_Layout_Template******");	
			System.out.println("----------------------------------------------------------");		
			System.out.println("Browser name :"+BrowserName+ ",++++ Environment: "+Environment+ ",++++  Gate number: "+ Gate + " ,++++  station name: "+ Station);
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
				
					if (MP.sendMessage(Msg)== true)
					{
						System.out.println(" The flights are listed in the gate tab ");
						ExtentTest childTest1 = parentTest.createNode("STEP 4 - Send message", "Sending message to the conversation.").pass("Message sent succesfully");
						res = true;
					}else {
						System.out.println("Messaging panel not verified");
						ExtentTest childTest1 = parentTest.createNode("STEP 4 - Send message", "Sending message to the conversation.").fail("Message not sent");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						res = false;
					}

				if (dashboardPage.verifyUserInitialsOnMsgs() == true){
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - Verify username initials ", "Verify username initials on each messages").pass("Verified username initials on each messages");
					res = true;
					System.out.println("Username initials are verified");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 5 - Verify username initials", "Verify username initials on each messages").fail("Failed to verify the username initials on each messages");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
					System.out.println("Username initials are not verified");
				}	
				
				if(utilityFunction.Validate("MP_SX_RX_MSG_TIMINGS")== true)
				{
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Verify timings ", "Verify time on each message").pass("Verified sent or received timings on each messages");
					res = true;
					System.out.println("Sent or received timings are verified");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Verify timings", "Verify time on each message").fail("Failed to Verify the sent or received timings on each messages");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
					System.out.println("Sent or received timings are not verified");
				}	
				
				if (logoutPage.logout() == true){
					System.out.println("Logout succesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Personal logout ", "Personal logout from the application").pass("Personal logout successful");
					res = true;
				}else {
					System.out.println("Logout Unsuccesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 6 - Personal logout ", "Personal logout from the application").fail("Personal logout failed.");
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
