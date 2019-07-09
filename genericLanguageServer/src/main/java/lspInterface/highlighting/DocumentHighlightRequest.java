package ablecLanguageServer;

import org.json.*;

public class DocumentHighlightRequest implements LSPRequest {
  private TextDocumentPositionParams params;

  public static final String METHOD_NAME = "textDocument/documentHighlight";

  public DocumentHighlightRequest(JSONObject params) {
    this.params = new TextDocumentPositionParams(params);
  }

  public LSPResponse processRequest() {
    DocumentUri doc = params.getDocument();
    Position position = params.getPosition();
    Server server = Server.getServer();
    Reference[] refs = server.findReferencesInDocumentForSymbolAt(doc, position);
    DocumentHighlight[] highlights = Reference.convertToDocumentHighlights(refs);
    DocumentHighlightResult result = new DocumentHighlightResult(highlights);
    return result;
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
