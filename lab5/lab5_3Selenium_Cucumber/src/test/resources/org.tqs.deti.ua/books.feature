Feature: Search book on the online library

    Background:
        Given that I am on the homepage

    Scenario: Search a book by title
        When I type "Harry Potter" in the search bar
        And I click on the search button
        Then I should get 1 results
        And the book should have the title "Harry Potter and the Sorcerer's Stone"


    Scenario: Search a book by author
        When I type "George R.R. Martin" in the search bar
        And I click on the search button
        Then I should get 1 results
        And the book should have the title "A Game of Thrones"


    Scenario: Search a non existent book
        When I type "mistery" in the search bar
        And I click on the search button
        Then I should get 0 results

    Scenario: Filter by category
        When I click on "Fiction" in the category filters
        Then I should get 3 results from the category "Fiction"