package domain.utilities;

import domain.Pizza;

public class LegacyOvenAdapter implements Oven {

    private final LegacyOven legacy;

    public LegacyOvenAdapter(LegacyOven legacy) {
        this.legacy = legacy;
    }

    @Override
    public void preheat(int degrees) {
        legacy.heatUp(degrees);
    }

    @Override
    public void bake(Pizza pizza, int minutes) {
        pizza.prepare();
        legacy.cook(minutes);
        pizza.cut();
        pizza.box();
    }
}
