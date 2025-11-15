
import domain.Pizza;
import domain.factory.PizzaFactory;
import domain.decorators.*;
import domain.utilities.*;
import domain.facade.PizzeriaFacade;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // =====================
        // 1) DECORATOR PATTERN
        // =====================
        System.out.println("=== DECORATOR PATTERN ===");

        Pizza margherita = PizzaFactory.createPizza("margherita");

        Pizza decorated = new ExtraCheese(
                                new ExtraOlives(
                                    new StuffedCrust(margherita)
                                )
                           );

        decorated.prepare();
        decorated.bake();
        decorated.cut();
        decorated.box();

        // =====================
        // 2) ADAPTER PATTERN
        // =====================
        System.out.println("\n=== ADAPTER PATTERN ===");

        Pizza rancho = PizzaFactory.createPizza("rancho");
        Oven oven = new LegacyOvenAdapter(new LegacyOven());

        oven.preheat(220);
        oven.bake(rancho, 15);

        // =====================
        // 3) FACADE PATTERN
        // =====================
        System.out.println("\n=== FACADE PATTERN ===");

        PizzeriaFacade facade = new PizzeriaFacade();

        System.out.println("\n-- Simple order --");
        facade.orderSimple("margherita");

        System.out.println("\n-- Order with toppings --");
        facade.orderWithToppings("rancho", List.of("cheese", "olives"));

        System.out.println("\n-- Custom order --");
        facade.orderCustom("Large", "Thin Crust", true, true, false);

        System.out.println("\n-- All orders saved in OrderManager (Singleton) --");
        facade.printOrders();
    }
}
