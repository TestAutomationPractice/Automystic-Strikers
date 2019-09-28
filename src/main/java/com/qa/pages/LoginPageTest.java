package com.qa.pages;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.util.TestUtil;

public class LoginPageTest extends TestBase{
		/*public LoginPageTest(){
			super();
		}*/
		
	boolean result;
	boolean Logout;
	
	@BeforeMethod
	public void setUp(){
		initialization();
		
	}
	
	@Test(priority=1)
	public void AddingNewMovie() throws InterruptedException{
		
		
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
	@DataProvider
	public Iterator<Object[]> getTestdata(){
		
		ArrayList<Object[]> testData = TestUtil.getDatafromExcel();
		return testData.iterator();
		
	}
	
	
	@Test(dataProvider = "getTestdata")
	public void LoginTestDriven(String Username, String Password) throws InterruptedException {
		
		driver.findElement(By.xpath("//button[@class='btn btn-secondary']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.name("password")).sendKeys(Password);
		driver.findElement(By.name("username")).sendKeys(Username);
		driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
		
		Thread.sleep(2000);
		try {
			Logout = driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).isDisplayed();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		Assert.assertEquals(Logout, true, "unable to login");
		
	}
	

	@AfterMethod
	public void tearDown() throws IOException{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".jpg"));
		driver.quit();
	}
}
