
package com.aa.connectme.pom;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aa.connectme.util.Report;
import com.aa.connectme.util.UtilityFunction;
import com.aventstack.extentreports.ExtentTest;


public class DashboardPage {

	/*
	 * Function Name: logout 
	 * Author: Ayush Raj 
	 * Date of Creation: 8-August-2017
	 * Description: This function is to perform logout functionality from the application
	 * Input Parameters: No input required 
	 * Date Modified: Reviewed
	 * By:
	 */
	WebDriver driver;
	private Report report = new Report();
	static UtilityFunction utilityFunction = new UtilityFunction();
	private String bellCount;
	private static LoginPage loginPage = new LoginPage();
	private LogoutPage logoutPage = new LogoutPage();
	
	public boolean logout() throws IOException {
		UtilityFunction utilityFunction = new UtilityFunction();
		boolean res = false;

		try {
			// check whether user profile is displayed in the Dash boar page and
			// wait for its visibility
			utilityFunction.waitForElementVisibility("DASHBOARD_HEADER_PROFILE");

			// if the dashboard page is visible then we click on the user
			// profile to get the logout application
			if (utilityFunction.Validate("DASHBOARD_HEADER_PROFILE") == true) {

				utilityFunction.clickElement("DASHBOARD_HEADER_PROFILE");
				utilityFunction.waitForElementVisibility("DASHBOARD_HEADER_USER_LOGOUT");

				if (utilityFunction.Validate("DASHBOARD_HEADER_USER_LOGOUT") == true)
					System.out.println("Help,Change role And Logout options should be displayed ");
				else
					System.out.println("Help,Change role And Logout options should be displayed ");

				// click on the logout application
				utilityFunction.clickElement("DASHBOARD_HEADER_USER_LOGOUT");

				res = true;
			} else {
				System.out.println("Logout icon is not displayed");
			}
		}

		catch (Exception e) {
			System.out.println("Logout error!");
		}
		return res;
	}
	/*
	 * Function Name: validateContentsOfHeader 
	 * Author: Niranjan Gowda S R 
	 * Date of Creation: 27-Dec-2017
	 * Description: This function is to validate Contents Of Header bar from the application dashboard page
	 * Input Parameters: GateName,StationName
	 * Date Modified: 
	 * Reviewed By:
	 */
	
	public boolean validateContentsOfHeaderInfo() {

		UtilityFunction utilityFunction = new UtilityFunction();
		boolean res = true;
		
			try {
			// wait till Dashboard page appears on screen		
			if (utilityFunction.waitForElementVisibility("DASHBOARD_HEADER_CONNECTME_LOGO")) {
				String GateInfo=utilityFunction.getElementText("DASHBOARD_HEADER_STATION_GATE_NAME");
				System.out.println("The dashboard header content is displayed : "+GateInfo);
			} else {
				System.out.println("Dashboard Header Gate info doesn't match with the Actual values ");
				
			}
		} catch (Exception e) {
			System.out.println("Failed to validate the header gate information" + e.getMessage());
			return false;
		}
		return res;
	}
	
	/*
	 * Function Name: validateContentsOfUserInfo 
	 * Author: Niranjan Gowda S R 
	 * Date of Creation: 2-Jan-2018
	 * Description: This function is to validate Contents Of Header bar from the application dashboard page
	 * Input Parameters: UN,PWD and station
	 * Date Modified: 
	 * Reviewed By:
	 */
	
	public boolean validateContentsOfUserInfo(String StationName,String GateName,String UN) {

		UtilityFunction utilityFunction = new UtilityFunction();
		boolean res = true;
		String str1 = null;
		String str2 = null;
		String str3 = null;
		try {
			// wait till Dashboard page appears on screen
			if (utilityFunction.waitForElementVisibility("LOGIN")) {
				String SGUInfo=utilityFunction.getElementText("DASHBOARD_HEADER_GATE_INFO");
				System.out.println(SGUInfo);
				String StationGateandUserId[]=SGUInfo.split("/");
				for (int i=0; i<StationGateandUserId.length; i++)
				{
					str1=StationGateandUserId[0];
					str2=StationGateandUserId[1];
					str3=StationGateandUserId[2];
					
				}
				if (StationName.contains(str1) && GateName.contains(str2) && UN.contains(str3)) {

					System.out.println("The station name : " + str1 + " , gate number : " + str2 + " and user id "
							+ str3 + " are matched");
					return res;

				} else {
					System.out.println("The station name : " + str1 + " , gate number : " + str2 + " and user id "
							+ str3 + " are  not matching with the actual inputs");
					return false;
				}
			} else {
				System.out.println("Dashboard Header Contents are doesn't match with the Actual values ");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Logout error" + e.getMessage());
		}
		return res;
	}
	/*
	 * Function Name: dashboardVisibility 
	 * Author: Bharath 
	 * Date of Creation: 10-January-2018
	 * Description: This function is to validate dashboardVisibility from the application dashboard page
	 * Input Parameters: no parameters
	 * Date Modified: Reviewed
	 * By:
	 */
	
	public boolean dashboardVisibility() {
		UtilityFunction utilityFunction = new UtilityFunction();
		boolean res = false;
		try {
			//to validate in dashboard header if user profile is displayed or not
			if (utilityFunction.waitForElementVisibility("DASHBOARD_HEADER_GATE_INFO")) {
				System.out.println("Connect me text is displayed");
				if(utilityFunction.waitForElementVisibility("LOGIN"))
				{
				System.out.println("Login link is displayed");	
				res = true;
				System.out.println("Main dashboard  page is displayed");
				}else
				{
					System.out.println("Failed to dispaly the Login link.");
				}
			} else {
				System.out.println("Main dashboard  page not displayed");
			}
		} catch (Exception e) {
			System.out.println("Dash board page Error..!");
		}
		return res;
	}
	/*
	 * Function Name: dashboardVisibility 
	 * Author: Bharath 
	 * Date of Creation: 10-January-2018
	 * Description: This function is to validate dashboardVisibility from the application dashboard page
	 * Input Parameters: no parameters
	 * Date Modified: Reviewed
	 * By:
	 */
	
	/*public boolean verifyFlights() {
		
		boolean res = false;
		try {
			if(utilityFunction.waitForElementVisibility("CURRENT_FLIGHT")) {
			if (utilityFunction.waitForElementVisibility("LEFT_GATE")) {
				System.out.println("Gate panel  is displayed");
				if((utilityFunction.waitForElementVisibility("CURRENT_FLIGHT"))||(utilityFunction.waitForElementVisibility("NEXT_FLIGHTS")))
					res = true;
				else
					System.out.println("Messages not displayed properly");
				utilityFunction.clickElement("SCHEDULE");
				String DefaultTime=utilityFunction.getElementText("DEFAULT_TIME");
				System.out.println(DefaultTime);
				
			} else {
				System.out.println("Gate panel is not displayed");
			}
			
		} }catch (Exception e) {
			System.out.println("Error..!");
		}
		return res;
	}*/
	
public boolean verifyFlights() {

		boolean res = false;
		try {
			if (utilityFunction.waitForElementVisibility("CURRENT_FLIGHT")
					|| (utilityFunction.waitForElementVisibility("LEFT_GATE"))) {
				// if (utilityFunction.waitForElementVisibility("LEFT_GATE")) {
				System.out.println("Gate panel  is displayed");
				if ((utilityFunction.waitForElementVisibility("CURRENT_FLIGHT"))

						|| (utilityFunction.waitForElementVisibility("NEXT_FLIGHTS"))) {
					String Cf = utilityFunction.getElementText("CURRENT_FLIGHT");
					System.out.println("Curent flight details is :" + Cf);
					String Nf = utilityFunction.getElementText("NEXT_FLIGHTS");
					System.out.println("Next flight details is :" + Nf);
					utilityFunction.clickElement("CLOCK_SYMBOL");
					utilityFunction.waitForElementVisibility("DEFAULT_SLIDER_DOT");
					utilityFunction.clickElement("DEFAULT_SLIDER_DOT");
					utilityFunction.waitForElementVisibility("DEFAULT_SLIDER_TIME");
					String Dtime = utilityFunction.getElementText("DEFAULT_SLIDER_TIME");
					System.out.println("Default time text is :" + Dtime);
					res = true;

				} else {

					System.out.println("Current Flights or Next flights are not yet loaded/displayed. ");
					return res;
				}
				/*
				 * } else { System.out.println("Gate panel is not displayed"); }



				 */

			} else {

				System.out.println("Flights are not yet loaded/displayed.");
				return res;

			}
		} catch (Exception e) {
			System.out.println("Flight table Error..!");
		}
		return res;
	}
	
	public boolean verifyFlightsRefresh(WebDriver driver) throws InterruptedException {
		boolean res = false;


		if (utilityFunction.waitForElementVisibility("CLOCK_SYMBOL") == true) {
			Thread.sleep(1000);
			if (utilityFunction.Validate("CLOCK_SYMBOL") == true) {
				utilityFunction.clickElement("CLOCK_SYMBOL");
				if (utilityFunction.waitForElementVisibility("DEFAULT_SLIDER_DOT") == true) {

					if (utilityFunction.Validate("DEFAULT_SLIDER_DOT") == true) {
						WebElement slider = UtilityFunction.driver
								.findElement(By.xpath(utilityFunction.readObjectRepositoryFile("DEFAULT_SLIDER_DOT")));
						Thread.sleep(3000);
						Actions move = new Actions(driver);
						/*
						 * Action action = move.dragAndDropBy(slider, 90, 0).build(); action.perform();

						 */
						move.dragAndDropBy(slider, 90, 0).build().perform();
						Thread.sleep(2000);
						driver.switchTo().defaultContent();
						System.out.println("switched the frame");
						utilityFunction.waitForElementVisibility("LEFT_GATE");
						utilityFunction.doubleClick("LEFT_GATE");
						Thread.sleep(5000);
						System.out.println("Flights displayed as per the selected time frame");

						res = true;
					}
				} else {
					System.out.println("Slider dot is missing.");
				}
			}
		} else {
			System.out.println("Clock symbol is not displayed.");
			return res;
		}

		return res;
	}
	/*public boolean verifyFlightsRefresh(WebDriver driver) throws InterruptedException {
		boolean res = false;
		
		if(utilityFunction.waitForElementVisibility("TOPICS_PANEL_TIMER")){
			utilityFunction.clickElement("TOPICS_PANEL_TIMER");
			
			utilityFunction.waitForElementVisibility("TOPICS_PANEL_SLIDER");
			
			WebElement slider = UtilityFunction.driver.findElement(By.xpath(utilityFunction.readObjectRepositoryFile("TOPICS_PANEL_TIMER_BOX")));
			Thread.sleep(3000);
		    Actions move = new Actions(driver);
		    org.openqa.selenium.interactions.Action action  = move.dragAndDropBy(slider, 30, 0).build();
		    action.perform();
		   
		    driver.switchTo().defaultContent();
		    System.out.println("switched the frame");
		    utilityFunction.waitForElementVisibility("LEFT_GATE");
		    utilityFunction.doubleClick("LEFT_GATE");
		    Thread.sleep(2000);
		    System.out.println("Flights displayed as per the selected time frame");
		    return true;
		} else {
			System.out.println("Issue displaying the flights.");
			return res;
		}
		
	}*/
	/*
	 * Function Name: verifyFlightSubscription 
	 * Author: Sneha S Bhojugade 
	 * Description: This function is to verify the auto subscription of flights
	 * Input Parameters: Flight number
	 * Date Modified:
	 * By:
	 */
	
	public boolean verifyFlightSubscription(String Flight) {
		
		boolean res = false;
		String strFlight = null;
		try {
			if(utilityFunction.waitForElementVisibility("CURRENT_FLIGHT")) {
				strFlight = utilityFunction.getElementText("SUBSCRIBED_FLIGHT_LIST");
				System.out.println("Subscried fliht : "+strFlight );
				System.out.println("test data flight : "+Flight);
				if(strFlight.contains(Flight))
					res = true;
				else
					System.out.println("Flight that departs in the next 4 hours is not subscribed");
			}
		}catch (Exception e) {
			System.out.println("Verify flight subscription Exception");
		}
		return res;
	}
	
	/*
	 * Function Name: verifyGateSubscription 
	 * Author: Sneha S Bhojugade 
	 * Description: This function is to verify the auto subscription of the logged in gate
	 * Input Parameters: Gate number
	 * Date Modified:
	 * By:
	 */
	
	
	/*public boolean verifyGateSubscription(String Gate) {
		
		boolean res = false;
		String strGate = null;
		try {
			if(utilityFunction.waitForElementVisibility("SUBSCRIBED_GATE_HEADER")) {
				strGate = utilityFunction.getElementText("SUBSCRIBED_GATE_NUMBER");
				if(strGate.contains(Gate))
					res = true;
				else
					System.out.println("Logged in Gate is not subscribed");
			}
		}catch (Exception e) {
			System.out.println("Exception");
		}
		return res;
	}*/
public boolean verifyGateSubscription() {
		
		boolean res = false;
		String strGate = null;
		try {
			if(utilityFunction.waitForElementVisibility("SUBSCRIBED_GATE_HEADER")) {
				String sgu = utilityFunction.getElementText("DASHBOARD_HEADER_STATION_GATE_NAME");
				String arr[] = sgu.split(" ");
				String gateno = arr[3];
				strGate = utilityFunction.getElementText("SUBSCRIBED_GATE_NUMBER");
				if(strGate.contains(gateno))
					res = true;
				else
					System.out.println("Logged in Gate is not subscribed");
			}
		}catch (Exception e) {
			System.out.println("Exception");
		}
		return res;
	}
/*
 * Function Name: login butoon click 
 * Author: Niranjan Gowda S R 
 * Description: This function is to verify the auto subscription of flights
 * Input Parameters: Flight number
 * Date Modified:
 * By:
 */
public boolean clickOnLoginButton() {
	
	boolean res = false;
	try {
		if(utilityFunction.waitForElementVisibility("LOGIN")==true) {
			Thread.sleep(2000);
				utilityFunction.clickElement("LOGIN");
				utilityFunction.waitForElementVisibility("PERSONNEL_PAGE_USERID");
				res = true;
		}else {
		System.out.println("Login button error");
		return res;
		}
	}catch (Exception e) {
		System.out.println("Login Exception");
	}
	return res;
}
/*
 * Function Name: defaultTimeFlightList 
 * Author: Niranjan Gowda S R 
 * Description: This function is to verify the auto subscription of flights
 * Input Parameters: Flight number
 * Date Modified:
 * By:
 */
public boolean defaultTimeFlightList() {
	
	boolean res = false;
	try {
		if(utilityFunction.waitForElementVisibility("CURRENT_FLIGHT")) {
			if(utilityFunction.Validate("CURRENT_FLIGHT")==true)
			{
				String currentflight=utilityFunction.getElementText("CURRENT_FLIGHT");
				System.out.println("The current flight number is : "+currentflight);
				if(utilityFunction.Validate("NEXT_FLIGHTS")==true)
				{
					List NextFligts=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("NEXT_FLIGHTS")));
					System.out.println("Total no of next flights are = "+NextFligts.size());
					for( int i=0; i<NextFligts.size();i++)
					{
						WebElement ele=(WebElement) NextFligts.get(i);
						String NextFlightName=ele.getText();
					System.out.println("Next flights name are :"+NextFlightName);
					}
					res=true;
				}else
				{
					System.out.println("There is no NEXT fligt for default time range.");
					return res;
				}
			}else
			{
				System.out.println("There is no CURRENT fligt for default time range.");
				return res;
			}
		}else {
		System.out.println("There is no current flights.");
		return res;
		}
	}catch (Exception e) {
		System.out.println("There are no Next flight are avaiable here.");
		res = true;
	}
	return res;
}
/*
 * Function Name: defaultTimeFlightList 
 * Author: Niranjan Gowda S R 
 * Description: This function is to verify the auto subscription of flights
 * Input Parameters: Flight number
 * Date Modified:
 * By:
 */
public boolean changedTimeRangeFlightList() {
	
	boolean res = false;
	try {
		if(utilityFunction.waitForElementVisibility("CURRENT_FLIGHT")) {
			if(utilityFunction.Validate("CURRENT_FLIGHT")==true)
			{
				String currentflight=utilityFunction.getElementText("CURRENT_FLIGHT");
				System.out.println("The current flight number is : "+currentflight);
				/*if(utilityFunction.Validate("NEXT_FLIGHTS")==true)
				{
					List NextFligts=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("NEXT_FLIGHTS")));
					System.out.println("Total no of next flights are = "+NextFligts.size());
					for( int i=0; i<NextFligts.size();i++)
					{
						WebElement ele=(WebElement) NextFligts.get(i);
						String NextFlightName=ele.getText();
					System.out.println("Next flights name are :"+NextFlightName);
					}
					res=true;
				}else
				{
					System.out.println("There is no Next fligt for default time range.");
					return res;
				}*/
				res=true;
			}else
			{
				System.out.println("There is no CURRENT fligt for default time range.");
				return res;
			}
		}else {
		System.out.println("There is no current flights.");
		return res;
		}
	}catch (Exception e) {
		System.out.println("Failed to display the Current & Next flights for the subscribed flight.");
	}
	return res;
}
/*
 * Function Name: verifyFlightTopicsandMsgs 
 * Author: Niranjan Gowda S R 
 * Description: This function is to verify the auto subscription of flights and respect messages
 * Input Parameters: Flight number
 * Date Modified:
 * By:
 */
public boolean verifyFlightTopicsandMsgs() {
	
	boolean res = false;
	try {
		if(utilityFunction.waitForElementVisibility("CURRENT_FLIGHT")) {
			{
			if(utilityFunction.Validate("CURRENT_FLIGHT")==true)
			{
				utilityFunction.clickElement("CURRENT_FLIGHT");
				WebElement olist=	utilityFunction.driver.findElement(By.xpath(utilityFunction.readObjectRepositoryFile("GP_TP_MSG_NUMBER")));
				if(olist.isDisplayed())
				{
					Thread.sleep(500);
					utilityFunction.clickElement("MSG_POPUP_YES_BUTTON");
				}else
				{
					System.out.println("There were no new messages.");
				}
				List msgcnt=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("MESSAGE1")));
				System.out.println("Total number of messages are : "+msgcnt.size());
				if(utilityFunction.waitForElementVisibility("NEXT_FLIGHTS")==true)
				{
					List NextFligts=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("NEXT_FLIGHTS")));
					for (int i=0; i<NextFligts.size(); i++)
					{
						WebElement ele=(WebElement) NextFligts.get(i);
						ele.click();
						WebElement olist1=	utilityFunction.driver.findElement(By.xpath(utilityFunction.readObjectRepositoryFile("GP_TP_MSG_NUMBER")));
						if(olist1.isDisplayed())
						{
							Thread.sleep(500);
							utilityFunction.clickElement("MSG_POPUP_YES_BUTTON");
						}else
						{
							System.out.println("There were no new messages.");
						}
						List msgcnt1=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("MESSAGE1")));
						System.out.println("Total number of messages are : "+msgcnt1.size());
					}	
				}else
				{
					System.out.println("Next flights not yet updated for the selected time range.");
				}
				res=true;
			}else
			{
				System.out.println("There is no CURRENT fligt for default time range.");
				return false;
			}
			}
		}else {
		System.out.println("There is no current flights.");
		return res;
		}
	}catch (Exception e) {
		System.out.println("Failed to display the Current & Next flights for the subscribed flight.");
	}
	return res;
}

/*
 * Function Name: verifyFlightTopicsandMsgs 
 * Author: Niranjan Gowda S R 
 * Description: This function is to verify the auto subscription of flights and respect messages
 * Input Parameters: Flight number
 * Date Modified:
 * By:
 */
public boolean verifyUserInitialsOnMsgs() {
	
	boolean res = false;
	try {
		if(utilityFunction.waitForElementVisibility("GATE_PANEL_GATENO")) {
			if(utilityFunction.Validate("GATE_PANEL_GATENO")==true)
			{
				Thread.sleep(1000);
				utilityFunction.clickElement("GATE_PANEL_GATENO");
				List msgcnt=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("MESSAGE1")));
				System.out.println("Total number of messages are : "+msgcnt.size());
				if(msgcnt.size()>=1)
				{
					utilityFunction.waitForElementVisibility("MP_USERNAME_INITIALS");
					List inicnt=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("MP_USERNAME_INITIALS")));
					System.out.println("Total number of user initials are : "+inicnt.size());
					for(int i=0 ;i<inicnt.size(); i++)
					{
						WebElement ele=(WebElement) inicnt.get(i);
						ele.click();
						Thread.sleep(1000);
						res=true;
					}	
				}	
			}else
			{
				System.out.println("Failed to display the gate number on the gate panel.");
				return false;
			}
		}else {
		System.out.println("Failed to display the gate number on the gate panel");
		return res;
		}
	}catch (Exception e) {
		System.out.println("Failed to display the Current & Next flights for the subscribed flight.");
	}
	return res;
}

/*
 * Function Name: verifyTopicPanel 
 * Author: Sneha S Bhojugade 
 * Description: This function is to verify the topic panel and the gate being displayed under it
 * Input Parameters: Flight number
 * Date Modified:
 * By:
 */

public boolean verifyGateNumber() {
	
	boolean res = false;
	String strGateNumber = null;
	String strGateHeader = null;
	String[] strparts = null;
	try {
		if(utilityFunction.waitForElementVisibility("GATE_TAB"))
			utilityFunction.clickElement("GATE_TAB");
		if(utilityFunction.waitForElementVisibility("GATE_TAB_GATE_NUMBER"))
			strGateNumber = utilityFunction.getElementText("GATE_TAB_GATE_NUMBER");
		if(utilityFunction.waitForElementVisibility("LOGIN_GATE_NUMBER")) {
			strGateHeader = utilityFunction.getElementText("LOGIN_GATE_NUMBER");
			strGateHeader = strGateHeader.replace(" ","");
			strparts = strGateHeader.split("·");
		}
		if(strGateNumber.equals(strparts[1]))
			res = true;
	}catch (Exception e) {
		System.out.println("Gate numbers not verified");
	}
	return res;
}

public boolean verifyPriorityNotification() {
	
	boolean res = false;
	int PrCnt = 0;
	try {
		if(utilityFunction.waitForElementVisibility("PRIORITY_BELL_COUNT")==true)
		{
			utilityFunction.Validate("PRIORITY_BELL_COUNT");
				String prio = utilityFunction.getElementText("PRIORITY_BELL_COUNT");
				PrCnt = Integer.parseInt(prio);
				System.out.println("Total number of priority messages are : "+PrCnt);
				if(PrCnt>0)
				{
					utilityFunction.clickElement("PRIORITY_BELL");
					utilityFunction.waitForElementInVisibility("PR_NOTIFICATIONS");
					if(utilityFunction.getElementText("PR_NOTIFICATIONS").equals("Notifications"))
					{
						utilityFunction.clickElement("PRIORITY_BELL");
						res = true;
					}
				}System.out.println("There are no priority messages");
				res = true;
				
			}else
			{
				System.out.println("There are no priority messages");
				res = true;
			}
		
	}catch (Exception e) {
		System.out.println("There are no priority messages");
		res = true;
	}
	return res;
}

/*---------------------------------------------------------------------------------------------------*/
public boolean getMesTopicFromNotificationTab() throws InterruptedException {
	 ArrayList<String> allMessageTitles = new ArrayList<>();
		boolean res = false;
		if (utilityFunction.waitForElementVisibility("PR_NOTIFICATION_HEADER")) {
			if (utilityFunction.Validate("PR_MESSAGES_TOPIC") == true) {
			/*
			 * List<WebElement> messageTimings = utilityFunction.driver
			 * .findElements(By.xpath(
			 * "//*[@id=\"notifications\"]//div[@class=\"message-body\"]"));
			 */
				
				List<WebElement> messageTimings = UtilityFunction.driver
						.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("PR_MESSAGES_TOPIC")));

				System.out.println("Number of priority messages displayed : "+ messageTimings.size());
				for (WebElement time : messageTimings) {
					String Text =time.getText();
					allMessageTitles.add(Text);
					
				}
				System.out.println("Message timings displayed by order"+ allMessageTitles);
				res= true;	
			}
		}
		return res;
	}
public boolean validateContentsOfHeaderInfo(String Station, String Gateno) {

	UtilityFunction utilityFunction = new UtilityFunction();
	boolean res = false;
	/*
	 * String strGateNumber = null; String strGateHeader = null;
	 */
	String[] strparts = null;

	try {
		// wait till Dashboard page appears on screen
		if (utilityFunction.waitForElementVisibility("DASHBOARD_HEADER_CONNECTME_LOGO")) {
			String GateInfo = utilityFunction.getElementText("DASHBOARD_HEADER_STATION_GATE_NAME");
			GateInfo = GateInfo.replace(" ", "");
			strparts = GateInfo.split("·");
			System.out.println("The dashboard header content is displayed : " + GateInfo);
			if (Station.equals(strparts[0]) && Gateno.equals(strparts[1]))
				res = true;

		} else {
			System.out.println("Dashboard Header Gate info doesn't match with the Actual values ");

		}
	} catch (Exception e) {
		System.out.println("Failed to validate the header gate information" + e.getMessage());
		return false;
	}
	return res;
}
public boolean VerifyingMPDateFormat() {
	boolean res = false;
	String Convertedate = null;
	String MpDate = null;
	try {
		Thread.sleep(4000);
		if (utilityFunction.waitForElementVisibility("MP_DATE_FORMAT") == true) {
			if (utilityFunction.Validate("MP_DATE_FORMAT") == true) {
				Convertedate = utilityFunction.DateValidation();
				System.out.println("Converted date format : " + Convertedate);
				MpDate = utilityFunction.getElementText("MP_DATE_FORMAT");
				System.out.println("Message panel date format : " + MpDate);
				if (Convertedate.contains(MpDate)) {
					res = true;
				} else {
					return res;
				}
			}
		} else {
			System.out.println("Failed to display the date format on the message panel");
			return res;
		}
	} catch (Exception e) {
		System.out.println("Date format Exception!");
	}
	return res;
}
public String getFirstFlightNumber() {
	String res = null;
	String s[] = null;

	try {
		
		if (utilityFunction.waitForElementVisibility("CURRENT_FLIGHT") == true) {
			Thread.sleep(3000);
			if (utilityFunction.waitForElementVisibility("SUBSCRIBED_FLIGHT_LIST") == true)
				if (utilityFunction.Validate("CURRENT_FLIGHT") == true) {
					res = utilityFunction.getElementText("CURRENT_FLIGHT");
					s = res.split(" ");
					res = s[1];
					System.out.println("selected flight  number:" + res);
					return res;
				}
		} else {
			System.out.println("Flight number is not updated");
			return res;
		}
	} catch (Exception e) {
		System.out.println("Failed to get the filght number.");
		return res;
	}

	return res;
}
public boolean slectingGatethread() {
	boolean res = false;
	try {
		if (utilityFunction.Validate("GATE_TAB_GATE_NUMBER") == true) {
			utilityFunction.clickElement("GATE_TAB_GATE_NUMBER");
			Thread.sleep(2000);
			if (utilityFunction.Validate("MP_YES_BTN") == true) {
				utilityFunction.clickElement("MP_YES_BTN");
				res = true;
			} else {
				utilityFunction.clickElement("GATE_TAB_GATE_NUMBER");
			}

		} else {
			System.out.println("unable to click on gate thread");
		}
		Thread.sleep(1000);
	} catch (Exception e) {

		System.out.println("failed to click on gate thread");
		utilityFunction.clickElement("GATE_TAB_GATE_NUMBER");
		res = true;
	}


	return res;
}
public boolean verifyPriorityBell() {
	boolean res = false;
	try {
		utilityFunction.waitForElementVisibility("PRIORITY_BELL");
		if (utilityFunction.Validate("PRIORITY_BELL") == true) {
			System.out.println("Priority Bell Displayed in Dashboard");
			bellCount = utilityFunction.getElementText("PRIORITY_BELL_COUNT");
			System.out.println("Priority Bell count: " + bellCount);
















			res = true;
		}
	} catch (Exception e) {
		System.out.println("Priority Bell Not Displayed in Dashboard");
	}




	return res;
}

public boolean clickOnPriorityBell() {
	boolean res = false;
	try {
		if (utilityFunction.Validate("PRIORITY_BELL") == true) {
			System.out.println("Priority Bell Displayed in Dashboard");
			utilityFunction.clickElement("PRIORITY_BELL");
			System.out.println("Clicked on Priority Bell");
			utilityFunction.waitForElementVisibility("PR_NOTIFICATION_HEADER");
			if (utilityFunction.Validate("PR_NOTIFICATION_HEADER") == true) {
				String header = utilityFunction.getElementText("PR_NOTIFICATION_HEADER");
				if (header.equalsIgnoreCase("Notifications")) {
					System.out.println("Notifications tab displayed");
					res = true;

				} else {
					System.out.println("Notifications tab not displayed");
					return res;
				}
			}
		}
	} catch (Exception e) {
		System.out.println("Priority Bell Not Displayed in Dashboard");
	}
	return res;
}

public boolean getListOfMesFromNotificationTab() throws InterruptedException {

	boolean res = false;
	try {

		if (utilityFunction.waitForElementVisibility("PR_NOTIFICATION_HEADER")) {
			if (utilityFunction.Validate("PR_MESSAGES_COUNT") == true) {

				List<WebElement> listOfRows = UtilityFunction.driver
						.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("PR_LIST_MESSAGES_COUNT")));

				System.out.println("List of priority messages : " + listOfRows.size());
				if (listOfRows.size() == (Integer.parseInt(bellCount))) {
					System.out.println("Priority bell count : " + bellCount + " matching with"
							+ "Unacknowledged priority messages : " + listOfRows.size());
					res = true;
				} else {
					System.out.println("Priority bell count : " + bellCount + " not matching with"
							+ "Unacknowledged priority messages : " + listOfRows.size());
					return res;
				}
			}
		}
	} catch (Exception e) {
		System.out.println("List of priority messages not displayed in dashboard");
	}
	return res;
}

public boolean getMesTimesFromNotificationTab() throws InterruptedException {
	 ArrayList<String> allMessageTimes = new ArrayList<>();
		boolean res = false;
		utilityFunction.addDelay(13000);
		if (utilityFunction.waitForElementVisibility("PR_NOTIFICATION_HEADER")) {
			if (utilityFunction.Validate("PR_MESSAGES_TIMES") == true) {
			/*
			 * List<WebElement> messageTimings = utilityFunction.driver
			 * .findElements(By.xpath(
			 * "//*[@id=\"notifications\"]//div[@class=\"message-footer-time\"]"));
			 */
				
				List<WebElement> messageTimings = UtilityFunction.driver
						.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("PR_MESSAGES_TIMES")));

				System.out.println("Number of priority messages displayed : "+ messageTimings.size());
				for (WebElement time : messageTimings) {
					String Text =time.getText();
					String[] Time = Text.split(":");
					allMessageTimes.add(Time[0]+Time[1]);
					
				}
				System.out.println("Message timings displayed by order"+ allMessageTimes);
				for(int k=0; k<messageTimings.size()-1; k++) {
					if(Integer.parseInt(allMessageTimes.get(k)) >= Integer.parseInt(allMessageTimes.get(k+1))) {
						System.out.println("Message timings displayed by desending order");
						res = true;
					}else {
						System.out.println("Message timings displayed by accending order");
						return res;
					}
				}
				
			}
		}
		return res;
	}
public boolean getRolesFromNotificationTab() throws InterruptedException {
	 ArrayList<String> allMessageRoles = new ArrayList<>();
		boolean res = false;
		if (utilityFunction.waitForElementVisibility("PR_NOTIFICATION_HEADER")) {
			if (utilityFunction.Validate("PR_MESSAGES_ROLE") == true) {
			/*
			 * List<WebElement> messageTimings = utilityFunction.driver
			 * .findElements(By.xpath(
			 * "//*[@id=\"notifications\"]//div[@class=\"message-footer-role\"]"));
			 */
				List<WebElement> messageTimings = UtilityFunction.driver
						.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("PR_MESSAGES_ROLE")));

				System.out.println("Number of priority messages displayed : "+ messageTimings.size());
				for (WebElement time : messageTimings) {
					String Text =time.getText();
					allMessageRoles.add(Text);
					
				}
				System.out.println("\n Message timings displayed by order"+ allMessageRoles);
				res= true;
			}
		}
		return res;
	}
public boolean getMesTitleFromNotificationTab() throws InterruptedException {
	 ArrayList<String> allMessageTitles = new ArrayList<>();
		boolean res = false;
		if (utilityFunction.waitForElementVisibility("PR_NOTIFICATION_HEADER")) {
			if (utilityFunction.Validate("PR_MESSAGES_TITLE") == true) {
			/*
			 * List<WebElement> messageTimings = utilityFunction.driver
			 * .findElements(By.xpath(
			 * "//*[@id=\"notifications\"]//div[@class=\"message-body\"]"));
			 */
				
				List<WebElement> messageTimings = UtilityFunction.driver
						.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("PR_MESSAGES_TITLE")));

				System.out.println("Number of priority messages displayed : "+ messageTimings.size());
				for (WebElement time : messageTimings) {
					String Text =time.getText();
					allMessageTitles.add(Text);
					
				}
				System.out.println("Message timings displayed by order"+ allMessageTitles);
				res= true;	
			}
		}
		return res;
	}

public boolean verifyIconOnPriorityMessages() {
	
	boolean res = false;
	try {
		if(utilityFunction.waitForElementVisibility("MESSAGE1")==true)
		{
			Thread.sleep(5000);
			if(utilityFunction.Validate("PR_ICON_ON_MESSAGE")==true);
				
				System.out.println("Bell icon validated on Priority Message ");
				res = true;	
			}else
			{
				System.out.println("Failed to validated Bell icon on Priority Message");
				return res;
			}
		
	}catch (Exception e) {
		System.out.println("Failed to validated Bell icon on Priority Message");
		return res;
	}
	return res;
}

public static boolean changeGateBasedonGetngResults(String strGentgGateNum) 
{
	boolean res = false;
	String strStatus;
	String GreyColor = "#627a88";
	String strColor;
	try {
		for(int i=1; i<GetNgPage.DepFltGates.size(); i++)
		{
				if (loginPage.changeGatenumber(strGentgGateNum, GetNgPage.DepFltGates.get(i)) == true) 
				{
					System.out.println("Station name :- "+strGentgGateNum+"  &     Gate number :- "+GetNgPage.DepFltGates.get(i));
					if(utilityFunction.Validate("FLIGHT_STATUS_BANNER")==true)
					{
						strStatus = utilityFunction.getElementText("FLIGHT_STATUS_BANNER");
						if(strStatus.equals(GetNgPage.DepFltStatus.get(i)))
						{
							strColor = utilityFunction.getElementColor("FLIGHT_STATUS_BANNER_COLOR");
							if(strColor.equals(GreyColor))
							{
								res = true;
							}else
							{
								return res;
							}
						}	
					}	
					System.out.println("Station name"+strGentgGateNum+"  &     Gate number"+GetNgPage.DepFltGates.get(i));
               res = true;
               break;
				}
		}
	}catch (Exception e) {
		System.out.println("Failed to validated Bell icon on Priority Message");
		return res;
	}
	return res;
}

public static boolean verifyNextFlightMovedToCurrentThread(String strGentgGateNum) 
{
	boolean res = false;
	
	try {
		for(int i=1; i<GetNgPage.DepFltGates.size(); i++)
		{
				if (loginPage.changeGatenumber(strGentgGateNum, GetNgPage.DepFltGates.get(i)) == true) 
				{
					System.out.println("Station name :- "+strGentgGateNum+"  &     Gate number :- "+GetNgPage.DepFltGates.get(i));
					if(utilityFunction.Validate("FLIGHT_STATUS_BANNER")==true)
					{
					List<WebElement> ele = utilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("CURRENT_FLT")));
						if(ele.size()>=2)
						{
							for (WebElement fltDetails : ele) 
							{
								Thread.sleep(2000);
								String FltNames = fltDetails.getText();
								System.out.println("Total number of Current flights are : "+FltNames);
							  res = true;
							}
						}else if(ele.size()==1)
						{
							for (WebElement fltDetails : ele) {
								Thread.sleep(2000);
								String FltNames = fltDetails.getText();
								System.out.println("There is only one current flight at this moment : "+FltNames);
							  res = true;
						}
						}
					}	
					System.out.println("Station name"+strGentgGateNum+"  &     Gate number"+GetNgPage.DepFltGates.get(i));
               res = true;
               break;
				}
		}
	}catch (Exception e) {
		System.out.println("There are no Current OR Next flights available at this moment.");
		return res = true;
	}
	return res;
}

public static boolean waitingForOFFStatusFlights(String strGentgGateNum) 
{
	boolean res = false;
	String strStatus;
	try {
		for(int i=1; i<GetNgPage.DepFltGates.size(); i++)
		{
				if (loginPage.changeGatenumber(strGentgGateNum, GetNgPage.DepFltGates.get(i)) == true) 
				{
					System.out.println("Station name :- "+strGentgGateNum+"  &     Gate number :- "+GetNgPage.DepFltGates.get(i));
					if(utilityFunction.Validate("FLIGHT_STATUS_BANNER")==true)
					{
						strStatus = utilityFunction.getElementText("FLIGHT_STATUS_BANNER");
						//OUT status flights
						if(strStatus.equals("OUT"))
						{
							while(utilityFunction.Validate("FLIGHT_STATUS_BANNER")==true)
							{ 
								int k=1;
								Thread.sleep(10000);
								System.out.println("Looping for status from OUT status for every 20 sces : This is "+k+" loop.");
								if(strStatus==null)
								{
									System.out.println("Waiting for the flight to take take off for the ++++ "+20000+" * "+k+" = "+ 20000*k + " ++++++ms.");
									res = true;
									break;		
								}
								k++;
							}
							//OFF status flights
						}else if(strStatus.equals("OFF"))
						{
							/*15 min= 900000 ms
							 10000*90 = 900000 ms
							 */
							for(int j=0;j<=90;j++)
							{
								if(utilityFunction.Validate("FLIGHT_STATUS_BANNER")==true)
								{
									int k=1;
									Thread.sleep(20000);
									System.out.println("Looping for status from OFF status for every 20 sces : This is "+k+" loop.");
									if(strStatus==null)
									{
										System.out.println("Flight got take off with in the time range of ++++ "+20000+" * "+k+" = "+ 20000*k + " ++++++ms.");
										res = true;
										break;
									}
									k++;
								}
							}
						}
					}	
					System.out.println("Station name"+strGentgGateNum+"  &     Gate number"+GetNgPage.DepFltGates.get(i));
               res = true;
               break;
				}
		}
	}catch (Exception e) {
		System.out.println("There is no flights are available at this moment to validate the OFF or OUT staus");
		return res = true;
	}
	return res;
}

public static boolean waitingForOFFFlightsPopUp(String strGentgGateNum) 
{
	boolean res = false;
	String strStatus;
	try {
		for(int i=1; i<GetNgPage.DepFltGates.size(); i++)
		{
				if (loginPage.changeGatenumber(strGentgGateNum, GetNgPage.DepFltGates.get(i)) == true) 
				{
					System.out.println("Station name :- "+strGentgGateNum+"  &     Gate number :- "+GetNgPage.DepFltGates.get(i));
					if(utilityFunction.Validate("FLIGHT_STATUS_BANNER")==true)
					{
						strStatus = utilityFunction.getElementText("FLIGHT_STATUS_BANNER");
						//OUT status flights
						if(strStatus.equals("OUT"))
						{
							while(utilityFunction.Validate("FLIGHT_STATUS_BANNER")==true)
							{ 
								int k=1;
								Thread.sleep(10000);
								System.out.println("Looping for status from OUT status for every 20 sces : This is "+k+" loop.");
								if(strStatus==null)
								{
									System.out.println("Waiting for the flight to take take off for the ++++ "+20000+" * "+k+" = "+ 20000*k + " ++++++ms.");
									res = true;
									break;		
								}
								k++;
							}
							//OFF status flights
						}else if(strStatus.equals("OFF"))
						{
							/*15 min= 900000 ms
							 10000*90 = 900000 ms
							 */
							for(int j=0;j<=90;j++)
							{
								if(utilityFunction.Validate("FLIGHT_STATUS_BANNER")==true)
								{
									int k=1;
									Thread.sleep(20000);
									System.out.println("Looping for status from OFF status for every 20 sces : This is "+k+" loop.");
									if(strStatus==null)
									{
										System.out.println("Flight got take off with in the time range of ++++ "+20000+" * "+k+" = "+ 20000*k + " ++++++ms.");
										if(utilityFunction.Validate("FLIGHT_OFF_POP_UP")==true)
										res = true;
										break;
									}
									k++;
								}
							}
						}
					}	
					System.out.println("Station name"+strGentgGateNum+"  &     Gate number"+GetNgPage.DepFltGates.get(i));
               res = true;
               break;
				}
		}
	}catch (Exception e) {
		System.out.println("There is no flights are available at this moment to validate the OFF or OUT staus");
		return res = true;
	}
	return res;
}
public boolean ManualChangeGate() throws Exception {
	boolean res = false;
if(utilityFunction.waitForElementVisibility("DASHBOARD_HEADER_MORE_ICON")==true) {
	Thread.sleep(2000);
	if(utilityFunction.Validate("DASHBOARD_HEADER_MORE_ICON")==true) {
		utilityFunction.clickElement("DASHBOARD_HEADER_MORE_ICON");
		Thread.sleep(2000);
		utilityFunction.clickElement("CHANGE_GATE");
		Thread.sleep(2000);
		utilityFunction.enterValueInEditField("STATION_CODE", "DFW");
		Thread.sleep(2000);
		if(utilityFunction.Validate("LOGIN_GATE_NUMBER")==true){
		utilityFunction.driver.findElement(By.xpath("//input[@placeholder='Gate Number']")).sendKeys("A8");
		Thread.sleep(2000);
		utilityFunction.clickElement("SUBMIT");
	System.out.println("sucessfully ");
	Thread.sleep(2000);
	utilityFunction.clickElement("GATE_TAB_GATE_NUMBER");
	res=true;
		}
	}
}
	return res;}
}
