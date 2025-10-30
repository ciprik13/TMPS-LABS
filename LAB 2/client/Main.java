package client;

import domain.Pizza;
import factory.PizzaFactory;
import builder.CustomPizzaBuilder;

public class Main {
    public static void main(String[] args) {
        Pizza rancho = PizzaFactory.createPizza("rancho");
        Pizza margherita = PizzaFactory.createPizza("margherita");
        Pizza barbeque = PizzaFactory.createPizza("barbeque");

        rancho.prepare();
        margherita.prepare();
        barbeque.prepare();

        System.out.println("\n=== Custom Pizza Builder ===");
        CustomPizzaBuilder customPizza = new CustomPizzaBuilder.PizzaBuilder()
                .setSize("Large")
                .setCrustType("Thin Crust")
                .addExtraCheese()
                .addExtraMeat()
                .addExtraPepperoni()
                .addExtraOlives()
                .build();
        customPizza.displayPizza();
    }
}
