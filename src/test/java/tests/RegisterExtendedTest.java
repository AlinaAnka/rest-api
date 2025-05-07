package tests;

import io.restassured.RestAssured;
import models.lombok.RegisterErrorModel;
import models.lombok.RegisterBodyModel;
import models.lombok.RegisterResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RegisterSpec.*;

public class RegisterExtendedTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void successfulRegisterTest() {

        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        RegisterResponseModel response = step("Successful registration", () -> {
            return given(registerRequestSpec)
                    .body(registerData)
                    .when()
                    .post("/register")
                    .then()
                    .spec(successfulRegisterResponse)
                    .extract().as(RegisterResponseModel.class);
        });
        step("Check registration", () -> {
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
            assertEquals("4", response.getId());
        });
    }

    @Test
    void unsuccessfulRegisterTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail("");
        registerData.setPassword("");

        RegisterErrorModel response = step("Unsuccessful registration", () ->
                given(registerRequestSpec)
                        .body(registerData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(unsuccessfulRegisterResponse)
                        .extract().as(RegisterErrorModel.class));
        step("Check unsuccessful registration", () ->
                assertEquals("Missing email or username", response.getError()));
    }

    @Test
    void missingPasswordRegisterTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("");

        RegisterErrorModel response = step("Missing password", () ->
                given(registerRequestSpec)
                        .body(registerData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(unsuccessfulRegisterResponse)
                        .extract().as(RegisterErrorModel.class));
        step("Check missing password", () ->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    void missingEmailRegisterTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail("");
        registerData.setPassword("pistol");

        RegisterErrorModel response = step("Missing email", () ->
                given(registerRequestSpec)
                        .body(registerData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(unsuccessfulRegisterResponse)
                        .extract().as(RegisterErrorModel.class));
        step("Check missing email", () ->
                assertEquals("Missing email or username", response.getError()));
    }

    @Test
    void wrongEmailRegisterTest() {
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail("holt55@reqres.in");
        registerData.setPassword("pistol");

        RegisterErrorModel response = step("Wrong email", () ->
                given(registerRequestSpec)
                        .body(registerData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(unsuccessfulRegisterResponse)
                        .extract().as(RegisterErrorModel.class));
        step("Check wrong email", () ->
                assertEquals("Note: Only defined users succeed registration", response.getError()));
    }
}

