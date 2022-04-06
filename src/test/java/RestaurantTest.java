import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    RestaurantService service = new RestaurantService();
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        restaurant = createTestingRestaurant();
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:32:00"),LocalTime.parse("10:30:01"),LocalTime.parse("21:59:59"));
        assertTrue(restaurant.isRestaurantOpen());
        assertTrue(restaurant.isRestaurantOpen());
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        restaurant = createTestingRestaurant();
        Restaurant mockedRestaurant = Mockito.spy(restaurant);

        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("08:32:00"),LocalTime.parse("10:29:59"),LocalTime.parse("22:00:01"));
        assertFalse(mockedRestaurant.isRestaurantOpen());
        assertFalse(mockedRestaurant.isRestaurantOpen());
        assertFalse(mockedRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        restaurant =createTestingRestaurant();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        restaurant =createTestingRestaurant();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        restaurant = createTestingRestaurant();
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    @Test
    public void orderTotal_must_not_return_only_lastItem_price() {

        restaurant = createTestingRestaurant();
        restaurant.addToMenu("Honey Chilly potato",100);
        restaurant.addToMenu("Sandwich", 150);
        List <Item> orderList = new ArrayList<>();
        orderList.add(restaurant.orderItem("Sandwich"));
        orderList.add(restaurant.orderItem("Sweet corn soup"));
        orderList.add(restaurant.orderItem("Honey Chilly potato"));

        int totalOrder = 0;
        for(Item item : orderList){
            totalOrder += item.getPrice();
        }
        assertNotEquals(totalOrder, orderList.get(orderList.size()-1).getPrice());
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public Restaurant createTestingRestaurant(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Restaurant restro =  new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restro.addToMenu("Sweet corn soup",119);
        restro.addToMenu("Vegetable lasagne", 269);
        return restro;
    }
}