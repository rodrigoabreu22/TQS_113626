package org.tqs.deti.ua;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooksSteps {

    private WebDriver driver;

    @Given("that I am on the homepage")
    public void setup() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get("https://cover-bookstore.onrender.com/");
    }

    @When("I type {string} in the search bar")
    public void search(String search) {
        driver.findElement(By.cssSelector("[data-testid=\"book-search-input\"]")).click();
        driver.findElement(By.cssSelector("[data-testid=\"book-search-input\"]")).sendKeys(search);
        driver.findElement(By.className("Navbar_searchBtn__26UF_")).click();
    }

    @When("I click on the search button")
    public void iClickTheSearchButton() {
        WebElement searchButton = driver.findElement(By.cssSelector(".Navbar_searchBarContainer__3UbnF > div:nth-child(1) > button:nth-child(2)"));
        searchButton.click();
    }

    @Then("I should get {int} results")
    public void getResults(int expected) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        try {
            wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".SearchList_searchBookCard__AnSAs")));
                List<WebElement> books = driver.findElements(By.cssSelector(".SearchList_searchBookCard__AnSAs"));
                assertEquals(expected, books.size(), "The number of books found does not match the expected count.");
            } catch (TimeoutException e) {
                assertEquals(expected, 0);
        }
    }

    @Then("the book should have the title {string}")
    public void bookShouldHaveTitle(String title) {
        System.out.println(title);
        List<WebElement> bookTitles = driver.findElements(By.cssSelector(".SearchList_bookTitle__1wo4a"));
        boolean found = bookTitles.stream().anyMatch(e -> e.getText().equalsIgnoreCase(title));
        assertTrue(found, "The book should have the title " + title);
    }

    @When("I click on {string} in the category filters")
    public void iClickOnCategoryInTheFilter(String category) {
        WebElement categoryLink = driver.findElement(By.xpath("//span[contains(text(),'" + category + "')]"));
        categoryLink.click();
    }

    @Then("I should get {int} results from the category {string}")
    public void getResultsFromCategory(int expected, String category) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        List<WebElement> books = driver.findElements(By.cssSelector(".GenreBooksPage_bookCardContainer__1aB5_"));
        assertEquals(expected, books.size(), "The number of books found does not match the expected count.");
        assertEquals("Fantasy & Fiction", driver.findElement(By.cssSelector(".GenreBooksPage_genreName__ZVXTW")).getText());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
