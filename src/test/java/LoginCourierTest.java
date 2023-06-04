import clients.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import dataproviders.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest{
    CourierClient courierClient = new CourierClient();

    Courier courier;
    private int id;

   @Before
    public void setUp(){
        courierClient.setUp();
        courier = ((new Courier("TesterIra033","54321","Ира")));
    }

    @Test
    @DisplayName("Check successful authorization")
    @Description("Authorization with correct data is successful")
    public void checkLoginCourier(){
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.loginCourier(courier);
        id = response.extract().path("id");
        response
                .assertThat().body("id", notNullValue())
                .and().statusCode(SC_OK);
    }
    @Test
    @DisplayName("Check authorization without login (400)")
    @Description("Authorization without login isn't possible")
    public void checkLoginWithoutLogin(){
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.loginCourier(courier);
        id = response.extract().path("id");
        ValidatableResponse responseLogin = courierClient.loginCourier((new Courier("","54321")));
        responseLogin
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check authorization without password (400)")
    @Description("Authorization without password isn't possible")
    public void checkLoginWithoutPassword(){;
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.loginCourier(courier);
        id = response.extract().path("id");
        ValidatableResponse responseLogin = courierClient.loginCourier((new Courier("TesterIra033","")));
        responseLogin
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check authorization non-existent courier (404)")
    @Description("Authorization non-existent courier isn't possible")
    public void checkLoginNonExisting(){
       ValidatableResponse responseLogin = courierClient.loginCourier(courier);
       responseLogin
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Check authorization with incorrect login (404)")
    @Description("Authorization with incorrect login isn't possible")
    public void checkLoginIncorrectLogin() {
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.loginCourier(courier);
        id = response.extract().path("id");
        ValidatableResponse responseLogin = courierClient.loginCourier((new Courier("TesterIra099","54321")));
        responseLogin
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Check authorization with incorrect password (404)")
    @Description("Authorization with incorrect password isn't possible")
    public void checkLoginIncorrectPassword() {
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.loginCourier(courier);
        id = response.extract().path("id");
        ValidatableResponse responseLogin = courierClient.loginCourier((new Courier("TesterIra033", "12345")));
        responseLogin
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }
    @After
    public void cleanUp() {
       if (id != 0) {
           courierClient.deleteCourier(id);
       }
   }

}
