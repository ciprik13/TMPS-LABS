package domain;


public class Margherita implements Pizza {
    @Override
    public void prepare(){
        System.out.println("Preparing Margherita Pizza");
    }

    @Override
    public void bake(){
        System.out.println("Baking Margherita Pizza at 220 degrees");
    }

    @Override
    public void cut(){
        System.out.println("Cutting Margherita Pizza into 6 slices");
    }

    @Override
    public void box(){
        System.out.println("Boxing Margherita Pizza");
    }
}
