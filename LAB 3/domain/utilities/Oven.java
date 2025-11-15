package domain.utilities;

import domain.Pizza;

public interface Oven {
    void preheat(int degrees);
    void bake(Pizza pizza, int minutes);
}
