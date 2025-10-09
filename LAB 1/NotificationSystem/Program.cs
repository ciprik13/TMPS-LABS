using NotificationSystem.Interfaces;
using NotificationSystem.Services;

INotificationService emailService = new EmailNotificationService();
emailService.Send("alice.green@example.com", "Hello Alice, this is a test email!");

INotificationService smsService = new SmsNotificationService();
smsService.Send("+37398672145", "Hello Bob, this is a test SMS!");
