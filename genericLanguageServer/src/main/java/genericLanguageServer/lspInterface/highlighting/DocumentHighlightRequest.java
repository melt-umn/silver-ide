package genericLanguageServer.lspInterface.highlighting;

import genericLanguageServer.lspInterface.LSPRequest;
import genericLanguageServer.lspInterface.LSPResponse;
import genericLanguageServer.lspInterface.document.TextDocumentPositionParams;
import genericLanguageServer.lspInterface.document.DocumentUri;
import genericLanguageServer.lspInterface.document.Position;
import genericLanguageServer.lspInterface.references.Reference;

import genericLanguageServer.Server;


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
