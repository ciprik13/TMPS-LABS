package domain.command;

import domain.facade.PizzeriaFacade;

public class SimpleOrderCommand implements OrderCommand {

    private final PizzeriaFacade facade;
    private final String type;

    public SimpleOrderCommand(PizzeriaFacade facade, String type) {
        this.facade = facade;
        this.type = type;
    }

    @Override
    public void execute() {
        facade.orderSimple(type);
    }
}
