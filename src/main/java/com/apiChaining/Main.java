package com.apiChaining;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;

public class Main {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://mystoreapi.com/";
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(bodyParams()).when().post("catalog/product")
                .then().assertThat().statusCode(201)
                .extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response); //for parsing Json
        String productId = js.getString("id");

        String getProduct = given().log().all()
                .header("Content-Type", "application/json")
                .when().get("catalog/product/" + productId)
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        System.out.println(getProduct);
    }

    public static String bodyParams() {
        return """
                {
                  "name": "TShirt",
                  "price": 20,
                  "manufacturer": "Nike",
                  "category": "Clothing",
                  "description": "Plain T Shirt",
                  "tags": "sale"
                }
                """;
    }

}
