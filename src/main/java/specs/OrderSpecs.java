package specs;

import dto.order.OrderDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;

public class OrderSpecs {

    private static final String BASE_URI = System.getProperty("baseUri");
    private static final String ORDER_PATH = "/store/order";
    private static RequestSpecification spec;

    public OrderSpecs() {
        spec = with()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .log().all();
    }

    public static ValidatableResponse createOrder(OrderDTO OrderDTO) {

        return given(spec)
                .basePath(ORDER_PATH)
                .body(OrderDTO)
                .when()
                .post()
                .then()
                .log().all();
    }

    public static ValidatableResponse deleteOrder(int orderId) {

        return given(spec)
                .basePath(ORDER_PATH)
                .pathParam("orderId", orderId)
                .when()
                .delete("/{orderId}")
                .then()
                .log().all();
    }

    public static ValidatableResponse getOrderById(int orderId) {

        return given(spec)
                .basePath(ORDER_PATH)
                .pathParam("orderId", orderId)
                .when()
                .get("/{orderId}")
                .then()
                .log().all();
    }
}
