package clients;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import dataproviders.Order;

import static io.restassured.RestAssured.given;
public class OrderClient extends Constants {

    @Step("Create order. POST /api/v1/orders")
    public ValidatableResponse createOrderRequest(Order order){
               return given()
                       .header("Content-type", "application/json")
                       .body(order)
                       .when()
                       .post(Constants.CREATE_ORDER_ENDPOINT)
                       .then();

    }

    @Step("Get order list. GET /api/v1/orders")
    public Response getOrdersRequest(){
        return  given().
                get(Constants.CREATE_ORDER_ENDPOINT);

    }
}


