package domain.strategy;

public class CarDeliveryStrategy implements DeliveryStrategy {
    @Override
    public void deliver(String orderName) {
        System.out.println("[Delivery-Car] Delivering order \"" + orderName + "\" by car.");
    }
}
