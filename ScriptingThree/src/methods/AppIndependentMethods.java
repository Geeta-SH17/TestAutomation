package methods;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
//import com.sun.istack.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

import driver.DriverScript;

public class AppIndependentMethods extends DriverScript {

	/*************************************
	 * write
	 * 
	 * 
	 */
	public void writeResult(String status, String message)
	{
		Logger log = null;
		try
		{
			log = Logger.getLogger("");
			switch(status.toLowerCase())
			{
			case "pass":
				log.info(message);
				break;
			case "fail":
				log.error(message);
				break;
			case "":
				log.info(message);
				break;
			case "exception":
				log.fatal(message);
				break;
			default:
				System.out.println("Invalid result status '"+status+"'");
		
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	/******************************************
	 * Method Name		: clickObject()
	 * Purpose			: to click the webElements in the DOM
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean clickObject(WebDriver oDriver, By objBy)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() > 0)
			{
				oEles.get(0).click();
				appInd.writeResult("Pass", "The element '"+String.valueOf(objBy)+"' was clicked successful");
				return true;
			}else {
				appInd.writeResult("Fail", "Failed to locate the element '"+String.valueOf(objBy)+"' in the DOM");
				return false;
			}
		}catch(Exception e)
		{
			System.out.println("Exception in the clickObject() method. "+ e.getMessage());
			return false;
		}
		finally
		{
			oEles = null;
		}
	}


/******************************************
	 * Method Name		: setObject()
	 * Purpose			: to enter the value in the webElement in the DOM
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean setObject(WebDriver oDriver, By objBy, String strValue)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() > 0)
			{
				oEles.get(0).sendKeys(strValue);
				appInd.writeResult("Pass", "The data '"+strValue+"' was entered in the element '"+String.valueOf(objBy)+"' successful");
				return true;
			}else {
				appInd.writeResult("Fail", "Failed to locate the element '"+String.valueOf(objBy)+"' in the DOM");
				return false;
			}
		}catch(Exception e)
		{
			System.out.println("Exception in the setObject() method. "+ e.getMessage());
			return false;
		}
		finally
		{
			oEles = null;
		}
	}
	
	/******************************************
	 * Method Name		: compareValue()
	 * Purpose			: to compare the both actual and expected values
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean compareValue(String actual, String expected)
	{
		try {
			if(actual.equalsIgnoreCase(expected)) {
				appInd.writeResult("Pass", "The actual '"+actual+"' & expected '"+expected+"' are matched");
				return true;
			}else {
				appInd.writeResult("Fail", "Mis-match in both actual '"+actual+"' & expected '"+expected+"' values");
				return true;
			}
		}catch(Exception e)
		{
			System.out.println("Exception in the compareValue() method. "+ e.getMessage());
			return false;
		}
	}
	
	/******************************************
	 * Method Name		: verifyText()
	 * Purpose			: to verify the text present in the DOM
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean verifyText(WebDriver oDriver, By objBy, String elementType, String Expected)
	{
		List<WebElement> oEles = null;
		String actual = null;
		Select oSel = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() > 0)
			{
				switch(elementType.toLowerCase())
				{
					case "text":
						actual = oEles.get(0).getText();
						break;
					case "value":
						actual = oEles.get(0).getAttribute("value");
						break;
					case "list":
						oSel = new Select(oEles.get(0));
						actual = oSel.getFirstSelectedOption().getText();
						break;
					default:
						appInd.writeResult("Fail", "Invalid element type '"+elementType+"' was mentioned");
						return false;
				}
				
				if(appInd.compareValue(actual, Expected)) {
					return true;
				}else {
					return false;
				}
			}else {
				appInd.writeResult("Fail", "Failed to locate the element '"+String.valueOf(objBy)+"' in the DOM");
				return false;
			}
		}catch(Exception e)
		{
			System.out.println("Exception in the verifyText() method. "+ e.getMessage());
			return false;
		}
		finally {
			oEles = null;
			oSel = null;
		}
	}
	
	/******************************************
	 * Method Name		: verifyElementPresent()
	 * Purpose			: to verify the presence of the given element in the DOM
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean verifyElementPresent(WebDriver oDriver, By objBy)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() > 0) {
				appInd.writeResult("Pass", "The element '"+String.valueOf(objBy)+"' present in the DOM");
				return true;
			}else {
				appInd.writeResult("Fail", "The element '"+String.valueOf(objBy)+"' doesnot exist in the DOM");
				return false;
			}
		}catch(Exception e)
		{
			System.out.println("Exception in the verifyElementPresent() method. "+ e.getMessage());
			return false;
		}
		finally {
			oEles = null;
		}
	}
	
	/******************************************
	 * Method Name		: verifyElementNotPresent()
	 * Purpose			: to verify the non-presence of the given element in the DOM
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean verifyElementNotPresent(WebDriver oDriver, By objBy)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() > 0) {
				appInd.writeResult("Fail", "The element '"+String.valueOf(objBy)+"' present in the DOM");
				return false;
			}else {
				appInd.writeResult("Pass", "The element '"+String.valueOf(objBy)+"' doesnot exist in the DOM");
				return true;
			}
		}catch(Exception e)
		{
			System.out.println("Exception in the verifyElementNotPresent() method. "+ e.getMessage());
			return false;
		}
		finally {
			oEles = null;
		}
	}
	/******************************************
	 * Method Name		: verifyOptionalElement()
	 * Purpose			: to verify the presence of the optional element in the DOM
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean verifyOptionalElement(WebDriver oDriver, By objBy)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);
			if(oEles.size() > 0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			System.out.println("Exception in the verifyOptionalElement() method. "+ e.getMessage());
			return false;
		}
		finally {
			oEles = null;
		}
	}
	/******************************************
	 * Method Name		: launchBrowser()
	 * Purpose			: to launch the required browser
	 * 
	 * 
	 * 
	 ******************************************/
	public WebDriver launchBrowser(String browserName)
	{
		WebDriver oDriver = null;
		try {
			switch(browserName.toLowerCase())
			{
				case "chrome":
					System.setProperty("webdriver.chrome.driver", ".\\Library\\driver\\chromedriver.exe");
					oDriver = new ChromeDriver();
					break;
				case "firefox":
					System.setProperty("webdriver.gecko.driver", ".\\Library\\driver\\geckodriver.exe");
					oDriver = new FirefoxDriver();
					break;
				case "ie":
					System.setProperty("webdriver.ie.driver", ".\\Library\\driver\\IEDriverServer.exe");
					oDriver = new InternetExplorerDriver();
					break;
				default:
					appInd.writeResult("Fail", "Invalid name of the browser '"+browserName+"'");
			}
			
			if(oDriver!=null) {
				oDriver.manage().window().maximize();
				appInd.writeResult("Pass", "The '"+browserName+"' browser has launched successful");
				return oDriver;
			}else {
				appInd.writeResult("Fail", "Failed to launch the '"+browserName+"' browser.");
				return null;
			}
		}catch(Exception e)
		{
			appInd.writeResult("Exception", "Exception in the method launchBrowser(). "+e.getMessage());
			return null;
		}
	}
	
	/******************************************
	 * Method Name		: closeBrowser()
	 * Purpose			: to launch the required browser
	 * 
	 * 
	 * 
	 ******************************************/
	public boolean closeBrowser(WebDriver odDriver)
	{
		try {
			odDriver.close();
			return true;
		}catch(Exception e)
		{
			appInd.writeResult("Exception", "Exception in the method closeBrowser(). "+e.getMessage());
			return false;
		}
	}
	
	//ReadProp from excel file
	
	/******************************************
	 * Method Name		: getTestDataFromExcel()
	 * Purpose			: to read the data from the excel file based on the SheetName & LogicalName
	 * 
	 * 
	 * 
	 ******************************************/
	public Map<String, String> getTestDataFromExcel(String sheetName, String logicalName)
	{
		FileInputStream fin = null;
		Map<String, String> objData = null;
		Workbook wb = null;
		Sheet sh = null;
		Row rowKey = null;
		Row rowVal = null;
		Cell cellKey = null;
		Cell cellVal = null;
		int rowNum = 0;
		int colNum = 0;
		String strKey = null;
		String strValue = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			objData = new HashMap<String, String>();
			fin = new FileInputStream(System.getProperty("user.dir")+"\\TestData\\TestData.xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh==null) {
				appInd.writeResult("Fail", "The sheet '"+sheetName+"' doesnot exist in the test data file");
				return null;
			}
			
			//Find out the rownum of the logicalname mentioned.
			int rows = sh.getPhysicalNumberOfRows();
			for(int r=0; r<rows; r++)
			{
				rowKey = sh.getRow(r);
				cellKey = rowKey.getCell(0);
				if(cellKey.getStringCellValue().equalsIgnoreCase(logicalName)) {
					rowNum = r;
					break;
				}
			}
			
			
			if(rowNum > 0) {
				rowKey = sh.getRow(0);
				rowVal = sh.getRow(rowNum);
				colNum = rowKey.getPhysicalNumberOfCells();
				for(int c = 0; c < colNum; c++)
				{
					cellKey = rowKey.getCell(c);
					cellVal = rowVal.getCell(c);
					strKey = cellKey.getStringCellValue();
					
					//We need to format the cell value in order to read the data
					if(cellVal == null || cellVal.getCellType()==CellType.BLANK)
					{
						strValue = "";
					}
					else if(cellVal.getCellType()==CellType.BOOLEAN) {
						strValue = String.valueOf(cellVal.getBooleanCellValue());
					}
					else if(cellVal.getCellType()==CellType.STRING) {
						strValue = cellVal.getStringCellValue();
					}
					else if(cellVal.getCellType()==CellType.NUMERIC) {
						//Check for date OR number
						if(HSSFDateUtil.isCellDateFormatted(cellVal)) {
							double dt = cellVal.getNumericCellValue();
							Calendar cal = Calendar.getInstance();
							cal.setTime(HSSFDateUtil.getJavaDate(dt));
							
							//if date is <10 then prefix with zero
							if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
								sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
							}else {
								sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
							}
							
							
							//if month is <10 then prefix with zero
							if((cal.get(Calendar.MONTH)+1) < 10) {
								sMonth = "0" + (cal.get(Calendar.MONTH)+1);
							}else {
								sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
							}
							
							
							sYear = String.valueOf(cal.get(Calendar.YEAR));
							strValue = sDay+"/"+sMonth+"/"+sYear;
						}else {
							strValue = String.valueOf(cellVal.getNumericCellValue());
						}
					}
					objData.put(strKey, strValue);
				}
				
				return objData;
			}else {
				appInd.writeResult("Fail", "The logicalName '"+logicalName+"' doesnot exist in the testdata excel file");
				return null;
			}
		}catch(Exception e)
		{
			appInd.writeResult("Exception", "Exception in the method getTestDataFromExcel(). "+e.getMessage());
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cellVal = null;
				cellKey = null;
				rowKey = null;
				rowVal = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				appInd.writeResult("Exception", "Exception in the method getTestDataFromExcel(). "+e.getMessage());
			}
		}
	}
	
}
