package com.example.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;

class LoginAutomationTest {
    @Test
    void testLogin() {
        // Set up ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--ignore-certificate-errors");
        
        // Set up the WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\shama\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver(options);
        
        // Create a WebDriverWait instance
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Navigate to the OrangeHRM demo site
            driver.get("https://opensource-demo.orangehrmlive.com/");
            
            // Wait for username field to be present and visible
            WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("username"))
            );
            
            // Find password field
            WebElement passwordField = driver.findElement(By.name("password"));
            
            // Perform login
            usernameField.sendKeys("Admin");
            passwordField.sendKeys("admin123");
            
            // Find and click login button
            WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
            loginButton.click();
            
            // Wait for URL to contain dashboard
            wait.until(ExpectedConditions.urlContains("/dashboard"));
            
            // Validate successful login by checking dashboard URL
            String expectedUrlPart = "/dashboard";
            String actualUrl = driver.getCurrentUrl();
            assertTrue(actualUrl.contains(expectedUrlPart), 
                      "URL should contain '/dashboard' after successful login");
            
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Rethrow to fail the test
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
