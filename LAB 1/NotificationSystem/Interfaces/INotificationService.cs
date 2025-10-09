namespace NotificationSystem.Interfaces
{
    public interface INotificationService
    {
        void Send(string recipient, string message);
    }
}