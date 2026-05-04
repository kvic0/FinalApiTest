package PetStore;

import User.BeforeTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.qameta.allure.restassured.AllureRestAssured;


public class PetstoreApiTestsMethods {

    @Test()
    public void createOrderFromDbDataTest(int id) {
        OrderDataLombok dbData = DatabaseUtils.getOrderFromDb(id);
        Response response = given()
                .baseUri("https://petstore.swagger.io/v2")
                .contentType(ContentType.JSON)
                .body(dbData)
                .filter(new AllureRestAssured())
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract()
                .response();
        Assert.assertEquals(response.getStatusCode(), 200);
        OrderDataLombok Response = response.as(OrderDataLombok.class);
        Assert.assertEquals(Response.getId(), dbData.getId(), "ID Doesn't match!");
        Assert.assertEquals(Response.getPetId(), dbData.getPetId(), "PetID Doesn't match!");
        Assert.assertEquals(Response.getQuantity(), dbData.getQuantity(), "Quantity Doesn't match!");
        Assert.assertEquals(Response.getStatus(), dbData.getStatus(), "Status Doesn't match!");
    }
    @Test
    public void getOrderFromDbDataTest(int id,int exceptedStatusCode,String expectedMassage) {
        OrderDataLombok dbData = DatabaseUtils.getOrderFromDb(id);


        Response response = RestAssured
                .given()
                .baseUri("https://petstore.swagger.io/v2") // დავამატეთ baseUri თანმიმდევრობისთვის
                .header("accept", "application/json")
                .filter(new AllureRestAssured())
                .when()
                .get("/store/order/" + dbData.getId())
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), exceptedStatusCode);
        if (exceptedStatusCode == 200) {
            OrderDataLombok Response = response.as(OrderDataLombok.class);
            Assert.assertEquals(Response.getId(), dbData.getId(), "ID Doesn't match!");
            Assert.assertEquals(Response.getPetId(), dbData.getPetId(), "PetID Doesn't match!");
            Assert.assertEquals(Response.getQuantity(), dbData.getQuantity(), "Quantity Doesn't match!");
            Assert.assertEquals(Response.getStatus(), dbData.getStatus(), "Status Doesn't match!");

        }
        else {
            Assert.assertEquals(response.jsonPath().getString("message"), expectedMassage,"Message is incorrect!");

        }

    }
    @Test
    public void deleteOrderFromDbDataTest(int id,int expectedStatuseCode,String expectedMessage) {
        OrderDataLombok dbData = DatabaseUtils.getOrderFromDb(id);

        Response response = RestAssured
                .given()
                .baseUri("https://petstore.swagger.io/v2")
                .header("accept", "application/json")
                .filter(new AllureRestAssured())
                .when()
                .delete("/store/order/" + dbData.getId())
                .then()
                .log().all()
                .extract()
                .response();
        Assert.assertEquals(response.getStatusCode(), expectedStatuseCode);
        if(expectedMessage != null) {
            Assert.assertEquals(response.jsonPath().getString("message"), expectedMessage,"Message is incorrect!");
        }
    }

}


