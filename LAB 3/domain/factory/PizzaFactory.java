import domain.Pizza;
import domain.Barbeque;
import domain.Margherita;
import domain.Rancho;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PizzaFactory {
    private static final Map<String, Supplier<Pizza>> pizzaMap = new HashMap<>();

    static {
        pizzaMap.put("rancho", Rancho::new);
        pizzaMap.put("margherita", Margherita::new);
        pizzaMap.put("barbeque", Barbeque::new);
    }

    public static Pizza createPizza(String type) {
        if (type == null) {
            System.out.println("Pizza type cannot be null");
            return null;
        }

        Supplier<Pizza> pizzaSupplier = pizzaMap.get(type.toLowerCase());
        
        if (pizzaSupplier != null) {
            return pizzaSupplier.get();
        } else {
            System.out.println("Invalid pizza type: " + type);
            return null;
        }
    }
}
