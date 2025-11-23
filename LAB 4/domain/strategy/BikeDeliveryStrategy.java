package domain.strategy;

public class BikeDeliveryStrategy implements DeliveryStrategy {
    @Override
    public void deliver(String orderName) {
        System.out.println("[Delivery-Bike] Delivering order \"" + orderName + "\" by bike.");
    }
}
