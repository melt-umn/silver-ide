package ablecLanguageServer;
public class ServerInitiatedMessage implements Comparable<ServerInitiatedMessage> {
  private int priority;
  private String message;

  public ServerInitiatedMessage(ServerInitiatedLSPNotification notif, int priority) {
    this.priority = priority;
    this.message = notif.getJson();
  }

  public int getPriority() {
    return priority;
  }

  public int compareTo(ServerInitiatedMessage message) {
    return priority - message.getPriority();
  }

  public void modifyMessage(String message) {
    if (message != null) {
      this.message = message;
    }
  }

  public String getMessage() {
    return message;
  }
}
