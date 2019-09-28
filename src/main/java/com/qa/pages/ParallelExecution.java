package com.qa.pages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.util.TestUtil;
import com.qa.util.WebEventListener;

public class ParallelExecution extends TestBase{
	
	boolean result;
	boolean Logout;
	WebDriver driver = null;
	
	@Test()
	public void AddingNewMovie() throws InterruptedException{
		
		String browserName = prop.getProperty("browser");
		
		if(browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "\\exeFiles\\chromedriver.exe");	
			driver = new ChromeDriver(); 
		}
		else if(browserName.equals("FF")){
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+ "\\exeFiles\\geckodriver.exe");
			
			driver = new FirefoxDriver(); 
		}
		
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url2"));
		
		driver.findElement(By.xpath("//button[@class='btn btn-secondary']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.name("password")).sendKeys(prop.getProperty("password"));
		driver.findElement(By.name("username")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
		
		//Click on Add Movie button
		Thread.sleep(2000);
		WebElement addMovies;
		WebDriverWait wait=new WebDriverWait(driver, 20);
		addMovies=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'add movie')]")));
		addMovies.click();
		
		
		
		//Filling Details
		driver.findElement(By.name("title")).sendKeys(prop.getProperty("title"));
		driver.findElement(By.name("director")).sendKeys(prop.getProperty("director"));
		driver.findElement(By.xpath("//textarea[@placeholder='Please enter a short description of the movie']")).sendKeys(prop.getProperty("desciption"));
		
		WebElement list =driver.findElement(By.xpath("//select[@name='categories']"));
		
		Select categories = new Select(list);
		categories.selectByValue(prop.getProperty("categories"));
		
		driver.findElement(By.name("file")).sendKeys(prop.getProperty("URL"));
		driver.findElement(By.xpath("//div[@class='row form-group']//*[5]")).click();
		driver.findElement(By.xpath("//button[contains(text(),'Save Movie')]")).click();

		
	}

	@Test(dependsOnMethods = { "AddingNewMovie" })
	public void VerifyMovies() throws InterruptedException{
		
		String browserName = prop.getProperty("browser");
		
		if(browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "\\exeFiles\\chromedriver.exe");	
			driver = new ChromeDriver(); 
		}
		else if(browserName.equals("FF")){
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+ "\\exeFiles\\geckodriver.exe");
			
			driver = new FirefoxDriver(); 
		}
		
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url2"));
		
		driver.findElement(By.xpath("//button[@class='btn btn-secondary']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.name("password")).sendKeys(prop.getProperty("password"));
		driver.findElement(By.name("username")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
		
		driver.findElement(By.xpath("//a[contains(text(),'Movies')]")).click();
		driver.findElement(By.name("search")).sendKeys(prop.getProperty("title"));
		
		
		try {
		result =driver.findElement(By.xpath("//div[contains(text(),'No Results found :/')]")).isDisplayed();
		}
		catch(Exception e) {
			
		}
		if(result=true) {
			Assert.assertEquals(result, true, "Movie Name Not Display");
			
		}
		else {
			Assert.assertEquals(result, true, "Movie Name is Display");
		}

		
	}
}
