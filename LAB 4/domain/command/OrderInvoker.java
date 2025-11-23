package domain.command;

import java.util.ArrayList;
import java.util.List;

public class OrderInvoker {

    private final List<OrderCommand> queue = new ArrayList<>();

    public void addCommand(OrderCommand command) {
        queue.add(command);
    }

    public void processCommands() {
        for (OrderCommand command : queue) {
            command.execute();
        }
        queue.clear();
    }

    public void execute(OrderCommand command) {
        command.execute();
    }
}
