package com.aa.connectme.Gate;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aa.connectme.pom.DashboardPage;
import com.aa.connectme.pom.GetNgPage;
import com.aa.connectme.pom.LoginPage;
import com.aa.connectme.util.Report;
import com.aa.connectme.util.UtilityFunction;
import com.aa.connectme.util.Xls_Reader;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TC60143_Verify_Gate_User_Timeout_when_flight_is_off_PopUp_Yes_no_buttons extends UtilityFunction{




	 public static WebDriver driver_wrapper,driver_browser,passeddriver;
 public static String parentwindow,childwindow;

	//WebDriver driver;
	private UtilityFunction utilityFunction = new UtilityFunction();
	
	private LoginPage loginPage = new LoginPage();
	private DashboardPage DP = new DashboardPage();
	private Report report = new Report();
	private Xls_Reader xlsReader = new Xls_Reader();
	private ExtentReports extent = utilityFunction.getExtent();
	private GetNgPage getngpage = new GetNgPage();
	public static String GetngStationName;
	boolean res = false;
	String str = "Exception";
	String UN;
	String PWD;
	String UN1;
	String PWD1;
	String ChromeUN1;
	String ChromePWD1;
	String Station;
	String Gate;
	String TopicName;
	String Uname1;
	String Uname2;
	String Uname3;
	String MessageText;
	@Parameters({"BrowserName","Environment","TestResult"})
	@Test

	public void Test(String BrowserName, String Environment,String TestResult) throws Exception {	
		utilityFunction.strTestCaseName = this.getClass().getSimpleName();
		utilityFunction.ModuleName = this.getClass().getPackage().getName();
		utilityFunction.DateAndTime = currentDateAndTime();
			
		int intLoop=xlsReader.rowCount("GetNGCredentials");
		System.out.println("The number of rows present on the "+"GetNGCredentials"+" is = "+intLoop);

			UN = xlsReader.getCellData("GetNGCredentials","UserName",2);
			PWD = xlsReader.getCellData("GetNGCredentials","Password",2);
			UN1 = xlsReader.getCellData("GetNGCredentials","UserName",3);
			PWD1 = xlsReader.getCellData("GetNGCredentials","Password",3);
			Station = xlsReader.getCellData("GetNGCredentials","Station",2);
			Gate = xlsReader.getCellData("GetNGCredentials","Gates",2);
		
			driver = utilityFunction.initilizeDriver("Chrome","GetNGProd");	
			System.out.println("*****TC60378_Verify_once_flight_out_from_gate_receiving_OUT_banner_with_grey_color******");
			System.out.println("-----------------------------------");
			try {
				
				parentTest = extent.createTest(this.getClass().getSimpleName());
				extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
				// Method call to Login to the application and to check whether login is successful or not
				
				if (getngpage.getNglogin(UN,PWD) == true){
					System.out.println("Login succesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 1 - GetNg Launch & Login ", "Launching the application and login").pass("GetNg Launch and login is successfull");
					res = true;
				}else {
					System.out.println("Login Unsuccesfull");
					ExtentTest childTest1 = parentTest.createNode("STEP 1 - GetNg Launch & Login", "Failed to Launching the application and login").fail("Getng fails to login");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				Thread.sleep(10000);
				GetngStationName = utilityFunction.getElementText("GETNG_STATION_NAME");
				if(getngpage.changeSchToDep()==true)
				{
					System.out.println("Change from SCH state to DEP state");
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Changing SCH-DEP ", "Changing SCH-DEP").pass("Sucessfully Changed from SCH-DEP");
					res = true;
					
				}else {
					System.out.println("Failed to Change from SCH state to DEP state");
					ExtentTest childTest1 = parentTest.createNode("STEP 2 - Changing SCH-DEP ", "Changing SCH-DEP").fail("Failed to Change from SCH-DEP");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				Thread.sleep(15000);
				getngpage.getDepOUTOFFFLTStatusDetails();
				System.out.println(GetNgPage.DepFltGates);
				System.out.println(GetNgPage.DepFltNums);
				System.out.println(GetNgPage.DepFltStatus);
				System.out.println(GetngStationName);
				
				if(getngpage.getNgSignOut()==true)
				{
					System.out.println("Clicked on SignOut dropdown");
					ExtentTest childTest1 = parentTest.createNode("STEP 7 - Selecting SignOut dropdown ", "Selecting SignOut and clicking on SignOut option").pass("SignOut is  successfull");
					res = true;
					
				}else {
					System.out.println("Failed to Click on Gate sorting button");
					ExtentTest childTest1 = parentTest.createNode("STEP 7 - Selecting SignOut dropdown ", "Failed in Selecting SignOut and clicking on SignOut option").fail("Failed to SignOut.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				driver = utilityFunction.initilizeDriver(BrowserName, Environment);
				Thread.sleep(5000);
				if(DP.waitingForOFFFlightsPopUp("DFW")==true)
				{
					System.out.println("Verified the flight banner for OUT/OFF status with 10 Seconds Pop Up.");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - verify the flight banner for OUT/OFF status", "Succesfully verifying the OUT/OFF status flight banner").pass("Verified the flight banner for OUT/OFF status.");
					res = true;
					
				}else {
					System.out.println("Failed to Verify the flight banner for OUT/OFF status with 10 Seconds Pop Up.");
					ExtentTest childTest1 = parentTest.createNode("STEP 3 - flight banner for OUT/OFF status ", "Failed while verifying OUT/OFF status flight banner").fail("Failed to verify the flight banner OUT/OFF status.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
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
