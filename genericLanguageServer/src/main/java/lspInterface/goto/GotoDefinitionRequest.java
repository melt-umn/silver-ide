package ablecLanguageServer;

import org.json.*;

public class GotoDefinitionRequest implements LSPRequest {
  private TextDocumentPositionParams params;

  public static final String METHOD_NAME = "textDocument/definition";

  public GotoDefinitionRequest(JSONObject params) {
    this.params = new TextDocumentPositionParams(params);
  }
  
  public LSPResponse processRequest() {
    Server server = Server.getServer();
    DocumentUri doc = params.getDocument();
    Position position = params.getPosition();
    Location loc = server.findDefinitionForSymbolAt(doc, position);
    GotoDefinitionResult result = new GotoDefinitionResult(loc);
    return result;
  }

  public String getMethodName() {
    return METHOD_NAME;
  }

}
