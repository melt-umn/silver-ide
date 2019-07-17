package genericLanguageServer.lspInterface.document.save;

import genericLanguageServer.lspInterface.LSPNotification;
import genericLanguageServer.Server;
import genericLanguageServer.lspInterface.messages.MessageType;
import genericLanguageServer.lspInterface.messages.LogMessageNotification;

import org.json.*;

public class DidSaveTextDocumentNotification implements LSPNotification {
  private DidSaveTextDocumentParams params;

  public static final String METHOD_NAME = "textDocument/didSave";

  public DidSaveTextDocumentNotification(JSONObject params) {
    this.params = new DidSaveTextDocumentParams(params);
  }

  public void processNotification() {
    Server server = Server.getServer();
    LogMessageNotification notif =
      new LogMessageNotification("Received DidSaveTextDocumentNotification for " + params.getDocument().toString(), MessageType.INFO);
    server.addMessageToSend(notif, 3);
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
