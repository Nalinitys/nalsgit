package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.*;

import java.time.Duration;

public class NaukriLoginTest {
	WebDriver driver = new ChromeDriver();
	@BeforeClass
    public void setup() {
        ChromeOptions options = new ChromeOptions();

        // Run Chrome in Headless Mode
        options.addArguments("--headless=new");  // Use new headless mode
        options.addArguments("--disable-gpu");  
        options.addArguments("--no-sandbox");  
        options.addArguments("--disable-dev-shm-usage");  
        options.addArguments("--window-size=1920,1080"); // Ensure full-page rendering

        driver = new ChromeDriver(options);
    }

    @Test
    public void naukriLoginTest() throws InterruptedException {
        String email = "nalinitys@gmail.com";
        String password = "sytinilan@143";

        if (email == null || password == null) {
            throw new RuntimeException("❌ Credentials not found! Set NAUKRI_EMAIL and NAUKRI_PASSWORD.");
        }

        // Open Naukri Login Page
        driver.get("https://www.naukri.com/nlogin/login");
        driver.manage().window().maximize();

        // Explicit wait for login fields
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@placeholder, 'Email ID')]")));
        WebElement passwordField = driver.findElement(By.xpath("//input[contains(@placeholder, 'Password')]"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(),'Login')]"));
        Thread.sleep(5000);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();

        Thread.sleep(2000);

        // Navigate to Profile
        WebElement viewProfile = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"view-profile-wrapper\"]/a[1]")));
        viewProfile.click();

        Thread.sleep(2000);
        WebElement editIcon = driver.findElement(By.xpath("//*[@Class='icon edit ']"));
        editIcon.click();

        Thread.sleep(2000);
        WebElement save = driver.findElement(By.xpath("//*[text()='Save']"));
        save.click();

        System.out.println("✅ Successfully logged into Naukri!");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
