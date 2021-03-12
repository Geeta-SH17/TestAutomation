package methods;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import driver.DriverScript;

public class AppDependentMethods extends DriverScript {

	/*********************************************
	 * Method Name	: navigateURL()
	 * Purpose		: to navigate the required URL
	 * Author		: tester1
	 * Parameters	: WebDriver, URL
	 * Return Type	: boolean
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public boolean navigateURL(WebDriver oDriver, String URL)
	{
		try {
			oDriver.navigate().to(URL);
			Thread.sleep(2000);
			
			if(appInd.compareValue(oDriver.getTitle(), "actiTIME - Login"))
			{
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			appInd.writeResult("Excception", "Exception in the method navigateURL(). "+e.getMessage());
			return false;
		}
	}
	
	/*********************************************
	 * Method Name	: loginToApp()
	 * Purpose		: to login to the actiTime application
	 * Author		: tester1
	 * Parameters	: WebDriver, UserName, Password
	 * Return Type	: boolean
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public boolean loginToApp(WebDriver oDriver, String userName, String password)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.setObject(oDriver, By.id("username"), userName);
			strStatus+= appInd.setObject(oDriver, By.name("pwd"), password);
			strStatus+= appInd.clickObject(oDriver, By.id("loginButton"));
			Thread.sleep(2000);
			
			//for newly created user 
			if(appInd.verifyOptionalElement(oDriver, By.xpath("")))
			strStatus+= appInd.verifyText(oDriver, By.xpath("//td[@class='pagetitle']"), "Text", "Enter Time-Track");
			
			//close the shortcut window
			if(appInd.verifyOptionalElement(oDriver, By.xpath("//div[@style='display: block;']")))
			{
				Thread.sleep(2000);
				strStatus+= appInd.clickObject(oDriver, By.id("gettingStartedShortcutsMenuCloseId"));
				Thread.sleep(4000);
			}
			
			if(strStatus.contains("false")) {
				appInd.writeResult("Fail", "Failed to login to actiTime");
				return false;
			}else {
				appInd.writeResult("Pass", "Login to actiTime was successful");
				return true;
			}
		}catch(Exception e)
		{
			appInd.writeResult("Exception", "Exception in the method loginToApp(). "+e.getMessage());
			return false;
		}
	}
	
	/*********************************************
	 * Method Name	: createUser()
	 * Purpose		: to create the new user in the actiTime application
	 * Author		: tester1
	 * Parameters	: WebDriver
	 * Return Type	: String
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public String createUser(WebDriver oDriver, Map<String, String> objData)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.clickObject(oDriver, By.xpath("//div[text()='USERS']"));
			Thread.sleep(2000);
			
			strStatus+= appInd.clickObject(oDriver, By.xpath("//div[text()='Add User']"));
			Thread.sleep(4000);
			
			strStatus+= appInd.setObject(oDriver, By.name("firstName"), objData.get("FirstName"));
			strStatus+= appInd.setObject(oDriver, By.name("lastName"), objData.get("LastName"));
			strStatus+= appInd.setObject(oDriver, By.name("email"), objData.get("Email"));
			strStatus+= appInd.setObject(oDriver, By.name("username"), objData.get("User_UN"));
			strStatus+= appInd.setObject(oDriver, By.name("password"), objData.get("User_PWD"));
			strStatus+= appInd.setObject(oDriver, By.name("passwordCopy"), objData.get("User_ReType"));

			strStatus+= appInd.clickObject(oDriver, By.xpath("//span[text()='Create User']"));
			Thread.sleep(2000);
			
			String userName = objData.get("LastName")+", "+objData.get("FirstName");
			strStatus+= appInd.verifyElementPresent(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+userName+"'"+"]"));
			
			if(strStatus.contains("false")) {
				appInd.writeResult("Fail", "Failed to create the new User in actiTime application");
				return null;
			}else {
				appInd.writeResult("Pass", "The new user has created successful");
				return userName;
			}
		}catch(Exception e)
		{
			appInd.writeResult("Exception", "Exception in the method createUser(). "+e.getMessage());
			return null;
		}
	}
	
	/*********************************************
	 * Method Name	: deleteUser()
	 * Purpose		: to delete the user in the actiTime application
	 * Author		: tester1
	 * Parameters	: WebDriver, userName
	 * Return Type	: boolean
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public boolean deleteUser(WebDriver oDriver, String userToDelete)
	{
		String strStatus = null;
		try {
			Thread.sleep(2000);
			strStatus+= appInd.clickObject(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+userToDelete+"'"+"]"));
			Thread.sleep(2000);
			
			strStatus+= appInd.clickObject(oDriver, By.xpath("//button[contains(text(), 'Delete User')]"));
			Thread.sleep(4000);
			
			oDriver.switchTo().alert().accept();
			Thread.sleep(4000);
			
			strStatus+= appInd.verifyElementNotPresent(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+userToDelete+"'"+"]"));
			
			if(strStatus.contains("false")) {
				appInd.writeResult("Fail", "Failed to delete the User in actiTime application");
				return false;
			}else {
				appInd.writeResult("Pass", "The user has deleted successful");
				return true;
			}
		}catch(Exception e)
		{
			appInd.writeResult("Exception", "Exception in the method deleteUser(). "+e.getMessage());
			return false;
		}
	}
	
	/*********************************************
	 * Method Name	: logoutFromApp()
	 * Purpose		: to logout from the actiTime application
	 * Author		: tester1
	 * Parameters	: WebDriver
	 * Return Type	: boolean
	 * Reviewed By	: Tester2
	 * Date Created	:
	 *********************************************/
	public boolean logoutFromApp(WebDriver oDriver)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.clickObject(oDriver, By.xpath("//a[@id='logoutLink']"));
			Thread.sleep(2000);
			
			strStatus+= appInd.verifyElementPresent(oDriver, By.xpath("//img[contains(@src, '/timer.png')]"));
			
			if(strStatus.contains("false")) {
				appInd.writeResult("Fail", "Failed to logout from actiTime application");
				return false;
			}else {
				appInd.writeResult("Pass", "Logout from actiTime was successful");
				return true;
			}
		}catch(Exception e)
		{
			appInd.writeResult("Exception", "Exception in the method logoutFromApp(). "+e.getMessage());
			return false;
		}
	}


}
