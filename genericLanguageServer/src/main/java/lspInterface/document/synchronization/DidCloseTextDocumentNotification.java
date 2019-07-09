package ablecLanguageServer;

import org.json.*;

public class DidCloseTextDocumentNotification implements LSPNotification {
  
  private DidCloseTextDocumentParams params;

  public static final String METHOD_NAME = "textDocument/didClose";

  public DidCloseTextDocumentNotification(JSONObject params) {
    this.params = new DidCloseTextDocumentParams(params);
  }

  public void processNotification() {
    Server server = Server.getServer();
    LogMessageNotification notif = 
      new LogMessageNotification("Received DidCloseTextDocumentNotification for " + params.getDocument().toString(), MessageType.INFO);
    server.addMessageToSend(notif, 3);
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
