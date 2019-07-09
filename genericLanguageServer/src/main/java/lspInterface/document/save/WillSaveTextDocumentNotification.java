package ablecLanguageServer;

import org.json.*;

public class WillSaveTextDocumentNotification implements LSPNotification {
  private WillSaveTextDocumentParams params;

  public static final String METHOD_NAME = "textDocument/willSave";
  
  public WillSaveTextDocumentNotification(JSONObject params) {
    this.params = new WillSaveTextDocumentParams(params);
  }

  public void processNotification() {
    Server server = Server.getServer();
    LogMessageNotification notif = 
      new LogMessageNotification("Received WillSaveTextDocumentNotification for " + params.getDocument().toString(), MessageType.INFO);
    server.addMessageToSend(notif, 3);
  }


  public String getMethodName() {
    return METHOD_NAME;
  }
}
