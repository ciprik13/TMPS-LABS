using NotificationSystem.Services;
using NotificationSystem.Models;
using NotificationSystem.Interfaces;

INotificationService emailService = new EmailNotificationService();
User user1 = new User("Alice Green", "alice.green@example.com", emailService);
user1.Notify("Hello Alice, this is a test email!");

INotificationService smsService = new SmsNotificationService();
User user2 = new User("Bob Brown", "+37398672145", smsService);
user2.Notify("Hello Bob, this is a test SMS!");


NotificationManager manager = new NotificationManager();

manager.AddNotificationService(new EmailNotificationService());
manager.AddNotificationService(new SmsNotificationService());

manager.NotifyAll("admin@example.com", "System maintenance scheduled at midnight!");