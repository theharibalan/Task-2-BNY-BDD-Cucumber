package com.hbn.apitesting.StepDefinitions;

import com.hbn.apitesting.Pages.ProductApiPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.json.JSONException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductApiStepDefinitions {
    private final ProductApiPage productApiPage = new ProductApiPage();
    private Response response;

    @Given("I send a POST request to create a product with name {string}, price {float}, category {string}, and description {string}")
    public void i_send_a_post_request_to_create_a_product_with_name_price_category_and_description(String name, Float price, String category, String description) {
        response = productApiPage.createProduct(name, price, category, description);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int expectedStatus) {
        int actualStatus = response.getStatusCode();
        assertEquals(expectedStatus, actualStatus);
    }

    @And("the response should contain name {string}, price {float}, category {string}, and description {string}")
    public void theResponseShouldContain(String expectedName, Float expectedPrice, String expectedCategory, String expectedDescription) {
        response.then()
                .body("name", equalTo(expectedName))
                .body("price", equalTo(expectedPrice)) // Ensure explicit float usage
                .body("category", equalTo(expectedCategory))
                .body("description", equalTo(expectedDescription));
    }

    @And("the response should contain error message {string}")
    public void the_response_should_contain_error_message(String expectedError) {
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains(expectedError));
    }

    @Given("I send a GET request for product with ID {int}")
    public void i_send_a_get_request_for_product_with_id(Integer id) {
        response = productApiPage.getProductById(id);
    }

    @Given("I send a DELETE request for product with ID {int}")
    public void i_send_a_delete_request_for_product_with_id(Integer id) {
        response = productApiPage.deleteProductById(id);
    }

    @Then("the response should contain message {string}")
    public void the_response_should_contain_message(String message) {
        assertEquals(message, response.jsonPath().getString("message"));
    }

    @Given("I send a PUT request to update product with ID {int} with name {string}, price {float}, category {string}, and description {string}")
    public void iSendAPUTRequestToUpdateProduct(int id, String name, Float price, String category, String description) throws JSONException {
        response = productApiPage.updateProduct(id, name, price, category, description);
    }
}
