package org.tqs.deti.ua;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RPNCaculator {

    private Calculator calc;

    @Given("a calculator I just turned on")
    public void setup(){
        calc = new Calculator();
    }

    @When("I add {int} and {int}")
    public void add(int a, int b){
        calc.push(a);
        calc.push(b);
        calc.push("+");
    }

    @When("I subtract {int} to {int}")
    public void subtract(int a, int b){
        calc.push(a);
        calc.push(b);
        calc.push("-");
    }

    @When("I multiply {int} to {int}")
    public void multiply(int a, int b){
        calc.push(a);
        calc.push(b);
        calc.push("*");
    }

    @When("I divide {int} by {int}")
    public void divide(int a, int b){
        calc.push(a);
        calc.push(b);
        calc.push("/");
    }


    @Then("the result is infinity")
    public void infinity(){
        Number value = calc.value();
        assertEquals(Double.POSITIVE_INFINITY, value.doubleValue());
    }

    @Then("the result is {int}")
    public void result(int expected){
        Number value = calc.value();
        assertEquals(expected, value.intValue());
    }
}
