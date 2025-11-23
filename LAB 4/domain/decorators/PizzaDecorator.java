package domain.decorators;
import domain.Pizza;

public abstract class PizzaDecorator implements Pizza {
    protected final Pizza basePizza;

    public PizzaDecorator(Pizza pizza) {
        this.basePizza = pizza;
    }

    @Override
    public void prepare() {
        basePizza.prepare();
    }

    @Override
    public void bake() {
        basePizza.bake();
    }

    @Override
    public void cut() {
        basePizza.cut();
    }

    @Override
    public void box() {
        basePizza.box();
    }
    
}
