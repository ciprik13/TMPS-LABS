package factory;

import domain.Pizza;
import domain.Barbeque;
import domain.Margherita;
import domain.Rancho;

public class PizzaFactory {
    public static Pizza createPizza(String type) {
        if (type == null) {
            return null;
        }

        switch(type.toLowerCase()){
            case "rancho":
                return new Rancho();
            case "margherita":
                return new Margherita();
            case "barbeque":
                return new Barbeque();
            default:
                System.out.println("Invalid pizza type");
                return null;
        }
    }
}
