package domain.strategy;

public class PickupStrategy implements DeliveryStrategy {
    @Override
    public void deliver(String orderName) {
        System.out.println("[Pickup] Customer will pick up the order \"" + orderName + "\" from the pizzeria.");
    }
}
