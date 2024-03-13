package Card;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.id("login-button"));
        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        login.click();
    }

    public void addItemToCart(String itemName) {
        WebElement item = driver.findElement(By.xpath("//div[text()='"+itemName+"']/parent::a/parent::div/following-sibling::div/button"));
        item.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt(); // re-assert the interrupted status
        }

        WebElement cart = driver.findElement(By.xpath("//*[@id='shopping_cart_container']/a"));
        cart.click();

        List<WebElement> itemList = driver.findElements(By.xpath("//div[text()='"+itemName+"']"));
        if (!itemList.isEmpty()) {
            System.out.println("The item '" + itemName + "' exists in the cart.");
        } else {
            System.out.println("The item '" + itemName + "' doesn't exist in the cart.");
        }

    }

    @Test
    public void testAddItemToCart() {
        addItemToCart("Sauce Labs Bike Light");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
