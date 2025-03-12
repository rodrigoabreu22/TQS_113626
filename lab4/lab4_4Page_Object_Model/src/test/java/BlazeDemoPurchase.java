import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlazeDemoPurchase {
    private WebDriver driver;

    @FindBy(id = "inputName")
    private WebElement inputName;

    @FindBy(id = "address")
    private WebElement inputAddress;

    @FindBy(id = "city")
    private WebElement inputCity;

    @FindBy(id = "state")
    private WebElement inputState;

    @FindBy(id = "zipCode")
    private WebElement inputZipCode;

    @FindBy(id = "cardType")
    private WebElement inputCardType;

    @FindBy(id = "creditCardNumber")
    private WebElement inputCreditCardNumber;

    @FindBy(id = "creditCardMonth")
    private WebElement inputMonth;

    @FindBy(id = "creditCardYear")
    private WebElement inputYear;

    @FindBy(id = "nameOnCard")
    private WebElement inputNameOnCard;

    @FindBy(css = "input[value=\"Purchase Flight\"]")
    private WebElement purchaseFlightButton;

    public BlazeDemoPurchase(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillForm(
        String name,
        String address,
        String city,
        String state,
        String zipCode,
        String cardType,
        String creditCardNumber,
        String month,
        String year,
        String nameOnCard
    ){
        inputName.sendKeys(name);
        inputAddress.sendKeys(address);
        inputCity.sendKeys(city);
        inputState.sendKeys(state);
        inputZipCode.sendKeys(zipCode);
        inputCardType.sendKeys(cardType);
        inputCreditCardNumber.sendKeys(creditCardNumber);
        inputMonth.sendKeys(month);
        inputYear.sendKeys(year);
        inputNameOnCard.sendKeys(nameOnCard);
    }

    public String getInputName() {
        return inputName.getAttribute("value");
    }

    public String getInputAddress() {
        return inputAddress.getAttribute("value");
    }

    public String getInputCity() {
        return inputCity.getAttribute("value");
    }

    public String getInputState() {
        return inputState.getAttribute("value");
    }

    public String getInputZipCode() {
        return inputZipCode.getAttribute("value");
    }

    public String getInputCardType() {
        return inputCardType.getAttribute("value");
    }

    public String getInputCreditCardNumber() {
        return inputCreditCardNumber.getAttribute("value");
    }

    public String getInputMonth() {
        return inputMonth.getAttribute("value");
    }

    public String getInputYear() {
        return inputYear.getAttribute("value");
    }

    public String getInputNameOnCard() {
        return inputNameOnCard.getAttribute("value");
    }

    public void clickPurchaseFlightButton() {
        purchaseFlightButton.click();
    }
}
