package driver;

import java.lang.reflect.Method;

import methods.AppDependentMethods;
import methods.AppIndependentMethods;
import methods.Datatable;

public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static Datatable datatable = null;
	public static boolean blnRes = false;
	
	
	public static void main(String[] args) {
		appInd = new AppIndependentMethods();
		appDep = new AppDependentMethods();
		datatable = new Datatable();
		
		Class cls = null;
		Object obj = null;
		Method method = null;
		String filePath = null;
		try {
			filePath = System.getProperty("user.dir")+"\\Controller\\RunController.xlsx";
			
			int rows = datatable.getRowNumber(filePath, "Scripts");
			for(int r=0; r<rows; r++) {
				String executeTest = datatable.getCellData(filePath, "Scripts", "ExecutionStatus", r+1);
				if(executeTest.equalsIgnoreCase("Yes")) {
					String scriptName = datatable.getCellData(filePath, "Scripts", "ScriptName", r+1);
					String className = datatable.getCellData(filePath, "Scripts", "ClassName", r+1);
					
					cls = Class.forName(className);
					obj = cls.newInstance();
					method = obj.getClass().getMethod(scriptName);
					method.invoke(obj);
				}
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}
		finally {
			cls = null;
			obj = null;
			method = null;
			datatable = null;
			filePath = null;
		}
	}
}
