package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MarketUMAuto {

    public WebDriver accessMarketUm(WebDriver driver){
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        String url = "https://www.marketalertum.com/";
        driver.get(url); 
        return driver;
    }
    
    public WebDriver goToLogInInvalid(WebDriver driver){
        driver.findElement(By.xpath("//a[@href='/Alerts/Login']")).click(); 
        driver.findElement(By.id("UserId")).sendKeys("wontWork"); 
        driver.findElement(By.xpath("//input[@type='submit']")).click(); 
        return driver;
    }

    public WebDriver goToLogInValid(WebDriver driver, String username){
        driver.findElement(By.xpath("//a[@href='/Alerts/Login']")).click(); 
        driver.findElement(By.id("UserId")).sendKeys(username); 
        driver.findElement(By.xpath("//input[@type='submit']")).click(); 
        return driver;
    }

    public WebDriver goToLogOut(WebDriver driver){
        driver.findElement(By.xpath("//a[@href='/Home/Logout']")).click();  
        return driver;
    }
    
    public WebDriver goToAlerts(WebDriver driver){
        driver.findElement(By.xpath("//a[@href='/Alerts/List']")).click();         
        return driver;
    }
 
}
