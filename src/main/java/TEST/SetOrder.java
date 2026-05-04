package TEST;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SetOrder  {
    static int id = 0;

    @Test(priority=1)
    public void setOrder(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        JSONObject requestbody = new JSONObject();
        requestbody.put("id","89");
        requestbody.put("petId","6969");
        requestbody.put("quantity","2");
        requestbody.put("shipDate","2026-04-26T16:40:18.190Z");
        requestbody.put("status","placed");
        requestbody.put("complete",true);

        Response response = RestAssured.given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(requestbody.toString())
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("status"), "placed");
        Assert.assertTrue(response.jsonPath().getBoolean("complete"));

    }
    @Test(priority=2)
    public void getOrder2(){

        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
        Response response = RestAssured
                .given()
                .header("accept", "application/json")
                .when()
                .get("/store/order/"+id)
                .then()
                .log().all()
                .extract().response();


    }

}
