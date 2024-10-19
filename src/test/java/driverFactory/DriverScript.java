package driverFactory;

import org.openqa.selenium.WebDriver;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {

	WebDriver driver;
	String inputpath="./FileInput/Controller.xlsx";
	String outputpath="./FileOutput/HybridResults.xlsx";
	String TCSheet="MasterTestCases";
	public void startTest() throws Throwable
	{
		String module_status="";
		String module_new="";
		ExcelFileUtil xl=new ExcelFileUtil(inputpath);

		for(int i=1;i<=xl.rowCount(TCSheet);i++)
		{
			if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
			{
				String TCModule=xl.getCellData(TCSheet, i, 1);

				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					String Description=xl.getCellData(TCModule, j, 0);
					String Otype=xl.getCellData(TCModule, j, 1);
					String Ltype=xl.getCellData(TCModule, j, 2);
					String Lvalue=xl.getCellData(TCModule, j,3);
					String Tdata=xl.getCellData(TCModule, j, 4);
					try 
					{
						if(Otype.equalsIgnoreCase("startBrowser"))
						{
							driver=FunctionLibrary.startBrowser();
						}
						if(Otype.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl();
						}
						if(Otype.equalsIgnoreCase("waitForElement"))
						{

							FunctionLibrary.waitForElement(Ltype, Lvalue, Tdata);
						}
						if(Otype.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(Ltype, Lvalue, Tdata);
						}
						if(Otype.equalsIgnoreCase("clickAction"))
						{
							Thread.sleep(2000);
							FunctionLibrary.clickAction(Ltype, Lvalue);
						}
						if(Otype.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(Tdata);
						}
						//	if(Otype.equalsIgnoreCase("clickAction"))
						//{
						//Thread.sleep(3000);
						//FunctionLibrary.clickAction(Ltype, Lvalue);
						//}
						if(Otype.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser();
						}
						if(Otype.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Ltype, Lvalue, Tdata);
						}
						if(Otype.equalsIgnoreCase("captureStock"))
						{
							FunctionLibrary.captureStock(Ltype, Lvalue);
						}
						if(Otype.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable();
						}

						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						module_status="True";
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						module_new="False";
					}

					if(module_status.equalsIgnoreCase("True"))
					{
						xl.setCellData(TCSheet, i, 3, "Pass", outputpath);
					}
				}
				if(module_new.equalsIgnoreCase("False"))
				{
					xl.setCellData(TCSheet, i, 3, "Fail", outputpath);
				}

			}
			else
			{
				xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
			}

		}
	}


}
