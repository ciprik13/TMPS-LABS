package domain;


public class Rancho implements Pizza {
    @Override
    public void prepare(){
        System.out.println("Preparing Rancho Pizza");
    }

    @Override
    public void bake(){
        System.out.println("Baking Rancho Pizza at 200 degrees");
    }

    @Override
    public void cut(){
        System.out.println("Cutting Rancho Pizza into 8 slices");
    }

    @Override
    public void box(){
        System.out.println("Boxing Rancho Pizza and preparing it for delivery");
    }
}