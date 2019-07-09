package ablecLanguageServer;

import org.json.*;

public class FindReferencesRequest implements LSPRequest {
  private ReferenceParams params;
  
  public static final String METHOD_NAME = "textDocument/references";

  public FindReferencesRequest(JSONObject params) {
    this.params = new ReferenceParams(params);
  }

  public LSPResponse processRequest() {
    Server server = Server.getServer();
    DocumentUri doc = params.getDocument();
    Position position = params.getPosition();
    Boolean includeDecl = params.shouldIncludeDeclaration();
    Reference[] refs = server.findReferencesForSymbolAt(doc, position, includeDecl);
    Location[] refLocations = Reference.convertToLocations(refs);
    FindReferencesResult result = new FindReferencesResult(refLocations);
    return result;
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
