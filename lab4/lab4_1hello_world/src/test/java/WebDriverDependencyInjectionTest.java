import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class WebDriverDependencyInjectionTest {

    @Test
    void test(FirefoxDriver driver) {
        String sutUrl = "https://rodrigoabreu22.github.io/PI_Microsite/blog";
        driver.get(sutUrl); 
        String title = driver.getTitle();

        // Verify
        assertThat(title).isEqualTo("Blog | Intelligence in Action: AI-Driven Networks"); 
    }

}
