Feature: Book Meal Reservation

    Scenario: Book a meal reservation

      Given the user is on the Home page
      When the user clicks the button View Meals for "BurgerUA"
      And cliks the reservation button of a valid meal
      Then the user should get an alert with its reservation code