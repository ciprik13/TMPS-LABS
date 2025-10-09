using NotificationSystem.Interfaces;

namespace NotificationSystem.Services
{
    public class SmsNotificationService : INotificationService
    {
        public void Send(string recipient, string message)
        {
            Console.WriteLine($"Sending SMS to {recipient}");
            Console.WriteLine($"SMS message: {message}");
            Console.WriteLine($"SMS sent successfully!\n");
        }
    }
}