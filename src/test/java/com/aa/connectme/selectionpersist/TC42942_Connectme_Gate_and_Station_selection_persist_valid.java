/*
 * Scenario Name: TC42942_Connectme_Gate_and_Station_selection_persist_valid
 * Author: Sneha Bhojugade
 * Date of Creation: 14th Jan 2018
 * Description: Test script to verify if the Station and Gate selection persists when the application is reopened
 * Date Modified: 
 * Reviewed By: 
 */

package com.aa.connectme.selectionpersist;

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
import com.aa.connectme.pom.LoginPage;


public class TC42942_Connectme_Gate_and_Station_selection_persist_valid extends UtilityFunction {WebDriver driver;
private UtilityFunction utilityFunction = new UtilityFunction();
private LoginPage loginPage = new LoginPage();
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
	int intLoop=xlsReader.rowCount("Station_Gate_persist");
	System.out.println("The number of rows present on the "+"Station_Gate_persist"+" is = "+intLoop);
	driver = utilityFunction.initilizeDriver(BrowserName,Environment);
	for (int i=1; i<intLoop; i++)
	{
		Station = xlsReader.getCellData("Station_Gate_persist","Station",i+1);
		Gate = xlsReader.getCellData("Station_Gate_persist","GateNumber",i+1);
				
		System.out.println("Browser name :"+BrowserName+ ",++++ Envirnament: "+Environment+ ",++++  Gate number: "+ Gate + " ,++++  station name: "+ Station);
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

			//Refresh the page to re-open the URL
			utilityFunction.refreshPage();

			//Function to verify if the Station and the Gate values have persisted
			;
			if (loginPage.stationGatePersists(Station,Gate) == true){
				System.out.println("Station and Gate info persists");
				ExtentTest childTest1 = parentTest.createNode("STEP 2 -Station and Gates ", "Station and Gate information").pass("Station and Gate information do persist");
				res = true;
			}else {
				System.out.println("Station and Gate information do not persist");
				ExtentTest childTest1 = parentTest.createNode("STEP 2 -Station and Gates ", "Station and Gate information").fail("Station and Gate information do not persist");
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