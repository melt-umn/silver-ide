package ablecLanguageServer;
public class ShowMessageNotification implements ServerInitiatedLSPNotification {
  private ShowMessageParams params;

  public static final String METHOD_NAME = "window/showMessage";

  public ShowMessageNotification(String message, Integer type) {
    params = new ShowMessageParams(type, message);
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("jsonrpc", "2.0");
    json.addKeyValuePair("method", METHOD_NAME);
    json.addKeyValuePair("params", params);
    json.endObject();
    return json.getJson();
  }

  public String getMessage() {
    return params.getMessage();
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
