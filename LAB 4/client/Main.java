import domain.facade.PizzeriaFacade;
import domain.singleton.OrderManager;

// Observer
import domain.observer.KitchenDisplayObserver;
import domain.observer.BillingObserver;

// Strategy
import domain.strategy.BikeDeliveryStrategy;

// Command
import domain.command.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== LAB 4 — BEHAVIORAL DESIGN PATTERNS ===");

        // OBSERVER
        OrderManager manager = OrderManager.getInstance();
        manager.addObserver(new KitchenDisplayObserver());
        manager.addObserver(new BillingObserver());

        // FACADE + STRATEGY
        PizzeriaFacade facade = new PizzeriaFacade();
        facade.setDeliveryStrategy(new BikeDeliveryStrategy());

        // COMMAND PATTERN — USING YOUR COMMANDS
        OrderInvoker invoker = new OrderInvoker();

        System.out.println("\n-- Command: Simple Order --");
        invoker.execute(new SimpleOrderCommand(facade, "margherita"));

        System.out.println("\n-- Command: Order with Toppings --");
        invoker.execute(new ToppingsOrderCommand(facade, "rancho", List.of("cheese", "olives")));

        System.out.println("\n-- Command: Custom Order --");
        invoker.execute(new CustomOrderCommand(
                facade,
                "Large",
                "Thin Crust",
                true,   // cheese
                true,   // pepperoni
                false   // mushrooms
        ));
    }
}
