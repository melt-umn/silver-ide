package ablecLanguageServer;

import org.json.*;

public class HoverRequest implements LSPRequest {
  private TextDocumentPositionParams params;

  public static final String METHOD_NAME = "textDocument/hover";  

  public HoverRequest(TextDocumentPositionParams params) {
    this.params = params;
  }

  public HoverRequest(JSONObject params) {
    this.params = new TextDocumentPositionParams(params);
  }

  public Position getPosition() {
    return params.getPosition();
  }

  public DocumentUri getDocument() {
    return params.getDocument();
  }

  public String getMethodName() {
    return METHOD_NAME;
  }

  public LSPResponse processRequest() {
    // processing to determine hover   
    String hoverContents = "You are hovering in " + params.getDocument().getUri() + " at " + params.getPosition().toString();
    LSPResponse result = new Hover(hoverContents);
    return result;
  }
}
