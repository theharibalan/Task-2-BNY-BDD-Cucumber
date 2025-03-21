Feature: Product Deletion

  @DeleteProductSuccess
  Scenario Outline: Delete a product successfully by ID
    Given I send a DELETE request for product with ID <id>
    Then the response status should be <status>
    And the response should contain message "<message>"

    Examples:
      | id | status | message                      |
      | 1  | 200    | Product deleted successfully |
      | 2  | 200    | Product deleted successfully |

  @DeleteProductFailure
  Scenario Outline: Attempt to delete a non-existing product
    Given I send a DELETE request for product with ID <id>
    Then the response status should be <status>
    And the response should contain error message "<error>"

    Examples:
      | id   | status | error             |
      | 999  | 404    | Product not found |
      | 5000 | 404    | Product not found |
