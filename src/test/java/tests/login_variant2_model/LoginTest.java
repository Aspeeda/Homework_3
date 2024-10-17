package tests.login_variant2_model;

import model.LoginBodyModel;
import model.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.LoginSpecs.*;

public class LoginTest extends TestBase {

    /**
     * Реализация через модель данных
     */

    @Test
    @DisplayName("Авторизация с корректным логином и паролем")
    void loginTest() {

        /**Создаем экземпляр класса модели данных и вносим необходимые и корректные данные*/

        LoginBodyModel data = new LoginBodyModel();
        data.setUsername("Ultramarine");
        data.setPassword("AveImperator");

        /** Отправка запроса и получение ответа*/

        LoginResponseModel response = given(loginRequestSpec)
                .body(data)
                .when()
                .get("/user/login")
                .then()
                .spec(loginResponseSpec)
                .extract().as(LoginResponseModel.class);

        /** Проверяем полученные ответы*/

        assertThat(response.getCode()).isEqualTo(200L);
        assertThat(response.getMessage()).contains("logged in user session");
    }

    @Test
    @DisplayName("Авторизация без ввода пароля")
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
