package com.aa.connectme.selectionpersist;

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

public class TC42944_Connectme_gate_selection_Page_persist_Override_Valid extends UtilityFunction {WebDriver driver;
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

	int intLoop=xlsReader.rowCount("PagepersistOverride");
	System.out.println("The number of rows present on the "+"PagepersistOverride"+" is = "+intLoop);
	driver = utilityFunction.initilizeDriver(BrowserName,Environment);
	for (int i=1; i<intLoop; i++)
	{
		Station = xlsReader.getCellData("PagepersistOverride","Station",i+1);
		Gate = xlsReader.getCellData("PagepersistOverride","GateNumber",i+1);
		//driver = utilityFunction.initilizeDriver(BrowserName,Environment);                              
		System.out.println("Browser name :"+BrowserName+ ",++++ Envirnament: "+Environment+ ",++++  Gate nuber: "+ Gate + " ,++++  station name: "+ Station);
		try {

			 parentTest = extent.createTest(this.getClass().getSimpleName());
			extent.setAnalysisStrategy(AnalysisStrategy.CLASS); 
			// Method call to Login to the application and check whether login is successful or not

			if (loginPage.login(Station,Gate) == true){
				System.out.println("Login succesfull");
				ExtentTest childTest1 = parentTest.createNode("STEP 1 -Login ", "login to the application").pass("User logged in to ConnectMe Gate App successfully");
				res = true;
			}else {
				System.out.println("Login Unsuccesfull");
				ExtentTest childTest1 = parentTest.createNode("STEP 1 -Login ", "login to the application").fail("Login Unsuccesfull");
				str = utilityFunction.captureScreenshot();
				childTest1.addScreenCaptureFromPath(str);
				res = false;
			}
			//Validating the dashboard visibility	
			if (dashboardPage.dashboardVisibility() == true){
				System.out.println("Dashboard is displayed succesfully");
				ExtentTest childTest1 = parentTest.createNode("STEP 2 - DashBoard Page ", "GateDash Board Page").pass("Gate dashboard page should be displayed successfully.");
				res = true;
			}else{
				System.out.println("Failed to display the Dashboard page.");
				ExtentTest childTest1 = parentTest.createNode("STEP 2 - DashBoard Page", "GateDash Board Page ").fail("Failed to display the Dashboard page.");
				str = utilityFunction.captureScreenshot();
				childTest1.addScreenCaptureFromPath(str);
				res = false;
			}

			for (int j=1; j<intLoop; j++) {
				Gate = xlsReader.getCellData("PagepersistOverride","ChangeGateNumber",j+1);
				if (loginPage.changeGatenumber(Station, Gate) == true) {

					System.out.println("Gate updating And displaying in header Bar");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - ChangeGate", "Gate updating And displaying in header Bar").pass("Gate updating And displaying in header Bar");
					res = true;

				}

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