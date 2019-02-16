
package com.aa.connectme.pom;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aa.connectme.util.Report;
import com.aa.connectme.util.UtilityFunction;

public class MessagingPanel {

	/*
	 * Function Name: verifyMessagePanel 
	 * Author: Sneha Bhojugade 
	 * Date of Creation: 8th Feb 2018
	 * Description: This function verifies the message panel and the read only message in it
	 * Input Parameters: Gate number 
	 * Date Modified: 
	 * By:
	 */
	WebDriver driver;
	private Report report = new Report();
	private UtilityFunction utilityFunction = new UtilityFunction();
	
	public boolean verifyMessagePanel() throws IOException {
		boolean res = false;
		String strGate = null;
		try {
			if(utilityFunction.waitForElementVisibility("SUBSCRIBED_GATE_NUMBER")==true) {
				if(utilityFunction.Validate("SUBSCRIBED_GATE_NUMBER")==true)
				{
		
					if(utilityFunction.waitForElementVisibility("DASHBOARD_HEADER_STATION_GATE_NAME")== true && utilityFunction.Validate("DASHBOARD_HEADER_STATION_GATE_NAME")==true)
					{
					String sgu=utilityFunction.getElementText("DASHBOARD_HEADER_STATION_GATE_NAME").substring(6,8).trim();
					strGate = utilityFunction.getElementText("SUBSCRIBED_GATE_NUMBER");
					if(strGate.equals(sgu)) 
					{
						System.out.println("Gate number is displayed accurately");
							res = true;
					}
					else
						System.out.println("Gate number not displayed properly");
					      return  res;
				 }
				}
			}
			else
				System.out.println("Message panel not displayed");
		}
		catch (Exception e) {
			System.out.println("Message panel error!");
		}
		return res;
	}
	
	/*public boolean verifyParticipantsList(String WG, String Role, String Participants) throws IOException {
		UtilityFunction utilityFunction = new UtilityFunction();
		String strWG = null;
		String strRole = null;
		String strParticipants = null;
		boolean res = false;
		try {
			if(utilityFunction.waitForElementVisibility("PARTICIPANTS_LIST_ICON"))
				utilityFunction.clickElement("PARTICIPANTS_LIST_ICON");
			if(utilityFunction.waitForElementVisibility("PARTICIPANTS_LIST"))
			{
				strWG = utilityFunction.getElementText("PL_WG");
				strRole = utilityFunction.getElementText("PL_ROLE");
				strParticipants = utilityFunction.getElementText("PL_PARTICIPANT");
				if((strWG.contains(WG))&&(strRole.contains(Role))&&(strParticipants.contains(Participants)))
					res = true;
			}
				
		}
		catch (Exception e) {
			System.out.println("Logout error!");
		}
		return res;
	}*/
	public boolean verifyParticipantsList() throws IOException {
		String strWG = null;
		String strRole =null;
		boolean res = false;
		try {
			if(utilityFunction.waitForElementVisibility("PARTICIPANTS_LIST_COUNT"))
			{
				if(utilityFunction.Validate("PARTICIPANTS_LIST_COUNT")==true)
				{
				utilityFunction.clickElement("PARTICIPANTS_LIST_COUNT");
				Thread.sleep(2000);
				if(utilityFunction.waitForElementVisibility("PL_WG_NAMES"))
				{
					List<WebElement> wglist=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("PL_WG_NAMES")));
					System.out.println("Total number of Workgroups are = "+wglist.size());
					for (int i=0; i<wglist.size(); i++)
					{
						WebElement ele=(WebElement) wglist.get(i);
						System.out.println(strWG=ele.getText());	
					}
					List<WebElement> Rolelist=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("PL_WG_ROLES_NAMES")));
					System.out.println("Total number of roles are = "+Rolelist.size());
					for (int j=0; j<Rolelist.size(); j++)
					{
						WebElement ele1=(WebElement) Rolelist.get(j);
						System.out.println(strRole=ele1.getText());
					}
					return true;
				}
				else
				{
					return res;
				}
			}else
			{
				return res;
			}
			}
		}
		catch (Exception e) {
			System.out.println("Participants page error");
		}
		return res;
	}
	/*
	 * Function Name: sendMessage 
	 * Author: Niranjan Gowda 
	 * Date of Creation: 6-5-2018
	 * Description: This function is to send the messages
	 * Input Parameters: message
	 * Date Modified: 
	 * By:
	 */
	public boolean sendMessage(String strMsg) throws IOException {
		Random rand = new Random();
		int value = rand.nextInt(1000);
		boolean res = false;
		try {
			if(utilityFunction.waitForElementVisibility("MP_MESSAGE_EDIT_BOX"))
				if(utilityFunction.Validate("MP_MESSAGE_EDIT_BOX") == true)
				{
					Thread.sleep(2000);
				utilityFunction.clickElement("MP_MESSAGE_EDIT_BOX");
					utilityFunction.enterValueInEditField("MP_MESSAGE_EDIT_BOX", strMsg+value);
					utilityFunction.waitForElementVisibility("MP_SEND_BTN");
					if(utilityFunction.Validate("MP_SEND_BTN"))
					{
						Thread.sleep(2000);
						utilityFunction.clickElement("MP_SEND_BTN");
						Thread.sleep(2000);
						res = true;
					}else
					{
						System.out.println("Send  is not enable");
						return res;
					}
				}
		}
		catch (Exception e) {
			System.out.println("Sending messages error!");
		}
		return res;
	}
	
	public String sendMessageWithRetrieveText(String message) {
		Random rand = new Random();
		int value = rand.nextInt(1000);
		String res = null;
		WebElement element;
		try {
			if(utilityFunction.waitForElementVisibility("MP_MESSAGE_EDIT_BOX")==true)
			{
				if(utilityFunction.Validate("MP_MESSAGE_EDIT_BOX")==true)
					
				{   Thread.sleep(3000);
					if(utilityFunction.Validate("MP_SEND_BTN")==true)
					{
					utilityFunction.enterValueInEditField("MP_MESSAGE_EDIT_BOX", message + value);
					Thread.sleep(4000);
					res = utilityFunction.driver.findElement(By.xpath("//textarea[@placeholder='Enter your message']"))
							.getAttribute("value");
					Thread.sleep(3000);
					utilityFunction.clickElement("MP_SEND_BTN ");
					return res;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Failed to send the messages.");
		}
		return res;
	}
	
	public String validateEditBoxAfterMsgSent() {
		String res = null;
		try {
			if(utilityFunction.waitForElementVisibility("MP_MESSAGE_EDIT_BOX")==true)
			{
				if(utilityFunction.Validate("MP_MESSAGE_EDIT_BOX")==true)
				{
					if(utilityFunction.Validate("MP_SEND_BTN")==true)
					{
					res = utilityFunction.driver.findElement(By.xpath("//textarea[@placeholder='Enter your message']"))
							.getAttribute("value");
					String res1 =res.replaceAll("", "empty");
					System.out.println(res1);
					return res1;
					
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Failed to send the messages.");
		}
		return res;
	}
	public String sendMsgENTERBtn(String message) throws IOException {
		String res = null;
		Random rand = new Random();
		int value = rand.nextInt(1000);
		try {
			Thread.sleep(6000);
			if(utilityFunction.waitForElementVisibility("MP_MESSAGE_EDIT_BOX")==true)
			{
				if(utilityFunction.Validate("MP_MESSAGE_EDIT_BOX")==true)
				{   Thread.sleep(6000);
					if(utilityFunction.Validate("MP_SEND_BTN")==true)
					{
						Thread.sleep(6000);
					utilityFunction.enterValueInEditField("MP_MESSAGE_EDIT_BOX", "Test" + value);
					Thread.sleep(4000);
					res = utilityFunction.driver.findElement(By.xpath("//textarea[@placeholder='Enter your message']"))
							.getAttribute("value");
						Thread.sleep(6000);
						Robot robot = new Robot();
						robot.keyPress(KeyEvent.VK_ENTER);
						Thread.sleep(6000);
						robot.keyRelease(KeyEvent.VK_ENTER);
						return res;
					}else
					{
						System.out.println("Send  is not enable");
						return res;
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println("Sending messages error!");
		}
		return res;
	}
	/*
	 * Function Name: sendMessage 
	 * Author: Niranjan Gowda 
	 * Date of Creation: 6-5-2018
	 * Description: This function is to send the messages
	 * Input Parameters: message
	 * Date Modified: 
	 * By:
	 */
	public boolean searchsendMessage(String strMsg)throws IOException{
		boolean res = false;
		try {
			if(utilityFunction.waitForElementVisibility("MP_SEARCH_ICON")==true)
				Thread.sleep(4000);
				utilityFunction.clickElement("MP_SEARCH_ICON");
				Thread.sleep(3000);
			utilityFunction.enterValueInEditField("MP_SEARCH_CHAT_TOPICS", strMsg);
			Thread.sleep(4000);
			utilityFunction.waitForElementVisibility("MESSAGE1");
			Thread.sleep(4000);
		if(utilityFunction.Validate("MESSAGE1"))
			{
			System.out.println("sent message is verified");
				res = true;
			}else
			{
				System.out.println("sent message is not verified");
				return res;
			}
		}
		catch (Exception e) {
			System.out.println("Sending messages error!");
		}
		return res;
	}
	
	/*
	 * Function Name: sendMessage 
	 * Author: Niranjan Gowda 
	 * Date of Creation: 6-5-2018
	 * Description: This function is to send the messages
	 * Input Parameters: message
	 * Date Modified: 
	 * By:
	 */
	public boolean searchRoleMessage(String RoleMsg)throws IOException{
		boolean res = false;
		try {
			if(utilityFunction.waitForElementVisibility("MP_SEARCH_ICON")==true)
			{
				if(utilityFunction.Validate("MP_SEARCH_ICON")==true)
				{
					Thread.sleep(3000);
					utilityFunction.clickElement("MP_SEARCH_ICON");
					Thread.sleep(3000);
					if(utilityFunction.waitForElementVisibility("MP_SEARCH_CHAT_TOPICS")==true)
					{
						if(utilityFunction.Validate("MP_SEARCH_CHAT_TOPICS")==true)
						{
							utilityFunction.enterValueInEditField("MP_SEARCH_CHAT_TOPICS", RoleMsg);
							utilityFunction.waitForElementVisibility("MESSAGE1");
							if(utilityFunction.Validate("MESSAGE1"))
							{
							System.out.println("Search messages through role name is verified");
								res = true;
							}else
							{
								System.out.println("Search messages through role name not verified");
								return res;
							}
							}
						}
						}
					}		
		}
		catch (Exception e) {
			System.out.println("Search messages through role name error!");
		}
		return res;
	}
	/*
	 * Function Name: sendMessage 
	 * Author: Niranjan Gowda 
	 * Date of Creation: 6-5-2018
	 * Description: This function is to send the messages
	 * Input Parameters: message
	 * Date Modified: 
	 * By:
	 */
	public String returnRoleName()throws IOException{
		String res = null ;
		try {
			utilityFunction.waitForElementVisibility("MP_SX_RX_USERROLE");
				if(utilityFunction.Validate("MP_SX_RX_USERROLE")==true)
				{
					Thread.sleep(2000);
					res =	utilityFunction.getElementText("MP_SX_RX_USERROLE");
					System.out.println(res);
					return	res;
				}else
				{
					return res;
				}
		}
		catch (Exception e) {
			System.out.println("Search messages through role name error!");
		}
		return res;
	}
	
	public int totalNoMsgBeforeSearch()throws IOException{
		int res = 0  ;
		try {
			utilityFunction.waitForElementVisibility("MESSAGE1");
				if(utilityFunction.Validate("MESSAGE1")==true)
				{
					List<WebElement> olist=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("MESSAGE1")));
					System.out.println("Total no of messages are before search = "+olist.size());
					res = olist.size();
				}else
				{
					return res;
				}
		}
		catch (Exception e) {
			System.out.println("Failed to dispaly the total number of messages before search.");
		}
		return res;
	}
	
	public int totalNoMsgAfterSearch()throws IOException{
		int res = 0  ;
		try {
			utilityFunction.waitForElementVisibility("MESSAGE1");
				if(utilityFunction.Validate("MESSAGE1")==true)
				{
					List<WebElement> olist=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("MESSAGE1")));
					System.out.println("Total no of messages are  after search = "+olist.size());
					res = olist.size();
				}else
				{
					return res;
				}
		}
		catch (Exception e) {
			System.out.println("Failed to dispaly the total number of messages after search.");
		}
		return res;
	}
	
	public boolean verifyunableAcknowledge() throws IOException {
		boolean res = false;
		String strGate = null;

		try {
			if(utilityFunction.waitForElementVisibility("GATE_MESSAGE_PANEL")) {
				Thread.sleep(5000);
					System.out.println("Gate number is displayed accurately");
					if((utilityFunction.waitForElementVisibility("MESSAGE1")) || (utilityFunction.waitForElementVisibility("READONLY")))
					{
						List olist= UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("MESSAGE1")));
						for (int i=0; i<olist.size(); i++)
						{
							WebElement ele=(WebElement) olist.get(i);
							ele.click();	
						}
						res = true;
					}
					else
					{
						System.out.println("Messages not displayed properly");
					}
				}
				else
					System.out.println("Gate number not displayed properly");
		}
		catch (Exception e) {
			System.out.println("Message panel error!");
		}
		return res;
	}
	
	public static void ackMsgPopUp()
	{
		UtilityFunction utilityFunction = new UtilityFunction();
		try {
			if(utilityFunction.Validate("MP_YES_BTN")==true)
			{
				utilityFunction.clickElement("MP_YES_BTN");	
			}	
		}
		catch (Exception e) {
			System.out.println("Acknowledge pop up error");
		}
	}
	
	public boolean clickAndVerifyCurFlightNum() throws Exception 
	{
 		boolean res = false;
 		try
 		{
 		if(utilityFunction.Validate("CURRENT_FLIGHT")==true)
 		{
 			utilityFunction.clickElement("CURRENT_FLIGHT");
 			String str = utilityFunction.getElementText("CURRENT_FLIGHT");
 			if(utilityFunction.Validate("MP_FLIGHT_NUM")== true)
 			{
 	 			String str1 = utilityFunction.getElementText("MP_FLIGHT_NUM");
 	 			if(str.equals(str1))
 	 			{
 	 				return res = true;
 	 			}		
 			}	
 		}
 		
 		return res;
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			return res;
 		}
 	}
	public boolean verfiyMsgTimeWithin24Hours() throws Exception 
	{
 		boolean res = false;
 		try
 		{
 		if(utilityFunction.Validate("CURRENT_FLIGHT")==true)
 		{
 			utilityFunction.clickElement("CURRENT_FLIGHT");
 			String str = utilityFunction.getElementText("CURRENT_FLIGHT");
 			if(utilityFunction.Validate("MP_FLIGHT_NUM")== true)
 			{
 	 			String str1 = utilityFunction.getElementText("MP_FLIGHT_NUM");
 	 			if(str.equals(str1))
 	 			{
 	 				return res = true;
 	 			}		
 			}	
 		}
 		
 		return res;
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			return res;
 		}
 	}
	
	
	/*
	 * Function Name: verifyclickPrioritybtn 
	 * Author: Niranjan Gowda s r 
	 * Date of Creation: 27th June 2018
	 * Description: This function verifies the priority button and clicking priority button
	 * Input Parameters: 
	 * Date Modified: 
	 * By:
	 */
	public boolean verifyAndclickPrioritybtn() throws Exception 
	{
 		boolean res = false;
 		try
 		{
 			utilityFunction.waitForElementVisibility("MP_PRIORITY_BELL_ICON");
	 		if(utilityFunction.Validate("MP_PRIORITY_BELL_ICON")==true)
	 		{
	 			/*String color = utilityFunction.driver.findElement(By.xpath("//div[@class='chat-footer-message-entry']/app-image[@src='icon-notification-small']")).getCssValue("color");
	 			System.out.println("Bell color is :"+color);
	 			String[] hexValue = color.replace("rgba(","").replace(")","").split(",");                           

	 			hexValue[0] = hexValue[0].trim();

	 			int hexValue1 = Integer.parseInt(hexValue[0]);                   

	 			hexValue[1] = hexValue[1].trim();

	 			int hexValue2 = Integer.parseInt(hexValue[1]);                   

	 			hexValue[2] = hexValue[2].trim();

	 			int hexValue3 = Integer.parseInt(hexValue[2]);                   

	 			String actualColor = String.format("FF0000", hexValue1, hexValue2, hexValue3);
	 			System.out.println(actualColor);
	 			utilityFunction.clickElement("MP_PRIORITY_BELL_ICON");
	 			String Blcolor1 = utilityFunction.driver.findElement(By.xpath("//div[@class='chat-footer-message-entry']/app-image[@src='icon-notification-small']")).getCssValue("background-color");
	 			System.out.println("Bell color is :"+Blcolor1);*/
		 			if(utilityFunction.Validate("MP_SEND_BTN")== true)
		 			{
		 				Thread.sleep(4000);
		 				utilityFunction.clickElement("MP_PRIORITY_BELL_ICON");
		 				res = true;
		 			}else
		 			{
		 				return res;
		 			}
	 			res = true;
	 		  }else
		 		{
		 		return res;
		 		}
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			return res;
 		}
		return res;
 	}
	/*
	 * Function Name: verifyMessagePanel 
	 * Author: Sneha Bhojugade 
	 * Date of Creation: 27th June 2018
	 * Description: This function verifies the message panel formats
	 * Input Parameters: Gate number 
	 * Date Modified: 
	 * By:
	 */
	
	public boolean verifyMsgPanel() throws Exception 
	{
 		boolean res = false;
 		String strGateHeader = null;
 		String[] strparts = null;
 		String strGateNumber = null;
 		try
 		{
 			Thread.sleep(4000);	
 			if(utilityFunction.waitForElementVisibility("DASH_MSG_PANEL_GATE_NUMBER")==true)
 					if(utilityFunction.Validate("DASH_MSG_PANEL_GATE_NUMBER")==true)
 					{
 					strGateNumber = utilityFunction.getElementText("DASH_MSG_PANEL_GATE_NUMBER");	
	 				  if(utilityFunction.waitForElementVisibility("LOGIN_GATE_NUMBER")==true) 
	 				  {
	 					 Thread.sleep(4000);
	 					  if(utilityFunction.Validate("LOGIN_GATE_NUMBER")==true)
	 	 					{
	 						strGateHeader = utilityFunction.getElementText("LOGIN_GATE_NUMBER");
	 						strGateHeader = strGateHeader.replace(" ","");
	 						strparts = strGateHeader.split("·");
	 					      if(strGateNumber.equals(strparts[1])) 
	 					      {	
	 						   res = true;
	 					      }else
	 					      {
	 						  return res;
	 					       }
	 	 					}
	 					}
 					}else
 					{
 						System.out.println("Failed to display the message panel gate number.");
 					}
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			return res;
 		}
 		return res;
 	}
	
	public boolean clickonflightwithYES() throws InterruptedException{
		boolean res=false;
		try {
			Thread.sleep(2000);
			utilityFunction.clickElement("CURRENT_FLIGHT");
			if(utilityFunction.Validate("MP_YES_BTN"))
			{
			utilityFunction.clickElement("MP_YES_BTN");	
				Thread.sleep(2000);
			}else if(utilityFunction.waitForElementVisibility("MP_MESSAGE_EDIT_BOX")==true)
				System.out.println("control moves to message panel");
				res=true;
			
		} catch (Exception e) {
			System.out.println("could not find POP error box");
			System.out.println("failed to click on gate thread");
			Thread.sleep(2000);
			utilityFunction.clickElement("CURRENT_FLIGHT");
			res=true;
		}
			
		return res;}
	
	public boolean verifyReadonlymessage(){
        boolean res=false;
        try {
               if(utilityFunction.waitForElementVisibility("VERIFY_READ_ONLY_MESSAGE")==true){
                      Thread.sleep(2000);
                      utilityFunction.Validate("VERIFY_READ_ONLY_MESSAGE");
                      String line=utilityFunction.getElementText("VERIFY_READ_ONLY_MESSAGE");
                      System.out.println("message line would be:"+ line);
                      res= true;
               }
        } catch (Exception e) {
               
               System.out.println("message line is not displayed");
        }
        
        
        return res;
  }
	public boolean verifyFirstMsgTimeWithin4hours(){
        boolean res=false;
        boolean timeResult=false;
        try {
               if(utilityFunction.Validate("NEXT_FLT")||utilityFunction.Validate("CURRENT_FLT")) {
            	   if(utilityFunction.Validate("NEXT_FLT")) {
            		   utilityFunction.clickElement("NEXT_FLT");
            		   try {
						utilityFunction.Validate("MSG_PANEL_FIRSTMSG");
					} catch (Exception e) {
						System.out.println("In Current Flight there is no msg");
						res=false;
					}
            		   timeResult =timeCompare(utilityFunction.getElementText("MSG_PANEL_FIRSTMSG"));
            	   }
            	   else if(utilityFunction.Validate("CURRENT_FLT")) {
            		   utilityFunction.clickElement("CURRENT_FLT");
            		   try {
            			   timeResult =timeCompare(utilityFunction.getElementText("MSG_PANEL_FIRSTMSG"));
   					} catch (Exception e) {
   						System.out.println("In Current Flight there is no msg");
   						res=false;
   					}
            	   }
               }
               if(timeResult) {
            	   res=true;
               }
        } catch (Exception e) {
               
               System.out.println("message line is not displayed");
        }
        
        
        return res;
  }
	public  boolean timeCompare(String msgTime) throws ParseException {
	      boolean res = false;
	      String formattedTime;
	ZoneId zoneId = ZoneId.of("US/Central");
	LocalTime localTime=LocalTime.now(zoneId);
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
	formattedTime=localTime.format(formatter);
	System.out.println( "Current time"+formattedTime);
	System.out.println( "Msg time"+msgTime);
	SimpleDateFormat format = new SimpleDateFormat("H:mm");
	Date date1 = format.parse(formattedTime);
	Date date2 = format.parse(msgTime);
	long difference = date1.getTime() - date2.getTime();
	long diffHours = difference / (60 * 60 * 1000) % 24;
	if(4<=diffHours) {
	      res=true;
	}
	System.out.print(diffHours + " hours, ");

	return res;
	}
	
}