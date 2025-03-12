import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BlazeFlightsList {
    private WebDriver driver;
    private final String url = "https://blazedemo.com/reserve.php";

    @FindBy(className = "table")
    private WebElement flightsTable;

    private List<WebElement> flights;

    public BlazeFlightsList(WebDriver driver){
        this.driver = driver; 
        driver.get(url);
        PageFactory.initElements(driver, this);
        initFlights();
    }

    public void initFlights(){
        WebElement listOfFlights = flightsTable.findElement(By.tagName("tbody"));
        flights = listOfFlights.findElements(By.tagName("tr"));
    }

    public BlazeDemoPurchase selectFlight(int number){
        for (WebElement flight : flights){
            System.out.println(flight.getText());

            List<WebElement> columns = flight.findElements(By.tagName("td"));
            for (WebElement elem : columns){
                System.out.println(elem.getText());
            }
            if (columns.get(1).getText().equals(String.valueOf(number))){
                columns.get(0).findElement(By.tagName("input")).click();
                return new BlazeDemoPurchase(this.driver);
            }
        }
        return null;
    }
}
