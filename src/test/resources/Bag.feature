Feature: Bag

  Background:
    Given the user is on a product page
    When adding the product to the Bag
    Then the product should appear in the Bag
    When the user is on a second product page
    When adding the product to the Bag
    Then both products are in the Bag

  Scenario: Remove item from the bag
    Given there are products in the bag
    When I remove product
    Then one product is removed

  Scenario: Add quantity to one product from the bag
    Given there are products in the bag
    When I add quantity to first product in the bag
    Then product quantity is increased

  Scenario: Remove quantity of the product from the bag
    Given there are products in the bag
    When I add quantity to first product in the bag
    Then product quantity is increased
    When I remove quantity for first product in the bag
    Then product quantity is decreased

