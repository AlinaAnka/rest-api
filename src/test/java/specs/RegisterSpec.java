package specs;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class RegisterSpec {

    public static RequestSpecification registerRequestSpec = with()
            .filter(withCustomTemplates())
            .header("x-api-key", "reqres-free-v1")
            .contentType(JSON)
            .log().all();

    public static ResponseSpecification registerResponseSpec (int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .log(STATUS)
                .log(BODY)
                .build();
    }
}

