package methods;

import java.io.FileInputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import driver.DriverScript;

public class Datatable extends DriverScript{
	/***********************************************
	 * Method Name	: getCellData()
	 * 
	 * 
	 * *********************************************
	 */
	public String getCellData(String filePath, String sheetName, String colName, int rowum)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		String strData = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh==null) {
				appInd.writeResult("Fail", "The sheet '"+sheetName+"' doesnot exist.");
				return null;
			}
			
			//Find the colNum based on column Name
			row = sh.getRow(0);
			for(int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
				cell = row.getCell(c);
				if(cell.getStringCellValue().trim().equalsIgnoreCase(colName)) {
					colNum = c;
					break;
				}
			}
			
			row = sh.getRow(rowum);
			cell = row.getCell(colNum);
			
			//Format excel cell value
			if(cell==null || cell.getCellType()==CellType.BLANK) {
				strData = "";
			}
			else if(cell.getCellType()==CellType.BOOLEAN) {
				strData = String.valueOf(cell.getBooleanCellValue());
			}
			else if(cell.getCellType()==CellType.STRING) {
				strData = cell.getStringCellValue();
			}
			else if(cell.getCellType()==CellType.NUMERIC) {
				if(HSSFDateUtil.isCellDateFormatted(cell)) {
					double dt = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(dt));
					
					//If day is <10 then prefix with zero
					if(cal.get(Calendar.DAY_OF_MONTH) < 10)
					{
						sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
					}else {
						sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
					}
					
					
					//IF month is <10 then prefix with zero
					if((cal.get(Calendar.MONTH)+1) < 10)
					{
						sMonth = "0" + (cal.get(Calendar.MONTH)+1);
					}else {
						sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
					}
					
					sYear = String.valueOf(cal.get(Calendar.YEAR));
					
					strData = sDay+"/"+sMonth+"/"+sYear;
				}else {
					strData = String.valueOf(cell.getNumericCellValue());
				}
			}
			return strData;
		}catch(Exception e)
		{
			appInd.writeResult("Excecption", "Exception in getCellData() method. "+e.getMessage());
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				appInd.writeResult("Exception", "Exception in getCellData() method. "+e.getMessage());
				return null;
			}
		}
	}
	
	
	
	
	
	/***********************************************
	 * Method Name	: getRowNumber()
	 * 
	 * 
	 * *********************************************
	 */
	public int getRowNumber(String filePath, String sheetName)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh==null) {
				appInd.writeResult("Fail", "The sheet '"+sheetName+"' doesnot exist.");
				return -1;
			}
			
			return sh.getPhysicalNumberOfRows()-1;
		}catch(Exception e)
		{
			appInd.writeResult("Exception", "Exception in getRowNumber() method. "+e.getMessage());
			return -1;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				appInd.writeResult("Exception", "Exception in getRowNumber() method. "+e.getMessage());
				return -1;
			}
		}
	}
}
