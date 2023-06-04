import clients.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import dataproviders.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.equalTo;


public class CreateCourierTest {
    CourierClient courierClient = new CourierClient();

    Courier courier;
    private int id;
    @Before
    public void setUp(){
        courierClient.setUp();
    }
    @Test
    @DisplayName("Check create new courier")
    @Description("check status code is 201 and responseBody is OK")
    public void checkCreateCourier(){
        courier = new Courier("TesterIra055","12345","Ира");
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        createResponse
                .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED);
        ValidatableResponse loginResponse = courierClient.loginCourier(courier);
        id = loginResponse.extract().path("id");
    }
    @Test
    @DisplayName("Check create new courier without firstName")
    @Description("check status code is 201 and responseBody is OK")
    public void checkCreateCourierWithoutName() {
        courier = new Courier("TesterIra055","12345","");
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        createResponse
                .assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(SC_CREATED);
        ValidatableResponse loginResponse = courierClient.loginCourier(courier);
        id = loginResponse.extract().path("id");
    }


    @Test
    @DisplayName("Check create new courier without login")
    @Description("Check status code is 400 and responseBody is correct")
    public void checkCreateCourierWithoutLogin(){
        ValidatableResponse createResponse = courierClient.createCourier((new Courier("","12345","Ира")));
        createResponse
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Check create new courier without password")
    @Description("Check status code is 400 and responseBody is correct")
    public void checkCreateCourierWithoutPassword(){
        ValidatableResponse createResponse = courierClient.createCourier((new Courier("TesterIra044","","Ира")));
        createResponse
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
    @After
    public void cleanUp() {
        if (id != 0) {
            courierClient.deleteCourier(id);
        } }
}

