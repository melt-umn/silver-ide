package ablecLanguageServer;
public class Message {
  String jsonrpc;

  public static final int OBJECT_PARAMS = 1;
  public static final int ARRAY_PARAMS = 2;

  public Message() {
    jsonrpc = "2.0";
  }
}
