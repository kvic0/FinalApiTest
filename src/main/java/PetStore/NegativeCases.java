package PetStore;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class NegativeCases {
    @DataProvider(name= "negativeData")
    public Object[][] getOrderData(){
        return new Object[][]{
                {"test",5,2,"2024-05-20T10:00:00.000+0000","placed",true},
                {0,"test",2,"2026-05-03T16:59:42.705Z","placed",true},
                {0,1,"test","2026-05-03T16:59:42.705Z","placed",true},
        };
    }
    @Test(dataProvider = "negativeData",description = "Petstore create order with invalid data")
    public void CreateInvalidOrder(Object id,Object petId,Object quantity,String shipDate,String status,boolean complete) {
        JSONObject requestbody = new JSONObject();
        requestbody.put("id", id);
        requestbody.put("petId", petId);
        requestbody.put("quantity", quantity);
        requestbody.put("shipDate", shipDate);
        requestbody.put("status", status);
        requestbody.put("complete", complete);

        Response response = given()
                .baseUri("https://petstore.swagger.io/v2")
                .contentType(ContentType.JSON)
                .body(requestbody.toString())
                .filter(new AllureRestAssured())
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 500);
        Assert.assertEquals(response.jsonPath().getString("message"),"something bad happened");


    }


}
