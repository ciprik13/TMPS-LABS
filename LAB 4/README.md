# Topic: Behavioral Design Patterns
### Course: Software Modeling & Design Techniques (aka TMPS)
### Author: Ciprian Moisenco | FAF-232

---

## Objectives

* Get familiar with the Behavioral Design Patterns (BDPs).
* Continue the previous laboratory work on **Pizza Ordering System**.
* Implement at least 1 BDP for the domain: **Observer, Strategy, Command**.

---

## Introduction

Behavioral Design Patterns are concerned with algorithms and the assignment of responsibilities between objects. These patterns help define how objects communicate with each other, making the communication more flexible and easier to understand. After implementing creational patterns (Lab 2) and structural patterns (Lab 3), I needed to focus on how different entities in my system interact and collaborate.

There are eleven main behavioral design patterns:

- **Chain of Responsibility** - Passes requests along a chain of handlers where each handler decides either to process the request or pass it to the next handler in the chain. Useful for event handling systems.

- **Command** - Encapsulates a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations. Perfect for implementing undo/redo functionality.

- **Iterator** - Provides a way to access elements of a collection sequentially without exposing the underlying representation. Used extensively in collections and data structures.

- **Mediator** - Reduces chaotic dependencies between objects by forcing them to collaborate only via a mediator object. Promotes loose coupling by preventing objects from referring to each other explicitly.

- **Memento** - Captures and externalizes an object's internal state so that the object can be restored to this state later, without violating encapsulation. Useful for save/load functionality.

- **Observer** - Defines a subscription mechanism to notify multiple objects about events happening to the object they're observing. Enables event-driven architectures and loose coupling between publishers and subscribers.

- **State** - Allows an object to alter its behavior when its internal state changes. The object will appear to change its class. Great for state machines and workflow systems.

- **Strategy** - Defines a family of algorithms, encapsulates each one, and makes them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

- **Template Method** - Defines the skeleton of an algorithm in a base class but lets subclasses override specific steps without changing the algorithm's structure.

- **Visitor** - Lets you separate algorithms from the objects on which they operate, making it easy to add new operations without modifying the object classes.

- **Interpreter** - Provides a way to evaluate language grammar or expressions. Used for creating domain-specific languages or parsing structured text.

For this laboratory work, I focused on three behavioral patterns that solved communication and interaction problems in my pizza ordering system:

- **Observer** - I needed a way for multiple parties (kitchen, billing) to be automatically notified when orders are placed. The Observer pattern creates a subscription model where observers are notified automatically.

- **Strategy** - Different delivery methods need to be applied based on customer preference (bike, car, pickup). The Strategy pattern encapsulates these algorithms and makes them interchangeable at runtime.

- **Command** - Order operations need to be encapsulated as objects to enable flexible execution. The Command pattern allows treating operations as first-class objects that can be executed on demand.

---

## Used Design Patterns

For this laboratory work, I implemented three behavioral design patterns:

- **Observer Pattern** - Implements an event notification system where the kitchen and billing departments automatically receive updates about new orders.

- **Strategy Pattern** - Enables flexible delivery methods with different algorithms: bike delivery, car delivery, and pickup. The delivery strategy can be changed at runtime.

- **Command Pattern** - Encapsulates order operations (simple, with toppings, custom) as command objects that can be executed through an invoker.

---

## Implementation

### Overview

I extended my pizza ordering system from Lab 3 by adding behavioral patterns on top of the existing creational and structural patterns. The goal was to improve communication between system entities and make the system more flexible in handling operations and delivery.

Here's what I implemented:

1. **Observer pattern** for event-driven notifications
2. **Strategy pattern** for flexible delivery methods
3. **Command pattern** for operation encapsulation

All of this works together with patterns from previous labs:

- **Lab 2 (Creational)**: Builder (CustomPizza), Singleton (OrderManager), Factory Method (Pizza types)
- **Lab 3 (Structural)**: Decorator (Pizza toppings), Facade (PizzeriaFacade), Adapter (LegacyOvenAdapter)

---

### 1. Observer Pattern

**The Problem:**
When an order is placed, multiple departments need to know:

- **Kitchen** needs to start preparing the order
- **Billing** needs to register the order for payment

Without the Observer pattern, I would need tight coupling between the OrderManager and all these entities, making the code rigid and hard to maintain.

**The Solution:**
The Observer pattern creates a subscription mechanism where observers register with a subject and are automatically notified of state changes.

#### Implementation Details

**OrderObserver.java (Observer Interface)**
_Location: `domain/observer/OrderObserver.java`_

```java
public interface OrderObserver {
    void onOrderAdded(String orderName);
}
```

This is the observer interface that all concrete observers must implement. The `onOrderAdded()` method is called by the subject when an order event occurs.

**KitchenDisplayObserver.java (Concrete Observer)**
_Location: `domain/observer/KitchenDisplayObserver.java`_

```java
public class KitchenDisplayObserver implements OrderObserver {
    @Override
    public void onOrderAdded(String orderName) {
        System.out.println("[Kitchen] New order received: " + orderName);
    }
}
```

The kitchen observer reacts to new orders by displaying them in the kitchen display system.

**BillingObserver.java (Concrete Observer)**
_Location: `domain/observer/BillingObserver.java`_

```java
public class BillingObserver implements OrderObserver {
    @Override
    public void onOrderAdded(String orderName) {
        System.out.println("[Billing] Registering order for billing: " + orderName);
    }
}
```

The billing observer registers the order for payment processing.

**Integration with OrderManager:**
_Location: `domain/singleton/OrderManager.java`_

```java
public class OrderManager {
    private static OrderManager instance;
    private List<Pizza> orders;
    private List<OrderObserver> observers = new ArrayList<>();

    private OrderManager() {
        orders = new ArrayList<>();
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    private void notifyOrderAdded(String orderName) {
        for (OrderObserver observer : observers) {
            observer.onOrderAdded(orderName);
        }
    }

    public void addOrder(Pizza pizza) {
        orders.add(pizza);
        String name = pizza.getClass().getSimpleName();
        System.out.println("Order added: " + name);
        notifyOrderAdded(name);
    }
}
```

The `OrderManager` maintains a list of observers and notifies them whenever a new order is added. Observers can be added at runtime, providing flexibility.

**Usage Example:**

```java
OrderManager manager = OrderManager.getInstance();
manager.addObserver(new KitchenDisplayObserver());
manager.addObserver(new BillingObserver());

// When a pizza is ordered, both observers are notified automatically
manager.addOrder(pizza);
```

---

### 2. Strategy Pattern

**The Problem:**
Different customers want different delivery methods:

- Bike delivery (fast for short distances)
- Car delivery (better for longer distances)
- Pickup (customer collects the order)

Hard-coding delivery logic in one place would create rigid code that's hard to extend with new delivery methods.

**The Solution:**
The Strategy pattern encapsulates each delivery algorithm in its own class, making them interchangeable. The delivery strategy can be selected and changed at runtime without modifying existing code.

#### Implementation Details

**DeliveryStrategy.java (Strategy Interface)**
_Location: `domain/strategy/DeliveryStrategy.java`_

```java
public interface DeliveryStrategy {
    void deliver(String orderName);
}
```

This interface defines the contract for all delivery strategies. Each strategy implements its own algorithm in `deliver()`.

**BikeDeliveryStrategy.java (Concrete Strategy)**
_Location: `domain/strategy/BikeDeliveryStrategy.java`_

```java
public class BikeDeliveryStrategy implements DeliveryStrategy {
    @Override
    public void deliver(String orderName) {
        System.out.println("[Delivery-Bike] Delivering order \"" + orderName + "\" by bike.");
    }
}
```

The bike delivery strategy implements delivery using a bike courier.

**CarDeliveryStrategy.java (Concrete Strategy)**
_Location: `domain/strategy/CarDeliveryStrategy.java`_

```java
public class CarDeliveryStrategy implements DeliveryStrategy {
    @Override
    public void deliver(String orderName) {
        System.out.println("[Delivery-Car] Delivering order \"" + orderName + "\" by car.");
    }
}
```

The car delivery strategy implements delivery using a car.

**PickupStrategy.java (Concrete Strategy)**
_Location: `domain/strategy/PickupStrategy.java`_

```java
public class PickupStrategy implements DeliveryStrategy {
    @Override
    public void deliver(String orderName) {
        System.out.println("[Pickup] Order \"" + orderName + "\" ready for pickup.");
    }
}
```

The pickup strategy indicates that the order is ready for customer collection.

**Integration with PizzeriaFacade:**
_Location: `domain/facade/PizzeriaFacade.java`_

```java
public class PizzeriaFacade {
    private DeliveryStrategy deliveryStrategy = new PickupStrategy();

    public void setDeliveryStrategy(DeliveryStrategy strategy) {
        this.deliveryStrategy = strategy;
    }

    public Pizza orderSimple(String type) {
        Pizza pizza = PizzaFactory.createPizza(type);
        // ... preparation logic ...
        deliveryStrategy.deliver(pizza.getClass().getSimpleName());
        return pizza;
    }
}
```

The `PizzeriaFacade` maintains a reference to a strategy and delegates the delivery to it. The strategy can be changed at runtime using `setDeliveryStrategy()`.

**Usage Example:**

```java
PizzeriaFacade facade = new PizzeriaFacade();

// Start with bike delivery
facade.setDeliveryStrategy(new BikeDeliveryStrategy());
facade.orderSimple("margherita");

// Switch to car delivery
facade.setDeliveryStrategy(new CarDeliveryStrategy());
facade.orderSimple("rancho");
```

---

### 3. Command Pattern

**The Problem:**
Order operations (simple order, order with toppings, custom order) need to be:

- **Encapsulated** as objects
- **Executed** through a unified interface
- **Queued** for batch processing if needed

Direct method calls don't provide this flexibility.

**The Solution:**
The Command pattern encapsulates operations as objects. Each operation (simple, with toppings, custom) becomes a command object that knows how to execute itself.

#### Implementation Details

**OrderCommand.java (Command Interface)**
_Location: `domain/command/OrderCommand.java`_

```java
public interface OrderCommand {
    void execute();
}
```

Every command must implement `execute()` to perform the action.

**SimpleOrderCommand.java (Concrete Command)**
_Location: `domain/command/SimpleOrderCommand.java`_

```java
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
```

The `SimpleOrderCommand` encapsulates a simple pizza order operation.

**ToppingsOrderCommand.java (Concrete Command)**
_Location: `domain/command/ToppingsOrderCommand.java`_

```java
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
```

The `ToppingsOrderCommand` encapsulates ordering a pizza with additional toppings.

**CustomOrderCommand.java (Concrete Command)**
_Location: `domain/command/CustomOrderCommand.java`_

```java
public class CustomOrderCommand implements OrderCommand {
    private final PizzeriaFacade facade;
    private final String size;
    private final String crust;
    private final boolean cheese;
    private final boolean pepperoni;
    private final boolean mushrooms;

    public CustomOrderCommand(PizzeriaFacade facade, String size, String crust,
                            boolean cheese, boolean pepperoni, boolean mushrooms) {
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
```

The `CustomOrderCommand` encapsulates building a custom pizza with specific attributes.

**OrderInvoker.java (Invoker)**
_Location: `domain/command/OrderInvoker.java`_

```java
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
```

The `OrderInvoker` acts as an invoker that can execute commands immediately or queue them for batch processing.

**Usage Example:**

```java
OrderInvoker invoker = new OrderInvoker();

// Execute commands immediately
invoker.execute(new SimpleOrderCommand(facade, "margherita"));
invoker.execute(new ToppingsOrderCommand(facade, "rancho", List.of("cheese", "olives")));

// Or queue commands for batch processing
invoker.addCommand(new SimpleOrderCommand(facade, "margherita"));
invoker.addCommand(new CustomOrderCommand(facade, "Large", "Thin Crust", true, true, false));
invoker.processCommands();
```

---

### 4. Integration and Client Code

All three behavioral patterns work together seamlessly with the existing patterns from previous labs.

**Main.java**
_Location: `client/Main.java`_

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("=== LAB 4 — BEHAVIORAL DESIGN PATTERNS ===");

        // OBSERVER
        OrderManager manager = OrderManager.getInstance();
        manager.addObserver(new KitchenDisplayObserver());
        manager.addObserver(new BillingObserver());

        // FACADE + STRATEGY
        PizzeriaFacade facade = new PizzeriaFacade();
        facade.setDeliveryStrategy(new BikeDeliveryStrategy());

        // COMMAND PATTERN
        OrderInvoker invoker = new OrderInvoker();

        System.out.println("\n-- Command: Simple Order --");
        invoker.execute(new SimpleOrderCommand(facade, "margherita"));

        System.out.println("\n-- Command: Order with Toppings --");
        invoker.execute(new ToppingsOrderCommand(facade, "rancho", List.of("cheese", "olives")));

        System.out.println("\n-- Command: Custom Order --");
        invoker.execute(new CustomOrderCommand(facade, "Large", "Thin Crust",
                                               true, true, false));
    }
}
```

The `Main` class demonstrates all patterns:

1. **Observer pattern** - Multiple observers react to order events
2. **Strategy pattern** - Delivery strategy is set to bike delivery
3. **Command pattern** - Different order operations executed through invoker

---

## Results / Screenshots

### Expected Console Output

```
=== LAB 4 — BEHAVIORAL DESIGN PATTERNS ===

-- Command: Simple Order --
Order added: Margherita
[Kitchen] New order received: Margherita
[Billing] Registering order for billing: Margherita
[Delivery-Bike] Delivering order "Margherita" by bike.

-- Command: Order with Toppings --
Order added: ExtraOlives
[Kitchen] New order received: ExtraOlives
[Billing] Registering order for billing: ExtraOlives
[Delivery-Bike] Delivering order "ExtraOlives" by bike.

-- Command: Custom Order --

[Custom Pizza Created]
Size: Large
Crust Type: Thin Crust
Has Extra Cheese: Yes
Has Extra Pepperoni: Yes
Has Extra Mushrooms: No
[Delivery-Bike] Delivering order "CustomPizza" by bike.
```

---

## Conclusions

In this lab, I successfully implemented three behavioral design patterns that made my pizza ordering system more flexible and easier to maintain.

- **Observer Pattern** for automatic notifications - when an order is placed, the kitchen and billing departments are notified automatically. No more manual updates.
- **Strategy Pattern** for flexible delivery - I can easily switch between different delivery methods (bike, car, pickup) without changing the core code.
- **Command Pattern** for operation encapsulation - all order operations are encapsulated as objects that can be executed through a unified interface, enabling features like command queuing.

Behavioral patterns are about how objects communicate and who's responsible for what. Before this lab, I would have probably hard-coded everything with direct method calls and tight coupling. Now I see how these patterns make the code cleaner and more maintainable.

The Observer pattern was especially useful - it's like having a notification system built into the code. The Strategy pattern showed me how to swap entire algorithms at runtime without breaking anything. And the Command pattern demonstrated that operations can be treated as first-class objects, opening up possibilities for queuing, logging, and batch processing.

These patterns integrate seamlessly with the creational and structural patterns from previous labs, creating a robust and flexible pizza ordering system that can easily be extended with new features.
