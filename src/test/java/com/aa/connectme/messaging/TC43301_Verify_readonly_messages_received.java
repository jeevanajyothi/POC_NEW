
package com.aa.connectme.messaging;

/******************************************************************************************************************************************************
 * 
 * Short Description :: Test To Verify If Entering the Valid GateNumber Password  and station Into The Login Page
 *                       
 * ****************************************************************************************************************************************************/

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.server.handler.GetElementText;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aa.connectme.util.Report;
import com.aa.connectme.util.TestData;
import com.aa.connectme.util.UtilityFunction;
import com.aa.connectme.util.Xls_Reader;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aa.connectme.pom.Constants;
import com.aa.connectme.pom.DashboardPage;
import com.aa.connectme.pom.LoginPage;
import com.aa.connectme.pom.MessagingPanel;

public class TC43301_Verify_readonly_messages_received extends UtilityFunction {

	WebDriver driver;
	private UtilityFunction utilityFunction = new UtilityFunction();
	private LoginPage loginPage = new LoginPage();
	private MessagingPanel MP = new MessagingPanel();
	private DashboardPage dashboardPage = new DashboardPage();
	private Report report = new Report();
	private Xls_Reader xlsReader = new Xls_Reader();
	private ExtentReports extent = utilityFunction.getExtent();
    boolean res = false;
    String str = "Exception";
	String Gate;
	String Station;
	String UserID;
	String Password;
	@Parameters({"BrowserName","Environment","TestResult"})
	@Test
	
	public void Test(String BrowserName, String Environment,String TestResult) throws Exception
	{	
	utilityFunction.strTestCaseName = this.getClass().getSimpleName();
	utilityFunction.ModuleName = this.getClass().getPackage().getName();
	utilityFunction.DateAndTime = currentDateAndTime();
	
		int intLoop=xlsReader.rowCount("Readonly_Messages");
		System.out.println("The number of rows present on the "+"Readonly_Messages"+" is = "+intLoop);
		driver = utilityFunction.initilizeDriver(BrowserName,Environment);
		for (int i=1; i<2; i++)
		{
			UserID = xlsReader.getCellData("Participants_list","Username",i+1);
			Password = xlsReader.getCellData("Participants_list","Password",i+1);
			Station = xlsReader.getCellData("Readonly_Messages","Station",i+1);
			Gate = xlsReader.getCellData("Readonly_Messages","GateNumber",i+1);
			System.out.println("*****TC43301_Verify_readonly_messages_received******");	
			System.out.println("---------------------------------------------------");
					
			System.out.println("Browser name :"+BrowserName+ ",++++ Environment: "+Environment+ ",++++  Gate number: "+ Gate + " ,++++  station name: "+ Station);
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

				if (dashboardPage.defaultTimeFlightList() == true){
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Default time range flights ", "Verifying the Default time range flights.").pass("Default time range flights are displayed successfully");
					res = true;
					System.out.println("Default time range flights are displayed successfully");
				}else{
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Default time range flights", "Verifying the Default time range flights.").fail("Failed to display the flights for default time range.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
					System.out.println("Failed to display the flights for default time range.");
				}	
				
				if (MP.verifyReadonlymessage() == true){
					System.out.println("Messaging panel verified");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - Read only message ", "Verifying the read only messages.").pass("Messaging panel verified successfully");
					res = true;
				}else {
					System.out.println("Messaging panel not verified");
					report.Reporter_ReportEvent(driver, "Messaging panel verified", Constants.EXTENT_REPORT_FAIL,
							"Messaging panel not verified");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - Read only message", "Verifying the read only messages.").fail("Messaging panel not verified.");
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