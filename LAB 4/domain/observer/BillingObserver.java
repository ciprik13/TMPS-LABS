package domain.observer;

public class BillingObserver implements OrderObserver {
    @Override
    public void onOrderAdded(String orderName) {
        System.out.println("[Billing] Registering order for billing: " + orderName);
    }
}
