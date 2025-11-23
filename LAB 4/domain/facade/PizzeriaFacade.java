package domain.facade;

import domain.Pizza;
import domain.factory.PizzaFactory;
import domain.decorators.*;
import domain.singleton.OrderManager;
import domain.utilities.*;
import domain.builder.CustomPizza;

import domain.strategy.DeliveryStrategy;
import domain.strategy.PickupStrategy;

import java.util.List;

public class PizzeriaFacade {

    private final Oven oven;
    private final OrderManager orderManager;

    private DeliveryStrategy deliveryStrategy = new PickupStrategy();

    public void setDeliveryStrategy(DeliveryStrategy strategy) {
        this.deliveryStrategy = strategy;
    }

    public PizzeriaFacade() {
        this.oven = new LegacyOvenAdapter(new LegacyOven());
        this.orderManager = OrderManager.getInstance();
    }

    public Pizza orderSimple(String type) {
        Pizza pizza = PizzaFactory.createPizza(type);

        if (pizza == null) {
            System.out.println("Invalid pizza type!");
            return null;
        }

        oven.preheat(220);
        oven.bake(pizza, 15);

        orderManager.addOrder(pizza);

        deliveryStrategy.deliver(pizza.getClass().getSimpleName());

        return pizza;
    }

    public Pizza orderWithToppings(String type, List<String> toppings) {
        Pizza pizza = PizzaFactory.createPizza(type);

        if (pizza == null) {
            System.out.println("Invalid pizza type!");
            return null;
        }

        for (String t : toppings) {
            switch (t.toLowerCase()) {
                case "cheese": pizza = new ExtraCheese(pizza); break;
                case "olives": pizza = new ExtraOlives(pizza); break;
                case "stuffed": pizza = new StuffedCrust(pizza); break;
                default:
                    System.out.println("Unknown topping: " + t);
            }
        }

        oven.preheat(220);
        oven.bake(pizza, 18);

        orderManager.addOrder(pizza);

        deliveryStrategy.deliver(pizza.getClass().getSimpleName());

        return pizza;
    }

    public CustomPizza orderCustom(String size, String crust, boolean cheese, boolean pepperoni, boolean mushrooms) {

        CustomPizza.PizzaBuilder builder = new CustomPizza.PizzaBuilder()
                .setSize(size)
                .setCrustType(crust);

        if (cheese) builder.addExtraCheese();
        if (pepperoni) builder.addExtraPepperoni();
        if (mushrooms) builder.addExtraMushrooms();

        CustomPizza pizza = builder.build();

        System.out.println("\n[Custom Pizza Created]");
        pizza.displayPizza();

        deliveryStrategy.deliver("CustomPizza");

        return pizza;
    }

    public void printOrders() {
        orderManager.showOrders();
    }
}
