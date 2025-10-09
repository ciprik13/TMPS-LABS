using NotificationSystem.Interfaces;

namespace NotificationSystem.Models
{
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
}