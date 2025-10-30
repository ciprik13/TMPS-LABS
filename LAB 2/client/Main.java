package client;

import domain.Pizza;
import factory.PizzaFactory;

public class Main {
    public static void main(String[] args) {
        Pizza rancho = PizzaFactory.createPizza("rancho");
        Pizza margherita = PizzaFactory.createPizza("margherita");
        Pizza barbeque = PizzaFactory.createPizza("barbeque");

        rancho.prepare();
        margherita.prepare();
        barbeque.prepare();
    }
}
