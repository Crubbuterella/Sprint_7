package clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import dataproviders.Courier;

import static io.restassured.RestAssured.given;

public class CourierClient extends Constants {

    @Step("Create new courier. POST /api/v1/courier")
    public ValidatableResponse createCourier(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Constants.CREATE_COURIER_ENDPOINT)
                .then();
    }

    @Step("Login courier. POST /api/v1/courier/login")
    public ValidatableResponse loginCourier(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Constants.LOGIN_COURIER_ENDPOINT)
                .then();
    }

    @Step("Delete courier with id")
    public void deleteCourier(int id) {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete(Constants.DELETE_COURIER_ENDPOINT + id)
                .then();
    }
}

