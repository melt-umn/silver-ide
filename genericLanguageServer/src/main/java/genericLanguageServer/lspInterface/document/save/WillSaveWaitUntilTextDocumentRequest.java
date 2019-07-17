package genericLanguageServer.lspInterface.document.save;

import genericLanguageServer.lspInterface.LSPRequest;
import genericLanguageServer.lspInterface.LSPResponse;

import org.json.*;

public class WillSaveWaitUntilTextDocumentRequest implements LSPRequest {
  private WillSaveTextDocumentParams params;

  public static final String METHOD_NAME = "textDocument/willSaveWaitUntil";

  public WillSaveWaitUntilTextDocumentRequest(JSONObject params) {
    this.params = new WillSaveTextDocumentParams(params);
  }

  public LSPResponse processRequest() {
    return new WillSaveWaitUntilTextDocumentResult();
    /*
    Server server = Server.getServer();
    DocumentUri doc = params.getDocument();
    if (doc != null && server.amWatchingFile(doc)) {
      TextEdit[] edits = server.formatDocument(doc);
      return new WillSaveWaitUntilTextDocumentResult(edits);
    } else {
      return new WillSaveWaitUntilTextDocumentResult();
    }
  }*/
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
