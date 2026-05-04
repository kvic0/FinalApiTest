package PetStore;

import org.testng.annotations.Test;

public class PetstoreApiTest {
    public int ID =1;
    PetstoreApiTestsMethods petstoreApiTests = new PetstoreApiTestsMethods();
    @Test(priority = 1,description = "PetStore Create order from DB")
    @io.qameta.allure.Step("Creating order for ID: {id}")
    public void createOrder(){
        petstoreApiTests.createOrderFromDbDataTest(ID);
    }
    @Test(priority = 2,description = "PetStore Read order ")
    public void getOrder(){
        petstoreApiTests.getOrderFromDbDataTest(ID,200,null);
    }
    @Test(priority = 3,description = "PetStore Delete order")
    public void deleteOrder(){
        petstoreApiTests.deleteOrderFromDbDataTest(ID,200,null);
    }
    @Test(priority = 4,description = "PetStore Delete order again")
    public void deleteOrderAgain() {
        petstoreApiTests.deleteOrderFromDbDataTest(ID, 404, "Order Not Found");

    }
    @Test(priority = 5,description = "PetStore Read deleted order")
    public void getDeletedOrder(){
        petstoreApiTests.getOrderFromDbDataTest(ID,404,"Order not found");
    }
}
