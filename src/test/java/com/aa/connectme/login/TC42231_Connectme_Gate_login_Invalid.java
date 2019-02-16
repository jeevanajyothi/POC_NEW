/*
* Scenario Name:  TC42231_Connectme_Gate_login_Invalid
* Author: Niranjan Gowda S R
* Date of Creation: 2 jan-18
* Description: Test To Verify If Entering the Invalid UserName And Password Into The Login Page.
* Date Modified: 
* Reviewed By:
*/
package com.aa.connectme.login;

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
import com.aa.connectme.util.TestData;
import com.aa.connectme.util.UtilityFunction;
import com.aa.connectme.util.Xls_Reader;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TC42231_Connectme_Gate_login_Invalid extends UtilityFunction {WebDriver driver;

private UtilityFunction utilityFunction = new UtilityFunction();
private LoginPage loginPage = new LoginPage();
private Report report = new Report();
private DashboardPage dashboardPage = new DashboardPage();
private Xls_Reader xlsReader = new Xls_Reader();
private ExtentReports extent = utilityFunction.getExtent();
boolean res = false;
String str = "Exception";

String invalidgate ;
String invalidstation;
String ErrorResponse;
@Parameters({"BrowserName","Environment","TestResult"})
@Test

public void Test(String BrowserName, String Environment,String TestResult) throws Exception
{	
utilityFunction.strTestCaseName = this.getClass().getSimpleName();
utilityFunction.ModuleName = this.getClass().getPackage().getName();
utilityFunction.DateAndTime = currentDateAndTime();
	
	int intLoop=xlsReader.rowCount("Invalid_Gate_Station");
	System.out.println("The number of rows present on the "+"Invalid_Gate_Station"+" is = "+intLoop);
	driver = utilityFunction.initilizeDriver(BrowserName,Environment);
	for (int i=1; i<intLoop; i++)
	{
			
		invalidstation = xlsReader.getCellData("Invalid_Gate_Station","InvalidStation",i+1);
		invalidgate = xlsReader.getCellData("Invalid_Gate_Station","InvalidGate",i+1);
		ErrorResponse = xlsReader.getCellData("Invalid_Gate_Station","Error_Message",i+1);
		System.out.println("Browser name :"+BrowserName+ ",++++ Envirnament: "+Environment+ ",++++  Gate nuber: "+ invalidgate + " ,++++  station name: "+ invalidstation);
	try {	
		 
	    parentTest = extent.createTest(this.getClass().getSimpleName());
		extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
		
		// Enter Invalid GateName, InValid Station & error message.
		if (loginPage.login_Invalid(invalidstation, invalidgate, ErrorResponse) == true) {
			System.out.println("Login Error pop up is displayed and closed.");
			ExtentTest childTest1 = parentTest.createNode("STEP 1 - Login ", "User not able to logged in to ConnectMe Gate App for").pass("Login Error pop up is displayed and closed.");
			res = true;
		}else
		{
			System.out.println("values are doesn't match.");
			ExtentTest childTest1 = parentTest.createNode("STEP 1 - Login", "User not able to logged in to ConnectMe Gate App for").fail("values are doesn't match.");
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
