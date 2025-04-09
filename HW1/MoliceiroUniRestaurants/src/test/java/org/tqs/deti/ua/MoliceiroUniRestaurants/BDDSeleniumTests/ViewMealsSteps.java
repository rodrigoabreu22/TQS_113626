package org.tqs.deti.ua.MoliceiroUniRestaurants.BDDSeleniumTests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@ExtendWith(SeleniumJupiter.class)
public class ViewMealsSteps {
    WebDriver driver;

    @Given("the user landed on the Home page")
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/restaurants");
    }


    @When("the user clicks the View Meals button for {string}")
    public void clickViewMealsButtonForRestaurant(String restaurantName) {
        // Find all restaurant cards
        List<WebElement> restaurantCards = driver.findElements(By.cssSelector(".restaurant-card"));
        
        for (WebElement card : restaurantCards) {
            // Find the restaurant name inside the card
            WebElement title = card.findElement(By.cssSelector(".card-title"));
        
            if (title.getText().equalsIgnoreCase(restaurantName)) {
                // Click the 'View Meals' button in this card
                WebElement viewMealsButton = card.findElement(By.cssSelector("a.btn.btn-success"));
                viewMealsButton.click();
                return;
            }
        }
    
        throw new RuntimeException("Restaurant with name '" + restaurantName + "' not found.");
    }


    @Then("the user should be redirected to the {string} meals page")
    public void getResults(String restaurantName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {
            // Wait for the <span> inside the <h2> to be visible
            WebElement heading = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.text-center.mb-4 span"))
            );

            String displayedName = heading.getText();
            System.out.println("Restaurant name on page: " + displayedName);

            // Assert that the name on the page matches the expected restaurant name
            assertEquals(restaurantName, displayedName, "The restaurant name displayed does not match the expected value.");

        } catch (TimeoutException e) {
            throw new AssertionError("Restaurant meals page did not load as expected.");
        }
    }
}
