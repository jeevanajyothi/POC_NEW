package com.aa.connectme.header;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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

public class TC58819_Verifyiflistofpriority_Messagesordered_Bymost_Recentat_Top extends UtilityFunction {

	WebDriver driver;
	private UtilityFunction utilityFunction = new UtilityFunction();
	private LoginPage loginPage = new LoginPage();
	private DashboardPage dashboardPage = new DashboardPage();
	private Report report = new Report();
	private Xls_Reader xlsReader = new Xls_Reader();
	private ExtentReports extent = utilityFunction.getExtent();
	private LogoutPage logoutPage = new LogoutPage();
	private MessagingPanel MP = new MessagingPanel();
	boolean res = false;
	String str = "Exception";
	String UserID, UserID1;
	String Password, Password1;
	String Msg;

	@Parameters({ "BrowserName", "Environment", "TestResult" })
	@Test

	public void Test(String BrowserName, String Environment, String TestResult) throws Exception {
		utilityFunction.strTestCaseName = this.getClass().getSimpleName();
		utilityFunction.ModuleName = this.getClass().getPackage().getName();
		utilityFunction.DateAndTime = currentDateAndTime();

		int intLoop = xlsReader.rowCount("Bell_Notification_Preview");
		driver = utilityFunction.initilizeDriver(BrowserName, Environment);
		for (int i = 1; i < 2; i++) {
			UserID = xlsReader.getCellData("Bell_Notification_Preview", "UserName", i + 1);
			Password = xlsReader.getCellData("Bell_Notification_Preview", "Password", i + 1);
			Msg = xlsReader.getCellData("Bell_Notification_Preview", "Message", i + 1);
			UserID1 = xlsReader.getCellData("Bell_Notification_Preview", "UserName1", i + 1);
			Password1 = xlsReader.getCellData("Bell_Notification_Preview", "Password1", i + 1);
			

			System.out.println("*****TC58819_Verifyiflistofpriority_Messagesordered_Bymost_Recentat_Top******");
			System.out.println("------------------------------------------------------------");
			try {
				parentTest = extent.createTest(this.getClass().getSimpleName());
				extent.setAnalysisStrategy(AnalysisStrategy.CLASS);

				if (dashboardPage.dashboardVisibility() == true) {
					ExtentTest childTest1 = parentTest
							.createNode("STEP 1 - Verify dashboard ", "Verify the dashboard component.")
							.pass("Dashboard components is verified successfully");
					res = true;
					System.out.println("Dashboard is displayed succesfully");
				} else {
					ExtentTest childTest1 = parentTest
							.createNode("STEP 1 - Verify dashboard", "Verify the dashboard component.")
							.fail("Failed to verify the dashboard components");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
					System.out.println("Failed to display the Dashboard page.");
				}

				if (loginPage.Personnelogin(UserID1, Password1) == true) {
					System.out.println("Login succesfull");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 2 - Personal login ", "Personal login with user credentials")
							.pass("Personal login successful");
					res = true;
				} else {
					System.out.println("Login Unsuccesfull");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 2 - Personal login", "Personal login with user credentials")
							.fail("Personal login failed.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}

				if (dashboardPage.verifyFlightsRefresh(driver) == true) {
					System.out.println("Flights refreshed according to the time frame selected");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 3 - Verify fights for different the time range ",
									"Flights refreshed according to the time range.")
							.pass("Flights refreshed according to the time frame selected");
					res = true;
				} else {
					System.out.println("Issue displaying the flights within the selected time frame");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 3 - Verify fights for different time range",
									"Flights refreshed according to the time range.")
							.fail("Issue displaying the flights within the selected time frame");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}

				if (MP.verifyAndclickPrioritybtn() == true) {
					System.out.println(" Verified and clicked the priority button ");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 4 - Click priority button", "Verify and click priority button.")
							.pass("Verified and clicked the priority button");
					res = true;
				} else {
					System.out.println("Failed to Verify and clicking the priority button");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 4 - Click priority button", "Verify and click priority button.")
							.fail("Failed to Verify and clicking the priority button.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}

				if (MP.sendMessage(Msg) == true) {
					System.out.println(" Message sent succesfully.");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 5 - Send message", "Sending message to the conversation.")
							.pass("Message sent succesfully");
					res = true;
				} else {
					System.out.println("Failed to send messages.");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 5 - Send message", "Sending message to the conversation.")
							.fail("Message not sent");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				/*if (dashboardPage.verifyFlightTopicsandMsgs() == true) {
					System.out.println(" The flights are listed in the gate tab ");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 6 - Verify fligts", "Verify the flights in the subscribed gate.")
							.pass("Fligts for the subscribed gate is displayed successfully");
					res = true;
				} else {
					System.out.println("Flights are not yet loaded/displayed.");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 6 - Verify fligts", "Verify the flights in the subscribed gate.")
							.fail("all  flights departing from User logged gate  not Displayed");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}*/
				if (logoutPage.logout() == true) {
					System.out.println("Logout succesfull");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 6 - Personal logout ", "Personal logout from the application")
							.pass("Personal logout successful");
					res = true;
				} else {
					System.out.println("Logout Unsuccesfull");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 6 - Personal logout ", "Personal logout from the application")
							.fail("Personal logout failed.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}

				if (loginPage.Personnelogin(UserID, Password) == true) {
					System.out.println("Login succesfull");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 7 - Personal login ", "Personal login with user credentials")
							.pass("Personal login successful");
					res = true;
				} else {
					System.out.println("Login Unsuccesfull");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 7 - Personal login", "Personal login with user credentials")
							.fail("Personal login failed.");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				utilityFunction.addDelay(13000);
				if (dashboardPage.verifyPriorityBell() == true) {
					System.out.println("Priority bell displayed in dashboard page");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 8 - Verify Priority bell ", "Priority bell presense in dashboard page")
							.pass("Priority bell displayed in dashboard page");
					res = true;
				} else {
					System.out.println("Priority bell not displayed in dashboard page");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 8 - Verify Priority bell", "Priority bell presense in dashboard page")
							.fail("Priority bell  not displayed in dashboard page");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				// Verify messages in any topic
				if (dashboardPage.clickOnPriorityBell() == true) {
					System.out.println("Clicked on priority bell suceesfully");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 9 - Click on priority bell", "Clicked on priority bell ")
							.pass("Clicked on priority bell suceesfully");
					res = true;
				} else {
					System.out.println("Failed to click on priority bell");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 9 - Click on priority bell", "Clicked on priority bell ").fail("");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				// Verify messages in any topic
				if (dashboardPage.getListOfMesFromNotificationTab() == true) {
					System.out.println("Priority message count matches with unacknowkedged priority messages");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 10 - Priority message", "Priority message count matches")
							.pass("Priority message count matches with unacknowkedged priority messages");
					res = true;
				} else {
					System.out.println("Priority message count not matches with unacknowkedged priority messages");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 10 - Priority message", "Priority message count matches")
							.fail("Priority message count not matches with unacknowkedged priority messages");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;
				}
				if (dashboardPage.getMesTimesFromNotificationTab() == true) {
					System.out.println("Automated messages displayed with message time\r\n");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 11 - Get message timings", "Notification message with timings displayed")
							.pass("Automated messages displayed with message time");
					res = true;
				} else {
					System.out.println("Automated messages are not displayed");
					ExtentTest childTest1 = parentTest
							.createNode("STEP 11 - Get message timings", "Notification message with timings displayed")
							.fail("There were no Automated messages displayed with message time");
					str = utilityFunction.captureScreenshot();
					childTest1.addScreenCaptureFromPath(str);
					res = false;

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Parameters({ "TestResult" })
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
			if (report.get(4).equals(Status.PASS.toString())) {
				UtilityFunction.testResult(report);
			} else {
				UtilityFunction.testResult(report);
			}
			extent.flush();
		}
	}

}
