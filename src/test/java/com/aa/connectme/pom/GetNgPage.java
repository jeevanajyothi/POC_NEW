package com.aa.connectme.pom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.aa.connectme.util.Report;
import com.aa.connectme.util.UtilityFunction;

public class GetNgPage extends UtilityFunction {
	WebDriver driver;
	private static UtilityFunction utilityFunction = new UtilityFunction();
	private Report report = new Report();
	private DashboardPage dashboardPage = new DashboardPage();
	public static ArrayList<String> allFlightsNum = new ArrayList<>();
	public static ArrayList<String> allFlightsTimes = new ArrayList<>();
	public static ArrayList<String> allFlightsStatus = new ArrayList<>();
	public static ArrayList<String> allFlightsEtdCodes = new ArrayList<>();
	public static ArrayList<String> allFlightsEtdMsg = new ArrayList<>();
	public static ArrayList<String> allFlightsDelayStamp = new ArrayList<>();
	public static ArrayList<String> stations = new ArrayList<String>();
	public static ArrayList<String> DepFltStatus = new ArrayList<String>();
	public static ArrayList<String> DepFltNums = new ArrayList<String>();
	public static ArrayList<String> DepFltGates = new ArrayList<String>();
	/*public static ArrayList<String> gate = new ArrayList<>();
	public static ArrayList<String> flight = new ArrayList<String>();
	public static ArrayList<String> status = new ArrayList<String>();*/
	
	
	public static int inoncount;


	public boolean getNglogin(String Username, String Password) throws IOException {
		boolean res = false;
		try {
			if (utilityFunction.Validate("GETNG_AA_ID") == true) {
				utilityFunction.enterValueInEditField("GETNG_AA_ID", Username);
				if (utilityFunction.Validate("GETNG_PWD") == true) {
					utilityFunction.enterValueInEditField("GETNG_PWD", Password);
					if (utilityFunction.Validate("GETNG_LOGIN_BTN") == true) {
						utilityFunction.clickElement("GETNG_LOGIN_BTN");
						Thread.sleep(30000);
						utilityFunction.waitForElementVisibility("GETNG_ZONE_LIST");
						if (utilityFunction.Validate("GETNG_ZONE_LIST") == true) {
							res = true;
						}
					}
				}
			} else {
				System.out.println("Failed to login");
				return res;
			}
		} catch (Exception e) {
			System.out.println("Login Failed");
		}
		return res;
	}

	public boolean clickDelayedTab() throws InterruptedException {
		boolean res = false;
		if (utilityFunction.waitForElementVisibility("GETNG_DELAYED")) {
			if (utilityFunction.Validate("GETNG_DELAYED") == true) {
				utilityFunction.clickElement("GETNG_DELAYED");
				res = true;
			}
		}
		return res;
	}

	
	public static void gateChange(int count) throws Exception {
		String TimeRange = "4";
		Thread.sleep(10000);
		if(utilityFunction.waitForElementVisibility("GETNG_SETTING_ICON")==true) {
			utilityFunction.driver.findElement(By.xpath(utilityFunction.readObjectRepositoryFile("GETNG_SETTING_ICON"))).click();
			Thread.sleep(10000);
			WebElement filter = utilityFunction.driver.findElement(By.xpath(utilityFunction.readObjectRepositoryFile("GETNG_STATION_FILTER")));
			Thread.sleep(7000);
			filter.sendKeys(stations.get(count));
			Thread.sleep(5000);
			utilityFunction.driver.findElement(By.xpath(utilityFunction.readObjectRepositoryFile("GETNG_STATION_NAME"))).click();
			Thread.sleep(10000);
			Set_TimeRange(TimeRange);
			Thread.sleep(2000);
			Click_ApplyButton();
			Thread.sleep(30000);
		}
		
	}
	
	public static void Set_TimeRange(String strTimeRange) throws InterruptedException 
    {
           int valTimeRange = Integer.parseInt(strTimeRange);
           WebElement slider = utilityFunction.driver.findElement(By.xpath(utilityFunction.readObjectRepositoryFile("GETNG_TIME_RANGE")));
           int width=slider.getSize().getWidth();
           Actions move = new Actions(UtilityFunction.driver);
           move.moveToElement(slider, ((width*valTimeRange)/13), 4).click();
           move.build().perform();

           String xpathTimeRange = ".//*[@id='db-filter-panel']/div[4]/div[4]/div[1]/span";
           String textTimeRange = utilityFunction.driver.findElement(By.xpath(xpathTimeRange)).getText();
           if(textTimeRange.contains(strTimeRange))
           {
                  System.out.println("Time Range "+strTimeRange+" Slider moved Correctly : "+textTimeRange);
           }
           else
           {
                  System.out.println("Time Range "+strTimeRange+" Slider Not moved Correctly : "+textTimeRange);
           }
    }
	public static void Click_ApplyButton() throws InterruptedException {
        Thread.sleep(3000);
        boolean applybutton  =utilityFunction.driver.findElement(By.xpath("//span[contains(text(),'Apply')]")).isDisplayed();
        if(applybutton==true)
        {
        	utilityFunction.driver.findElement(By.xpath("//span[contains(text(),'Apply')]")).click();
               System.out.println("Apply button Displayed and Clicked");
        }
        else
        {
               System.out.println("Apply button is Not displayed");
        }
        /*//          Wait Until Total Gates Display at Dashboard Section
        WebDriverWait wait1 = new WebDriverWait(driver, 120);
        wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='total-gates-div']/div")));*/
 }   

	public boolean clickGte() throws InterruptedException {
		boolean res = false;
		if (utilityFunction.waitForElementVisibility("GETNG_DEPT_GTE")) {
			if (utilityFunction.Validate("GETNG_DEPT_GTE") == true) {
				utilityFunction.clickElement("GETNG_DEPT_GTE");
				utilityFunction.addDelay(2000);
				utilityFunction.clickElement("GETNG_DEPT_GTE");
				res = true;
			}
		}
		return res;
	}

	public boolean getDeptFltNumAndFltTimeFromGetNg() throws InterruptedException {
		boolean res = false;
		if (utilityFunction.waitForElementVisibility("GETNG_DEPT_FLT_NUM")) {
			if (utilityFunction.Validate("GETNG_DEPT_FLT_NUM") == true) {
				List<WebElement> Flightrows = utilityFunction.driver
						.findElements(By.xpath("//div[contains(@class,' flt-dep-num')]/span"));

				System.out.println(Flightrows.size());

				for (WebElement Flights : Flightrows) {
					Thread.sleep(2000);
					allFlightsNum.add(Flights.getText());
				}
				List<WebElement> Timerows = utilityFunction.driver
						.findElements(By.xpath("//div[contains(@class,'flt-tme ng-binding dep-sub-no-skd')]"));

				System.out.println(Timerows.size());

				try {
					for (WebElement Times : Timerows) {
						Thread.sleep(2000);
						String TimeText =Times.getText();
						String[] sta = TimeText.split(" ");
						allFlightsTimes.add(sta[0]);
					}
				} catch (Exception e) {
					System.out.println("unable to fetch gatenumber error!");
				}
				res = true;
			}
		}
		return res;
	}
	public boolean getDeptStatusFromGetNg() throws InterruptedException {
		boolean res = false;
		if (utilityFunction.waitForElementVisibility("GETNG_DEPT_FLT_STATUS")) {
			if (utilityFunction.Validate("GETNG_DEPT_FLT_STATUS") == true) {
				List<WebElement> Statusrows = utilityFunction.driver
						.findElements(By.xpath("//div[contains(@class,'col-lg-1 etd-rsn no-padding text-underline dep-etd-noskd')]/parent::div"));

				System.out.println(Statusrows.size());

				for (WebElement status : Statusrows) {
					String flightText =status.getText();
					String[] sta = flightText.split("\n");
					allFlightsStatus.add(sta[4]);
					
				}
				System.out.println(allFlightsStatus);
				res = true;
			}
		}
		return res;
	}
	public boolean clickDeptETDmsg() throws InterruptedException {
		boolean res = false;
		if (utilityFunction.waitForElementVisibility("GETNG_DEPT_FLT_ETD")) {
			if (utilityFunction.Validate("GETNG_DEPT_FLT_ETD") == true) {
				List<WebElement> ETDrows = utilityFunction.driver
						.findElements(By.xpath("//div[contains(@class,'col-lg-1 etd-rsn no-padding text-underline dep-etd-noskd')]//SPAN[@class='text-underline ng-binding ng-scope']"));

				System.out.println(ETDrows.size());

				for (WebElement etdCode : ETDrows) {
					Thread.sleep(2000);
					allFlightsEtdCodes.add(etdCode.getText());
					System.out.println(etdCode.getText());
					etdCode.click();
					List<WebElement> ETDmsg = utilityFunction.driver
							.findElements(By.xpath("//div[@class='col-lg-12  col-xs-12 rows-etd-popover']//div[@class='col-lg-6 col-xs-6 swap-etd']"));
					for (WebElement etdMsg : ETDmsg) {
						allFlightsEtdMsg.add(etdMsg.getText());
						System.out.println(etdMsg.getText());
						Thread.sleep(2000);
						break;
					}
					List<WebElement> DelayTime = utilityFunction.driver
							.findElements(By.xpath("//div[@class='col-lg-12  col-xs-12 rows-etd-popover']//div[@class='col-lg-2 col-xs-2 swap-etd']"));
					for (WebElement delaystamp : DelayTime) {
						allFlightsDelayStamp.add(delaystamp.getText());
						System.out.println(delaystamp.getText());
						Thread.sleep(2000);
						break;
					}
					etdCode.click();
					Thread.sleep(3000);
					res = true;
				}
			}
			
		}
		return res;
	}

	public boolean getNgSignOut() throws InterruptedException {
		boolean res = false;
		if (utilityFunction.waitForElementVisibility("GETNG_DD_SIGNOUT")) {
			if (utilityFunction.Validate("GETNG_DD_SIGNOUT") == true) {
				utilityFunction.clickElement("GETNG_DD_SIGNOUT");
				if (utilityFunction.waitForElementVisibility("GETNG_SIGNOUT")) {
					if (utilityFunction.Validate("GETNG_SIGNOUT") == true) {
						utilityFunction.clickElement("GETNG_SIGNOUT");
						utilityFunction.addDelay(2000);
						utilityFunction.closeBrowsers();
						res = true;
					}
				}
			}
		}
		return res;
	}
	public boolean getindexofStatus() throws InterruptedException {
		boolean res = false;
		for(int i=0; i<=allFlightsNum.size(); i++) {
			if(GetNgPage.allFlightsStatus.get(i).contains("IN") || GetNgPage.allFlightsStatus.get(i).contains("ON")){
			System.out.println(allFlightsStatus.get(i));	
			inoncount=i;
			res=true;
			break;
			}else {
				System.out.println(allFlightsStatus.get(i));	
				return res;
			}
		}	
		return res;
	}
	public boolean getindexofOtherthanINON() throws InterruptedException {
		boolean res = false;
		for(int i=0; i<=allFlightsNum.size(); i++) {
			if(GetNgPage.allFlightsStatus.get(i).contains("IN") || GetNgPage.allFlightsStatus.get(i).contains("ON")){
			System.out.println(allFlightsStatus.get(i));	
			
			}else {
				System.out.println(allFlightsStatus.get(i));	
				inoncount=i;
				res=true;
				break;
			}

		}	
		return res;
	}
	
	public boolean changeSchToDep() throws InterruptedException {
		boolean res = false;
		
        WebElement slider = utilityFunction.driver.findElement(By.xpath(".//*[@id='wrapper']/div[2]/aside/section/div[1]/div[1]/div/span[2]"));
        int valSchDep = 0;
        int widthSliderBar = slider.getSize().width; //  Total width of the slider = 35
        System.out.println("Width of the Slider :"+widthSliderBar);
        
        Actions sliderActions = new Actions(utilityFunction.driver);
        sliderActions.clickAndHold(slider);   
        sliderActions.moveByOffset(0, (int) valSchDep).release().build().perform();    // from 0 to 35
        sliderActions.release();  
       if( utilityFunction.waitForElementVisibility("GETNG_DEP")==true)
       {
       res = true;
       }else
       {
    	   return res;
       }
       return res;
	}
	/*public static ArrayList<String> getDepOUTOFFFLTStatusDetails() throws InterruptedException {
		ArrayList<String> overallList = new ArrayList<>();
		ArrayList<String> DepFltResults = new ArrayList<String>();
		ArrayList<String> Results = new ArrayList<String>();
		//Fetching the departed flight status like OFF or OUT 
		if (utilityFunction.waitForElementVisibility("GETNG_DEP_STATUS")) {
			if (utilityFunction.Validate("GETNG_DEP_STATUS") == true) {
				
				List<WebElement> DepStatusindicatiosn = utilityFunction.driver
						.findElements(By.xpath("//div[contains(@class,'col-sm-2 col-lg-2 col-dep-flt  col-dep-fullvalue')]/div[5]"));
			

				System.out.println("Total OFF/OUT status indication count :"+DepStatusindicatiosn.size());
				for (WebElement depindication : DepStatusindicatiosn) {
					Thread.sleep(2000);
					String XX =depindication.getText();
					DepFltStatus.add(XX);
					overallList.addAll(DepFltStatus);
					//break;
				}
				
					//Fetching the departed flight numbers
					List<WebElement> DepFlightsNumbers = utilityFunction.driver
							.findElements(By.xpath("//div[contains(@class,' flt-dep-num')]/span"));
					
					System.out.println("Total OFF/OUT status indication count :"+DepFlightsNumbers.size());
					for (WebElement Depfltdetails : DepFlightsNumbers) {
						DepFltNums.add(Depfltdetails.getText());
						overallList.addAll(DepFltNums);
						Thread.sleep(1000);
						//break;
					}
					//Fetching the departed flight gates
					List<WebElement> DepGateNumbers = utilityFunction.driver
							.findElements(By.xpath("//div[@class='col-sm-2 col-lg-2 col-dep-flt  col-dep-fullvalue']/div[@class='col-lg-2 no-padding dep-gte']/span[1]"));
					System.out.println("Total OFF/OUT status indication count :"+DepGateNumbers.size());
					for (WebElement depgates : DepGateNumbers) {
						DepFltGates.add(depgates.getText());
						overallList.addAll(DepFltGates);
						Thread.sleep(1000);
						//break;
					}			
		}
			System.out.println("329---->"+overallList);
			System.out.println("329++++++++---->");
			
	}
		return overallList;
	}*/
	
	public void getDepOUTOFFFLTStatusDetails() throws InterruptedException {
		
		//Fetching the departed flight status like OFF or OUT 
		//
		if (utilityFunction.waitForElementVisibility("GETNG_DEP_STATUS")) {
			if (utilityFunction.Validate("GETNG_DEP_STATUS") == true) {
 
				List<WebElement> DepStatusindicatiosn = utilityFunction.driver
						.findElements(By.xpath("//div[contains(@class,'col-dep-fullvalue')]"));

				System.out.println("Total OFF/OUT status indication count :" + DepStatusindicatiosn.size());
				for (WebElement depindication : DepStatusindicatiosn) {
					Thread.sleep(2000);
					String XX = depindication.getText();
					String values[] = XX.split("\n");
					if(DepFltGates.size() <= 4) {
					if (values.length == 6) {
						DepFltGates.add(values[0]);
						DepFltNums.add(values[1]);
						DepFltStatus.add(values[4]);

					} else if (values.length == 5) {
						DepFltGates.add(values[0]);
						DepFltNums.add(values[1]);
						DepFltStatus.add(values[4]);

					} else if (values.length == 7) {
						DepFltGates.add(values[0]);
						DepFltNums.add(values[1]);
						DepFltStatus.add(values[4]);
					}
					 break;
				}else {
					break;
				}
				}
			}

		}
		System.out.println("329---->" + DepFltGates + "flight" + DepFltNums + "status" + DepFltStatus);
	}
}
