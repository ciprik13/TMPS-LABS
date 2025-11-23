package domain.builder;

public class CustomPizza {
    private String size;
    private String crustType;
    private boolean extraCheese;
    private boolean extraMeat;
    private boolean extraMushrooms;
    private boolean extraPepperoni;
    private boolean extraOlives;

    private CustomPizza(PizzaBuilder builder) {
        this.size = builder.size;
        this.crustType = builder.crustType;
        this.extraCheese = builder.extraCheese;
        this.extraMeat = builder.extraMeat;
        this.extraMushrooms = builder.extraMushrooms;
        this.extraPepperoni = builder.extraPepperoni;
        this.extraOlives = builder.extraOlives;
    }

    public void displayPizza(){
        System.out.println("Pizza Size: " + size);
        System.out.println("Crust Type: " + crustType);
        System.out.println("Extra Cheese: " + (extraCheese ? "Yes" : "No"));
        System.out.println("Extra Meat: " + (extraMeat ? "Yes" : "No"));
        System.out.println("Extra Mushrooms: " + (extraMushrooms ? "Yes" : "No"));
        System.out.println("Extra Pepperoni: " + (extraPepperoni ? "Yes" : "No"));
        System.out.println("Extra Olives: " + (extraOlives ? "Yes" : "No"));
    }

    public static class PizzaBuilder {
        private String size;
        private String crustType;
        private boolean extraCheese;
        private boolean extraMeat;
    private boolean extraMushrooms;
        private boolean extraPepperoni;
        private boolean extraOlives;

        public PizzaBuilder setSize(String size) {
            this.size = size;
            return this;
        }

        public PizzaBuilder setCrustType(String crustType) {
            this.crustType = crustType;
            return this;
        }

        public PizzaBuilder addExtraCheese() {
            this.extraCheese = true;
            return this;
        }

        public PizzaBuilder addExtraMeat() {
            this.extraMeat = true;
            return this;
        }

        public PizzaBuilder addExtraMushrooms() {
            this.extraMushrooms = true;
            return this;
        }

        public PizzaBuilder addExtraPepperoni() {
            this.extraPepperoni = true;
            return this;
        }

        public PizzaBuilder addExtraOlives() {
            this.extraOlives = true;
            return this;
        }

        public CustomPizza build() {
            return new CustomPizza(this);
        }
    }
}
