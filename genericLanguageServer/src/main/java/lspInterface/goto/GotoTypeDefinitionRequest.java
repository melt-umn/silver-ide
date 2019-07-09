package ablecLanguageServer;

import org.json.*;

public class GotoTypeDefinitionRequest implements LSPRequest {
  private TextDocumentPositionParams params;

  public static final String METHOD_NAME = "textDocument/typeDefinition";

  public GotoTypeDefinitionRequest(JSONObject params) {
    this.params = new TextDocumentPositionParams(params);
  }
  
  public LSPResponse processRequest() {
    Server server = Server.getServer();
    DocumentUri doc = params.getDocument();
    Position position = params.getPosition();
    Location loc = server.findTypeDefinitionForSymbolAt(doc, position);
    GotoTypeDefinitionResult result = new GotoTypeDefinitionResult(loc);
    return result;
  }

  public String getMethodName() {
    return METHOD_NAME;
  }

}
