import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegisterTest {

    private static final String API_KEY = "reqres-free-v1";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void successfulRegisterTest() {
        String registerData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .header("x-api-key", API_KEY)
                .body(registerData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulRegisterTest() {
        String registerData = "{}";

        given()
                .header("x-api-key", API_KEY)
                .body(registerData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));


    }
    @Test
    void missingPasswordRegisterTest() {
        String registerData = "{\"email\": \"eve.holt@reqres.in\"}";

        given()
                .header("x-api-key", API_KEY)
                .body(registerData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
        }

    @Test
    void wrongPasswordRegisterTest() {
        String registerData = "{\"email\": \"holt55@reqres.in\", \"password\": \"pistol55\"}";

        given()
                .header("x-api-key", API_KEY)
                .body(registerData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    void wrongEmailRegisterTest() {
        String registerData = "{\"email\": \"holt55@reqres.in\", \"password\": \"pistol\"}";

        given()
                .header("x-api-key", API_KEY)
                .body(registerData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    void missingEmailRegisterTest() {
        String registerData = "{\"password\": \"pistol\"}";

        given()
                .header("x-api-key", API_KEY)
                .body(registerData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }
}
