package genericLanguageServer.lspInterface.messages;

import org.json.*;

public class NotificationMessage extends Message {
  private String method;
  private JSONObject params;

  public NotificationMessage(String method, JSONObject params) {
    this.method = method;
    this.params = params;
  }

  public String getMethod() {
    return method;
  }

  public JSONObject getParams() {
    return params;
  }
}
