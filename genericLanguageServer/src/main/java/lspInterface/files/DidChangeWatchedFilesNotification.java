package ablecLanguageServer;

import org.json.*;

public class DidChangeWatchedFilesNotification implements LSPNotification {
  private DidChangeWatchedFilesParams params;

  public static final String METHOD_NAME = "workspace/didChangeWatchedFiles";

  public DidChangeWatchedFilesNotification(DidChangeWatchedFilesParams params) {
    this.params = params;
  }

  public DidChangeWatchedFilesNotification(JSONObject params) {
    this.params = new DidChangeWatchedFilesParams(params);
  }

  public void processNotification() {
    Server server = Server.getServer();
    DocumentUri[] changedDocs = params.getChangedDocuments();
    for (DocumentUri changedDoc : changedDocs) {
      if (server.amWatchingFile(changedDoc)) {
        Diagnostic[] errors = server.getErrorsForFile(changedDoc);
        PublishDiagnosticsParams diagnosticParams = new PublishDiagnosticsParams(changedDoc, errors);
        PublishDiagnosticsNotification diagnosticNotif = new PublishDiagnosticsNotification(diagnosticParams);
        server.addMessageToSend(diagnosticNotif, 2);

        LogMessageNotification notif = 
          new LogMessageNotification("File " + changedDoc.getUri() + " is being watched and was changed", MessageType.INFO);
        server.addMessageToSend(notif, 3);
      }
    }
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
