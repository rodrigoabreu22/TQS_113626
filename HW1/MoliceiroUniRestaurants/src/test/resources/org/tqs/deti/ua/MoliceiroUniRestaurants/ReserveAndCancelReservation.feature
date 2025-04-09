Feature: Reserve and Cancel Reservation

    Scenario: View Restaurant Meals

      Given the user is on the restaurant page
      When the user cliks the reservation button of a valid meal
      And gets his code
      And goes clicks the tab to search reservation
      And searchs his code on the searchbar
      And cancel the reservation
      Then its reservation should be cancelled.