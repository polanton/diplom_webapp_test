package utils;

import configs.Configs;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.CreateUserRequest;
import model.LoginRequest;
import model.User;

import static io.restassured.RestAssured.given;

public class SendRequest {



    @Step("отправка запроса на создание пользователя")
    public static Response sendCreateUser(User user) {

        CreateUserRequest requestJson = new CreateUserRequest(user.getEmail(),user.getPassword(),user.getName());
        return given()
                .header("Content-type", "application/json")
                .body(requestJson)
                .when()
                .post(Configs.CREATE_USER_URL);
    }

    @Step("отправка запроса на логин")
    public static Response sendLoginUser(User user) {
        LoginRequest requestJson = new LoginRequest(user.getEmail(), user.getPassword());
        return given()
                .header("Content-type", "application/json")
                .body(requestJson)
                .when()
                .post(Configs.LOGIN_URL);
    }

    @Step("отправка запроса на удаление пользователя")
    public static Response sendDeleteUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", user.getToken())
                .when()
                .delete(Configs.DELETE_USER_URL);
    }

}
