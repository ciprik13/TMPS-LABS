package domain.decorators;

import domain.Pizza;

public class ExtraCheese extends PizzaDecorator {

    public ExtraCheese(Pizza basePizza) {
        super(basePizza);
    }

    @Override
    public void prepare() {
        super.prepare();
        System.out.println(" + adding extra cheese");
    }
}
