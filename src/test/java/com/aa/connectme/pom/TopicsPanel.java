package com.aa.connectme.pom;

import com.aa.connectme.util.Report;
import com.aa.connectme.util.UtilityFunction;
import com.aa.connectme.util.Xls_Reader;

public class TopicsPanel extends UtilityFunction {
	
	private UtilityFunction utilityFunction = new UtilityFunction();
	private Report report = new Report();
	private Xls_Reader xlsReader = new Xls_Reader();
	/*
	 * Function Name: verifyTopicsTab Author: Date of Creation:
	 * 28th January 2019 
	 * Description: Function to verify the topics tab 
	 * Parameters: no parameters Date Modified: By:
	 */
	public boolean verifyTopicPanelandClick() throws Exception 
	{
		boolean res = false;
		String topics = null;
 		try
 		{
 			Thread.sleep(4000);
 			if (utilityFunction.waitForElementVisibility("TOPICS_TAB"))
				topics = utilityFunction.getElementText("TOPICS_TAB");
			if (utilityFunction.Validate("TOPICS_TAB") == true) {
				if (topics.contains("Topics")) {
					utilityFunction.clickElement("TOPICS_TAB");
					res = true;
				}
					
			}
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			return res;
 		}
 		return res;
 	}
	
	
	
	
	public boolean SelectingGAteAgentRole() throws Exception 
	{
		boolean res = false;
		String topics = null;
 		try
 		{ 			
 			Thread.sleep(4000);
 			if (utilityFunction.waitForElementVisibility("GATE_AGENT"))
				topics = utilityFunction.getElementText("GATE_AGENT");
			if (utilityFunction.Validate("GATE_AGENT") == true) {
				if (topics.contains("Gate Agent")) {
					utilityFunction.clickElement("GATE_AGENT");
					try {
					if(utilityFunction.Validate("MP_YES_BTN")==true)
 					{
 						utilityFunction.clickElement("MP_YES_BTN");	
 					}
					}catch(Exception e) {
						System.out.println("Pop up is not dispalyed");
					}
					res = true;
				}
					
			}
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			return res;
 		}
 		return res;
 	}

}
