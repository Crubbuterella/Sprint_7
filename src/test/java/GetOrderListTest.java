import clients.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;
public class GetOrderListTest {
    OrderClient clientOrder = new OrderClient();

    @Before
    public void setUp(){
        clientOrder.setUp();}

    @Test
    @DisplayName("Check get order list (200)")
    @Description("Successful response of the list of orders")
    public void checkGetOrderList(){
        clientOrder.getOrdersRequest()
                .then().statusCode(SC_OK)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}

