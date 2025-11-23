package domain.decorators;

import domain.Pizza;

public class ExtraOlives extends PizzaDecorator {

    public ExtraOlives(Pizza basePizza) {
        super(basePizza);
    }

    @Override
    public void prepare() {
        super.prepare();
        System.out.println(" + adding extra olives");
    }
}
