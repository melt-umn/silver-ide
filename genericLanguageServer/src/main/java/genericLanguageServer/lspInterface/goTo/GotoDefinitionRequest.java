package genericLanguageServer.lspInterface.goTo;

import genericLanguageServer.Server;
import genericLanguageServer.lspInterface.LSPRequest;
import genericLanguageServer.lspInterface.LSPResponse;
import genericLanguageServer.lspInterface.document.TextDocumentPositionParams;
import genericLanguageServer.lspInterface.document.DocumentUri;
import genericLanguageServer.lspInterface.document.Position;
import genericLanguageServer.lspInterface.document.Location;

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
