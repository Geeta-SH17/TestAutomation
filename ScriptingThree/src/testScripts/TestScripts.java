package testScripts;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import driver.DriverScript;

public class TestScripts extends DriverScript {

	/********************************************
	 * Script Name	:	TS_LoginLogout()
	 * Test Case ID	: 	SRS_101
	 * 
	 * 
	 * 
	 *******************************************/
	public boolean TS_LoginLogout()
	{
		WebDriver oBrowser = null;
		String strStatus = null;
		Map<String, String> objData = null;
		try {
			objData = appInd.getTestDataFromExcel("TestData", "SRS_101");
			
			oBrowser = appInd.launchBrowser(objData.get("Browser"));
			if(oBrowser!=null) {
				strStatus+= appDep.navigateURL(oBrowser, objData.get("URL"));
				strStatus+= appDep.loginToApp(oBrowser, objData.get("UserName"), objData.get("Password"));
				strStatus+= appDep.logoutFromApp(oBrowser);
				strStatus+= appInd.closeBrowser(oBrowser);
				
				if(strStatus.contains("false")) {
					appInd.writeResult("Fail", "The test script TS_LoginLogout() was failed.");
					return false;
				}else {
					appInd.writeResult("Pass", "The test script TS_LoginLogout() was PAssed.");
					return true;
				}
			}else {
				return false;
			}
		}catch(Exception e)
		{
			appInd.writeResult("Exception", "Exception in TS_LoginLogout() script. "+e.getMessage());
			return false;
		}
		finally {
			oBrowser = null;
		}
	}
	
	/********************************************
	 * Script Name	:	TS_CreateAndDeleteUser()
	 * Test Case ID	: 	SRS_102
	 * 
	 * 
	 * 
	 *******************************************/
	public boolean TS_CreateAndDeleteUser()
	{
		WebDriver oBrowser = null;
		String strStatus = null;
		Map<String, String> objData = null;
		try {
			objData = appInd.getTestDataFromExcel("TestData", "SRS_102");
			
			oBrowser = appInd.launchBrowser(objData.get("Browser"));
			if(oBrowser != null) {
				strStatus+= appDep.navigateURL(oBrowser, objData.get("URL"));
				strStatus+= appDep.loginToApp(oBrowser, objData.get("UserName"), objData.get("Password"));
				String userName = appDep.createUser(oBrowser, objData);
				strStatus+= appDep.deleteUser(oBrowser, userName);
				strStatus+= appDep.logoutFromApp(oBrowser);
				strStatus+= appInd.closeBrowser(oBrowser);
				
				if(strStatus.contains("false")) {
					appInd.writeResult("Fail", "The script TS_CreateAndDeleteUser() was failed");
					return true;
				}else {
					appInd.writeResult("Pass", "The script TS_CreateAndDeleteUser() was passed");
					return true;
				}
			}else {
				return false;
			}
		}catch(Exception e)
		{
			appInd.writeResult("Exception", "Exception in TS_CreateAndDeleteUser() script. "+e.getMessage());
			return false;
		}
		finally {
			oBrowser = null;
		}
	}
	
	
	/********************************************
	 * Script Name	:	TS_LoginWithNewUserAndDeleteUser()
	 * Test Case ID	: 	SRS_103
	 * 
	 * 
	 * 
	 *******************************************/
	public boolean TS_LoginWithNewUserAndDeleteUser()
	{
		WebDriver oBrowser1 = null;
		WebDriver oBrowser2 = null;
		String strStatus = null;
		Map<String, String> objData1 = null;
		Map<String, String> objData2 = null;
		try {
			objData1 = appInd.getTestDataFromExcel("TestData", "SRS_103_1");
			
			oBrowser1 = appInd.launchBrowser(objData1.get("Browser"));
			if(oBrowser1 != null) {
				strStatus+= appDep.navigateURL(oBrowser1, objData1.get("URL"));
				strStatus+= appDep.loginToApp(oBrowser1, objData1.get("UserName"), objData1.get("Password"));
				String userName = appDep.createUser(oBrowser1, objData1);
				
				//Login with newly created user.
				objData2 = appInd.getTestDataFromExcel("TestData", "SRS_103_2");
				
				oBrowser2 = appInd.launchBrowser(objData2.get("Browser"));
				strStatus+= appDep.navigateURL(oBrowser2, objData2.get("URL"));
				boolean blnRes = appDep.loginToApp(oBrowser2, objData2.get("UserName"), objData2.get("Password"));
				if(blnRes) {
					strStatus+=true;
				}else {
					strStatus+=false;
				}
				strStatus+= appInd.closeBrowser(oBrowser2);
				
				
				//delete the user
				strStatus+= appDep.deleteUser(oBrowser1, userName);
				strStatus+= appDep.logoutFromApp(oBrowser1);
				strStatus+= appInd.closeBrowser(oBrowser1);
				
				if(strStatus.contains("false")) {
					appInd.writeResult("Fail", "The script TS_LoginWithNewUserAndDeleteUser() was failed");
					return true;
				}else {
					appInd.writeResult("Pass", "The script TS_LoginWithNewUserAndDeleteUser() was passed");
					return true;
				}
			}else {
				return false;
			}
		}catch(Exception e)
		{
			appInd.writeResult("Exception", "Exception in TS_LoginWithNewUserAndDeleteUser() script. "+e.getMessage());
			return false;
		}
		finally {
			oBrowser1 = null;
			oBrowser2 = null;
		}
	}
}
