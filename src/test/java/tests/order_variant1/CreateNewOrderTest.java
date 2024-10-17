package tests.order_variant1;

import dto.order.OrderDTO;
import dto.order.OrderResponseDTO;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import specs.CreateOrderSpecs;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateNewOrderTest extends BaseTest {


    /**
     * Проверка создания заказа при корректном заполнении всех полей
     */

    @Test
    public void checkCreateOrder() {
        Date date = new Date();

        /** Заполнение необходимыми данными */

        OrderDTO newOrder = OrderDTO
                .builder()
                .id(1)
                .petId(12)
                .quantity(4)
                .shipDate(date)
                .status("approved")
                .build();

        /** Отправка запроса на создание заказа и валидация схемы*/

        createSpecs.createOrder(newOrder)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateOrder.json"));

        /** Преобразование ответа в объект OrderResponseDTO*/

        OrderResponseDTO response = CreateOrderSpecs.createOrder(newOrder)
                .extract().body().as(OrderResponseDTO.class);
        assertAll("Order Response Assertions",
                () -> assertEquals(1, response.getId(), "Incorrect ID"),
                () -> assertEquals(12, response.getPetId(), "Incorrect petID"),
                () -> assertEquals(4, response.getQuantity(), "Incorrect quantity"),
                () -> assertEquals(date, response.getShipDate(), "Incorrect shipDate"),
                () -> assertEquals("approved", response.getStatus(), "Incorrect Status"));
    }

    /**
     * Проверка создания заказа при некорректном заполнении всех полей
     */

    @Test
    public void checkCreateOrderInvalidValue() {

        /** Заполнение некорректными значениями */

        OrderDTO invalidOrder = OrderDTO
                .builder()
                .id(0)
                .petId(-1)
                .quantity(0)
                .shipDate(null)
                .status("1")
                .complete(true)
                .build();

        /** Отправка запроса на создание заказа */

        Response response = createSpecs.createOrder(invalidOrder)
                .extract().response();

        /** Проверка кода ответа на ошибку */

        assertEquals(400, response.getStatusCode(), "Invalid Order");

        // Проверка наличия сообщения об ошибке в ответе
        String errorMessage = response.jsonPath().getString("message");
        assertEquals("Invalid Order", errorMessage, "Expected error message for invalid order data");
    }



           /*petStoreApi.createUser(user1)
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"))
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"))
                .body("message", equalTo("0"));*/

       /* UserResponseDTO response = petStoreApi.createUser(user1)
                .extract().body().as(UserResponseDTO.class);

        Assertions.assertEquals(200L, response.getCode(), "Incorrect code");
        Assertions.assertEquals("unknown", response.getType(), "Incorrect type");
        Assertions.assertEquals("0", response.getMessage(), "Incorrect message");
*/
//        String actualMessage = createSpecs.createOrder(newOrder)
//                .extract().body().jsonPath().get("message");

//        Assertions.assertEquals("0", actualMessage, "Incorrect message");

}
