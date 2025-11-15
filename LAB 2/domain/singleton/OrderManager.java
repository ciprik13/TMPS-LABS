package domain.singleton;

import java.util.ArrayList;
import java.util.List;

import domain.Pizza;

public class OrderManager {
    private static OrderManager instance;

    private List<Pizza> orders;

    private OrderManager() {
        orders = new ArrayList<>();
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void addOrder(Pizza pizza) {
        orders.add(pizza);
        System.out.println("Order added: " + pizza.getClass().getSimpleName());
    }
    
    public void showOrders(){
        System.out.println("Current Orders:");
        for(Pizza pizza : orders){
            System.out.println("- " + pizza.getClass().getSimpleName());
        }
    }
}
