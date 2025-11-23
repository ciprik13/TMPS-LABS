package domain.command;

import domain.facade.PizzeriaFacade;

public class CustomOrderCommand implements OrderCommand {

    private final PizzeriaFacade facade;
    private final String size;
    private final String crust;
    private final boolean cheese;
    private final boolean pepperoni;
    private final boolean mushrooms;

    public CustomOrderCommand(
            PizzeriaFacade facade,
            String size,
            String crust,
            boolean cheese,
            boolean pepperoni,
            boolean mushrooms
    ) {
        this.facade = facade;
        this.size = size;
        this.crust = crust;
        this.cheese = cheese;
        this.pepperoni = pepperoni;
        this.mushrooms = mushrooms;
    }

    @Override
    public void execute() {
        facade.orderCustom(size, crust, cheese, pepperoni, mushrooms);
    }
}
