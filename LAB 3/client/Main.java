import domain.Pizza;
import domain.factory.PizzaFactory;
import domain.decorators.*;
import domain.utilities.*;

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
    }
}
