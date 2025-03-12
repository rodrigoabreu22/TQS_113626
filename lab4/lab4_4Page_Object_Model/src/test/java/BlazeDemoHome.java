import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class BlazeDemoHome {
    private WebDriver driver;
    private static final String url = "https://blazedemo.com/";

    @FindBy(name="fromPort")
    private WebElement fromCity;

    @FindBy(name="toPort")
    private WebElement toCity;

    @FindBy(xpath = "/html/body/div[3]/form/div/input")
    private WebElement findButton;

    public BlazeDemoHome(WebDriver driver){
        this.driver = driver; 
        driver.get(url);
        PageFactory.initElements(driver, this);
    }

   public void setFromCity(String val) {
    Select select = new Select(fromCity);
    select.selectByVisibleText(val);
    }
    
    public void setToCity(String val) {
        Select select = new Select(toCity);
        select.selectByVisibleText(val);
    }
    
    public String getFromCity() {
        Select select = new Select(fromCity);
        return select.getFirstSelectedOption().getText();
    }
    
    public String getToCity() {
        Select select = new Select(toCity);
        return select.getFirstSelectedOption().getText();
    }

    public void findFlights(){
        findButton.click();
    }

}
