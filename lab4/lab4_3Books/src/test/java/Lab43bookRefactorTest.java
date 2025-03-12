
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.*;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

@ExtendWith(SeleniumJupiter.class)
public class Lab43bookRefactorTest {
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @BeforeEach
  public void setUp() {
    vars = new HashMap<String, Object>();
  }
  @AfterEach
  public void tearDown(FirefoxDriver driver) {
    driver.quit();
  }
  @Test
  public void lab43bookTest(FirefoxDriver driver) {
    driver.get("https://cover-bookstore.onrender.com/");
    driver.findElement(By.cssSelector("[data-testid=\"book-search-input\"]")).click();
    driver.findElement(By.cssSelector("[data-testid=\"book-search-input\"]")).sendKeys("Harry Potter");
    driver.findElement(By.className("Navbar_searchBtn__26UF_")).click();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.elementToBeClickable(By.className("SearchList_bookInfoContainer__bkMz1")));
    assertThat(driver.findElement(By.className("SearchList_bookTitle__1wo4a")).getText(), is("Harry Potter and the Sorcerer\'s Stone"));
    assertThat(driver.findElement(By.className("SearchList_bookAuthor__3giPc")).getText(), is("J.K. Rowling"));
  }
}