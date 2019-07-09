package ablecLanguageServer;

import org.json.*;

public class DidOpenTextDocumentNotification implements LSPNotification {
  private DidOpenTextDocumentParams params;

  public static final String METHOD_NAME = "textDocument/didOpen";

  public DidOpenTextDocumentNotification(JSONObject params) {
    this.params = new DidOpenTextDocumentParams(params);
  }

  public void processNotification() {
    Server server = Server.getServer();
    LogMessageNotification notif = 
      new LogMessageNotification("Received DidOpenFileNotification for " + params.getDocument().toString(), MessageType.INFO);
    server.addMessageToSend(notif, 3);
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
