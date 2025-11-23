package domain;


public class Barbeque implements Pizza {
    @Override
    public void prepare(){
        System.out.println("Preparing Barbeque Pizza");
    }

    @Override
    public void bake(){
        System.out.println("Baking Barbeque Pizza at 220 degrees");
    }

    @Override
    public void cut(){
        System.out.println("Cutting Barbeque Pizza into 6 slices");
    }

    @Override
    public void box(){
        System.out.println("Boxing Barbeque Pizza");
    }
}
