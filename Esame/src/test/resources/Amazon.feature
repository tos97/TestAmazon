Feature: Amazon test for the cucumber exam, in the first given enter true if you want mobile viewing, false web desktop

  Background:
    Given the opening of the test app on mobile: false
    Given open amazon site

  Scenario: print the list of featured articles
    When the list of featured articles is found and prints them in the console
    Then check if it found anything

  Scenario: print the category list
    When found and prints in the console the list of categories
    Then check if it found anything

  Scenario: search through autocompletion
    When searching for Iphone click on a result in the suggestion menu
    Then check if he clicked the correct item

  Scenario: print the search results of the first 3 pages
    Given searching for Iphone click on a result in the suggestion menu
    When searched for an item then starts saving items
    Then check if it worked

  Scenario: opens search results in different tabs
    Given searching for Iphone click on a result in the suggestion menu
    When has searched for an element then opens the first 3 results in other tabs
    Then check if it worked

  Scenario: opens nav bar by putting filters to search
    When search how many results come out of the tag: Videogiochi
    Then check if the results are right

  Scenario: puts items in the cart
    When the item to search for is Accendini is placed in the cart
    And the item to search for is Borraccia is placed in the cart
    And the item to search for is Penne is placed in the cart
    Then check if it was really added 3
    Then empty cart