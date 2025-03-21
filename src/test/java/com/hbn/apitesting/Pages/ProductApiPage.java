package com.hbn.apitesting.Pages;

import com.hbn.apitesting.entity.Product;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProductApiPage {
    private static final String BASE_URL = "http://localhost:8080/api/products";

//    public Response createProduct(String name, int price, String category, String description) throws JSONException {
//        RequestSpecification request = given();
//        request.header("Content-Type", "application/json");
//
//        JSONObject requestParams = new JSONObject();
//        requestParams.put("name", name);
//        requestParams.put("price", price);
//        requestParams.put("category", category);
//        requestParams.put("description", description);
//
//        request.body(requestParams.toString());
//        return request.post(BASE_URL);
//    }

    public Response createProduct(String name, Float price, String category, String description) {
        Product product = new Product(name, price, category, description);

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post(BASE_URL)
                .then()
                .extract()
                .response();
    }


    public Response getProductById(int id) {
        return given()
                .header("Content-Type", "application/json")
                .get(BASE_URL + "/" + id);
    }

    public Response deleteProductById(int id) {
        return given()
                .header("Content-Type", "application/json")
                .delete(BASE_URL + "/" + id);
    }

    public Response updateProduct(int id, String name, Float price, String category, String description) {
        Map<String, Object> requestBody = Map.of(
                "name", name,
                "price", price,
                "category", category,
                "description", description
        );

        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(BASE_URL + "/" + id)
                .then()
                .extract()
                .response();
    }




}

