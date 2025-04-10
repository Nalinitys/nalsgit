package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager; 
import io.github.bonigarcia.wdm.managers.ChromeDriverManager; 
import org.testng.annotations.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class NaukriLoginTest {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            // Run Chrome in Headless Mode
//            options.addArguments("--headless=new");  // Use new headless mode- not launching browser in jenkins
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");


            driver = new ChromeDriver(options);
            System.out.println("✅ WebDriver initialized successfully!");

        } catch (Exception e) {
            System.out.println("❌ WebDriver initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void naukriLoginTest() {
        if (driver == null) {
            System.out.println("❌ Test failed: WebDriver is not initialized!");
            return;
        }
        String email = System.getenv("NAUKRI_USERNAME");
        String password = System.getenv("NAUKRI_PASSWORD");
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        
        if (email == null || password == null) {
            System.out.println("❌ Credentials not found! Set NAUKRI_EMAIL and NAUKRI_PASSWORD.");
            return;
        }

        driver.get("https://www.naukri.com/nlogin/login");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
        	WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@placeholder, 'Email ID')]")));
            WebElement passwordField = driver.findElement(By.xpath("//input[contains(@placeholder, 'Password')]"));
            WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(),'Login')]"));

            emailField.sendKeys(email);
            passwordField.sendKeys(password);
            loginButton.click();
            System.out.println("✅ Successfully logged in!");

            // Navigate to Profile
            WebElement viewProfile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='view-profile-wrapper']/a[1]")));
            viewProfile.click();

            WebElement editIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='icon edit ']")));
            editIcon.click();

            WebElement save = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Save']")));
            save.click();

            System.out.println("✅ Profile updated successfully!");
        } catch (Exception e) {
            System.out.println("❌ Element not found: " + e.getMessage());
            takeScreenshot("naukri-login-failure.png");
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ WebDriver closed successfully!");
        }
    }

    public void takeScreenshot(String fileName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Path.of(fileName));
            System.out.println("📸 Screenshot captured: " + fileName);
        } catch (Exception e) {
            System.out.println("⚠️ Screenshot capture failed: " + e.getMessage());
        }
    }
}
