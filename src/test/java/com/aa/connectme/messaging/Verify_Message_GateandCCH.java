package com.aa.connectme.messaging;

// * Short Description :: sending message from Gateapplication and verifying the same message in CCHapplication with same selected Gate. 
 

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aa.connectme.pom.Cchrequeriments;
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

public class Verify_Message_GateandCCH extends UtilityFunction {

	WebDriver driver;
	public static WebDriver driver_wrapper, driver_browser, passeddriver;
	private UtilityFunction utilityFunction = new UtilityFunction();
	private LoginPage loginPage = new LoginPage();
	private DashboardPage dashboardPage = new DashboardPage();
	private MessagingPanel MP=new MessagingPanel();
	private LogoutPage logoutPage = new LogoutPage();
	private Report report = new Report();
	private Xls_Reader xlsReader = new Xls_Reader();
	private ExtentReports extent = utilityFunction.getExtent();
	private Cchrequeriments Cchr=new Cchrequeriments();
	boolean res = false;
    String str = "Exception";
    String UserID;
	String Password;
	String Gate;
	String Station;
	String message;
	String UN;
	String PWD;
	String MessageText;
	//String Station;
	String TopicName;
	String Uname1;
	String Uname2;
	String Uname3;
	@Parameters({"BrowserName","Environment","TestResult"})
	@Test
	
	public void Test(String BrowserName, String Environment,String TestResult) throws Exception
	{	
		utilityFunction.strTestCaseName = this.getClass().getSimpleName();
		utilityFunction.ModuleName = this.getClass().getPackage().getName();
		utilityFunction.DateAndTime = currentDateAndTime();
		
			int intLoop=xlsReader.rowCount("Participants_list");
			System.out.println("The number of rows present on the "+"Readonly_Messages"+" is = "+intLoop);
			driver = utilityFunction.initilizeDriver(BrowserName,Environment);
			for (int i=1; i<2; i++)
			{
				UserID = xlsReader.getCellData("Participants_list","Username",i+1);
				Password = xlsReader.getCellData("Participants_list","Password",i+1);
				Station = xlsReader.getCellData("Readonly_Messages","Station",i+1);
				Gate = xlsReader.getCellData("Readonly_Messages","GateNumber",i+1);
				System.out.println("*****Verify_Message_GateandCCH******");	
				System.out.println("---------------------------------------------------");
						
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
					
					if(dashboardPage.slectingGatethread()==true) {
						System.out.println("Clicking on GateThread");
						ExtentTest childTest1 = parentTest.createNode("STEP 3 -Function: click on Gate Thread", "validating the Gate Thread and YES POPUP").pass("Gate Thread is clicked");
						System.out.println("Step-3 executed");
						res = true;
					}else {
						System.out.println("Failed in Clicking on GateThread");
						ExtentTest childTest1 = parentTest.createNode("STEP 3 - Personal login", "Unable in validating the Gate Thread and YES POPUP").fail("clicking on Gate Thread is failed");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						res = false;
					}
					if(dashboardPage.ManualChangeGate()==true) {
						System.out.println("Clicking on manualGate");
						ExtentTest childTest1 = parentTest.createNode("STEP 4 -Function:select Gate Manual", "manual subsraction of Gate ").pass("Gate number enterd manually");
						System.out.println("Step-4 executed");
						res = true;
					}else {
						System.out.println("Failed in Clicking on manualGate");
						ExtentTest childTest1 = parentTest.createNode("STEP 4 -Function:select Gate Manual", "manual subsraction of Gate is not entered").fail("unabel to enter the Gatenumber");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						res = false;	
					}
					MessageText = MP.sendMessageWithRetrieveText("Test");
					if(MessageText!= null) {
						System.out.println(" Message sent sucesfully ");
						ExtentTest childTest1 = parentTest.createNode("STEP 5 - Send message", "Sending message to the conversation.").pass("Message sent succesfully");
						res = true;
					}else {
						System.out.println("Unbale to send message");
						ExtentTest childTest1 = parentTest.createNode("STEP 5 - Send message", "Failed in Sending message to the conversation.").fail("Message not sent");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						res = false;	
					}
					Thread.sleep(2000);
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
					driver.close();
								
					//UtilityFunction.minimizeBrowser();
					int intLoop1=xlsReader.rowCount("Verify_topic_panel");
					System.out.println("The number of rows present on the "+"Verify_topic_panel"+" is = "+intLoop1);

						UN = xlsReader.getCellData("Verify_topic_panel","UserName",2);
						PWD = xlsReader.getCellData("Verify_topic_panel","Password",2);
						Station = xlsReader.getCellData("Verify_topic_panel","Station",2);
						
					driver_browser = utilityFunction.initilizeDriver("Chrome", "CCH_StageURL");
					if (Cchr.Cchlogin(UN, PWD, Station, driver_browser) == true) {
						System.out.println("Login succesfull");
						ExtentTest childTest1 = parentTest
								.createNode("STEP 6 - CCH Launch & Login ", "Launching the application and login")
								.pass("CCH Launch and login is successfull");
						res = true;
					} else {
						System.out.println("Login Unsuccesfull");
						ExtentTest childTest1 = parentTest
								.createNode("STEP 6 - CCH Launch & Login", "Failed in Launching the application and login")
								.fail("CCH fails to login");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						res = false;
					}
					
					if(Cchr.selectWorkGroup()==true)
					{
						System.out.println("Worgroup and Role retained");
						ExtentTest childTest1 = parentTest.createNode("STEP 7 - Selecting workgroup ", "Selecting the workgroup from available workgroup list").pass("Workgroup selection successfull");
						res = true;
						
					}else {
						System.out.println("Worgroup and Role not retained");
						ExtentTest childTest1 = parentTest.createNode("STEP 7 - Selecting workgroup ", "Selecting the workgroup from available workgroup list is not valid").fail("Failed to select the Workgroup from the list.");
						res = false;
						
					}
					if(Cchr.selectRole()==true) {
						System.out.println("Role is changed succesfully");
						ExtentTest childTest1 = parentTest.createNode("STEP 8 - Selecting Role ", "Selecting the role from available role list").pass("Role selection successfull");
						res = true;
					}else{
						System.out.println("Role is not changed ");
						ExtentTest childTest1 = parentTest.createNode("STEP 8 - Selecting Role ", "Failed in Selecting the Role from available workgroup list").fail("Failed to select the role from the list.");
						res = false;	
					}
					if(Cchr.clickContinue()==true) {
						System.out.println("Save button clicked");
						ExtentTest childTest1 = parentTest.createNode("STEP 9 - Click on save button ", "Clicking on save button").pass("Save button clicked successfully");
						res = true;
					}else{
						System.out.println("Save button is not clicked");	
						ExtentTest childTest1 = parentTest.createNode("STEP 9 - Click on save button ", "Failed in Clicking on save button").fail("Failed to click the save button");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						res = false;
					}
					if(Cchr.clickSecondSelectedGate()==true) {
						ExtentTest childTest1 = parentTest.createNode("STEP 10 - Click to select Secondgate ", "Selecting Second gate in the terminal").pass("Selecting Second gate is sucessfull");
						res = true;
						System.out.println("Clicked on select all radion button");
					}else{
						ExtentTest childTest1 = parentTest.createNode("STEP 10 - Click to select Secondgate ", "Failed in Selecting Second gate gates in the terminal").fail("Unable to select Second gate");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						res = false;
						System.out.println("unable to select Second gate");	
						}
					if(Cchr.clickApply()==true) {
						System.out.println("Apply button clicked");
						ExtentTest childTest1 = parentTest.createNode("STEP 11 - Click on save button ", "Clicking on save button").pass("APPLY button clicked successfully");
						res = true;
					}else{
						System.out.println("Apply button is not clicked");	
						ExtentTest childTest1 = parentTest.createNode("STEP 11 - Click on save button ", "Failed in Clicking on save button").fail("Failed to click the APPLY button");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						System.out.println("unable to select Second gate");
						res = false;
					}
					if(Cchr.verifySidePanel()==true) {
						System.out.println("Validating the Dashboard");
						ExtentTest childTest1 = parentTest.createNode("STEP 12 - Validating the Dashboard ", "DashBoard visibility is true").pass("Visibility of DashBoard is successfully");
						res = true;
					}else{
						System.out.println("Apply button is not clicked");	
						ExtentTest childTest1 = parentTest.createNode("STEP 12 - Validating the Dashboard ", "Failed in DashBoard visibility is true").fail("Failed in Visibility of DashBoard is successfully");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						System.out.println("unable to select Second gate");
						res = false;
					}
					if(Cchr.searchMessage(MessageText)==true) {
						System.out.println("Message is found");
						ExtentTest childTest1 = parentTest.createNode("STEP 13 - Search message", "Search a message").pass("Searching message successful");
						res = true;
					}else{
						System.out.println("Message not found");
						ExtentTest childTest1 = parentTest.createNode("STEP 13 - Search message", "Unable to Search a message").fail("Searching message  unsuccessful");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						res = false;
					}
					
					if (Cchr.logout() == true) {
						ExtentTest childTest1 = parentTest
								.createNode("STEP 14 - Click on logout button", "Clicking on logout button")
								.pass("Logout button clicked successfully");
						res = true;
						System.out.println("Logout succesful");
					} else {
						ExtentTest childTest1 = parentTest
								.createNode("STEP 14 - Click on logout button", "Logout is failed")
								.fail("Logout is failed.");
						str = utilityFunction.captureScreenshot();
						childTest1.addScreenCaptureFromPath(str);
						res = false;
						System.out.println("Logout is failed");
					}			
							} 
				catch (Exception e) {
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
