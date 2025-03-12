import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@ExtendWith(SeleniumJupiter.class)
public class MainSiteTest {
    WebDriver driver;

    @BeforeEach
    public void setup(){
        driver = new FirefoxDriver();
    }

    @Test
    public void testFlightPurchase() {

        driver.get("https://blazedemo.com/");

        BlazeDemoHome home = new BlazeDemoHome(driver);

        home.setFromCity("Boston");
        assertThat(home.getFromCity()).isEqualTo("Boston");

        home.setToCity("Berlin");
        assertThat(home.getToCity()).isEqualTo("Berlin");

        BlazeFlightsList flightsPage = new BlazeFlightsList(driver);

        flightsPage.selectFlight(43);

        BlazeDemoPurchase purchasePage = new BlazeDemoPurchase(driver);

        purchasePage.fillForm(
                "Cristiano Ronaldo",
                "Rua da Madeira",
                "Funchal",
                "Madeira",
                "1234",
                "",
                "123456789",
                "",
                "",
                "CR7"
        );

        assertThat(purchasePage.getInputName()).isEqualTo("Cristiano Ronaldo");

        assertThat(purchasePage.getInputState()).isEqualTo("Madeira");

        assertThat(purchasePage.getInputCreditCardNumber()).isEqualTo("123456789");

        assertThat(purchasePage.getInputYear()).isEqualTo("2017");

        assertThat(purchasePage.getInputCardType()).isEqualTo("visa");

        purchasePage.clickPurchaseFlightButton();

        ConfirmationPage confirmationPage = new ConfirmationPage(driver);

        assertEquals(confirmationPage.getConfirmationMessage(), "Thank you for your purchase today!");

    }

    @AfterEach
    public void close(){
        driver.close();
    }
}