package client;

import domain.Pizza;
import domain.Rancho;
import domain.Barbeque;
import domain.Margherita;

public class Main {
    public static void main(String[] args) {
        Pizza rancho = new Rancho();
        rancho.prepare();
        rancho.bake();
        rancho.cut();
        rancho.box();

        System.out.println("------------------------------");

        Pizza margherita = new Margherita();
        margherita.prepare();
        margherita.bake();
        margherita.cut();
        margherita.box();

        System.out.println("------------------------------");

        Pizza barbeque = new Barbeque();
        barbeque.prepare();
        barbeque.bake();
        barbeque.cut();
        barbeque.box();
    }
}
