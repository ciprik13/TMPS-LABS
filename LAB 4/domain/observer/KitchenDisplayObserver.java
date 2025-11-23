package domain.observer;

public class KitchenDisplayObserver implements OrderObserver {
    @Override
    public void onOrderAdded(String orderName) {
        System.out.println("[Kitchen] New order received: " + orderName);
    }
}
