package com.aa.connectme.pom;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aa.connectme.util.UtilityFunction;

public class Cchrequeriments extends UtilityFunction {
	private UtilityFunction utilityFunction = new UtilityFunction();
	
	public boolean selectWorkGroup() throws InterruptedException {	
        WebElement element;	
		boolean res = false;
		
		if(utilityFunction.Validate("LOGIN_ROLE_CHOOSE_WORKGROUP")==true)
		{
		utilityFunction.clickElement("LOGIN_SELECTED_WORKGROUP");
		Thread.sleep(2000);
			if(utilityFunction.Validate("LOGIN_WORKGROUP_LIST")==true)
			{
				List<WebElement> wglist=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("LOGIN_WORKGROUP_LIST")));
				System.out.println("Total number of Workgroups are = "+wglist.size());
				Thread.sleep(2000);
				if(wglist.size()>0)
				{
				WebElement WorkGroupSelection = (WebElement) wglist.get(0);
				String SelectedWorkGroup = WorkGroupSelection.getText();
				WorkGroupSelection.click();
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
		}
		return res;
	}
	public boolean selectRole() throws InterruptedException 
	{	
		boolean res = false;
		
		if(utilityFunction.Validate("LOGIN_ROLE_CHOOSE_ROLE")==true)
		{
		utilityFunction.clickElement("LOGIN_SELECTED_ROLE");
		Thread.sleep(2000);
			if(utilityFunction.Validate("LOGIN_ROLE_LIST")==true)
			{
			List<WebElement> olist=UtilityFunction.driver.findElements(By.xpath(utilityFunction.readObjectRepositoryFile("LOGIN_ROLE_LIST")));
			System.out.println("Total number of roles is = "+olist.size());
			Thread.sleep(2000);
				if(olist.size()>0)
				{
				WebElement RoleSelection = (WebElement) olist.get(0);
				String SelectedRole = RoleSelection.getText();
				RoleSelection.click();
				System.out.println("Roles Selected is " + SelectedRole);
				res = true;
				}else
				{
					return res;
				}
			}
		} else {
			System.out.println("Role is not selected");
			return res;
		}
		return res;
	}
	
	public boolean clickContinue() throws InterruptedException 
	{
		boolean res = false;
		Thread.sleep(2000);
		if(utilityFunction.Validate("WG_RL_CONTINUE")==true)
		{
		utilityFunction.clickElement("WG_RL_CONTINUE");
		Thread.sleep(2000);
		res = true;
		}
		return res;
	}
	
	public boolean clickSecondSelectedGate() throws InterruptedException {
		boolean res=false;
		try
		{
			Thread.sleep(2000);
			utilityFunction.waitForElementVisibility("SELECTED_SECOND_GATE");
			utilityFunction.clickElement("SELECTED_SECOND_GATE");
			res = true;
		} catch(Exception e) {
			System.out.println("selected gate click error!");
			return res;
		}
		return res;
	}
	public boolean clickApply() throws InterruptedException {
		boolean res = false;
        WebElement element;
		try {	
			if(utilityFunction.Validate("MG_APPLY"))
			{
				Thread.sleep(1000);
				utilityFunction.clickElement("MG_APPLY");
				res = true;
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println("Manage gates apply button Exception!");
			return res;
		}
		return res;
	}
	public boolean verifySidePanel() {
		boolean res = false;
		try {
			Thread.sleep(5000);
			if (utilityFunction.Validate("INBOX_ICON"))
				if (utilityFunction.Validate("ARCHIVE_ICON"))
					Thread.sleep(2000);
					res = true;
		} catch (Exception e) {
			System.out.println("Side panel Exception!");
		}
		return res;
	}
	
	public boolean searchMessage(String message) {

		WebElement ele;
		WebElement ele1;
		boolean res = false;
		String searchResult = null;

		try {
			Thread.sleep(2000);
			utilityFunction.waitForElementVisibility("MSG_PANEL_SEARCH_ICON");
			utilityFunction.clickElement("MSG_PANEL_SEARCH_ICON");
			Thread.sleep(3000);

			// utilityFunction.waitForElementVisibility("MSG_PANEL_SEARCH_FIELD",passeddriver);
			utilityFunction.clickElement("MSG_PANEL_SEARCH_FIELD");
			Thread.sleep(3000);
			utilityFunction.enterValueInEditField("MSG_PANEL_SEARCH_FIELD", message);
			utilityFunction.waitForElementVisibility("MSG_PANEL_SEARCH_RESULT");
			Thread.sleep(3000);

			searchResult = utilityFunction.getElementText("MSG_PANEL_SEARCH_RESULT");
			if (searchResult.contains(message))
				res = true;
		} catch (Exception e) {
			System.out.println("Failed to search the messages.");
		}
		return res;
	}
	
	public String sendMessageWithRetrieveText(String message, WebDriver passeddriver) {
		Random rand = new Random();
		int value = rand.nextInt(1000);
		String res = null;
		WebDriver driver;
		driver = passeddriver;
		WebElement element;
		try {
			element = driver.findElement(By.xpath("//div[@class='chat-header']/div[1]"));
			if (element.isDisplayed()) {
				Thread.sleep(4000);
				driver.findElement(By.xpath("//textarea[@placeholder='Enter your message']")).sendKeys(message + value);
				Thread.sleep(3000);
				res = driver.findElement(By.xpath("//textarea[@placeholder='Enter your message']"))
						.getAttribute("value");
				driver.findElement(By.xpath("//button[contains(text(),'Send')]")).click();
				Thread.sleep(3000);
				return res;
			}
		} catch (Exception e) {
			System.out.println("Failed to send the messages.");
		}
		return res;
	}
	public boolean logout() throws IOException {
		WebElement element;
		boolean res = false;
		try {
			utilityFunction.waitForElementVisibility("DB_LOGOUT_TEXT");
			if (utilityFunction.Validate("DB_LOGOUT_TEXT") == true) {
				Thread.sleep(2000);
				utilityFunction.clickElement("DB_LOGOUT_TEXT");
				Thread.sleep(10000);
				utilityFunction.waitForElementVisibility("LOGIN_UN_INPUT");
				if (utilityFunction.Validate("LOGIN_UN_INPUT")) {
					res = true;
				} else {
					return res;
				}
			} else {
				System.out.println("Logout failed");
				return res;
			}
		} catch (Exception e) {
			System.out.println("Logout error!");
		}
		return res;
	}
	
	public boolean Cchlogin(String Username, String Password, String Station,WebDriver passeddriver) throws IOException {
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
	
	
	
	
	
	
	
	
	
	
	
}
