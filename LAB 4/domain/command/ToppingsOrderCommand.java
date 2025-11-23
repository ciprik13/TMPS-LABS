package domain.command;

import domain.facade.PizzeriaFacade;

import java.util.List;

public class ToppingsOrderCommand implements OrderCommand {

    private final PizzeriaFacade facade;
    private final String type;
    private final List<String> toppings;

    public ToppingsOrderCommand(PizzeriaFacade facade, String type, List<String> toppings) {
        this.facade = facade;
        this.type = type;
        this.toppings = toppings;
    }

    @Override
    public void execute() {
        facade.orderWithToppings(type, toppings);
    }
}
