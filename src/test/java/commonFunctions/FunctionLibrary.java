package commonFunctions;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {

	public static WebDriver driver;
	public static Properties conpro;
	
	public static WebDriver startBrowser() throws Throwable
	{
	  conpro=new Properties();
	  conpro.load(new FileInputStream("./PropertiesFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver=new FirefoxDriver();
		}
		else
		{
			Reporter.log("browser value is not matching");
			
		}
		
		return driver;
		
	}
	
	
	public static void openUrl()
	{
		driver.get(conpro.getProperty("url"));
	}
	
	
	public static void waitForElement(String ltype,String lvalue,String Tdata)
	{
		WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Tdata)));
		
		if(ltype.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(lvalue)));
		}
		if(ltype.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(lvalue)));
		}	
		if(ltype.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(lvalue)));
		}
		
	}
	
	
	public static void typeAction(String ltype,String lvalue,String Tdata)
	{
		if(ltype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(lvalue)).sendKeys(Tdata);
		}
		if(ltype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(lvalue)).sendKeys(Tdata);
		}
		if(ltype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(lvalue)).sendKeys(Tdata);
		}

	}
	
	
	public static void clickAction(String ltype,String lvalue)
	{
		if(ltype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(lvalue)).click();
		}
		if(ltype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(lvalue)).click();
		}
		if(ltype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(lvalue)).click();
		}
	}
	
	
	public static void validateTitle(String exp_title)
	{
		String act_title=driver.getTitle();
		try
		{
			Assert.assertEquals(act_title, exp_title,"title not matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	
	
	public static void closeBrowser()
	{
		driver.quit();
	}
	
	

}
