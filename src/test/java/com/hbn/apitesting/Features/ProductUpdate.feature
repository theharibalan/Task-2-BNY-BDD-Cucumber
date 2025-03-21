Feature: Update Product API Testing

  @UpdateProductSuccess
  Scenario Outline: Update an existing product successfully
    Given I send a PUT request to update product with ID <id> with name "<name>", price <price>, category "<category>", and description "<description>"
    Then the response status should be 200
    And the response should contain name "<name>", price <price>, category "<category>", and description "<description>"

    Examples:
      | id | name       | price  | category    | description                |
      | 1  | Laptop Pro | 1500 | Electronics | Upgraded gaming laptop     |
      | 2  | Shirt XL   | 60   | Clothing    | Larger size cotton shirt   |

  @UpdateProductFailure
  Scenario Outline: Update a product that does not exist
    Given I send a PUT request to update product with ID <id> with name "<name>", price <price>, category "<category>", and description "<description>"
    Then the response status should be 404
    And the response should contain error message "Product not found"

    Examples:
      | id  | name    | price  | category | description     |
      | 999 | Watch X | 200  | Watches  | Smart watch pro |
