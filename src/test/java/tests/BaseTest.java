package tests;

import io.restassured.response.ValidatableResponse;
import specs.OrderSpecs;

public class BaseTest {
    protected OrderSpecs createSpecs = new OrderSpecs();


    public void orderCleanup(int createdOrderId) {
        if (createdOrderId > 0) {
            ValidatableResponse responseD = OrderSpecs.deleteOrder(createdOrderId);
            responseD.statusCode(200);
        }
    }

        public void userCleanup(int userId) {
            if (userId > 0) {
                ValidatableResponse responseD = OrderSpecs.deleteOrder(userId);
                responseD.statusCode(200);
            }
    }
}
