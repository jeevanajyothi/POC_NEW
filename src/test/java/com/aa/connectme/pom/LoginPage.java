package com.aa.connectme.pom;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.aa.connectme.util.Report;
import com.aa.connectme.util.UtilityFunction;
import com.aa.connectme.util.Xls_Reader;

public class LoginPage extends UtilityFunction {
	
	private UtilityFunction utilityFunction = new UtilityFunction();
	private Report report = new Report();
	private Xls_Reader xlsReader = new Xls_Reader();
	/*
	* Function Name: login
	* Author: Niranjan Gowda S R
	* Date of Creation: 24-Dec
	* Description: This function is to perform login into the application using the Gate Number, Station.
	* Input Parameters: 3 letter station code and 2-4 Gate Number
	* Date Modified: If any
	* Reviewed By:
	*/
	public boolean login(String station,String GateNum) throws IOException {
		boolean res = false;
		
		try {
					
			//validate if login username is dispalyed when clicked on the URL
		if(utilityFunction.Validate("LOGIN_GATE_SELECTION_TAB") == true){   
				System.out.println("Gate selection tab is displayed with Gate Number Input Field");
					
				//Enter GateNumber and station code and then click on submit button
				utilityFunction.enterValueInEditField("LOGIN_STATION", station);
				utilityFunction.enterValueInEditField("LOGIN_GATE_NUMBER", GateNum);
				utilityFunction.clickElement("LOGIN_SUBMIT");
				
				//Sign is successfull only when we get the entered gate name is displayed in dashboard.
				if(utilityFunction.waitForElementVisibility("DASHBOARD_HEADER_GATE_INFO"))
					System.out.println("Login in was Sucessful and the entered gate name is displayed in dashboard");
				else
					System.out.println("Login in was not Sucessful and the entered gate name is not displayed in dashboard");
				//if entered gate name is displayed in the dashboard 
				if(utilityFunction.Validate("DASHBOARD_HEADER_GATE_INFO") == true){
					String GateInfo=utilityFunction.getElementText("DASHBOARD_HEADER_GATE_INFO");
					System.out.println("The Gate Information is:"+GateInfo);
					res = true;
				}
			}
				else {
					System.out.println("Failed to display the Gate Number input field");
			 }
		 }
		catch(Exception e){
			System.out.println("Login Failed");
		}
		return res;
	}
public String loginButtonStatus() {
		String res="";
		if(utilityFunction.Validate("loginDisabled") == true) {
			WebElement wb=utilityFunction.getWebElement("loginDisabled");
			if(wb.isEnabled()) {
				res="Login button is enabled";
			}
			else 
			{
				res="Login button is disabled";
			}

		}
		return res;
	}

	public boolean loginFunctionVerification(String User,String Pass) throws IOException {
		boolean res = false;
		int falseCount=0;

		try {
			//Hint verification
			if(utilityFunction.Validate("PERSONNEL_PAGE_USERID") && utilityFunction.Validate("PERSONNEL_PAGE_PASSWORD") && utilityFunction.Validate("loginDisabled") == true) {
				System.out.println("userName & password hints are showing correctly.");	
				res=true;

			}
			else {
				System.out.println("userName & password hints are showing incorrectly........!!");
				falseCount++;
			}

			//disable login button verification

			if(!(loginButtonStatus().equalsIgnoreCase("Login button is disabled"))) {
				falseCount++;
			}

			//waring color
			if(utilityFunction.Validate("PERSONNEL_PAGE_USERID") == true) {
				//utilityFunction.enterValueInEditField("PERSONNEL_PAGE_PASSWORD", Pass);
				utilityFunction.clickElement("PERSONNEL_PAGE_USERID");
				utilityFunction.clickElement("PERSONNEL_PAGE_PASSWORD");
				utilityFunction.clickElement("PERSONNEL_PAGE_USERID");

				if(utilityFunction.Validate("userNameEmptyError")&&utilityFunction.Validate("passwordEmptyError")) {
					String color="rgba(245, 35, 5, 1)";
					if(utilityFunction.getWebElement(("userNameEmptyError")).getCssValue("color").equalsIgnoreCase(color)&&utilityFunction.getWebElement(("passwordEmptyError")).getCssValue("color").equalsIgnoreCase(color)) {
						System.out.println("Warning Text color verified sucessfully");
					}
					else {
						falseCount++;
					}
									
				}
				else {
					System.out.println("Error with warning msg(Red Color)");
					falseCount++;
				}

			}
			//verifyHeaderMsg
			if(utilityFunction.Validate("logiHeaderMsg")) {
				System.out.println("Header veifed sucessfully");
			}
			else {
				falseCount++;
				System.out.println("Header veifed unsucessfully");
			}
			//verifyHeaderMsg
			if(utilityFunction.Validate("NotificationIcon")&&utilityFunction.Validate("connectMeHeaderLogo")&&utilityFunction.Validate("defaultStationNameandGate")) {

				System.out.println("Notification,ConnectmeHeader & deafultstation displaying sucessfully");
			}
			else {
				falseCount++;
				System.out.println("Notification,ConnectmeHeader & deafultstation displaying unsucessfully");
			}
			//verifygate&topic option is enanled
			if(utilityFunction.Validate("gateEnabled")&&utilityFunction.Validate("topicsDisabled")&&utilityFunction.Validate("defaultStationNameandGate")) {

				System.out.println("Gate is enabled & Topics disabled");
			}
			else {
				falseCount++;
				System.out.println("Gate is not enabled");
			}
			//verifyTimechageIcon&Searchoption
			if(utilityFunction.Validate("timeChangeIcon")&&utilityFunction.Validate("searchTopicORgate")) {
				System.out.println("Search option & hoursChange icon is displaying sucessfully");
			}
			else {
				falseCount++;
				System.out.println("Search option & hoursChange icon is displaying unsucessfully");
			}
		}
		catch(Exception e){
			System.out.println("Login page verification Failed");
		}
		return falseCount==0;
	}	
		
	/*
	* Function Name: Personnel Login Page
	* Author: Niranjan Gowda S R
	* Date of Creation: 24-Dec
	* Description: This function is to perform login into the application using the Gate Number, Station.
	* Input Parameters: 3 letter station code and 2-4 Gate Number
	* Date Modified: If any
	* Reviewed By:
	*/
	public boolean Personnelogin(String User, String Pass) throws IOException {
		boolean res = false;
		
		try {
					
			utilityFunction.waitForElementVisibility("PERSONNEL_PAGE_USERID");
		   if(utilityFunction.Validate("PERSONNEL_PAGE_USERID") == true)
		     {   
			   Thread.sleep(5000);
				utilityFunction.enterValueInEditField("PERSONNEL_PAGE_USERID", User);
					if(utilityFunction.Validate("PERSONNEL_PAGE_PASSWORD") == true)
					{
						utilityFunction.enterValueInEditField("PERSONNEL_PAGE_PASSWORD", Pass);
						if(utilityFunction.Validate("PERSONNEL_PAGE_LOGIN")== true)
						{
							utilityFunction.clickElement("PERSONNEL_PAGE_LOGIN");
							System.out.println("UN , PWD is entered and click on Login button ");
							  utilityFunction.waitForElementVisibility("MP_MESSAGE_EDIT_BOX");
								if(utilityFunction.Validate("MP_MESSAGE_EDIT_BOX") == true)
								{
									System.out.println("Verified succesful login.");
								res = true;
								}else
								{
									System.out.println("Personal login unsuccesful.");
									return res;
									
								}
						}
					}
		     }
		 }
		catch(Exception e){
			System.out.println("Personnel Login Failed");
		}
		return res;
	}	
		/*
		 * 
		 * Function Name: login Invalid
		 * login Author: Niranjan Gowda S R
		 * Date of Creation:27-12-2017
		 * Description: This function is to perform invalid personnel login into the application using the username and password.
		 * Input Parameters: Username and password. 
		 * Date Modified:  
		 * If any Reviewed By:
		 */

		public boolean login_Invalid(String Station,String GateName, String ErrorMsg) throws IOException {
			boolean res = false;

			try {

				if (utilityFunction.Validate("LOGIN_GATE_SELECTION_TAB")) {
					System.out.println("Login Page is displayed with Gate Number Input Field");

					//Enter GateNumber and station code and then click on submit button
					utilityFunction.enterValueInEditField("LOGIN_STATION", Station);
					utilityFunction.enterValueInEditField("LOGIN_GATE_NUMBER", GateName);
					utilityFunction.clickElement("LOGIN_SUBMIT");

					if (utilityFunction.waitForElementVisibility("LOGIN_ERROR_POPUP")) {
						
						 String Errortext=getElementText("LOGIN_ERROR_POPUP");
						 System.out.println("pop up text is : "+Errortext);
							 if(Errortext.contains(ErrorMsg))
							 {
							 report.Reporter_ReportEvent(driver, "User logged in to ConnectMe Gate Application with "+GateName+" & "+Station+".", Constants.EXTENT_REPORT_PASS,
										"Actual and Expected error pop up text is matched with : "+GateName+" & "+Station);
							System.out.println("Actual error pop up text : "+Errortext+" & Expected error pop up text : "+ErrorMsg+" is matched. ");
							if (utilityFunction.waitForElementVisibility("LOGIN_ERROR_POPUP_CLOSE")) {
								res = utilityFunction.clickElement("LOGIN_ERROR_POPUP_CLOSE");
								System.out.println("LOGIN_ERROR_POPUP_CLOSE is displayed and Clicked on Close Button");
								 res = true;	
							} 
							}else {
								report.Reporter_ReportEvent(driver, "User logged in to ConnectMe Gate Application with "+GateName+" & "+Station+".", Constants.EXTENT_REPORT_FAIL,
										"Actual and Expected error pop up text is Doesn't match for : "+GateName+" & "+Station);
								utilityFunction.waitForElementVisibility("LOGIN_ERROR_POPUP_CLOSE") ;
								res = utilityFunction.clickElement("LOGIN_ERROR_POPUP_CLOSE");
								System.out.println("LOGIN_ERROR_POPUP_CLOSE is displayed and Clicked on Close Button");
									return false;
								}
					}else
					{
					Field_Blank(GateName,Station);
					report.Reporter_ReportEvent(driver, "User logged in to ConnectMe Gate Application with "+GateName+" & "+Station+".", Constants.EXTENT_REPORT_FAIL,
							"Failed to logged into ConnectMe Gate App for : "+GateName+" & "+Station);
					return false;
					}
				} else {
					System.out.println("User is not logged in to ConnectMe Gate successfully");

				}
			} catch (Exception e) {
				System.out.println("Login Failed");
			}
			return res;
		}
		/*
		 * 
		 * Function Name: Personnel login Invalid
		 * login Author: Niranjan Gowda S R
		 * Date of Creation:27-12-2017
		 * Description: This function is to perform invalid personnel login into the application using the username and password.
		 * Input Parameters: Username and password. 
		 * Date Modified:  
		 * If any Reviewed By:
		 */
		public boolean Personnelogin_Invalid(String UserId, String Password,String ErrorMsg) throws IOException {
			boolean res = false;

			try {
				if(utilityFunction.Validate("PERSONNEL_PAGE_USERID") == true){  
					
						utilityFunction.enterValueInEditField("PERSONNEL_PAGE_USERID", UserId);
						utilityFunction.enterValueInEditField("PERSONNEL_PAGE_PASSWORD", Password);
						utilityFunction.clickElement("PERSONNEL_PAGE_LOGIN");

					if (utilityFunction.waitForElementVisibility("PERSONNEL_LOGIN_ERROR_POPUP")) {
						
						String ErrorText=getElementText("PERSONNEL_LOGIN_ERROR_POPUP");
						System.out.println("The error pop up text is :"+ErrorText);
							if(ErrorMsg.contains(ErrorText))
							{
							System.out.println("Actual error pop up text : "+ErrorText+" & Expected error pop up text : "+ErrorMsg+" is matched. ");
							 
								if (utilityFunction.waitForElementVisibility("PERSONNEL_LOGIN_ERROR_POPUP_CLOSE")) 
								{
								  utilityFunction.clickElement("PERSONNEL_LOGIN_ERROR_POPUP_CLOSE");
								Thread.sleep(2000);
								System.out.println("PERSONNEL_LOGIN_ERROR_POPUP_CLOSE is displayed and Clicked on Close Button");
								res =  true;
								}
							}else{
								return false;
							}
					}else{
						return false;
					}

				} else {
					System.out.println("User is not logged in to ConnectMe Gate successfully");

				}
			} catch (Exception e) {
				System.out.println("Login Failed");
			}
			return res;
		}

		/*
		 * Function Name: login_Blank Author: Bharath Kummagoori 
		 * Date of Creation:18-Aug-2017 
		 * Description: This function is to perform blank login into the
		 * application using the username, password and 3 letter station code Input
		 * Parameters:username, password and 3 letter station code Input
		 *  Date Modified: 
		 *  If any Reviewed By:
		 */
		public boolean login_Blank(String Station, String GateName, String ErrorMsg) throws IOException {
			boolean res = false;

			try {
				if (utilityFunction.Validate("LOGIN_GATE_SELECTION_TAB")== true) {
					System.out.println("Login Page is displayed with Gate Number Input Field");
					utilityFunction.enterValueInEditField("LOGIN_STATION", Station);
					utilityFunction.enterValueInEditField("LOGIN_GATE_NUMBER", GateName);
					utilityFunction.clickElement("LOGIN_SUBMIT");	
					
					if(Station.equals("") || Station.length()<=3 && GateName.equals("") || GateName.length()<=4)
					{
						if( Station.length()<=3 && GateName.length()<=4)
						{
							String gtext=utilityFunction.getElementText("LOGIN_GATE_ERROR_MSG");
							System.out.println("Actual error message is : "+gtext);
							if(ErrorMsg.equals(gtext))
							{
								System.out.println("Expected error message is : "+ErrorMsg);
								System.out.println("The Actual and expected values are matched");
								report.Reporter_ReportEvent(driver, "User logged in to ConnectMe Gate Application", Constants.EXTENT_REPORT_PASS,
										"User not able to logged in to ConnectMe Gate App for : "+Station+" & "+GateName);
								 res = true;
							}
						}else if(Station.length()<=3 && GateName.equals(""))
						{
							String gtext=utilityFunction.getElementText("LOGIN_GATE_ERROR_MSG");
							System.out.println("Actual error message is : "+gtext);
							if(ErrorMsg.equals(gtext))
							{
								System.out.println("Expected error message is : "+ErrorMsg);
								System.out.println("The Actual and expected values are matched");
								report.Reporter_ReportEvent(driver, "User logged in to ConnectMe Gate Application", Constants.EXTENT_REPORT_PASS,
										"User not able to logged in to ConnectMe Gate App for : "+Station+" & "+GateName);
								 res = true;
							}
						}else if( Station.equals("") && GateName.length()<=4)
						{
							String stext=utilityFunction.getElementText("LOGIN_STATION_ERROR_MSG");
							System.out.println("Actual error message is : "+stext);
							if(ErrorMsg.equals(stext))
							{
								System.out.println("Expected error message is : "+ErrorMsg);
								System.out.println("The Actual and expected values are matched");
								report.Reporter_ReportEvent(driver, "User logged in to ConnectMe Gate Application", Constants.EXTENT_REPORT_PASS,
										"User not able to logged in to ConnectMe Gate App for : "+Station+" & "+GateName);
								 res = true;
							}
						}else if(Station.length()<=3 && GateName.length()<=4)
						{
							String stext=utilityFunction.getElementText("LOGIN_STATION_ERROR_MSG");
							System.out.println("Actual error message is : "+stext);
							if(ErrorMsg.equals(stext))
							{
								System.out.println("Expected error message is : "+ErrorMsg);
								System.out.println("The Actual and expected values are matched");
								report.Reporter_ReportEvent(driver, "User logged in to ConnectMe Gate Application", Constants.EXTENT_REPORT_PASS,
										"User not able to logged in to ConnectMe Gate App for : "+Station+" & "+GateName);
								 res = true;
							}
						}else if(Station.equals("") && GateName.equals(""))
						{
							String stext=utilityFunction.getElementText("LOGIN_STATION_ERROR_MSG");
							String gtext=utilityFunction.getElementText("LOGIN_GATE_ERROR_MSG");
							System.out.println("Actual error message is : "+stext+"+++++++++++++"+gtext+".");
							report.Reporter_ReportEvent(driver, "User logged in to ConnectMe Gate Application", Constants.EXTENT_REPORT_PASS,
									"User not able to logged in to ConnectMe Gate App for : "+Station+" & "+GateName);
							 res = true;
						}else
						{
							System.out.println("No values are entered");
							report.Reporter_ReportEvent(driver, "User logged in to ConnectMe Gate Application", Constants.EXTENT_REPORT_FAIL,
									"Failed to logged into ConnectMe Gate App for : "+Station+" & "+GateName);
							return false;
						}
					}else
					{
					report.Reporter_ReportEvent(driver, "User logged in to ConnectMe Gate Application", Constants.EXTENT_REPORT_FAIL,
							"Failed to logged into ConnectMe Gate App for : "+Station+" & "+GateName);
					return false;
					}
				}else
				{
					System.out.println("Failed to open the Login gate selection page");
					report.Reporter_ReportEvent(driver, "User logged in to ConnectMe Gate Application", Constants.EXTENT_REPORT_FAIL,
							"Failed to logged into ConnectMe Gate App for : "+Station+" & "+GateName);
					return false;
				}
				}catch (Exception e) {
				System.out.println("Login Failed...");
			}
			return res;
			
		}
		
		/*
		 * Function Name: login_Blank Author: Bharath Kummagoori 
		 * Date of Creation:18-Aug-2017 
		 * Description: This function is to perform blank login into the
		 * application using the username, password and 3 letter station code Input
		 * Parameters:username, password and 3 letter station code Input
		 *  Date Modified: 
		 *  If any Reviewed By:
		 */
		public boolean Field_Blank(String Station, String GateName) throws IOException {
			boolean res = false;
			
			try {
				if (Station.equals("") && GateName.equals("")) {
					String color1 = utilityFunction.getElementColor("LOGIN_STATION");
					String color2 = utilityFunction.getElementColor("LOGIN_GATE_NUMBER");
				
				System.out.println("The gate border colour is "+color1+" & the station border colour is "+color2);
					if (color1.equals(color2)) {
						System.out.println("Gate and Station name is not authenticated for empty");
						res = true;
						}else
						{
							return res;
						}
					}
			} catch (Exception e) {
			}
			return res;
		}
		/*
		 * Function Name: login_Blank Author: Niranjan Gowda S R 
		 * Date of Creation:2-Jan-2018 
		 * Description: This function is to perform blank login into the application using the username, password and 3 letter station code Input
		 * Parameters:username, password and 3 letter station code Input
		 *  Date Modified: 
		 *  If any Reviewed By:
		 */
		public boolean Personnel_Field_Blank(String Username,String Password) throws IOException {
			boolean res = false;
			
			try {
				if (Username.equals("") && Password.equals("")) {
				String color1 = utilityFunction.getborderColor("PERSONNEL_PAGE_USERID");
				String color2 = utilityFunction.getborderColor("PERSONNEL_PAGE_PASSWORD");
				System.out.println("The gate border colour is "+color1+" & the station border colour is "+color2);
					if (color1.equals(color2)) {
						System.out.println("Username and Password name is not authenticated for empty");
						res = true;
						}
					}else if (Username.equals("")) {
						String color = utilityFunction.getborderColor("PERSONNEL_PAGE_USERID");
						System.out.println(color);
						if (color.equals("Red")) {
							System.out.println("UserName is not authenticated as Username is empty.");
							res = true;
						}
					}else if (Password.equals("")) {
						String color = utilityFunction.getborderColor("PERSONNEL_PAGE_PASSWORD");
						System.out.println(color);
						if (color.equals("Red")) {
							System.out.println("Password is not authenticated as Password is empty");
							res = true;
						}
					} else
					{
						return res;
					}
			} catch (Exception e) {
			}
			return res;
		}
		/*
		 * Function Name: login_Blank Author: Niranjan Gowda S R 
		 * Date of Creation:27-Dec-2017 
		 * Description: This function is to perform blank login into the application using the username and password.
		 * Parameters:username and password
		 *  Date Modified: 
		 *  If any Reviewed By:
		 */
		public boolean Personnelogin_Blank(String Errmsg1, String Errmsg2) throws IOException {
			boolean res = false;

			try {
				if(utilityFunction.Validate("PERSONNEL_PAGE_USERID") == true)
				{   
						utilityFunction.enterValueInEditField("PERSONNEL_PAGE_USERID", " ");
						if(utilityFunction.Validate("PERSONNEL_PAGE_PASSWORD") == true)
						{
						   utilityFunction.enterValueInEditField("PERSONNEL_PAGE_PASSWORD", "");
						      utilityFunction.enterValueInEditField("PERSONNEL_PAGE_USERID", " ");
						
							if (utilityFunction.waitForElementVisibility("LOGIN_BLANK_USERID_ERROR_MSG"))
							{
								if(utilityFunction.Validate("LOGIN_BLANK_USERID_ERROR_MSG")== true)
								{
									 String usertext=utilityFunction.getElementText("LOGIN_BLANK_USERID_ERROR_MSG"); 
									 System.out.println("User id error msg : "+usertext);
									 if(utilityFunction.Validate("LOGIN_BLANK_PWD_ERROR_MSG")== true)
									 {
										 String pwdtext=utilityFunction.getElementText("LOGIN_BLANK_PWD_ERROR_MSG");
										 System.out.println("PWD error msg : "+pwdtext);
										 if(usertext.equals(Errmsg1) && pwdtext.equals(Errmsg2))
										 {
												res = true;
												 System.out.println("UN & PWD Blank field Error message verified");
										}
									 }
								}
							}
					
						}
					
				} else {
					System.out.println("Failed to login to the Personnel login page.");
					return false;
				}
			} catch (Exception e) {
				System.out.println("Personal Login Failed");
				return false;
			}
			return res;
		}
		
		
		/*
		* Function Name: login_OtherUser
		* Author: Ayush Raj
		* Date of Creation: 23-August-2017
		* Description: This function is to perform login into the application using the username, password and 3 letter station code
		* Input Parameters: 3 letter station code,username and password
		* Date Modified: If any
		* Reviewed By:
		*/
		
		public boolean login_OtherUser(String UserName,String Password,String Station) throws IOException {
			boolean res = false;
			
			try {
						
				//validate if login username is dispalyed when clicked on the URL
				if(utilityFunction.Validate("LOGIN_USERNAME") == true){   
					System.out.println("Login Page should be displayed");
						
					//Enter username password and station code and then click on sign in button
					utilityFunction.enterValueInEditField("LOGIN_USERNAME", UserName);
					utilityFunction.enterValueInEditField("LOGIN_PASSWORD", Password);
					utilityFunction.enterValueInEditField("LOGIN_STATION", Station);
					utilityFunction.clickElement("LOGIN_SIGN_IN");
					
					//Sign is successfull only when we get the role confirmation screen
					if(utilityFunction.waitForElementVisibility("LOGIN_ROLE_CONFIRMTION_YES"))
						System.out.println("Login in was Sucessful and Pop to change role confirmation should be dispalyed");
					else
						System.out.println("Login in was not Sucessful and Pop to change role confirmation should be dispalyed");
					//if login confirmation page is displayed then 
					if(utilityFunction.Validate("LOGIN_ROLE_CONFIRMTION_YES") == true){
						res = true;
					}
				}
					else {
						System.out.println("User is not logged in to ConnectMe App successfully");
				 }
			 }
			catch(Exception e){
				System.out.println("Login error!");
			}
			return res;
		}
		
		
		/*
         * Function Name: stationGatePersists
         * Author: Sneha Bhojugade
         * Date of Creation: 14th Jan 2018
         * Description: This function verifies if the previously entered station code and the gate number persists
         * Input Parameters: Station code and Gate number
         * Date Modified: If any
         * Reviewed By:
         */
         public boolean stationGatePersists(String Station, String Gate) throws Exception {
                boolean res = false;
                String strStation = null;
                String strGate = null;
                try{
                       Thread.sleep(5000);
                       if(utilityFunction.Validate("LOGIN_GATE_SELECTION_TAB"))
                       {
                             //WebElement TxtBoxContent = driver.findElement(By.id("xpath|//*[@id=\"mat-input-0\"]"));
                             //String Txt = TxtBoxContent.getText();
                             //System.out.println(Txt);
                             strStation = driver.findElement(By.xpath("//input[@placeholder='Station Code']")).getAttribute("value");
                             strGate = driver.findElement(By.xpath("//input[@placeholder='Gate Number']")).getAttribute("value");
                             if((strStation.equals(Station)) && (strGate.equals(Gate)))
                                    res = true;
                       }
                }
                catch(Exception e){
                       System.out.println("Exception!");
                }
                return res;
         }
         /*
          * Function Name: 
          * Author: Bharath Kummagoori
          * Date of Creation: 28th Jan 2019
          * Description:
          * Input Parameters: 
          * Date Modified: If any
          * Reviewed By:
          */
         public boolean login_CCH(String Username, String Password, String Station, WebDriver passeddriver) throws IOException {
     		System.out.println(Station);
        	 boolean res = false;
     		WebDriver driver;
             driver = passeddriver;
             WebElement element;
     		
     		try 
     		{
     			
     			driver.findElement(By.xpath("//input[@placeholder='AA ID']")).sendKeys(Username);
                 driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(Password);
                 driver.findElement(By.xpath("//input[@placeholder='Station Code']")).sendKeys(Station);
                 driver.findElement(By.xpath("//button[contains(text(),'Log In')]")).click();
                 Thread.sleep(5000);
                 element = driver.findElement(By.xpath("//div[@class='choose-profile']"));
                 if (element.isDisplayed()){
     				res = true;
     				return res; 
     			} 
                 else {
     				System.out.println("Failed to login");
     				return res;
     			 }
     		 }
     		catch(Exception e){
     			System.out.println("Login Failed");
     		}
     		return res;
     	}
         
         
         public boolean selectWorkGroup(WebDriver passeddriver) throws InterruptedException {
     		WebDriver driver;
             driver = passeddriver;
             WebElement element;
     		
     		boolean res = false;
     		utilityFunction.driver.findElement(By.xpath(utilityFunction.readObjectRepositoryFile("LOGIN_SELECTED_WORKGROUP"))).click();
     		Thread.sleep(2000);
     		element = utilityFunction.driver.findElement(By.xpath(utilityFunction.readObjectRepositoryFile("LOGIN_SELECTED_WORKGROUP")));
             if (element.isDisplayed()){
             	List<WebElement> wglist = utilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("WORKGROUP_LIST")));
     			System.out.println("Total number of Workgroups are = "+wglist.size());
     			Thread.sleep(1000);
     			if(wglist.size()>0)
     			{
     			element = (WebElement) wglist.get(3);
     			String SelectedWorkGroup = element.getText();
     			element.click();
     			System.out.println("WorkGroup Selected is " + SelectedWorkGroup);
     			res = true;
             }
     				else
     				{
     				 return	res;
     				}
     		} else {
     			System.out.println("WorkGroup is not selected");
     			return res;	
     		}
     		return res;
     	}
         
         
     	public boolean selectGateAgentRole(WebDriver passeddriver) throws InterruptedException 
    	{	
    		boolean res = false;
    		WebDriver driver;
    		driver = passeddriver;
    		WebElement element;
    		
    	element = utilityFunction.driver.findElement(By.xpath("//mat-select[@aria-label='Choose Role']"));
            if (element.isDisplayed()==true){
            	utilityFunction.driver.findElement(By.xpath("(//div[@class='mat-select-trigger']/div/span)[3]")).click();
            	Thread.sleep(3000);
            	 utilityFunction.driver.findElement(By.xpath("//span[text()=' Gate Agent ']")).click();
                 res = true;
			/*
			 * //div[@class='cdk-overlay-pane'])[3]/div/div/mat-option/span if
			 * (element.isDisplayed()){ List olist
			 * =utilityFunction.driver.findElements(By.xpath(
			 * "(//div[@class='cdk-overlay-pane'])[3]/div/div/mat-option/span"));
			 * System.out.println("Total number of roles is = "+olist.size());
			 * Thread.sleep(2000); if(olist.size()>0) { WebElement RoleSelection =
			 * (WebElement) olist.get(6); String SelectedRole = RoleSelection.getText();
			 * RoleSelection.click(); System.out.println("Roles Selected is " +
			 * SelectedRole); res = true; }else { return res; } }
			 */
            }
    	
    		 else {
    			System.out.println("Role is not selected");
    			return res;
    		}
    		return res;
    	}
     	
     	public boolean clickContinue(WebDriver passeddriver) throws InterruptedException 
    	{
    		WebDriver driver;
            driver = passeddriver;
            WebElement element;
    		boolean res=false;
    		element = driver.findElement(By.xpath("//button[contains(text(),'Continue')]"));
            if (element.isDisplayed()){
            	driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
            	Thread.sleep(2000);
        		res = true;
            }
    		
    		return res;
    	}
         
          
          public boolean changeGatenumber(String Station,String Gate) throws Exception{
                 boolean res = false;
                 String strgate;
                 try{
                	 Thread.sleep(5000);
                	 utilityFunction.waitForElementVisibility("DASHBOARD_HEADER_MORE_ICON");
          		   if(utilityFunction.Validate("DASHBOARD_HEADER_MORE_ICON") == true)
          		   { 
          			    Thread.sleep(1000);
          				utilityFunction.clickElement("DASHBOARD_HEADER_MORE_ICON");
          				utilityFunction.waitForElementVisibility("CHANGE_GATE");
            				 if(utilityFunction.Validate("CHANGE_GATE") == true) {
            					 utilityFunction.clickElement("CHANGE_GATE");
            					 Thread.sleep(2000);
            					 utilityFunction.enterValueInEditField("STATION_CODE", Station);
            					 utilityFunction.enterValueInEditField("GATE_NUMBER", Gate);		
            					    utilityFunction.clickElement("SUBMIT");
            					    Thread.sleep(8000);
            					    if(utilityFunction.waitForElementVisibility("GATE_THREAD"))
            					    	 if(utilityFunction.Validate("GATE_THREAD") == true)
            							   { 
            					    		 strgate =  utilityFunction.getElementText("GATE_THREAD");
            					    		 if(strgate.equals(Gate))
            					    		 {
            					    		 System.out.println("Station and Gate number changed successfully.");
            						          res = true;
            					    		 }else
            					    		 {
            					    			 System.out.println("Failed to validate the gate number After changing the gate number");
            					    			 return res;	 
            					    		 }
            							   }else
            							   {
            								   System.out.println("Failed to to change station and gatenumber ");
            								   return res;
            							   }            					          					 
            				 }                             
                      }                 
                 } catch(Exception e) {
                        System.out.println("Gate validation Exception!");
                 }
                 return res;
          }
          
          public boolean clickSelectAll(WebDriver passeddriver) throws InterruptedException{
      		boolean res=false;
      		WebDriver driver;
              driver = passeddriver;
              WebElement element; 
      		try
      		{
      			Thread.sleep(2000);
      			element = utilityFunction.driver.findElement(By.xpath(utilityFunction.readObjectRepositoryFile("LOGIN_GT_SELECT_ALL")));
                  if (element.isDisplayed()){
                  	Thread.sleep(1000);
                  	utilityFunction.driver.findElement(By.xpath(utilityFunction.readObjectRepositoryFile("LOGIN_GT_SELECT_ALL"))).click();
                  	if(verifyAllSelectedgates(passeddriver)==true)
          			{
          			res = true;
          			}
                  	else
          			{
          				return res;
          			}
      			}
      		else{
      			System.out.println("Failed to click the select all");
      			return res;
      		}
                  if(verifyAllSelectedgates(passeddriver)==true)
      			{
      			res = true;
      			}else
      			{
      				return res;
      			}
      		}catch(Exception e) {
      			System.out.println("Error while selecting the select all radion button");
      			return res;
      		}
      		return res;
      	}
          
          
          
          
          public boolean verifyAllSelectedgates(WebDriver passeddriver) throws InterruptedException{
      		boolean res=false;
      		WebDriver driver;
      		driver = passeddriver;
      		WebElement element;
      		try
      		{
      			Thread.sleep(2000);
      			 element = utilityFunction.driver.findElement(By.xpath("//input[@aria-checked='true']"));
      	            if (element.isDisplayed()) {
      	            	List gatecnt =  utilityFunction.driver.findElements(By.xpath("//span[@class='mat-checkbox-label']"));
      	    			System.out.println("Count of gates = "+gatecnt.size());
      	    			List gatechkboxcnt=  utilityFunction.driver.findElements(By.xpath("//input[@aria-checked='true']"));
      	    			System.out.println("Count of selected radio button = "+gatechkboxcnt.size());
      	    			if(gatecnt.size() == gatechkboxcnt.size())
      	    			{
      	    				System.out.println("Count of gates  =  Count of selected radio button");
      	    			res = true;
      	    			}
      	    			
      			/*utilityFunction.waitForElementVisibility("LOGIN_GATE_NAMES_CHKBOX");
      		if(utilityFunction.Validate("LOGIN_GATE_NAMES_CHKBOX")==true)
      		{
      			List gatecnt=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("LOGIN_GATE_NAMES")));
      			System.out.println("Count of gates = "+gatecnt.size());
      			List gatechkboxcnt=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("LOGIN_GATE_NAMES_CHKBOX")));
      			System.out.println("Count of selected radio button = "+gatechkboxcnt.size());
      			if(gatecnt.size() == gatechkboxcnt.size())
      			{
      				System.out.println("Count of gates  =  Count of selected radio button");
      			res = true;
      			}*/
      	    			else
      			{
      				return res;
      			}
      		}else{
      			System.out.println("Failed to click the select all");
      			return res;
      		}
      		}catch(Exception e) {
      			System.out.println("Error while selecting the select all radion button");
      			return res;
      		}
      		return res;
      	}
          
          public boolean clickApply(WebDriver passeddriver) throws InterruptedException {
      		boolean res = false;
      		WebDriver driver;
              driver = passeddriver;
              WebElement element;
      		try {
      			element = driver.findElement(By.xpath("//button[contains(text(),'Apply')]"));
                  if (element.isDisplayed()){
                  	Thread.sleep(1000);
                  	driver.findElement(By.xpath("//button[contains(text(),'Apply')]")).click();
                  	res = true;
      				return res;
      			}
      		} catch (Exception e) {
      			System.out.println("Manage gates apply button Exception!!");
      			return res;
      		}
      		return res;
      	}
          

      	public boolean verifyTopicPanelandClick(WebDriver passeddriver) throws InterruptedException {
      		boolean res = false;
      		try {
      			if (utilityFunction.waitForElementVisibility("SELECTED_GT_LIST", passeddriver) == true) {
      				if (utilityFunction.Validate("SELECTED_GT_LIST", passeddriver) == true) {
      					Thread.sleep(5000);   				
      					utilityFunction.driver.findElement(By.xpath("//*[contains(text(),'Topics')]")).click();
      				    res = true;
      					}
      				 else {
      					return res;
      				}
      			} else {
      				return res;
      			}
      		} /*
      			 * } if(utilityFunction.waitForElementVisibility("GATES_TAB")) {
      			 * if(utilityFunction.Validate("GATES_TAB") == true) { if
      			 * (utilityFunction.waitForElementVisibility("SELECTED_GT_LIST"))
      			 * Thread.sleep(2000); if(utilityFunction.Validate("SELECTED_GT_LIST") == true)
      			 * { if(utilityFunction.waitForElementVisibility("TOPICS_TAB"))
      			 * Thread.sleep(2000); if(utilityFunction.Validate("TOPICS_TAB") == true) {
      			 * utilityFunction.clickElement("TOPICS_TAB"); res = true; }else { return res; }
      			 * } } } }
      			 */ catch (Exception e) {
      			System.out.println("Verifying topic panel Exception!");
      			return res;
      		}
      		return res;
      	}
      	
      	
      	public boolean searchMessage(String message, WebDriver passeddriver) {
    		WebDriver driver = passeddriver;
    		WebElement ele;
    		WebElement ele1;
    		boolean res = false;
    		String searchResult = null;

    		try {
    			Thread.sleep(2000);

    			Actions act = new Actions(driver);
    			ele = driver.findElement(By.xpath("//app-image[contains(@class,'chat-header-icon-bar-search-icon')]"));
    			act.moveToElement(ele).click().build().perform();
    			Thread.sleep(2000);
    			ele1 = driver.findElement(By.xpath("//input[@placeholder='Search Chat Messages']"));
    			ele1.click();
    			ele1.sendKeys(message);
    			res = true;
    		} catch (Exception e) {
    			System.out.println("Failed to search the messages.");
    		}
    		return res;
    	}
          
          /*
           * Function Name: Change gate number
           * Author: Bharath Kummagoori
           * Date of Creation: 19th Jan 2018
           * Description: This function change the gate
           * Input Parameters: Gate number
           * Date Modified: If any
           * Reviewed By:
           */
           
           public boolean iChangeGatenumber(){
                  boolean res = true;
                  String invalidgate ;
                  String Emsg;
                  int intLoop=xlsReader.rowCount("PagepersistOverride");
                  System.out.println(intLoop);
                  try{
                               for (int j=1; j<intLoop; j++) {
                               Emsg = xlsReader.getCellData("PagepersistOverride","ErrorMsg",j+1);
                               invalidgate = xlsReader.getCellData("PagepersistOverride","ChangeInvalidGateNumber",j+1);
                               
                               if(utilityFunction.waitForElementVisibility("CHANGE_GATE")) 
                              {
                                   utilityFunction.clickElement("CHANGE_GATE");
                                   //utilityFunction.clickElement("LOGIN_GATE_NUMBER");
                                utilityFunction.enterValueInEditField("GATE_NUMBER", invalidgate);
                           //    utilityFunction.wait(5000);
                                utilityFunction.waitForElementVisibility("LOGIN_GATE_ERROR_MSG");
                                Thread.sleep(2000);
                                String str = driver.findElement(By.xpath("(//form/mat-form-field/div/div/div)[4]")).getAttribute("value");
                             System.out.println(str);
                                      if(str.contains(Emsg))
                                      {  System.out.println(str);
                                             return res;
                                      }else
                                      {
                                    	  System.out.println("not validating");
                                             return false;
                                      }              
                              }else
                              {
                                 System.out.println("failed to display change gate");
                                 return false;
                              }  
                         }
                  }
                  catch(Exception e) {
                         System.out.println("Exception!");
                  }
                  return res;
           }
         public boolean launchNewTab(String Url) {
        	 boolean res = false;
        	 if(Url!=null) {
        	 ((JavascriptExecutor) driver).executeScript("window.open()");
        	 ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs.get(1));
				driver.get(readConfigurationFile(Url));
				res = true;
        	 }
			return res;
        	 
         } 
		  
     }      

		
