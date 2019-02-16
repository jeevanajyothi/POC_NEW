package com.aa.connectme.Gate;

/******************************************************************************************************************************************************
 * 
 * Short Description :: verifying for dash board visibility , default time flight lists & Flights refreshed according to the time frame selected , .
 *                       
 * ****************************************************************************************************************************************************/

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
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


public class TC43365_Gate_Mode_display_flights_time_range extends UtilityFunction {

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
	@Parameters({"BrowserName","Environment","TestResult"})
	@Test
	
	public void Test(String BrowserName, String Environment,String TestResult) throws Exception
	{	
	utilityFunction.strTestCaseName = this.getClass().getSimpleName();
	utilityFunction.ModuleName = this.getClass().getPackage().getName();
	utilityFunction.DateAndTime = currentDateAndTime();

		

	Station = xlsReader.getCellData("Valid_Gate_Station","Station",2);
	Gate = xlsReader.getCellData("Valid_Gate_Station","GateNumber",2);
		driver = utilityFunction.initilizeDriver(BrowserName,Environment);		
		System.out.println("Browser name :"+BrowserName+ ",++++ Envirnament: "+Environment+ ",++++  Gate nuber: "+ Gate + " ,++++  station name: "+ Station);
		try {
			// Method call to Login to the application and check whether login is successful or not
			
			 parentTest = extent.createTest(this.getClass().getSimpleName());
			extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
			System.out.println("*****TC43365_Gate_Mode_display_flights_time_range******");	
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
			
			if (dashboardPage.defaultTimeFlightList() == true){
				ExtentTest childTest1 = parentTest.createNode("STEP 2 - Default time range flights ", "Verifying the Default time range flights.").pass("Default time range flights are displayed successfully");
				res = true;
				System.out.println("Default time range flights are displayed successfully");
			}else{
				ExtentTest childTest1 = parentTest.createNode("STEP 2 - Default time range flights", "Verifying the Default time range flights.").fail("Failed to display the flights for default time range.");
				str = utilityFunction.captureScreenshot();
				childTest1.addScreenCaptureFromPath(str);
				res = false;
				System.out.println("There is no flights for the default time range.");
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
			
			if (dashboardPage.changedTimeRangeFlightList() == true){
				ExtentTest childTest1 = parentTest.createNode("STEP 4 - Changed time range flights ", "Verifying the Changed time range flights.").pass("Changed time range flights are displayed successfully");
				res = true;
				System.out.println("Changed time range flights are displayed successfully");
			}else{
				ExtentTest childTest1 = parentTest.createNode("STEP 4 - Changed time range flights", "Verifying the changed time range flights.").fail("Failed to display the flights for Changed time range.");
				str = utilityFunction.captureScreenshot();
				childTest1.addScreenCaptureFromPath(str);
				res = false;
				System.out.println("Failed to display the flights for Changed time range.");
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