import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldChromeJupiterTest {

    static final Logger log = getLogger(HelloWorldChromeJupiterTest.class);

    private WebDriver driver; 

    @BeforeAll
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup(); 
    }

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver(); 
    }

    @Test
    void test() {
        // Exercise
        String sutUrl = "https://rodrigoabreu22.github.io/PI_Microsite/";
        driver.get(sutUrl); 
        String title = driver.getTitle(); 
        log.debug("The title of {} is {}", sutUrl, title); 

        // Verify
        assertThat(title).isEqualTo("Intelligence in Action: AI-Driven Networks | Intelligence in Action: AI-Driven Networks"); 
    }

    @Test
    void test2(){
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl); 
        
        driver.findElement(By.linkText("Slow calculator")).click();

        assertThat(driver.getCurrentUrl()).isEqualTo("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");
    }

    @AfterEach
    void teardown() {
        driver.quit(); 
    }

}
