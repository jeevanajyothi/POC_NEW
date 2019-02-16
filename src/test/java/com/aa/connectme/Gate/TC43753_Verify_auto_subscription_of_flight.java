package com.aa.connectme.Gate;

/******************************************************************************************************************************************************
 * 
 * Short Description :: verifying the following scenarios 
 *                         1 . Verifying the Dash board displaying
 *                         2 . Verifying Flights refreshed according to the time frame selected or not
 *                         3 . Verifying Flight departing in the next 4 hours is subscribed.
 *                       
 * ****************************************************************************************************************************************************/

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aa.connectme.pom.Constants;
import com.aa.connectme.pom.DashboardPage;
import com.aa.connectme.pom.LoginPage;
import com.aa.connectme.util.Report;
import com.aa.connectme.util.UtilityFunction;
import com.aa.connectme.util.Xls_Reader;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TC43753_Verify_auto_subscription_of_flight extends UtilityFunction {
	WebDriver driver;
	private UtilityFunction utilityFunction = new UtilityFunction();
	private LoginPage loginPage = new LoginPage();
	private DashboardPage dashboardPage = new DashboardPage();
	private Report report = new Report();
	private Xls_Reader xlsReader = new Xls_Reader();
	private ExtentReports extent = utilityFunction.getExtent();
    boolean res = false;
    String str = "Exception";
	String Gate;
	String Station;
	String Flight;
	@Parameters({"BrowserName","Environment","TestResult"})
	@Test
	
	public void Test(String BrowserName, String Environment,String TestResult) throws Exception
	{	
	utilityFunction.strTestCaseName = this.getClass().getSimpleName();
	utilityFunction.ModuleName = this.getClass().getPackage().getName();
	utilityFunction.DateAndTime = currentDateAndTime();
		int intLoop=xlsReader.rowCount("Verify_Flight_Subscription");
		
		System.out.println("The number of rows present on the "+"Verify_Flight_Subscription"+" is = "+intLoop);
		driver = utilityFunction.initilizeDriver(BrowserName,Environment);	
		for (int i=1; i<2; i++)
		{
			Station = xlsReader.getCellData("Verify_Flight_Subscription","Station",i+1);
			Gate = xlsReader.getCellData("Verify_Flight_Subscription","GateNumber",i+1);
			Flight = xlsReader.getCellData("Verify_Flight_Subscription","Flight",i+1);
				
			System.out.println("Browser name :"+BrowserName+ ",++++ Environment: "+Environment+ ",++++  Gate number: "+ Gate + " ,++++  station name: "+ Station);
			try {

				// Method call to Login to the application and check whether login is successful or not
				 parentTest = extent.createTest(this.getClass().getSimpleName());
				extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
				System.out.println("*****TC43753_Verify_auto_subscription_of_flight******");	
				System.out.println("---------------------------------------------------");
				
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
				if (dashboardPage.verifyFlightsRefresh(driver) == true) {
					System.out.println("Flights refreshed according to the time frame selected");
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Verify fights for different the time range ", "Flights refreshed according to the time range.").pass("Flights refreshed according to the time frame selected");
					res = true;
				}else {
					System.out.println("Issue displaying the flights within the selected time frame");
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Verify fights for different time range", "Flights refreshed according to the time range.").fail("Issue displaying the flights within the selected time frame");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				/*utilityFunction.waitForElementVisibility("CURRENT_FLIGHT");
				Flight = dashboardPage.getFirstFlightNumber();*/
				if (dashboardPage. getFirstFlightNumber() != null){
					System.out.println("Flight departing in the next 4 hours is subscribed");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - Verify auto subscribed flight ", "Verify auto subscribed flight for the gate.").pass("Flight departing in the next 4 hours is the subscribed");
					res = true;
				}else {
					System.out.println("Flight departing for the upcoming hours is not subscribed");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - Verify auto subscribed flight", "Verify auto subscribed flight for the gate.").fail("Flight departing in the next 4 hours is not subscribed");
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
