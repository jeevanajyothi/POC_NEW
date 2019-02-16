package com.aa.connectme.listeners;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

public class TestCaseListener implements ITestListener {
	public static final ArrayList<ArrayList<String>> reports = new ArrayList<>();
	public static final String reportSheet = "sheet1";

	
	@Override
	public void onFinish(ITestContext arg0) {
//		try {
//			for (ArrayList<String> report : reports) {
//				String fn = System.getProperty("user.dir") + Framework.readConfigurationFile(report.get(0));
//				File file = new File(fn);
//				FileInputStream in = new FileInputStream(file);
//				XSSFWorkbook workbook = new XSSFWorkbook(in);
//				XSSFSheet sheet = workbook.getSheet(reportSheet);
//				int size = sheet.getPhysicalNumberOfRows();
//				XSSFRow row = sheet.createRow(size);
//				row.createCell(0).setCellValue(report.get(1));
//				row.createCell(1).setCellValue(report.get(2));
//				row.createCell(2).setCellValue(report.get(3));
//				String status = report.get(4);
//				Cell cell = row.createCell(3);
//				CellStyle css = workbook.createCellStyle();
//				if (status.equals(Status.PASS.toString())) {
//					css.setFillForegroundColor(IndexedColors.GREEN.getIndex());
//					cell.setCellStyle(css);
//					cell.setCellValue(status);
//				} else {
//					css.setFillForegroundColor(IndexedColors.RED.getIndex());
//					cell.setCellStyle(css);
//					cell.setCellValue(status);
//				}
//				css = null;
//				in.close();
//				// File f = new File(fn); // updated
//				try (FileOutputStream out = new FileOutputStream(fn)) {
//					workbook.write(out);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				workbook.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		reports.clear();
	}

	@Override
	public void onStart(ITestContext arg0) {
		String filePath = System.getProperty("user.dir") + File.separator + "Execution_Report" + File.separator;
		File[] reportFiles = new File[] { new File(filePath + "TestResults.xlsx")};
		for (File f : reportFiles) {
			try {
				XSSFWorkbook xssf = new XSSFWorkbook(new FileInputStream(f));
				int sheetIndex = xssf.getSheetIndex(reportSheet);
				if (!(sheetIndex == -1))
					xssf.removeSheetAt(sheetIndex);
				FileOutputStream out = new FileOutputStream(f);
				xssf.createSheet(reportSheet);
				Sheet sheet = xssf.getSheet(reportSheet);
				Row row = sheet.createRow(0);
				CellStyle css = xssf.createCellStyle();
				for (int i = 0; i < 4; i++) {
					css.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
					row.createCell(i).setCellStyle(css);
				}
				row.getCell(0).setCellValue("Date");
				row.getCell(1).setCellValue("Module Name");
				row.getCell(2).setCellValue("TestCase Name");
				row.getCell(3).setCellValue("Status");
				xssf.write(out);
				out.close();
				xssf.close();
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

		}

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

}
