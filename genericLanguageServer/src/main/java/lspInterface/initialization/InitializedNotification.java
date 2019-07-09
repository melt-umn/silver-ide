package ablecLanguageServer;

public class InitializedNotification implements LSPNotification {
  private static int notificationsReceived = 0;

  public static final String METHOD_NAME = "initialized";

  public InitializedNotification() {
    notificationsReceived += 1;
    // this should only be sent once be wary here
    if (notificationsReceived > 1) {
      Logger logger = Logger.getLogger();
      logger.logMessage("Initialized Notification sent for time number: " + notificationsReceived);
    }
  }

  public void processNotification() {
  }

  public String getMethodName() {
    return METHOD_NAME;
  }

}
