package com.aa.connectme.Gate;

/******************************************************************************************************************************************************
 * 
 * Short Description :: verifying the following scenarios 
 *                         1 . Verifying the Dash board displaying
 *                         2 . Verifying Flights refreshed according to the time frame selected or not
 *                         3 . Verifying gate is subscribing.
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

public class TC43754_Verify_auto_subscription_of_gate extends UtilityFunction {
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
		int intLoop=xlsReader.rowCount("Verify_Gate_Subscription");
		
		System.out.println("The number of rows present on the "+"Verify_Gate_Subscription"+" is = "+intLoop);
		driver = utilityFunction.initilizeDriver(BrowserName,Environment);
		for (int i=1; i<2; i++)
		{
			Station = xlsReader.getCellData("Verify_Gate_Subscription","Station",i+1);
			Gate = xlsReader.getCellData("Verify_Gate_Subscription","GateNumber",i+1);
					
			System.out.println("Browser name :"+BrowserName+ ",++++ Environment: "+Environment+ ",++++  Gate number: "+ Gate + " ,++++  station name: "+ Station);
			try {
				System.out.println("*****TC43754_Verify_auto_subscription_of_gate******");	
				System.out.println("---------------------------------------------------");
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
				

				if (dashboardPage. verifyGateSubscription()){
					System.out.println("Logged in gate is subscribed");
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Verify subscribed gate ", "Verify subscribed gate gate logged in gate.").pass("Subscribed agte and logged in gate are matched");
					res = true;
				}else {
					System.out.println("Logged in gate is not subscribed");
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Verify subscribed gate", "Verify subscribed gate gate logged in gate.").fail("Mis match in the logged in gate and subscribed gate.");
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