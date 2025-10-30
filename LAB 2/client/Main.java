package client;

import domain.Pizza;
import factory.PizzaFactory;
import builder.CustomPizzaBuilder;
import singleton.OrderManager;

public class Main {
    public static void main(String[] args) {
        // Factory Method Pattern
        System.out.println("Pizza Factory Method Pattern:");
        Pizza rancho = PizzaFactory.createPizza("rancho");
        Pizza margherita = PizzaFactory.createPizza("margherita");
        Pizza barbeque = PizzaFactory.createPizza("barbeque");

        rancho.prepare();
        margherita.prepare();
        barbeque.prepare();

        // Singleton Pattern
        System.out.println("\nOrder Manager Singleton Pattern:");
        OrderManager orderManager = OrderManager.getInstance();
        orderManager.addOrder(rancho);
        orderManager.addOrder(margherita);
        orderManager.addOrder(barbeque);

        orderManager.showOrders();

        // Builder Pattern
        System.out.println("\nCustom Pizza Builder Pattern:");
        CustomPizzaBuilder customPizza = new CustomPizzaBuilder.PizzaBuilder()
                .setSize("Large")
                .setCrustType("Thin Crust")
                .addExtraCheese()
                .addExtraMeat()
                .addExtraPepperoni()
                .addExtraOlives()
                .build();
        customPizza.displayPizza();
    }
}
