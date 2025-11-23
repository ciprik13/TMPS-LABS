package domain.decorators;

import domain.Pizza;

public class StuffedCrust extends PizzaDecorator {

    public StuffedCrust(Pizza basePizza) {
        super(basePizza);
    }

    @Override
    public void prepare() {
        super.prepare();
        System.out.println(" + using stuffed crust");
    }
}
