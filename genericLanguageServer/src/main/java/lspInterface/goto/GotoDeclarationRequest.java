package ablecLanguageServer;

import org.json.*;

public class GotoDeclarationRequest implements LSPRequest {
  private TextDocumentPositionParams params;

  public static final String METHOD_NAME = "textDocument/declaration";

  public GotoDeclarationRequest(JSONObject params) {
    this.params = new TextDocumentPositionParams(params);
  }
  
  public LSPResponse processRequest() {
    Server server = Server.getServer();
    DocumentUri doc = params.getDocument();
    Position position = params.getPosition();
    Location loc = server.findDeclarationForSymbolAt(doc, position);
    GotoDeclarationResult result = new GotoDeclarationResult(loc);
    return result;
  }

  public String getMethodName() {
    return METHOD_NAME;
  }

}
