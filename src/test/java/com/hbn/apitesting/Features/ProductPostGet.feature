Feature: Product API Testing

  @CreateProductSuccess
  Scenario Outline: Create a new product successfully
    Given I send a POST request to create a product with name "<name>", price <price>, category "<category>", and description "<description>"
    Then the response status should be 201
    And the response should contain name "<name>", price <price>, category "<category>", and description "<description>"

    Examples:
      | name        | price | category    | description                     |
      | Smartphone  | 800   | Electronics | Flagship smartphone             |
      | RunningShoes | 120  | Footwear    | Comfortable running shoes       |
      | Watch       | 200   | Accessories | Smartwatch with health tracking |

  @CreateProductFailure
  Scenario Outline: Attempt to create a product with invalid data
    Given I send a POST request to create a product with name "<name>", price <price>, category "<category>", and description "<description>"
    Then the response status should be <status>
    And the response should contain error message "<error>"

    Examples:
      | name        | price | category    | description      | status | error                                  |
      |            | 120   | Footwear    | Missing name     | 400    | Name is required                       |
      | Tablet     | 0     | Electronics | Invalid price    | 400    | Price must be positive                 |
      | Sneakers   | -30   | Footwear    | Invalid price    | 400    | Price must be positive                 |
      | SmartTV    | 1500  |             | Invalid category | 400    | Invalid category specified             |

  @GetProductById
  Scenario Outline: Retrieve a product by ID
    Given I send a GET request for product with ID <id>
    Then the response status should be <status>
    And the response should contain name "<name>", price <price>, category "<category>", and description "<description>"

    Examples:
      | id  | name        | price | category    | description                     | status |
      | 1   | Smartphone  | 800   | Electronics | Flagship smartphone             | 200    |
      | 2   | RunningShoes | 120  | Footwear    | Comfortable running shoes       | 200    |

  @ProductNotFound
  Scenario Outline: Attempt to retrieve a non-existent product
    Given I send a GET request for product with ID <id>
    Then the response status should be <status>
    And the response should contain error message "<error>"

    Examples:
      | id   | status | error             |
      | 999  | 404    | Product not found |
      | 5000 | 404    | Product not found |
