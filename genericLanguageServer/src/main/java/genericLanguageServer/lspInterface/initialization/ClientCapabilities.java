package genericLanguageServer.lspInterface.initialization;

import org.json.*;

public class ClientCapabilities {
  private WorkspaceClientCapabilities workspace;
  private TextDocumentClientCapabilities textDocument;

  public ClientCapabilities(JSONObject obj) {
      if (obj.has("workspace")) {
        workspace = new WorkspaceClientCapabilities(obj.getJSONObject("workspace"));
      }
      if (obj.has("textDocument")) {
        textDocument = new TextDocumentClientCapabilities(obj.getJSONObject("textDocument"));
      }
  }

  public boolean sendsWillSaveNotification() {
    return textDocument.sendsWillSaveNotification();
  }

  public boolean sendsWillSaveWaitUntilRequest() {
    return textDocument.sendsWillSaveWaitUntilRequest();
  }

  public boolean sendsDidSaveNotification() {
    return textDocument.sendsDidSaveNotification();
  }
}
