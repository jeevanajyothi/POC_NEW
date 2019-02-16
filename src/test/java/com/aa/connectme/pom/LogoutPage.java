package com.aa.connectme.pom;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.aa.connectme.util.Report;
import com.aa.connectme.util.UtilityFunction;
import com.aa.connectme.util.Xls_Reader;

public class LogoutPage extends UtilityFunction {
	
	private UtilityFunction utilityFunction = new UtilityFunction();
	private Report report = new Report();
	private Xls_Reader xlsReader = new Xls_Reader();
	/*
	* Function Name: logout
	* Author: Sneha S Bhojugade
	* Description: Function to logout of the application
	* Date Modified: If any
	* Reviewed By:
	*/
	public boolean logout() throws IOException {
		boolean res = false;
		
		try {	
			utilityFunction.waitForElementVisibility("DASHBOARD_HEADER_MORE_ICON");
		   if(utilityFunction.Validate("DASHBOARD_HEADER_MORE_ICON") == true)
		   { 
			    Thread.sleep(1000);
				utilityFunction.clickElement("DASHBOARD_HEADER_MORE_ICON");
				utilityFunction.waitForElementVisibility("LOGOUT");
				 if(utilityFunction.Validate("LOGOUT") == true)
				   { 
					 Thread.sleep(5000);
				    utilityFunction.clickElement("LOGOUT");
				     if(utilityFunction.waitForElementVisibility("LOGIN"))
				    	 if(utilityFunction.Validate("LOGIN") == true)
						   { 
				    		 System.out.println("Log in button is displlayed after succesful logout");
					          res = true;
						   }else
						   {
							   System.out.println("Failed to display Log in button after logout");
							   return res;
						   }
				   }
			} 
		 }
		catch(Exception e){
			System.out.println("Logout Failed");
		}
		return res;
	}	
}