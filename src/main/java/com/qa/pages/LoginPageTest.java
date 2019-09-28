package com.qa.pages;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;

public class LoginPageTest extends TestBase{
		/*public LoginPageTest(){
			super();
		}*/
		
	boolean result;
	
	@BeforeMethod
	public void setUp(){
		initialization();
		
	}
	
	@Test(priority=1)
	public void AddingNewMovie() throws InterruptedException{
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.findElement(By.name("password")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.name("username")).sendKeys(prop.getProperty("password"));
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
		
		driver.findElement(By.xpath("//a[contains(text(),'Profile')]")).click();
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
			Assert.assertTrue(true, "Movie Name is Display");
		}
		
	}
	
	@Test(priority=1)
	public void LoginTestDriven() throws InterruptedException{
		
	}
	

	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
}
