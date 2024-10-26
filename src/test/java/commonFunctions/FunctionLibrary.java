package commonFunctions;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
	
	
	public static void waitForElement(String ltype,String lvalue,String Tdata) throws Throwable
	{
		WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Tdata)));
		
		if(ltype.equalsIgnoreCase("xpath"))
		{
			Thread.sleep(2000);
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
			driver.findElement(By.xpath(lvalue)).clear();
			driver.findElement(By.xpath(lvalue)).sendKeys(Tdata);
		}
		if(ltype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(lvalue)).clear();
			driver.findElement(By.name(lvalue)).sendKeys(Tdata);
		}
		if(ltype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(lvalue)).clear();
			driver.findElement(By.id(lvalue)).sendKeys(Tdata);
		}

	}
	
	
	public static void clickAction(String ltype,String lvalue) throws Throwable
	{
		if(ltype.equalsIgnoreCase("xpath"))
		{
			Thread.sleep(3000);
			driver.findElement(By.xpath(lvalue)).click();
		}
		if(ltype.equalsIgnoreCase("name"))
		{
			Thread.sleep(3000);
			driver.findElement(By.name(lvalue)).click();
		}
		if(ltype.equalsIgnoreCase("id"))
		{
			Thread.sleep(3000);
			driver.findElement(By.id(lvalue)).sendKeys(Keys.ENTER);
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
	
	
	public static void closeBrowser() throws Throwable
	{
		Thread.sleep(2000);
		driver.quit();
	}
	
	
	public static void dropDownAction(String ltype,String lvalue,String tdata)
	{
		if(ltype.equalsIgnoreCase("xpath"))
		{
			int value=Integer.parseInt(tdata);
			Select element=new Select(driver.findElement(By.xpath(lvalue)));
			element.selectByIndex(value);
		}
		if(ltype.equalsIgnoreCase("name"))
		{
			int value=Integer.parseInt(tdata);
			Select element=new Select(driver.findElement(By.name(lvalue)));
			element.selectByIndex(value);
		}
		if(ltype.equalsIgnoreCase("id"))
		{
			int value=Integer.parseInt(tdata);
			Select element=new Select(driver.findElement(By.id(lvalue)));
			element.selectByIndex(value);
		}
		
	}
	
	
	public static void captureStock(String ltype,String lvalue) throws Throwable
	{
		String StockNum="";
		if(ltype.equalsIgnoreCase("xpath"))
		{
			StockNum=driver.findElement(By.xpath(lvalue)).getAttribute("value");
		}
		if(ltype.equalsIgnoreCase("name"))
		{
			StockNum=driver.findElement(By.name(lvalue)).getAttribute("value");
		}
		if(ltype.equalsIgnoreCase("id"))
		{
			StockNum=driver.findElement(By.id(lvalue)).getAttribute("value");
		}
		
		FileWriter fw=new FileWriter("./CaptureData/stockNum.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(StockNum);
		bw.flush();
		bw.close();
	}
	
	
	public static void stockTable() throws Throwable
	{
		
		FileReader fr=new FileReader("./CaptureData/stockNum.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_data=br.readLine();
		
		if(!driver.findElement(By.xpath(conpro.getProperty("searchText"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("searchIcon"))).click();
		driver.findElement(By.xpath(conpro.getProperty("searchText"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("searchText"))).sendKeys(Exp_data);
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("searchBtn"))).click();
		Thread.sleep(3000);
		
		String Act_data=driver.findElement(By.xpath("//table[@id='tbl_a_stock_itemslist']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Act_data+"       "+Exp_data,true);
		try {
			Assert.assertEquals(Exp_data, Act_data,"StockNumber is not matching");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	
	public static void capturesup(String ltype,String lvalue) throws Throwable
	{
		String supplierNum="";
		if(ltype.equalsIgnoreCase("xpath"))
		{
			supplierNum=driver.findElement(By.xpath(lvalue)).getAttribute("value");
		}
		if(ltype.equalsIgnoreCase("name"))
		{
			supplierNum=driver.findElement(By.name(lvalue)).getAttribute("value");
		}
		if(ltype.equalsIgnoreCase("id"))
		{
			supplierNum=driver.findElement(By.id(lvalue)).getAttribute("value");
		}
		
		FileWriter fw=new FileWriter("./CaptureData/supplierNum.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(supplierNum);
		bw.flush();
		bw.close();
		
	}
	
	
	public static void suppliertable() throws Throwable
	{
		FileReader fr=new FileReader("./CaptureData/supplierNum.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_data=br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("searchText"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("searchIcon"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("searchText"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("searchText"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(conpro.getProperty("searchBtn"))).click();
		
		String Act_data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
	    Reporter.log(Exp_data+"     "+Act_data,true);
	    try {
			Assert.assertEquals(Exp_data, Act_data,"supplier num not found");
		} catch (AssertionError e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	
	
	public static void capturecus(String ltype,String lvalue) throws Throwable
	{
		String customerNum="";
		if(ltype.equalsIgnoreCase("xpath"))
		{
			customerNum=driver.findElement(By.xpath(lvalue)).getAttribute("value");
		}
		if(ltype.equalsIgnoreCase("name"))
		{
			customerNum=driver.findElement(By.name(lvalue)).getAttribute("value");
		}
		if(ltype.equalsIgnoreCase("id"))
		{
			customerNum=driver.findElement(By.id(lvalue)).getAttribute("value");
		}
		
		FileWriter fw=new FileWriter("./CaptureData/customerNum.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(customerNum);
		bw.flush();
		bw.close();
		
	}
	
	
	public static void customertable() throws Throwable
	{
		FileReader fr=new FileReader("./CaptureData/customerNum.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_data=br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("searchText"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("searchIcon"))).click();
		
		driver.findElement(By.xpath(conpro.getProperty("searchText"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("searchText"))).sendKeys(Exp_data);
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("searchBtn"))).click();
		String Act_data=driver.findElement(By.xpath("//table[@id='//table[@id='tbl_a_customerslist']/tbody/tr[1]/td[5]/div/span/span")).getText();
	    Reporter.log(Exp_data+"     "+Act_data,true);
	    try {
			Assert.assertEquals(Exp_data, Act_data,"customer num not found");
		} catch (AssertionError e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	
	public static String generateDate()
	{
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("yyyy_mm_dd");
		return df.format(date);
	}
	
	
}
