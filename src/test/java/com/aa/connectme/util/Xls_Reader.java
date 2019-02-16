/*
* Scenario Name: Xls_Reader 
* Author: Uma Goudar
* Date of Creation: 28-Aug-2017
* Description: This class will have reusable functions related to Excel
* Date Modified: 30-Aug-17
* Reviewed By:
*/
package com.aa.connectme.util;


import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Calendar;

public class Xls_Reader {

	public static String filename = System.getProperty("user.dir") + "\\Test_Data\\TestData.xlsx";
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	
	/*
	* Function Name: Xls_Reader 
	* Author: Uma Goudar
	* Date of Creation: 28-Aug-2017
	* Description: Constructor to load the excel file
	* Parameters: 
	* Date Modified: 
	* Reviewed By:
	*/
	public Xls_Reader() {
		
		try {
			fis = new FileInputStream(filename);
			workbook = new XSSFWorkbook(fis);
			//sheet = workbook.getSheetAt(0);
			//fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	/*
	* Function Name: rowCount 
	* Author: Niranjan Gowda S R
	* Date of Creation: 1-Jan-2018
	* Description: To get the physical number of rows in Excel
	* Parameters: FileName, SheetName
	* Date Modified: 
	* Reviewed By:
	*/
	public int rowCount(String SheetName)
	{
		FileInputStream fin=null;
		Workbook wb=null;
		Sheet sh=null;
		int rc=0;
		try
		{
			fin=new FileInputStream(filename);
			wb=new XSSFWorkbook(fin);
			sh=wb.getSheet(SheetName);
			rc=sh.getPhysicalNumberOfRows();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fin.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return rc;
	}
	
	/*
	* Function Name: getCellData 
	* Author: Uma Goudar
	* Date of Creation: 28-Aug-2017
	* Description: Read Excel cell data by providing Sheetname, column name and row number
	* Parameters : sheetName,  colName, rowNum
	* Date Modified:
	* Reviewed By:
	*/
	// returns the data from a cell
	public String getCellData(String sheetName, String colName, int rowNum) {
		
		int index;
		int col_Num;
		int rowTraverse;
		
		
		try {
			//rowNum = Integer.parseInt(rowNumber);
			//Check if no data in Excel file
			if (rowNum <= 0){
				return ""; }

			//Get sheet index
			index = workbook.getSheetIndex(sheetName);
			
			//Check if No columns in the Excel sheet
			col_Num = -1;
			if (index == -1){
				return ""; }

			//Traverse the Excel sheet data to get the Column Name
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (rowTraverse = 0; rowTraverse < row.getLastCellNum(); rowTraverse++) {
				
				if (row.getCell(rowTraverse).getStringCellValue().trim().equals(colName.trim())){
					col_Num = rowTraverse;
				}
			}
			
			if (col_Num == -1){
				return ""; }

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			
			if (row == null){
				return "";  }
			
			cell = row.getCell(col_Num);

			if (cell == null){
				return "";   }
			
			//Return the data as per the data type of Excel sheet
			if (cell.getCellType() == Cell.CELL_TYPE_STRING){
				return cell.getStringCellValue(); 
			}else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}else
				{
					double d=cell.getNumericCellValue();
					DecimalFormat df=new DecimalFormat("#.#######");
					cellText=df.format(d);
				}

				return cellText;
				
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";}
			else{
				return String.valueOf(cell.getBooleanCellValue()); }

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}
}
