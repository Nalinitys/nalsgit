package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class NaukriLoginTest {
    public static void main(String[] args) throws InterruptedException {
        // Get credentials from environment variables
        String email = System.getenv("NAUKRI_EMAIL");
        String password = System.getenv("NAUKRI_PASSWORD");

        
        if (email == null || password == null) {
            System.out.println("Error: Credentials not found. Make sure you set GitHub Secrets.");
            return;
        }

        // Set up WebDriver with headless mode (for GitHub Actions)
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--no-sandbox");
        WebDriver driver = new ChromeDriver(options);

        try {
            // Open Naukri Login Page
            driver.get("https://www.naukri.com/nlogin/login");
            driver.manage().window().maximize();

            // Login
            WebElement emailField = driver.findElement(By.xpath("//input[contains(@placeholder, 'Email ID')]"));
            WebElement passwordField = driver.findElement(By.xpath("//input[contains(@placeholder, 'Password')]"));
            WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(),'Login')]"));

            Thread.sleep(5000);
            emailField.sendKeys(email);
            passwordField.sendKeys(password);
            loginButton.click();
            Thread.sleep(2000);


          WebElement viewProfile = driver.findElement(By.xpath("//*[@class=\"view-profile-wrapper\"]/a[1]"));

          Thread.sleep(2000);
            viewProfile.click();
            Thread.sleep(2000);
            WebElement editIcon = driver.findElement(By.xpath("//*[@Class='icon edit ']"));
            editIcon.click();
            Thread.sleep(2000);
            WebElement save = driver.findElement(By.xpath("//*[text()='Save']"));       
          
            save.click();

            // Wait for page to load
//            Thread.sleep(5000);

            System.out.println("✅ Successfully logged into Naukri!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close browser
            driver.quit();
        }
    }
}
