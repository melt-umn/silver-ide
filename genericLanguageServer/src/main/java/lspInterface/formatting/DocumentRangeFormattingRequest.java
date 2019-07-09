package ablecLanguageServer;

import org.json.*;

public class DocumentRangeFormattingRequest implements LSPRequest {
  private DocumentRangeFormattingParams params;

  public static final String METHOD_NAME = "textDocument/rangeFormatting";

  public DocumentRangeFormattingRequest(JSONObject params) {
    this.params = new DocumentRangeFormattingParams(params);
  }

  public LSPResponse processRequest() {
    Server server = Server.getServer();
    DocumentUri doc = params.getDocument();
    Range range = params.getRange();
    if (doc != null && server.amWatchingFile(doc)) {
      TextEdit[] edits = server.formatDocumentRange(doc, range);
      return new DocumentRangeFormattingResult(edits);
    } else {
      return new DocumentRangeFormattingResult();
    }
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
