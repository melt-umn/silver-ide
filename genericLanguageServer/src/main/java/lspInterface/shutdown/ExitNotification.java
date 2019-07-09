package ablecLanguageServer;

public class ExitNotification implements LSPNotification {

  public static final String METHOD_NAME = "exit";

  public ExitNotification() {

  }

  public void processNotification() {
    System.exit(0);
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
