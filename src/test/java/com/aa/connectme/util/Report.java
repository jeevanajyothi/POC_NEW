package com.aa.connectme.util;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Report extends UtilityFunction {

	public static WebDriver driver;
	public static String Path =System.getProperty("user.dir") + "\\Execution_Report\\ExtentReport.html";
    public static ExtentReports extent=new ExtentReports(Path,true);
	public static ExtentTest test;
	//public static ExtentReports extentReport = new ExtentReports(Path, true);
	//public static ExtentTest startTest =extentReport.startTest("", "");
	
	public void Reporter_ReportEvent(WebDriver driver1,String stepName,String status,String detail)
	{
		try{
			driver=driver1;
			extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));

			test=extent.startTest(stepName);

			if(status.equalsIgnoreCase("PASS"))
			{
				test.log(LogStatus.PASS,stepName,detail+ test.addScreenCapture(this.takeScreenShot()));
			}
			else if(status.equalsIgnoreCase("FAIL")) {
				test.log(LogStatus.FAIL,stepName,detail+ test.addScreenCapture(this.takeScreenShot()));
				test.log(LogStatus.INFO,""+ test.addScreenCapture(this.takeScreenShot()));
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
		extent.endTest(test);
		extent.flush();
	}
	

	
	protected String takeScreenShot() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        String path = System.getProperty("user.dir") + "\\Test_Results\\Screenshots\\"+dateFormat.format(new Date()) +".png";
        try {
               File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
               FileUtils.copyFile(screenshotFile, new File(path));
        } catch (Exception e) {
               e.printStackTrace();
        }
        return path;
	}

}