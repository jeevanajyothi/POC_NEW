package com.aa.connectme.header;

/******************************************************************************************************************************************************
 * 
 * Short Description :: Test to verify Header bar contents. It should contain the following fields - Operational Statistics/Filters, Inboxes, City, User & Role and Address Book 
 *                       
 * 
 * ****************************************************************************************************************************************************/

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aa.connectme.util.Report;
import com.aa.connectme.util.UtilityFunction;
import com.aa.connectme.util.Xls_Reader;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aa.connectme.pom.Constants;
import com.aa.connectme.pom.DashboardPage;
import com.aa.connectme.pom.LoginPage;

import com.aa.connectme.util.TestData;

public class TC42946_GATE_MVP_UI_Gate_Mode_Header extends UtilityFunction {

	WebDriver driver;
	private UtilityFunction utilityFunction = new UtilityFunction();
	private LoginPage loginPage = new LoginPage();
	private Report report = new Report();
	private DashboardPage dashboardPage = new DashboardPage();
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
		int intLoop=xlsReader.rowCount("Valid_Gate_Station");
		
		System.out.println("The number of rows present on the "+"Valid_Gate_Station"+" is = "+intLoop);
		driver = utilityFunction.initilizeDriver(BrowserName,Environment);	
		for (int i=1; i<2; i++)
		{
				Station = xlsReader.getCellData("Valid_Gate_Station","Station",i+1);
				Gate = xlsReader.getCellData("Valid_Gate_Station","GateNumber",i+1);
				
			System.out.println("Browser name :"+BrowserName+ ",++++ Envirnament: "+Environment+ ",++++  Gate nuber: "+ Gate + " ,++++  station name: "+ Station);
			System.out.println("*****TC42946_GATE_MVP_UI_Gate_Mode_Header******");	
			System.out.println("---------------------------------------------------");
			try {
				 parentTest = extent.createTest(this.getClass().getSimpleName());
				extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
						
			//Validating the dashboard visibility	
			if (dashboardPage.dashboardVisibility() == true){
				System.out.println("Dashboard is displayed succesfully");
				ExtentTest childTest1 = parentTest.createNode("STEP 1 - DashBoard Page ", "GateDash Board Page").pass("Gate dashboard page should be displayed successfully.");
				res = true;
			}else{
				System.out.println("Failed to display the Dashboard page.");
				ExtentTest childTest1 = parentTest.createNode("STEP 1 - DashBoard Page", "GateDash Board Page ").fail("Failed to display the Dashboard page.");
				str = utilityFunction.captureScreenshot();
				childTest1.addScreenCaptureFromPath(str);
				res = false;
			}	
			
		//Validating the dashboard content	
			if(dashboardPage.validateContentsOfHeaderInfo()== true){
				System.out.println("Valid contents are match");
				ExtentTest childTest1 = parentTest.createNode("STEP 2 - Header ", "Contents of header info").pass("Validate header contents are match");
				res = true;
				}
			else{
				System.out.println("Contents are not match");
				ExtentTest childTest1 = parentTest.createNode("STEP 2 - Header", "Contents of header info ").fail("Validate header contents are not match.");
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