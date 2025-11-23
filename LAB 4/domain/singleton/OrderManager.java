package domain.singleton;

import java.util.ArrayList;
import java.util.List;

import domain.Pizza;
import domain.observer.OrderObserver;

public class OrderManager {
    private static OrderManager instance;

    private List<Pizza> orders;
    private List<OrderObserver> observers = new ArrayList<>();

    private OrderManager() {
        orders = new ArrayList<>();
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    private void notifyOrderAdded(String orderName) {
        for (OrderObserver observer : observers) {
            observer.onOrderAdded(orderName);
        }
    }

    public void addOrder(Pizza pizza) {
        orders.add(pizza);
        String name = pizza.getClass().getSimpleName();
        System.out.println("Order added: " + name);
        notifyOrderAdded(name);
    }
    
    public void showOrders(){
        System.out.println("Current Orders:");
        for(Pizza pizza : orders){
            System.out.println("- " + pizza.getClass().getSimpleName());
        }
    }
}
