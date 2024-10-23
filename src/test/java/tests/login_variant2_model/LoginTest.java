package tests.login_variant2_model;

import model.LoginBodyModel;
import model.LoginResponseModel;
import model.UserModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.LoginSpecs.*;

public class LoginTest extends BaseTest {
    private int userId;

    @AfterEach
    public void cleanup() {
        userCleanup(userId);
    }

    /**
     * Реализация через модель данных
     */

//разлогирования нет, потому что удалить пользователя можно только будучи залогиненным

    @Test
    @DisplayName("Авторизация с корректным логином и паролем")
    void loginTest() {

        /**Создаем пользователя*/

        UserModel data = new UserModel();
        data.setId(1);
        data.setUsername("user1");
        data.setPassword("123");
        data.setEmail("123@email.com");
        data.setPhone("1234");
        data.setFirstName("user1");
        data.setLastName("userson");
        data.setUserStatus(1);

        LoginResponseModel response = given(loginRequestSpec)
                .body(data)
                .when()
                .post("/user")
                .then()
                .spec(createUserResponse)
                .extract().as(LoginResponseModel.class);

        assertThat(response.getCode()).isEqualTo(200L);

        /**Создаем экземпляр класса модели данных и вносим необходимые и корректные данные*/

        LoginBodyModel data1 = new LoginBodyModel();
        data1.setUsername("user1");
        data1.setPassword("123");

        /** Проверить существует ли указанный пользователь */

        UserModel existingUser = given(loginRequestSpec)
                .pathParam("username", data.getUsername())
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(200)
                .extract().as(UserModel.class);

        userId = existingUser.getId();

        assertThat(existingUser.getUsername()).isEqualTo(data.getUsername());

        /** Отправка запроса и получение ответа*/

        LoginResponseModel response1 = given(loginRequestSpec)
                .body(data)
                .when()
                .get("/user/login")
                .then()
                .spec(loginResponseSpec)
                .extract().as(LoginResponseModel.class);

        /** Проверяем полученные ответы*/
        assertThat(response1.getCode()).isEqualTo(200L);
        assertThat(response1.getMessage()).contains("logged in user session");
    }

    @Test
    @DisplayName("Авторизация незарегистрированным пользователем")
    void missingPasswordTest() {
        /**Создаем экземпляр класса модели данных и вносим необходимые данные, пропускаем пароль*/
        LoginBodyModel data = new LoginBodyModel();
        data.setUsername("Zus");
        data.setPassword("");

        /** Отправка запроса и получение ответа*/

        LoginResponseModel response = given(loginRequestSpec)
                .body(data)
                .when()
                .get("/user/login")
                .then()
                .spec(loginResponseSpecMissingData)
                .extract().as(LoginResponseModel.class);


        assertThat(response.getMessage()).contains("Invalid username/password supplied");
    }
}
