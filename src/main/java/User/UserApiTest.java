package User;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserApiTest extends BeforeTest {
    @Test(priority = 1, dataProvider = "userData", dataProviderClass = UserDataProvider.class)
    public void createUser(int id, String username, String firstname, String lastname, String email, String password, String phonenumber, int userStatus) {

        JSONObject requestbody = new JSONObject();
        requestbody.put("id", id);
        requestbody.put("username", username);
        requestbody.put("firstName", firstname);
        requestbody.put("lastName", lastname);
        requestbody.put("email", email);
        requestbody.put("password", password);
        requestbody.put("phone", phonenumber);
        requestbody.put("userStatus", userStatus);

        Response response = requestSpec()
                .given()
                .body(requestbody.toString())
                .filter(new AllureRestAssured())
                .when()
                .post("/user")
                .then()
                .log().all()
                .extract()
                .response();

        // შემოწმება (Assertion)
        Assert.assertEquals(response.getStatusCode(), 200, "სტატუს კოდი უნდა იყოს 200");
    }
    @Test(priority = 2, dataProvider = "userData", dataProviderClass = UserDataProvider.class)
    public void getUser(int id, String username, String firstname, String lastname, String email, String password, String phonenumber, int userStatus) {
        Response response = requestSpec()
                .given()
                .pathParam("userName", username)
                .filter(new AllureRestAssured())
                .when()
                .get("/user/{userName}")
                .then()
                .log().all()
                .extract()
                .response();
        Assert.assertEquals(response.jsonPath().getString("username"), username, "Username არ ემთხვევა!");
        Assert.assertEquals(response.jsonPath().getString("firstName"), firstname, "FirstName არ ემთხვევა!");
        Assert.assertEquals(response.jsonPath().getString("lastName"), lastname, "LastName არ ემთხვევა!");
        Assert.assertEquals(response.jsonPath().getString("email"), email, "Email არ ემთხვევა!");
        Assert.assertEquals(response.jsonPath().getString("password"), password, "Password არ ემთხვევა!");
        Assert.assertEquals(response.jsonPath().getString("phone"), phonenumber, "Phone არ ემთხვევა!");
        Assert.assertEquals(response.jsonPath().getInt("userStatus"), userStatus, "UserStatus არ ემთხვევა!");
    }
    @Test(priority = 3, dataProvider = "userData", dataProviderClass = UserDataProvider.class)
    public void updateUser(int id, String username, String firstname, String lastname, String email, String password, String phonenumber, int userStatus) {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";


        JSONObject updateBody = new JSONObject();
        updateBody.put("id", id);
        updateBody.put("username", username);
        updateBody.put("firstName", firstname);
        updateBody.put("lastName", lastname + "_updated");
        updateBody.put("email", email);
        updateBody.put("password", password);
        updateBody.put("phone", "995" + phonenumber);
        updateBody.put("userStatus", userStatus);

        Response response = requestSpec()
                .given()
                .pathParam("userName", username)
                .body(updateBody.toString())
                .filter(new AllureRestAssured())
                .when()
                .put("/user/{userName}")
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200, "მომხმარებლის განახლება ვერ მოხერხდა!");
    }
    @Test(priority = 4, dataProvider = "userData", dataProviderClass = UserDataProvider.class)
    public void checkUserUpdate(int id, String username, String firstname, String lastname, String email, String password, String phonenumber, int userStatus) {

        String expectedLastName = lastname + "_updated";
        String expectedPhone = "995" + phonenumber;

        Response response = requestSpec()
                .given()
                .pathParam("userName", username)
                .when()
                .filter(new AllureRestAssured())
                .get("/user/{userName}")
                .then()
                .log().all()
                .extract()
                .response();
        Assert.assertEquals(response.jsonPath().getString("lastName"),expectedLastName);
        Assert.assertEquals(response.jsonPath().getString("phone"),expectedPhone);
    }
    @Test(priority = 5, dataProvider = "userData", dataProviderClass = UserDataProvider.class)
    public void loginUser(int id, String username, String firstname, String lastname, String email, String password, String phonenumber, int userStatus) {

        Response response = requestSpec()
                .queryParam("username", username)
                .queryParam("password", password)
                .filter(new AllureRestAssured())
                .when()
                .get("/user/login")
                .then()
                .log().all()
                .extract().response();

        // a. მეთოდმა დააბრუნა სტატუს კოდი 200
        Assert.assertEquals(response.getStatusCode(), 200, "Login ვერ მოხერხდა!");
        Assert.assertNotNull(response.jsonPath().getString("message"), "Message არის null!");
    }
    @Test(priority = 6, dataProvider = "userData", dataProviderClass = UserDataProvider.class)
    public void logoutUser(int id, String username, String firstname, String lastname, String email, String password, String phonenumber, int userStatus) {

        Response response = requestSpec()
                .filter(new AllureRestAssured())
                .when()
                .get("/user/logout")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("message"), "ok");
    }
    @Test(priority = 7, dataProvider = "userData", dataProviderClass = UserDataProvider.class)
    public void deleteUser(int id, String username, String firstname, String lastname, String email, String password, String phonenumber, int userStatus) {

        Response response = requestSpec()
                .pathParam("userName", username)
                .filter(new AllureRestAssured())
                .when()
                .delete("/user/{userName}")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("message"), username);
        System.out.println("მომხმარებელი " + username + " წაიშალა.");
    }




}
