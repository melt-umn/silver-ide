package ablecLanguageServer;

import org.json.*;

public class GotoImplementationRequest implements LSPRequest {
  private TextDocumentPositionParams params;

  public static final String METHOD_NAME = "textDocument/implementation";

  public GotoImplementationRequest(JSONObject params) {
    this.params = new TextDocumentPositionParams(params);
  }
  
  public LSPResponse processRequest() {
    Server server = Server.getServer();
    DocumentUri doc = params.getDocument();
    Position position = params.getPosition();
    Location loc = server.findImplementationForSymbolAt(doc, position);
    GotoImplementationResult result = new GotoImplementationResult(loc);
    return result;
  }

  public String getMethodName() {
    return METHOD_NAME;
  }

}
