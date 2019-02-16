package com.aa.connectme.util;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.aa.connectme.listeners.TestCaseListener;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class UtilityFunction {

	//Declare variables
	static HSSFWorkbook excel_Workbook;
	static HSSFSheet excel_sheet;
	static HSSFRow row;
	static HSSFCell cell;
	public static WebDriver driver;
	private int intObjectSyncTimeOut = 90;
	private int intBrowserSyncTimeOut = 180;

	private static Properties properties;
	private Select select;
	private Actions action;
	private Alert alert;
	private DocumentBuilderFactory builderFactory;
	private DocumentBuilder builder;
	private org.w3c.dom.Document xmlDocument;
	private NodeList nodeList;
	private XPath xPath;
	private String data;
	private int ActiverecordCount = 0;
	private String Wrapper_Path;
	private static ExtentReports extent;
    private static ExtentTest test;
    private static ExtentHtmlReporter htmlReporter;
    static String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    private static String filePath = "Execution_Report"+ File.separator+"ExtentReport"+".html";
    public static String strTestCaseName;
    public static String ModuleName;
    public static String DateAndTime;
    public static ExtentTest parentTest;
	//Open Excel file from a given path
	public static HSSFSheet getExcelFile(String path, String sheetname) throws Exception{
		try{
			FileInputStream input_file= new FileInputStream(path);
			excel_Workbook=new HSSFWorkbook(input_file);
			excel_sheet=excel_Workbook.getSheet(sheetname);
			input_file.close();
			return excel_sheet;
		}
		catch(Exception e){
			System.out.println(e);
			return excel_sheet;
		}
	}


	//get number of rows from the excel
	public static HSSFRow getRow(HSSFSheet sheet, int rowNum){
		HSSFRow row1=sheet.getRow(rowNum);
		return row1;
	}

	//get cell data from the excel
	public static String getCellData(HSSFSheet sheet, int rowNum,int cellNum){
		try{
			row=sheet.getRow(rowNum);
			cell=row.getCell(cellNum);
			String data=cell.getStringCellValue();
			return data;
		}catch(Exception e){
			System.out.println(e);
			return "";
		}
	}


	//get Cell Result 
	public static void setCellResult(String path,int rowNum, String val) throws IOException, InterruptedException{
		FileInputStream inp=new FileInputStream(path);
		HSSFWorkbook workbook=new HSSFWorkbook(inp);
		HSSFSheet sheet1=workbook.getSheet("Test Steps");
		HSSFRow row1=sheet1.getRow(rowNum);
		row1.createCell(6).setCellValue(val);
		inp.close();
		Thread.sleep(100);
		FileOutputStream out=new FileOutputStream(path);
		workbook.write(out);
		out.close();
	}

	//set test case result 
	public static void setTestCaseResult(String path, int rowNo, int colNum, String val) throws Exception {
		FileInputStream inp=new FileInputStream(path);
		HSSFWorkbook workbook=new HSSFWorkbook(inp);
		HSSFSheet sheet1=workbook.getSheet("Test Cases");
		HSSFRow row1=sheet1.getRow(rowNo);
		row1.createCell(colNum).setCellValue(val);
		inp.close();
		FileOutputStream out=new FileOutputStream(path);
		workbook.write(out);
		out.close();
	}

	//Extract value from the WebElement
	public String getValueUsingXpath(String objName)
	{
		try
		{
			WebElement tempElement = getWebElement(objName);
			String value = tempElement.getText();
			if ((tempElement != null) && 
					(tempElement.isEnabled())) {
				return value;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Failed to get the Value of the Element  " + objName); }
		return null;
	}

	//Extract Attirbute from the WebElement	  
	public String getValueUsingAttribute(String objName,String attribute)
	{
		try
		{
			WebElement tempElement = getWebElement(objName);
			String value = tempElement.getAttribute(attribute);
			if ((tempElement != null) && 
					(tempElement.isEnabled())) {
				return value;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Failed to get the Value of the Element  " + objName); }
		return null;
	}


	public UtilityFunction() {}

	// to read the confiGuration File using the string name as argument
	public static String readConfigurationFile(String key)
	{
		try
		{
			properties = new Properties();
			properties.load(new FileInputStream("Test_Configuration\\Config.properties"));
			if (key.length() > 0) {
				return properties.getProperty(key).trim();
			}
		}
		catch (Exception e) {
			System.out.println(e);

			System.err.println("Could not read property " + key + " from Config file."); }
		return null;
	}


	// to read the object repository
	public String readObjectRepositoryFile(String key)
	{
		try
		{
			properties = new Properties();
			properties.load(new FileInputStream("Test_ObjectRepository\\OR.properties"));
			if (key.trim().length() > 0) {
				return properties.getProperty(key).trim();
			}
		}
		catch (Exception e) {
			System.out.println(e);

			System.err.print("Could not to read Object property " + key); }
		return null;
	}

	// to initialize the web driver
	public WebDriver initilizeDriver(String BrowserName, String Environment) { 
		String URL = null;
		String Wrapper_Path = null;

		try {
				if (BrowserName.trim().equalsIgnoreCase("chrome")) {
					ChromeOptions cOptions = new ChromeOptions();
					cOptions.addArguments("test-type");
					cOptions.addArguments("start-maximized");
					cOptions.addArguments("--js-flags=--expose-gc");  
					cOptions.addArguments("--enable-precise-memory-info"); 
					cOptions.addArguments("--disable-popup-blocking");
					cOptions.addArguments("--disable-default-apps");
					cOptions.addArguments("--disable-extensions");
					cOptions.addArguments("disable-infobars");
					Map<String, Object> prefs = new HashMap<String, Object>();
					prefs.put("credentials_enable_service", false);
					prefs.put("profile.password_manager_enabled", false);
					cOptions.setExperimentalOption("prefs", prefs);
					System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
					driver = new org.openqa.selenium.chrome.ChromeDriver(cOptions);
					driver.manage().window().maximize();
					if (Environment.equals("Test")) {
						URL = readConfigurationFile("TestURL");
					}
					else if (Environment.equals("Stage")) {
						URL = readConfigurationFile("StageURL");
					}
					else if (Environment.equals("Prod")) {
						URL = readConfigurationFile("ProdURL");		  
					}
					else if (Environment.equals("GetNGProd")) {
						URL = readConfigurationFile("GETNGPRODURL");		  
					}
					else if (Environment.equals("TdecUrl")) {
						URL = readConfigurationFile("TEDC_URL");		  
					}
					else if (Environment.equals("CCH_StageURL")) {
						URL = readConfigurationFile("CCH_StageURL");		  
					}
					driver.get(URL);
					driver.manage().timeouts().pageLoadTimeout(intBrowserSyncTimeOut, TimeUnit.SECONDS);
					return driver;
				}
				if (BrowserName.trim().equalsIgnoreCase("EO_Web") || BrowserName.trim().equalsIgnoreCase("EO.Web")) {		              
					ChromeOptions options = new ChromeOptions();
					Proxy proxy = new Proxy();
					proxy.setHttpProxy("localhost:3128");
					DesiredCapabilities cap=new DesiredCapabilities();
					cap.setCapability("network.proxy.type", 1);
					cap.setCapability("network.proxy.http", "localhost");
					cap.setCapability("network.proxy.http_port", 3128);
					cap.setCapability("dom.max_script_run_time", 30000);
					cap.setCapability("network.proxy.no_proxies_on", "adadvisor.net, intuit.com, doubleclick.net, doubleclick.com, google.com, webengage.com, demdex.net");
					if (Environment.equals("Test")) {
						Wrapper_Path = readConfigurationFile("EO_Web_path_Test");
					}
					else if (Environment.equals("Stage")) {
						Wrapper_Path = readConfigurationFile("EO_Web_path_Stage");
					}
					else if (Environment.equals("Prod")) {
						Wrapper_Path = readConfigurationFile("EO_Web_path_PROD");	  
					}
					options.setBinary(Wrapper_Path);
					System.setProperty("webdriver.chrome.driver", ".//chromedriver.exe");       
					driver = new ChromeDriver(options);        
					Reporter.log("EO.Web - Browser Launched Successfully");
					//Thread.sleep(10000);
					return driver;
				}
			}
		catch (Exception e) {
			System.err.println("Failed to Launch the Application");
			System.out.println(e);
		}
		return null;
	}


	// to enter value into an edit field
	public boolean enterValueInEditField(String objName, String value)
	{
		try
		{
			WebElement tempElement = getWebElement(objName);
			if ((tempElement != null) && 
					(tempElement.isEnabled())) {
				tempElement.clear();
				tempElement.sendKeys(new CharSequence[] { value });
				return true;
			}
		}
		catch (Exception e) {
			System.out.println(e);

			System.err.println("Failed to Enter the text field " + objName); }
		return false;
	}



	public boolean enterValueInEditField(WebElement webelement, String value)
	{
		try
		{
			value = value.trim();
			if ((webelement.isDisplayed()) && (webelement.isEnabled())) {
				webelement.clear();
				webelement.sendKeys(new CharSequence[] { value });
				System.out.println(value + " entered successfully in text field");
				return true;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.print("Failed to Enter value in the text field"); }
		return false;
	}






	public boolean clickElement(String objName)
	{
		try
		{
			WebElement tempElement = getWebElement(objName);
			if ((tempElement != null) && 
					(tempElement.isEnabled())) {
				tempElement.click();
				return true;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Failed to Click on Element  " + objName); }
		return false;
	}
	

	public boolean clickElement(WebElement webelement)
	{
		try
		{
			if ((webelement.isDisplayed()) && (webelement.isEnabled())) {
				webelement.click();
				System.out.println("WebElement clicked Successfully");
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.print("Failed to Click on WebElement"); }
		return false;
	}






	public boolean selectCheckBox(String objName, String status)
	{
		try
		{
			WebElement tempElement = getWebElement(objName);
			if (tempElement != null) {
				if (status.equalsIgnoreCase("On")) {
					if (tempElement.isEnabled()) {
						if (!tempElement.isSelected()) {
							tempElement.click();
							System.out.println("Check box " + objName + " checked");
							return true;
						}
						System.out.println("Check box " + objName + " is already checked");
						return true;
					}

				}
				else if ((status.equalsIgnoreCase("Off")) && 
						(tempElement.isEnabled())) {
					if (tempElement.isSelected()) {
						tempElement.click();
						System.out.println("Check box " + objName + " Unchecked");
						return true;
					}
					System.out.println("Check box " + objName + " is already Unchecked");
					return true;
				}

			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());

			System.err.println("Failed to Check/Uncheck the check box"); }
		return false;
	}






	public boolean selectCheckBox(WebElement webelement, String status)
	{
		try
		{
			if (status.equalsIgnoreCase("On")) {
				if ((webelement.isDisplayed()) && (webelement.isEnabled())) {
					if (!webelement.isSelected()) {
						webelement.click();
						System.out.println("Check box checked");
						return true;
					}
					System.out.println("Check box is already checked");
					return true;
				}

			}
			else if ((status.equalsIgnoreCase("Off")) && 
					(webelement.isDisplayed()) && (webelement.isEnabled())) {
				if (webelement.isSelected()) {
					webelement.click();
					System.out.println("Check box Unchecked");
					return true;
				}
				System.out.println("Check box is already Unchecked");
				return true;
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());

			System.err.println("Failed to Check/Uncheck the check box"); }
		return false;
	}







	public boolean selectItemFromListBoxByValue(String objName, String value)
	{
		value = value.trim();
		try {
			WebElement tempElement = getWebElement(objName);
			if ((tempElement != null) && 
					(tempElement.isEnabled())) {
				select = new Select(tempElement);
				select.selectByValue(value);
				System.out.println(value + " selected from the listbox " + objName);
				return true;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Failed to Select item " + value + " from the list box"); }
		return false;
	}







	public boolean selectItemFromListBoxByText(String objName, String Text)
	{
		Text = Text.trim();
		try {
			WebElement tempElement = getWebElement(objName);
			if ((tempElement != null) && 
					(tempElement.isEnabled())) {
				select = new Select(tempElement);
				select.selectByVisibleText(Text);
				System.out.println(Text + " selected from the listbox " + objName);
				return true;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Failed to Select item " + Text + " from the list box"); }
		return false;
	}






	public boolean selectItemFromListBoxByIndex(String objName, int index)
	{
		try
		{
			WebElement tempElement = getWebElement(objName);
			if ((tempElement != null) && 
					(tempElement.isEnabled())) {
				select = new Select(tempElement);
				select.selectByIndex(index);
				System.out.println("Index " + index + " selected from the listbox " + objName);
				return true;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Failed to Select item index " + index + " from the list box"); }
		return false;
	}







	public boolean selectItemFromListBoxByValue(WebElement webelement, String value)
	{
		value = value.trim();
		try {
			if ((webelement.isDisplayed()) && (webelement.isDisplayed())) {
				select = new Select(webelement);
				select.selectByValue(value);
				System.out.println(value + " selected from the listbox");
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Failed to Select item " + value + " from the list box"); }
		return false;
	}







	public boolean selectItemFromListBoxByText(WebElement webelement, String Text)
	{
		//If text is not null only then select the text.
		if (Text.equals("null")){
			return false;
		}else{
			Text = Text.trim();
			try {
				if (Text.length() > 0) {
					select = new Select(webelement);
					if ((webelement.isEnabled())) {
						select.selectByVisibleText(Text);
						System.out.println(Text + " selected from the listbox");
						return true;
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());

				System.err.println("Failed to Select item " + Text + " from the list box"); }
			return true;
		}

	}






	public boolean selectItemFromListBoxByIndex(WebElement webelement, int index)
	{
		try
		{
			select = new Select(webelement);
			if ((webelement.isDisplayed()) && (webelement.isDisplayed())) {
				select.selectByIndex(index);
				System.out.println("Index " + index + " selected from the listbox");
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Failed to Select item index " + index + " from the list box"); }
		return false;
	}







	/*	  public boolean selectRadioButton(String objName, String value)
	  {
	    value = value.trim();
	    try {
	      List<WebElement> radio = getWebElement(objName);
	      if (radio.size() > 0) {
	        for (WebElement webelement : radio) {
	          if (webelement.getAttribute("value").equalsIgnoreCase(value)) {
	            webelement.click();
	            System.out.println("Radio Button value " + value + " selected successfully");
	            return true;
	          }
	        }
	      }
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	    }
	    System.err.println("Failed to Select Radio Button value " + value);
	    return false;
	  }*/







	public boolean selectRadioButton(List<WebElement> webelement, String value)
	{
		value = value.trim();
		try {
			for (WebElement element : webelement) {
				if (element.getAttribute("value").equalsIgnoreCase(value)) {
					element.click();
					System.out.println("Radio Button value " + value + " selected successfully");
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.err.println("Failed to Select Radio Button value " + value);
		return false;
	}







	public WebElement getWebElement(String objName)
	{
		objName = objName.trim();
		int size = 0;
		String locatorType = null;
		String locator = null;
		try
		{
			if (readObjectRepositoryFile(objName).length() > 0) {
				String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
				locatorType = strpropertyname[0].toLowerCase().trim();
				locator = strpropertyname[1].trim();

				if (locatorType.length() < 1) {
					System.err.println("Locator type not specified in OR file.");
				}
				size = driver.findElements(By.xpath(locator)).size();
				//if (size <= 0) break label590;
				if (driver.findElement(By.xpath(locator)).isDisplayed()) {
					return driver.findElement(By.xpath(strpropertyname[1]));
				}
				String str1;
				switch ((str1 = locatorType).hashCode()) {case 3355:  if (str1.equals("id")) {} break; case 3373707:  if (str1.equals("name")) {} break; case 114256029:  if (str1.equals("xpath")) break; break; case 228335784:  if (str1.equals("partiallinktext")) {} break; case 858964706:  if (str1.equals("cssselector")) {} break; case 1195141159:  if (!str1.equals("linktext"))
				{
					//   break label590;
					size = driver.findElements(By.xpath(locator)).size();
					//if (size <= 0) break label590;
					if (driver.findElement(By.xpath(locator)).isDisplayed()) {
						return driver.findElement(By.xpath(strpropertyname[1]));
					}





					size = driver.findElements(By.name(locator)).size();
					//if (size <= 0) break label590;
					if (driver.findElement(By.name(locator)).isDisplayed()) {
						return driver.findElement(By.name(locator));
					}





					size = driver.findElements(By.id(locator)).size();
					//  if (size <= 0) break label590;
					if (driver.findElement(By.id(locator)).isDisplayed()) {
						return driver.findElement(By.id(locator));
					}
				}




				size = driver.findElements(By.linkText(locator)).size();
				if (size > 0) {
					if (driver.findElement(By.linkText(locator)).isDisplayed()) {
						return driver.findElement(By.linkText(locator));
					}





					size = driver.findElements(By.partialLinkText(locator)).size();
					if (size > 0) {
						if (driver.findElement(By.partialLinkText(locator)).isDisplayed()) {
							return driver.findElement(By.partialLinkText(locator));
						}





						size = driver.findElements(By.cssSelector(locator)).size();
						if ((size > 0) && 
								(driver.findElement(By.cssSelector(locator)).isDisplayed()))
							return driver.findElement(By.cssSelector(locator));
					}
				}
				break; }
			}
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage()); }
		label590:
			System.err.println("The specified webelement " + objName + " was not found");
			return null;
	}







	/*public List<WebElement> getWebElements(String objName)
	  {
	    objName = objName.trim();
	    int size = 0;
	    String locatorType = null;
	    String locator = null;
	    try
	    {
	      if (readObjectRepositoryFile(objName).length() > 0) {
	        String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
	        locatorType = strpropertyname[0].toLowerCase().trim();
	        locator = strpropertyname[1].trim();

	        if (locatorType.length() < 1) {
	          System.err.println("Locator type not specified in OR file.");
	        }
	        String str1;
	        switch ((str1 = locatorType).hashCode()) {case 3355:  if (str1.equals("id")) {} break; case 3373707:  if (str1.equals("name")) {} break; case 114256029:  if (str1.equals("xpath")) break; break; case 228335784:  if (str1.equals("partiallinktext")) {} break; case 858964706:  if (str1.equals("cssselector")) {} break; case 1195141159:  if (!str1.equals("linktext"))
	          {
	            break label600;
	            size = driver.findElements(By.xpath(locator)).size();
	            if (size > 0) {
	              System.out.println("Number of WebElements matching locator description " + size);
	              return driver.findElements(By.xpath(locator));




	              size = driver.findElements(By.name(locator)).size();
	              if (size > 0) {
	                System.out.println("Number of WebElements matching locator description " + size);
	                return driver.findElements(By.name(locator));




	                size = driver.findElements(By.id(locator)).size();
	                if (size > 0) {
	                  System.out.println("Number of WebElements matching locator description " + size);
	                  return driver.findElements(By.id(locator));
	                }
	              }
	            }
	          } else {
	            size = driver.findElements(By.linkText(locator)).size();
	            if (size > 0) {
	              System.out.println("Number of WebElements matching locator description " + size);
	              return driver.findElements(By.linkText(locator));




	              size = driver.findElements(By.partialLinkText(locator)).size();
	              if (size > 0) {
	                System.out.println("Number of WebElements matching locator description " + size);
	                return driver.findElements(By.partialLinkText(locator));




	                size = driver.findElements(By.cssSelector(locator)).size();
	                if (size > 0) {
	                  System.out.println("Number of WebElements matching locator description " + size);
	                  return driver.findElements(By.cssSelector(locator));
	                }
	              }
	            }
	          }
	          break; }
	      }
	    } catch (NoSuchElementException e) { System.out.println(e.getMessage()); }
	    label600:
	    System.err.println("The specified webelement " + objName + " was not found");
	    return null;
	  }*/






	public boolean mouseHoverMenuItem(List<WebElement> webelement)
	{
		try
		{
			action = new Actions(driver);
			for (WebElement element : webelement) {
				action.moveToElement(element).build().perform();
			}
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}






	public void logResults(String folderName, String step, String status, String description)
			throws IOException
	{
		Date date = new Date();
		DateFormat dateFormat = new java.text.SimpleDateFormat("MM/dd/YYYY HH:mm:ss");
		String strDate = dateFormat.format(date);
		strDate = strDate.replace("/", "").replace(":", "").replace(" ", "").substring(0, 8);

		String logFileDirectory = readConfigurationFile("logFileDirectory") + folderName + "\\";
		String logFileName = logFileDirectory + folderName + "_" + strDate + ".csv";

		createDirectory(logFileDirectory);
		createFile(logFileName);

		System.out.println("Writing Results..");
		BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(new File(logFileName), true));
		writer.write("[" + dateFormat.format(date) + "]" + "," + step + "," + status + "," + description);
		writer.newLine();
		writer.close();
	}

	public static void createDirectory(String dirPath) {
		File file = new File(dirPath);
		file.mkdirs();
	}

	public static void createFile(String logFileName) throws IOException {
		File file = new File(logFileName);
		file.createNewFile();
	}





	public boolean captureScreenShot(String stepName, String FolderName)
	{
		try
		{
			File srcFile = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File("Test_Results\\SnapShots\\" + FolderName + "\\" + stepName + ".jpg"), true);
			System.out.println("Screenshot captured successfully");
			return true;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			System.err.println("Failed to capture Screenshot");
		}
		return false;
	}







	public boolean captureObjectLevelScreenShot(String objName, String stepName, String FolderName)
	{
		objName = objName.trim();
		int imageHeight = 0;
		int imageWidth = 0;
		try {
			WebElement tempElement = getWebElement(objName);

			imageHeight = tempElement.getSize().getHeight();

			imageWidth = tempElement.getSize().getWidth();

			File srcFile = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

			BufferedImage fullimage = ImageIO.read(srcFile);
			Point point = tempElement.getLocation();

			BufferedImage subimg = fullimage.getSubimage(point.getX(), point.getY(), imageWidth, imageHeight);
			ImageIO.write(subimg, "png", srcFile);
			FileUtils.copyFile(srcFile, new File("Test_Results\\SnapShots\\" + FolderName + "\\" + stepName + ".jpg"), true);
			System.out.println("Screenshot captured successfully");
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.err.println("Failed to capture Screenshot");
		}
		return false;
	}







	public boolean captureObjectLevelScreenShot(WebElement webelement, String stepName, String FolderName)
	{
		int imageHeight = 0;
		int imageWidth = 0;
		try
		{
			imageHeight = webelement.getSize().getHeight();

			imageWidth = webelement.getSize().getWidth();

			File srcFile = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

			BufferedImage fullimage = ImageIO.read(srcFile);
			Point point = webelement.getLocation();

			BufferedImage subimg = fullimage.getSubimage(point.getX(), point.getY(), imageWidth, imageHeight);
			ImageIO.write(subimg, "jpg", srcFile);
			FileUtils.copyFile(srcFile, new File("Test_Results\\SnapShots\\" + FolderName + "\\" + stepName + ".jpg"), true);
			System.out.println("Screenshot captured successfully");
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.err.println("Failed to capture Screenshot");
		}
		return false;
	}







	public boolean waitForElementVisibility(String objName)
	{
		WebDriverWait wait = new WebDriverWait(driver, intObjectSyncTimeOut);
		try {
			if (readObjectRepositoryFile(objName).length() > 0) {
				String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
				if (strpropertyname[0].equalsIgnoreCase("xpath"))
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strpropertyname[1])));
					System.out.println(objName + " was synchronized successfully for Visibility");
					return true;
				}

				if (strpropertyname[0].equalsIgnoreCase("name")) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(strpropertyname[1])));
					System.out.println(objName + " was synchronized successfully for Visibility");
					return true;
				}
				if (strpropertyname[0].equalsIgnoreCase("id")) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(strpropertyname[1])));
					System.out.println(objName + " was synchronized successfully");
					return true;
				}
				if (strpropertyname[0].equalsIgnoreCase("linkText")) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(strpropertyname[1])));
					System.out.println(objName + " was synchronized successfully for Visibility");
					return true;
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Could not synchronize " + objName + " for Visibility"); }
		return false;
	}







	public boolean waitForElementVisibility(WebElement webelement)
	{
		WebDriverWait wait = new WebDriverWait(driver, intObjectSyncTimeOut);
		try {
			wait.until(ExpectedConditions.visibilityOf(webelement));
			System.out.println("Object Synchronization Successfull for Visibility");
			return true;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());

			System.err.println("Could not Synchronize Object for Visibility"); }
		return false;
	}


	public boolean waitForElementInVisibility(String objName)
	{
		WebDriverWait wait = new WebDriverWait(driver, intObjectSyncTimeOut);
		try {
			if (readObjectRepositoryFile(objName).length() > 0) {
				String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
				if (strpropertyname[0].equalsIgnoreCase("xpath"))
				{
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(strpropertyname[1])));
					System.out.println(objName + " was synchronized successfully for Invisibility");
					return true;
				}

				if (strpropertyname[0].equalsIgnoreCase("name")) {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.name(strpropertyname[1])));
					System.out.println(objName + " was synchronized successfully for Invisibility");
					return true;
				}
				if (strpropertyname[0].equalsIgnoreCase("id")) {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(strpropertyname[1])));
					System.out.println(objName + " was synchronized successfully for Invisibility");
					return true;
				}
				if (strpropertyname[0].equalsIgnoreCase("linkText")) {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.linkText(strpropertyname[1])));
					System.out.println(objName + " was synchronized successfully for Invisibility");
					return true;
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Could not synchronize " + objName + " for Invisibility"); }
		return false;
	}







	public boolean waitForElementInVisibility(By by)
	{
		WebDriverWait wait = new WebDriverWait(driver, intObjectSyncTimeOut);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			System.out.println("Object Synchronization Successfull for Invisibility");
			return true;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Could not Synchronize Object for Invisibility"); }
		return false;
	}







	public boolean doubleClick(String objName)
	{
		objName = objName.trim();
		try {
			action = new Actions(driver);
			WebElement tempElement = getWebElement(objName);
			if ((tempElement != null) && 
					(tempElement.isEnabled())) {
				action.doubleClick(tempElement).build().perform();
				System.out.println("Double clicked on the element " + objName);
				return true;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			System.err.println("Failed to double click on the element " + objName);
		}
		return false;
	}






	public boolean doubleClick(WebElement webelement)
	{
		try
		{
			action = new Actions(driver);
			if ((webelement.isDisplayed()) && (webelement.isEnabled())) {
				action.doubleClick(webelement).build().perform();
				System.out.println("Double clicked on the webelement successfully");
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.err.println("Failed to double click on the webelement");
		}
		return false;
	}






	public boolean verifyTextExist(String text)
	{
		try
		{
			text.trim();
			if (driver.getPageSource().contains(text)) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Could not find the text " + text); }
		return false;
	}







	public boolean clickBrowserBack()
	{
		driver.navigate().back();
		return true;
	}







	public void closeBrowsers()
	{
		driver.close();
		//driver.quit();
		//return true;
	}

	
	
	
	




	public String getElementText(String objName)
	{
		try
		{
			WebElement tempElement = getWebElement(objName);
			if ((tempElement != null) && 
					(tempElement.getText().trim().length() > 0)) {
				return tempElement.getText().trim();
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("No Text Found for the object " + objName); }
		return null;
	}

	public String getEleText(String objName)
	{
		String str=null;
		try
		{
			WebElement tempElement = getWebElement(objName);
				str=tempElement.getCssValue("value") ;
				System.out.println(str);
				return str;
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("No Text Found for the object " + objName); }
		return str;
	}




	public String verifyAlertText(String objName)
	{
		try
		{
			if (isAlertPresent(objName)) {
				alert = driver.switchTo().alert();
				String tempText = alert.getText().trim();
				alert.accept();
				if (tempText.length() > 0) {
					return alert.getText().trim();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("No alert text displayed"); }
		return null;
	}






	public boolean isAlertPresent(String objName)
	{
		try
		{
			WebDriverWait alertWait = new WebDriverWait(driver, 5L);
			if (alertWait.until(ExpectedConditions.alertIsPresent()) != null) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("No alert was present"); }
		return false;
	}






	public boolean acceptAlert(String objName)
	{
		try
		{
			if (isAlertPresent(objName)) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}






	public boolean dismissAlert(String objName)
	{
		try
		{
			if (isAlertPresent(objName)) {
				Alert alert = driver.switchTo().alert();
				alert.dismiss();
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}






	/*	  public boolean sendKeyBoardInput(String keyboardinput)
	  {
	    try
	    {
	      action = new Actions(driver);
	      keyboardinput = keyboardinput.toUpperCase().trim();
	      String str; switch ((str = keyboardinput).hashCode()) {case -1273587079:  if (str.equals("ARROW DOWN")) {} break; case -653593709:  if (str.equals("PAGE DOWN")) {} break; case -497413582:  if (str.equals("ARROW UP")) {} break; case -85595700:  if (str.equals("PAGE UP")) {} break; case 82805:  if (str.equals("TAB")) {} break; case 66129592:  if (str.equals("ENTER")) break; break; case 78869602:  if (str.equals("SHIFT")) {} break; case 1669525821:  if (str.equals("CONTROL")) {} break; case 2054124673:  if (!str.equals("ESCAPE")) {
	          break label590;
	          action.sendKeys(new CharSequence[] { Keys.ENTER }).build().perform();
	          System.out.println("Sending Keyboard input ENTER");
	          return true;

	          action.sendKeys(new CharSequence[] { Keys.CONTROL }).build().perform();
	          System.out.println("Sending Keyboard input CONTROL");
	          return true;

	          action.sendKeys(new CharSequence[] { Keys.SHIFT }).build().perform();
	          System.out.println("Sending Keyboard input SHIFT");
	          return true;

	          action.sendKeys(new CharSequence[] { Keys.TAB }).build().perform();
	          System.out.println("Sending Keyboard input TAB");
	          return true;

	          action.sendKeys(new CharSequence[] { Keys.ARROW_DOWN }).build().perform();
	          System.out.println("Sending Keyboard input Arrow down");
	          return true;

	          action.sendKeys(new CharSequence[] { Keys.ARROW_UP }).build().perform();
	          System.out.println("Sending Keyboard input Arrow Up");
	          return true;
	        } else {
	          action.sendKeys(new CharSequence[] { Keys.ESCAPE }).build().perform();
	          System.out.println("Sending Keyboard input Escape");
	          return true;

	          action.sendKeys(new CharSequence[] { Keys.PAGE_UP }).build().perform();
	          System.out.println("Sending Keyboard input PageUp");
	          return true;

	          action.sendKeys(new CharSequence[] { Keys.PAGE_DOWN }).build().perform();
	          System.out.println("Sending Keyboard input PageDown");
	          return true;
	        }
	        break; }
	    } catch (Exception e) {
	      System.out.println(e.getMessage());
	      System.err.println("Failed to send Keyboard input " + keyboardinput); }
	    label590:
	    return false;
	  }*/






	public String generateRandomNumber()
	{
		String strRandom = "";
		String strNumbers = "123456789";
		Random rnd = new Random();
		StringBuilder strRandomNumber = new StringBuilder(9);
		for (int i = 0; i < 8; i++)
			strRandomNumber.append(strNumbers.charAt(rnd.nextInt(strNumbers.length())));
		strRandom = "4" + strRandomNumber.toString();
		return strRandom;
	}







	public String getCurrentPageTitle()
	{
		if (driver.getTitle().trim().length() > 0) {
			return driver.getTitle().trim();
		}
		return null;
	}







	public int initilizeTestDataSource(String TestCaseName)
			throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException, IOException, javax.xml.xpath.XPathExpressionException
	{
		System.out.println("Reading test data...");

		FileInputStream file = new FileInputStream(new File("Test_Data\\testdata.xml"));
		builderFactory = DocumentBuilderFactory.newInstance();
		builder = builderFactory.newDocumentBuilder();
		xmlDocument = builder.parse(file);

		xPath = XPathFactory.newInstance().newXPath();
		strTestCaseName = TestCaseName;
		String expression = "//TestCaseName[@name='" + strTestCaseName + "']/TestRecord[@isActive='Yes']";

		nodeList = ((NodeList)xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET));
		ActiverecordCount = nodeList.getLength();

		System.out.println("Total number of test data records found " + ActiverecordCount);

		if (ActiverecordCount == 0) {
			System.err.println("No Test Records found. Please verify test data source\n");
			return 0;
		}
		return ActiverecordCount;
	}


	public boolean Validate(String objName)
	{
		objName = objName.trim();
		String locatorType = null;
		String locator = null;
		boolean res = false;
		try
		{
			if (readObjectRepositoryFile(objName).length() > 0) {
				String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
				locatorType = strpropertyname[0].toLowerCase().trim();
				locator = strpropertyname[1].trim();

				if (locatorType.length() < 1) {
					System.err.println("Locator type not specified in OR file.");
				}
				if (driver.findElement(By.xpath(locator)).isDisplayed()){
					res = true;
					return res; 
				}   
			}
		} 
		catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
			System.err.println("The specified webelement " + objName + " was not found");
		}
		return res;
	}



	public String getTestData(String strFieldName, int recordNumber)
	{
		try
		{
			String expression = "//TestCaseName[@name='" + strTestCaseName + "']/TestRecord[@isActive='Yes']/" + strFieldName;

			nodeList = ((NodeList)xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET));

			data = nodeList.item(recordNumber).getFirstChild().getNodeValue();
		}
		catch (Exception e) {
			System.err.println(strFieldName + " does not exist. Please verify column name\n");
		}

		return data;
	}


	/*
	 * Function Name: getTestData Author: Bharath Kummagoori Date of
	 * Creation:18-Aug-2017 
	 * Description: This function is to perform blank login into the application using the username, password and 3 letter station code
	 * Input Parameters: 
	 * Date Modified: If any 
	 * Reviewed By:
	 */

	public String getborderColor(String objName) {
		objName = objName.trim();
		String locatorType = null;
		String locator = null;
		try {
			if (readObjectRepositoryFile(objName).length() > 0) {
				String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
				locatorType = strpropertyname[0].toLowerCase().trim();
				locator = strpropertyname[1].trim();

				if (locatorType.length() < 1) {
					System.err.println("Locator type not specified in OR file.");
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		String color = null;
		try {
			color = driver.findElement(By.xpath(locator)).getCssValue("border-color");
		} catch (Exception e) {
			System.out.println("XPATH Exception: " + xPath + " Exception: " + e);
		}

		// System.out.println(color);
		String s1 = color.substring(4, 17);
		// System.out.println(s1);
		StringTokenizer st = new StringTokenizer(s1);
		int r = Integer.parseInt(st.nextToken(",").trim());
		int g = Integer.parseInt(st.nextToken(",").trim());
		int b = Integer.parseInt(st.nextToken(",").trim());
		Color c = new Color(r, g, b);
		String hex = "#" + Integer.toHexString(c.getRGB()).substring(2);
		// System.out.println(hex);
		String col = null;
		if (r == 214 && g == 150 && b == 158) {
			System.out.println("Color is Red!");
			col = "Red";

		}
		return col;
	}
	
	public String getElementColor(String objName)
	{
		objName = objName.trim();
		String locatorType = null;
		String locator = null;
		try {
			if (readObjectRepositoryFile(objName).length() > 0) {
				String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
				locatorType = strpropertyname[0].toLowerCase().trim();
				locator = strpropertyname[1].trim();

				if (locatorType.length() < 1) {
					System.err.println("Locator type not specified in OR file.");
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		String color = null;
		try {
			color = driver.findElement(By.xpath(locator)).getCssValue("color");
		} catch (Exception e) {
			System.out.println("XPATH Exception: " + xPath + " Exception: " + e);
		}

		// System.out.println(color);
		String s1 = color.substring(4, 17);
		// System.out.println(s1);
		StringTokenizer st = new StringTokenizer(s1);
		int r = Integer.parseInt(st.nextToken(",").trim());
		int g = Integer.parseInt(st.nextToken(",").trim());
		int b = Integer.parseInt(st.nextToken(",").trim());
		Color c = new Color(r, g, b);
		String hex = "#" + Integer.toHexString(c.getRGB()).substring(2);
		// System.out.println(hex);
		String col = null;
		if (r == 214 && g == 150 && b == 158) {
			System.out.println("Color is Red!");
			col = "Red";

		}
		return col;
	}

	public int sizeValueOfAnyTag(String objName,String tagName){
		int numberOfFlights;
		objName = objName.trim();
		String locatorType = null;
		String locator = null;

		if (readObjectRepositoryFile(objName).length() > 0) {
			String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
			locatorType = strpropertyname[0].toLowerCase().trim();
			locator = strpropertyname[1].trim();

			if (driver.findElement(By.xpath(locator)).isDisplayed()){

				WebElement industries = driver.findElement(By.xpath(locator));
				List<WebElement> links = industries.findElements(By.tagName(tagName));
				numberOfFlights = links.size();
				return numberOfFlights; 

			}  
			else{
				return 0;
			}
		}
		return 0;
	}

	/*
	 * Function Name: RandomNumGenerate 
	 * Author: Uma Goudar
	 * Creation:18-Aug-2017 
	 * Description: This function generates and returns the random number
	 * Input Parameters: minNum, maxNum
	 * Date Modified: 
	 * Reviewed By:
	 */
	public int RandomNumGenerate(int minNum, int maxNum)
	{
		Random rand = new Random();
		return (rand.nextInt(maxNum) +minNum);
	}

	/*
	 * Function Name: createAndGetObject 
	 * Author: Niranjan Gowda S R
	 * Creation:12-Sep-2017 
	 * Description: This function returns the webelement based on object we passed
	 * Input Parameters: oWebdriver, strObjectName
	 * Date Modified: 
	 * Reviewed By:
	 */
	public WebElement createAndGetObject(WebDriver oWebDriver,String strObjectName)
	{

		String arrProps[]=null;
		WebElement oEle=null;
		try
		{
			arrProps=readObjectRepositoryFile(strObjectName).split("\\|");
			switch(arrProps[0].toLowerCase())
			{
			case "id":
				oEle=driver.findElement(By.id(arrProps[1]));
				break;
			case "xpath":
				oEle=driver.findElement(By.xpath(arrProps[1]));
				break;
			case "cssselector":
				oEle=oWebDriver.findElement(By.cssSelector(arrProps[1]));
				break;
			case "name":
				oEle=oWebDriver.findElement(By.name(arrProps[1]));
				break;
			case "linktext":
				oEle=oWebDriver.findElement(By.linkText(arrProps[1]));
				break;
			default:
				System.err.println("The specified webelement " + strObjectName + " was not found");
			}
			return oEle;
		}catch(Exception e)
		{
			return null;
		}
	}
	/*
	 * Function Name: createAndGetListObjects 
	 * Author: Niranjan Gowda S R
	 * Creation:12-Sep-2017 
	 * Description: This function returns the list of webelements based on object we passed
	 * Input Parameters: oWebdriver, strObjectName
	 * Date Modified: 
	 * Reviewed By:
	 */
	public List<WebElement> createAndGetListObjects(WebDriver oWebDriver, String strObjectName)
	{
		String arrProps[]=null;
		List<WebElement> oEle=null;
		try
		{
			arrProps=readObjectRepositoryFile(strObjectName).split("\\|");
			switch(arrProps[0].toLowerCase())
			{

			case "xpath":
				oEle=oWebDriver.findElements(By.xpath(arrProps[1]));
				break;
			case "cssselector":
				oEle=oWebDriver.findElements(By.cssSelector(arrProps[1]));
				break;
			case "name":
				oEle=oWebDriver.findElements(By.name(arrProps[1]));
				break;
			case "linktext":
				oEle=oWebDriver.findElements(By.linkText(arrProps[1]));
				break;
			case "classname":
				oEle=oWebDriver.findElements(By.className(arrProps[1]));
				break;
			default:
				System.err.println("The specified webelement " + strObjectName + " was not found");
			}
			return oEle;
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/*
     * Function Name: refreshPage 
      * Author: Sneha Bhojugade
     * Creation: 14th Jan 2018 
      * Description: Function to click on the refresh icon on the page
     * Input Parameters: Webdriver
     * Date Modified: If any
     * Reviewed By:
     */
     
     public boolean refreshPage() throws Exception {
           boolean res = false;
           try
           {
           driver.navigate().refresh();
           Thread.sleep(5000);
           res = true;
           return res;
           }
           catch(Exception e)
           {
                  e.printStackTrace();
                  return false;
           }
     }
     public static String currentDateAndTime() 
 	{
 		String res=null;
 		try
 		{
 			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 			Calendar cal = Calendar.getInstance();
 			res= dateFormat.format(cal.getTime());
 			return res;
 		}catch (Exception e) {
 			e.printStackTrace();
 		}
 		return res;
 	}
 	/*
 	 * Function Name: DateValidation 
 	 * Author: Niranjan Gowda S R
 	 * Creation: 11th March 2018 
 	 * Description: Converting any date format to required MMMMM d, yyyy format
 	 * Input Parameters:
 	 * Date Modified: If any
 	 * Reviewed By:
 	 */
 	
     public String DateValidation() throws Exception {
 		String res = null;
 		try
 		{
 			SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
 			Date date = new Date();
 	        newDateFormat.applyPattern("d MMMMM yyyy");
 	        String ModifiedDate = newDateFormat.format(date);
 	        System.out.println(ModifiedDate);
 		return ModifiedDate;
 		}
 		catch(Exception e)
 		{
 			e.printStackTrace();
 			return res;
 		}
 	}
 	/*
 	 * Function Name: SystemDate 
 	 * Author: Niranjan Gowda S R
 	 * Creation: 11th March 2018 
 	 * Description: Converting any date format to required mm/date/year format
 	 * Input Parameters:
 	 * Date Modified: If any
 	 * Reviewed By:
 	 */
 	
 	public static String SystemDate() throws Exception {
 		String res=null;
 		try
 		{
 			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
 			Calendar cal = Calendar.getInstance();
 			res= dateFormat.format(cal.getTime());
 			return res;
 		}catch (Exception e) {
 			e.printStackTrace();
 		}
 		return res;
 	}
 	
     public static ExtentReports getExtent(){
         if (extent != null)
                return extent; //avoid creating new instance of html file
         extent = new ExtentReports();           
         extent.attachReporter(getHtmlReporter());
         return extent;
   }

   private static ExtentHtmlReporter getHtmlReporter() {

         htmlReporter = new ExtentHtmlReporter(filePath);

         // make the charts visible on report open
         htmlReporter.config().setChartVisibilityOnOpen(true);

         htmlReporter.config().setDocumentTitle("ConnectMe Automation Report");
         htmlReporter.config().setReportName("ConnectMe Automation Testing");
         return htmlReporter;
   }

   public static ExtentTest createTest(String name, String description){
         test = extent.createTest(name, description);
         return test;
   }
   
 //*********************Capturing the screenshots*********************//
   public  String captureScreenshot() throws IOException, InterruptedException{
         String destDir = null;
         File scrFile = null;
         
                destDir = readConfigurationFile("screenShootPathWeb");
                scrFile = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
               
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
         String destFile = dateFormat.format(new Date()) + ".png";
         try {
          FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
          
         } catch (IOException e) {
          e.printStackTrace();
         }
         
         String dest=destDir + "/" + destFile;
         return dest;
      }
   @AfterClass
   public static void closure() {
	   
 	  driver.close();
 	/* try {
		//Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
   }
   @AfterSuite
   public static void killBrowser() {
	   //To kill the chrome driver after execution.
       try {
		Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   public static void testResult(ArrayList<String> report) {
		try {
				String fn = System.getProperty("user.dir") + UtilityFunction.readConfigurationFile(report.get(0));
				File file = new File(fn);
				FileInputStream in = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(in);
				XSSFSheet sheet = workbook.getSheet(TestCaseListener.reportSheet);
				int size = sheet.getPhysicalNumberOfRows();
				XSSFRow row = sheet.createRow(size);
				row.createCell(0).setCellValue(report.get(1));
				row.createCell(1).setCellValue(report.get(2));
				row.createCell(2).setCellValue(report.get(3));
				String status = report.get(4);
				Cell cell = row.createCell(3);
				CellStyle css = workbook.createCellStyle();
				if (status.equals(Status.PASS.toString())) {
					css.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					cell.setCellStyle(css);
					cell.setCellValue(status);
				} else {
					css.setFillForegroundColor(IndexedColors.RED.getIndex());
					cell.setCellStyle(css);
					cell.setCellValue(status);
				}
				css = null;
				in.close();
				// File f = new File(fn); // updated
				try (FileOutputStream out = new FileOutputStream(fn)) {
					workbook.write(out);
				} catch (Exception e) {
					e.printStackTrace();
				}
				workbook.close();
			
	} catch (Exception e) {
		e.printStackTrace();
	}	
}
   public static void resultOutputFile(String path, String DateAndTime,String ModuleName,String scriptName, String PassFail) throws Exception {
	      
	      String fn = System.getProperty("user.dir")+UtilityFunction.readConfigurationFile(path);
	      File file= new File(fn);
	      FileInputStream in=  new FileInputStream(file); 
	      XSSFWorkbook workbook=new XSSFWorkbook(in);
	      XSSFSheet sheet=workbook.getSheet("Sheet1");
	      
	      int size=sheet.getPhysicalNumberOfRows();
	      XSSFRow row = sheet.createRow(size);   
	      row.createCell(0).setCellValue(DateAndTime);
	      row.createCell(1).setCellValue(ModuleName);
	      row.createCell(2).setCellValue(scriptName);
	      row.createCell(3).setCellValue(PassFail);
	      in.close();
	      

	 File f = new File(fn); //<-- FILL HERE

	 try (FileOutputStream out = new FileOutputStream(f,false)) {
	      workbook.write(out);
	     out.close();
	    
	 } catch (Exception e) {
	     e.printStackTrace();

	 }
	 workbook.close();

	}


  public void addDelay(int milliseconds) {
		try {
			System.out.println(("adding delay: " + milliseconds + " ms"));
			Thread.sleep(milliseconds);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
  
  
  public boolean waitForElementVisibility(String objName , WebDriver passeddriver)
	{
		WebDriver driver;
		driver = passeddriver;
		WebDriverWait wait = new WebDriverWait(driver, intObjectSyncTimeOut);
		try {
			if (readObjectRepositoryFile(objName).length() > 0) {
				String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
				if (strpropertyname[0].equalsIgnoreCase("xpath"))
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strpropertyname[1])));
					System.out.println(objName + " was synchronized successfully for Visibility");
					return true;
				}

				if (strpropertyname[0].equalsIgnoreCase("name")) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(strpropertyname[1])));
					System.out.println(objName + " was synchronized successfully for Visibility");
					return true;
				}
				if (strpropertyname[0].equalsIgnoreCase("id")) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(strpropertyname[1])));
					System.out.println(objName + " was synchronized successfully");
					return true;
				}
				if (strpropertyname[0].equalsIgnoreCase("linkText")) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(strpropertyname[1])));
					System.out.println(objName + " was synchronized successfully for Visibility");
					return true;
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Could not synchronize " + objName + " for Visibility"); }
		return false;
	}
  
  
  public boolean Validate(String objName, WebDriver passeddriver)
	{
		objName = objName.trim();
		String locatorType = null;
		String locator = null;
		boolean res = false;
		WebDriver driver;
		driver = passeddriver;
		try
		{
			if (readObjectRepositoryFile(objName).length() > 0) {
				String[] strpropertyname = readObjectRepositoryFile(objName).split("\\|");
				locatorType = strpropertyname[0].toLowerCase().trim();
				locator = strpropertyname[1].trim();
				

				if (locatorType.length() < 1) {
					System.err.println("Locator type not specified in OR file.");
				}
				if (driver.findElement(By.xpath(locator)).isDisplayed()){
					//res = false;
					res = true;
					return res; 
				}   
			}
		} 
		catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
			System.err.println("The specified webelement " + objName + " was not found");
		}
		return res;
	}
  
  
  
  
  
  public boolean clickElement(String objName, WebDriver passeddriver)
	{
		WebDriver driver;
		driver = passeddriver;
		try
		{
			WebElement tempElement = getWebElement(objName);
			if ((tempElement != null) && 
					(tempElement.isEnabled())) {
				tempElement.click();
				return true;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());

			System.err.println("Failed to Click on Element  " + objName); }
		return false;
	}
  
}