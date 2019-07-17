package genericLanguageServer.lspInterface.document.synchronization;

import genericLanguageServer.lspInterface.LSPNotification;
import genericLanguageServer.Server;
import genericLanguageServer.lspInterface.messages.MessageType;
import genericLanguageServer.lspInterface.messages.LogMessageNotification;
import org.json.*;

public class DidChangeTextDocumentNotification implements LSPNotification {
  private DidChangeTextDocumentParams params;

  public static final String METHOD_NAME = "textDocument/didChange";

  public DidChangeTextDocumentNotification(JSONObject params) {
    this.params = new DidChangeTextDocumentParams(params);
  }

  public void processNotification() {
    Server server = Server.getServer();
    LogMessageNotification notif =
      new LogMessageNotification("Received DidChangeTextDocumentNotification for " + params.getDocument().toString(), MessageType.INFO);
    server.addMessageToSend(notif, 3);
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
