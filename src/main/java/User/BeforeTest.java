package User;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BeforeTest {
    @BeforeClass(description = "Before Class Set Up")
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    public RequestSpecification requestSpec() {
        return RestAssured
                .given()
                .baseUri(RestAssured.baseURI)
                .header("accept", "application/json")
                .header("Content-Type", "application/json");
    }
}