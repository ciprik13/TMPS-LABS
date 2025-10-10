# SOLID Principles Implementation - Notification System

## Laboratory Overview

This Laboratory Work is a console-based notification system implemented in C# that demonstrates three core SOLID principles: Single Responsibility Principle (S), Open/Closed Principle (O), and Dependency Inversion Principle (D). The system simulates sending notifications through different channels such as email and SMS.

## SOLID Principles Implementation

### 1. Single Responsibility Principle (SRP)

**Definition:** A class should have only one reason to change, meaning it should have only one job or responsibility.

**Implementation:**

The SRP is implemented by separating notification concerns into distinct classes, each handling a specific type of notification.

**INotificationService Interface** (`Interfaces/INotificationService.cs`):
```csharp
namespace NotificationSystem.Interfaces
{
    public interface INotificationService
    {
        void Send(string recipient, string message);
    }
}
```

This interface defines a contract that all notification services must follow. Its single responsibility is to define the signature for sending notifications.

**EmailNotificationService** (`Services/EmailNotificationService.cs`):
```csharp
public class EmailNotificationService : INotificationService
{
    public void Send(string recipient, string message)
    {
        Console.WriteLine($"Sending email to {recipient}");
        Console.WriteLine($"Email message: {message}");
        Console.WriteLine($"Email sent successfully!\n");
    }
}
```

This class has a single responsibility: sending email notifications. If the email sending logic needs to change (e.g., connecting to an SMTP server), only this class would be modified.

**SmsNotificationService** (`Services/SmsNotificationService.cs`):
```csharp
public class SmsNotificationService : INotificationService
{
    public void Send(string recipient, string message)
    {
        Console.WriteLine($"Sending SMS to {recipient}");
        Console.WriteLine($"SMS message: {message}");
        Console.WriteLine($"SMS sent successfully!\n");
    }
}
```

This class has a single responsibility: sending SMS notifications. Changes to SMS logic do not affect email notifications.

**Benefits:**
- Each class is focused on a single task
- Changes to one notification type do not affect others
- Code is easier to understand and maintain
- Testing is simplified as each class can be tested independently

### 2. Open/Closed Principle (OCP)

**Definition:** Software entities should be open for extension but closed for modification. You should be able to add new functionality without changing existing code.

**Implementation:**

The OCP is demonstrated through the NotificationManager class, which can accommodate new notification services without requiring modifications to its core logic.

**NotificationManager** (`Services/NotificationManager.cs`):
```csharp
public class NotificationManager
{
    private readonly List<INotificationService> _notificationServices;

    public NotificationManager()
    {
        _notificationServices = new List<INotificationService>();
    }

    public void AddNotificationService(INotificationService service)
    {
        _notificationServices.Add(service);
    }

    public void NotifyAll(string recipient, string message)
    {
        Console.WriteLine($"\nBroadcasting message to {recipient}...\n");
        
        foreach (var service in _notificationServices)
        {
            service.Send(recipient, message);
        }
        
        Console.WriteLine($"Message delivered through {_notificationServices.Count} channels!");
    }
}
```

**Usage Example** (`Program.cs`):
```csharp
NotificationManager manager = new NotificationManager();

manager.AddNotificationService(new EmailNotificationService());
manager.AddNotificationService(new SmsNotificationService());

manager.NotifyAll("admin@example.com", "System maintenance scheduled at midnight!");
```

**Extension Without Modification:**

If we need to add a new notification type (e.g., Push Notifications), we simply:
1. Create a new class `PushNotificationService` that implements `INotificationService`
2. Add it to the manager: `manager.AddNotificationService(new PushNotificationService())`

The `NotificationManager` class itself requires no changes. It is closed for modification but open for extension.

**Benefits:**
- New notification types can be added without modifying existing classes
- Reduces the risk of introducing bugs in tested code
- Promotes code reusability and scalability

### 3. Dependency Inversion Principle (DIP)

**Definition:** High-level modules should not depend on low-level modules. Both should depend on abstractions. Abstractions should not depend on details. Details should depend on abstractions.

**Implementation:**

The DIP is implemented through the User class, which depends on the `INotificationService` interface rather than concrete notification service implementations.

**User Class** (`Models/User.cs`):
```csharp
public class User
{
    public string Name { get; set; }
    public string Email { get; set; }
    private readonly INotificationService _notificationService;

    public User(string name, string email, INotificationService notificationService)
    {
        Name = name;
        Email = email;
        _notificationService = notificationService;
    }

    public void Notify(string message)
    {
        Console.WriteLine($"[User: {Name}] Preparing notification...");
        _notificationService.Send(Email, message);
    }
}
```

The `User` class (high-level module) depends on the `INotificationService` interface (abstraction), not on `EmailNotificationService` or `SmsNotificationService` (low-level modules).

**Usage Example** (`Program.cs`):
```csharp
INotificationService emailService = new EmailNotificationService();
User user1 = new User("Alice Green", "alice.green@example.com", emailService);
user1.Notify("Hello Alice, this is a test email!");

INotificationService smsService = new SmsNotificationService();
User user2 = new User("Bob Brown", "+37398672145", smsService);
user2.Notify("Hello Bob, this is a test SMS!");
```

**Dependency Injection:**

The notification service is injected through the constructor, allowing us to:
- Pass any implementation of `INotificationService`
- Swap implementations without modifying the `User` class
- Easily create mock services for testing

**Benefits:**
- User class is decoupled from specific notification implementations
- Easy to swap notification methods at runtime
- Facilitates unit testing with mock objects
- Reduces tight coupling between classes

## Program Output

When executed, the program produces the following output:

```
[User: Alice Green] Preparing notification...
Sending email to alice.green@example.com
Email message: Hello Alice, this is a test email!
Email sent successfully!

[User: Bob Brown] Preparing notification...
Sending SMS to +37398672145
SMS message: Hello Bob, this is a test SMS!
SMS sent successfully!

Broadcasting message to admin@example.com...

Sending email to admin@example.com
Email message: System maintenance scheduled at midnight!
Email sent successfully!

Sending SMS to admin@example.com
SMS message: System maintenance scheduled at midnight!
SMS sent successfully!

Message delivered through 2 channels!
```

## Results and Analysis

### What Was Achieved

1. **Modularity:** The system is divided into distinct, independent components that each serve a specific purpose.

2. **Extensibility:** New notification types can be added by simply creating new classes that implement the `INotificationService` interface.

3. **Maintainability:** Changes to one notification method do not require changes to other parts of the system.

4. **Testability:** Each component can be tested independently, and mock services can easily be created for testing purposes.

5. **Flexibility:** The notification method for users can be changed at runtime without modifying the User class.

## Conclusion

This project successfully demonstrates the practical application of three fundamental SOLID principles in C#:

**Single Responsibility Principle** ensures that each class has a clear, single purpose, making the codebase easier to understand and maintain. By separating email and SMS notification logic into distinct classes, we avoid the pitfall of creating monolithic classes that are difficult to modify and test.

**Open/Closed Principle** allows the system to be extended with new notification types without modifying existing, tested code. The `NotificationManager` class demonstrates this principle by working with the `INotificationService` interface, enabling seamless addition of new notification channels.

**Dependency Inversion Principle** reduces coupling between high-level and low-level modules by introducing abstractions. The `User` class depends on the `INotificationService` interface rather than concrete implementations, providing flexibility and enabling easier testing.

These principles work together to create a robust, maintainable, and extensible notification system. The architecture allows for future enhancements such as adding push notifications, Slack messages, or other communication channels without disrupting the existing codebase.

Through this implementation, I have gained practical understanding of how SOLID principles contribute to better software design and why they are considered fundamental best practices in object-oriented programming.