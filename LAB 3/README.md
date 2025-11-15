# Topic: Structural Design Patterns

### Course: Software Modeling & Design Techniques (aka TMPS)

### Author: Ciprian Moisenco | FAF-232

---

## Objectives

- Get familiar with the Structural Design Patterns (SDPs).
- Continue the previous laboratory work on **Pizza Ordering System**.
- Implement at least 3 SDPs for the domain: **Decorator, Facade, Adapter**.

---

## Introduction

Structural Design Patterns are all about how we organize and compose our classes and objects to form larger, more flexible structures. These patterns focus on simplifying the design by identifying simple ways to realize relationships between entities. After implementing creational patterns in Lab 2, I needed to think about how to make my pizza ordering system more maintainable and easier to use.

There are seven main structural design patterns:

- **Adapter** - Allows objects with incompatible interfaces to collaborate. It acts as a wrapper between two objects, catching calls for one object and transforming them to a format and interface recognizable by the second object. This is like a power adapter that lets you plug your device into a foreign outlet.

- **Bridge** - Separates an object's abstraction from its implementation so that the two can vary independently. This pattern is useful when both the class and what it does vary often. It helps avoid a permanent binding between an abstraction and its implementation.

- **Composite** - Composes objects into tree structures to represent part-whole hierarchies. It lets clients treat individual objects and compositions of objects uniformly. This is particularly useful when you need to work with tree-like structures.

- **Decorator** - Attaches new behaviors to objects by placing these objects inside special wrapper objects that contain the behaviors. It provides a flexible alternative to subclassing for extending functionality, allowing you to add responsibilities to objects dynamically.

- **Facade** - Provides a unified interface to a set of interfaces in a subsystem. It defines a higher-level interface that makes the subsystem easier to use by wrapping a complicated subsystem with a simpler interface.

- **Flyweight** - Minimizes memory usage by sharing as much data as possible with similar objects. It's particularly useful when you need to create a large number of similar objects, storing common data shared between objects externally.

- **Proxy** - Provides a surrogate or placeholder for another object to control access to it. It can add additional functionality when accessing an object, such as lazy initialization, access control, logging, etc.

For this laboratory work, I focused on three structural patterns that solved real problems in my pizza ordering system:

- **Decorator** - I needed a way to add toppings and features to pizzas (like extra cheese, olives, stuffed crust) without creating tons of subclasses. The Decorator pattern lets me wrap pizzas with additional functionality dynamically.

- **Facade** - My ordering process was getting complicated, involving multiple steps across different classes (Factory, Singleton, Decorators, etc.). The Facade pattern helped me create a simple interface that hides all that complexity from the client.

- **Adapter** - I wanted to use an old legacy oven system, but its interface didn't match what my modern pizza system expected. The Adapter pattern acts as a translator between the two incompatible interfaces.

---

## Used Design Patterns

For this laboratory work, I implemented three structural design patterns:

- **Decorator Pattern** – I use this to dynamically add toppings and features to pizzas without changing the base pizza classes. Customers can get basic pizzas, or add extra cheese, extra olives, and stuffed crust in any combination.

- **Facade Pattern** – This simplifies the ordering process for clients. Instead of manually creating pizzas with the factory, applying decorators, managing orders with singleton, and baking them (4+ steps), the facade handles everything in one simple method call.

- **Adapter Pattern** – This allows me to integrate a legacy oven system that has a completely different interface than what my modern pizza system expects, making them work together seamlessly.

---

## Implementation

### Overview

I extended my pizza ordering system from Lab 2 by adding structural patterns on top of the existing creational patterns. The goal was to make the system more flexible and easier to use while maintaining all the functionality from before.

Here's what I did:

1. Implemented the **Decorator pattern** to add toppings and features to pizzas
2. Created a **Facade** to simplify the ordering workflow
3. Built an **Adapter** to integrate with a legacy oven system

All of this works together with my previous patterns: Builder (for CustomPizza), Singleton (for OrderManager), and Factory Method (for standard Pizzas).

---

### 1. Decorator Pattern

The first problem I tackled was how to add toppings to pizzas. Initially, I thought about creating subclasses like `MargheritaWithCheese`, `MargheritaWithOlives`, `MargheritaWithCheeseAndOlives`, etc. But this would lead to a huge number of classes!

The Decorator pattern solved this elegantly. I created a base decorator that wraps any pizza, and then specific decorators for each topping.

**PizzaDecorator.java (Base Decorator)**
_Location: `domain/decorators/PizzaDecorator.java`_

```java
public abstract class PizzaDecorator implements Pizza {
    protected final Pizza basePizza;

    public PizzaDecorator(Pizza pizza) {
        this.basePizza = pizza;
    }

    @Override
    public void prepare() {
        basePizza.prepare();
    }

    @Override
    public void bake() {
        basePizza.bake();
    }

    @Override
    public void cut() {
        basePizza.cut();
    }

    @Override
    public void box() {
        basePizza.box();
    }
}
```

This is my base decorator. The key insight here is that it implements `Pizza` (so it can be used anywhere a pizza is expected) and also HAS a `Pizza` (the wrapped pizza). This is the foundation that makes everything work.

**ExtraCheese.java (Concrete Decorator)**
_Location: `domain/decorators/ExtraCheese.java`_

```java
public class ExtraCheese extends PizzaDecorator {

    public ExtraCheese(Pizza basePizza) {
        super(basePizza);
    }

    @Override
    public void prepare() {
        super.prepare();
        System.out.println(" + adding extra cheese");
    }
}
```

The `ExtraCheese` decorator adds functionality during the prepare step by calling the wrapped pizza's `prepare()` method first, then adding its own behavior. I also created `ExtraOlives` and `StuffedCrust` decorators following the same pattern.

**Other Concrete Decorators:**

- **ExtraOlives** - Adds extra olives to the pizza
- **StuffedCrust** - Adds stuffed crust feature to the pizza

The beauty of this approach is that I can stack decorators:

```java
Pizza margherita = PizzaFactory.createPizza("margherita");
Pizza decorated = new ExtraCheese(
                    new ExtraOlives(
                        new StuffedCrust(margherita)
                    )
                  );
decorated.prepare();  // Now has ALL toppings!
```

Each decorator wraps the previous one, creating a chain of functionality. No need for separate classes for every combination!

---

### 2. Facade Pattern

After implementing all these patterns, I realized the client code was becoming too complex. To order a pizza with toppings, you had to:

1. Use the Factory to create a Pizza
2. Apply Decorators if you want extra toppings
3. Get the Singleton OrderManager instance
4. Create/configure an Oven
5. Preheat the oven
6. Bake the pizza
7. Add the order to OrderManager

That's a lot of steps! So I created a Facade to simplify this.

**PizzeriaFacade.java**
_Location: `domain/facade/PizzeriaFacade.java`_

```java
public class PizzeriaFacade {

    private final Oven oven;
    private final OrderManager orderManager;

    public PizzeriaFacade() {
        this.oven = new LegacyOvenAdapter(new LegacyOven());
        this.orderManager = OrderManager.getInstance();
    }

    public Pizza orderSimple(String type) {
        Pizza pizza = PizzaFactory.createPizza(type);

        if (pizza == null) {
            System.out.println("Invalid pizza type!");
            return null;
        }

        oven.preheat(220);
        oven.bake(pizza, 15);

        orderManager.addOrder(pizza);

        return pizza;
    }

    public Pizza orderWithToppings(String type, List<String> toppings) {
        Pizza pizza = PizzaFactory.createPizza(type);

        if (pizza == null) {
            System.out.println("Invalid pizza type!");
            return null;
        }

        for (String t : toppings) {
            switch (t.toLowerCase()) {
                case "cheese": pizza = new ExtraCheese(pizza); break;
                case "olives": pizza = new ExtraOlives(pizza); break;
                case "stuffed": pizza = new StuffedCrust(pizza); break;
                default:
                    System.out.println("Unknown topping: " + t);
            }
        }

        oven.preheat(220);
        oven.bake(pizza, 18);

        orderManager.addOrder(pizza);

        return pizza;
    }

    public CustomPizza orderCustom(String size, String crust,
                                   boolean cheese, boolean pepperoni,
                                   boolean mushrooms) {
        CustomPizza.PizzaBuilder builder = new CustomPizza.PizzaBuilder()
                .setSize(size)
                .setCrustType(crust);

        if (cheese) builder.addExtraCheese();
        if (pepperoni) builder.addExtraPepperoni();
        if (mushrooms) builder.addExtraMushrooms();

        CustomPizza pizza = builder.build();

        System.out.println("\n[Custom Pizza Created]");
        pizza.displayPizza();

        return pizza;
    }

    public void printOrders() {
        orderManager.showOrders();
    }
}
```

Now instead of the client doing all those steps, they just call:

```java
facade.orderWithToppings("rancho", List.of("cheese", "olives"));
```

Much simpler! The facade handles all the complexity internally. I created different methods for different use cases:

- `orderSimple()` - For basic pizzas without toppings
- `orderWithToppings()` - For pizzas with decorator-based toppings
- `orderCustom()` - For completely custom pizzas using the Builder pattern
- `printOrders()` - To display all orders managed by the Singleton

---

### 3. Adapter Pattern

The last challenge was integrating a legacy oven system. Our pizzeria has an old oven that still works perfectly, but its interface doesn't match the modern interface our system expects.

**My modern interface (Oven.java):**
_Location: `domain/utilities/Oven.java`_

```java
public interface Oven {
    void preheat(int degrees);
    void bake(Pizza pizza, int minutes);
}
```

**Legacy oven (LegacyOven.java):**
_Location: `domain/utilities/LegacyOven.java`_

```java
public class LegacyOven {
    public void heatUp(int temp) {
        System.out.println("[Legacy Oven] Heating up to " + temp + "°C");
    }

    public void cook(int mins) {
        System.out.println("[Legacy Oven] Cooking for " + mins + " minutes");
    }
}
```

See the problem? My system expects `preheat()` and `bake()` but the legacy oven has `heatUp()` and `cook()`. They're incompatible!

**LegacyOvenAdapter.java**
_Location: `domain/utilities/LegacyOvenAdapter.java`_

```java
public class LegacyOvenAdapter implements Oven {

    private final LegacyOven legacy;

    public LegacyOvenAdapter(LegacyOven legacy) {
        this.legacy = legacy;
    }

    @Override
    public void preheat(int degrees) {
        legacy.heatUp(degrees);
    }

    @Override
    public void bake(Pizza pizza, int minutes) {
        pizza.prepare();
        legacy.cook(minutes);
        pizza.cut();
        pizza.box();
    }
}
```

The adapter acts as a translator! It implements my `Oven` interface but internally uses the `LegacyOven`. When someone calls `preheat(220)`, the adapter translates it to `heatUp(220)`. When someone calls `bake(pizza, 15)`, it handles the entire pizza preparation workflow and translates the baking part to `cook(15)`.

This way, the rest of my system doesn't need to know anything about the legacy oven's interface. If I want to switch to a different oven later, I just need to create a new adapter!

---

### 4. Integration with Previous Patterns

This lab builds on the creational patterns from Lab 2:

**Factory Method (PizzaFactory)** - Used to create standard pizzas:

```java
Pizza pizza = PizzaFactory.createPizza("margherita");
```

**Singleton (OrderManager)** - Ensures only one order manager exists:

```java
OrderManager orderManager = OrderManager.getInstance();
```

**Builder (CustomPizza)** - Used for creating custom pizzas with complex configurations:

```java
CustomPizza pizza = new CustomPizza.PizzaBuilder()
    .setSize("Large")
    .setCrustType("Thin Crust")
    .addExtraCheese()
    .addExtraPepperoni()
    .build();
```

All these patterns work together seamlessly through the Facade!

---

### 5. Client Code

**Main.java**
_Location: `client/Main.java`_

```java
public static void main(String[] args) {

    // =====================
    // 1) DECORATOR PATTERN
    // =====================
    System.out.println("=== DECORATOR PATTERN ===");

    Pizza margherita = PizzaFactory.createPizza("margherita");

    Pizza decorated = new ExtraCheese(
                            new ExtraOlives(
                                new StuffedCrust(margherita)
                            )
                       );

    decorated.prepare();
    decorated.bake();
    decorated.cut();
    decorated.box();

    // =====================
    // 2) ADAPTER PATTERN
    // =====================
    System.out.println("\n=== ADAPTER PATTERN ===");

    Pizza rancho = PizzaFactory.createPizza("rancho");
    Oven oven = new LegacyOvenAdapter(new LegacyOven());

    oven.preheat(220);
    oven.bake(rancho, 15);

    // =====================
    // 3) FACADE PATTERN
    // =====================
    System.out.println("\n=== FACADE PATTERN ===");

    PizzeriaFacade facade = new PizzeriaFacade();

    System.out.println("\n-- Simple order --");
    facade.orderSimple("margherita");

    System.out.println("\n-- Order with toppings --");
    facade.orderWithToppings("rancho", List.of("cheese", "olives"));

    System.out.println("\n-- Custom order --");
    facade.orderCustom("Large", "Thin Crust", true, true, false);

    System.out.println("\n-- All orders saved in OrderManager (Singleton) --");
    facade.printOrders();
}
```

I organized my Main class to clearly demonstrate each pattern separately. This makes it easy to understand what each pattern does and see the benefits.

---

## Results / Screenshots

### Expected Console Output

```
=== DECORATOR PATTERN ===
Preparing Margherita Pizza
 + using stuffed crust
 + adding extra olives
 + adding extra cheese
Baking Margherita Pizza at 220 degrees
Cutting Margherita Pizza into 6 slices
Boxing Margherita Pizza

=== ADAPTER PATTERN ===
[Legacy Oven] Heating up to 220°C
Preparing Rancho Pizza
[Legacy Oven] Cooking for 15 minutes
Cutting Rancho Pizza into 8 slices
Boxing Rancho Pizza and preparing it for delivery

=== FACADE PATTERN ===

-- Simple order --
[Legacy Oven] Heating up to 220°C
Preparing Margherita Pizza
[Legacy Oven] Cooking for 15 minutes
Cutting Margherita Pizza into 6 slices
Boxing Margherita Pizza
Order added: Margherita

-- Order with toppings --
[Legacy Oven] Heating up to 220°C
Preparing Rancho Pizza
 + adding extra cheese
 + adding extra olives
[Legacy Oven] Cooking for 18 minutes
Cutting Rancho Pizza into 8 slices
Boxing Rancho Pizza and preparing it for delivery
Order added: ExtraCheese

-- Custom order --

[Custom Pizza Created]
Pizza Size: Large
Crust Type: Thin Crust
Extra Cheese: Yes
Extra Meat: No
Extra Mushrooms: No
Extra Pepperoni: Yes
Extra Olives: No

-- All orders saved in OrderManager (Singleton) --
Current Orders:
- Margherita
- ExtraCheese
```

### Pattern Demonstrations

**1. Decorator Pattern Demonstration**

- Shows how a basic Margherita pizza can be wrapped with multiple decorators
- Each decorator adds its feature during the `prepare()` phase
- The output clearly shows the chain: stuffed crust → extra olives → extra cheese
- All pizza operations (prepare, bake, cut, box) work seamlessly

**2. Adapter Pattern Demonstration**

- Shows the legacy oven being used through the modern `Oven` interface
- `preheat(220)` is translated to `[Legacy Oven] Heating up to 220°C`
- `bake()` handles the entire pizza workflow and translates to `cook()`
- The rest of the system has no idea it's using a legacy oven!

**3. Facade Pattern Demonstration**

- **Simple order**: One method call handles everything (factory, oven, order manager)
- **Order with toppings**: Facade applies decorators automatically based on the list
- **Custom order**: Facade uses the Builder pattern internally
- **Print orders**: Shows all pizzas managed by the Singleton OrderManager

---

## Project Structure

```
LAB 3/
├── client/
│   └── Main.java                    # Client code demonstrating patterns
├── domain/
│   ├── Pizza.java                   # Pizza interface
│   ├── Margherita.java              # Concrete pizza
│   ├── Rancho.java                  # Concrete pizza
│   ├── Barbeque.java                # Concrete pizza
│   ├── builder/
│   │   └── CustomPizza.java         # Builder pattern (Lab 2)
│   ├── decorators/
│   │   ├── PizzaDecorator.java      # Base decorator
│   │   ├── ExtraCheese.java         # Concrete decorator
│   │   ├── ExtraOlives.java         # Concrete decorator
│   │   └── StuffedCrust.java        # Concrete decorator
│   ├── facade/
│   │   └── PizzeriaFacade.java      # Facade pattern
│   ├── factory/
│   │   └── PizzaFactory.java        # Factory pattern (Lab 2)
│   ├── singleton/
│   │   └── OrderManager.java        # Singleton pattern (Lab 2)
│   └── utilities/
│       ├── Oven.java                # Modern oven interface
│       ├── LegacyOven.java          # Legacy oven implementation
│       └── LegacyOvenAdapter.java   # Adapter pattern
└── README.md
```

---

## Conclusions

In this laboratory work, I successfully implemented three structural design patterns that significantly improved my pizza ordering system. Here's what I learned and achieved:

**What I Implemented:**

- **Decorator Pattern** to dynamically add toppings (extra cheese, extra olives, stuffed crust) to pizzas without creating separate subclasses for every combination. This gives customers unlimited flexibility in customizing their pizzas.
- **Facade Pattern** to simplify the ordering workflow from 7+ separate steps into single, convenient method calls. The facade intelligently coordinates the Factory, Singleton, Decorators, and Adapter patterns behind the scenes.
- **Adapter Pattern** to integrate a legacy oven system with an incompatible interface. This allows us to use existing equipment without rewriting our entire system or the legacy code.

**Key Learnings:**

1. **Flexibility through Composition**: The Decorator pattern showed me how composition (wrapping objects) is more flexible than inheritance (subclassing). I can create any combination of toppings at runtime!

2. **Simplification through Abstraction**: The Facade pattern taught me that hiding complexity doesn't mean removing it—it means organizing it better and presenting a cleaner interface to clients.

3. **Integration through Translation**: The Adapter pattern demonstrated that incompatible systems can work together if we build the right "translator" between them.

4. **Pattern Synergy**: The real power came from combining patterns. My Facade uses the Factory, applies Decorators, manages with Singleton, and bakes with an Adapter—all working together seamlessly!

**Real-World Applications:**
These patterns are everywhere in production systems:

- **Decorator**: Java I/O streams (`BufferedReader(new FileReader())`), middleware in web frameworks
- **Facade**: Library APIs, SDK interfaces, microservice gateways
- **Adapter**: Database drivers, third-party API integrations, legacy system modernization

This lab helped me understand that good software design isn't just about making code work—it's about making it flexible, maintainable, and easy to extend. The structural patterns I implemented here solve real problems that developers face every day, and now I have practical experience applying them in a realistic domain.

**Future Enhancements:**

- Add more decorators (extra bacon, jalapeños, pineapple)
- Implement pricing calculation in decorators
- Add delivery tracking system
- Create a GUI for the pizzeria facade
- Implement the Proxy pattern for order authentication
- Use Composite pattern for combo deals (multiple pizzas)

---

## References

- Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). _Design Patterns: Elements of Reusable Object-Oriented Software_. Addison-Wesley.
- Freeman, E., & Freeman, E. (2004). _Head First Design Patterns_. O'Reilly Media.
- Refactoring.Guru - Design Patterns: https://refactoring.guru/design-patterns
- Course materials: Software Modeling & Design Techniques (TMPS)
