using NotificationSystem.Interfaces;

namespace NotificationSystem.Services
{
    public class EmailNotificationService : INotificationService
    {
        public void Send(string recipient, string message)
        {
            Console.WriteLine($"Sending email to {recipient}");
            Console.WriteLine($"Email message: {message}");
            Console.WriteLine($"Email sent successfully!\n");
        }
    }
}