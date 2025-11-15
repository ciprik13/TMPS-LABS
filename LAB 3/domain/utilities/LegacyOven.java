package domain.utilities;

public class LegacyOven {

    public void heatUp(int temp) {
        System.out.println("[Legacy Oven] Heating up to " + temp + "°C");
    }

    public void cook(int mins) {
        System.out.println("[Legacy Oven] Cooking for " + mins + " minutes");
    }
}
