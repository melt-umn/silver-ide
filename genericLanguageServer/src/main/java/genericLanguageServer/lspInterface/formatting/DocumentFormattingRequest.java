package genericLanguageServer.lspInterface.formatting;

import genericLanguageServer.Server;
import genericLanguageServer.lspInterface.LSPRequest;
import genericLanguageServer.lspInterface.LSPResponse;
import genericLanguageServer.lspInterface.document.TextEdit;
import genericLanguageServer.lspInterface.document.DocumentUri;

import org.json.*;

public class DocumentFormattingRequest implements LSPRequest {
  private DocumentFormattingParams params;

  public static final String METHOD_NAME = "textDocument/formatting";

  public DocumentFormattingRequest(JSONObject params) {
    this.params = new DocumentFormattingParams(params);
  }

  public LSPResponse processRequest() {
    Server server = Server.getServer();
    DocumentUri doc = params.getDocument();
    if (doc != null && server.amWatchingFile(doc)) {
      TextEdit[] edits = server.formatDocument(doc);
      return new DocumentFormattingResult(edits);
    } else {
      return new DocumentFormattingResult();
    }
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
