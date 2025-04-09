package org.tqs.deti.ua.MoliceiroUniRestaurants.BDDSeleniumTests;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
public class BookReservationSteps {
    WebDriver driver;

    @Given("the user is on the Home page")
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/restaurants");
    }

    @When("the user clicks the button View Meals for {string}")
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

    @And("cliks the reservation button of a valid meal")
    public void clicksMealReserveButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {
            // Wait for all reserve buttons to be present and clickable
            List<WebElement> reserveButtons = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("button.btn.btn-success.btn-sm"))
            );

            // Skip the first two buttons and click the third one
            if (reserveButtons.size() > 2) {
                reserveButtons.get(2).click();  // Click the third button
            } else {
                throw new AssertionError("Not enough valid meal reservation buttons found.");
            }
        } catch (TimeoutException e) {
            throw new AssertionError("No valid meal reservation button found.");
        }
    }



    @Then("the user should get an alert with its reservation code")
    public void getResult() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {
            // Wait for the modal to appear with the correct title
            WebElement modalTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-title"))
            );
            assertEquals("Reservation Confirmation", modalTitle.getText(), "Modal title is incorrect.");

            // Check confirmation message
            WebElement modalMessage = driver.findElement(By.id("modalMessage"));
            assertEquals("Reservation confirmed!", modalMessage.getText(), "Modal message is incorrect.");

            // Ensure the code block is displayed
            WebElement codeBlock = driver.findElement(By.id("highlightCode"));
            assertTrue(codeBlock.isDisplayed(), "Reservation code block is not displayed.");

        } catch (TimeoutException e) {
            throw new AssertionError("Reservation modal did not appear.");
        }
    }

}
