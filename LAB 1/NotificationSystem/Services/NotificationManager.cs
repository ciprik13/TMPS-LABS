using NotificationSystem.Interfaces;

namespace NotificationSystem.Services
{
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
}