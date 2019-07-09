package ablecLanguageServer;

import org.json.*;

public class DocumentOnTypeFormattingRequest implements LSPRequest {

  private DocumentOnTypeFormattingParams params;

  public static final String METHOD_NAME = "textDocument/onTypeFormatting";

  public DocumentOnTypeFormattingRequest(JSONObject params) {
    this.params = new DocumentOnTypeFormattingParams(params);
  }

  public LSPResponse processRequest() {
    Server server = Server.getServer();
    DocumentUri doc = params.getDocument();
    Position position = params.getPosition();
    String character = params.getTypedCharacter();
    if (doc != null && server.amWatchingFile(doc)) {
      TextEdit[] edits = server.formatDocumentOnType(doc, position, character);
      return new DocumentOnTypeFormattingResult(edits);
    } else {
      return new DocumentOnTypeFormattingResult();
    }
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
