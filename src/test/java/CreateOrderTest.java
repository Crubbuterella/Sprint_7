import clients.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import dataproviders.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    Order order;
    OrderClient orderClient = new OrderClient();
    int track;

    public CreateOrderTest(Order order) {

        this.order = order;
    }

    @Before
    public void setUp(){

        orderClient.setUp();
    }

    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {new Order("Егор","Темнов","Красная площадь, д.19, кв.18","Преображенская площадь","+79111123344",2,"2023-06-05","комментарий",new String[]{"BLACK"})},
                {new Order("Вован","Светлов","Синяя площадь, д.10, кв.27","Сокольники","81112223344",2,"2023-06-06","comment",new String[]{"GREY"})},
                {new Order("Ира","Попова","г. Москва, ул. Ленина, д.50","ВДНХ","89123334455",3,"2023-06-07","-",new String[]{"BLACK","GREY"})},
                {new Order("Степан","Гусев","г. Москва, ул. Златова, д.25","ВДНХ","89556667788",4,"2023-06-08","+",new String[]{})}
        };
    }

    @Test
    @DisplayName("Check create a new order")
    @Description("Create some orders with positive params")
    public void checkCreateOrder(){
        ValidatableResponse response = orderClient.createOrderRequest(order);
        response
                .statusCode(SC_CREATED)
                .and()
                .assertThat().body("track", notNullValue());
    }
}

