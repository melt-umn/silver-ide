package genericLanguageServer.lspInterface.messages;

import genericLanguageServer.lspInterface.ServerInitiatedLSPNotification;
import genericLanguageServer.JSONBuilder;

public class LogMessageNotification implements ServerInitiatedLSPNotification {
  private LogMessageParams params;

  public static final String METHOD_NAME = "window/logMessage";

  public LogMessageNotification(String message, Integer type) {
    params = new LogMessageParams(type, message);
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

  public String getMethodName() {
    return METHOD_NAME;
  }
}
