# Creational Design Patterns Implementation - Pizza Ordering System

## Laboratory Overview

This laboratory work demonstrates the implementation of three fundamental creational design patterns in Java: Factory Method, Singleton, and Builder patterns. The project simulates a pizza ordering system where different types of pizzas can be created, orders can be managed centrally, and custom pizzas can be built with various options.

## Objectives

1. Study and understand the Creational Design Patterns.
2. Choose a domain, define its main classes/models/entities and choose the appropriate instantiation mechanisms.
3. Use some creational design patterns for object instantiation in a sample project.

## Theory

In software engineering, creational design patterns are general solutions that deal with object creation, trying to create objects in a manner suitable to the situation. The basic form of object creation could result in design problems or added complexity to the design. Creational design patterns solve this problem by optimizing, hiding, or controlling the object creation.

## Project Structure

The project is organized into the following packages:

- **domain**: Contains the Pizza interface and concrete pizza implementations
- **factory**: Implements the Factory Method pattern for pizza creation
- **builder**: Implements the Builder pattern for custom pizza construction
- **singleton**: Implements the Singleton pattern for order management
- **client**: Contains the Main class demonstrating all patterns

## Implemented Creational Design Patterns

### 1. Factory Method Pattern

**Definition:** The Factory Method pattern defines an interface for creating objects but lets subclasses decide which class to instantiate. It encapsulates object creation logic in a separate method or class.

**Implementation:**

The Factory Method pattern is implemented through the `PizzaFactory` class, which provides a centralized way to create different types of pizzas.

**Pizza Interface** (`domain/Pizza.java`):

```java
package domain;

public interface Pizza {
    void prepare();
    void bake();
    void cut();
    void box();
}
```

The `Pizza` interface defines the contract that all concrete pizza types must implement, ensuring consistency across different pizza varieties.

**PizzaFactory Class** (`factory/PizzaFactory.java`):

```java
package factory;

import domain.Pizza;
import domain.Barbeque;
import domain.Margherita;
import domain.Rancho;

public class PizzaFactory {
    public static Pizza createPizza(String type) {
        if (type == null) {
            return null;
        }

        switch(type.toLowerCase()){
            case "rancho":
                return new Rancho();
            case "margherita":
                return new Margherita();
            case "barbeque":
                return new Barbeque();
            default:
                System.out.println("Invalid pizza type");
                return null;
        }
    }
}
```

The factory centralizes the object creation logic, making it easy to add new pizza types without modifying client code.

**Concrete Pizza Implementations:**

**Margherita Pizza** (`domain/Margherita.java`):

```java
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
```

**Barbeque Pizza** (`domain/Barbeque.java`):

```java
public class Barbeque implements Pizza {
    @Override
    public void prepare(){
        System.out.println("Preparing Barbeque Pizza");
    }

    @Override
    public void bake(){
        System.out.println("Baking Barbeque Pizza at 220 degrees");
    }

    @Override
    public void cut(){
        System.out.println("Cutting Barbeque Pizza into 6 slices");
    }

    @Override
    public void box(){
        System.out.println("Boxing Barbeque Pizza");
    }
}
```

**Rancho Pizza** (`domain/Rancho.java`):

```java
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
```

**Usage Example:**

```java
Pizza rancho = PizzaFactory.createPizza("rancho");
Pizza margherita = PizzaFactory.createPizza("margherita");
Pizza barbeque = PizzaFactory.createPizza("barbeque");

rancho.prepare();
margherita.prepare();
barbeque.prepare();
```

**Benefits:**

- **Encapsulation:** Object creation logic is encapsulated in one place
- **Flexibility:** New pizza types can be added easily without changing client code
- **Maintainability:** Changes to object creation only require modifications to the factory
- **Loose Coupling:** Clients depend on the Pizza interface, not concrete implementations

### 2. Singleton Pattern

**Definition:** The Singleton pattern ensures that a class has only one instance and provides a global point of access to that instance. This is useful when exactly one object is needed to coordinate actions across the system.

**Implementation:**

The Singleton pattern is implemented through the `OrderManager` class, which manages all pizza orders in the system.

**OrderManager Class** (`singleton/OrderManager.java`):

```java
package singleton;

import java.util.ArrayList;
import java.util.List;
import domain.Pizza;

public class OrderManager {
    private static OrderManager instance;
    private List<Pizza> orders;

    private OrderManager() {
        orders = new ArrayList<>();
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void addOrder(Pizza pizza) {
        orders.add(pizza);
        System.out.println("Order added: " + pizza.getClass().getSimpleName());
    }

    public void showOrders(){
        System.out.println("Current Orders:");
        for(Pizza pizza : orders){
            System.out.println("- " + pizza.getClass().getSimpleName());
        }
    }
}
```

**Key Features:**

1. **Private Constructor:** Prevents direct instantiation from outside the class
2. **Static Instance:** Holds the single instance of the class
3. **Static getInstance() Method:** Provides global access point and lazy initialization
4. **Centralized State:** Maintains a single list of orders accessible throughout the application

**Usage Example:**

```java
OrderManager orderManager = OrderManager.getInstance();
orderManager.addOrder(rancho);
orderManager.addOrder(margherita);
orderManager.addOrder(barbeque);
orderManager.showOrders();
```

**Benefits:**

- **Controlled Access:** Single instance ensures consistent state across the application
- **Global Access:** Easy access to the order manager from anywhere in the code
- **Lazy Initialization:** Instance is created only when first requested
- **Resource Management:** Prevents multiple instances that could waste memory

**Thread Safety Note:** The current implementation uses lazy initialization. For multi-threaded environments, consider using synchronized methods or eager initialization.

### 3. Builder Pattern

**Definition:** The Builder pattern separates the construction of a complex object from its representation, allowing the same construction process to create different representations. It's particularly useful when an object has many optional parameters.

**Implementation:**

The Builder pattern is implemented through the `CustomPizzaBuilder` class, which allows step-by-step construction of custom pizzas with various toppings and options.

**CustomPizzaBuilder Class** (`builder/CustomPizzaBuilder.java`):

```java
package builder;

public class CustomPizzaBuilder {
    private String size;
    private String crustType;
    private boolean extraCheese;
    private boolean extraMeat;
    private boolean extraPepperoni;
    private boolean extraOlives;

    private CustomPizzaBuilder(PizzaBuilder builder) {
        this.size = builder.size;
        this.crustType = builder.crustType;
        this.extraCheese = builder.extraCheese;
        this.extraMeat = builder.extraMeat;
        this.extraPepperoni = builder.extraPepperoni;
        this.extraOlives = builder.extraOlives;
    }

    public void displayPizza(){
        System.out.println("Pizza Size: " + size);
        System.out.println("Crust Type: " + crustType);
        System.out.println("Extra Cheese: " + (extraCheese ? "Yes" : "No"));
        System.out.println("Extra Meat: " + (extraMeat ? "Yes" : "No"));
        System.out.println("Extra Pepperoni: " + (extraPepperoni ? "Yes" : "No"));
        System.out.println("Extra Olives: " + (extraOlives ? "Yes" : "No"));
    }

    public static class PizzaBuilder {
        private String size;
        private String crustType;
        private boolean extraCheese;
        private boolean extraMeat;
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

        public PizzaBuilder addExtraPepperoni() {
            this.extraPepperoni = true;
            return this;
        }

        public PizzaBuilder addExtraOlives() {
            this.extraOlives = true;
            return this;
        }

        public CustomPizzaBuilder build() {
            return new CustomPizzaBuilder(this);
        }
    }
}
```

**Key Features:**

1. **Fluent Interface:** Methods return `this` for method chaining
2. **Step-by-Step Construction:** Each method adds one attribute to the object
3. **Immutability:** Once built, the CustomPizzaBuilder object cannot be modified
4. **Nested Builder Class:** Inner `PizzaBuilder` class handles the construction logic
5. **Private Constructor:** Ensures objects can only be created through the builder

**Usage Example:**

```java
CustomPizzaBuilder customPizza = new CustomPizzaBuilder.PizzaBuilder()
        .setSize("Large")
        .setCrustType("Thin Crust")
        .addExtraCheese()
        .addExtraMeat()
        .addExtraPepperoni()
        .addExtraOlives()
        .build();

customPizza.displayPizza();
```

**Benefits:**

- **Readability:** Code is easy to read and understand
- **Flexibility:** Can create objects with different combinations of parameters
- **Optional Parameters:** Handles multiple optional parameters elegantly
- **Immutability:** Built objects are immutable, ensuring thread safety
- **Validation:** Can add validation logic in the build() method

## Complete Demonstration

**Main Class** (`client/Main.java`):

```java
package client;

import domain.Pizza;
import factory.PizzaFactory;
import builder.CustomPizzaBuilder;
import singleton.OrderManager;

public class Main {
    public static void main(String[] args) {
        // Factory Method Pattern
        System.out.println("Pizza Factory Method Pattern:");
        Pizza rancho = PizzaFactory.createPizza("rancho");
        Pizza margherita = PizzaFactory.createPizza("margherita");
        Pizza barbeque = PizzaFactory.createPizza("barbeque");

        rancho.prepare();
        margherita.prepare();
        barbeque.prepare();

        // Singleton Pattern
        System.out.println("\nOrder Manager Singleton Pattern:");
        OrderManager orderManager = OrderManager.getInstance();
        orderManager.addOrder(rancho);
        orderManager.addOrder(margherita);
        orderManager.addOrder(barbeque);

        orderManager.showOrders();

        // Builder Pattern
        System.out.println("\nCustom Pizza Builder Pattern:");
        CustomPizzaBuilder customPizza = new CustomPizzaBuilder.PizzaBuilder()
                .setSize("Large")
                .setCrustType("Thin Crust")
                .addExtraCheese()
                .addExtraMeat()
                .addExtraPepperoni()
                .addExtraOlives()
                .build();
        customPizza.displayPizza();
    }
}
```

## Program Output

When executed, the program produces the following output:

```
Pizza Factory Method Pattern:
Preparing Rancho Pizza
Preparing Margherita Pizza
Preparing Barbeque Pizza

Order Manager Singleton Pattern:
Order added: Rancho
Order added: Margherita
Order added: Barbeque
Current Orders:
- Rancho
- Margherita
- Barbeque

Custom Pizza Builder Pattern:
Pizza Size: Large
Crust Type: Thin Crust
Extra Cheese: Yes
Extra Meat: Yes
Extra Pepperoni: Yes
Extra Olives: Yes
```

## Design Pattern Interactions

The three patterns work together to create a cohesive pizza ordering system:

1. **Factory Method** creates standard pizza types efficiently
2. **Singleton** ensures all orders are managed in one central location
3. **Builder** provides flexibility for creating custom pizzas with specific requirements

This combination demonstrates how different creational patterns can complement each other in a real-world application.

## Results and Analysis

### What Was Achieved

1. **Factory Method Pattern:** Successfully implemented a centralized pizza creation mechanism that can easily be extended with new pizza types without modifying client code.

2. **Singleton Pattern:** Created a global order management system that ensures a single point of control for all pizza orders throughout the application lifecycle.

3. **Builder Pattern:** Developed a flexible custom pizza builder that handles multiple optional parameters elegantly through method chaining.

4. **Separation of Concerns:** Each pattern addresses a specific aspect of object creation:

   - Factory handles standard object creation
   - Singleton manages shared resource access
   - Builder handles complex object construction

5. **Extensibility:** The system can easily accommodate:
   - New pizza types (add to factory)
   - Additional order management features (extend singleton)
   - More customization options (extend builder)

### Benefits of Using Creational Patterns

1. **Maintainability:** Changes to object creation logic are localized
2. **Flexibility:** New types and variations can be added easily
3. **Reusability:** Patterns provide proven solutions that can be reused
4. **Reduced Complexity:** Complex object creation is simplified and standardized
5. **Improved Code Quality:** Follows best practices and design principles

## Conclusion

This project successfully demonstrates the practical application of three fundamental creational design patterns in Java through a pizza ordering system:

- **Factory Method Pattern** centralizes pizza creation, providing flexibility and easy extensibility for new pizza types
- **Singleton Pattern** ensures single-instance order management, maintaining consistency across the application
- **Builder Pattern** handles complex custom pizza construction with multiple optional parameters through a fluent interface

These patterns work synergistically, each addressing different aspects of object creation. Through this implementation, I have gained practical understanding of how creational design patterns contribute to better software architecture, helping to manage complexity, improve code organization, and provide proven solutions to common design problems in object-oriented programming.
