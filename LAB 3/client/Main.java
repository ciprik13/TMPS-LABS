import domain.Pizza;
import domain.factory.PizzaFactory;
import domain.decorators.*;

public class Main {
    public static void main(String[] args) {

        Pizza pizza = PizzaFactory.createPizza("margherita");

        Pizza decorated = new ExtraCheese(new ExtraOlives(new StuffedCrust(pizza)));

        decorated.prepare();
        decorated.bake();
        decorated.cut();
        decorated.box();
    }
}
